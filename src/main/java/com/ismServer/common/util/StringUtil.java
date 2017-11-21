package com.ismServer.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Random;

/**
 * 字符串工具类
 *
 * @author Terry Zhang
 * @date 2017-09-19 九月 21:31
 * @modify
 **/
public class StringUtil {

    /**
     * byte转16位字符串
     * @param hash
     * @return String
     */
    public static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public static int compare(String s1, String s2) {
        char[] arr1 = s1.toCharArray(), arr2 = s2.toCharArray();
        int index = 0, len1 = arr1.length, len2 = arr2.length;
        int len = len1 < len2 ? len1 : len2;
        while (index < len) {
            char c1 = arr1[index], c2 = arr2[index];
            char c11 = (char) (c1 >= 'a' ? c1 - ('a' - 'A') : c1);
            char c21 = (char) (c2 >= 'a' ? c2 - ('a' - 'A') : c2);
            if (c11 == c21) {
                if (c1 != c2) {
                    return c1 - c2;
                }
            } else {
                return c11 - c21;
            }
            index++;
        }
        if (len1 == len2) {
            return 0;
        } else if (len1 > len2) {
            return arr1[len];
        } else {
            return -arr2[len];
        }
    }

    public static void sort(String[] src) {
        int[][] arr = new int[src.length][2];
        int count = 0, index = 0;
        arr[0][0] = 0;
        arr[0][1] = src.length - 1;
        while (index <= count) {
            int left = arr[index][0];
            int right = arr[index][1];
            int[] dir = { 0, 1 };
            int i = left, j = right;
            String temp;
            while (i < j) {
                if (compare(src[i], src[j]) > 0) {
                    temp = src[i];
                    src[i] = src[j];
                    src[j] = temp;
                    dir[0] = 1 - dir[0];
                    dir[1] = 1 - dir[1];
                }
                i += dir[0];
                j -= dir[1];
            }
            if (j - left > 1) {
                count++;
                arr[count][0] = left;
                arr[count][1] = j - 1;
            }
            if (right - i > 1) {
                count++;
                arr[count][0] = i + 1;
                arr[count][1] = right;
            }
            index++;
        }
    }

    public static final String numberChar = "0123456789";

    /**
     *  返回一个定长的随机字符串(只包含大小写字母、数字)
     * @param length  随机字符串长度
     * @return 随机字符串
     */
    public static String generateNumberString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
        }
        return sb.toString();
    }
    
    public static String generateOrderNo() {
    	StringBuffer sb = new StringBuffer();
    	sb.append(DateUtil.getCurrentDateTimeWithoutSign());
    	sb.append(generateNumberString(10));
    	
    	return sb.toString();
    }
}
