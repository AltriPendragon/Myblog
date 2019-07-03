var startTime = (Date.UTC(2019,5,1,0,0,0))/1000;
function secondToDate(second) {

    var tempTime;
    var strTime="";
    if(!second){
        return 0;
    }

    if(second>=24*3600){
        tempTime =  parseInt(second/86400);
        strTime+=tempTime+"天";
        second-=tempTime*86400
    }

    if(second>=3600){
        tempTime =  parseInt(second/3600);
        strTime+=tempTime+"时";
        second-=tempTime*3600;
    }

    if(second>=60){
        tempTime =  parseInt(second/60);
        strTime+=tempTime+"分";
        second-=tempTime*60;
    }

    strTime+=second+"秒";

    $(".tillTime").text(strTime);
}

function setTillTime() {
    var now = parseInt((new Date().getTime())/1000);

    console.log(startTime)
    console.log(now)

    var gapTime = now-startTime

    setInterval(function () {
        secondToDate(gapTime);
        gapTime++;
    },1000);
}

setTillTime();