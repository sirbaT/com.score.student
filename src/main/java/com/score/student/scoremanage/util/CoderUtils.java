package com.score.student.scoremanage.util;

import antlr.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by yaguang.wang
 * on 2017/8/15.
 * coder utils SHA1,Base64 and MD5
 */
public class CoderUtils {
    public static final String KEY_SHA = "SHA";
    public static final String KEY_MD5 = "MD5";

    /**
     * BASE64 解码
     *
     * @param key 需要解码的字段
     * @return 解码后的字段
     * @throws IOException 解码失败
     */
    public static byte[] decryptBASE64(String key) throws IOException {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64 加密
     *
     * @param key 需要被译成密文的字符串的二进制
     * @return 翻译成的密文
     */
    public static String encryptBASE64(byte[] key) {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    /**
     * MD5 加密
     *
     * @param data 需要加密的数据
     * @return 加密后的字符串
     * @throws NoSuchAlgorithmException 未找到加密算法
     */
    public static byte[] encryptMD5(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data);
        return md5.digest();
    }

    /**
     * SHA 加密
     *
     * @param data 需要加密的数据
     * @return 加密后的字符串
     * @throws NoSuchAlgorithmException 未找到加密算法
     */
    public static byte[] encryptSHA(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance(KEY_SHA);
        md5.update(data);
        return md5.digest();
    }
}
