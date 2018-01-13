package cn.sibat.boot.server.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nanphonfy on 2017/9/22.
 */
public class TimeUtil {
    public static final String PATTERN_yyMMdd = "yyMMdd ";
    public static final String PATTERN_yy_MM_dd_HHmmss = "yy-MM-dd HH:mm:ss";
    public static final String PATTERN_yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String PATTERN_yy_MM_dd = "yyyy-MM-dd";
    public static final String PATTERN_yyyy_MM_dd = "yyyy-MM-dd";


    /**
     * 根据时间片映射小时
     *
     * @param timeslice 时间片
     * @return
     */
    public static int getHourByTimesLice(String timeslice) {
        return getInt(timeslice, 0) / 12;
    }

    /**
     * 根据时间片映射分钟数
     *
     * @param timeslice 时间片
     * @return
     */
    public static int getMinuteByTimesLice(String timeslice) {
        return getInt(timeslice, 0) * 5;
    }

    /**
     * 根据时间片获得映射时间，返回结果格式01:15:00
     *
     * @param timeslice
     * @return
     * @throws
     */
    public static String getTimeByTimesLice(String timeslice) {
        //最大时间片
        if ("288".equals(timeslice)) {
            return "23:59:00";
        }
        int totalMinute = (getInt(timeslice, 0) - 1) * 5;
        String hour = getStrZeroFill(totalMinute / 60);
        String minute = getStrZeroFill(totalMinute % 60);
        return hour + ":" + minute + ":00";

    }

    /**
     * 根据时间片获得前5分钟的映射时间，返回结果格式01:15:00
     *
     * @param timeslice
     * @return
     * @throws
     */
    public static String getTimeForwardByTimesLice(String timeslice) {
        //最大时间片
        if ("288".equals(timeslice)) {
            return "23:59:00";
        }
        int totalMinute = (getInt(timeslice, 0) - 1) * 5;
        String hour = getStrZeroFill(totalMinute / 60);
        String minute = getStrZeroFill(totalMinute % 60);
        return hour + ":" + minute + ":00";

    }

    /**
     * 数字不足2位补0
     *
     * @param number
     * @return
     * @throws
     */
    public static String getStrZeroFill(int number) {
        String qualifier = "";
        if (number < 10) {
            qualifier = "0" + number;
        } else {
            qualifier = "" + number;
        }
        return qualifier;
    }

    /**
     * 时间片不足3位补0
     *
     * @param timeslie
     * @return
     * @throws ParseException
     */
    public static String getTimesLiceZeroFill(int timeslie) {
        String qualifier = "";
        if (timeslie < 10) {
            qualifier = "00" + timeslie;
        } else if (timeslie < 100) {
            qualifier = "0" + timeslie;
        } else {
            qualifier = "" + timeslie;
        }
        return qualifier;
    }

    /**
     * 字符串转int
     *
     * @param str
     * @param defaultValue
     * @return
     */
    public static int getInt(String str, int defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        if (isInt(str)) {
            return Integer.parseInt(str);
        } else {
            return defaultValue;
        }
    }

    /**
     * 判断一个字符串是否为数字
     */
    public static boolean isInt(String str) {
        return str.matches("\\d+");
    }

    /**
     * 得到当前时间戳
     *
     * @return
     */
    public static long getCurrentTimeStamp() {
        return System.currentTimeMillis();
    }

    /**
     * 得到历史时间的时间戳
     *
     * @param date
     * @param pattern
     * @return
     */
    public static long getHistoryTimeStamp(String date, String pattern) {
        Date time = parseStringToDate(date, pattern);
        if (time != null) {
            return time.getTime();
        }
        return 0L;
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

    public static String parseDateToString(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat _df = new SimpleDateFormat(pattern);
        return _df.format(date);
    }

    /**
     * 得到当前年份，用于拼接hbase表，查询数据
     *
     * @return 17
     */
    public static int getNowYear() {
        //2017-2000=17
        return Calendar.getInstance().get(Calendar.YEAR) - 2000;
    }


    /**
     * 根据历史日期，返回历史年份，用于拼接hbase表，查询数据
     *
     * @param date 17-04-20 18:31:52
     * @return 17
     */
    public static String getHistoricalYear(String date) {
        return date.substring(0, 2);
    }
}
