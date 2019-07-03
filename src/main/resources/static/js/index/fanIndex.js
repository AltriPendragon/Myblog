var temp=0;

function removeIcon(index) {
    if(index==0){
        $("#icons").removeClass("am-icon-paw")

    }

    if(index==1){
        $("#icons").removeClass("am-icon-tags")
    }

    if(index==2){
        $("#icons").removeClass("am-icon-arrows")
    }
}

function addIcon(index) {
    if(index==0){
        $("#icons").addClass("am-icon-paw")
    }

    if(index==1){
        $("#icons").addClass("am-icon-tags")
    }

    if(index==2){
        $("#icons").addClass("am-icon-arrows")
    }
}

function addHide(index) {
    if(index==0){
        $("#home").addClass("am-hide")
    }

    else if(index==1){
        $("#home1").addClass("am-hide")
    }

    else{
        $("#home2").addClass("am-hide")
    }
}

function removeHide(index) {
    if(index==0){
        $("#home").removeClass("am-hide")
    }

    else if(index==1){
        $("#home1").removeClass("am-hide")
    }

    else{
        $("#home2").removeClass("am-hide")
    }
}


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

function getTagCloud() {
    $.ajax({
        type:"get",
        url:"/getTagsCloud",
        dataType:"json",

        success:function (data) {
            if(data['status']==200){
                $(".tag-cloud-items").empty();
                $.each(data['result'],function (index, obj) {
                    var item = '<a href="/tag?tag='+obj['tag']+'" style="font-size:'+obj['tagSize']+'px">'+obj['tag']+'</a>';
                    $(".tag-cloud-items").append(item)
                })

            }

        }
    })
}

// $(document).ready(function () {
//     $(".am-dropdown-choice").click(function () {
//         var index = $(this).index()
// //            $("#home").addClass("am-hide")
// //            $("#home1").removeClass("am-hide")
//         addHide(temp)
//         removeHide(index)
//
//         removeIcon(temp)
//         addIcon(index);
//         temp=index
//
//     })
//
// //        $("#test2").click(function () {
// //            var index = $(this).index()
// ////            $("#home1").addClass("am-hide")
// ////            $("#home2").removeClass("am-hide")
// //            addHide(temp)
// //            removeHide(index)
// //            removeIcon(temp)
// //            addIcon(index);
// //            temp=index
// //        })
//
// //        $("#test3").click(function () {
// //            $("#home").addClass("am-hide")
// //            $("#home1").removeClass("am-hide")
// //            $("#icons").removeClass("am-icon-twitter")
// //            $("#icons").addClass("am-icon-github")
// //        })
//
//     $("#icons,.mycontent").mouseover(function () {
//         isHover = true;
//         $(".tag").addClass("am-active")
//     }).unbind("click")
//
//
//
//     $(".mycontent,#icons").mouseout(function () {
//         isHover = false;
//         setTimeout(function () {
//             if(isHover==false){
//                 $(".tag").removeClass("am-active")
//             }
//
//         },200)
//     })
// })
//
// $(document).on("click",".am-dropdown-choice-tagCloud",function () {
//     getTagCloud();
// })