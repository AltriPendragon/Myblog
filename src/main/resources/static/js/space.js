var index = 0;
var word = $(".name h3").html();
var images = [];
var imageIndex = 0;

function changeHeight() {
    var imgHeight = window.innerHeight;
    $("figure").css("height",imgHeight);
}

function type(){
    $(".name h3").html(word.substring(0,index++));
    if(index > word.length) {
        return;
    } else {
        setTimeout(type,110);
    };
}
window.onload = type();
window.onload = function () {
    changeHeight();
}



window.onresize = function () {
    changeHeight();
}

function initImage(){
    $.ajax({
        type:"get",
        url:"/getBgImagesByType",
        sync:false,
        dataType:"json",
        data:{
            type:1
        },

        success:function (data) {
            images = data;
            $("figure").css('background-image',"url("+images[0].url+")")
        },

        error:function () {
        }
    })
}

initImage()

$(".select-choice").on("click",function () {
    if($(".select-choice").hasClass("am-icon-bars")){
        $(".select-choice").removeClass("am-icon-bars").addClass("am-icon-close");
        $(".site-top .choice nav").addClass("navbar")
    }

    else {
        $(".select-choice").removeClass("am-icon-close").addClass("am-icon-bars");
        $(".site-top .choice nav").removeClass("navbar")
    }
})

$(".left-image").on("click",function () {
    imageIndex--;
    imageIndex = (imageIndex+images.length)%images.length;
    $("figure").css('background-image',"url("+images[imageIndex].url+")")
})

$(".right-image").on("click",function () {
    imageIndex++;
    imageIndex = (imageIndex+images.length)%images.length;
    $("figure").css('background-image',"url("+images[imageIndex].url+")")
})
