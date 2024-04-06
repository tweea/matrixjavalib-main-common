/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.security;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import net.matrix.lang.ImpossibleException;

/**
 * HMAC-SHA1 消息签名及 AES 对称加密工具。
 */
public final class Cryptos {
    /**
     * HMAC-SHA1 算法名。
     */
    private static final String HMACSHA1_NAME = "HmacSHA1";

    // RFC2401
    /**
     * 默认的HMAC-SHA1 密钥长度。
     */
    private static final int DEFAULT_HMACSHA1_KEYSIZE = 160;

    /**
     * 阻止实例化。
     */
    private Cryptos() {
    }

    // -- HMAC-SHA1 funciton --//
    /**
     * 使用 HMAC-SHA1 进行消息签名，返回字节数组,长度为 20 字节。
     * 
     * @param input
     *     原始输入字符数组
     * @param key
     *     HMAC-SHA1 密钥
     * @return 签名
     * @throws GeneralSecurityException
     *     签名失败
     */
    public static byte[] hmacSha1(final byte[] input, final byte[] key)
        throws GeneralSecurityException {
        try {
            SecretKey secretKey = new SecretKeySpec(key, HMACSHA1_NAME);
            Mac mac = Mac.getInstance(HMACSHA1_NAME);
            mac.init(secretKey);
            return mac.doFinal(input);
        } catch (NoSuchAlgorithmException e) {
            throw new ImpossibleException(e);
        }
    }

    /**
     * 校验 HMAC-SHA1 签名是否正确。
     * 
     * @param expected
     *     已存在的签名
     * @param input
     *     原始输入字符串
     * @param key
     *     密钥
     * @return 签名是否正确
     * @throws GeneralSecurityException
     *     签名失败
     */
    public static boolean isMacValid(final byte[] expected, final byte[] input, final byte[] key)
        throws GeneralSecurityException {
        byte[] actual = hmacSha1(input, key);
        return Arrays.equals(expected, actual);
    }

    /**
     * 生成 HMAC-SHA1 密钥，返回字节数组，长度为 160 位(20字节)。
     * HMAC-SHA1 算法对密钥无特殊要求，RFC2401 建议最少长度为 160 位(20字节)。
     * 
     * @return 密钥
     */
    public static byte[] generateHmacSha1Key() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(HMACSHA1_NAME);
            keyGenerator.init(DEFAULT_HMACSHA1_KEYSIZE);
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new ImpossibleException(e);
        }
    }
}
