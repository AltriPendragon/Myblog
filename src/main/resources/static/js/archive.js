var date;
var checkUrl = location.href;
var parameters = checkUrl.split("=");

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

function animationRule(total,tag) {

    var parm1 = total*0.11;
    var parm2 = total*0.45;
    var style = document.styleSheets[2];
    if(tag==1){
        style.deleteRule(1);
    }
    style.insertRule(" @-webkit-keyframes heightSlide{\n" +
        "            0%{\n" +
        "                height: 0;\n" +
        "            }\n" +
        "\n" +
        "\n" +
        "            11%{\n" +
        "                height: "+parm1+"px;\n" +
        "            }\n" +
        "\n" +
        "            45%{\n" +
        "                height: "+parm2+"px;\n" +
        "            }\n" +
        "\n" +
        "            100%{\n" +
        "                height: "+total+"px;\n" +
        "            }\n" +
        "        }",1)


}


function play() {
    // document.querySelector(".timezone").className="timezone"
    // window.requestAnimationFrame(function(time) {
    //     window.requestAnimationFrame(function(time) {
    //         document.querySelector(".timezone").className = "timezone changezone ";
    //     });
    // });

    $(".timezone").css('animation', 'heightSlide 2s linear');

}

function archiveWithTime(index) {
    $.ajax({
        type:"get",
        url:"/getArchiveArticlesByTime",
        dataType:"json",
        data:{
            time:date,
            pageNum:1,
            rows:10
        },

        success:function (data) {
            $(".timezone").empty();
            animationRule(data['result'].length*120,1);
            $("#Pagination").pagination(data['totalSize'], {
                num_edge_entries: 1, //边缘页数
                num_display_entries: 4, //主体页数
                load_first_page:false,
                callback: function (index) {
                    play();
                    archiveWithTime(index);
                },
                items_per_page:10,//每页显示1项
                current_page:index,
                prev_text : '<<',
                next_text : '>>',

            });


            $.each(data['result'],function (index, obj) {
                var time = obj['createTime'].split("-");
                var tag = obj['tags'].split(",");
                var tagHtml = "";
                var line = "";
                if(index+1==data['result'].length){
                    line = "line";
                }
                $.each(tag,function (index,value) {
                    tagHtml+='<a href="/tag/?tag='+value+'" class="article-meta-tag-innerTag">'+value+'</a>'
                })



                var top = '<div>\n' +
                    '                <div class="time-special"></div>\n' +
                    '                <h2 class="time-special-year">'+time[0]+'年'+time[1]+'月</h2>\n' +
                    '            </div>'

                var item = '<div>\n' +
                    '                <div class="new-timeline-major">\n' +
                    '\n' +
                    '\n' +
                    '                    <span class="node am-animation-slide-top am-animation-delay-1"></span>\n' +
                    '\n' +
                    '                    <div class="content am-animation-slide-top am-animation-delay-1">\n' +
                    '                        <header class="am-comment-hd">\n' +
                    '                            <div class="contentTitle">\n' +
                    '                                <a href="/article/'+obj['articleId']+'">'+obj['title']+'</a>\n' +
                    '                            </div>\n' +
                    '                        </header>\n' +
                    '\n' +
                    '                        <div class="am-comment-bd">\n' +
                    '                            <i class="am-icon-calendar article-tag"><a href="/archive/?archive='+obj['createTime']+'" class="article-meta-tag-innerTag">'+obj['createTime']+'</a></i>\n' +
                    '                            <i class="am-icon-tag article-tag">'+tagHtml+'</i>\n' +
                    '                            <i class="am-icon-folder article-tag"><a href="/category/?category='+obj['category']+'" class="article-meta-tag-innerTag">'+obj['category']+'</a></i>\n' +
                    '                        </div>\n' +
                    '\n' +
                    '                        <span class="title-left"></span>\n' +
                    '                    </div>\n' +
                    '\n' +
                    '                </div>\n' +
                    '                <div class="time"></div>\n' +
                    '                <h2 class="article-time">'+obj['createTime']+'</h2>\n' +
                    '                <div class="'+line+'"></div>\n' +
                    '            </div>'



                var leftItem = ' <div>\n' +
                    '                <div class="new-timeline-major timeLeft">\n' +
                    '\n' +
                    '\n' +
                    '                    <span class="node am-animation-slide-top am-animation-delay-1"></span>\n' +
                    '\n' +
                    '                    <div class="content am-animation-slide-top am-animation-delay-1">\n' +
                    '                        <header class="am-comment-hd">\n' +
                    '                            <div class="contentTitle">\n' +
                    '                                <a href="/article/'+obj['articleId']+'">'+obj['title']+'</a>\n' +
                    '                            </div>\n' +
                    '                        </header>\n' +
                    '\n' +
                    '                        <div class="am-comment-bd">\n' +
                    '                            <i class="am-icon-calendar article-tag"><a href="/archive/?archive='+obj['createTime']+'" class="article-meta-tag-innerTag">'+obj['createTime']+'</a></i>\n' +
                    '                            <i class="am-icon-tag article-tag">'+tagHtml+'</i>\n' +
                    '                            <i class="am-icon-folder article-tag"><a href="/category/?category='+obj['category']+'"  class="article-meta-tag-innerTag">'+obj['category']+'</a></i>\n' +
                    '                        </div>\n' +
                    '\n' +
                    '                        <span class="title-left"></span>\n' +
                    '                    </div>\n' +
                    '\n' +
                    '                </div>\n' +
                    '                <div class="time_left"></div>\n' +
                    '                <h2 class="article-time-left">'+obj['createTime']+'</h2>\n' +
                    '                <div class="'+line+'"></div>\n' +
                    '            </div>'


                if(index==0){
                    $(".timezone").append(top);
                }

                var num = Math.random();
                if(num<0.5){
                    $(".timezone").append(item);
                }

                if(num>0.5){
                    $(".timezone").append(leftItem);
                }




            })
        }

    })
}

function archiveArticles(index) {
    $.ajax({
        type:"get",
        url:"/getArchiveArticles",
        dataType:"json",
        data:{
            pageNum:index+1,
            rows:10
        },

        success:function (data) {
            animationRule(data['result'].length*120,1);
            $("#Pagination").pagination(data['totalSize'], {
                num_edge_entries: 1, //边缘页数
                num_display_entries: 4, //主体页数
                load_first_page:false,
                callback: function (index) {
                    play();
                    archiveArticles(index);
                },
                items_per_page:10,//每页显示1项
                current_page:index,
                prev_text : '<<',
                next_text : '>>',

            });

            $(".timezone").empty();

            var state = 0;
            $('head').append('<style>.timezone:before {content:"# 很好! 目前总计'+data['totalSize']+'篇日志，继续努力。"}</style>')
            $.each(data['result'],function (index, obj) {
                var time = obj['createTime'].substring(0,4);
                var tag = obj['tags'].split(",");
                var tagHtml = "";
                var line = "";
                var top_class="";
                if(index+1==data['result'].length){
                    line = "line";
                }
                $.each(tag,function (index,value) {
                    tagHtml+='<a href="/tag/?tag='+value+'" class="article-meta-tag-innerTag">'+value+'</a>'
                })





                var item = '<div>\n' +
                    '                <div class="new-timeline-major">\n' +
                    '\n' +
                    '\n' +
                    '                    <span class="node am-animation-slide-top am-animation-delay-1"></span>\n' +
                    '\n' +
                    '                    <div class="content am-animation-slide-top am-animation-delay-1">\n' +
                    '                        <header class="am-comment-hd">\n' +
                    '                            <div class="contentTitle">\n' +
                    '                                <a href="/article/'+obj['articleId']+'">'+obj['title']+'</a>\n' +
                    '                            </div>\n' +
                    '                        </header>\n' +
                    '\n' +
                    '                        <div class="am-comment-bd">\n' +
                    '                            <i class="am-icon-calendar article-tag"><a href="/archive/?archive='+obj['createTime']+'" class="article-meta-tag-innerTag">'+obj['createTime']+'</a></i>\n' +
                    '                            <i class="am-icon-tag article-tag">'+tagHtml+'</i>\n' +
                    '                            <i class="am-icon-folder article-tag"><a href="/category/?category='+obj['category']+'" class="article-meta-tag-innerTag">'+obj['category']+'</a></i>\n' +
                    '                        </div>\n' +
                    '\n' +
                    '                        <span class="title-left"></span>\n' +
                    '                    </div>\n' +
                    '\n' +
                    '                </div>\n' +
                    '                <div class="time"></div>\n' +
                    '                <h2 class="article-time">'+obj['createTime']+'</h2>\n' +
                    '                <div class="'+line+'"></div>\n' +
                    '            </div>'



                var leftItem = ' <div>\n' +
                    '                <div class="new-timeline-major timeLeft">\n' +
                    '\n' +
                    '\n' +
                    '                    <span class="node am-animation-slide-top am-animation-delay-1"></span>\n' +
                    '\n' +
                    '                    <div class="content am-animation-slide-top am-animation-delay-1">\n' +
                    '                        <header class="am-comment-hd">\n' +
                    '                            <div class="contentTitle">\n' +
                    '                                <a href="/article/'+obj['articleId']+'">'+obj['title']+'</a>\n' +
                    '                            </div>\n' +
                    '                        </header>\n' +
                    '\n' +
                    '                        <div class="am-comment-bd">\n' +
                    '                            <i class="am-icon-calendar article-tag"><a href="/archive/?archive='+obj['createTime']+'" class="article-meta-tag-innerTag">'+obj['createTime']+'</a></i>\n' +
                    '                            <i class="am-icon-tag article-tag">'+tagHtml+'</i>\n' +
                    '                            <i class="am-icon-folder article-tag"><a href="/category/?category='+obj['category']+'"  class="article-meta-tag-innerTag">'+obj['category']+'</a></i>\n' +
                    '                        </div>\n' +
                    '\n' +
                    '                        <span class="title-left"></span>\n' +
                    '                    </div>\n' +
                    '\n' +
                    '                </div>\n' +
                    '                <div class="time_left"></div>\n' +
                    '                <h2 class="article-time-left">'+obj['createTime']+'</h2>\n' +
                    '                <div class="'+line+'"></div>\n' +
                    '            </div>'



                if(state==0||state!=time){

                    if(state!=0){
                        top_class = "time-special-year-top"
                    }

                    var top = '<div class="'+top_class+'">\n' +
                        '                <div class="time-special"></div>\n' +
                        '                <h2 class="time-special-year">'+time+'年</h2>\n' +
                        '            </div>'

                    $(".timezone").append(top);
                    state = time;
                }


                var num = Math.random();
                if(num<0.5){
                    $(".timezone").append(item);
                }

                if(num>0.5){
                    $(".timezone").append(leftItem);
                }


                state = time;


            })



        }

    })
}

if(parameters[1]==""||parameters[1]==undefined){
    archiveArticles(0)
}

else {
    date = parameters[1];
    archiveWithTime(0);
}




function archiveDetails() {
    $.ajax({
        type:"get",
        url:"/getArchiveDetails",
        dataType:"json",
        success:function (data) {
            $.each(data['data'],function (index, obj) {
                var item = '<div class="category-date">\n' +
                    '                <div class="category">\n' +
                    '                <span>\n' +
                    '                    <a class="archiveName">'+obj['archiveName']+'</a>\n' +
                    '                    <span class="category-name">('+obj['archiveNum']+')</span>\n' +
                    '                </span>\n' +
                    '                </div>\n' +
                    '            </div>'

                $(".categories").append(item);
            })
        }

    })
}
archiveDetails();
isLoginPlus();


animationRule(120*10,0);

$(document).on("click",".archiveName",function () {
    var text = $(this).text();
    var new_text = text.substring(0,4)+"-"+text.substring(5,7);
    date = new_text;
    $(".pagination").empty();

    play();
    archiveWithTime(0);

})

$(".timezone").each(function() {
    $(this)[0].addEventListener("animationend",function(){
        $(this).css("animation","");
    });
});


