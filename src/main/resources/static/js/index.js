

var temp;

//切换音乐播放器
function switchMusicBox() {

    $(".music-box").hasClass("music-box-hidden")?
        $(".music-box").removeClass("music-box-hidden"):
        $(".music-box").addClass("music-box-hidden")


    if($(".switch-btn").hasClass("am-icon-chevron-right")==true){
        $(".switch-btn").removeClass("am-icon-chevron-right");
        $(".switch-btn").addClass("am-icon-chevron-left");
    }


    else {
        $(".switch-btn").removeClass("am-icon-chevron-left");
        $(".switch-btn").addClass("am-icon-chevron-right");
    }

}

//获得标签云
function getTagCloud() {
    $.ajax({
        type:"get",
        url:"/getTagsCloudPlus",
        dataType:"json",

        success:function (data) {
            if(data['status']==200){
                $(".tag-cloud-items").empty();
                $.each(data['result'],function (index, obj) {
                    var item = '<a href="/tag?tag='+obj['tag']+'" style="font-size:'+obj['tagSize']+'px">'+obj['tag']+'</a>';
                    $(".tag-cloud-items").append(item)
                })

                $(".tag-info-block-text").text(data['result'].length)

            }

        }
    })
}

//登录后获得用户名
function isLoginPlus() {
    $.ajax({
        type:"get",
        url:"/isLoginPlus",
        dataType:"json",
        success:function (data) {
            if(data['state']==1){
                $(".user-name-show").text(data['name']);
            }
        }
    })
}

//根据上一次的位置初始化图片位置
function initSize() {

    var height = $('html,body').scrollTop();
    $(document).scrollTop(0);
    temp = window.innerHeight-20;
    $(".header-bg-1").css("height",temp+"px")

    if(height!=0){
        $('html,body').animate({scrollTop:temp},600);
    }

}

//请求第几页文章
function ajaxFirst(index) {
    $.ajax({
        type:"post",
        url:"/getPageArticles",
        dataType:"json",
        data:{
            rows:8,
            pageNum:index+1
        },

        success:function (data) {
            if(data.status==200){

                $('html,body').animate({scrollTop:temp},600);

                $(".article-info-block-text").text(data['totalSize'])

                $("#Pagination").pagination(data['totalSize'], {
                    num_edge_entries: 1, //边缘页数
                    num_display_entries: 3, //主体页数
                    load_first_page:false,
                    callback: function (index) {
                        ajaxFirst(index);
                    },
                    items_per_page:8,//每页显示1项
                    current_page:index,
                    prev_text : '<<',
                    next_text : '>>',

                });

                $(".articles").empty();

                $.each(data['result'],function (index, obj) {
                    var type="原创";
                    if(obj['type']==2){
                        type="转载"
                    }

                    if(obj['type']==3){
                        type="翻译"
                    }


                    var tag = obj['tags'].split(",");
                    var tagHtml="";
                    $.each(tag,function (index,value) {
                        tagHtml+='<a href="/tag/?tag='+value+'" class="article-meta-tag-innerTag">'+value+'</a>'
                    })


                    var category = obj['category'].split(",");
                    var categoryHtml = "";
                    $.each(category,function (index,value) {
                        categoryHtml+='&nbsp;<a href="/category/?category='+value+'">'+value+'</a>'
                    })


                    var article = '<div class="am-divider"></div>\n' +
                        '                <div class="article">\n' +
                        '\n' +
                        '                    <div class="article-header">\n' +
                        '                        <a class="label">'+type+'</a>\n' +
                        '\n' +
                        '                        <h2 class="article-title">&nbsp;&nbsp;<a href="/article/'+obj['articleId']+'" target="_blank">'+obj['title']+'</a> </h2>\n' +
                        '                        <span class="title-left"></span>\n' +
                        '                        <div class="my-icon" data-text="'+obj['read']+'"></div>\n' +
                        '\n' +
                        '                    </div>\n' +
                        '                    \n' +
                        '                    <div class="article-meta-top">\n' +
                        '                    </div>\n' +
                        '                    <div class="article-entry-content">&nbsp;'+obj['summary']+'</div>\n' +
                        '\n' +
                        '                    <hr class="article-divider">\n' +
                        '                    <div class="article-meta-bottom">\n' +
                        '\n' +
                        '                        <span class="am-icon-calendar article-meta-tag">&nbsp;<a href="/archive/?archive='+obj['createTime']+'">'+obj['createTime']+'</a></span>\n' +
                        '                        <span class="am-icon-folder article-meta-tag">'+categoryHtml+'</span>\n' +
                        '                        <span class="am-icon-tags article-meta-tag">&nbsp;'+tagHtml+'</span>\n' +
                        '                        <span><a class="read-all" href="/article/'+obj['articleId']+'">阅读全文</a></span>\n' +
                        '                    </div>\n' +
                        '                    <!--<div class="article-tags">sdfsdf</div>-->\n' +
                        '\n' +
                        '                </div>'


                    $('.articles').append(article);
                })
            }
        },

        error:function () {
        }
    })
}

initSize();

getTagCloud();

isLoginPlus();

window.onresize=function () {
    var temp = window.innerHeight-20;
    $(".header-bg-1").css("height",temp+"px")

}

//点击跳过图片
$(".header-down").on("click",function (event) {
    var pageOffset = window.innerHeight-20;
    event.preventDefault();
    $('html,body').animate({scrollTop:pageOffset},600);
})


//监听位置改变导航栏的颜色
$(document).ready(function () {
    var height = window.innerHeight;
    $(window).scroll(function () {
        if($(document).scrollTop()>=height-18){
            $(".am-topbar").addClass("nav-color-change")
            $(".am-topbar-nav a").css("color","#0e90d2")
            $(".am-topbar-brand a").css("color","#4d4d4d")

        }

        else {
            $(".am-topbar").removeClass("nav-color-change")
            $(".am-topbar-nav a").css("color","#ffffff")
            $(".am-topbar-brand a").css("color","#ffffff")

        }
    })
})
ajaxFirst(0);