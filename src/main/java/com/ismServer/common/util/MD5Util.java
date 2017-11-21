package com.ismServer.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.ismServer.common.constant.ApplicationErrorCode;
import com.ismServer.common.exception.TranFailException;

/**
 * MD5工具类
 *
 * @author Terry Zhang
 * @date 2017-09-19 九月 21:30
 * @modify
 **/
public class MD5Util {

    /**
     * 字符串md5加密
     * @param sourceStr
     * @return String
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static String encodeByMD5(String sourceStr) throws TranFailException {

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            return StringUtil.byteToHex(digest.digest(sourceStr.getBytes("UTF-8")));
        } catch (Exception e) {
            throw new TranFailException(ApplicationErrorCode.SYSTEM_ERROR, "随机数加密失败");
        }
    }
}
