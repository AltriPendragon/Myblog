var checkUrl = location.href;
var registers = checkUrl.split("/");
var register = registers[3];

//根据url判断是注册还是登陆
if(register=="register"){
    signInClick();
}


function resetInput() {
    $(".username").val("");
    $(".telephone-code").val("");
    $("#real-userName").val("");
    $(".password").val("");
    $("#new-password").val("");
    $("#check-new-password").val("")
}


function resetIcon() {
    $(".icon-double-telephone").removeClass("am-icon-close am-icon-check");
    $(".icon-double-newPassword").removeClass("am-icon-close am-icon-check");
    $(".icon-double-password").removeClass("am-icon-close am-icon-check");
    $(".icon-check-newPassword").removeClass("am-icon-close am-icon-check");
}

function signInClick() {
    resetInput();
    resetIcon();

    $(".code-box,.check-new-password,.return-back,.sign-in-box,.real-username-box").removeClass("box-hidden");

    $(".lost-password,.sign-in,.submit-box,.check-new-password").addClass("box-hidden");
}

function isUserExist() {
    var telephone = $(".username").val();
    var flag = 0;
    $.ajax({
        type:"get",
        url:"/isUserExist",
        dataType:"json",
        async:false,
        data:{
            telephone:telephone
        },

        success:function (data) {
            if(data['status']==200){
                flag = 1;
            }

        }

    })

    return flag;
}

function checkNewPassword() {
    var newPassword = $("#new-password").val();
    var check_newPassword = $("#check-new-password").val();
    if(newPassword!=check_newPassword){
        layer.alert('两次密码不一致',{
            icon:7,
            skin: 'layer-ext-moon'
        })

        return 0;
    }

    return 1;
}

function checkPhone(){
    var phone = $(".username").val();
    var phoneExist = 0;

    if(phone==""){
        layer.alert('电话不能为空',{
            icon:7,
            skin: 'layer-ext-moon'
        })

        return false;
    }

    if(!(/^1[34578]\d{9}$/.test(phone))){
        layer.alert('电话格式错误',{
            icon:2,
            skin: 'layer-ext-moon'
        })
        return false;
    }

    if(register=="register"){
        $.ajax({
            type:"get",
            url:"/getUserByProviderId",
            dataType:"json",
            async:false,
            data:{
                telephone:phone
            },

            success:function (data) {
                if(data['status']==500){
                    layer.alert('该电话已经注册',{
                        icon:2,
                        skin: 'layer-ext-moon'
                    })

                    phoneExist = 1;
                }

            }


        })

        if(phoneExist==1){
            return false;
        }
    }



    return true;
}

function loginSubmitCheck() {

    var check = 1;
    var telephone = $(".username").val();
    var password = $(".password").val();
    if(telephone==""||password==""){
        //1正确，2错误
        layer.alert('必填项不能为空',{
            icon:2,
            skin: 'layer-ext-moon'
        })
        return false;
    }

    var flag = isUserExist();
    if(flag==0){

        layer.alert('未注册用户',{
            icon:7,
            skin: 'layer-ext-moon'
        })
        return false;
    }

    $.ajax({
        type:"get",
        url:"/checkUserNameAndPassword",
        dataType:"json",
        async:false,
        data:{
            telephone:telephone,
            password:password
        },

        success:function (data) {
            if(data==0){
                layer.alert('用户名或密码错误',{
                    icon:2,
                    skin: 'layer-ext-moon'
                })

                check = 0;

            }

        }


    })

    if(check==0){
        return false;
    }

    return true;
}

$(".username").blur(function () {
    var check = checkPhone();
    $(".icon-double-telephone").removeClass("am-icon-check");
    $(".icon-double-telephone").removeClass("am-icon-close");
    if(check==true){
        $(".icon-double-telephone").addClass("am-icon-check")
    }

    else {
        $(".icon-double-telephone").addClass("am-icon-close");
    }

})

$("#check-new-password").blur(function () {
    var flag = checkNewPassword();

    $(".check-new-password").removeClass("am-icon-check");
    $(".check-new-password").removeClass("am-icon-close");

    if(flag==1){
        $(".icon-check-newPassword").removeClass("am-icon-close")
        $(".icon-check-newPassword").addClass("am-icon-check")
    }

    else {
        $(".icon-check-newPassword").removeClass("am-icon-check")
        $(".icon-check-newPassword").addClass("am-icon-close");
    }

})

$("#new-password").blur(function () {
    var new_password  = $("#new-password").val();
    $(".icon-double-newPassword").removeClass("am-icon-check");
    $(".icon-double-newPassword").removeClass("am-icon-close");
    if(new_password==""){
        $(".icon-double-newPassword").addClass("am-icon-close");
    }

    else {
        $(".icon-double-newPassword").addClass("am-icon-check");
    }
})

$(".submit-box").on("click",function () {

    // var flag = isUserExist();
    // if(flag==1){
    //     if($(".submit-box div").hasClass("submit-icon")==true){
    //         $(".submit-box div").removeClass("submit-icon");
    //         $(".submit-box div").addClass("icon-loading");
    //
    //         $("#submit").attr('value', "登录中...");
    //
    //     }
    //
    //     else {
    //         $(".submit-box div").addClass("submit-icon");
    //         $(".submit-box div").removeClass("icon-loading");
    //     }
    // }
    //
    // else {
    //     layer.alert('未注册用户',{
    //         icon:7,
    //         skin: 'layer-ext-moon'
    //     })
    // }



})

$(".sign-in-box").on("click",function () {
    var telephone = $(".username").val();
    var telephoneCode = $(".telephone-code").val();
    var realName = $("#real-userName").val();
    var password = $(".password").val();

    if(telephone==""||telephoneCode==""||realName==""||password==""){
        layer.alert('必填项不能为空',{
            icon:7,
            skin: 'layer-ext-moon'
        })

        return;
    }

    $.ajax({
        type:"post",
        url:"/register",
        dataType:"json",
        data:{
            providerId:telephone,
            telephoneCode:telephoneCode,
            name:realName,
            password:password
        },

        success:function (data) {
            if(data['status']==200){
                layer.alert('注册成功',{
                    icon:1,
                    skin: 'layer-ext-moon'
                })
            }

            else {
                layer.alert('注册失败',{
                    icon:2,
                    skin: 'layer-ext-moon'
                })
            }

            resetInput();
            resetIcon();

            $(".password-box,.submit-box.submit-box,.lost-password,.sign-in").removeClass("box-hidden");

            $(".reset-box,.return-back,.code-box,.new-password,.check-new-password,.sign-in-box,.real-username-box").addClass("box-hidden");
        }
    })
})

$(".reset-box").on("click",function () {
    var telephone = $(".username").val();
    var telephoneCode = $(".telephone-code").val();
    var newPassword = $("#new-password").val();
    var check_newPassword = $("#check-new-password").val();

    if(telephone==""||telephoneCode==""||newPassword==""||check_newPassword==""){
        layer.alert('必填项不能为空',{
            icon:2,
            skin: 'layer-ext-moon'
        })

        return;
    }

    else {
        checkNewPassword();

        $.ajax({
            type:"post",
            url:"/resetPassword",
            dataType:"json",
            data:{
                providerId:telephone,
                telephoneCode:telephoneCode,
                password:check_newPassword
            },

            success:function (data) {
                if(data['status']==200){
                    layer.alert('重置成功',{
                        icon:1,
                        skin: 'layer-ext-moon'
                    })


                }

                else {
                    layer.alert('重置失败',{
                        icon:2,
                        skin: 'layer-ext-moon'
                    })
                }


            }
        })

    }

})


$(".lost-password").on("click",function () {
    resetInput();
    resetIcon();

    $(".reset-box,.return-back,.code-box,.new-password,.check-new-password").removeClass("box-hidden");

    $(".password-box,.submit-box,.lost-password,.sign-in").addClass("box-hidden")
})

$(".return-back").on("click",function () {
    register = "login";
    resetInput();
    resetIcon();

    $(".password-box,.submit-box.submit-box,.lost-password,.sign-in").removeClass("box-hidden");

    $(".reset-box,.return-back,.code-box,.new-password,.check-new-password,.sign-in-box,.real-username-box").addClass("box-hidden");
})

$(".sign-in").on("click",function () {

    register = "register";
    resetInput();
    resetIcon();

    $(".code-box,.check-new-password,.return-back,.sign-in-box,.real-username-box").removeClass("box-hidden");

    $(".lost-password,.sign-in,.submit-box,.check-new-password").addClass("box-hidden");
})

function timeCount() {
    var time = 60;
    var newText = $("#send-message");

    $(".entry").attr("disabled",true);
    $(".entry").css("pointer-events","none");



    var timeContDown=function () {
        window.setTimeout(function() {
            if(time > 0) {
                time -= 1;
                newText.text(time + "秒重新发送");
                timeContDown();
            } else {
                newText.text("重新发送");
                $(".entry").attr("disabled",false);
                //$(".entry").css("pointer-events","auto");
                $(".entry").css("pointer-events","");
                time=60;
            }
        }, 1000);
    }

    timeContDown();


}

$("#send-message").on("click",function () {
    if(checkPhone()==false){
        return;
    }

    else {
        timeCount();
        var telephone = $(".username").val();

        $.ajax({
            type:"post",
            url:"/getTelephoneCode",
            dataType:"json",
            data:{
                telephone:telephone
            },

            success:function (data) {

            }
        })
    }

})