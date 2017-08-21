package com.score.student.scoremanage.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * Created by yaguang.wang
 * on 2017/8/15.
 * create RSA PublicKey and PrivateKey use for  encrypt pwd
 * <p>
 * RSA 算法的一次加密长度为 密钥长度的/8 -11 ，在本类中为1024/8 - 11 = 117bit 数据量很少
 */
public class RSAUtils {
    public static final String KEY_ALGORTHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA"; //签名算法

    public static final String PUBLIC_KEY = "RSAPublicKey";
    public static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 初始化密钥
     *
     * @return 公钥 和 私钥对
     * @throws NoSuchAlgorithmException 没有找到密钥算法
     */
    public static Map<String, Object> initKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORTHM);
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keys = new HashMap<>(2);
        keys.put(PUBLIC_KEY, publicKey);
        keys.put(PRIVATE_KEY, privateKey);
        return keys;
    }

    /**
     * 取得公钥，并转化为String类型
     *
     * @param keyMap 密钥对
     * @return 公钥
     */
    public static String getPublicKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return CoderUtils.encryptBASE64(key.getEncoded());
    }

    /**
     * 取得私钥字符串
     *
     * @param keyMap 密钥对
     * @return 私钥
     */
    public static String getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return CoderUtils.encryptBASE64(key.getEncoded());
    }

    /**
     * 用私钥加密
     *
     * @param data 加密数据
     * @param key  密钥
     * @return 加密后的密文
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        // 翻译bace64 字符串
        byte[] keyBytes = CoderUtils.decryptBASE64(key);
        // 取私钥
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> keys = initKey();

        System.out.println(Arrays.toString(encryptByPrivateKey("ABC".getBytes(), getPrivateKey(keys))));
    }
}