/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.security;

/**
 * 加密算法常量。
 */
public final class CryptoConstant {
    // 随机数生成器算法
    public static final String SYSTEM = "";

    public static final String DEFAULT = "Default";

    public static final String NONCE_AND_IV = "NonceAndIV";

    // 摘要算法
    public static final String MD5 = "MD5";

    public static final String SHA1 = "SHA1";

    public static final String SM3 = "SM3";

    // 对称加密算法
    /**
     * 密钥长度 8 字节。
     */
    public static final String DES = "DES";

    public static final String DES_ECB_NOPADDING = "DES/ECB/NoPadding";

    public static final String DES_ECB_ZEROBYTEPADDING = "DES/ECB/ZeroBytePadding";

    public static final String DES_ECB_PKCS5PADDING = "DES/ECB/Pkcs5Padding";

    public static final String DES_CBC_NOPADDING = "DES/CBC/NoPadding";

    public static final String DES_CBC_ZEROBYTEPADDING = "DES/CBC/ZeroBytePadding";

    public static final String DES_CBC_PKCS5PADDING = "DES/CBC/Pkcs5Padding";

    /**
     * 密钥长度 16 或 24 字节。
     */
    public static final String DESEDE = "DESede";

    public static final String DESEDE_ECB_NOPADDING = "DESede/ECB/NoPadding";

    public static final String DESEDE_ECB_ZEROBYTEPADDING = "DESede/ECB/ZeroBytePadding";

    public static final String DESEDE_ECB_PKCS5PADDING = "DESede/ECB/Pkcs5Padding";

    public static final String DESEDE_CBC_NOPADDING = "DESede/CBC/NoPadding";

    public static final String DESEDE_CBC_ZEROBYTEPADDING = "DESede/CBC/ZeroBytePadding";

    public static final String DESEDE_CBC_PKCS5PADDING = "DESede/CBC/Pkcs5Padding";

    /**
     * 密钥长度 0 到 32 字节。
     */
    public static final String AES = "AES";

    public static final String AES_ECB_NOPADDING = "AES/ECB/NoPadding";

    public static final String AES_ECB_ZEROBYTEPADDING = "AES/ECB/ZeroBytePadding";

    public static final String AES_ECB_PKCS5PADDING = "AES/ECB/Pkcs5Padding";

    public static final String AES_CBC_NOPADDING = "AES/CBC/NoPadding";

    public static final String AES_CBC_ZEROBYTEPADDING = "AES/CBC/ZeroBytePadding";

    public static final String AES_CBC_PKCS5PADDING = "AES/CBC/Pkcs5Padding";

    /**
     * 密钥长度 16 字节。
     */
    public static final String SM4 = "SM4";

    public static final String SM4_ECB_NOPADDING = "SM4/ECB/NoPadding";

    public static final String SM4_ECB_ZEROBYTEPADDING = "SM4/ECB/ZeroBytePadding";

    public static final String SM4_ECB_PKCS5PADDING = "SM4/ECB/Pkcs5Padding";

    public static final String SM4_CBC_NOPADDING = "SM4/CBC/NoPadding";

    public static final String SM4_CBC_ZEROBYTEPADDING = "SM4/CBC/ZeroBytePadding";

    public static final String SM4_CBC_PKCS5PADDING = "SM4/CBC/Pkcs5Padding";

    // 非对称加密算法
    /**
     * 密钥长度足够大。
     */
    public static final String RSA = "RSA";

    public static final String RSA_NONE_PKCS1PADDING = "RSA/NONE/Pkcs1Padding";

    /**
     * 密钥长度足够大。
     */
    public static final String SM2 = "EC";

    public static final String SM2_NONE_NOPADDING = "SM2/NONE/NoPadding";

    // 签名算法
    public static final String SHA1_RSA = "SHA1withRSA";

    public static final String SM3_SM2 = "SM3withSM2";

    /**
     * 阻止实例化。
     */
    private CryptoConstant() {
    }
}
