/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.security;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;

/**
 * 加密算法工具。
 */
@ThreadSafe
public final class CryptoMx {
    /**
     * 随机数生成器。
     */
    private static final SecureRandom SYSTEM_SECURE_RANDOM = new SecureRandom();

    /**
     * 加密算法实现。
     */
    private static final Provider PROVIDER = new BouncyCastleProvider();

    /**
     * 阻止实例化。
     */
    private CryptoMx() {
    }

    // 随机数生成器算法
    /**
     * 获取随机数生成器算法实例。
     *
     * @param algorithm
     *     算法名称。
     * @return 随机数生成器算法实例。
     */
    @Nonnull
    public static SecureRandom getSecureRandom(@Nonnull String algorithm) {
        if (CryptoAlgorithm.Random.forCode(algorithm) == CryptoAlgorithm.Random.SYSTEM) {
            return SYSTEM_SECURE_RANDOM;
        }
        try {
            return SecureRandom.getInstance(algorithm, PROVIDER);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 获取随机数生成器算法实例。
     *
     * @param algorithm
     *     算法枚举值。
     * @return 随机数生成器算法实例。
     */
    @Nonnull
    public static SecureRandom getSecureRandom(@Nonnull CryptoAlgorithm.Random algorithm) {
        return getSecureRandom(algorithm.algorithm);
    }

    /**
     * 生成随机数。
     *
     * @param randomData
     *     随机数。
     * @param secureRandom
     *     随机数生成器算法实例。
     */
    public static void generateRandom(@Nonnull byte[] randomData, @Nonnull SecureRandom secureRandom) {
        secureRandom.nextBytes(randomData);
    }

    /**
     * 生成随机数。
     *
     * @param length
     *     随机数长度。
     * @param secureRandom
     *     随机数生成器算法实例。
     * @return 随机数。
     */
    @Nonnull
    public static byte[] generateRandom(int length, @Nonnull SecureRandom secureRandom) {
        byte[] randomData = new byte[length];
        generateRandom(randomData, secureRandom);
        return randomData;
    }

    // 摘要算法
    /**
     * 获取摘要算法实例。
     *
     * @param algorithm
     *     算法名称。
     * @return 摘要算法实例。
     */
    @Nonnull
    public static MessageDigest getMessageDigest(@Nonnull String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm, PROVIDER);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 获取摘要算法实例。
     *
     * @param algorithm
     *     算法枚举值。
     * @return 摘要算法实例。
     */
    @Nonnull
    public static MessageDigest getMessageDigest(@Nonnull CryptoAlgorithm.Digest algorithm) {
        return getMessageDigest(algorithm.algorithm);
    }

    /**
     * 更新摘要。
     *
     * @param plainData
     *     明文。
     * @param digest
     *     摘要算法实例。
     */
    public static void updateDigest(@Nonnull byte[] plainData, @Nonnull MessageDigest digest) {
        digest.update(plainData);
    }

    /**
     * 计算摘要。
     *
     * @param plainData
     *     明文。
     * @param digest
     *     摘要算法实例。
     * @return 摘要。
     */
    @Nonnull
    public static byte[] digest(@Nonnull byte[] plainData, @Nonnull MessageDigest digest) {
        updateDigest(plainData, digest);
        return digest.digest();
    }

    /**
     * 计算摘要。
     *
     * @param plainData
     *     明文。
     * @param saltData
     *     盐。
     * @param digest
     *     摘要算法实例。
     * @return 摘要。
     */
    @Nonnull
    public static byte[] digest(@Nonnull byte[] plainData, @Nonnull byte[] saltData, @Nonnull MessageDigest digest) {
        updateDigest(saltData, digest);
        updateDigest(plainData, digest);
        return digest.digest();
    }

    /**
     * 更新摘要。
     *
     * @param plainData
     *     明文。
     * @param digest
     *     摘要算法实例。
     * @throws IOException
     *     读取明文失败。
     */
    public static void updateDigest(@Nonnull InputStream plainData, @Nonnull MessageDigest digest)
        throws IOException {
        byte[] input = new byte[8 * 1024];
        int readLength = -1;
        while ((readLength = IOUtils.read(plainData, input)) > 0) {
            digest.update(input, 0, readLength);
        }
    }

    /**
     * 计算摘要。
     *
     * @param plainData
     *     明文。
     * @param digest
     *     摘要算法实例。
     * @return 摘要。
     * @throws IOException
     *     读取明文失败。
     */
    @Nonnull
    public static byte[] digest(@Nonnull InputStream plainData, @Nonnull MessageDigest digest)
        throws IOException {
        updateDigest(plainData, digest);
        return digest.digest();
    }

    /**
     * 计算摘要。
     *
     * @param plainData
     *     明文。
     * @param saltData
     *     盐。
     * @param digest
     *     摘要算法实例。
     * @return 摘要。
     * @throws IOException
     *     读取明文失败。
     */
    @Nonnull
    public static byte[] digest(@Nonnull InputStream plainData, @Nonnull byte[] saltData, @Nonnull MessageDigest digest)
        throws IOException {
        updateDigest(saltData, digest);
        updateDigest(plainData, digest);
        return digest.digest();
    }

    // 加密算法
    /**
     * 获取加密算法参数生成器实例。
     *
     * @param algorithm
     *     算法名称。
     * @return 加密算法参数生成器实例。
     */
    @Nonnull
    public static AlgorithmParameterGenerator getAlgorithmParameterGenerator(@Nonnull String algorithm) {
        try {
            return AlgorithmParameterGenerator.getInstance(algorithm, PROVIDER);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 初始化加密算法参数生成器实例。
     *
     * @param algorithmParameterGenerator
     *     加密算法参数生成器实例。
     * @param size
     *     参数长度。
     */
    public static void initAlgorithmParameterGenerator(@Nonnull AlgorithmParameterGenerator algorithmParameterGenerator, int size) {
        algorithmParameterGenerator.init(size);
    }

    /**
     * 初始化加密算法参数生成器实例。
     *
     * @param algorithmParameterGenerator
     *     加密算法参数生成器实例。
     * @param size
     *     参数长度。
     * @param secureRandom
     *     随机数生成器算法实例。
     */
    public static void initAlgorithmParameterGenerator(@Nonnull AlgorithmParameterGenerator algorithmParameterGenerator, int size,
        @Nonnull SecureRandom secureRandom) {
        algorithmParameterGenerator.init(size, secureRandom);
    }

    /**
     * 生成加密算法参数实例。
     *
     * @param algorithmParameterGenerator
     *     加密算法参数生成器实例。
     * @return 加密算法参数实例。
     */
    @Nonnull
    public static AlgorithmParameters generateAlgorithmParameter(@Nonnull AlgorithmParameterGenerator algorithmParameterGenerator) {
        return algorithmParameterGenerator.generateParameters();
    }

    /**
     * 获取加密算法参数实例。
     *
     * @param algorithm
     *     算法名称。
     * @return 加密算法参数实例。
     */
    @Nonnull
    public static AlgorithmParameters getAlgorithmParameter(@Nonnull String algorithm) {
        try {
            return AlgorithmParameters.getInstance(algorithm, PROVIDER);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 初始化加密算法参数实例。
     *
     * @param algorithmParameter
     *     加密算法参数实例。
     * @param paramData
     *     参数。
     */
    public static void initAlgorithmParameter(@Nonnull AlgorithmParameters algorithmParameter, @Nonnull byte[] paramData) {
        try {
            algorithmParameter.init(paramData);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 获取加密算法参数。
     *
     * @param algorithmParameter
     *     加密算法参数实例。
     * @return
     *     参数。
     */
    @Nonnull
    public static byte[] encodeAlgorithmParameter(@Nonnull AlgorithmParameters algorithmParameter) {
        try {
            return algorithmParameter.getEncoded();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 获取加密算法实例。
     *
     * @param transformation
     *     算法变种名称。
     * @return 加密算法实例。
     */
    @Nonnull
    public static Cipher getCipher(@Nonnull String transformation) {
        try {
            return Cipher.getInstance(transformation, PROVIDER);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 初始化加密算法实例加密秘钥。
     *
     * @param cipher
     *     加密算法实例。
     * @param key
     *     秘钥。
     */
    public static void initCipherForEncrypt(@Nonnull Cipher cipher, @Nonnull Key key) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 初始化加密算法实例加密秘钥。
     *
     * @param cipher
     *     加密算法实例。
     * @param key
     *     秘钥。
     * @param algorithmParameter
     *     加密算法参数。
     */
    public static void initCipherForEncrypt(@Nonnull Cipher cipher, @Nonnull Key key, @Nonnull AlgorithmParameters algorithmParameter) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, algorithmParameter);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 初始化加密算法实例加密秘钥。
     *
     * @param cipher
     *     加密算法实例。
     * @param key
     *     秘钥。
     * @param secureRandom
     *     随机数生成器算法实例。
     */
    public static void initCipherForEncrypt(@Nonnull Cipher cipher, @Nonnull Key key, @Nonnull SecureRandom secureRandom) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, secureRandom);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 初始化加密算法实例加密秘钥。
     *
     * @param cipher
     *     加密算法实例。
     * @param key
     *     秘钥。
     * @param algorithmParameter
     *     加密算法参数。
     * @param secureRandom
     *     随机数生成器算法实例。
     */
    public static void initCipherForEncrypt(@Nonnull Cipher cipher, @Nonnull Key key, @Nonnull AlgorithmParameters algorithmParameter,
        @Nonnull SecureRandom secureRandom) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, algorithmParameter, secureRandom);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 初始化加密算法实例解密秘钥。
     *
     * @param cipher
     *     加密算法实例。
     * @param key
     *     秘钥。
     */
    public static void initCipherForDecrypt(@Nonnull Cipher cipher, @Nonnull Key key) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 初始化加密算法实例解密秘钥。
     *
     * @param cipher
     *     加密算法实例。
     * @param key
     *     秘钥。
     * @param algorithmParameter
     *     加密算法参数。
     */
    public static void initCipherForDecrypt(@Nonnull Cipher cipher, @Nonnull Key key, @Nonnull AlgorithmParameters algorithmParameter) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, algorithmParameter);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 初始化加密算法实例解密秘钥。
     *
     * @param cipher
     *     加密算法实例。
     * @param key
     *     秘钥。
     * @param secureRandom
     *     随机数生成器算法实例。
     */
    public static void initCipherForDecrypt(@Nonnull Cipher cipher, @Nonnull Key key, @Nonnull SecureRandom secureRandom) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, secureRandom);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 初始化加密算法实例解密秘钥。
     *
     * @param cipher
     *     加密算法实例。
     * @param key
     *     秘钥。
     * @param algorithmParameter
     *     加密算法参数。
     * @param secureRandom
     *     随机数生成器算法实例。
     */
    public static void initCipherForDecrypt(@Nonnull Cipher cipher, @Nonnull Key key, @Nonnull AlgorithmParameters algorithmParameter,
        @Nonnull SecureRandom secureRandom) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, algorithmParameter, secureRandom);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 加密。
     *
     * @param plainData
     *     明文。
     * @param cipher
     *     加密算法实例。
     * @return 密文。
     */
    @Nonnull
    public static byte[] encrypt(@Nonnull byte[] plainData, @Nonnull Cipher cipher) {
        try {
            return cipher.doFinal(plainData);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 加密。
     *
     * @param plainData
     *     明文。
     * @param cipherData
     *     密文。
     * @param cipher
     *     加密算法实例。
     * @throws IOException
     *     读取明文失败。
     */
    public static void encrypt(@Nonnull InputStream plainData, @Nonnull OutputStream cipherData, @Nonnull Cipher cipher)
        throws IOException {
        try {
            byte[] input = new byte[8 * 1024];
            byte[] output = new byte[8 * 1024];
            int readLength = -1;
            while ((readLength = IOUtils.read(plainData, input)) > 0) {
                int outputLength = cipher.update(input, 0, readLength, output);
                if (outputLength > 0) {
                    cipherData.write(output, 0, outputLength);
                }
            }
            int outputLength = cipher.doFinal(output, 0);
            if (outputLength > 0) {
                cipherData.write(output, 0, outputLength);
            }
        } catch (IllegalBlockSizeException | BadPaddingException | ShortBufferException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 解密。
     *
     * @param cipherData
     *     密文。
     * @param cipher
     *     加密算法实例。
     * @return 明文。
     */
    @Nonnull
    public static byte[] decrypt(@Nonnull byte[] cipherData, @Nonnull Cipher cipher) {
        try {
            return cipher.doFinal(cipherData);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 解密。
     *
     * @param cipherData
     *     密文。
     * @param plainData
     *     明文。
     * @param cipher
     *     加密算法实例。
     * @throws IOException
     *     读取密文失败。
     */
    public static void decrypt(@Nonnull InputStream cipherData, @Nonnull OutputStream plainData, @Nonnull Cipher cipher)
        throws IOException {
        try {
            byte[] input = new byte[8 * 1024];
            byte[] output = new byte[8 * 1024];
            int readLength = -1;
            while ((readLength = IOUtils.read(cipherData, input)) > 0) {
                int outputLength = cipher.update(input, 0, readLength, output);
                if (outputLength > 0) {
                    plainData.write(output, 0, outputLength);
                }
            }
            int outputLength = cipher.doFinal(output, 0);
            if (outputLength > 0) {
                plainData.write(output, 0, outputLength);
            }
        } catch (IllegalBlockSizeException | BadPaddingException | ShortBufferException e) {
            throw new IllegalArgumentException(e);
        }
    }

    // 对称加密算法
    /**
     * 获取加密算法参数生成器实例。
     *
     * @param algorithm
     *     算法枚举值。
     * @return 加密算法参数生成器实例。
     */
    @Nonnull
    public static AlgorithmParameterGenerator getAlgorithmParameterGenerator(@Nonnull CryptoAlgorithm.Symmetric algorithm) {
        return getAlgorithmParameterGenerator(algorithm.algorithm);
    }

    /**
     * 对称加密算法参数生成器实例构建器。
     */
    public static class SymmetricAlgorithmParameterGeneratorBuilder {
        /**
         * 加密算法参数生成器实例。
         */
        private AlgorithmParameterGenerator algorithmParameterGenerator;

        /**
         * 参数长度。
         */
        private int size;

        /**
         * 随机数生成器算法实例。
         */
        private SecureRandom secureRandom;

        /**
         * 阻止实例化。
         */
        private SymmetricAlgorithmParameterGeneratorBuilder() {
        }

        /**
         * 构造加密算法参数生成器实例构建器。
         *
         * @param algorithm
         *     算法名称。
         * @return 加密算法参数生成器实例构建器。
         */
        @Nonnull
        public static SymmetricAlgorithmParameterGeneratorBuilder newBuilder(@Nonnull String algorithm) {
            SymmetricAlgorithmParameterGeneratorBuilder builder = new SymmetricAlgorithmParameterGeneratorBuilder();
            builder.algorithmParameterGenerator = getAlgorithmParameterGenerator(algorithm);
            return builder;
        }

        /**
         * 构造加密算法参数生成器实例构建器。
         *
         * @param algorithm
         *     算法枚举值。
         * @return 加密算法参数生成器实例构建器。
         */
        @Nonnull
        public static SymmetricAlgorithmParameterGeneratorBuilder newBuilder(@Nonnull CryptoAlgorithm.Symmetric algorithm) {
            SymmetricAlgorithmParameterGeneratorBuilder builder = new SymmetricAlgorithmParameterGeneratorBuilder();
            builder.algorithmParameterGenerator = getAlgorithmParameterGenerator(algorithm);
            return builder;
        }

        /**
         * 设置参数长度。
         *
         * @param size
         *     参数长度。
         * @return 加密算法参数生成器实例构建器。
         */
        @Nonnull
        public SymmetricAlgorithmParameterGeneratorBuilder setSize(int size) {
            this.size = size;
            return this;
        }

        /**
         * 设置随机数生成器算法实例。
         *
         * @param algorithm
         *     算法名称。
         * @return 加密算法参数生成器实例构建器。
         */
        @Nonnull
        public SymmetricAlgorithmParameterGeneratorBuilder setSecureRandom(@Nonnull String algorithm) {
            this.secureRandom = getSecureRandom(algorithm);
            return this;
        }

        /**
         * 设置随机数生成器算法实例。
         *
         * @param algorithm
         *     算法枚举值。
         * @return 加密算法参数生成器实例构建器。
         */
        @Nonnull
        public SymmetricAlgorithmParameterGeneratorBuilder setSecureRandom(@Nonnull CryptoAlgorithm.Random algorithm) {
            this.secureRandom = getSecureRandom(algorithm);
            return this;
        }

        /**
         * 设置随机数生成器算法实例。
         *
         * @param secureRandom
         *     随机数生成器算法实例。
         * @return 加密算法参数生成器实例构建器。
         */
        @Nonnull
        public SymmetricAlgorithmParameterGeneratorBuilder setSecureRandom(@Nonnull SecureRandom secureRandom) {
            this.secureRandom = secureRandom;
            return this;
        }

        /**
         * 构建加密算法参数生成器实例。
         *
         * @return 加密算法参数生成器实例。
         */
        @Nonnull
        public AlgorithmParameterGenerator build() {
            if (size == 0) {
                if (secureRandom != null) {
                    throw new IllegalArgumentException();
                }
            } else {
                if (secureRandom == null) {
                    initAlgorithmParameterGenerator(algorithmParameterGenerator, size);
                } else {
                    initAlgorithmParameterGenerator(algorithmParameterGenerator, size, secureRandom);
                }
            }
            return algorithmParameterGenerator;
        }
    }

    /**
     * 获取加密算法参数实例。
     *
     * @param algorithm
     *     算法枚举值。
     * @return 加密算法参数实例。
     */
    @Nonnull
    public static AlgorithmParameters getAlgorithmParameter(@Nonnull CryptoAlgorithm.Symmetric algorithm) {
        return getAlgorithmParameter(algorithm.algorithm);
    }

    /**
     * 对称加密算法参数实例构建器。
     */
    public static class SymmetricAlgorithmParameterBuilder {
        /**
         * 加密算法参数实例。
         */
        private AlgorithmParameters algorithmParameter;

        /**
         * 参数。
         */
        private byte[] paramData;

        /**
         * 阻止实例化。
         */
        private SymmetricAlgorithmParameterBuilder() {
        }

        /**
         * 构造加密算法参数实例构建器。
         *
         * @param algorithm
         *     算法名称。
         * @return 加密算法参数实例构建器。
         */
        @Nonnull
        public static SymmetricAlgorithmParameterBuilder newBuilder(@Nonnull String algorithm) {
            SymmetricAlgorithmParameterBuilder builder = new SymmetricAlgorithmParameterBuilder();
            builder.algorithmParameter = getAlgorithmParameter(algorithm);
            return builder;
        }

        /**
         * 构造加密算法参数实例构建器。
         *
         * @param algorithm
         *     算法枚举值。
         * @return 加密算法参数实例构建器。
         */
        @Nonnull
        public static SymmetricAlgorithmParameterBuilder newBuilder(@Nonnull CryptoAlgorithm.Symmetric algorithm) {
            SymmetricAlgorithmParameterBuilder builder = new SymmetricAlgorithmParameterBuilder();
            builder.algorithmParameter = getAlgorithmParameter(algorithm);
            return builder;
        }

        /**
         * 设置参数。
         *
         * @param paramData
         *     参数。
         * @return 加密算法参数实例构建器。
         */
        @Nonnull
        public SymmetricAlgorithmParameterBuilder setParam(@Nonnull byte[] paramData) {
            this.paramData = paramData;
            return this;
        }

        /**
         * 构建加密算法参数实例。
         *
         * @return 加密算法参数实例。
         */
        @Nonnull
        public AlgorithmParameters build() {
            initAlgorithmParameter(algorithmParameter, paramData);
            return algorithmParameter;
        }
    }

    /**
     * 获取对称加密算法秘钥生成器实例。
     *
     * @param algorithm
     *     算法名称。
     * @return 秘钥生成器实例。
     */
    @Nonnull
    public static KeyGenerator getKeyGenerator(@Nonnull String algorithm) {
        try {
            return KeyGenerator.getInstance(algorithm, PROVIDER);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 获取对称加密算法秘钥生成器实例。
     *
     * @param algorithm
     *     算法枚举值。
     * @return 秘钥生成器实例。
     */
    @Nonnull
    public static KeyGenerator getKeyGenerator(@Nonnull CryptoAlgorithm.Symmetric algorithm) {
        return getKeyGenerator(algorithm.algorithm);
    }

    /**
     * 初始化秘钥生成器实例。
     *
     * @param keyGenerator
     *     秘钥生成器实例。
     * @param keySize
     *     秘钥长度。
     */
    public static void initKeyGenerator(@Nonnull KeyGenerator keyGenerator, int keySize) {
        keyGenerator.init(keySize);
    }

    /**
     * 初始化秘钥生成器实例。
     *
     * @param keyGenerator
     *     秘钥生成器实例。
     * @param secureRandom
     *     随机数生成器算法实例。
     */
    public static void initKeyGenerator(@Nonnull KeyGenerator keyGenerator, @Nonnull SecureRandom secureRandom) {
        keyGenerator.init(secureRandom);
    }

    /**
     * 初始化秘钥生成器实例。
     *
     * @param keyGenerator
     *     秘钥生成器实例。
     * @param keySize
     *     秘钥长度。
     * @param secureRandom
     *     随机数生成器算法实例。
     */
    public static void initKeyGenerator(@Nonnull KeyGenerator keyGenerator, int keySize, @Nonnull SecureRandom secureRandom) {
        keyGenerator.init(keySize, secureRandom);
    }

    /**
     * 秘钥生成器实例构建器。
     */
    public static class SymmetricKeyGeneratorBuilder {
        /**
         * 秘钥生成器实例。
         */
        private KeyGenerator keyGenerator;

        /**
         * 秘钥长度。
         */
        private int keySize;

        /**
         * 随机数生成器算法实例。
         */
        private SecureRandom secureRandom;

        /**
         * 阻止实例化。
         */
        private SymmetricKeyGeneratorBuilder() {
        }

        /**
         * 构造秘钥生成器实例构建器。
         *
         * @param algorithm
         *     算法名称。
         * @return 秘钥生成器实例构建器。
         */
        @Nonnull
        public static SymmetricKeyGeneratorBuilder newBuilder(@Nonnull String algorithm) {
            SymmetricKeyGeneratorBuilder builder = new SymmetricKeyGeneratorBuilder();
            builder.keyGenerator = getKeyGenerator(algorithm);
            return builder;
        }

        /**
         * 构造秘钥生成器实例构建器。
         *
         * @param algorithm
         *     算法枚举值。
         * @return 秘钥生成器实例构建器。
         */
        @Nonnull
        public static SymmetricKeyGeneratorBuilder newBuilder(@Nonnull CryptoAlgorithm.Symmetric algorithm) {
            SymmetricKeyGeneratorBuilder builder = new SymmetricKeyGeneratorBuilder();
            builder.keyGenerator = getKeyGenerator(algorithm);
            return builder;
        }

        /**
         * 设置秘钥长度。
         *
         * @param keySize
         *     秘钥长度。
         * @return 秘钥生成器实例构建器。
         */
        @Nonnull
        public SymmetricKeyGeneratorBuilder setKeySize(int keySize) {
            this.keySize = keySize;
            return this;
        }

        /**
         * 设置随机数生成器算法实例。
         *
         * @param algorithm
         *     算法名称。
         * @return 秘钥生成器实例构建器。
         */
        @Nonnull
        public SymmetricKeyGeneratorBuilder setSecureRandom(@Nonnull String algorithm) {
            this.secureRandom = getSecureRandom(algorithm);
            return this;
        }

        /**
         * 设置随机数生成器算法实例。
         *
         * @param algorithm
         *     算法枚举值。
         * @return 秘钥生成器实例构建器。
         */
        @Nonnull
        public SymmetricKeyGeneratorBuilder setSecureRandom(@Nonnull CryptoAlgorithm.Random algorithm) {
            this.secureRandom = getSecureRandom(algorithm);
            return this;
        }

        /**
         * 设置随机数生成器算法实例。
         *
         * @param secureRandom
         *     随机数生成器算法实例。
         * @return 秘钥生成器实例构建器。
         */
        @Nonnull
        public SymmetricKeyGeneratorBuilder setSecureRandom(@Nonnull SecureRandom secureRandom) {
            this.secureRandom = secureRandom;
            return this;
        }

        /**
         * 构建秘钥生成器实例。
         *
         * @return 秘钥生成器实例。
         */
        @Nonnull
        public KeyGenerator build() {
            if (keySize == 0) {
                if (secureRandom != null) {
                    initKeyGenerator(keyGenerator, secureRandom);
                }
            } else {
                if (secureRandom == null) {
                    initKeyGenerator(keyGenerator, keySize);
                } else {
                    initKeyGenerator(keyGenerator, keySize, secureRandom);
                }
            }
            return keyGenerator;
        }
    }

    /**
     * 生成对称加密算法秘钥实例。
     *
     * @param keyGenerator
     *     秘钥生成器实例。
     * @return 秘钥实例。
     */
    @Nonnull
    public static SecretKey generateSecretKey(@Nonnull KeyGenerator keyGenerator) {
        return keyGenerator.generateKey();
    }

    /**
     * 获取对称加密算法秘钥实例。
     *
     * @param keyData
     *     秘钥。
     * @param algorithm
     *     算法名称。
     * @return 秘钥实例。
     */
    @Nonnull
    public static SecretKey getSecretKey(@Nonnull byte[] keyData, @Nonnull String algorithm) {
        return new SecretKeySpec(keyData, algorithm);
    }

    /**
     * 获取对称加密算法秘钥实例。
     *
     * @param keyData
     *     秘钥。
     * @param algorithm
     *     算法枚举值。
     * @return 秘钥实例。
     */
    @Nonnull
    public static SecretKey getSecretKey(@Nonnull byte[] keyData, @Nonnull CryptoAlgorithm.Symmetric algorithm) {
        return getSecretKey(keyData, algorithm.algorithm);
    }

    /**
     * 获取对称加密算法实例。
     *
     * @param algorithm
     *     算法枚举值。
     * @return 加密算法实例。
     */
    @Nonnull
    public static Cipher getCipher(@Nonnull CryptoAlgorithm.Symmetric algorithm) {
        return getCipher(algorithm.transformation);
    }

    /**
     * 对称加密算法实例构建器。
     */
    public static class SymmetricCipherBuilder {
        /**
         * 加密算法实例。
         */
        private Cipher cipher;

        /**
         * 秘钥。
         */
        private Key key;

        /**
         * 加密算法参数。
         */
        private AlgorithmParameters algorithmParameter;

        /**
         * 随机数生成器算法实例。
         */
        private SecureRandom secureRandom;

        /**
         * 阻止实例化。
         */
        private SymmetricCipherBuilder() {
        }

        /**
         * 构造对称加密算法实例构建器。
         *
         * @param transformation
         *     算法变种名称。
         * @return 对称加密算法实例构建器。
         */
        @Nonnull
        public static SymmetricCipherBuilder newBuilder(@Nonnull String transformation) {
            SymmetricCipherBuilder builder = new SymmetricCipherBuilder();
            builder.cipher = getCipher(transformation);
            return builder;
        }

        /**
         * 构造对称加密算法实例构建器。
         *
         * @param algorithm
         *     算法枚举值。
         * @return 对称加密算法实例构建器。
         */
        @Nonnull
        public static SymmetricCipherBuilder newBuilder(@Nonnull CryptoAlgorithm.Symmetric algorithm) {
            SymmetricCipherBuilder builder = new SymmetricCipherBuilder();
            builder.cipher = getCipher(algorithm);
            return builder;
        }

        /**
         * 设置秘钥。
         *
         * @param keyData
         *     秘钥。
         * @param algorithm
         *     算法名称。
         * @return 对称加密算法实例构建器。
         */
        @Nonnull
        public SymmetricCipherBuilder setKey(@Nonnull byte[] keyData, @Nonnull String algorithm) {
            this.key = getSecretKey(keyData, algorithm);
            return this;
        }

        /**
         * 设置秘钥。
         *
         * @param keyData
         *     秘钥。
         * @param algorithm
         *     算法枚举值。
         * @return 对称加密算法实例构建器。
         */
        @Nonnull
        public SymmetricCipherBuilder setKey(@Nonnull byte[] keyData, @Nonnull CryptoAlgorithm.Symmetric algorithm) {
            this.key = getSecretKey(keyData, algorithm);
            return this;
        }

        /**
         * 设置秘钥。
         *
         * @param key
         *     秘钥。
         * @return 对称加密算法实例构建器。
         */
        @Nonnull
        public SymmetricCipherBuilder setKey(@Nonnull SecretKey key) {
            this.key = key;
            return this;
        }

        /**
         * 设置加密算法参数。
         *
         * @param algorithmParameter
         *     加密算法参数。
         * @return 对称加密算法实例构建器。
         */
        @Nonnull
        public SymmetricCipherBuilder setAlgorithmParameter(@Nonnull AlgorithmParameters algorithmParameter) {
            this.algorithmParameter = algorithmParameter;
            return this;
        }

        /**
         * 设置随机数生成器算法实例。
         *
         * @param algorithm
         *     算法名称。
         * @return 对称加密算法实例构建器。
         */
        @Nonnull
        public SymmetricCipherBuilder setSecureRandom(@Nonnull String algorithm) {
            this.secureRandom = getSecureRandom(algorithm);
            return this;
        }

        /**
         * 设置随机数生成器算法实例。
         *
         * @param algorithm
         *     算法枚举值。
         * @return 对称加密算法实例构建器。
         */
        @Nonnull
        public SymmetricCipherBuilder setSecureRandom(@Nonnull CryptoAlgorithm.Random algorithm) {
            this.secureRandom = getSecureRandom(algorithm);
            return this;
        }

        /**
         * 设置随机数生成器算法实例。
         *
         * @param secureRandom
         *     随机数生成器算法实例。
         * @return 对称加密算法实例构建器。
         */
        @Nonnull
        public SymmetricCipherBuilder setSecureRandom(@Nonnull SecureRandom secureRandom) {
            this.secureRandom = secureRandom;
            return this;
        }

        /**
         * 构建对称加密算法实例。
         *
         * @return 对称加密算法实例。
         */
        @Nonnull
        public Cipher buildForEncrypt() {
            if (algorithmParameter == null) {
                if (secureRandom == null) {
                    initCipherForEncrypt(cipher, key);
                } else {
                    initCipherForEncrypt(cipher, key, secureRandom);
                }
            } else {
                if (secureRandom == null) {
                    initCipherForEncrypt(cipher, key, algorithmParameter);
                } else {
                    initCipherForEncrypt(cipher, key, algorithmParameter, secureRandom);
                }
            }
            return cipher;
        }

        /**
         * 构建对称加密算法实例。
         *
         * @return 对称加密算法实例。
         */
        @Nonnull
        public Cipher buildForDecrypt() {
            if (algorithmParameter == null) {
                if (secureRandom == null) {
                    initCipherForDecrypt(cipher, key);
                } else {
                    initCipherForDecrypt(cipher, key, secureRandom);
                }
            } else {
                if (secureRandom == null) {
                    initCipherForDecrypt(cipher, key, algorithmParameter);
                } else {
                    initCipherForDecrypt(cipher, key, algorithmParameter, secureRandom);
                }
            }
            return cipher;
        }
    }

    // 消息认证码算法
    /**
     * 获取对称加密算法秘钥生成器实例。
     *
     * @param algorithm
     *     算法枚举值。
     * @return 秘钥生成器实例。
     */
    @Nonnull
    public static KeyGenerator getKeyGenerator(@Nonnull CryptoAlgorithm.Mac algorithm) {
        return getKeyGenerator(algorithm.algorithm);
    }

    /**
     * 秘钥生成器实例构建器。
     */
    public static class MacKeyGeneratorBuilder {
        /**
         * 秘钥生成器实例。
         */
        private KeyGenerator keyGenerator;

        /**
         * 秘钥长度。
         */
        private int keySize;

        /**
         * 随机数生成器算法实例。
         */
        private SecureRandom secureRandom;

        /**
         * 阻止实例化。
         */
        private MacKeyGeneratorBuilder() {
        }

        /**
         * 构造秘钥生成器实例构建器。
         *
         * @param algorithm
         *     算法名称。
         * @return 秘钥生成器实例构建器。
         */
        @Nonnull
        public static MacKeyGeneratorBuilder newBuilder(@Nonnull String algorithm) {
            MacKeyGeneratorBuilder builder = new MacKeyGeneratorBuilder();
            builder.keyGenerator = getKeyGenerator(algorithm);
            return builder;
        }

        /**
         * 构造秘钥生成器实例构建器。
         *
         * @param algorithm
         *     算法枚举值。
         * @return 秘钥生成器实例构建器。
         */
        @Nonnull
        public static MacKeyGeneratorBuilder newBuilder(@Nonnull CryptoAlgorithm.Mac algorithm) {
            MacKeyGeneratorBuilder builder = new MacKeyGeneratorBuilder();
            builder.keyGenerator = getKeyGenerator(algorithm);
            return builder;
        }

        /**
         * 设置秘钥长度。
         *
         * @param keySize
         *     秘钥长度。
         * @return 秘钥生成器实例构建器。
         */
        @Nonnull
        public MacKeyGeneratorBuilder setKeySize(int keySize) {
            this.keySize = keySize;
            return this;
        }

        /**
         * 设置随机数生成器算法实例。
         *
         * @param algorithm
         *     算法名称。
         * @return 秘钥生成器实例构建器。
         */
        @Nonnull
        public MacKeyGeneratorBuilder setSecureRandom(@Nonnull String algorithm) {
            this.secureRandom = getSecureRandom(algorithm);
            return this;
        }

        /**
         * 设置随机数生成器算法实例。
         *
         * @param algorithm
         *     算法枚举值。
         * @return 秘钥生成器实例构建器。
         */
        @Nonnull
        public MacKeyGeneratorBuilder setSecureRandom(@Nonnull CryptoAlgorithm.Random algorithm) {
            this.secureRandom = getSecureRandom(algorithm);
            return this;
        }

        /**
         * 设置随机数生成器算法实例。
         *
         * @param secureRandom
         *     随机数生成器算法实例。
         * @return 秘钥生成器实例构建器。
         */
        @Nonnull
        public MacKeyGeneratorBuilder setSecureRandom(@Nonnull SecureRandom secureRandom) {
            this.secureRandom = secureRandom;
            return this;
        }

        /**
         * 构建秘钥生成器实例。
         *
         * @return 秘钥生成器实例。
         */
        @Nonnull
        public KeyGenerator build() {
            if (keySize == 0) {
                if (secureRandom != null) {
                    initKeyGenerator(keyGenerator, secureRandom);
                }
            } else {
                if (secureRandom == null) {
                    initKeyGenerator(keyGenerator, keySize);
                } else {
                    initKeyGenerator(keyGenerator, keySize, secureRandom);
                }
            }
            return keyGenerator;
        }
    }

    /**
     * 获取对称加密算法秘钥实例。
     *
     * @param keyData
     *     秘钥。
     * @param algorithm
     *     算法枚举值。
     * @return 秘钥实例。
     */
    @Nonnull
    public static SecretKey getSecretKey(@Nonnull byte[] keyData, @Nonnull CryptoAlgorithm.Mac algorithm) {
        return getSecretKey(keyData, algorithm.algorithm);
    }

    /**
     * 获取消息认证码算法实例。
     *
     * @param algorithm
     *     算法名称。
     * @return 消息认证码算法实例。
     */
    @Nonnull
    public static Mac getMac(@Nonnull String algorithm) {
        try {
            return Mac.getInstance(algorithm, PROVIDER);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 获取消息认证码算法实例。
     *
     * @param algorithm
     *     算法枚举值。
     * @return 消息认证码算法实例。
     */
    @Nonnull
    public static Mac getMac(@Nonnull CryptoAlgorithm.Mac algorithm) {
        return getMac(algorithm.algorithm);
    }

    /**
     * 初始化消息认证码算法实例秘钥。
     *
     * @param mac
     *     消息认证码算法实例。
     * @param key
     *     秘钥。
     */
    public static void initMac(@Nonnull Mac mac, @Nonnull Key key) {
        try {
            mac.init(key);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 消息认证码算法实例构建器。
     */
    public static class MacBuilder {
        /**
         * 消息认证码算法实例。
         */
        private Mac mac;

        /**
         * 秘钥。
         */
        private Key key;

        /**
         * 阻止实例化。
         */
        private MacBuilder() {
        }

        /**
         * 构造消息认证码算法实例构建器。
         *
         * @param algorithm
         *     算法名称。
         * @return 消息认证码算法实例构建器。
         */
        @Nonnull
        public static MacBuilder newBuilder(@Nonnull String algorithm) {
            MacBuilder builder = new MacBuilder();
            builder.mac = getMac(algorithm);
            return builder;
        }

        /**
         * 构造消息认证码算法实例构建器。
         *
         * @param algorithm
         *     算法枚举值。
         * @return 消息认证码算法实例构建器。
         */
        @Nonnull
        public static MacBuilder newBuilder(@Nonnull CryptoAlgorithm.Mac algorithm) {
            MacBuilder builder = new MacBuilder();
            builder.mac = getMac(algorithm);
            return builder;
        }

        /**
         * 设置秘钥。
         *
         * @param keyData
         *     秘钥。
         * @param algorithm
         *     算法名称。
         * @return 消息认证码算法实例构建器。
         */
        @Nonnull
        public MacBuilder setKey(@Nonnull byte[] keyData, @Nonnull String algorithm) {
            this.key = getSecretKey(keyData, algorithm);
            return this;
        }

        /**
         * 设置秘钥。
         *
         * @param keyData
         *     秘钥。
         * @param algorithm
         *     算法枚举值。
         * @return 消息认证码算法实例构建器。
         */
        @Nonnull
        public MacBuilder setKey(@Nonnull byte[] keyData, @Nonnull CryptoAlgorithm.Mac algorithm) {
            this.key = getSecretKey(keyData, algorithm);
            return this;
        }

        /**
         * 设置秘钥。
         *
         * @param key
         *     秘钥。
         * @return 消息认证码算法实例构建器。
         */
        @Nonnull
        public MacBuilder setKey(@Nonnull SecretKey key) {
            this.key = key;
            return this;
        }

        /**
         * 构建消息认证码算法实例。
         *
         * @return 消息认证码算法实例。
         */
        @Nonnull
        public Mac build() {
            initMac(mac, key);
            return mac;
        }
    }

    /**
     * 生成消息认证码。
     *
     * @param plainData
     *     数据。
     * @param mac
     *     消息认证码算法实例。
     * @return 消息认证码。
     */
    @Nonnull
    public static byte[] sign(@Nonnull byte[] plainData, @Nonnull Mac mac) {
        mac.update(plainData);
        return mac.doFinal();
    }

    /**
     * 生成消息认证码。
     *
     * @param plainData
     *     数据。
     * @param mac
     *     消息认证码算法实例。
     * @return 消息认证码。
     * @throws IOException
     *     读取数据失败。
     */
    @Nonnull
    public static byte[] sign(@Nonnull InputStream plainData, @Nonnull Mac mac)
        throws IOException {
        byte[] input = new byte[8 * 1024];
        int readLength = -1;
        while ((readLength = IOUtils.read(plainData, input)) > 0) {
            mac.update(input, 0, readLength);
        }
        return mac.doFinal();
    }

    /**
     * 验证消息认证码。
     *
     * @param plainData
     *     数据。
     * @param signData
     *     消息认证码。
     * @param mac
     *     消息认证码算法实例。
     * @return 是否通过验证。
     */
    public static boolean verify(@Nonnull byte[] plainData, @Nonnull byte[] signData, @Nonnull Mac mac) {
        byte[] actualSignData = sign(plainData, mac);
        return Arrays.constantTimeAreEqual(actualSignData, signData);
    }

    /**
     * 验证消息认证码。
     *
     * @param plainData
     *     数据。
     * @param signData
     *     消息认证码。
     * @param mac
     *     消息认证码算法实例。
     * @return 是否通过验证。
     * @throws IOException
     *     读取数据失败。
     */
    public static boolean verify(@Nonnull InputStream plainData, @Nonnull byte[] signData, @Nonnull Mac mac)
        throws IOException {
        byte[] actualSignData = sign(plainData, mac);
        return Arrays.constantTimeAreEqual(actualSignData, signData);
    }

    // 非对称加密算法
    /**
     * 获取非对称加密算法秘钥对生成器实例。
     *
     * @param algorithm
     *     算法名称。
     * @return 秘钥对生成器实例。
     */
    @Nonnull
    public static KeyPairGenerator getKeyPairGenerator(@Nonnull String algorithm) {
        try {
            return KeyPairGenerator.getInstance(algorithm, PROVIDER);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 获取非对称加密算法秘钥对生成器实例。
     *
     * @param algorithm
     *     算法枚举值。
     * @return 秘钥对生成器实例。
     */
    @Nonnull
    public static KeyPairGenerator getKeyPairGenerator(@Nonnull CryptoAlgorithm.Asymmetric algorithm) {
        return getKeyPairGenerator(algorithm.algorithm);
    }

    /**
     * 初始化秘钥对生成器实例。
     *
     * @param keyPairGenerator
     *     秘钥对生成器实例。
     * @param keySize
     *     秘钥长度。
     */
    public static void initKeyPairGenerator(@Nonnull KeyPairGenerator keyPairGenerator, int keySize) {
        keyPairGenerator.initialize(keySize);
    }

    /**
     * 初始化秘钥对生成器实例。
     *
     * @param keyPairGenerator
     *     秘钥对生成器实例。
     * @param keySize
     *     秘钥长度。
     * @param secureRandom
     *     随机数生成器算法实例。
     */
    public static void initKeyPairGenerator(@Nonnull KeyPairGenerator keyPairGenerator, int keySize, @Nonnull SecureRandom secureRandom) {
        keyPairGenerator.initialize(keySize, secureRandom);
    }

    /**
     * 秘钥对生成器实例构建器。
     */
    public static class AsymmetricKeyPairGeneratorBuilder {
        /**
         * 秘钥对生成器实例。
         */
        private KeyPairGenerator keyPairGenerator;

        /**
         * 秘钥长度。
         */
        private int keySize;

        /**
         * 随机数生成器算法实例。
         */
        private SecureRandom secureRandom;

        /**
         * 阻止实例化。
         */
        private AsymmetricKeyPairGeneratorBuilder() {
        }

        /**
         * 构造秘钥对生成器实例构建器。
         *
         * @param algorithm
         *     算法名称。
         * @return 秘钥对生成器实例构建器。
         */
        @Nonnull
        public static AsymmetricKeyPairGeneratorBuilder newBuilder(@Nonnull String algorithm) {
            AsymmetricKeyPairGeneratorBuilder builder = new AsymmetricKeyPairGeneratorBuilder();
            builder.keyPairGenerator = getKeyPairGenerator(algorithm);
            return builder;
        }

        /**
         * 构造秘钥对生成器实例构建器。
         *
         * @param algorithm
         *     算法枚举值。
         * @return 秘钥对生成器实例构建器。
         */
        @Nonnull
        public static AsymmetricKeyPairGeneratorBuilder newBuilder(@Nonnull CryptoAlgorithm.Asymmetric algorithm) {
            AsymmetricKeyPairGeneratorBuilder builder = new AsymmetricKeyPairGeneratorBuilder();
            builder.keyPairGenerator = getKeyPairGenerator(algorithm);
            return builder;
        }

        /**
         * 设置秘钥长度。
         *
         * @param keySize
         *     秘钥长度。
         * @return 秘钥对生成器实例构建器。
         */
        @Nonnull
        public AsymmetricKeyPairGeneratorBuilder setKeySize(int keySize) {
            this.keySize = keySize;
            return this;
        }

        /**
         * 设置随机数生成器算法实例。
         *
         * @param algorithm
         *     算法名称。
         * @return 秘钥对生成器实例构建器。
         */
        @Nonnull
        public AsymmetricKeyPairGeneratorBuilder setSecureRandom(@Nonnull String algorithm) {
            this.secureRandom = getSecureRandom(algorithm);
            return this;
        }

        /**
         * 设置随机数生成器算法实例。
         *
         * @param algorithm
         *     算法枚举值。
         * @return 秘钥对生成器实例构建器。
         */
        @Nonnull
        public AsymmetricKeyPairGeneratorBuilder setSecureRandom(@Nonnull CryptoAlgorithm.Random algorithm) {
            this.secureRandom = getSecureRandom(algorithm);
            return this;
        }

        /**
         * 设置随机数生成器算法实例。
         *
         * @param secureRandom
         *     随机数生成器算法实例。
         * @return 秘钥对生成器实例构建器。
         */
        @Nonnull
        public AsymmetricKeyPairGeneratorBuilder setSecureRandom(@Nonnull SecureRandom secureRandom) {
            this.secureRandom = secureRandom;
            return this;
        }

        /**
         * 构建秘钥对生成器实例。
         *
         * @return 秘钥对生成器实例。
         */
        @Nonnull
        public KeyPairGenerator build() {
            if (keySize == 0) {
                if (secureRandom != null) {
                    throw new IllegalArgumentException();
                }
            } else {
                if (secureRandom == null) {
                    initKeyPairGenerator(keyPairGenerator, keySize);
                } else {
                    initKeyPairGenerator(keyPairGenerator, keySize, secureRandom);
                }
            }
            return keyPairGenerator;
        }
    }

    /**
     * 生成非对称加密算法秘钥对实例。
     *
     * @param keyPairGenerator
     *     秘钥对生成器实例。
     * @return 秘钥对实例。
     */
    @Nonnull
    public static KeyPair generateKeyPair(@Nonnull KeyPairGenerator keyPairGenerator) {
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 获取非对称加密算法秘钥工厂实例。
     *
     * @param algorithm
     *     算法名称。
     * @return 秘钥工厂实例。
     */
    @Nonnull
    public static KeyFactory getKeyFactory(@Nonnull String algorithm) {
        try {
            return KeyFactory.getInstance(algorithm, PROVIDER);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 获取非对称加密算法秘钥工厂实例。
     *
     * @param algorithm
     *     算法枚举值。
     * @return 秘钥工厂实例。
     */
    @Nonnull
    public static KeyFactory getKeyFactory(@Nonnull CryptoAlgorithm.Asymmetric algorithm) {
        return getKeyFactory(algorithm.algorithm);
    }

    /**
     * 获取非对称加密算法私钥实例。
     *
     * @param keyData
     *     私钥。
     * @param algorithm
     *     算法名称。
     * @return 私钥实例。
     */
    @Nonnull
    public static PrivateKey getPrivateKey(@Nonnull byte[] keyData, @Nonnull String algorithm) {
        KeyFactory keyFactory = getKeyFactory(algorithm);
        KeySpec keySpec = new PKCS8EncodedKeySpec(keyData);
        try {
            return keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 获取非对称加密算法私钥实例。
     *
     * @param keyData
     *     私钥。
     * @param algorithm
     *     算法枚举值。
     * @return 私钥实例。
     */
    @Nonnull
    public static PrivateKey getPrivateKey(@Nonnull byte[] keyData, @Nonnull CryptoAlgorithm.Asymmetric algorithm) {
        KeyFactory keyFactory = getKeyFactory(algorithm);
        KeySpec keySpec = new PKCS8EncodedKeySpec(keyData);
        try {
            return keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 获取非对称加密算法公钥实例。
     *
     * @param keyData
     *     公钥。
     * @param algorithm
     *     算法名称。
     * @return 公钥实例。
     */
    @Nonnull
    public static PublicKey getPublicKey(@Nonnull byte[] keyData, @Nonnull String algorithm) {
        KeyFactory keyFactory = getKeyFactory(algorithm);
        KeySpec keySpec = new X509EncodedKeySpec(keyData);
        try {
            return keyFactory.generatePublic(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 获取非对称加密算法公钥实例。
     *
     * @param keyData
     *     公钥。
     * @param algorithm
     *     算法枚举值。
     * @return 公钥实例。
     */
    @Nonnull
    public static PublicKey getPublicKey(@Nonnull byte[] keyData, @Nonnull CryptoAlgorithm.Asymmetric algorithm) {
        KeyFactory keyFactory = getKeyFactory(algorithm);
        KeySpec keySpec = new X509EncodedKeySpec(keyData);
        try {
            return keyFactory.generatePublic(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 获取非对称加密算法实例。
     *
     * @param algorithm
     *     算法枚举值。
     * @return 加密算法实例。
     */
    @Nonnull
    public static Cipher getCipher(@Nonnull CryptoAlgorithm.Asymmetric algorithm) {
        return getCipher(algorithm.transformation);
    }

    /**
     * 非对称加密算法实例构建器。
     */
    public static class AsymmetricCipherBuilder {
        /**
         * 加密算法实例。
         */
        private Cipher cipher;

        /**
         * 秘钥。
         */
        private Key key;

        /**
         * 随机数生成器算法实例。
         */
        private SecureRandom secureRandom;

        /**
         * 阻止实例化。
         */
        private AsymmetricCipherBuilder() {
        }

        /**
         * 构造非对称加密算法实例构建器。
         *
         * @param transformation
         *     算法变种名称。
         * @return 非对称加密算法实例构建器。
         */
        @Nonnull
        public static AsymmetricCipherBuilder newBuilder(@Nonnull String transformation) {
            AsymmetricCipherBuilder builder = new AsymmetricCipherBuilder();
            builder.cipher = getCipher(transformation);
            return builder;
        }

        /**
         * 构造非对称加密算法实例构建器。
         *
         * @param algorithm
         *     算法枚举值。
         * @return 非对称加密算法实例构建器。
         */
        @Nonnull
        public static AsymmetricCipherBuilder newBuilder(@Nonnull CryptoAlgorithm.Asymmetric algorithm) {
            AsymmetricCipherBuilder builder = new AsymmetricCipherBuilder();
            builder.cipher = getCipher(algorithm);
            return builder;
        }

        /**
         * 设置私钥。
         *
         * @param keyData
         *     私钥。
         * @param algorithm
         *     算法名称。
         * @return 非对称加密算法实例构建器。
         */
        @Nonnull
        public AsymmetricCipherBuilder setPrivateKey(@Nonnull byte[] keyData, @Nonnull String algorithm) {
            this.key = getPrivateKey(keyData, algorithm);
            return this;
        }

        /**
         * 设置私钥。
         *
         * @param keyData
         *     私钥。
         * @param algorithm
         *     算法枚举值。
         * @return 非对称加密算法实例构建器。
         */
        @Nonnull
        public AsymmetricCipherBuilder setPrivateKey(@Nonnull byte[] keyData, @Nonnull CryptoAlgorithm.Asymmetric algorithm) {
            this.key = getPrivateKey(keyData, algorithm);
            return this;
        }

        /**
         * 设置私钥。
         *
         * @param key
         *     私钥。
         * @return 非对称加密算法实例构建器。
         */
        @Nonnull
        public AsymmetricCipherBuilder setPrivateKey(@Nonnull PrivateKey key) {
            this.key = key;
            return this;
        }

        /**
         * 设置公钥。
         *
         * @param keyData
         *     公钥。
         * @param algorithm
         *     算法名称。
         * @return 非对称加密算法实例构建器。
         */
        @Nonnull
        public AsymmetricCipherBuilder setPublicKey(@Nonnull byte[] keyData, @Nonnull String algorithm) {
            this.key = getPublicKey(keyData, algorithm);
            return this;
        }

        /**
         * 设置公钥。
         *
         * @param keyData
         *     公钥。
         * @param algorithm
         *     算法枚举值。
         * @return 非对称加密算法实例构建器。
         */
        @Nonnull
        public AsymmetricCipherBuilder setPublicKey(@Nonnull byte[] keyData, @Nonnull CryptoAlgorithm.Asymmetric algorithm) {
            this.key = getPublicKey(keyData, algorithm);
            return this;
        }

        /**
         * 设置公钥。
         *
         * @param key
         *     公钥。
         * @return 非对称加密算法实例构建器。
         */
        @Nonnull
        public AsymmetricCipherBuilder setPublicKey(@Nonnull PublicKey key) {
            this.key = key;
            return this;
        }

        /**
         * 设置随机数生成器算法实例。
         *
         * @param algorithm
         *     算法名称。
         * @return 非对称加密算法实例构建器。
         */
        @Nonnull
        public AsymmetricCipherBuilder setSecureRandom(@Nonnull String algorithm) {
            this.secureRandom = getSecureRandom(algorithm);
            return this;
        }

        /**
         * 设置随机数生成器算法实例。
         *
         * @param algorithm
         *     算法枚举值。
         * @return 非对称加密算法实例构建器。
         */
        @Nonnull
        public AsymmetricCipherBuilder setSecureRandom(@Nonnull CryptoAlgorithm.Random algorithm) {
            this.secureRandom = getSecureRandom(algorithm);
            return this;
        }

        /**
         * 设置随机数生成器算法实例。
         *
         * @param secureRandom
         *     随机数生成器算法实例。
         * @return 非对称加密算法实例构建器。
         */
        @Nonnull
        public AsymmetricCipherBuilder setSecureRandom(@Nonnull SecureRandom secureRandom) {
            this.secureRandom = secureRandom;
            return this;
        }

        /**
         * 构建非对称加密算法实例。
         *
         * @return 非对称加密算法实例。
         */
        @Nonnull
        public Cipher buildForEncrypt() {
            if (secureRandom == null) {
                initCipherForEncrypt(cipher, key);
            } else {
                initCipherForEncrypt(cipher, key, secureRandom);
            }
            return cipher;
        }

        /**
         * 构建非对称加密算法实例。
         *
         * @return 非对称加密算法实例。
         */
        @Nonnull
        public Cipher buildForDecrypt() {
            if (secureRandom == null) {
                initCipherForDecrypt(cipher, key);
            } else {
                initCipherForDecrypt(cipher, key, secureRandom);
            }
            return cipher;
        }
    }

    // 签名算法
    /**
     * 获取非对称加密算法秘钥对生成器实例。
     *
     * @param algorithm
     *     算法枚举值。
     * @return 秘钥对生成器实例。
     */
    @Nonnull
    public static KeyPairGenerator getKeyPairGenerator(@Nonnull CryptoAlgorithm.Sign algorithm) {
        return getKeyPairGenerator(algorithm.algorithm);
    }

    /**
     * 秘钥对生成器实例构建器。
     */
    public static class SignKeyPairGeneratorBuilder {
        /**
         * 秘钥对生成器实例。
         */
        private KeyPairGenerator keyPairGenerator;

        /**
         * 秘钥长度。
         */
        private int keySize;

        /**
         * 随机数生成器算法实例。
         */
        private SecureRandom secureRandom;

        /**
         * 阻止实例化。
         */
        private SignKeyPairGeneratorBuilder() {
        }

        /**
         * 构造秘钥对生成器实例构建器。
         *
         * @param algorithm
         *     算法名称。
         * @return 秘钥对生成器实例构建器。
         */
        @Nonnull
        public static SignKeyPairGeneratorBuilder newBuilder(@Nonnull String algorithm) {
            SignKeyPairGeneratorBuilder builder = new SignKeyPairGeneratorBuilder();
            builder.keyPairGenerator = getKeyPairGenerator(algorithm);
            return builder;
        }

        /**
         * 构造秘钥对生成器实例构建器。
         *
         * @param algorithm
         *     算法枚举值。
         * @return 秘钥对生成器实例构建器。
         */
        @Nonnull
        public static SignKeyPairGeneratorBuilder newBuilder(@Nonnull CryptoAlgorithm.Sign algorithm) {
            SignKeyPairGeneratorBuilder builder = new SignKeyPairGeneratorBuilder();
            builder.keyPairGenerator = getKeyPairGenerator(algorithm);
            return builder;
        }

        /**
         * 设置秘钥长度。
         *
         * @param keySize
         *     秘钥长度。
         * @return 秘钥对生成器实例构建器。
         */
        @Nonnull
        public SignKeyPairGeneratorBuilder setKeySize(int keySize) {
            this.keySize = keySize;
            return this;
        }

        /**
         * 设置随机数生成器算法实例。
         *
         * @param algorithm
         *     算法名称。
         * @return 秘钥对生成器实例构建器。
         */
        @Nonnull
        public SignKeyPairGeneratorBuilder setSecureRandom(@Nonnull String algorithm) {
            this.secureRandom = getSecureRandom(algorithm);
            return this;
        }

        /**
         * 设置随机数生成器算法实例。
         *
         * @param algorithm
         *     算法枚举值。
         * @return 秘钥对生成器实例构建器。
         */
        @Nonnull
        public SignKeyPairGeneratorBuilder setSecureRandom(@Nonnull CryptoAlgorithm.Random algorithm) {
            this.secureRandom = getSecureRandom(algorithm);
            return this;
        }

        /**
         * 设置随机数生成器算法实例。
         *
         * @param secureRandom
         *     随机数生成器算法实例。
         * @return 秘钥对生成器实例构建器。
         */
        @Nonnull
        public SignKeyPairGeneratorBuilder setSecureRandom(@Nonnull SecureRandom secureRandom) {
            this.secureRandom = secureRandom;
            return this;
        }

        /**
         * 构建秘钥对生成器实例。
         *
         * @return 秘钥对生成器实例。
         */
        @Nonnull
        public KeyPairGenerator build() {
            if (keySize == 0) {
                if (secureRandom != null) {
                    throw new IllegalArgumentException();
                }
            } else {
                if (secureRandom == null) {
                    initKeyPairGenerator(keyPairGenerator, keySize);
                } else {
                    initKeyPairGenerator(keyPairGenerator, keySize, secureRandom);
                }
            }
            return keyPairGenerator;
        }
    }

    /**
     * 获取非对称加密算法秘钥工厂实例。
     *
     * @param algorithm
     *     算法枚举值。
     * @return 秘钥工厂实例。
     */
    @Nonnull
    public static KeyFactory getKeyFactory(@Nonnull CryptoAlgorithm.Sign algorithm) {
        return getKeyFactory(algorithm.algorithm);
    }

    /**
     * 获取非对称加密算法私钥实例。
     *
     * @param keyData
     *     私钥。
     * @param algorithm
     *     算法枚举值。
     * @return 私钥实例。
     */
    @Nonnull
    public static PrivateKey getPrivateKey(@Nonnull byte[] keyData, @Nonnull CryptoAlgorithm.Sign algorithm) {
        KeyFactory keyFactory = getKeyFactory(algorithm);
        KeySpec keySpec = new PKCS8EncodedKeySpec(keyData);
        try {
            return keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 获取非对称加密算法公钥实例。
     *
     * @param keyData
     *     公钥。
     * @param algorithm
     *     算法枚举值。
     * @return 公钥实例。
     */
    @Nonnull
    public static PublicKey getPublicKey(@Nonnull byte[] keyData, @Nonnull CryptoAlgorithm.Sign algorithm) {
        KeyFactory keyFactory = getKeyFactory(algorithm);
        KeySpec keySpec = new X509EncodedKeySpec(keyData);
        try {
            return keyFactory.generatePublic(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 获取签名算法实例。
     *
     * @param signAlgorithm
     *     签名算法名称。
     * @return 签名算法实例。
     */
    @Nonnull
    public static Signature getSignature(@Nonnull String signAlgorithm) {
        try {
            return Signature.getInstance(signAlgorithm, PROVIDER);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 获取签名算法实例。
     *
     * @param algorithm
     *     算法枚举值。
     * @return 签名算法实例。
     */
    @Nonnull
    public static Signature getSignature(@Nonnull CryptoAlgorithm.Sign algorithm) {
        return getSignature(algorithm.signAlgorithm);
    }

    /**
     * 签名算法实例构建器。
     */
    public static class SignatureBuilder {
        /**
         * 签名算法实例。
         */
        private Signature signature;

        /**
         * 秘钥。
         */
        private Key key;

        /**
         * 随机数生成器算法实例。
         */
        private SecureRandom secureRandom;

        /**
         * 阻止实例化。
         */
        private SignatureBuilder() {
        }

        /**
         * 构造签名算法实例构建器。
         *
         * @param signAlgorithm
         *     签名算法名称。
         * @return 签名算法实例构建器。
         */
        @Nonnull
        public static SignatureBuilder newBuilder(@Nonnull String signAlgorithm) {
            SignatureBuilder builder = new SignatureBuilder();
            builder.signature = getSignature(signAlgorithm);
            return builder;
        }

        /**
         * 构造签名算法实例构建器。
         *
         * @param algorithm
         *     算法枚举值。
         * @return 签名算法实例构建器。
         */
        @Nonnull
        public static SignatureBuilder newBuilder(@Nonnull CryptoAlgorithm.Sign algorithm) {
            SignatureBuilder builder = new SignatureBuilder();
            builder.signature = getSignature(algorithm);
            return builder;
        }

        /**
         * 设置私钥。
         *
         * @param keyData
         *     私钥。
         * @param algorithm
         *     算法名称。
         * @return 签名算法实例构建器。
         */
        @Nonnull
        public SignatureBuilder setPrivateKey(@Nonnull byte[] keyData, @Nonnull String algorithm) {
            this.key = getPrivateKey(keyData, algorithm);
            return this;
        }

        /**
         * 设置私钥。
         *
         * @param keyData
         *     私钥。
         * @param algorithm
         *     算法枚举值。
         * @return 签名算法实例构建器。
         */
        @Nonnull
        public SignatureBuilder setPrivateKey(@Nonnull byte[] keyData, @Nonnull CryptoAlgorithm.Sign algorithm) {
            this.key = getPrivateKey(keyData, algorithm);
            return this;
        }

        /**
         * 设置私钥。
         *
         * @param key
         *     私钥。
         * @return 签名算法实例构建器。
         */
        @Nonnull
        public SignatureBuilder setPrivateKey(@Nonnull PrivateKey key) {
            this.key = key;
            return this;
        }

        /**
         * 设置公钥。
         *
         * @param keyData
         *     公钥。
         * @param algorithm
         *     算法名称。
         * @return 签名算法实例构建器。
         */
        @Nonnull
        public SignatureBuilder setPublicKey(@Nonnull byte[] keyData, @Nonnull String algorithm) {
            this.key = getPublicKey(keyData, algorithm);
            return this;
        }

        /**
         * 设置公钥。
         *
         * @param keyData
         *     公钥。
         * @param algorithm
         *     算法枚举值。
         * @return 签名算法实例构建器。
         */
        @Nonnull
        public SignatureBuilder setPublicKey(@Nonnull byte[] keyData, @Nonnull CryptoAlgorithm.Sign algorithm) {
            this.key = getPublicKey(keyData, algorithm);
            return this;
        }

        /**
         * 设置公钥。
         *
         * @param key
         *     公钥。
         * @return 签名算法实例构建器。
         */
        @Nonnull
        public SignatureBuilder setPublicKey(@Nonnull PublicKey key) {
            this.key = key;
            return this;
        }

        /**
         * 设置随机数生成器算法实例。
         *
         * @param algorithm
         *     算法名称。
         * @return 签名算法实例构建器。
         */
        @Nonnull
        public SignatureBuilder setSecureRandom(@Nonnull String algorithm) {
            this.secureRandom = getSecureRandom(algorithm);
            return this;
        }

        /**
         * 设置随机数生成器算法实例。
         *
         * @param algorithm
         *     算法枚举值。
         * @return 签名算法实例构建器。
         */
        @Nonnull
        public SignatureBuilder setSecureRandom(@Nonnull CryptoAlgorithm.Random algorithm) {
            this.secureRandom = getSecureRandom(algorithm);
            return this;
        }

        /**
         * 设置随机数生成器算法实例。
         *
         * @param secureRandom
         *     随机数生成器算法实例。
         * @return 签名算法实例构建器。
         */
        @Nonnull
        public SignatureBuilder setSecureRandom(@Nonnull SecureRandom secureRandom) {
            this.secureRandom = secureRandom;
            return this;
        }

        /**
         * 构建签名算法实例。
         *
         * @return 签名算法实例。
         */
        @Nonnull
        public Signature buildForSign() {
            if (secureRandom == null) {
                try {
                    signature.initSign((PrivateKey) key);
                } catch (InvalidKeyException e) {
                    throw new IllegalArgumentException(e);
                }
            } else {
                try {
                    signature.initSign((PrivateKey) key, secureRandom);
                } catch (InvalidKeyException e) {
                    throw new IllegalArgumentException(e);
                }
            }
            return signature;
        }

        /**
         * 构建签名算法实例。
         *
         * @return 签名算法实例。
         */
        @Nonnull
        public Signature buildForVerify() {
            if (secureRandom == null) {
                try {
                    signature.initVerify((PublicKey) key);
                } catch (InvalidKeyException e) {
                    throw new IllegalArgumentException(e);
                }
            } else {
                throw new IllegalArgumentException();
            }
            return signature;
        }
    }

    /**
     * 生成签名。
     *
     * @param plainData
     *     数据。
     * @param signature
     *     签名算法实例。
     * @return 签名。
     */
    @Nonnull
    public static byte[] sign(@Nonnull byte[] plainData, @Nonnull Signature signature) {
        try {
            signature.update(plainData);
            return signature.sign();
        } catch (SignatureException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 验证签名。
     *
     * @param plainData
     *     数据。
     * @param signData
     *     签名。
     * @param signature
     *     签名算法实例。
     * @return 是否通过验证。
     */
    public static boolean verify(@Nonnull byte[] plainData, @Nonnull byte[] signData, @Nonnull Signature signature) {
        try {
            signature.update(plainData);
            return signature.verify(signData);
        } catch (SignatureException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
