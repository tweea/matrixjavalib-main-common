/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.security;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * 加密算法工具。
 */
public final class CryptoMx {
    /**
     * 随机数生成器。
     */
    private static final SecureRandom RANDOM = new SecureRandom();

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
     * @return 随机数生成器算法实例。
     */
    public static SecureRandom getSecureRandom() {
        return RANDOM;
    }

    /**
     * 获取随机数生成器算法实例。
     * 
     * @param algorithm
     *     算法名称。
     * @return 随机数生成器算法实例。
     */
    public static SecureRandom getSecureRandom(String algorithm) {
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
    public static SecureRandom getSecureRandom(CryptoAlgorithm.Random algorithm) {
        return getSecureRandom(algorithm.algorithm);
    }

    /**
     * 生成随机数。
     * 
     * @param length
     *     随机数长度。
     * @param random
     *     随机数生成器算法实例。
     * @return 随机数。
     */
    public static byte[] generateRandom(int length, SecureRandom random) {
        byte[] randomData = new byte[length];
        random.nextBytes(randomData);
        return randomData;
    }

    /**
     * 生成随机数。
     * 
     * @param length
     *     随机数长度。
     * @return 随机数。
     */
    public static byte[] generateRandom(int length) {
        SecureRandom random = RANDOM;
        return generateRandom(length, random);
    }

    /**
     * 生成随机数。
     * 
     * @param length
     *     随机数长度。
     * @param algorithm
     *     算法名称。
     * @return 随机数。
     */
    public static byte[] generateRandom(int length, String algorithm) {
        SecureRandom random = getSecureRandom(algorithm);
        return generateRandom(length, random);
    }

    /**
     * 生成随机数。
     * 
     * @param length
     *     随机数长度。
     * @param algorithm
     *     算法枚举值。
     * @return 随机数。
     */
    public static byte[] generateRandom(int length, CryptoAlgorithm.Random algorithm) {
        SecureRandom random = getSecureRandom(algorithm);
        return generateRandom(length, random);
    }

    // 摘要算法
    /**
     * 获取摘要算法实例。
     * 
     * @param algorithm
     *     算法名称。
     * @return 摘要算法实例。
     */
    public static MessageDigest getDigest(String algorithm) {
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
    public static MessageDigest getDigest(CryptoAlgorithm.Digest algorithm) {
        return getDigest(algorithm.algorithm);
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
    public static byte[] digest(byte[] plainData, MessageDigest digest) {
        return digest.digest(plainData);
    }

    /**
     * 计算摘要。
     * 
     * @param plainData
     *     明文。
     * @param algorithm
     *     算法名称。
     * @return 摘要。
     */
    public static byte[] digest(byte[] plainData, String algorithm) {
        MessageDigest digest = getDigest(algorithm);
        return digest(plainData, digest);
    }

    /**
     * 计算摘要。
     * 
     * @param plainData
     *     明文。
     * @param algorithm
     *     算法枚举值。
     * @return 摘要。
     */
    public static byte[] digest(byte[] plainData, CryptoAlgorithm.Digest algorithm) {
        MessageDigest digest = getDigest(algorithm);
        return digest(plainData, digest);
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
    public static byte[] digest(byte[] plainData, byte[] saltData, MessageDigest digest) {
        digest.update(saltData);
        return digest.digest(plainData);
    }

    /**
     * 计算摘要。
     * 
     * @param plainData
     *     明文。
     * @param saltData
     *     盐。
     * @param algorithm
     *     算法名称。
     * @return 摘要。
     */
    public static byte[] digest(byte[] plainData, byte[] saltData, String algorithm) {
        MessageDigest digest = getDigest(algorithm);
        return digest(plainData, saltData, digest);
    }

    /**
     * 计算摘要。
     * 
     * @param plainData
     *     明文。
     * @param saltData
     *     盐。
     * @param algorithm
     *     算法枚举值。
     * @return 摘要。
     */
    public static byte[] digest(byte[] plainData, byte[] saltData, CryptoAlgorithm.Digest algorithm) {
        MessageDigest digest = getDigest(algorithm);
        return digest(plainData, saltData, digest);
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
    public static byte[] digest(InputStream plainData, MessageDigest digest)
        throws IOException {
        byte[] input = new byte[8 * 1024];
        int readLength = -1;
        while ((readLength = IOUtils.read(plainData, input)) > 0) {
            digest.update(input, 0, readLength);
        }
        return digest.digest();
    }

    /**
     * 计算摘要。
     * 
     * @param plainData
     *     明文。
     * @param algorithm
     *     算法名称。
     * @return 摘要。
     * @throws IOException
     *     读取明文失败。
     */
    public static byte[] digest(InputStream plainData, String algorithm)
        throws IOException {
        MessageDigest digest = getDigest(algorithm);
        return digest(plainData, digest);
    }

    /**
     * 计算摘要。
     * 
     * @param plainData
     *     明文。
     * @param algorithm
     *     算法枚举值。
     * @return 摘要。
     * @throws IOException
     *     读取明文失败。
     */
    public static byte[] digest(InputStream plainData, CryptoAlgorithm.Digest algorithm)
        throws IOException {
        MessageDigest digest = getDigest(algorithm);
        return digest(plainData, digest);
    }

    // 加密算法
    /**
     * 获取加密算法实例。
     * 
     * @param transformation
     *     算法变种名称。
     * @return 加密算法实例。
     */
    public static Cipher getCipher(String transformation) {
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
    public static void initEncryptKey(Cipher cipher, Key key) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
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
     */
    public static void initDecryptKey(Cipher cipher, Key key) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
        } catch (InvalidKeyException e) {
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
    public static byte[] encrypt(byte[] plainData, Cipher cipher) {
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
    public static void encrypt(InputStream plainData, OutputStream cipherData, Cipher cipher)
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
    public static byte[] decrypt(byte[] cipherData, Cipher cipher) {
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
     *     读取明文失败。
     */
    public static void decrypt(InputStream cipherData, OutputStream plainData, Cipher cipher)
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
     * 获取对称加密算法实例。
     * 
     * @param algorithm
     *     算法枚举值。
     * @return 加密算法实例。
     */
    public static Cipher getCipher(CryptoAlgorithm.Symmetric algorithm) {
        return getCipher(algorithm.transformation);
    }

    /**
     * 获取对称加密算法秘钥生成器实例。
     * 
     * @param algorithm
     *     算法名称。
     * @return 秘钥生成器实例。
     */
    public static KeyGenerator getKeyGenerator(String algorithm) {
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
    public static KeyGenerator getKeyGenerator(CryptoAlgorithm.Symmetric algorithm) {
        return getKeyGenerator(algorithm.algorithm);
    }

    /**
     * 生成对称加密算法秘钥实例。
     * 
     * @param algorithm
     *     算法名称。
     * @param keySize
     *     秘钥长度。
     * @return 秘钥实例。
     */
    public static SecretKey generateSecretKey(String algorithm, int keySize) {
        KeyGenerator keyGenerator = getKeyGenerator(algorithm);
        keyGenerator.init(keySize);
        return keyGenerator.generateKey();
    }

    /**
     * 生成对称加密算法秘钥实例。
     * 
     * @param algorithm
     *     算法枚举值。
     * @param keySize
     *     秘钥长度。
     * @return 秘钥实例。
     */
    public static SecretKey generateSecretKey(CryptoAlgorithm.Symmetric algorithm, int keySize) {
        KeyGenerator keyGenerator = getKeyGenerator(algorithm);
        keyGenerator.init(keySize);
        return keyGenerator.generateKey();
    }

    /**
     * 生成对称加密算法秘钥实例。
     * 
     * @param algorithm
     *     算法枚举值。
     * @return 秘钥实例。
     */
    public static SecretKey generateSecretKey(CryptoAlgorithm.Symmetric algorithm) {
        return generateSecretKey(algorithm, algorithm.defaultKeySize);
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
    public static SecretKey getSecretKey(byte[] keyData, String algorithm) {
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
    public static SecretKey getSecretKey(byte[] keyData, CryptoAlgorithm.Symmetric algorithm) {
        return getSecretKey(keyData, algorithm.algorithm);
    }

    /**
     * 对称加密。
     * 
     * @param plainData
     *     明文。
     * @param keyData
     *     秘钥。
     * @param algorithm
     *     算法名称。
     * @param transformation
     *     算法变种名称。
     * @return 密文。
     */
    public static byte[] encrypt(byte[] plainData, byte[] keyData, String algorithm, String transformation) {
        Cipher cipher = getCipher(transformation);
        SecretKey secretKey = getSecretKey(keyData, algorithm);
        initEncryptKey(cipher, secretKey);
        return encrypt(plainData, cipher);
    }

    /**
     * 对称加密。
     * 
     * @param plainData
     *     明文。
     * @param keyData
     *     秘钥。
     * @param algorithm
     *     算法枚举值。
     * @return 密文。
     */
    public static byte[] encrypt(byte[] plainData, byte[] keyData, CryptoAlgorithm.Symmetric algorithm) {
        Cipher cipher = getCipher(algorithm);
        SecretKey secretKey = getSecretKey(keyData, algorithm);
        initEncryptKey(cipher, secretKey);
        return encrypt(plainData, cipher);
    }

    /**
     * 对称加密。
     * 
     * @param plainData
     *     明文。
     * @param cipherData
     *     密文。
     * @param keyData
     *     秘钥。
     * @param algorithm
     *     算法名称。
     * @param transformation
     *     算法变种名称。
     * @throws IOException
     *     读取明文失败。
     */
    public static void encrypt(InputStream plainData, OutputStream cipherData, byte[] keyData, String algorithm, String transformation)
        throws IOException {
        Cipher cipher = getCipher(transformation);
        SecretKey secretKey = getSecretKey(keyData, algorithm);
        initEncryptKey(cipher, secretKey);
        encrypt(plainData, cipherData, cipher);
    }

    /**
     * 对称加密。
     * 
     * @param plainData
     *     明文。
     * @param cipherData
     *     密文。
     * @param keyData
     *     秘钥。
     * @param algorithm
     *     算法枚举值。
     * @throws IOException
     *     读取明文失败。
     */
    public static void encrypt(InputStream plainData, OutputStream cipherData, byte[] keyData, CryptoAlgorithm.Symmetric algorithm)
        throws IOException {
        Cipher cipher = getCipher(algorithm);
        SecretKey secretKey = getSecretKey(keyData, algorithm);
        initEncryptKey(cipher, secretKey);
        encrypt(plainData, cipherData, cipher);
    }

    /**
     * 对称解密。
     * 
     * @param cipherData
     *     密文。
     * @param keyData
     *     秘钥。
     * @param algorithm
     *     算法名称。
     * @param transformation
     *     算法变种名称。
     * @return 明文。
     */
    public static byte[] decrypt(byte[] cipherData, byte[] keyData, String algorithm, String transformation) {
        Cipher cipher = getCipher(transformation);
        SecretKey secretKey = getSecretKey(keyData, algorithm);
        initDecryptKey(cipher, secretKey);
        return decrypt(cipherData, cipher);
    }

    /**
     * 对称解密。
     * 
     * @param cipherData
     *     密文。
     * @param keyData
     *     秘钥。
     * @param algorithm
     *     算法枚举值。
     * @return 明文。
     */
    public static byte[] decrypt(byte[] cipherData, byte[] keyData, CryptoAlgorithm.Symmetric algorithm) {
        Cipher cipher = getCipher(algorithm);
        SecretKey secretKey = getSecretKey(keyData, algorithm);
        initDecryptKey(cipher, secretKey);
        return decrypt(cipherData, cipher);
    }

    /**
     * 对称解密。
     * 
     * @param cipherData
     *     密文。
     * @param plainData
     *     明文。
     * @param keyData
     *     秘钥。
     * @param algorithm
     *     算法名称。
     * @param transformation
     *     算法变种名称。
     * @throws IOException
     *     读取明文失败。
     */
    public static void decrypt(InputStream cipherData, OutputStream plainData, byte[] keyData, String algorithm, String transformation)
        throws IOException {
        Cipher cipher = getCipher(transformation);
        SecretKey secretKey = getSecretKey(keyData, algorithm);
        initDecryptKey(cipher, secretKey);
        decrypt(cipherData, plainData, cipher);
    }

    /**
     * 对称解密。
     * 
     * @param cipherData
     *     密文。
     * @param plainData
     *     明文。
     * @param keyData
     *     秘钥。
     * @param algorithm
     *     算法枚举值。
     * @throws IOException
     *     读取明文失败。
     */
    public static void decrypt(InputStream cipherData, OutputStream plainData, byte[] keyData, CryptoAlgorithm.Symmetric algorithm)
        throws IOException {
        Cipher cipher = getCipher(algorithm);
        SecretKey secretKey = getSecretKey(keyData, algorithm);
        initDecryptKey(cipher, secretKey);
        decrypt(cipherData, plainData, cipher);
    }

    // 非对称加密算法
    /**
     * 获取非对称加密算法实例。
     * 
     * @param algorithm
     *     算法枚举值。
     * @return 加密算法实例。
     */
    public static Cipher getCipher(CryptoAlgorithm.Asymmetric algorithm) {
        return getCipher(algorithm.transformation);
    }

    /**
     * 获取非对称加密算法秘钥对生成器实例。
     * 
     * @param algorithm
     *     算法名称。
     * @return 秘钥对生成器实例。
     */
    public static KeyPairGenerator getKeyPairGenerator(String algorithm) {
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
    public static KeyPairGenerator getKeyPairGenerator(CryptoAlgorithm.Asymmetric algorithm) {
        return getKeyPairGenerator(algorithm.algorithm);
    }

    /**
     * 生成非对称加密算法秘钥对实例。
     * 
     * @param algorithm
     *     算法名称。
     * @param keySize
     *     秘钥长度。
     * @return 秘钥对实例。
     */
    public static KeyPair generateKeyPair(String algorithm, int keySize) {
        KeyPairGenerator keyPairGenerator = getKeyPairGenerator(algorithm);
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 生成非对称加密算法秘钥对实例。
     * 
     * @param algorithm
     *     算法枚举值。
     * @param keySize
     *     秘钥长度。
     * @return 秘钥对实例。
     */
    public static KeyPair generateKeyPair(CryptoAlgorithm.Asymmetric algorithm, int keySize) {
        KeyPairGenerator keyPairGenerator = getKeyPairGenerator(algorithm);
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 生成非对称加密算法秘钥对实例。
     * 
     * @param algorithm
     *     算法枚举值。
     * @return 秘钥对实例。
     */
    public static KeyPair generateKeyPair(CryptoAlgorithm.Asymmetric algorithm) {
        return generateKeyPair(algorithm, algorithm.defaultKeySize);
    }

    /**
     * 获取非对称加密算法秘钥工厂实例。
     * 
     * @param algorithm
     *     算法名称。
     * @return 秘钥工厂实例。
     */
    public static KeyFactory getKeyFactory(String algorithm) {
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
    public static KeyFactory getKeyFactory(CryptoAlgorithm.Asymmetric algorithm) {
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
    public static PrivateKey getPrivateKey(byte[] keyData, String algorithm) {
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
    public static PrivateKey getPrivateKey(byte[] keyData, CryptoAlgorithm.Asymmetric algorithm) {
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
    public static PublicKey getPublicKey(byte[] keyData, String algorithm) {
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
    public static PublicKey getPublicKey(byte[] keyData, CryptoAlgorithm.Asymmetric algorithm) {
        KeyFactory keyFactory = getKeyFactory(algorithm);
        KeySpec keySpec = new X509EncodedKeySpec(keyData);
        try {
            return keyFactory.generatePublic(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 非对称加密。
     * 
     * @param plainData
     *     明文。
     * @param keyData
     *     公钥。
     * @param algorithm
     *     算法名称。
     * @param transformation
     *     算法变种名称。
     * @return 密文。
     */
    public static byte[] encryptPublic(byte[] plainData, byte[] keyData, String algorithm, String transformation) {
        Cipher cipher = getCipher(transformation);
        PublicKey publicKey = getPublicKey(keyData, algorithm);
        initEncryptKey(cipher, publicKey);
        return encrypt(plainData, cipher);
    }

    /**
     * 非对称加密。
     * 
     * @param plainData
     *     明文。
     * @param keyData
     *     公钥。
     * @param algorithm
     *     算法枚举值。
     * @return 密文。
     */
    public static byte[] encryptPublic(byte[] plainData, byte[] keyData, CryptoAlgorithm.Asymmetric algorithm) {
        Cipher cipher = getCipher(algorithm);
        PublicKey publicKey = getPublicKey(keyData, algorithm);
        initEncryptKey(cipher, publicKey);
        return encrypt(plainData, cipher);
    }

    /**
     * 非对称解密。
     * 
     * @param cipherData
     *     密文。
     * @param keyData
     *     私钥。
     * @param algorithm
     *     算法名称。
     * @param transformation
     *     算法变种名称。
     * @return 明文。
     */
    public static byte[] decryptPrivate(byte[] cipherData, byte[] keyData, String algorithm, String transformation) {
        Cipher cipher = getCipher(transformation);
        PrivateKey privateKey = getPrivateKey(keyData, algorithm);
        initDecryptKey(cipher, privateKey);
        return decrypt(cipherData, cipher);
    }

    /**
     * 非对称解密。
     * 
     * @param cipherData
     *     密文。
     * @param keyData
     *     私钥。
     * @param algorithm
     *     算法枚举值。
     * @return 明文。
     */
    public static byte[] decryptPrivate(byte[] cipherData, byte[] keyData, CryptoAlgorithm.Asymmetric algorithm) {
        Cipher cipher = getCipher(algorithm);
        PrivateKey privateKey = getPrivateKey(keyData, algorithm);
        initDecryptKey(cipher, privateKey);
        return decrypt(cipherData, cipher);
    }

    // 签名算法
    /**
     * 获取签名算法实例。
     * 
     * @param signAlgorithm
     *     签名算法名称。
     * @return 签名算法实例。
     */
    public static Signature getSignature(String signAlgorithm) {
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
    public static Signature getSignature(CryptoAlgorithm.Sign algorithm) {
        return getSignature(algorithm.signAlgorithm);
    }

    /**
     * 获取非对称加密算法秘钥对生成器实例。
     * 
     * @param algorithm
     *     算法枚举值。
     * @return 秘钥对生成器实例。
     */
    public static KeyPairGenerator getKeyPairGenerator(CryptoAlgorithm.Sign algorithm) {
        return getKeyPairGenerator(algorithm.algorithm);
    }

    /**
     * 生成非对称加密算法秘钥对实例。
     * 
     * @param algorithm
     *     算法枚举值。
     * @param keySize
     *     秘钥长度。
     * @return 秘钥对实例。
     */
    public static KeyPair generateKeyPair(CryptoAlgorithm.Sign algorithm, int keySize) {
        KeyPairGenerator keyPairGenerator = getKeyPairGenerator(algorithm);
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 生成非对称加密算法秘钥对实例。
     * 
     * @param algorithm
     *     算法枚举值。
     * @return 秘钥对实例。
     */
    public static KeyPair generateKeyPair(CryptoAlgorithm.Sign algorithm) {
        return generateKeyPair(algorithm, algorithm.defaultKeySize);
    }

    /**
     * 获取非对称加密算法秘钥工厂实例。
     * 
     * @param algorithm
     *     算法枚举值。
     * @return 秘钥工厂实例。
     */
    public static KeyFactory getKeyFactory(CryptoAlgorithm.Sign algorithm) {
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
    public static PrivateKey getPrivateKey(byte[] keyData, CryptoAlgorithm.Sign algorithm) {
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
    public static PublicKey getPublicKey(byte[] keyData, CryptoAlgorithm.Sign algorithm) {
        KeyFactory keyFactory = getKeyFactory(algorithm);
        KeySpec keySpec = new X509EncodedKeySpec(keyData);
        try {
            return keyFactory.generatePublic(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 生成签名。
     * 
     * @param data
     *     数据。
     * @param keyData
     *     私钥。
     * @param algorithm
     *     算法名称。
     * @param signAlgorithm
     *     签名算法名称。
     * @return 签名。
     */
    public static byte[] signPrivate(byte[] data, byte[] keyData, String algorithm, String signAlgorithm) {
        Signature signature = getSignature(signAlgorithm);
        PrivateKey privateKey = getPrivateKey(keyData, algorithm);
        try {
            signature.initSign(privateKey);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        }
        try {
            signature.update(data);
            return signature.sign();
        } catch (SignatureException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 生成签名。
     * 
     * @param data
     *     数据。
     * @param keyData
     *     私钥。
     * @param algorithm
     *     算法枚举值。
     * @return 签名。
     */
    public static byte[] signPrivate(byte[] data, byte[] keyData, CryptoAlgorithm.Sign algorithm) {
        Signature signature = getSignature(algorithm);
        PrivateKey privateKey = getPrivateKey(keyData, algorithm);
        try {
            signature.initSign(privateKey);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        }
        try {
            signature.update(data);
            return signature.sign();
        } catch (SignatureException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 验证签名。
     * 
     * @param data
     *     数据。
     * @param signData
     *     签名。
     * @param keyData
     *     公钥。
     * @param algorithm
     *     算法名称。
     * @param signAlgorithm
     *     签名算法名称。
     * @return 是否通过验证。
     */
    public static boolean verifyPublic(byte[] data, byte[] signData, byte[] keyData, String algorithm, String signAlgorithm) {
        Signature signature = getSignature(signAlgorithm);
        PublicKey publicKey = getPublicKey(keyData, algorithm);
        try {
            signature.initVerify(publicKey);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        }
        try {
            signature.update(data);
            return signature.verify(signData);
        } catch (SignatureException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 验证签名。
     * 
     * @param data
     *     数据。
     * @param signData
     *     签名。
     * @param keyData
     *     公钥。
     * @param algorithm
     *     算法枚举值。
     * @return 是否通过验证。
     */
    public static boolean verifyPublic(byte[] data, byte[] signData, byte[] keyData, CryptoAlgorithm.Sign algorithm) {
        Signature signature = getSignature(algorithm);
        PublicKey publicKey = getPublicKey(keyData, algorithm);
        try {
            signature.initVerify(publicKey);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        }
        try {
            signature.update(data);
            return signature.verify(signData);
        } catch (SignatureException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
