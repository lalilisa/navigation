package com.example.demo.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    private static String regexDigit="\\d+";

    public static boolean isEmpty(String t){

        return t==null || t.isEmpty();
    }

    public static boolean checkIntegerString(String number){

        return number != null && number.matches(regexDigit);
    }

    public static boolean checkFormatDate(String checkFormatDate){

        return checkFormatDate != null && checkFormatDate.matches("\\d{2}/\\d{2}/\\d{4}");
    }
    public static String getTextCurrentDate(String format){
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        return  simpleDateFormat.format(date);
    }

    public static boolean checkNumberStringInRange(String target,int start,int end){
        int targetpa=Integer.parseInt(target);
        return targetpa>=start && targetpa <=end;
    }
}
