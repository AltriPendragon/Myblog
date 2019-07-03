var articleId;
var user_id = 0;
var pid = 0;
var floor = 0;
var comment_num = 0


var headImg = "";
var id = "";


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

function comment_plus() {
    var item = '<h3>Comments |\n' +
        '                                <span>'+comment_num+'条评论</span>\n' +
        '                            </h3>'
    $(".comment-main-top").empty();
    $(".comment-main-top").append(item);
}

function getArticle() {
    var testEditormdView;
    var url = location.href;
    var id = url.split("/");

    articleId = id[4];
    $.get("/getArticleById/?id="+id[4], function(markdown) {


        $(document).attr("title",markdown['title']);
        var tag = markdown['tags'].split(",");
        var tagHtml="";
        $.each(tag,function (index,value) {
            tagHtml+='<i class="am-icon-tag"></i>\n' +
                '<a class="am-icon-tag-a" href="/tag/?tag='+value+'">'+value+'</a>'
        })



        var articleTitle = '<h1>'+markdown['title']+'</h1>';

        var articleTop = '                   <span class="am-icon-user article-meta-tag">&nbsp;'+markdown['author']+'</span>\n' +
            '                                <span class="am-icon-clock-o article-meta-tag">&nbsp;'+markdown['createTime']+'</span>\n' +
            '                                <span class="am-icon-folder article-meta-tag">&nbsp;'+markdown['category']+'</span>\n' +
            '                                <span class="am-icon-eye article-meta-tag">&nbsp;'+markdown['read']+'</span>\n'


        var editUrl = '<a href="/edit/'+articleId+'">编辑</a>\n'



        var articleFooter = '<div class="end-logo">\n' +
            '                                <i class="am-icon-btn am-success am-icon-lg">完</i>\n' +
            '                            </div>\n' +
            '                            <div>\n' +
            '                                <ul class="post-copyright">\n' +
            '                                    <li>\n' +
            '                                        <strong>本文作者：</strong>\n' +
            '                                        <span>Altri</span>\n' +
            '                                    </li>\n' +
            '                                    <li>\n' +
            '                                        <strong>原文链接：</strong>\n' +
            '                                        <span>\n' +
            '                                            <a href="">https://www.neroarc.cn/article/'+markdown['articleId']+'</a>\n' +
            '                                        </span>\n' +
            '                                    </li>\n' +
            '                                    <li>\n' +
            '                                        <strong>版权说明：</strong>\n' +
            '                                        本博客所有文章除特别说明外，均采用\n' +
            '                                        <span>\n' +
            '                                            <a href=""> CC BY 3.0 CN协议</a>\n' +
            '                                        </span>\n' +
            '                                        进行许可。转载请署名作者且注明文章出处\n' +
            '                                    </li>\n' +
            '                                </ul>\n' +
            '                            </div>\n' +
            '\n' +
            '                            <div class="article-tags">\n' +
            '                                <div>'+tagHtml+'</div>\n' +
            '                            </div>\n' +
            '                            <hr>'

        $(".article-top").prepend(articleTitle);
        $(".article-meta").prepend(articleTop);
        $(".am-icon-edit").append(editUrl);
        $(".article-top,.comment").removeClass("display-none")

        testEditormdView = editormd.markdownToHTML("test-editormd-view", {
            markdown        : markdown['content'] ,//+ "\r\n" + $("#append-test").text(),
            //htmlDecode      : true,       // 开启 HTML 标签解析，为了安全性，默认不开启
            htmlDecode      : "style,script,iframe",  // you can filter tags decode
            //toc             : false,
            tocm            : true,    // Using [TOCM]
            //tocContainer    : "#custom-toc-container", // 自定义 ToC 容器层
            //gfm             : false,
            //tocDropdown     : true,
            // markdownSourceCode : true, // 是否保留 Markdown 源码，即是否删除保存源码的 Textarea 标签
            emoji           : true,
            taskList        : true,
            tex             : true,  // 默认不解析
            flowChart       : true,  // 默认不解析
            sequenceDiagram : true,  // 默认不解析
        });

        $.ajax({
            type:"get",
            url:"/getLastAndNextArticles",
            dataType:"json",
            data:{
                articleId:id[4]
            },

            success:function (data) {
                var last = data[0];
                var next = data[1];
                var lastTitle=last['title'];
                var nextTitle=next['title'];
                var lastUrl="/article/"+last['articleId'];
                var nextUrl="/article/"+next['articleId'];


                if(last['flag']==0){
                    lastTitle = "无";
                    lastUrl = "";
                }

                if(next['flag']==0){
                    nextTitle = "无";
                    nextUrl = "";
                }

                var otherArticles='<span class="last-article">\n' +
                    '                                    <i class="am-icon-angle-left"></i>\n' +
                    '                                    <a href="'+lastUrl+'">'+lastTitle+'</a>\n' +
                    '                                </span>\n' +
                    '\n' +
                    '                                <span class="next-article">\n' +
                    '                                    <a href="'+nextUrl+'">'+nextTitle+'</a>\n' +
                    '                                    <i class="am-icon-angle-right"></i>\n' +
                    '                                </span>'


                $(".other-articles").html(otherArticles)
            }
        })

        $(".article-footer").prepend(articleFooter)

        //console.log("返回一个 jQuery 实例 =>", testEditormdView);

        // 获取Markdown源码

        //alert(testEditormdView.getMarkdown());



    });


    $.ajax({
        type:"get",
        url:"/getCommentsOfArticle",
        dataType:"json",
        data:{
            articleId:articleId
        },

        success:function (data) {


            if(data['result']==undefined){
                var item = '<div><p class="no-message">还没有任何伟论发表哦<span class="blog-icon">&#xe606;</span></p></div>'
                $(".comment-main-top").append(item);
            }

            else {
                comment_num = data['commentNum']
                comment_plus();
                floor = data['result'].length;
                var temp_floor = floor;
                $.each(data['result'],function (index, obj) {
                    var temp="";
                    var isLike = "am-icon-thumbs-o-up";

                    if(obj['isLike']==1){
                        isLike = "am-icon-thumbs-up";
                    }

                    $.each(obj['replies'],function (index, obj) {
                        var text = "";
                        if(obj['responsorId']!=0){
                            var text = '回复<a>@'+obj['responsorName']+'</a>：'+obj['content']+'';
                        }

                        else {
                            var text = obj['content'];
                        }

                        var reply_item = '<div class="reply-item">\n' +
                            '                                                <div class="reply-user-img">\n' +
                            '                                                    <a href="">\n' +
                            '                                                        <img src="'+obj['avatarImgUrl']+'">\n' +
                            '                                                    </a>\n' +
                            '                                                </div>\n' +
                            '                                                <div class="reply-item-content">\n' +
                            '                                                    <div class="user">\n' +
                            '                                                        <span class="user-name">'+obj['userName']+'</span>\n' +
                            '                                                        <span class="user-id" style="display: none">'+obj['remarkerId']+'</span>\n' +
                            '                                                        <span class="text">'+text+'</span>\n' +
                            '                                                    </div>\n' +
                            '\n' +
                            '\n' +
                            '                                                    <div class="info">\n' +
                            '                                                        <span class="time">'+obj['date']+'</span>\n' +
                            '                                                        <span class="reply inner-reply"><a>回复</a></span>\n' +
                            '                                                    </div>\n' +
                            '                                                </div>\n' +
                            '                                            </div>'

                        temp+=reply_item;
                    })


                    var list_item = '<div class="list-item">\n' +
                        '                                    <div class="user-img">\n' +
                        '                                        <a href="">\n' +
                        '                                            <img src="'+obj['avatarImgUrl']+'">\n' +
                        '                                        </a>\n' +
                        '                                    </div>\n' +
                        '                                    <div class="list-item-content">\n' +
                        '                                        <div class="user">\n' +
                        '                                            <span class="user-name">'+obj['userName']+'</span>\n' +
                        '                                            <span class="pid" style="display: none">'+obj['commentId']+'</span>\n' +
                        '                                        </div>\n' +
                        '                                        <p class="text">'+obj['content']+'</p>\n' +
                        '                                        <div class="info">\n' +
                        '                                            <span class="floor">#'+(temp_floor--)+'</span>\n' +
                        '                                            <span class="time">'+obj['date']+'</span>\n' +
                        '                                            <span class="like">\n' +
                        '                                                <a>\n' +
                        '                                                    <i class="'+isLike+'">&nbsp;&nbsp;'+obj['likes']+'</i>\n' +
                        '                                                </a>\n' +
                        '                                            </span>\n' +
                        '\n' +
                        '                                            <span class="reply top-reply"><a>回复</a></span>\n' +
                        '\n' +
                        '                                        </div>\n' +
                        '\n' +
                        '                                        <div class="reply-box">'+temp+'</div>\n' +
                        '                                    </div>\n' +
                        '\n' +
                        '                                    <div class="comment-reply am-animation-slide-top" style="display: none">\n' +
                        '                                        <div class="user-img">\n' +
                        '                                            <img src="">\n' +
                        '                                        </div>\n' +
                        '\n' +
                        '                                        <div class="comment-reply-textarea">\n' +
                        '                                            <textarea placeholder="" id="comment-reply-textarea"></textarea>\n' +
                        '                                            <button class="comment-reply-textarea-btn am-text-break">发表评论</button>\n' +
                        '                                        </div>\n' +
                        '                                    </div>\n' +
                        '                                </div>'


                    $(".comment-main-list").append(list_item);
                })
            }


        }

    })
}

getArticle();

// $(function() {
//     var testEditormdView;
//     var url = location.href;
//     var id = url.split("/");
//
//     articleId = id[4];
//     $.get("/getArticleById/?id="+id[4], function(markdown) {
//
//
//
//         var tag = markdown['tags'].split(",");
//         var tagHtml="";
//         $.each(tag,function (index,value) {
//             tagHtml+='<i class="am-icon-tag"></i>\n' +
//                 '<a class="am-icon-tag-a" href="/tag/?tag='+value+'">'+value+'</a>'
//         })
//
//         $(document).attr("title",markdown['title']);
//
//         var articleTitle = '<h1>'+markdown['title']+'</h1>';
//
//         var articleTop = '                   <span class="am-icon-user article-meta-tag">&nbsp;'+markdown['author']+'</span>\n' +
//             '                                <span class="am-icon-clock-o article-meta-tag">&nbsp;'+markdown['createTime']+'</span>\n' +
//             '                                <span class="am-icon-folder article-meta-tag">&nbsp;'+markdown['category']+'</span>\n' +
//             '                                <span class="am-icon-eye article-meta-tag">&nbsp;'+markdown['read']+'</span>\n'
//
//
//         var editUrl = '<a href="/edit/'+articleId+'">编辑</a>\n'
//
//
//
//         var articleFooter = '<div class="end-logo">\n' +
//             '                                <i class="am-icon-btn am-success am-icon-lg">完</i>\n' +
//             '                            </div>\n' +
//             '                            <div>\n' +
//             '                                <ul class="post-copyright">\n' +
//             '                                    <li>\n' +
//             '                                        <strong>本文作者：</strong>\n' +
//             '                                        <span>Altri</span>\n' +
//             '                                    </li>\n' +
//             '                                    <li>\n' +
//             '                                        <strong>原文链接：</strong>\n' +
//             '                                        <span>\n' +
//             '                                            <a href="">https://www.neroarc.cn/article/'+markdown['articleId']+'</a>\n' +
//             '                                        </span>\n' +
//             '                                    </li>\n' +
//             '                                    <li>\n' +
//             '                                        <strong>版权说明：</strong>\n' +
//             '                                        本博客所有文章除特别说明外，均采用\n' +
//             '                                        <span>\n' +
//             '                                            <a href=""> CC BY 3.0 CN协议</a>\n' +
//             '                                        </span>\n' +
//             '                                        进行许可。转载请署名作者且注明文章出处\n' +
//             '                                    </li>\n' +
//             '                                </ul>\n' +
//             '                            </div>\n' +
//             '\n' +
//             '                            <div class="article-tags">\n' +
//             '                                <div>'+tagHtml+'</div>\n' +
//             '                            </div>\n' +
//             '                            <hr>'
//
//         $(".article-top").prepend(articleTitle);
//         $(".article-meta").prepend(articleTop);
//         $(".am-icon-edit").append(editUrl);
//         $(".article-top,.comment").removeClass("display-none")
//
//         testEditormdView = editormd.markdownToHTML("test-editormd-view", {
//             markdown        : markdown['content'] ,//+ "\r\n" + $("#append-test").text(),
//             //htmlDecode      : true,       // 开启 HTML 标签解析，为了安全性，默认不开启
//             htmlDecode      : "style,script,iframe",  // you can filter tags decode
//             //toc             : false,
//             tocm            : true,    // Using [TOCM]
//             //tocContainer    : "#custom-toc-container", // 自定义 ToC 容器层
//             //gfm             : false,
//             //tocDropdown     : true,
//             // markdownSourceCode : true, // 是否保留 Markdown 源码，即是否删除保存源码的 Textarea 标签
//             emoji           : true,
//             taskList        : true,
//             tex             : true,  // 默认不解析
//             flowChart       : true,  // 默认不解析
//             sequenceDiagram : true,  // 默认不解析
//         });
//
//         $.ajax({
//             type:"get",
//             url:"/getLastAndNextArticles",
//             dataType:"json",
//             data:{
//                 articleId:id[4]
//             },
//
//             success:function (data) {
//                 var last = data[0];
//                 var next = data[1];
//                 var lastTitle=last['title'];
//                 var nextTitle=next['title'];
//                 var lastUrl="/article/"+last['articleId'];
//                 var nextUrl="/article/"+next['articleId'];
//
//
//                 if(last['flag']==0){
//                     lastTitle = "无";
//                     lastUrl = "";
//                 }
//
//                 if(next['flag']==0){
//                     nextTitle = "无";
//                     nextUrl = "";
//                 }
//
//                 var otherArticles='<span class="last-article">\n' +
//                     '                                    <i class="am-icon-angle-left"></i>\n' +
//                     '                                    <a href="'+lastUrl+'">'+lastTitle+'</a>\n' +
//                     '                                </span>\n' +
//                     '\n' +
//                     '                                <span class="next-article">\n' +
//                     '                                    <a href="'+nextUrl+'">'+nextTitle+'</a>\n' +
//                     '                                    <i class="am-icon-angle-right"></i>\n' +
//                     '                                </span>'
//
//
//                 $(".other-articles").html(otherArticles)
//             }
//         })
//
//         $(".article-footer").prepend(articleFooter)
//
//         //console.log("返回一个 jQuery 实例 =>", testEditormdView);
//
//         // 获取Markdown源码
//
//         //alert(testEditormdView.getMarkdown());
//
//
//
//     });
//
//
//     $.ajax({
//         type:"get",
//         url:"/getCommentsOfArticle",
//         dataType:"json",
//         data:{
//             articleId:articleId
//         },
//
//         success:function (data) {
//
//
//             if(data['result']==undefined){
//                 var item = '<div><p class="no-message">还没有任何伟论发表哦<span class="blog-icon">&#xe606;</span></p></div>'
//                 $(".comment-main-top").append(item);
//             }
//
//             else {
//                 comment_num = data['commentNum']
//                 comment_plus();
//                 floor = data['result'].length;
//                 var temp_floor = floor;
//                 $.each(data['result'],function (index, obj) {
//                     var temp="";
//                     var isLike = "am-icon-thumbs-o-up";
//
//                     if(obj['isLike']==1){
//                         isLike = "am-icon-thumbs-up";
//                     }
//
//                     $.each(obj['replies'],function (index, obj) {
//                         var text = "";
//                         if(obj['responsorId']!=0){
//                             var text = '回复<a>@'+obj['responsorName']+'</a>：'+obj['content']+'';
//                         }
//
//                         else {
//                             var text = obj['content'];
//                         }
//
//                         var reply_item = '<div class="reply-item">\n' +
//                             '                                                <div class="reply-user-img">\n' +
//                             '                                                    <a href="">\n' +
//                             '                                                        <img src="'+obj['avatarImgUrl']+'">\n' +
//                             '                                                    </a>\n' +
//                             '                                                </div>\n' +
//                             '                                                <div class="reply-item-content">\n' +
//                             '                                                    <div class="user">\n' +
//                             '                                                        <span class="user-name">'+obj['userName']+'</span>\n' +
//                             '                                                        <span class="user-id" style="display: none">'+obj['remarkerId']+'</span>\n' +
//                             '                                                        <span class="text">'+text+'</span>\n' +
//                             '                                                    </div>\n' +
//                             '\n' +
//                             '\n' +
//                             '                                                    <div class="info">\n' +
//                             '                                                        <span class="time">'+obj['date']+'</span>\n' +
//                             '                                                        <span class="reply inner-reply"><a>回复</a></span>\n' +
//                             '                                                    </div>\n' +
//                             '                                                </div>\n' +
//                             '                                            </div>'
//
//                         temp+=reply_item;
//                     })
//
//
//                     var list_item = '<div class="list-item">\n' +
//                         '                                    <div class="user-img">\n' +
//                         '                                        <a href="">\n' +
//                         '                                            <img src="'+obj['avatarImgUrl']+'">\n' +
//                         '                                        </a>\n' +
//                         '                                    </div>\n' +
//                         '                                    <div class="list-item-content">\n' +
//                         '                                        <div class="user">\n' +
//                         '                                            <span class="user-name">'+obj['userName']+'</span>\n' +
//                         '                                            <span class="pid" style="display: none">'+obj['commentId']+'</span>\n' +
//                         '                                        </div>\n' +
//                         '                                        <p class="text">'+obj['content']+'</p>\n' +
//                         '                                        <div class="info">\n' +
//                         '                                            <span class="floor">#'+(temp_floor--)+'</span>\n' +
//                         '                                            <span class="time">'+obj['date']+'</span>\n' +
//                         '                                            <span class="like">\n' +
//                         '                                                <a>\n' +
//                         '                                                    <i class="'+isLike+'">&nbsp;&nbsp;'+obj['likes']+'</i>\n' +
//                         '                                                </a>\n' +
//                         '                                            </span>\n' +
//                         '\n' +
//                         '                                            <span class="reply top-reply"><a>回复</a></span>\n' +
//                         '\n' +
//                         '                                        </div>\n' +
//                         '\n' +
//                         '                                        <div class="reply-box">'+temp+'</div>\n' +
//                         '                                    </div>\n' +
//                         '\n' +
//                         '                                    <div class="comment-reply am-animation-slide-top" style="display: none">\n' +
//                         '                                        <div class="user-img">\n' +
//                         '                                            <img src="">\n' +
//                         '                                        </div>\n' +
//                         '\n' +
//                         '                                        <div class="comment-reply-textarea">\n' +
//                         '                                            <textarea placeholder="" id="comment-reply-textarea"></textarea>\n' +
//                         '                                            <button class="comment-reply-textarea-btn am-text-break">发表评论</button>\n' +
//                         '                                        </div>\n' +
//                         '                                    </div>\n' +
//                         '                                </div>'
//
//
//                     $(".comment-main-list").append(list_item);
//                 })
//             }
//
//
//         }
//
//     })
//
//
// });

isLoginPlus();




$("#comment-btn").on("click",function () {


    var tag = 0;//判断是否登录

    $.ajax({
        type:"get",
        url:"/isLogin",
        async:false,
        dataType:"json",
        success:function (data) {
            if(data['state']==0){
                window.location.href="/login";
            }

            else {
                tag = 1;
                headImg = data['headImg'];
                id = data['id'];
            }

        }
    })

    if(tag!=0){
        var commentContent = $("#comment-content").val();
        if(commentContent==""){
            layer.alert('信息不能为空',{
                icon:2,
                skin: 'layer-ext-moon'
            })

            return;
        }
        $.ajax({
            type:"get",
            url:"/addComment",
            dataType:"json",
            data:{
                articleId:articleId,
                commentContent:commentContent
            },

            success:function (data) {
                comment_num++;
                comment_plus();
                var comment=' <div class="list-item">\n' +
                    '                                    <div class="user-img">\n' +
                    '                                        <a href="">\n' +
                    '                                            <img src="'+headImg+'">\n' +
                    '                                        </a>\n' +
                    '                                    </div>\n' +
                    '                                    <div class="list-item-content">\n' +
                    '                                        <div class="user">\n' +
                    '                                            <span class="user-name">'+data['remarkerName']+'</span>\n' +
                    '                                            <span class="pid" style="display: none">'+data['pid']+'</span>\n' +
                    '                                        </div>\n' +
                    '                                        <p class="text">'+commentContent+'</p>\n' +
                    '                                        <div class="info">\n' +
                    '                                            <span class="floor">#'+(++floor)+'</span>\n' +
                    '                                            <span class="time">'+data['date']+'</span>\n' +
                    '                                            <span class="like">\n' +
                    '                                                <a>\n' +
                    '                                                    <i class="am-icon-thumbs-o-up">&nbsp;&nbsp;0</i>\n' +
                    '                                                </a>\n' +
                    '                                            </span>\n' +
                    '\n' +
                    '                                            <span class="reply top-reply"><a>回复</a></span>\n' +
                    '\n' +
                    '                                        </div>\n' +
                    '\n' +
                    '                                        <div class="reply-box"></div>\n' +
                    '                                    </div>\n' +
                    '                                    <div class="comment-reply am-animation-slide-top" style="display: none">\n' +
                    '                                        <div class="user-img">\n' +
                    '                                            <img src="'+headImg+'">\n' +
                    '                                        </div>\n' +
                    '                                        <div class="comment-reply-textarea">\n' +
                    '                                            <textarea placeholder="" id="comment-reply-textarea"></textarea>\n' +
                    '                                            <button class="comment-reply-textarea-btn am-text-break">发表评论</button>\n' +
                    '                                        </div>\n' +
                    '                                    </div>' +
                    '                                </div>'


                $(".comment-main-list").prepend(comment);
                $("#comment-content").val("")
            }
        })
    }





})


var topOld = "";
var innerOld = "";
var lastReply = "";


$(document).on("click",".top-reply",function () {
    var temp = $(this).parent().parent().parent();
    var tag = 0;//判断是否登录

    $.ajax({
        type:"get",
        url:"/isLogin",
        async:false,
        dataType:"json",
        success:function (data) {
            if(data['state']==0){
                window.location.href="/login";
            }

            else {
                tag = 1;
                headImg = data['headImg'];
                id = data['id'];
            }

        }
    })

    if(tag!=0){
        //如果同源，或者第一次
        if($(this).is(lastReply)==true||lastReply==""){
            //如果上次点击过，则消失
            if(temp.is(topOld)==true){
                topOld = "";
                temp.children(".comment-reply").css("display","none");
                return;
            }

            //否则则显示
            else {
                user_id = 0;

                temp.children(".comment-reply").children(".comment-reply-textarea").children("textarea").attr("placeholder","写下你的伟论...");
                temp.children(".comment-reply").children(".user-img").children("img").attr("src",headImg);
                temp.children(".comment-reply").css("display","block");
            }
        }

        //如果不同源，则先清除存在的输入框，再重新
        else {
            $(".comment-reply").css("display","none");
            user_id = 0;
            temp.children(".comment-reply").children(".comment-reply-textarea").children("textarea").attr("placeholder","写下你的伟论...");
            temp.children(".comment-reply").children(".user-img").children("img").attr("src",headImg);
            temp.children(".comment-reply").css("display","block");
        }

        topOld = temp;
        lastReply = $(this);
    }

})


$(document).on("click",".inner-reply",function () {
    var temp = $(this).parent().parent().parent().parent().parent().parent();
    var person = "回复 @"+$(this).parent().prev(".user").children(".user-name").text()+" :";

    var tag = 0;//判断是否登录

    $.ajax({
        type:"get",
        url:"/isLogin",
        async:false,
        dataType:"json",
        success:function (data) {
            if(data['state']==0){
                window.location.href="/login";
            }

            else {
                tag = 1;
                headImg = data['headImg'];
                id = data['id'];
            }

        }
    })

    if(tag!=0){
        //如果同源或第一次，则显示
        if($(this).is(lastReply)||lastReply==""){

            if($(this).is(innerOld)){
                innerOld = "";
                temp.children(".comment-reply").css("display","none");
                return;
            }

            else {
                user_id = $(this).parent().prev(".user").children(".user-id").text();
                temp.children(".comment-reply").children(".comment-reply-textarea").children("textarea").attr("placeholder",person);
                temp.children(".comment-reply").children(".user-img").children("img").attr("src",headImg);
                temp.children(".comment-reply").css("display","block");
            }

        }

        //如果不是，则清除，显示
        else {
            $(".comment-reply").css("display","none");
            user_id = $(this).parent().prev(".user").children(".user-id").text();
            temp.children(".comment-reply").children(".comment-reply-textarea").children("textarea").attr("placeholder",person);
            temp.children(".comment-reply").children(".user-img").children("img").attr("src",headImg);
            temp.children(".comment-reply").css("display","block");
        }

        lastReply = $(this);
        innerOld = $(this);
    }


})

$(document).on("click",".comment-reply-textarea-btn",function () {


    var reply_btn = $(this).parent().children("textarea");
    var reply = reply_btn.val();
    var box = $(this).parent().parent().prev(".list-item-content").children(".reply-box");
    var pid = $(this).parent().parent().prev(".list-item-content").children(".user").children(".pid").text();
    var reply_constant=""

    if($(this).parent().children("textarea").attr("placeholder").indexOf("@")!=-1){

        //var index = $(this).parent().children("textarea").attr("placeholder").indexOf(":");
        //person = $(this).parent().children("textarea").attr("placeholder").substring(3,index);
        reply_constant = "回复 ";
        if(reply==""){
            layer.alert('信息不能为空',{
                icon:2,
                skin: 'layer-ext-moon'
            })
            return;
        }

        $.ajax({
            type:"get",
            url:"/addComment",
            dataType:"json",
            data:{
                articleId:articleId,
                commentContent:reply,
                responsorId:user_id,
                pId:pid

            },

            success:function (data) {
                comment_num++;
                comment_plus();
                var reply_item = '<div class="reply-item">\n' +
                    '                                                <div class="reply-user-img">\n' +
                    '                                                    <a href="">\n' +
                    '                                                        <img src="'+headImg+'">\n' +
                    '                                                    </a>\n' +
                    '                                                </div>\n' +
                    '                                                <div class="reply-item-content">\n' +
                    '                                                    <div class="user">\n' +
                    '                                                        <span class="user-name">'+data['remarkerName']+'</span>\n' +
                    '                                                        <span class="user-id" style="display: none">'+id+'</span>\n' +
                    '                                                        <span class="text">'+reply_constant+'<a>@'+data['responsorName']+'</a>：'+reply+'</span>\n' +
                    '                                                    </div>\n' +
                    '\n' +
                    '\n' +
                    '                                                    <div class="info">\n' +
                    '                                                        <span class="time">'+data['date']+'</span>\n' +
                    '                                                        <span class="reply inner-reply"><a>回复</a></span>\n' +
                    '                                                    </div>\n' +
                    '                                                </div>\n' +
                    '                                            </div>'

                box.append(reply_item);
                reply_btn.val("");
            }
        })

    }

    else {
        $.ajax({
            type:"get",
            url:"/addComment",
            dataType:"json",
            data:{
                articleId:articleId,
                commentContent:reply,
                pId:pid
            },

            success:function (data) {
                comment_num++;
                comment_plus();
                var reply_item = '<div class="reply-item">\n' +
                    '                                                <div class="reply-user-img">\n' +
                    '                                                    <a href="">\n' +
                    '                                                        <img src="'+headImg+'">\n' +
                    '                                                    </a>\n' +
                    '                                                </div>\n' +
                    '                                                <div class="reply-item-content">\n' +
                    '                                                    <div class="user">\n' +
                    '                                                        <span class="user-name">'+data['remarkerName']+'</span>\n' +
                    '                                                        <span class="user-id" style="display: none">'+id+'</span>\n' +
                    '                                                        <span class="text">'+reply+'</span>\n' +
                    '                                                    </div>\n' +
                    '\n' +
                    '\n' +
                    '                                                    <div class="info">\n' +
                    '                                                        <span class="time">'+data['date']+'</span>\n' +
                    '                                                        <span class="reply inner-reply"><a>回复</a></span>\n' +
                    '                                                    </div>\n' +
                    '                                                </div>\n' +
                    '                                            </div>'

                box.append(reply_item);
                reply_btn.val("");
            }
        })
    }



})

$(document).on("click",".like",function () {
    var tag = 0;//判断是否登录

    $.ajax({
        type:"get",
        url:"/isLogin",
        async:false,
        dataType:"json",
        success:function (data) {
            if(data['state']==0){
                window.location.href="/login";
            }

            else {
                tag = 1;
                headImg = data['headImg'];
                id = data['id'];
            }

        }
    })


    if(tag!=0){
        var temp = $(this).children("a").children("i");
        var commentId = $(this).parent().parent().children(".user").children(".pid").text();
        $.ajax({
            type:"get",
            url:"/commentLikeAction",
            dataType:"json",
            data:{
                commentId:commentId,
                articleId:articleId
            },
            success:function (data) {
                if(data==1){
                    var number = $(temp).text();
                    number++;
                    $(temp).html("&nbsp;&nbsp;"+number);
                    $(temp).removeClass("am-icon-thumbs-o-up");
                    $(temp).addClass("am-icon-thumbs-up")
                }

                else {
                    var number = $(temp).text();
                    number--
                    $(temp).html("&nbsp;&nbsp;"+number);
                    $(temp).removeClass("am-icon-thumbs-up");
                    $(temp).addClass("am-icon-thumbs-o-up");
                }
            }
        })

//            if($(temp).hasClass("am-icon-thumbs-o-up")==true){
//                var number = $(temp).text();
//                number++;
//                $(temp).html("&nbsp;&nbsp;"+number);
//                $(temp).removeClass("am-icon-thumbs-o-up");
//                $(temp).addClass("am-icon-thumbs-up")
//            }
//
//            else {
//                var number = $(temp).text();
//                number--
//                $(temp).html("&nbsp;&nbsp;"+number);
//                $(temp).removeClass("am-icon-thumbs-up");
//                $(temp).addClass("am-icon-thumbs-o-up");
//
//            }
    }
})