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
     * 随机数生成器算法。
     */
    public enum Random {
        SYSTEM(CryptoConstant.SYSTEM),

        DEFAULT(CryptoConstant.DEFAULT),

        NONCE_AND_IV(CryptoConstant.NONCE_AND_IV);

        private static final Map<String, Random> CODE_MAP = EnumMx.buildValueMap(Random.class, v -> v.algorithm);

        /**
         * 算法名称。
         */
        public final String algorithm;

        Random(String algorithm) {
            this.algorithm = algorithm;
        }

        /**
         * 算法名称转换为枚举值。
         * 
         * @param code
         *     算法名称。
         * @return 枚举值。
         */
        public static Random forCode(String code) {
            return CODE_MAP.get(code);
        }
    }

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
        DES_ECB_NOPADDING(CryptoConstant.DES, CryptoConstant.DES_ECB_NOPADDING),

        DES_ECB_ZEROBYTEPADDING(CryptoConstant.DES, CryptoConstant.DES_ECB_ZEROBYTEPADDING),

        DES_ECB_PKCS5PADDING(CryptoConstant.DES, CryptoConstant.DES_ECB_PKCS5PADDING),

        DES_CBC_NOPADDING(CryptoConstant.DES, CryptoConstant.DES_CBC_NOPADDING),

        DES_CBC_ZEROBYTEPADDING(CryptoConstant.DES, CryptoConstant.DES_CBC_ZEROBYTEPADDING),

        DES_CBC_PKCS5PADDING(CryptoConstant.DES, CryptoConstant.DES_CBC_PKCS5PADDING),

        /**
         * 密钥长度 16 或 24 字节。
         */
        DESEDE_ECB_NOPADDING(CryptoConstant.DESEDE, CryptoConstant.DESEDE_ECB_NOPADDING),

        DESEDE_ECB_ZEROBYTEPADDING(CryptoConstant.DESEDE, CryptoConstant.DESEDE_ECB_ZEROBYTEPADDING),

        DESEDE_ECB_PKCS5PADDING(CryptoConstant.DESEDE, CryptoConstant.DESEDE_ECB_PKCS5PADDING),

        DESEDE_CBC_NOPADDING(CryptoConstant.DESEDE, CryptoConstant.DESEDE_CBC_NOPADDING),

        DESEDE_CBC_ZEROBYTEPADDING(CryptoConstant.DESEDE, CryptoConstant.DESEDE_CBC_ZEROBYTEPADDING),

        DESEDE_CBC_PKCS5PADDING(CryptoConstant.DESEDE, CryptoConstant.DESEDE_CBC_PKCS5PADDING),

        /**
         * 密钥长度 0 到 32 字节。
         */
        AES_ECB_NOPADDING(CryptoConstant.AES, CryptoConstant.AES_ECB_NOPADDING),

        AES_ECB_ZEROBYTEPADDING(CryptoConstant.AES, CryptoConstant.AES_ECB_ZEROBYTEPADDING),

        AES_ECB_PKCS5PADDING(CryptoConstant.AES, CryptoConstant.AES_ECB_PKCS5PADDING),

        AES_CBC_NOPADDING(CryptoConstant.AES, CryptoConstant.AES_CBC_NOPADDING),

        AES_CBC_ZEROBYTEPADDING(CryptoConstant.AES, CryptoConstant.AES_CBC_ZEROBYTEPADDING),

        AES_CBC_PKCS5PADDING(CryptoConstant.AES, CryptoConstant.AES_CBC_PKCS5PADDING),

        /**
         * 密钥长度 16 字节。
         */
        SM4_ECB_NOPADDING(CryptoConstant.SM4, CryptoConstant.SM4_ECB_NOPADDING),

        SM4_ECB_ZEROBYTEPADDING(CryptoConstant.SM4, CryptoConstant.SM4_ECB_ZEROBYTEPADDING),

        SM4_ECB_PKCS5PADDING(CryptoConstant.SM4, CryptoConstant.SM4_ECB_PKCS5PADDING),

        SM4_CBC_NOPADDING(CryptoConstant.SM4, CryptoConstant.SM4_CBC_NOPADDING),

        SM4_CBC_ZEROBYTEPADDING(CryptoConstant.SM4, CryptoConstant.SM4_CBC_ZEROBYTEPADDING),

        SM4_CBC_PKCS5PADDING(CryptoConstant.SM4, CryptoConstant.SM4_CBC_PKCS5PADDING);

        private static final Map<String, Symmetric> CODE_MAP = EnumMx.buildValueMap(Symmetric.class, v -> v.transformation);

        /**
         * 算法名称。
         */
        public final String algorithm;

        /**
         * 算法变种名称。
         */
        public final String transformation;

        Symmetric(String algorithm, String transformation) {
            this.algorithm = algorithm;
            this.transformation = transformation;
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
     * 消息认证码算法。
     */
    public enum Mac {
        HMAC_MD5(CryptoConstant.HMAC_MD5),

        HMAC_SHA1(CryptoConstant.HMAC_SHA1),

        HMAC_SM3(CryptoConstant.HMAC_SM3);

        private static final Map<String, Mac> CODE_MAP = EnumMx.buildValueMap(Mac.class, v -> v.algorithm);

        /**
         * 算法名称。
         */
        public final String algorithm;

        Mac(String algorithm) {
            this.algorithm = algorithm;
        }

        /**
         * 算法名称转换为枚举值。
         * 
         * @param code
         *     算法名称。
         * @return 枚举值。
         */
        public static Mac forCode(String code) {
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
        RSA_NONE_PKCS1PADDING(CryptoConstant.RSA, CryptoConstant.RSA_NONE_PKCS1PADDING),

        /**
         * 密钥长度足够大。
         */
        SM2_NONE_NOPADDING(CryptoConstant.SM2, CryptoConstant.SM2_NONE_NOPADDING);

        private static final Map<String, Asymmetric> CODE_MAP = EnumMx.buildValueMap(Asymmetric.class, v -> v.transformation);

        /**
         * 算法名称。
         */
        public final String algorithm;

        /**
         * 算法变种名称。
         */
        public final String transformation;

        Asymmetric(String algorithm, String transformation) {
            this.algorithm = algorithm;
            this.transformation = transformation;
        }

        /**
         * 算法变种名称转换为枚举值。
         * 
         * @param code
         *     算法变种名称。
         * @return 枚举值。
         */
        public static Asymmetric forCode(String code) {
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
        SHA1_RSA(CryptoConstant.RSA, CryptoConstant.SHA1_RSA),

        /**
         * SM3 和 SM2。
         */
        SM3_SM2(CryptoConstant.SM2, CryptoConstant.SM3_SM2);

        private static final Map<String, Sign> CODE_MAP = EnumMx.buildValueMap(Sign.class, v -> v.signAlgorithm);

        /**
         * 算法名称。
         */
        public final String algorithm;

        /**
         * 签名算法名称。
         */
        public final String signAlgorithm;

        Sign(String algorithm, String signAlgorithm) {
            this.algorithm = algorithm;
            this.signAlgorithm = signAlgorithm;
        }

        /**
         * 签名算法名称转换为枚举值。
         * 
         * @param code
         *     签名算法名称。
         * @return 枚举值。
         */
        public static Sign forCode(String code) {
            return CODE_MAP.get(code);
        }
    }
}
