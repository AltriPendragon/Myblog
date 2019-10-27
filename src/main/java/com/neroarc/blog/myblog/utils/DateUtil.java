package com.neroarc.blog.myblog.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author :fjx
 * @Date ;
 * @Descripe : 时间转换工具
 */
public class DateUtil {

    public static long getLongTime() {
        Date date = new Date();
        Long time = date.getTime();

        System.out.println(time);
        return  time;
    }

    public static long getLongTime(Date date){
        Long time = date.getTime();
        return time;
    }

    public static String getStringTime() {
        Date date = new Date();
        Long time = date.getTime();

        Date date1 = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(date1));
        return  sdf.format(date1);
    }

    public static String changeTimeByLong(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(date));
        return sdf.format(date);

    }

    public static long changeTimeByString(String s) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(s);
        Long time = date.getTime();
        System.out.println(time);
        return time;
    }

    public static String changeTimeByDate(Date date){
        Long time = date.getTime();

        Date date1 = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(date1));
        return  sdf.format(date1);
    }


   public static void main(String args[]) throws ParseException {

        DateUtil.getLongTime();
        DateUtil.getStringTime();
        DateUtil.changeTimeByLong(27632632);
        DateUtil.changeTimeByString("2018-09-11 21:20:19");

   }
}
