package cn.sibat.boot.server.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static final String PATTERN_yyyyMM = "yyyyMM";
    public static final String PATTERN_yyyy_MM_dd = "yyyy-MM-dd";
    public static final String PATTERN_yyyy_MM = "yyyy-MM";
    public static final String PATTERN_yyMMddHHmmss = "yyMMddHHmmss";
    public static final String PATTERN_HHmmss = "HH:mm:ss";
    public static final String PATTERN_yyyy_MM_dd_HHmmss = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_yy_MM_dd_HHmmss = "yy-MM-dd HH:mm:ss";
    public static final String PATTERN_yyyyMMdd = "yyyyMMdd";
    public static final String PATTERN_yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
    public static final String PATTERN_HTTP = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final String PATTERN_LOG = "yyyy/MM/dd HH:mm";

    public static String getCurrentTimeString() {
        return parseDateToString(Calendar.getInstance().getTime(),
                PATTERN_yyyy_MM_dd_HHmmss);
    }

    public static String parseDateToString(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat _df = new SimpleDateFormat(pattern);
        return _df.format(date);
    }

    public static String parseDateToTimeString(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat _df = new SimpleDateFormat(pattern);
        return _df.format(date);
    }

    public static final long getDateLong() {
        return System.currentTimeMillis();
    }

    public static Date format(Date date, String pattern) {
        if (date == null) {
            return date;
        }
        SimpleDateFormat _df = new SimpleDateFormat(pattern);
        _df.format(date);
        return date;
    }

    public static double getCurrentTime_fortoken() {
        return Double.parseDouble(getCurrentTimeString().substring(0, 13));
    }

    public static String parseDateToHttp(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat _df = new SimpleDateFormat(PATTERN_HTTP, Locale.US);
        return _df.format(date);
    }

    public static Date parseLongToDate(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return cal.getTime();
    }

    public static long getTimeDiff(Date begin_date, Date end_date) {
        return end_date.getTime() - begin_date.getTime();
    }

    public static Date parseStringToDate(String date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat _df = new SimpleDateFormat(pattern);
        try {
            return _df.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static int getCurrentYear(){
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    public static int getCurrentWeek(){
        Calendar cal = Calendar.getInstance();
        // 设置周一为每周第一天，外国人通常以周日为第一天
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getCurrentMonth(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 月份是从0开始，所有加1
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * eg: field=Calendar.DATE, amount=7   则返回date后的第七天时间
     *     field=Calendar.DATE, amount=-7  则返回date前的第七天时间
     *     field=Calendar.MONTH, amount=-1  则返回date前的第一个月时间
     *
     * @param date
     * @param field 代表天周月年
     * @param amount 负数为当前时间前，正数当前时间后
     * @return
     */
    public static Date getDateByAmount(Date date, int field, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, amount);
        return cal.getTime();
    }

    /**
     * https://www.cnblogs.com/0201zcr/p/5000977.html
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1,Date date2){
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }
}
