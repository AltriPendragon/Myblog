var testEditor;
var count = 0;
var count1 = 0;

var checkUrl = location.href;
var editIds = checkUrl.split("/");
var editId = editIds[4];

var editValue = "";//初始编辑值
var updateTag = 0;


//编辑得到文章内容
if(editId!=undefined&&editId!=""){
    $.ajax({
        type:"get",
        url:"/getArticleById",
        dataType:"json",
        async:false,
        data:{
            id:editId
        },

        success:function (data) {
            updateTag = 1;
            editValue = data['content'];
            var tagArray = data['tags'].split(",");
            var typeArray = data['category'].split(",");

            $("#article-title").val(data['title']);
            $.each(tagArray,function (index, value) {
                var tagItem = '<div class="tag-item">\n' +
                    '                                    <span contenteditable="true" class="tag-name">'+value+'</span>\n' +
                    '                                    <button class="am-btn am-icon-times close-tag button-control"></button>\n' +
                    '                                </div>';
                $(".tag-list").append(tagItem);
            })

            $.each(typeArray,function (index, value) {
                var typeItem = '<div class="type-item">\n' +
                    '                                    <span contenteditable="true" class="type-name">'+value+'</span>\n' +
                    '                                    <button class="am-btn am-icon-times close-tag-type button-control"></button>\n' +
                    '                                </div>';

                $(".type-list").append(typeItem);
            })

            $(".select-choice option[value="+data['type']+"]").attr('selected','selected');


        }
    })
}


//            var page = false;
//            window.onbeforeunload = function checkLeave(e){
//                console.log("bbbbbb")
//                var evt = e ? e : (window.event ? window.event : null);  //此方法为了在firefox中的兼容
//                if(!page){
//                    evt.returnValue='您还未保存报告，确定要离开当前页面？';
//                    console.log("aaaaaaaaaa")
//                }
//            };

$(".article-cancel").on('click',function () {
    $('#select-model').modal('close');
})

$(window).bind('beforeunload',function(){
    return '您输入的内容尚未保存，确定离开此页面吗？';
});


$(function() {
    testEditor = editormd("test-editormd", {
        width   : "90%",
        height  : 820,
        syncScrolling : "single",
        saveHTMLToTextarea : true,
        htmlDecode : "style,script,iframe|on*",
        emoji : true,
        imageUpload : true,
        previewTheme : "dark",
        imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
        imageUploadURL : "/uploadBlogImg",
        path    : "/editTest/lib/",
        value:editValue
    });

    /*
    // or
    testEditor = editormd({
        id      : "test-editormd",
        width   : "90%",
        height  : 640,
        path    : "../lib/"
    });
    */


    // $(".publish").bind('click',function () {
    //     // console.log(testEditor.getMarkdown())
    //     console.log(testEditor.getHTML())
    // })

});


$(".add-tag").click(function () {

    var temp = $(".tag-list").parent().children(".tag-list").children(".tag-item:last-child").children("span").text();
    console.log($(this).parent().children(".tag-list").children(".tag-item:last-child").children("span").text())
    if(temp!=""||count==0){
        count++;
        $(this).parent().children(".tag-list").append('<div class="tag-item">' +
            '<span contenteditable="true" class="tag-name"></span>' +
            '<button class="am-btn am-icon-times close-tag button-control"></button>' +
            '</div>');
        //$(".tag-item").last().children("span").focus();
        $(this).parent().children(".tag-list").children(".tag-item:last-child").children("span").focus();
    }
})

$(document).on("click",".close-tag",function () {
    $(this).parent().remove()
    count--;
})


$(".add-tag-type").click(function () {

    var temp = $(this).parent().children(".type-list").children(".type-item:last-child").children("span").text();
    console.log($(this).parent().children(".type-list").children(".type-item:last-child").children("span").text())
    if(temp!=""||count1==0){
        count1++;
        $(this).parent().children(".type-list").append('<div class="type-item">' +
            '<span contenteditable="true" class="type-name"></span>' +
            '<button class="am-btn am-icon-times close-tag-type button-control"></button>' +
            '</div>');
        //$(".tag-item").last().children("span").focus();
        $(this).parent().children(".type-list").children(".type-item:last-child").children("span").focus();
    }
})

$(document).on("click",".close-tag-type",function () {
    $(this).parent().remove()
    count1--;
})


$(".article-publish").on('click',function () {

    var tag="" ;
    var category="";
    var title = $('#article-title').val();
    $(window).unbind('beforeunload');
    var content = testEditor.getMarkdown();
    var contentHtml  = testEditor.getHTML();
    var type = $(".text-field option:selected").val();

    $('.tag-item').each(function (i,n) {
        if(i<$('.tag-item').length-1){
            tag = tag+$(n).find("span").text()+",";
        }

        else {
            tag = tag+$(n).find("span").text();
        }
        console.log(tag)
    })


    $('.type-item').each(function (i,n) {
        if(i<$('.type-item').length-1){
            category = category+$(n).find("span").text()+",";
        }

        else {
            category = category+$(n).find("span").text();
        }
        console.log(category)
    })






    if(updateTag==0){

        var article = {
            content:content,
            category:category,
            tags:tag,
            title:title,
            type:type,
            htmlStr:contentHtml
        }

        $.ajax({
            type:"post",
            contentType: "application/json;charset=UTF-8",
            url:"/publishArticle",
            dataType:"json",
            data: JSON.stringify(article),
            success:function (data) {
                if(data.status==200){
                    console.log('container')
                    $('#select-model').modal('close');
                    $('.container').css('display','block');

                    var info_box = '<div class="title">'+data['title']+'</div>\n' +
                        '                        <div class="desc">'+data['summary']+'</div>\n' +
                        '                        <div class="text">仗剑天涯，从你的摘要开始</div>'

                    $(".article-info-box").empty();
                    $(".article-info-box").append(info_box);
                }
            },

            error:function () {
                $('#select-model').modal('close');
            }
        })
    }

    else {

        var article = {
            content:content,
            category:category,
            tags:tag,
            title:title,
            type:type,
            htmlStr:contentHtml,
            articleId:editId
        }

        $.ajax({
            type:"post",
            contentType: "application/json;charset=UTF-8",
            url:"/updateArticle",
            dataType:"json",
            data: JSON.stringify(article),
            success:function (data) {
                if(data.status=200){
                    $('#select-model').modal('close');
                }
            },

            error:function () {
                $('#select-model').modal('close');
            }
        })
    }




})
