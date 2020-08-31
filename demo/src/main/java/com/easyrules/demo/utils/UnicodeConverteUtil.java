package com.easyrules.demo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Unicode 编码转换工具类
 * @Auth zongf
 * @Time 2019-04-28
 */
public class UnicodeConverteUtil {


    private static String unicodeParttenExp = "\\\\u[0-9a-zA-Z]{4}";

    /**
     * 判断字符串是否包含Unicode 字符
     * 如果字符串为空, 则返回false
     * @param str 字符串
     * @return true/false
     * @Auth zongf
     * @Time 2019-04-28
     */
    public static boolean isContainsUnicodeChar(String str) {

        if (null == str || str.isEmpty()) {
            return false;
        }else {
            Pattern pattern = Pattern.compile(unicodeParttenExp);
            return pattern.matcher(str).find();
        }
    }

    /**
     * 是否是unicode格式字符, 只校验字符格式
     * @param str 字符串
     * @return true/false
     * @Auth zongf
     * @Time 2019-04-28
     */
    public static boolean isUnicodeChar(String str) {
        if (isContainsUnicodeChar(str)) {
            if (str.length() == 6) {
                return true;
            }
        }
        return false;
    }

    /**
     * 字符转Unicode 编码
     * @param c 字符
     * @return String Unicode编码字符串
     * @Auth zongf
     * @Time 2019-04-28
     */
    public static String char2Unicode(char c) {

        // 如果是ASCII字符, 则直接返回
        if (c >= 0 && c <= 127) {
            return String.valueOf(c);
        }

        // 转换16 进制字符
        String s = Integer.toHexString(c);

        // 不足四位则补齐
        if (s.length() < 4) {
            s = "0000".substring(s.length(), 4) + s;
        }

        // 返回Unicode 编码字符串
        return "\\u" + s;
    }

    /**
     * 字符串转Unicode编码字符串
     * @param str 字符串
     * @return String
     * @Auth zongf
     * @Time 2019-04-28
     */
    public static String string2Unicode(String str) {

        // 字符串为null 或长度为0, 则直接返回
        if(str == null || str.length()==0) return str;

        StringBuffer unicodeSb = new StringBuffer();

        for (char aChar : str.toCharArray()) {
            unicodeSb.append(char2Unicode(aChar));
        }

        return unicodeSb.toString();
    }

    /**
     * 转换Unicode字符
     * @param unicodeChar unicode 字符
     * @return char
     * @Auth zongf
     * @Time 2019-04-28
     */
    public static char unicode2char(String unicodeChar) {

        // 如果不是Unicode 字符, 则返回字符乱码.
        if (!isUnicodeChar(unicodeChar)) return '?';

        char c = (char) Integer.parseInt(unicodeChar.substring(2), 16);

        return c;
    }

    /**
     * 转换字符串中的unicode编码
     * @param unicode
     * @return String
     * @Auth zongf
     * @Time 2019-04-28
     */
    public static String unicode2String(String unicode) {

        // 如果不包含Unicode编码, 则直接返回
        if (!isContainsUnicodeChar(unicode)) {
            return unicode;
        }

        // 创建捕获匹配表达式
        Pattern compile = Pattern.compile("(" + unicodeParttenExp + ")");
        Matcher matcher = compile.matcher(unicode);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, String.valueOf(unicode2char(matcher.group())));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}


