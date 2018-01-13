package cn.sibat.boot.server.util;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static final String REGEX_UNICODE = "[\r\t\f\\x0b\\xa0\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u200b\u2028\u2029\u3000]";
    private static final Log logger = LogFactory.getLog(StringUtil.class);
    static String IMAGE_URL_PREFIX = "http://imgsize.ph.126.net/?imgurl=";
    private static final String STYLE = "##,###";
    private static final String CASH_STYLE = "0.##";


    private final static int INTERVAL = 5;

    public static int getHourByRowKeyTimeStamp(String timestamp) {
        //1709060515
        return getInt(timestamp.substring(6, 8), -1);
    }

    public static String getFiveMinuteByTime(String time) {
        logger.info(time);
        int m = getInt(time.substring(12, 14), 0) / INTERVAL * INTERVAL;
        if (m >= 10) {
            return (time.substring(0, 12).replaceAll(":", "").replaceAll("-", "").replaceAll(" ", "") + m).trim();
        }
        else {
            return (time.substring(0, 12).replaceAll(":", "").replaceAll("-", "").replaceAll(" ", "") + "0" + m).trim();
        }
    }

    /**
     * @param time    时间
     * @param formate 格式yyyy年MM月dd日
     * @return
     */
    public static String getDate(long time, String formate) {
        System.out.println("----" + time);
        try {
            if (StringUtils.isEmpty(formate)) {
                formate = "yyyy年MM月dd日";
            }
            SimpleDateFormat formator = new SimpleDateFormat(formate);
            return formator.format(new Date(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * @param time    时间
     * @param formate 格式yyyy年MM月dd日
     * @return
     */
    public static String getDate(Date date, String formate) {
        try {
            if (date == null) {
                return "";
            }
            if (StringUtils.isEmpty(formate)) {
                formate = "yyyy年MM月dd日";
            }
            SimpleDateFormat formator = new SimpleDateFormat(formate);
            return formator.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * @param duration 是 毫秒
     * @return
     */
    public static String getDuration(long duration) {
        duration /= 1000;
        if (duration <= 0) {
            return "0";
        }
        try {
            long hour = duration / 3600;
            long min = (duration % 3600) / 60;
            long s = (duration % 3600) % 60;
            if (hour == 0) {
                if (min == 0) {
                    return s + "秒";
                } else {
                    return min + "分钟" + s + "秒";
                }
            } else {
                return hour + "小时" + min + "分钟" + s + "秒";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";

    }

    /**
     * @param time    是长 秒
     * @param formate
     * @return
     */
    public static String getDurationMs(long duration) {
        if (duration <= 0) {
            return "";
        }
        try {
            duration = duration / 1000;
            long hour = duration / 3600;
            long min = (duration % 3600) / 60;
            long s = (duration % 3600) % 60;
            if (hour >= 48) {
                return (hour / 24) + "天";
            }
            if (hour == 0) {
                if (min == 0) {
                    return s + "秒";
                } else {
                    return min + "分钟" + s + "秒";
                }
            } else {
                return hour + "小时" + min + "分钟" + s + "秒";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";

    }

    public static String getDurationMin(long duration) {
        if (duration <= 0) {
            return "";
        }
        try {
            duration = duration / 1000;
            long hour = duration / 3600;
            long min = (duration % 3600) / 60;
            if (hour >= 48) {
                return (hour / 24) + "天";
            }
            if (hour == 0) {
                if (min > 0) {
                    return min + "分钟";
                }
            } else {
                return hour + "小时" + min + "分钟";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    // 显示给前台页面的上次在线的时间
    public static String showDuration(Date lastLoginTime) {
        if (lastLoginTime == null) {
            return "";
        }
        return showDuration(lastLoginTime.getTime());
    }

    // 显示给前台页面的上次在线的时间
    public static String showDuration(long lastLoginTime) {
        long duration = System.currentTimeMillis() - lastLoginTime;
        if (duration <= 0) {
            return "";
        }
        try {
            duration = duration / 1000; // 多少秒
            long hour = duration / 3600; // 多少小时
            int day = (int) (hour / 24);
            long min = (duration % 3600) / 60;
            if (day == 0) {
                if (hour == 0) {
                    if (min == 0) {
                        return 1 + "分钟";
                    } else {
                        return min + "分钟";
                    }
                } else {
                    return hour + "小时";
                }
            } else {
                return day + "天";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static int floor(double number) {
        return (int) Math.floor(number);
    }

    /**
     * 转义
     *
     * @param input
     * @return
     */
    public static String escapeHTMLTag(String input) {
        if (input == null) {
            return "";
        }
        input = input.trim().replaceAll("&", "&amp;");
        input = input.replaceAll("<", "&lt;");
        input = input.replaceAll(">", "&gt;");
        input = input.replaceAll("\"", "&quot;");
        input = input.replaceAll(REGEX_UNICODE, " ");
        return input.trim();
    }

    /**
     * 反转义
     *
     * @param text
     * @return
     */
    public static String unescapeHTMLTag(String text) {
        if (text == null) {
            return "";
        }
        text = text.replaceAll("&quot;", "\"");
        text = text.replaceAll("&gt;", ">");
        text = text.replaceAll("&lt;", "<");
        text = text.trim().replaceAll("&amp;", "&");
        return text.trim();
    }

    // /**
    // * 截取中文字符
    // *
    // * @param text
    // * @return
    // */
    // public static String getSubString(String text, int size) {
    // size = size*2;
    // if (text == null) {
    // return "";
    // }
    // int resultCount = 0;
    // int totalCount = 0;
    // for (int i = 0; i < text.length(); i++) {
    // int c = text.codePointAt(i);
    // if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
    // totalCount++;
    // } else {
    // totalCount += 2;
    // }
    // if (totalCount <= size) {
    // resultCount++;
    // } else {
    // break;
    // }
    // }
    // if (resultCount < text.length()) {
    // return text.substring(0, resultCount) + "..";
    // } else {
    // return text.substring(0, resultCount);
    // }
    // }
    //

    /**
     * 中文一个2个字符,英文1个字符,截取size个字符
     *
     * @param text
     * @return
     */
    public static String getSubString2(String text, int size) {
        if (text == null) {
            return "";
        }
        int resultCount = 0;
        int totalCount = 0;
        for (int i = 0; i < text.length(); i++) {
            int c = text.codePointAt(i);
            if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
                totalCount++;
            } else {
                totalCount += 2;
            }
            if (totalCount <= size) {
                resultCount++;
            } else {
                break;
            }
        }
        if (resultCount < text.length()) {
            return text.substring(0, resultCount);
        } else {
            return text.substring(0, resultCount);
        }
    }

    /**
     * 根据byte的长度来subString操作
     *
     * @param srcStr
     * @param length
     * @return
     */
    public static String subStringByByte(String srcStr, int length)

    {
        StringBuffer sb = new StringBuffer(length);
        int srcLength = srcStr.length();// source string length
        int tempLength = 0;// the byte length
        for (int i = 0; i < srcLength; i++) {
            String tempStr = String.valueOf(srcStr.charAt(i));// string consists
            // of a char
            byte[] b = tempStr.getBytes();// the byte length in the tempStr
            tempLength += b.length;
            if (length >= tempLength) {
                sb.append(tempStr);
            } else {
                break;
            }
        }
        return sb.toString();
    }

    /**
     * 得到字符串长度
     *
     * @param text
     * @return
     */
    public static int getLength(String text) {
        if (text == null) {
            return 0;
        }
        int totalCount = 0;
        for (int i = 0; i < text.length(); i++) {
            int c = text.codePointAt(i);
            if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
                totalCount++;
            } else {
                totalCount += 2;
            }
        }
        return totalCount;
    }

    /**
     * 名字标红
     *
     * @param text
     * @param keywords
     * @return
     */
    public static String makeNameRed(String text, String keywords) {
        if (StringUtils.isEmpty(text)) {
            return "";
        }
        if (StringUtils.isEmpty(keywords)) {
            return text;
        }
        return text.replace(keywords, "<span class=\"cDRed\">" + keywords + "</span>");
    }

    /**
     * 处理js
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static String escapeJavaScript(String str) throws Exception {
        if (str == null || str.length() == 0) {
            return null;
        }
        StringWriter out = new StringWriter(str.length() * 2);
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            char ch = str.charAt(i);
            if (ch < ' ') {
                switch (ch) {
                    case 8: // '\b'
                        out.write(92);
                        out.write(98);
                        break;

                    case 10: // '\n'
                        out.write(92);
                        out.write(110);
                        break;

                    case 9: // '\t'
                        out.write(92);
                        out.write(116);
                        break;

                    case 12: // '\f'
                        out.write(92);
                        out.write(102);
                        break;

                    case 13: // '\r'
                        out.write(92);
                        out.write(114);
                        break;

                    case 11: // '\013'
                    default:
                        break;
                }
            } else {
                switch (ch) {
                    case 39: // '\''
                        out.write(39);
                        break;

                    case 34: // '"'
                        out.write(92);
                        out.write(34);
                        break;

                    case 92: // '\\'
                        out.write(92);
                        out.write(92);
                        break;

                    default:
                        out.write(ch);
                        break;
                }
            }
        }
        return out.toString();
    }

    /**
     * 是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("(-|)[0-9]+");
        return pattern.matcher(str).matches();
    }

    public static String encodeSpecialCode(String source) {
        source = source.replaceAll("\"", "&#34");
        source = source.replaceAll("'", "&#39");
        source = source.replaceAll("\\(", "&#40");
        source = source.replaceAll("\\)", "&#41");
        return source;
    }

    public static String filterHtml(String str) {
        String regxpForHtml = "<([^>]*)>";
        Pattern pattern = Pattern.compile(regxpForHtml);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        boolean result1 = matcher.find();
        while (result1) {
            matcher.appendReplacement(sb, "");
            result1 = matcher.find();
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static int parseInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (Exception e) {
            logger.error("Not a valid number!");
        }
        return 0;
    }

    public static String encodeURL(String url, String encode) {
        if (StringUtils.isEmpty(url)) {
            return "";
        }
        String result = url;
        try {
            return URLEncoder.encode(url, encode);
        } catch (Exception e) {
            logger.error("", e);
        }
        return result;
    }

    public static String encodeURL(String url) {
        return encodeURL(url, "utf-8");
    }

    /*
     * 随机获取oimage的服务器地址
     */
    @Deprecated
    public static String randOimage() {
        Random rand = new Random();
        int value1 = rand.nextInt(8);
        if (value1 <= 0) {
            value1 = 1;
        }
        if (value1 >= 8) {
            value1 = 8;
        }

        char[] items = new char[]{'a', 'b', 'c'};
        int value2 = rand.nextInt(3);
        if (value2 <= 0) {
            value2 = 0;
        }
        if (value2 >= 3) {
            value2 = 2;
        }

        return items[value2] + Integer.toString(value1);
    }

    @SuppressWarnings("deprecation")
    public static String getWeek(Date date) {
        if (date == null) {
            return "";
        }
        int week = date.getDay();
        String str = null;
        if (0 == week) {
            str = "周日";
        } else if (1 == week) {
            str = "周一";
        } else if (2 == week) {
            str = "周二";
        } else if (3 == week) {
            str = "周三";
        } else if (4 == week) {
            str = "周四";
        } else if (5 == week) {
            str = "周五";
        } else if (6 == week) {
            str = "周六";
        } else {
            str = "周" + week;
        }
        return str;
    }

    // 将以||@分割的text分割开
    public static String[] splitAtText(String org) {
        return org.split("\\|\\|\\@");
    }

    public static boolean checkMobile(String mobile) {
        if (mobile == null || "".equals(mobile)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^1(3|5|8)\\d{9}$");
        Matcher matcher = pattern.matcher(mobile);
        return matcher.find();
    }

    // public static String sublength(String content,int length){
    // if(StringUtils.isBlank(content)||length<=0){
    // return "";
    // }
    // StringBuffer result = new StringBuffer();
    // int resultLength = 0;
    // for(int i=0;i<content.length();i++){
    // char ch = content.charAt(i);
    // if(CharUtils.isAscii(ch)){
    // resultLength++;
    // }else{
    // resultLength+=2;
    // }
    // result.append(ch);
    // if(resultLength>=2*length){
    // break;
    // }
    // }
    // return result.toString();
    // }

    /**
     * replace each char by replacement from start util length times
     *
     * @param content
     * @param start
     * @param length
     * @param replacement
     * @return
     */
    public static String replace(String content, int start, int length, String replacement) {
        if (StringUtils.isBlank(content)) {
            return "";
        }
        if (length <= 0 || start < 0 || start >= content.length()) {
            return content;
        }
        String r = replacement;
        if (StringUtils.isBlank(replacement)) {
            r = "";
        }
        int end = start + length;
        StringBuffer result = new StringBuffer(content.length());
        for (int i = 0; i < content.length(); i++) {
            if (i >= start && i < end) {
                result.append(r);
            } else {
                char ch = content.charAt(i);
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static String escapeHTML(String input) {
        if (input == null) {
            return "";
        }
        input = input.replaceAll("<", "&lt;");
        input = input.replaceAll(">", "&gt;");
        input = input.replaceAll("\"", "&quot;");
        input = input.replaceAll("'", "&apos;");
        input = input.replaceAll("&", "&amp;");
        return input.trim();
    }

    public static String escapeSequence(String input) {
        if (input == null) {
            return "";
        }
        input = input.replaceAll("<", "&lt;");
        input = input.replaceAll(">", "&gt;");
        input = input.replaceAll("\"", "&quot;");
        input = input.replaceAll("'", "&apos;");
        input = input.replaceAll("/*", "&nbsp;");
        input = input.replaceAll("*/", "&nbsp;");
        input = input.replaceAll(REGEX_UNICODE, "&nbsp;");
        return input.trim();
    }

    public static String escHTML(String s) {
        if (s == null) {
            return "";
        }
        s = s.trim();
        StringBuilder buffer = new StringBuilder();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);
            if (ch == '"') {
                buffer.append("&quot;");
            } else if (ch == '&') {
                buffer.append("&amp;");
            } else if (ch == '<') {
                buffer.append("&lt;");
            } else if (ch == '>') {
                buffer.append("&gt;");
            } else {
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }

    public static final String escapeParenthesis(String source) {

        if (source == null) {
            return source;
        }
        source = source.replaceAll("\\(", "&#40");
        source = source.replaceAll("\\)", "&#41");
        source = source.replaceAll("\\u0028", "&#40");
        source = source.replaceAll("\\u0029", "&#41");
        return source;
    }


    /**
     * This method escape the HTML format and quote the string as a valid JSON String.
     *
     * @param str
     * @return
     */
    public static String escHTMLAndJSONExt(String str) {
        str = escHTMLAndJSON(str);
        if (str.contains("#")) {
            str = " " + str.trim() + " ";
        }
        return str;
    }

    /**
     * This method escape the HTML format and quote the string as a valid JSON String.
     *
     * @param str
     * @return
     */
    public static String escHTMLAndJSON(String str) {
        str = getHTMLValidText(str);
        return escapeJSONString(str);
    }

    public static String getHTMLValidText(String s) {
        if (s == null) {
            return "";
        }
        StringBuilder buffer = new StringBuilder();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);
            if (ch == '"') {
                buffer.append("&quot;");
            } else if (ch == '&') {
                buffer.append("&amp;");
            } else if (ch == '<') {
                buffer.append("&lt;");
            } else if (ch == '>') {
                buffer.append("&gt;");
            } else if (ch == '\'') {
                buffer.append("&#39;");
            } else {
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }

    public static String escHTMLNoTrim(String s) {
        if (s == null) {
            return "";
        }
        StringBuilder buffer = new StringBuilder();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);
            if (ch == '"') {
                buffer.append("&quot;");
            } else if (ch == '&') {
                buffer.append("&amp;");
            } else if (ch == '<') {
                buffer.append("&lt;");
            } else if (ch == '>') {
                buffer.append("&gt;");
            } else {
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }

    public static String escJSON(String str) {
        return escapeJSONString(str);
    }

    public static String escReplyBuildingJSON(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        str = str.replace("\r\n", "\n");
        str = str.replaceAll("[\n-\r]", "<br/>");
        str = escapeJSONString(str);
        return str;
    }

    public static String prettySubStr(String str, int size) {
        if (str == null) {
            return null;
        }
        String subStr = StringUtils.substring(str, 0, size);
        if (subStr.length() < str.length()) {
            subStr += "...";
        }
        return subStr;
    }

    private static String escapeJSONString(String string) {
        if (string == null || string.length() == 0) {
            return "";
        }

        char b;
        char c = 0;
        int i;
        int len = string.length();
        StringBuilder sb = new StringBuilder(len * 2);
        String t;
        char[] chars = string.toCharArray();
        char[] buffer = new char[1030];
        int bufferIndex = 0;
        for (i = 0; i < len; i += 1) {
            if (bufferIndex > 1024) {
                sb.append(buffer, 0, bufferIndex);
                bufferIndex = 0;
            }
            b = c;
            c = chars[i];
            switch (c) {
                case '\\':
                case '"':
                    buffer[bufferIndex++] = '\\';
                    buffer[bufferIndex++] = c;
                    break;
                case '/':
                    if (b == '<') {
                        buffer[bufferIndex++] = '\\';
                    }
                    buffer[bufferIndex++] = c;
                    break;
                default:
                    if (c < ' ') {
                        switch (c) {
                            case '\b':
                                buffer[bufferIndex++] = '\\';
                                buffer[bufferIndex++] = 'b';
                                break;
                            case '\t':
                                buffer[bufferIndex++] = '\\';
                                buffer[bufferIndex++] = 't';
                                break;
                            case '\n':
                                buffer[bufferIndex++] = '\\';
                                buffer[bufferIndex++] = 'n';
                                break;
                            case '\f':
                                buffer[bufferIndex++] = '\\';
                                buffer[bufferIndex++] = 'f';
                                break;
                            case '\r':
                                buffer[bufferIndex++] = '\\';
                                buffer[bufferIndex++] = 'r';
                                break;
                            default:
                                t = "000" + Integer.toHexString(c);
                                int tLength = t.length();
                                buffer[bufferIndex++] = '\\';
                                buffer[bufferIndex++] = 'u';
                                buffer[bufferIndex++] = t.charAt(tLength - 4);
                                buffer[bufferIndex++] = t.charAt(tLength - 3);
                                buffer[bufferIndex++] = t.charAt(tLength - 2);
                                buffer[bufferIndex++] = t.charAt(tLength - 1);
                        }
                    } else {
                        buffer[bufferIndex++] = c;
                    }
            }
        }
        sb.append(buffer, 0, bufferIndex);
        return sb.toString();
    }

    /**
     * 提供给前端模板使用.按长度截取字符串,一个全角字符算两个长度
     *
     * @param content
     * @param length
     * @return
     */
    public static String sublength(String content, int length) {
        return sublength(content, length, true);
    }

    public static String REGEX = "[\\u4e00-\\u9fa5]";

    /**
     * 计算字符串长度，汉字算2个，英文1个
     *
     * @param str
     * @return
     */
    public static int charlength(String str) {
        int sum = 0;
        int i = 0;
        for (; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch >= '\u4E00' && ch <= '\u9FA5') {
                sum += 2;
            } else {
                sum += 1;
            }
        }
        return sum;
    }

    /**
     * 提供给前端模板使用.按长度截取字符串,汉字按两个字符计算<br>
     * 如果超过长度时,最后一个为汉字,则该汉字被截断
     *
     * @param content
     * @param length
     * @return
     */
    public static String sublength(String str, int length, boolean appendSuffix) {
        int sum = 0;
        int i = 0;
        if (str == null) {
            return "";
        }
        for (; i < str.length(); i++) {
            if (sum >= length) {
                break;
            }
            char ch = str.charAt(i);
            if (ch >= '\u4E00' && ch <= '\u9FA5') {
                sum += 2;
            } else {
                sum += 1;
            }
        }
        return str.substring(0, i) + (appendSuffix && (sum > length || i < str.length()) ? "..." : "");
    }

    /**
     * 提供给前端模板使用.截取给定字符串的前length个字符
     *
     * @param content
     * @param length
     * @return
     */
    public static String substr(String content, int length) {
        if (StringUtils.isBlank(content) || length <= 0) {
            return "";
        }
        if (content.length() <= length) {
            return content;
        }
        return content.substring(0, length);
    }

    // 双字节字符(包含了对汉字的匹配)
    private final static String DOUBLE_BYTE_CHAR_REGEX = "[^\\x00-\\xff]";

    /**
     * 汉字算两个字符
     *
     * @param content
     * @return
     */
    public static int length(String content) {
        if (StringUtils.isBlank(content)) {
            return 0;
        }
        int resultLength = 0;
        for (int i = 0; i < content.length(); i++) {
            char ch = content.charAt(i);
            if (String.valueOf(ch).matches(DOUBLE_BYTE_CHAR_REGEX)) {
                resultLength += 2;
            } else {
                resultLength++;
            }
        }

        return resultLength;
    }

    public static String urlEncode(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return url;
        }
    }

    public static String urlDecode(String url) {
        try {
            return URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return url;
        }
    }

    public static String subString(String str, int length) {
        length = length * 2;
        return subString(str, length, true);
    }


    /**
     * 截取字符串，双字节字符算两个长度。 截取长度处于最后一个双字节中间时，该字符不会添加到结果中。
     *
     * @param str
     * @param length
     * @param appendSuffix
     * @return
     */
    public static String subString(String str, int length, boolean appendSuffix) {
        if (str == null) {
            return "";
        }
        int sum = 0;
        int index = 0;
        int strLength = str.length();
        for (int i = 0; i < strLength; i++) {
            char ch = str.charAt(i);
            int thisLen = 0;
            if (String.valueOf(ch).matches(DOUBLE_BYTE_CHAR_REGEX)) {
                thisLen = 2;
            } else {
                thisLen = 1;
            }

            if ((sum + thisLen) > length) {
                break;
            }
            sum += thisLen;
            index = i;
            if (sum >= length) {
                break;
            }
        }
        if (index + 1 >= strLength) {// 取整个字符串
            return str;
        } else {
            return str.substring(0, index + 1) + (appendSuffix ? "..." : "");
        }
    }

    public double stringToDouble(String num) {

        return Double.parseDouble(num);
    }

    public static long cash2Integer(BigDecimal cash) {
        BigDecimal temp = BigDecimal.ZERO;
        if (cash != null && cash.signum() > 0) {
            temp = cash;
        }
        return temp.longValue();
    }

    public static String formatNumber(BigDecimal currency) {
        if (null != currency) {
            return formatNumber(currency.doubleValue());
        } else {
            return "";
        }
    }

    public static String cash2Double(BigDecimal cash) {
        BigDecimal temp = BigDecimal.ZERO;
        if (cash != null && cash.signum() > 0) {
            temp = cash;
        }
        return formatNumber(temp.doubleValue(), CASH_STYLE);
    }

    public static String formatNumber(long currency) {
        return formatNumber((double) currency);
    }

    public static String formatNumber(double numberDouble) {
        return formatNumber(Math.floor(numberDouble), STYLE);
    }

    public static String formatNumber(double numberDouble, String pattern) {
        try {
            DecimalFormat df = new DecimalFormat();
            df.applyPattern(pattern);
            return df.format(numberDouble);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return numberDouble + "";
    }

    /**
     * 把字符型数字转换成整型.
     *
     * @param str 字符型数字
     * @return int 返回整型值。如果不能转换则返回默认值defaultValue.
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

    public static double getDouble(String str, double defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static float getFloat(String str, float defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static long getLong(String str, long defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }


    /**
     * 判断一个字符串是否为数字
     */
    public static boolean isInt(String str) {
        return str.matches("\\d+");
    }

    public static String getRequestHost(String requestUrl) {
        int idx = requestUrl.indexOf("http://");
        if (idx != -1) {
            int endIdx = requestUrl.indexOf("/", idx + 7);
            if (endIdx != -1) {
                return requestUrl.substring(0, endIdx);
            }
        }
        return requestUrl;
    }

    /**
     * 判断一个字符串是否空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 替换html特殊字符
     *
     * @param str
     * @return
     */
    public static String filterHTMLTag(String str) {
        str = str.replaceAll("<br>", "\n");
        str = str.replaceAll("&amp;", "&");
        str = str.replaceAll("&lt;", "<");
        str = str.replaceAll("&gt;", ">");
        str = str.replaceAll("&apos;", "'");
        str = str.replaceAll("&quot;", "\"");
        return str;
    }

    /**
     * 判断是否可以转成long
     *
     * @param str
     * @return
     */
    public static boolean isValidLong(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int[] transferToIntArray(String[] strs) {
        if (strs == null || strs.length == 0) {
            return new int[0];
        } else {
            int[] t = new int[strs.length];
            for (int i = 0; i < strs.length; i++) {
                t[i] = Integer.parseInt(strs[i]);
            }
            return t;
        }
    }

    /**
     * 正则表达式校验
     *
     * @param str
     * @param regex
     * @return
     */
    public static boolean check(String str, String regex) {
        boolean flag = false;
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static void main(String[] args) {
        System.out.println(getDuration(21900000));
    }
}
