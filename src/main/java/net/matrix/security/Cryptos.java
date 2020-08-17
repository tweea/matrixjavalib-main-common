/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.security;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import net.matrix.lang.ImpossibleException;

/**
 * HMAC-SHA1 消息签名及 AES 对称加密工具。
 */
public final class Cryptos {
    /**
     * AES 算法名。
     */
    private static final String AES_NAME = "AES";

    /**
     * 带随机向量 AES 算法名。
     */
    private static final String AES_GCM_NAME = "AES/GCM/NoPadding";

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
     * 默认的 AES 算法密钥长度。
     */
    private static final int DEFAULT_AES_KEYSIZE = 128;

    /**
     * 默认随机向量长度。
     */
    private static final int DEFAULT_IVSIZE = 16;

    /**
     * 内部随机量。
     */
    private static final SecureRandom RANDOM = new SecureRandom();

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

    // -- AES funciton --//
    /**
     * 使用 AES 加密。
     * 
     * @param input
     *     明文
     * @param key
     *     符合 AES 要求的密钥
     * @return 密文
     * @throws GeneralSecurityException
     *     加密失败
     */
    public static byte[] aesEncrypt(final byte[] input, final byte[] key)
        throws GeneralSecurityException {
        return aes(input, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * 使用 AES 加密。
     * 
     * @param input
     *     明文
     * @param key
     *     符合 AES 要求的密钥
     * @param iv
     *     初始向量
     * @return 密文
     * @throws GeneralSecurityException
     *     加密失败
     */
    public static byte[] aesEncrypt(final byte[] input, final byte[] key, final byte[] iv)
        throws GeneralSecurityException {
        return aes(input, key, iv, Cipher.ENCRYPT_MODE);
    }

    /**
     * 使用 AES 解密。
     * 
     * @param input
     *     密文
     * @param key
     *     符合 AES 要求的密钥
     * @return 明文
     * @throws GeneralSecurityException
     *     解密失败
     */
    public static byte[] aesDecrypt(final byte[] input, final byte[] key)
        throws GeneralSecurityException {
        return aes(input, key, Cipher.DECRYPT_MODE);
    }

    /**
     * 使用 AES 解密。
     * 
     * @param input
     *     密文
     * @param key
     *     符合 AES 要求的密钥
     * @param iv
     *     初始向量
     * @return 明文
     * @throws GeneralSecurityException
     *     解密失败
     */
    public static byte[] aesDecrypt(final byte[] input, final byte[] key, final byte[] iv)
        throws GeneralSecurityException {
        return aes(input, key, iv, Cipher.DECRYPT_MODE);
    }

    /**
     * 使用 AES 加密或解密无编码的原始字节数组，返回无编码的字节数组结果。
     * 
     * @param input
     *     原始字节数组
     * @param key
     *     符合 AES 要求的密钥
     * @param mode
     *     Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
     * @return 密文或明文
     * @throws GeneralSecurityException
     *     加密或解密出现错误
     */
    private static byte[] aes(final byte[] input, final byte[] key, final int mode)
        throws GeneralSecurityException {
        try {
            SecretKey secretKey = new SecretKeySpec(key, AES_NAME);
            Cipher cipher = Cipher.getInstance(AES_NAME);
            cipher.init(mode, secretKey);
            return cipher.doFinal(input);
        } catch (GeneralSecurityException e) {
            throw new ImpossibleException(e);
        }
    }

    /**
     * 使用 AES 加密或解密无编码的原始字节数组，返回无编码的字节数组结果。
     * 
     * @param input
     *     原始字节数组
     * @param key
     *     符合 AES 要求的密钥
     * @param iv
     *     初始向量
     * @param mode
     *     Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
     * @return 密文或明文
     * @throws GeneralSecurityException
     *     加密或解密出现错误
     */
    private static byte[] aes(final byte[] input, final byte[] key, final byte[] iv, final int mode)
        throws GeneralSecurityException {
        try {
            SecretKey secretKey = new SecretKeySpec(key, AES_NAME);
            GCMParameterSpec ivSpec = new GCMParameterSpec(128, iv);
            Cipher cipher = Cipher.getInstance(AES_GCM_NAME);
            cipher.init(mode, secretKey, ivSpec);
            return cipher.doFinal(input);
        } catch (GeneralSecurityException e) {
            throw new ImpossibleException(e);
        }
    }

    /**
     * 生成 AES 密钥，返回字节数组，默认长度为 128 位(16 字节)。
     * 
     * @return 密钥
     */
    public static byte[] generateAesKey() {
        return generateAesKey(DEFAULT_AES_KEYSIZE);
    }

    /**
     * 生成 AES 密钥，可选长度为 128,192,256 位。
     * 
     * @param keysize
     *     密钥长度
     * @return 密钥
     */
    public static byte[] generateAesKey(final int keysize) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_NAME);
            keyGenerator.init(keysize);
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new ImpossibleException(e);
        }
    }

    /**
     * 生成随机向量，默认大小为 16 字节。
     * 
     * @return 随机向量
     */
    public static byte[] generateIV() {
        byte[] bytes = new byte[DEFAULT_IVSIZE];
        RANDOM.nextBytes(bytes);
        return bytes;
    }
}
