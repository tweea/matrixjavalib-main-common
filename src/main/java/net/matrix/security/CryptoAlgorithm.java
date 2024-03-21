/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.security;

import java.util.Map;

import net.matrix.java.lang.EnumMx;

/**
 * 加密算法枚举。
 */
public class CryptoAlgorithm {
    /**
     * 摘要算法。
     */
    public enum Digest {
        MD5(CryptoConstant.MD5),

        SHA1(CryptoConstant.SHA1),

        SM3(CryptoConstant.SM3);

        private static final Map<String, Digest> CODE_MAP = EnumMx.buildValueMap(Digest.class, v -> v.algorithm);

        /**
         * 算法名称。
         */
        public final String algorithm;

        Digest(String algorithm) {
            this.algorithm = algorithm;
        }

        /**
         * 算法名称转换为枚举值。
         * 
         * @param code
         *     算法名称。
         * @return 枚举值。
         */
        public static Digest forCode(String code) {
            return CODE_MAP.get(code);
        }
    }

    /**
     * 对称加密算法。
     */
    public enum Symmetric {
        /**
         * 密钥长度 8 字节。
         */
        DES_ECB_NOPADDING(CryptoConstant.DES, CryptoConstant.DES_ECB_NOPADDING, 64),

        DES_ECB_ZEROBYTEPADDING(CryptoConstant.DES, CryptoConstant.DES_ECB_ZEROBYTEPADDING, 64),

        DES_ECB_PKCS5PADDING(CryptoConstant.DES, CryptoConstant.DES_ECB_PKCS5PADDING, 64),

        /**
         * 密钥长度 16 或 24 字节。
         */
        DESEDE_ECB_NOPADDING(CryptoConstant.DESEDE, CryptoConstant.DESEDE_ECB_NOPADDING, 128),

        DESEDE_ECB_ZEROBYTEPADDING(CryptoConstant.DESEDE, CryptoConstant.DESEDE_ECB_ZEROBYTEPADDING, 128),

        DESEDE_ECB_PKCS5PADDING(CryptoConstant.DESEDE, CryptoConstant.DESEDE_ECB_PKCS5PADDING, 128),

        /**
         * 密钥长度 0 到 32 字节。
         */
        AES_ECB_NOPADDING(CryptoConstant.AES, CryptoConstant.AES_ECB_NOPADDING, 128),

        AES_ECB_ZEROBYTEPADDING(CryptoConstant.AES, CryptoConstant.AES_ECB_ZEROBYTEPADDING, 128),

        AES_ECB_PKCS5PADDING(CryptoConstant.AES, CryptoConstant.AES_ECB_PKCS5PADDING, 128),

        /**
         * 密钥长度 16 字节。
         */
        SM4_ECB_NOPADDING(CryptoConstant.SM4, CryptoConstant.SM4_ECB_NOPADDING, 128),

        SM4_ECB_ZEROBYTEPADDING(CryptoConstant.SM4, CryptoConstant.SM4_ECB_ZEROBYTEPADDING, 128),

        SM4_ECB_PKCS5PADDING(CryptoConstant.SM4, CryptoConstant.SM4_ECB_PKCS5PADDING, 128);

        private static final Map<String, Symmetric> CODE_MAP = EnumMx.buildValueMap(Symmetric.class, v -> v.transformation);

        /**
         * 算法名称。
         */
        public final String algorithm;

        /**
         * 算法变种名称。
         */
        public final String transformation;

        /**
         * 默认秘钥长度。
         */
        public final int defaultKeySize;

        Symmetric(String algorithm, String transformation, int defaultKeySize) {
            this.algorithm = algorithm;
            this.transformation = transformation;
            this.defaultKeySize = defaultKeySize;
        }

        /**
         * 算法变种名称转换为枚举值。
         * 
         * @param code
         *     算法变种名称。
         * @return 枚举值。
         */
        public static Symmetric forCode(String code) {
            return CODE_MAP.get(code);
        }
    }

    /**
     * 非对称加密算法。
     */
    public enum Asymmetric {
        /**
         * 密钥长度足够大。
         */
        RSA_NONE_PKCS1PADDING(CryptoConstant.RSA, CryptoConstant.RSA_NONE_PKCS1PADDING, 2048),

        /**
         * 密钥长度足够大，不支持私钥加密。
         */
        SM2_NONE_NOPADDING(CryptoConstant.SM2, CryptoConstant.SM2_NONE_NOPADDING, 192);

        private static final Map<String, Asymmetric> CODE_MAP = EnumMx.buildValueMap(Asymmetric.class, v -> v.transformation);

        /**
         * 算法名称。
         */
        public final String algorithm;

        /**
         * 算法变种名称。
         */
        public final String transformation;

        /**
         * 默认秘钥长度。
         */
        public final int defaultKeySize;

        Asymmetric(String algorithm, String transformation, int defaultKeySize) {
            this.algorithm = algorithm;
            this.transformation = transformation;
            this.defaultKeySize = defaultKeySize;
        }

        /**
         * 算法变种名称转换为枚举值。
         * 
         * @param code
         *     算法变种名称。
         * @return 枚举值。
         */
        public static Asymmetric forCode(final String code) {
            return CODE_MAP.get(code);
        }
    }

    /**
     * 签名算法。
     */
    public enum Sign {
        /**
         * SHA1 和 RSA。
         */
        SHA1_RSA(CryptoConstant.RSA, CryptoConstant.SHA1_RSA, 2048),

        /**
         * SM3 和 SM2。
         */
        SM3_SM2(CryptoConstant.SM2, CryptoConstant.SM3_SM2, 192);

        private static final Map<String, Sign> CODE_MAP = EnumMx.buildValueMap(Sign.class, v -> v.signAlgorithm);

        /**
         * 算法名称。
         */
        public final String algorithm;

        /**
         * 签名算法名称。
         */
        public final String signAlgorithm;

        /**
         * 默认秘钥长度。
         */
        public final int defaultKeySize;

        Sign(String algorithm, String signAlgorithm, int defaultKeySize) {
            this.algorithm = algorithm;
            this.signAlgorithm = signAlgorithm;
            this.defaultKeySize = defaultKeySize;
        }

        /**
         * 签名算法名称转换为枚举值。
         * 
         * @param code
         *     签名算法名称。
         * @return 枚举值。
         */
        public static Sign forCode(final String code) {
            return CODE_MAP.get(code);
        }
    }
}
