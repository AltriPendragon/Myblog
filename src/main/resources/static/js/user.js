var tag = 0;//表示是否上传图片
var editId = 0;//友链点击编辑后存储editId
var defaultImg = "https://fjxblog.oss-cn-shenzhen.aliyuncs.com/user/1/blogImg/defaultHeadImg.jpg"
var defaultImgGirl = "https://fjxblog.oss-cn-shenzhen.aliyuncs.com/user/1/blogImg/defaultHeadImgGirl.jpg";

var no_data = '<ul class="comment-information-list">\n' +
    '                            <div class="no-data">\n' +
    '                                <img src="https://fjxblog.oss-cn-shenzhen.aliyuncs.com/user/1/blogImg/nodata.png" class="no-data-img">\n' +
    '                            </div>\n' +
    '                        </ul>'

function getUserInfo() {
    $.ajax({
        type:"get",
        url:"/getUserInfo",
        dataType:"json",
        success:function (data) {
            var gender = "";
            $(".form-body").empty()

            if(data['gender']==0){
                gender = '<div class="am-u-sm-9">\n' +
                    '                                        <label class="am-radio-inline sex-choice">\n' +
                    '                                            <input type="radio" name="gender" value="1" data-am-ucheck>\n' +
                    '                                            <span class="am-icon-male"></span>\n' +
                    '                                        </label>\n' +
                    '                                        <label class="am-radio-inline sex-choice">\n' +
                    '                                            <input type="radio" name="gender" value="0" data-am-ucheck checked>\n' +
                    '                                            <span class="am-icon-female"></span>\n' +
                    '                                        </label>\n' +
                    '                                    </div>'
            }

            else{
                gender = '<div class="am-u-sm-9">\n' +
                    '                                        <label class="am-radio-inline sex-choice">\n' +
                    '                                            <input type="radio" name="gender" value="1" data-am-ucheck checked>\n' +
                    '                                            <span class="am-icon-male"></span>\n' +
                    '                                        </label>\n' +
                    '                                        <label class="am-radio-inline sex-choice">\n' +
                    '                                            <input type="radio" name="gender" value="0" data-am-ucheck>\n' +
                    '                                            <span class="am-icon-female"></span>\n' +
                    '                                        </label>\n' +
                    '                                    </div>'
            }



            var user = '<form class="am-form line-form">\n' +
                '                                <div class="am-form-group">\n' +
                '                                    <div class="am-u-sm-3 head-img-text">头像:</div>\n' +
                '                                    <div class="am-u-sm-9">\n' +
                '                                        <div class="am-form-group am-form-file">\n' +
                '                                            <div class="user-head-img">\n' +
                '                                                <img src="'+data['imgUrl']+'"  class="am-round">\n' +
                '                                            </div>\n' +
                '                                            <button class="am-btn am-btn-danger am-btn-sm am-btn-upload" type="button">\n' +
                '                                                <i class="am-icon-cloud-upload"></i>\n' +
                '                                                更改头像\n' +
                '                                            </button>\n' +
                '                                            <input id="upload-headImg" type="file" name="file">\n' +
                '                                        </div>\n' +
                '                                    </div>\n' +
                '                                </div>\n' +
                '                                <div class="am-form-group">\n' +
                '                                    <label class="am-u-sm-3 am-form-label">\n' +
                '                                        昵称:\n' +
                '                                    </label>\n' +
                '                                    <div class="am-u-sm-9">\n' +
                '                                        <input type="text" value="'+data['name']+'" class="userInfo-input" id="user-name">\n' +
                '                                    </div>\n' +
                '                                </div>\n' +
                '                                <div class="am-form-group">\n' +
                '                                    <label class="am-u-sm-3 am-form-label">\n' +
                '                                        手机:\n' +
                '                                    </label>\n' +
                '                                    <div class="am-u-sm-9">\n' +
                '                                        <input type="text" value="'+data['telephone']+'" class="userInfo-input" id="user-telephone" disabled="disabled">\n' +
                '                                    </div>\n' +
                '                                </div>\n' +
                '                                <div class="am-form-group">\n' +
                '                                    <label class="am-u-sm-3 am-form-label">\n' +
                '                                        性别:\n' +
                '                                    </label>'+gender+'\n' +
                '                                </div>\n' +
                '                                <div class="am-form-group">\n' +
                '                                    <label class="am-u-sm-3 am-form-label">\n' +
                '                                        个人信息:\n' +
                '                                    </label>\n' +
                '                                    <div class="am-u-sm-9">\n' +
                '                                        <textarea placeholder="个人信息" class="user-info" id="user-info">'+data['info']+'</textarea>\n' +
                '                                    </div>\n' +
                '                                </div>\n' +
                '\n' +
                '                                <div class="am-form-group">\n' +
                '                                    <div class="am-u-sm-3"></div>\n' +
                '                                    <div class="am-u-sm-9">\n' +
                '                                        <button class="am-btn am-btn-primary user-info-btn" type="button">保存</button>\n' +
                '                                    </div>\n' +
                '                                </div>\n' +
                '                            </form>'


            $(".form-body").append(user);
        }
    })
}
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



getUserInfo();
isLoginPlus();



function imgSrcToBase64(img) {
    var canvas = document.createElement("canvas");
    canvas.width = img.width;
    canvas.height = img.height;
    var ctx = canvas.getContext("2d");
    ctx.drawImage(img,0,0,img.width,img.height);
    return canvas.toDataURL();
}


function getObjectURL(file) {
    var url = null;
    if (window.createObjectURL!=undefined) {
        url = window.createObjectURL(file) ;
    } else if (window.URL!=undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file) ;
    } else if (window.webkitURL!=undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file) ;
    }
    return url ;
}

//单选框选择改变后图像变化
$(document).on("change","input[type=radio][name=gender]",function () {
    if(tag==0){
        if (this.value == 1) {
            $(".user-head-img img").attr("src",defaultImg);
        }
        else if(this.value == 0) {
            $(".user-head-img img").attr("src",defaultImgGirl);
        }
    }

})

//点击上传文件后显示
$(document).on("change","#upload-headImg",function () {
    var objUrl = getObjectURL(this.files[0]) ;//获取文件信息
    if (objUrl) {
        $(".user-head-img img").attr("src", objUrl);
    }
    tag = 1;
})


//友链事件
$(document).on("click",".link-edit-save-btn",function () {

    var nickname = $("#nickname").val();
    var introduce = $("#introduce").val();
    var url = $("#url").val();
    var headImg = $("#headImg").val();
    $.ajax({
        type:"get",
        url:"/updateLink",
        dataType:"json",
        data:{
            id:editId,
            name:nickname,
            introduce:introduce,
            url:url,
            headImg:headImg
        },

        success:function (data) {
            if(data['status']==200){
                layer.alert('更新成功',{
                    icon:1,
                    skin: 'layer-ext-moon'
                })
            }

            else {
                layer.alert('更新失败',{
                    icon:2,
                    skin: 'layer-ext-moon'
                })

            }

            window.location.reload()
        }
    })


})

$(document).on("click",".link-item-add",function () {
    var nickname = $("input[name='nickname']").val()
    var introduce = $("input[name='introduce']").val()
    var url = $("input[name='url']").val()
    var headImg = $("input[name='headImg']").val()

    $.ajax({
        type:"get",
        url:"/addLink",
        dataType:"json",
        data:{
            name:nickname,
            introduce:introduce,
            url:url,
            headImg:headImg
        },

        success:function (data) {
            if(data['status']==200){
                layer.alert('更新成功',{
                    icon:1,
                    skin: 'layer-ext-moon'
                })
            }

            else {
                layer.alert('更新失败',{
                    icon:2,
                    skin: 'layer-ext-moon'
                })

            }

            window.location.reload()
        }
    })
})

$(document).on("click",".btn-link-edit",function () {

    var id = $(this).parent().parent().parent().parent().children(".link-id").text();
    var $modal = $('#edit-1');
    $.ajax({
        type:"get",
        url:"/getLink",
        dataType:"json",
        data:{
            id:id
        },

        success:function (data) {
            editId = data['id'];
            if(data['status']==200){
                var temp = '<div class="am-modal-dialog">\n' +
                    '                                        <div class="am-modal-hd">友链\n' +
                    '                                            <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>\n' +
                    '                                        </div>\n' +
                    '                                        <form class="am-form am-form-horizontal">\n' +
                    '                                            <div class="am-form-group link-item-edit">\n' +
                    '                                                <label class="am-u-md-3">昵称/nickname</label>\n' +
                    '                                                <div class="am-u-sm-9">\n' +
                    '                                                    <input id="nickname" type="text" placeholder="昵称/nickname" value="'+data['name']+'">\n' +
                    '                                                </div>\n' +
                    '                                            </div>\n' +
                    '                                            <div class="am-form-group link-item-edit">\n' +
                    '                                                <label class="am-u-md-3">介绍/introduce</label>\n' +
                    '                                                <div class="am-u-sm-9">\n' +
                    '                                                    <input id="introduce" type="text" placeholder="介绍/introduce" value="'+data['introduce']+'">\n' +
                    '                                                </div>\n' +
                    '                                            </div>\n' +
                    '                                            <div class="am-form-group link-item-edit">\n' +
                    '                                                <label class="am-u-md-3">URL</label>\n' +
                    '                                                <div class="am-u-sm-9">\n' +
                    '                                                    <input id="url" type="text" placeholder="URL" value="'+data['url']+'">\n' +
                    '                                                </div>\n' +
                    '                                            </div>\n' +
                    '\n' +
                    '                                            <div class="am-form-group link-item-edit">\n' +
                    '                                                <label class="am-u-md-3">头像/headImg</label>\n' +
                    '                                                <div class="am-u-sm-9">\n' +
                    '                                                    <input id="headImg" type="text" placeholder="头像/headImg" value="'+data['headImg']+'">\n' +
                    '                                                </div>\n' +
                    '                                            </div>\n' +
                    '\n' +
                    '                                            <div class="am-form-group link-item-edit">\n' +
                    '                                                <div class="am-u-sm-9">\n' +
                    '                                                    <button class="am-btn am-btn-primary link-edit-save-btn" type="button">保存修改</button>\n' +
                    '                                                </div>\n' +
                    '                                            </div>\n' +
                    '                                        </form>\n' +
                    '                                    </div>'


                $(".edit-modal").empty();
                $(".edit-modal").append(temp)
                $modal.modal()
            }

            else {
                layer.alert('获取失败',{
                    icon:2,
                    skin: 'layer-ext-moon'
                })
            }

        }
    })
})

$(document).on("click",".link-btn-add",function () {
    $("#add-1").val("");//清空input的值
    var $modal = $('#add-1');
    $modal.modal();
})

$(document).on("click",".btn-link-delete",function () {
    var id = $(this).parent().parent().parent().parent().children(".link-id").text();

    layer.confirm('你确认删除吗？', {icon: 3, title:'提示'}, function(index){
        $.ajax({
            type:"get",
            url:"/deleteLink",
            dataType:"json",
            data:{
                id:id
            },

            success:function (data) {
                if(data['status']==200){
                    layer.alert('更新成功',{
                        icon:1,
                        skin: 'layer-ext-moon'
                    })
                }

                else {
                    layer.alert('更新失败',{
                        icon:2,
                        skin: 'layer-ext-moon'
                    })

                }

                window.location.reload()
            }
        })
        layer.close(index);
    },function (index) {
        layer.close(index);
    });


})



//图片事件
$(document).on("click",".image-edit-save-btn",function () {

    var tag = $("#tag").val();
    var description = $("#description").val();
    var url = $("#bgUrl").val();
    var type = $("#bgType").val();

    alert(editId)

    $.ajax({
        type:"get",
        url:"/updateImage",
        dataType:"json",
        data:{
            tag:tag,
            description:description,
            url:url,
            type:type,
            id:editId
        },

        success:function (data) {
            if(data['status']==200){
                layer.alert('更新成功',{
                    icon:1,
                    skin: 'layer-ext-moon'
                })
            }

            else {
                layer.alert('更新失败',{
                    icon:2,
                    skin: 'layer-ext-moon'
                })

            }

            // window.location.reload()
        }
    })


})
$(document).on("click",".image-item-add",function () {
    var tag = $("input[name='tag']").val()
    var description = $("input[name='description']").val()
    var url = $("input[name='bgUrl']").val()
    var type = $("input[name='bgType']").val()

    $.ajax({
        type:"get",
        url:"/addImage",
        dataType:"json",
        data:{
            tag:tag,
            description:description,
            url:url,
            type:type
        },

        success:function (data) {
            if(data['status']==200){
                layer.alert('更新成功',{
                    icon:1,
                    skin: 'layer-ext-moon'
                })
            }

            else {
                layer.alert('更新失败',{
                    icon:2,
                    skin: 'layer-ext-moon'
                })

            }

            window.location.reload()
        }
    })
})
$(document).on("click",".btn-image-edit",function () {

    var id = $(this).parent().parent().parent().parent().children(".image-id").text();
    var $modal = $('#image-edit-1');
    $.ajax({
        type:"get",
        url:"/getBgImageById",
        dataType:"json",
        data:{
            id:id
        },

        success:function (data) {
            editId = data['id'];
            if(data['status']==200){
                var temp = '<div class="am-modal-dialog">\n' +
                    '                                        <div class="am-modal-hd">图片\n' +
                    '                                            <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>\n' +
                    '                                        </div>\n' +
                    '                                        <form class="am-form am-form-horizontal">\n' +
                    '                                            <div class="am-form-group image-item-edit">\n' +
                    '                                                <label class="am-u-md-3">Tag</label>\n' +
                    '                                                <div class="am-u-sm-9">\n' +
                    '                                                    <input id="tag" type="text" placeholder="标签" value="'+data['tag']+'">\n' +
                    '                                                </div>\n' +
                    '                                            </div>\n' +
                    '                                            <div class="am-form-group image-item-edit">\n' +
                    '                                                <label class="am-u-md-3">描述</label>\n' +
                    '                                                <div class="am-u-sm-9">\n' +
                    '                                                    <input id="description" type="text" placeholder="描述" value="'+data['description']+'">\n' +
                    '                                                </div>\n' +
                    '                                            </div>\n' +
                    '                                            <div class="am-form-group image-item-edit">\n' +
                    '                                                <label class="am-u-md-3">URL</label>\n' +
                    '                                                <div class="am-u-sm-9">\n' +
                    '                                                    <input id="bgUrl" type="text" placeholder="URL" value="'+data['url']+'">\n' +
                    '                                                </div>\n' +
                    '                                            </div>\n' +
                    '\n' +
                    '                                            <div class="am-form-group image-item-edit">\n' +
                    '                                                <label class="am-u-md-3">Type</label>\n' +
                    '                                                <div class="am-u-sm-9">\n' +
                    '                                                    <input id="bgType" type="text" placeholder="type" value="'+data['type']+'">\n' +
                    '                                                </div>\n' +
                    '                                            </div>\n' +
                    '\n' +
                    '                                            <div class="am-form-group image-item-edit">\n' +
                    '                                                <div class="am-u-sm-9">\n' +
                    '                                                    <button class="am-btn am-btn-primary image-edit-save-btn" type="button">保存修改</button>\n' +
                    '                                                </div>\n' +
                    '                                            </div>\n' +
                    '                                        </form>\n' +
                    '                                    </div>'


                $(".image-edit-modal").empty();
                $(".image-edit-modal").append(temp)
                $modal.modal()
            }

            else {
                layer.alert('获取失败',{
                    icon:2,
                    skin: 'layer-ext-moon'
                })
            }

        }
    })
})

$(document).on("click",".image-btn-add",function () {
    $("#image-add-1").val("");//清空input的值
    var $modal = $('#image-add-1');
    $modal.modal();
})



$(".user-info-choice").on("click",function () {
    $(".userInfo").css("display","block");
    $(".comment-information").css("display","none");
    $(".reply-information").css("display","none");
    $(".admin-leave-message").css("display","none");
    $(".admin-comment-information").css("display","none");
    $(".admin-link").css("display","none");
    if($("#Pagination").hasClass("pagination-css")==false){
        $("#Pagination").addClass("pagination-css");
    }
})

function ajaxCommentFirst(index) {
    $.ajax({
        type:"get",
        url:"/getReferMineComments",
        dataType:"json",
        data:{
            rows:10,
            pageNum:index+1
        },
        success:function (data) {

            $(".comment-information-list").empty();
            if(data['status']==200){
                if($("#Pagination").hasClass("pagination-css")==true){
                    $("#Pagination").removeClass("pagination-css");
                }
                $("#Pagination").pagination(data['totalSize'], {
                    num_edge_entries: 1, //边缘页数
                    num_display_entries: 3, //主体页数
                    load_first_page:false,
                    callback: function (index) {
                        ajaxCommentFirst(index);
                    },
                    items_per_page:10,//每页显示1项
                    current_page:index,
                    prev_text : '<<',
                    next_text : '>>',

                });
                $.each(data['result'],function (index, obj) {
                    var item = '<li>\n' +
                        '                                <div class="comment-time">'+obj['time']+'</div>\n' +
                        '                                <div class="comment-content-item">\n' +
                        '                                    <span class="notice-icon">\n' +
                        '                                        <i class="am-icon-bell-o"></i>\n' +
                        '                                    </span>\n' +
                        '                                    <span>'+obj['name']+'在文章<a href="/article/'+obj['articleId']+'">'+obj['title']+'</a>中@了我：'+obj['content']+'</span>\n' +
                        '                                </div>\n' +
                        '                            </li>'


                    $(".comment-information-list").append(item);
                })
            }

            else {
                if($("#Pagination").hasClass("pagination-css")==false){
                    $("#Pagination").addClass("pagination-css");
                }
                $(".comment-information-list").append(no_data);
            }
        }
    })
}
$(".comment-information-choice").on("click",function () {
    $(".userInfo").css("display","none");
    $(".reply-information").css("display","none");
    $(".admin-leave-message").css("display","none");
    $(".admin-comment-information").css("display","none");
    $(".admin-link").css("display","none");
    $(".admin-image").css("display","none");
    $(".comment-information").css("display","block");
    ajaxCommentFirst(0);
})


function replyFirst(index) {
    $.ajax({
        type:"get",
        url:"/getReplyComments",
        dataType:"json",
        data:{
            rows:10,
            pageNum:index+1
        },
        success:function (data) {
            $(".comment-information-list").empty();
            if(data['status']==200){
                if($("#Pagination").hasClass("pagination-css")==true){
                    $("#Pagination").removeClass("pagination-css");
                }
                $("#Pagination").pagination(data['totalSize'], {
                    num_edge_entries: 1, //边缘页数
                    num_display_entries: 3, //主体页数
                    load_first_page:false,
                    callback: function (index) {
                        replyFirst(index);
                    },
                    items_per_page:10,//每页显示1项
                    current_page:index,
                    prev_text : '<<',
                    next_text : '>>',

                });
                $.each(data['result'],function (index, obj) {
                    var item = '<li>\n' +
                        '                                <div class="comment-time">'+obj['time']+'</div>\n' +
                        '                                <div class="comment-content-item">\n' +
                        '                                    <span class="notice-icon">\n' +
                        '                                        <i class="am-icon-bell-o"></i>\n' +
                        '                                    </span>\n' +
                        '                                    <span>'+obj['name']+'在文章<a href="/article/'+obj['articleId']+'">'+obj['title']+'</a>中回复了我：'+obj['content']+'</span>\n' +
                        '                                </div>\n' +
                        '                            </li>'


                    $(".comment-information-list").append(item);
                })
            }

            else {
                if($("#Pagination").hasClass("pagination-css")==false){
                    $("#Pagination").addClass("pagination-css");
                }
                $(".comment-information-list").append(no_data);
            }
        }
    })
}
$(".reply-choice").on("click",function () {
    $(".comment-information").css("display","none");
    $(".userInfo").css("display","none");
    $(".admin-leave-message").css("display","none");
    $(".admin-comment-information").css("display","none");
    $(".admin-link").css("display","none");
    $(".admin-image").css("display","none");
    $(".reply-information").css("display","block");
    replyFirst(0);
})

$(".admin-all-choice").on("click",function () {
    $.ajax({
        type:"get",
        url:"/getAllCounts",
        dataType:"json",

        success:function (data) {

            var comment_badge = "";
            var leave_message = "";

            if(data['commentCount']!=0){
                comment_badge =  '<i class="left-badge">'+data['commentCount']+'</i>\n' ;
            }

            if(data['messageCount']!=0){
                leave_message =  '<i class="left-badge">'+data['messageCount']+'</i>\n';
            }




            var commentItem = '<a>\n' +
                '                                    <i class="am-icon-angle-right"></i>\n' +
                '                                    <span>评论</span>'+comment_badge+'\n' +
                '                                </a>'


            var leaveMessageItem = '<a>\n' +
                '                                    <i class="am-icon-angle-right"></i>\n' +
                '                                    <span>留言</span>'+leave_message+'\n' +
                '                                </a>'

            $(".admin-comment-information-choice").empty();
            $(".admin-leave-message-choice").empty();
            $(".admin-comment-information-choice").append(commentItem);
            $(".admin-leave-message-choice").append(leaveMessageItem);
        }
    })
})

function ajaxAdminCommentFirst(index) {
    $.ajax({
        type:"get",
        url:"/getNotReadingComment",
        dataType:"json",
        data:{
            pageNum:index+1,
            rows:10
        },

        success:function (data) {


            $(".comment-information-list").empty();

            if(data['status']==200){
                if($("#Pagination").hasClass("pagination-css")==true){
                    $("#Pagination").removeClass("pagination-css");
                }
                $("#Pagination").pagination(data['totalSize'], {
                    num_edge_entries: 1, //边缘页数
                    num_display_entries: 3, //主体页数
                    load_first_page:false,
                    callback: function (index) {
                        ajaxAdminCommentFirst(index);
                    },
                    items_per_page:10,//每页显示1项
                    current_page:index,
                    prev_text : '<<',
                    next_text : '>>',

                });
                $.each(data['result'],function (index, obj) {

                    var unRead = '<span class="unread-icon">未处理</span>'
                    var read = '<span class="read-icon">已处理</span>'
                    var temp = unRead;
                    if(obj['read']==1){
                        temp = read;
                    }

                    var item = '<li>\n' +
                        '                                <div class="comment-time">'+obj['time']+'</div>\n' +
                        '                                <div class="comment-content-item">\n' +
                        '                                    <span class="notice-icon">\n' +
                        '                                        <i class="am-icon-bell-o"></i>\n' +
                        '                                    </span>\n' +
                        '                                    <span>'+obj['name']+'在文章<a href="/article/'+obj['articleId']+'">'+obj['title']+'</a>中评论：'+obj['content']+'</span>'+temp+'\n' +
                        '<span class="comment-id" style="display: none">'+obj['commentId']+'</span>'+
                        '                                </div>\n' +
                        '                            </li>'

                    $(".comment-information-list").append(item);
                })
            }

            else {
                if($("#Pagination").hasClass("pagination-css")==false){
                    $("#Pagination").addClass("pagination-css");
                }
                $(".comment-information-list").append(no_data);
            }



        }
    })
}
$(".admin-comment-information-choice").on("click",function () {
    $(".reply-information").css("display","none");
    $(".comment-information").css("display","none");
    $(".userInfo").css("display","none");
    $(".admin-leave-message").css("display","none");
    $(".admin-link").css("display","none");
    $(".admin-comment-information").css("display","block");
    ajaxAdminCommentFirst(0);
})


function ajaxAdminLeaveMessageFirst(index) {
    $.ajax({
        type:"get",
        url:"/getNotReadingLeaveMessage",
        dataType:"json",
        data:{
            pageNum:index+1,
            rows:10
        },

        success:function (data) {

            $(".comment-information-list").empty();
            if(data['status']==200){
                if($("#Pagination").hasClass("pagination-css")==true){
                    $("#Pagination").removeClass("pagination-css");
                }
                $("#Pagination").pagination(data['totalSize'], {
                    num_edge_entries: 1, //边缘页数
                    num_display_entries: 3, //主体页数
                    load_first_page:false,
                    callback: function (index) {
                        ajaxAdminLeaveMessageFirst(index);
                    },
                    items_per_page:10,//每页显示1项
                    current_page:index,
                    prev_text : '<<',
                    next_text : '>>',

                });
                $.each(data['result'],function (index, obj) {
                    var unRead = '<span class="unread-icon">未处理</span>'
                    var read = '<span class="read-icon">已处理</span>'
                    var temp = unRead;
                    if(obj['read']==1){
                        temp = read;
                    }

                    var item = '<li>\n' +
                        '                                <div class="comment-time">'+obj['time']+'</div>\n' +
                        '                                <div class="comment-content-item">\n' +
                        '                                    <span class="notice-icon">\n' +
                        '                                        <i class="am-icon-bell-o"></i>\n' +
                        '                                    </span>\n' +
                        '                                    <span>'+obj['name']+'在留言板留下了这样的中话语：'+obj['content']+'</span>'+temp+'\n' +
                        '<span class="leave-message-id" style="display: none">'+obj['leaveMessageId']+'</span>'+
                        '                                </div>\n' +
                        '                            </li>'

                    $(".comment-information-list").append(item);
                })
            }

            else {
                if($("#Pagination").hasClass("pagination-css")==false){
                    $("#Pagination").addClass("pagination-css");
                }
                $(".comment-information-list").append(no_data);
            }



        }
    })
}
$(".admin-leave-message-choice").on("click",function () {
    $(".reply-information").css("display","none");
    $(".comment-information").css("display","none");
    $(".userInfo").css("display","none");
    $(".admin-comment-information").css("display","none");
    $(".admin-link").css("display","none");
    $(".admin-image").css("display","none");
    $(".admin-leave-message").css("display","block");
    ajaxAdminLeaveMessageFirst(0);
})

//获取友链名单
function ajaxAdminLinkFirst(index) {
    $.ajax({
        type:"get",
        url:"/getLinks",
        dataType:"json",
        data:{
            pageNum:index+1,
            rows:10
        },
        success:function (data) {

            var thead = '<thead>\n' +
                '                                <tr>\n' +
                '                                    <th class="table-check">\n' +
                '                                        <input class="table-check-box" type="checkbox">\n' +
                '                                    </th>\n' +
                '                                    <th class="table-id">ID</th>\n' +
                '                                    <th class="table-id">昵称</th>\n' +
                '                                    <th class="table-id">介绍</th>\n' +
                '                                    <th class="table-id">URL</th>\n' +
                '                                    <th class="table-id">操作</th>\n' +
                '                                </tr>\n' +
                '                                </thead>'

            var tbody = '<tbody>\n' +
                '                                </tbody>';

            $(".admin-link .am-form .am-table").empty();

            if(data['status']==200){

                $(".admin-link .am-form .am-table").append(thead);
                $(".admin-link .am-form .am-table").append(tbody);

                if($("#Pagination").hasClass("pagination-css")==true){
                    $("#Pagination").removeClass("pagination-css");
                }
                $("#Pagination").pagination(data['totalSize'], {
                    num_edge_entries: 1, //边缘页数
                    num_display_entries: 3, //主体页数
                    load_first_page:false,
                    callback: function (index) {
                        ajaxAdminLinkFirst(index)
                    },
                    items_per_page:10,//每页显示1项
                    current_page:index,
                    prev_text : '<<',
                    next_text : '>>',

                });
                $.each(data['result'],function (index, obj) {
                    var item = ' <tr>\n' +
                        '                                        <td>\n' +
                        '                                            <input type="checkbox">\n' +
                        '                                        </td>\n' +
                        '                                        <td>'+obj['id']+'</td>\n' +
                        '                                        <td class="link-id" style="display: none">'+obj['id']+'</td>\n' +
                        '                                        <td>'+obj['name']+'</td>\n' +
                        '                                        <td>'+obj['introduce']+'</td>\n' +
                        '                                        <td>'+obj['url']+'</td>\n' +
                        '    \n' +
                        '                                        <td>\n' +
                        '                                            <div class="am-btn-toolbar">\n' +
                        '                                                <div class="am-btn-group am-btn-group-xs">\n' +
                        '                                                    <button class="am-btn am-btn-default am-text-secondary am-btn-xs btn-link-edit-delete btn-link-edit" type="button">\n' +
                        '                                                        <span class="am-icon-pencil-square-o"></span>\n' +
                        '                                                        编辑\n' +
                        '                                                    </button>\n' +
                        '    \n' +
                        '                                                    <button class="am-btn am-btn-default am-text-danger am-hide-sm-only am-btn-xs btn-link-edit-delete btn-link-delete" type="button">\n' +
                        '                                                        <span class="am-icon-trash-o"></span>\n' +
                        '                                                        删除\n' +
                        '                                                    </button>\n' +
                        '    \n' +
                        '    \n' +
                        '    \n' +
                        '                                                </div>\n' +
                        '                                            </div>\n' +
                        '                                        </td>\n' +
                        '                                    </tr>'

                    $(".admin-link .am-form tbody").append(item)
                })
            }

            else {
                if($("#Pagination").hasClass("pagination-css")==false){
                    $("#Pagination").addClass("pagination-css");
                }
                $(".admin-link .am-form .am-table").append(no_data);
            }



        }
    })
}
$(".admin-link-choice").on("click",function () {
    $(".reply-information").css("display","none");
    $(".comment-information").css("display","none");
    $(".userInfo").css("display","none");
    $(".admin-comment-information").css("display","none");
    $(".admin-leave-message").css("display","none");
    $(".admin-image").css("display","none");
    $(".admin-link").css("display","block");
    ajaxAdminLinkFirst(0);
})

//获取图片
function ajaxImageFirst(index) {
    $.ajax({
        type:"get",
        url:"/getPageBgImages",
        dataType:"json",
        data:{
            pageNum:index+1,
            rows:10
        },
        success:function (data) {

            var thead = '<thead>\n' +
                '                                <tr>\n' +
                '                                    <th class="table-check">\n' +
                '                                        <input class="table-check-box" type="checkbox">\n' +
                '                                    </th>\n' +
                '                                    <th class="table-id">ID</th>\n' +
                '                                    <th class="table-id">Tag</th>\n' +
                '                                    <th class="table-id">描述</th>\n' +
                '                                    <th class="table-id">URL</th>\n' +
                '                                    <th class="table-id">操作</th>\n' +
                '                                </tr>\n' +
                '                                </thead>'

            var tbody = '<tbody>\n' +
                '                                </tbody>';

            $(".admin-image .am-form .am-table").empty();

            if(data['status']==200){

                $(".admin-image .am-form .am-table").append(thead);
                $(".admin-image .am-form .am-table").append(tbody);

                if($("#Pagination").hasClass("pagination-css")==true){
                    $("#Pagination").removeClass("pagination-css");
                }
                $("#Pagination").pagination(data['totalSize'], {
                    num_edge_entries: 1, //边缘页数
                    num_display_entries: 3, //主体页数
                    load_first_page:false,
                    callback: function (index) {
                        ajaxImageFirst(index)
                    },
                    items_per_page:10,//每页显示1项
                    current_page:index,
                    prev_text : '<<',
                    next_text : '>>',

                });
                $.each(data['result'],function (index, obj) {
                    var item = ' <tr>\n' +
                        '                                        <td>\n' +
                        '                                            <input type="checkbox">\n' +
                        '                                        </td>\n' +
                        '                                        <td>'+obj['id']+'</td>\n' +
                        '                                        <td class="image-id" style="display: none">'+obj['id']+'</td>\n' +
                        '                                        <td>'+obj['tag']+'</td>\n' +
                        '                                        <td>'+obj['description']+'</td>\n' +
                        '                                        <td>'+obj['url']+'</td>\n' +
                        '    \n' +
                        '                                        <td>\n' +
                        '                                            <div class="am-btn-toolbar">\n' +
                        '                                                <div class="am-btn-group am-btn-group-xs">\n' +
                        '                                                    <button class="am-btn am-btn-default am-text-secondary am-btn-xs btn-image-edit-delete btn-image-edit" type="button">\n' +
                        '                                                        <span class="am-icon-pencil-square-o"></span>\n' +
                        '                                                        编辑\n' +
                        '                                                    </button>\n' +
                        '    \n' +
                        '                                                    <button class="am-btn am-btn-default am-text-danger am-hide-sm-only am-btn-xs btn-image-edit-delete btn-image-delete" type="button">\n' +
                        '                                                        <span class="am-icon-trash-o"></span>\n' +
                        '                                                        删除\n' +
                        '                                                    </button>\n' +
                        '    \n' +
                        '    \n' +
                        '    \n' +
                        '                                                </div>\n' +
                        '                                            </div>\n' +
                        '                                        </td>\n' +
                        '                                    </tr>'

                    $(".admin-image .am-form tbody").append(item)
                })
            }

            else {
                if($("#Pagination").hasClass("pagination-css")==false){
                    $("#Pagination").addClass("pagination-css");
                }
                $(".admin-image .am-form .am-table").append(no_data);
            }



        }
    })
}
$(".admin-image-manage-choice").on("click",function () {
    $(".reply-information").css("display","none");
    $(".comment-information").css("display","none");
    $(".userInfo").css("display","none");
    $(".admin-comment-information").css("display","none");
    $(".admin-leave-message").css("display","none");
    $(".admin-link").css("display","none");
    $(".admin-image").css("display","block");
    ajaxImageFirst(0);
})




$(document).on("click",".user-info-btn",function () {
    var headImg = "";
    if(tag==1){
        headImg = imgSrcToBase64($(".user-head-img img")[0]);
    }

    else {
        headImg = $(".user-head-img img").attr('src');
    }

    var name = $("#user-name").val();
    var info = $("#user-info").val();
    var gender = $("input[name='gender']:checked").val();

    $.ajax({
        type:"get",
        url:"/isUserNameExist",
        dataType:"json",
        data:{
            name:name,
        },

        success:function (data) {
            if(data['status']==200){
                $.ajax({
                    type:"post",
                    url:"/updateUserInfo",
                    dataType:"json",
                    data:{
                        img:headImg,
                        name:name,
                        info:info,
                        gender:gender
                    },

                    success:function (data) {
                        if(data['status']==200){
                            layer.alert('更新成功',{
                                icon:1,
                                skin: 'layer-ext-moon'
                            })
                        }

                        else {
                            layer.alert('更新失败',{
                                icon:2,
                                skin: 'layer-ext-moon'
                            })

                            window.location.reload()
                        }

                    }
                })
            }

            else {
                layer.alert('用户名已经存在',{
                    icon:2,
                    skin: 'layer-ext-moon'
                })

            }

        }
    })



})

$(document).on("click",".unread-icon",function () {
    $(this).removeClass("unread-icon");
    $(this).addClass("read-icon")
    $(this).text("已处理")

    var tag = $(this).hasClass("comment-id");
    if(tag==true){
        var commentId = $(this).next().text();
        $.ajax({
            type:"get",
            url:"/updateCommentRead",
            dataType:"json",
            data:{
                commentId:commentId
            },

            success:function () {

            }

        })
    }

    else {
        var messageId = $(this).next().text();
        $.ajax({
            type:"get",
            url:"/updateMessageRead",
            dataType:"json",
            data:{
                messageId:messageId
            },

            success:function () {

            }

        })
    }

})

$(".clear-all-comment").on("click",function () {
    $(".comment-information-list .unread-icon").addClass("read-icon")
    $(".comment-information-list .unread-icon").text("已处理")
    $(".comment-information-list .unread-icon").removeClass("unread-icon")

    $.ajax({
        type:"get",
        url:"/clearAllComment",
        dataType:"json",
        success:function () {

        }

    })

})

$(".clear-all-message").on("click",function () {
    $(".comment-information-list .unread-icon").addClass("read-icon")
    $(".comment-information-list .unread-icon").text("已处理")
    $(".comment-information-list .unread-icon").removeClass("unread-icon")

    $.ajax({
        type:"get",
        url:"/clearAllMessage",
        dataType:"json",
        success:function () {

        }

    })
})