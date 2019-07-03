var checkUrl = location.href;//获取url
var parameters = checkUrl.split("=");//以=为分隔符获取参数
var tag = parameters[1];

//根据页码显示不同页的标签
function tags(index) {
    tag = decodeURI(tag);
    $.ajax({
        type: "get",
        url: "/getArticlesByTag",
        dataType: "json",
        data: {
            pageNum: index + 1,
            rows: 10,
            tag: tag
        },

        success: function (data) {
            if(data['status']==200){

                $("#Pagination").pagination(data['totalSize'], {
                    num_edge_entries: 1, //边缘页数
                    num_display_entries: 4, //主体页数
                    load_first_page:false,
                    callback: function (index) {
                        tags(index)
                    },
                    items_per_page:10,//每页显示1项
                    current_page:index,
                    prev_text : '<<',
                    next_text : '>>',

                });

                $(".line").empty();
                var category_top = '<div class="line-top">\n' +
                    '                            <span></span>\n' +
                    '                            <h2>#'+tag+'</h2>\n' +
                    '                        </div>'


                $(".line").append(category_top);

                $.each(data['result'],function (index, obj) {
                    var tagHtml = "";
                    var tags = obj['tags'].split(",");
                    $.each(tags,function (index, value) {
                        tagHtml+='<a href="/tag?tag='+value+'">'+value+'</a>'
                    })

                    var line_item = '<div class="line-item">\n' +
                        '                            <span class="node am-animation-slide-top am-animation-delay-1"></span>\n' +
                        '                            <div class="content am-comment-main am-animation-slide-top am-animation-delay-1">\n' +
                        '                                <header class="am-comment-hd">\n' +
                        '                                    <div class="contentTitle am-comment-meta">\n' +
                        '                                        <a href="/article/'+obj['articleId']+'">'+obj['title']+'</a>\n' +
                        '                                    </div>\n' +
                        '                                </header>\n' +
                        '                                <div class="am-comment-bd">\n' +
                        '                                    <i class="am-icon-calendar"><a href="/archive?archive='+obj['createTime']+'">'+obj['createTime']+'</a></i>\n' +
                        '                                    <i class="am-icon-folder"><a href="/category?category='+obj['category']+'">'+obj['category']+'</a></i>\n' +
                        '                                    <i class="am-icon-tag">'+tagHtml+'</i>\n' +
                        '                                </div>\n' +
                        '                            </div>\n' +
                        '                        </div>'

                    $(".line").append(line_item);
                })


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

tags(0);
isLoginPlus();