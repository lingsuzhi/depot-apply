package com.lsz.depot.core.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class LocalDateUtil {


    public static String toStr(LocalDateTime time, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return df.format(time);
    }

    public static String toStr(LocalDateTime time) {

        return toStr(time, "yyyy-MM-dd");
    }

    public static LocalDateTime parse(String timeStr){
        return LocalDateTime.of( LocalDate.parse(timeStr),LocalTime.MIN);
    }
//    public static String parseDateTimestamp(LocalDateTime date) throws ParseException {
//        if (null == date) {
//            date = LocalDateTime.now();
//        }
//        Timestamp timestamp = Timestamp.valueOf(date);//LocationDateTime 转时间戳
//        String s = timestamp.toString();
//        String res;
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        long ts = simpleDateFormat.parse(s).getTime();
//        res = String.valueOf(ts);
//        return res;
//    }

}
