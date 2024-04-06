/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import static net.matrix.data.convert.BinaryStringConverter.HEX;
import static net.matrix.data.convert.BinaryStringConverter.UTF8;

class CryptoMxTest {
    KeyPair rsaKeyPair = CryptoMx
        .generateKeyPair(CryptoMx.AsymmetricKeyPairGeneratorBuilder.newBuilder(CryptoAlgorithm.Asymmetric.RSA_NONE_PKCS1PADDING).build());

    KeyPair sm2KeyPair = CryptoMx.generateKeyPair(CryptoMx.AsymmetricKeyPairGeneratorBuilder.newBuilder(CryptoAlgorithm.Asymmetric.SM2_NONE_NOPADDING).build());

    @Test
    void testGetSecureRandom() {
        CryptoAlgorithm.Random algorithm = CryptoAlgorithm.Random.DEFAULT;

        SecureRandom random = CryptoMx.getSecureRandom(algorithm);
        assertThat(random.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGenerateRandom() {
        CryptoAlgorithm.Random algorithm = CryptoAlgorithm.Random.DEFAULT;
        SecureRandom random = CryptoMx.getSecureRandom(algorithm);

        byte[] randomData = CryptoMx.generateRandom(5, random);
        assertThat(randomData).hasSize(5);
    }

    @Test
    void testGetMessageDigest() {
        CryptoAlgorithm.Digest algorithm = CryptoAlgorithm.Digest.MD5;

        MessageDigest digest = CryptoMx.getMessageDigest(algorithm);
        assertThat(digest.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testDigest_md5() {
        CryptoAlgorithm.Digest algorithm = CryptoAlgorithm.Digest.MD5;
        MessageDigest digest = CryptoMx.getMessageDigest(algorithm);
        byte[] plainData = UTF8.toBinary("沧海月明");
        byte[] digestData = HEX.toBinary("5b95c94bbc42391c190ae5e91b26c007");

        assertThat(CryptoMx.digest(plainData, digest)).isEqualTo(digestData);
    }

    @Test
    void testDigest_sha1() {
        CryptoAlgorithm.Digest algorithm = CryptoAlgorithm.Digest.SHA1;
        MessageDigest digest = CryptoMx.getMessageDigest(algorithm);
        byte[] plainData = UTF8.toBinary("沧海月明");
        byte[] digestData = HEX.toBinary("0f88beb5d269a339887aca1d769edc7c88f7eab0");

        assertThat(CryptoMx.digest(plainData, digest)).isEqualTo(digestData);
    }

    @Test
    void testDigest_sm3() {
        CryptoAlgorithm.Digest algorithm = CryptoAlgorithm.Digest.SM3;
        MessageDigest digest = CryptoMx.getMessageDigest(algorithm);
        byte[] plainData = UTF8.toBinary("沧海月明");
        byte[] digestData = HEX.toBinary("e5aee80250131e14ca2a0e2165a7759b47b2285dadd5ece90251b79c2cd9f7b1");

        assertThat(CryptoMx.digest(plainData, digest)).isEqualTo(digestData);
    }

    @Test
    void testDigest_salt() {
        CryptoAlgorithm.Digest algorithm = CryptoAlgorithm.Digest.MD5;
        MessageDigest digest = CryptoMx.getMessageDigest(algorithm);
        byte[] plainData = UTF8.toBinary("海月明");
        byte[] saltData = UTF8.toBinary("沧");
        byte[] digestData = HEX.toBinary("5b95c94bbc42391c190ae5e91b26c007");

        assertThat(CryptoMx.digest(plainData, saltData, digest)).isEqualTo(digestData);
    }

    @Test
    void testDigest_stream()
        throws IOException {
        CryptoAlgorithm.Digest algorithm = CryptoAlgorithm.Digest.MD5;
        MessageDigest digest = CryptoMx.getMessageDigest(algorithm);
        byte[] plainData = UTF8.toBinary("沧海月明");
        byte[] digestData = HEX.toBinary("5b95c94bbc42391c190ae5e91b26c007");

        assertThat(CryptoMx.digest(new ByteArrayInputStream(plainData), digest)).isEqualTo(digestData);
    }

    @Test
    void testDigest_stream_salt()
        throws IOException {
        CryptoAlgorithm.Digest algorithm = CryptoAlgorithm.Digest.MD5;
        MessageDigest digest = CryptoMx.getMessageDigest(algorithm);
        byte[] plainData = UTF8.toBinary("海月明");
        byte[] saltData = UTF8.toBinary("沧");
        byte[] digestData = HEX.toBinary("5b95c94bbc42391c190ae5e91b26c007");

        assertThat(CryptoMx.digest(new ByteArrayInputStream(plainData), saltData, digest)).isEqualTo(digestData);
    }

    @Test
    void testGetAlgorithmParameterGenerator_symmetric() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_CBC_NOPADDING;

        AlgorithmParameterGenerator algorithmParameterGenerator = CryptoMx.getAlgorithmParameterGenerator(algorithm);
        assertThat(algorithmParameterGenerator.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testSymmetricAlgorithmParameterGeneratorBuilder() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_CBC_NOPADDING;

        AlgorithmParameterGenerator algorithmParameterGenerator = CryptoMx.SymmetricAlgorithmParameterGeneratorBuilder.newBuilder(algorithm).build();
        assertThat(algorithmParameterGenerator.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testSymmetricAlgorithmParameterGeneratorBuilder_random() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_CBC_NOPADDING;
        CryptoAlgorithm.Random randomAlgorithm = CryptoAlgorithm.Random.DEFAULT;

        AlgorithmParameterGenerator algorithmParameterGenerator = CryptoMx.SymmetricAlgorithmParameterGeneratorBuilder.newBuilder(algorithm).setSize(16)
            .setSecureRandom(randomAlgorithm).build();
        assertThat(algorithmParameterGenerator.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGenerateAlgorithmParameter_des() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_CBC_NOPADDING;
        AlgorithmParameterGenerator algorithmParameterGenerator = CryptoMx.SymmetricAlgorithmParameterGeneratorBuilder.newBuilder(algorithm).build();

        AlgorithmParameters algorithmParameter = CryptoMx.generateAlgorithmParameter(algorithmParameterGenerator);
        assertThat(algorithmParameter.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGenerateAlgorithmParameter_desede() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DESEDE_CBC_NOPADDING;
        AlgorithmParameterGenerator algorithmParameterGenerator = CryptoMx.SymmetricAlgorithmParameterGeneratorBuilder.newBuilder(algorithm).build();

        AlgorithmParameters algorithmParameter = CryptoMx.generateAlgorithmParameter(algorithmParameterGenerator);
        assertThat(algorithmParameter.getAlgorithm()).isEqualTo(CryptoAlgorithm.Symmetric.DES_CBC_NOPADDING.algorithm);
    }

    @Test
    void testGenerateAlgorithmParameter_aes() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.AES_CBC_NOPADDING;
        AlgorithmParameterGenerator algorithmParameterGenerator = CryptoMx.SymmetricAlgorithmParameterGeneratorBuilder.newBuilder(algorithm).build();

        AlgorithmParameters algorithmParameter = CryptoMx.generateAlgorithmParameter(algorithmParameterGenerator);
        assertThat(algorithmParameter.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGenerateAlgorithmParameter_sm4() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.SM4_CBC_NOPADDING;
        AlgorithmParameterGenerator algorithmParameterGenerator = CryptoMx.SymmetricAlgorithmParameterGeneratorBuilder.newBuilder(algorithm).build();

        AlgorithmParameters algorithmParameter = CryptoMx.generateAlgorithmParameter(algorithmParameterGenerator);
        assertThat(algorithmParameter.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetAlgorithmParameter_symmetric() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_CBC_NOPADDING;

        AlgorithmParameters algorithmParameter = CryptoMx.getAlgorithmParameter(algorithm);
        assertThat(algorithmParameter.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testSymmetricAlgorithmParameterBuilder() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_CBC_NOPADDING;
        AlgorithmParameterGenerator algorithmParameterGenerator = CryptoMx.SymmetricAlgorithmParameterGeneratorBuilder.newBuilder(algorithm).build();
        byte[] paramData = CryptoMx.encodeAlgorithmParameter(CryptoMx.generateAlgorithmParameter(algorithmParameterGenerator));

        AlgorithmParameters algorithmParameter = CryptoMx.SymmetricAlgorithmParameterBuilder.newBuilder(algorithm).setParam(paramData).build();
        assertThat(algorithmParameter.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetKeyGenerator() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_ECB_NOPADDING;

        KeyGenerator keyGenerator = CryptoMx.getKeyGenerator(algorithm);
        assertThat(keyGenerator.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testKeyGeneratorBuilder() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_ECB_NOPADDING;

        KeyGenerator keyGenerator = CryptoMx.KeyGeneratorBuilder.newBuilder(algorithm).build();
        assertThat(keyGenerator.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testKeyGeneratorBuilder_random() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_ECB_NOPADDING;
        CryptoAlgorithm.Random randomAlgorithm = CryptoAlgorithm.Random.DEFAULT;

        KeyGenerator keyGenerator = CryptoMx.KeyGeneratorBuilder.newBuilder(algorithm).setSecureRandom(randomAlgorithm).build();
        assertThat(keyGenerator.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGenerateSecretKey_des() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_ECB_NOPADDING;
        KeyGenerator keyGenerator = CryptoMx.KeyGeneratorBuilder.newBuilder(algorithm).build();

        SecretKey secretKey = CryptoMx.generateSecretKey(keyGenerator);
        assertThat(secretKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGenerateSecretKey_desede() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DESEDE_ECB_NOPADDING;
        KeyGenerator keyGenerator = CryptoMx.KeyGeneratorBuilder.newBuilder(algorithm).build();

        SecretKey secretKey = CryptoMx.generateSecretKey(keyGenerator);
        assertThat(secretKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGenerateSecretKey_aes() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.AES_ECB_NOPADDING;
        KeyGenerator keyGenerator = CryptoMx.KeyGeneratorBuilder.newBuilder(algorithm).build();

        SecretKey secretKey = CryptoMx.generateSecretKey(keyGenerator);
        assertThat(secretKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGenerateSecretKey_sm4() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.SM4_ECB_NOPADDING;
        KeyGenerator keyGenerator = CryptoMx.KeyGeneratorBuilder.newBuilder(algorithm).build();

        SecretKey secretKey = CryptoMx.generateSecretKey(keyGenerator);
        assertThat(secretKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetSecretKey_des() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_ECB_NOPADDING;
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c");

        SecretKey secretKey = CryptoMx.getSecretKey(keyData, algorithm);
        assertThat(secretKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetSecretKey_desede() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DESEDE_ECB_NOPADDING;
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");

        SecretKey secretKey = CryptoMx.getSecretKey(keyData, algorithm);
        assertThat(secretKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetSecretKey_aes() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.AES_ECB_NOPADDING;
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");

        SecretKey secretKey = CryptoMx.getSecretKey(keyData, algorithm);
        assertThat(secretKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetSecretKey_sm4() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.SM4_ECB_NOPADDING;
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5f");

        SecretKey secretKey = CryptoMx.getSecretKey(keyData, algorithm);
        assertThat(secretKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetCipher_symmetric() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_ECB_NOPADDING;

        Cipher cipher = CryptoMx.getCipher(algorithm);
        assertThat(cipher.getAlgorithm()).isEqualTo(algorithm.transformation);
    }

    @Test
    void testSymmetricCipherBuilder() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_CBC_NOPADDING;
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c");

        Cipher cipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForEncrypt();
        assertThat(cipher.getAlgorithm()).isEqualTo(algorithm.transformation);
    }

    @Test
    void testSymmetricCipherBuilder_random() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_CBC_NOPADDING;
        CryptoAlgorithm.Random randomAlgorithm = CryptoAlgorithm.Random.DEFAULT;
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c");

        Cipher cipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setSecureRandom(randomAlgorithm).buildForEncrypt();
        assertThat(cipher.getAlgorithm()).isEqualTo(algorithm.transformation);
    }

    @Test
    void testSymmetricCipherBuilder_iv() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_CBC_NOPADDING;
        AlgorithmParameterGenerator algorithmParameterGenerator = CryptoMx.SymmetricAlgorithmParameterGeneratorBuilder.newBuilder(algorithm).build();
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c");
        AlgorithmParameters algorithmParameter = CryptoMx.generateAlgorithmParameter(algorithmParameterGenerator);

        Cipher cipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForEncrypt();
        assertThat(cipher.getAlgorithm()).isEqualTo(algorithm.transformation);
    }

    @Test
    void testSymmetric_des_ecb_no() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_ECB_NOPADDING;
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c");
        Cipher encryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForEncrypt();
        Cipher decryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForDecrypt();
        byte[] plainData = Arrays.copyOf(UTF8.toBinary("沧海月明"), 16);

        byte[] cipherData = CryptoMx.encrypt(plainData, encryptCipher);
        assertThat(CryptoMx.decrypt(cipherData, decryptCipher)).isEqualTo(plainData);
    }

    @Test
    void testSymmetric_des_ecb_zero() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_ECB_ZEROBYTEPADDING;
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c");
        Cipher encryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForEncrypt();
        Cipher decryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForDecrypt();
        byte[] plainData = UTF8.toBinary("沧海月明");

        byte[] cipherData = CryptoMx.encrypt(plainData, encryptCipher);
        assertThat(CryptoMx.decrypt(cipherData, decryptCipher)).isEqualTo(plainData);
    }

    @Test
    void testSymmetric_des_ecb_pkcs5() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_ECB_PKCS5PADDING;
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c");
        Cipher encryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForEncrypt();
        Cipher decryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForDecrypt();
        byte[] plainData = UTF8.toBinary("沧海月明");

        byte[] cipherData = CryptoMx.encrypt(plainData, encryptCipher);
        assertThat(CryptoMx.decrypt(cipherData, decryptCipher)).isEqualTo(plainData);
    }

    @Test
    void testSymmetric_des_cbc_no() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_CBC_NOPADDING;
        AlgorithmParameterGenerator algorithmParameterGenerator = CryptoMx.SymmetricAlgorithmParameterGeneratorBuilder.newBuilder(algorithm).build();
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c");
        AlgorithmParameters algorithmParameter = CryptoMx.generateAlgorithmParameter(algorithmParameterGenerator);
        Cipher encryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForEncrypt();
        Cipher decryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForDecrypt();
        byte[] plainData = Arrays.copyOf(UTF8.toBinary("沧海月明"), 16);

        byte[] cipherData = CryptoMx.encrypt(plainData, encryptCipher);
        assertThat(CryptoMx.decrypt(cipherData, decryptCipher)).isEqualTo(plainData);
    }

    @Test
    void testSymmetric_des_cbc_zero() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_CBC_ZEROBYTEPADDING;
        AlgorithmParameterGenerator algorithmParameterGenerator = CryptoMx.SymmetricAlgorithmParameterGeneratorBuilder.newBuilder(algorithm).build();
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c");
        AlgorithmParameters algorithmParameter = CryptoMx.generateAlgorithmParameter(algorithmParameterGenerator);
        Cipher encryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForEncrypt();
        Cipher decryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForDecrypt();
        byte[] plainData = UTF8.toBinary("沧海月明");

        byte[] cipherData = CryptoMx.encrypt(plainData, encryptCipher);
        assertThat(CryptoMx.decrypt(cipherData, decryptCipher)).isEqualTo(plainData);
    }

    @Test
    void testSymmetric_des_cbc_pkcs5() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_CBC_PKCS5PADDING;
        AlgorithmParameterGenerator algorithmParameterGenerator = CryptoMx.SymmetricAlgorithmParameterGeneratorBuilder.newBuilder(algorithm).build();
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c");
        AlgorithmParameters algorithmParameter = CryptoMx.generateAlgorithmParameter(algorithmParameterGenerator);
        Cipher encryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForEncrypt();
        Cipher decryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForDecrypt();
        byte[] plainData = UTF8.toBinary("沧海月明");

        byte[] cipherData = CryptoMx.encrypt(plainData, encryptCipher);
        assertThat(CryptoMx.decrypt(cipherData, decryptCipher)).isEqualTo(plainData);
    }

    @Test
    void testSymmetric_desede_ecb_no() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DESEDE_ECB_NOPADDING;
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");
        Cipher encryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForEncrypt();
        Cipher decryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForDecrypt();
        byte[] plainData = Arrays.copyOf(UTF8.toBinary("沧海月明"), 16);

        byte[] cipherData = CryptoMx.encrypt(plainData, encryptCipher);
        assertThat(CryptoMx.decrypt(cipherData, decryptCipher)).isEqualTo(plainData);
    }

    @Test
    void testSymmetric_desede_ecb_zero() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DESEDE_ECB_ZEROBYTEPADDING;
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");
        Cipher encryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForEncrypt();
        Cipher decryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForDecrypt();
        byte[] plainData = UTF8.toBinary("沧海月明");

        byte[] cipherData = CryptoMx.encrypt(plainData, encryptCipher);
        assertThat(CryptoMx.decrypt(cipherData, decryptCipher)).isEqualTo(plainData);
    }

    @Test
    void testSymmetric_desede_ecb_pkcs5() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DESEDE_ECB_PKCS5PADDING;
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");
        Cipher encryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForEncrypt();
        Cipher decryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForDecrypt();
        byte[] plainData = UTF8.toBinary("沧海月明");

        byte[] cipherData = CryptoMx.encrypt(plainData, encryptCipher);
        assertThat(CryptoMx.decrypt(cipherData, decryptCipher)).isEqualTo(plainData);
    }

    @Test
    void testSymmetric_desede_cbc_no() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DESEDE_CBC_NOPADDING;
        AlgorithmParameterGenerator algorithmParameterGenerator = CryptoMx.SymmetricAlgorithmParameterGeneratorBuilder.newBuilder(algorithm).build();
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");
        AlgorithmParameters algorithmParameter = CryptoMx.generateAlgorithmParameter(algorithmParameterGenerator);
        Cipher encryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForEncrypt();
        Cipher decryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForDecrypt();
        byte[] plainData = Arrays.copyOf(UTF8.toBinary("沧海月明"), 16);

        byte[] cipherData = CryptoMx.encrypt(plainData, encryptCipher);
        assertThat(CryptoMx.decrypt(cipherData, decryptCipher)).isEqualTo(plainData);
    }

    @Test
    void testSymmetric_desede_cbc_zero() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DESEDE_CBC_ZEROBYTEPADDING;
        AlgorithmParameterGenerator algorithmParameterGenerator = CryptoMx.SymmetricAlgorithmParameterGeneratorBuilder.newBuilder(algorithm).build();
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");
        AlgorithmParameters algorithmParameter = CryptoMx.generateAlgorithmParameter(algorithmParameterGenerator);
        Cipher encryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForEncrypt();
        Cipher decryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForDecrypt();
        byte[] plainData = UTF8.toBinary("沧海月明");

        byte[] cipherData = CryptoMx.encrypt(plainData, encryptCipher);
        assertThat(CryptoMx.decrypt(cipherData, decryptCipher)).isEqualTo(plainData);
    }

    @Test
    void testSymmetric_desede_cbc_pkcs5() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DESEDE_CBC_PKCS5PADDING;
        AlgorithmParameterGenerator algorithmParameterGenerator = CryptoMx.SymmetricAlgorithmParameterGeneratorBuilder.newBuilder(algorithm).build();
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");
        AlgorithmParameters algorithmParameter = CryptoMx.generateAlgorithmParameter(algorithmParameterGenerator);
        Cipher encryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForEncrypt();
        Cipher decryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForDecrypt();
        byte[] plainData = UTF8.toBinary("沧海月明");

        byte[] cipherData = CryptoMx.encrypt(plainData, encryptCipher);
        assertThat(CryptoMx.decrypt(cipherData, decryptCipher)).isEqualTo(plainData);
    }

    @Test
    void testSymmetric_aes_ecb_no() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.AES_ECB_NOPADDING;
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");
        Cipher encryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForEncrypt();
        Cipher decryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForDecrypt();
        byte[] plainData = Arrays.copyOf(UTF8.toBinary("沧海月明"), 16);

        byte[] cipherData = CryptoMx.encrypt(plainData, encryptCipher);
        assertThat(CryptoMx.decrypt(cipherData, decryptCipher)).isEqualTo(plainData);
    }

    @Test
    void testSymmetric_aes_ecb_zero() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.AES_ECB_ZEROBYTEPADDING;
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");
        Cipher encryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForEncrypt();
        Cipher decryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForDecrypt();
        byte[] plainData = UTF8.toBinary("沧海月明");

        byte[] cipherData = CryptoMx.encrypt(plainData, encryptCipher);
        assertThat(CryptoMx.decrypt(cipherData, decryptCipher)).isEqualTo(plainData);
    }

    @Test
    void testSymmetric_aes_ecb_pkcs5() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.AES_ECB_PKCS5PADDING;
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");
        Cipher encryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForEncrypt();
        Cipher decryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForDecrypt();
        byte[] plainData = UTF8.toBinary("沧海月明");

        byte[] cipherData = CryptoMx.encrypt(plainData, encryptCipher);
        assertThat(CryptoMx.decrypt(cipherData, decryptCipher)).isEqualTo(plainData);
    }

    @Test
    void testSymmetric_aes_cbc_no() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.AES_CBC_NOPADDING;
        AlgorithmParameterGenerator algorithmParameterGenerator = CryptoMx.SymmetricAlgorithmParameterGeneratorBuilder.newBuilder(algorithm).build();
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");
        AlgorithmParameters algorithmParameter = CryptoMx.generateAlgorithmParameter(algorithmParameterGenerator);
        Cipher encryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForEncrypt();
        Cipher decryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForDecrypt();
        byte[] plainData = Arrays.copyOf(UTF8.toBinary("沧海月明"), 16);

        byte[] cipherData = CryptoMx.encrypt(plainData, encryptCipher);
        assertThat(CryptoMx.decrypt(cipherData, decryptCipher)).isEqualTo(plainData);
    }

    @Test
    void testSymmetric_aes_cbc_zero() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.AES_CBC_ZEROBYTEPADDING;
        AlgorithmParameterGenerator algorithmParameterGenerator = CryptoMx.SymmetricAlgorithmParameterGeneratorBuilder.newBuilder(algorithm).build();
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");
        AlgorithmParameters algorithmParameter = CryptoMx.generateAlgorithmParameter(algorithmParameterGenerator);
        Cipher encryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForEncrypt();
        Cipher decryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForDecrypt();
        byte[] plainData = UTF8.toBinary("沧海月明");

        byte[] cipherData = CryptoMx.encrypt(plainData, encryptCipher);
        assertThat(CryptoMx.decrypt(cipherData, decryptCipher)).isEqualTo(plainData);
    }

    @Test
    void testSymmetric_aes_cbc_pkcs5() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.AES_CBC_PKCS5PADDING;
        AlgorithmParameterGenerator algorithmParameterGenerator = CryptoMx.SymmetricAlgorithmParameterGeneratorBuilder.newBuilder(algorithm).build();
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");
        AlgorithmParameters algorithmParameter = CryptoMx.generateAlgorithmParameter(algorithmParameterGenerator);
        Cipher encryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForEncrypt();
        Cipher decryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForDecrypt();
        byte[] plainData = UTF8.toBinary("沧海月明");

        byte[] cipherData = CryptoMx.encrypt(plainData, encryptCipher);
        assertThat(CryptoMx.decrypt(cipherData, decryptCipher)).isEqualTo(plainData);
    }

    @Test
    void testSymmetric_sm4_ecb_no() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.SM4_ECB_NOPADDING;
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5f");
        Cipher encryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForEncrypt();
        Cipher decryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForDecrypt();
        byte[] plainData = Arrays.copyOf(UTF8.toBinary("沧海月明"), 16);

        byte[] cipherData = CryptoMx.encrypt(plainData, encryptCipher);
        assertThat(CryptoMx.decrypt(cipherData, decryptCipher)).isEqualTo(plainData);
    }

    @Test
    void testSymmetric_sm4_ecb_zero() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.SM4_ECB_ZEROBYTEPADDING;
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5f");
        Cipher encryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForEncrypt();
        Cipher decryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForDecrypt();
        byte[] plainData = UTF8.toBinary("沧海月明");

        byte[] cipherData = CryptoMx.encrypt(plainData, encryptCipher);
        assertThat(CryptoMx.decrypt(cipherData, decryptCipher)).isEqualTo(plainData);
    }

    @Test
    void testSymmetric_sm4_ecb_pkcs5() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.SM4_ECB_PKCS5PADDING;
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5f");
        Cipher encryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForEncrypt();
        Cipher decryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForDecrypt();
        byte[] plainData = UTF8.toBinary("沧海月明");

        byte[] cipherData = CryptoMx.encrypt(plainData, encryptCipher);
        assertThat(CryptoMx.decrypt(cipherData, decryptCipher)).isEqualTo(plainData);
    }

    @Test
    void testSymmetric_sm4_cbc_no() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.SM4_CBC_NOPADDING;
        AlgorithmParameterGenerator algorithmParameterGenerator = CryptoMx.SymmetricAlgorithmParameterGeneratorBuilder.newBuilder(algorithm).build();
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5f");
        AlgorithmParameters algorithmParameter = CryptoMx.generateAlgorithmParameter(algorithmParameterGenerator);
        Cipher encryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForEncrypt();
        Cipher decryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForDecrypt();
        byte[] plainData = Arrays.copyOf(UTF8.toBinary("沧海月明"), 16);

        byte[] cipherData = CryptoMx.encrypt(plainData, encryptCipher);
        assertThat(CryptoMx.decrypt(cipherData, decryptCipher)).isEqualTo(plainData);
    }

    @Test
    void testSymmetric_sm4_cbc_zero() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.SM4_CBC_ZEROBYTEPADDING;
        AlgorithmParameterGenerator algorithmParameterGenerator = CryptoMx.SymmetricAlgorithmParameterGeneratorBuilder.newBuilder(algorithm).build();
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5f");
        AlgorithmParameters algorithmParameter = CryptoMx.generateAlgorithmParameter(algorithmParameterGenerator);
        Cipher encryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForEncrypt();
        Cipher decryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForDecrypt();
        byte[] plainData = UTF8.toBinary("沧海月明");

        byte[] cipherData = CryptoMx.encrypt(plainData, encryptCipher);
        assertThat(CryptoMx.decrypt(cipherData, decryptCipher)).isEqualTo(plainData);
    }

    @Test
    void testSymmetric_sm4_cbc_pkcs5() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.SM4_CBC_PKCS5PADDING;
        AlgorithmParameterGenerator algorithmParameterGenerator = CryptoMx.SymmetricAlgorithmParameterGeneratorBuilder.newBuilder(algorithm).build();
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5f");
        AlgorithmParameters algorithmParameter = CryptoMx.generateAlgorithmParameter(algorithmParameterGenerator);
        Cipher encryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForEncrypt();
        Cipher decryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForDecrypt();
        byte[] plainData = UTF8.toBinary("沧海月明");

        byte[] cipherData = CryptoMx.encrypt(plainData, encryptCipher);
        assertThat(CryptoMx.decrypt(cipherData, decryptCipher)).isEqualTo(plainData);
    }

    @Test
    void testSymmetric_stream_ecb()
        throws IOException {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.SM4_ECB_PKCS5PADDING;
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5f");
        Cipher encryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForEncrypt();
        Cipher decryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).buildForDecrypt();
        byte[] plainData = UTF8.toBinary(RandomStringUtils.random(16 * 1024 + 200));

        ByteArrayOutputStream cipherDataStream = new ByteArrayOutputStream();
        CryptoMx.encrypt(new ByteArrayInputStream(plainData), cipherDataStream, encryptCipher);
        ByteArrayOutputStream plainDataStream = new ByteArrayOutputStream();
        CryptoMx.decrypt(new ByteArrayInputStream(cipherDataStream.toByteArray()), plainDataStream, decryptCipher);
        assertThat(plainDataStream.toByteArray()).isEqualTo(plainData);
    }

    @Test
    void testSymmetric_stream_cbc()
        throws IOException {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.SM4_CBC_PKCS5PADDING;
        AlgorithmParameterGenerator algorithmParameterGenerator = CryptoMx.SymmetricAlgorithmParameterGeneratorBuilder.newBuilder(algorithm).build();
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5f");
        AlgorithmParameters algorithmParameter = CryptoMx.generateAlgorithmParameter(algorithmParameterGenerator);
        Cipher encryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForEncrypt();
        Cipher decryptCipher = CryptoMx.SymmetricCipherBuilder.newBuilder(algorithm).setKey(keyData, algorithm).setAlgorithmParameter(algorithmParameter)
            .buildForDecrypt();
        byte[] plainData = UTF8.toBinary(RandomStringUtils.random(16 * 1024 + 200));

        ByteArrayOutputStream cipherDataStream = new ByteArrayOutputStream();
        CryptoMx.encrypt(new ByteArrayInputStream(plainData), cipherDataStream, encryptCipher);
        ByteArrayOutputStream plainDataStream = new ByteArrayOutputStream();
        CryptoMx.decrypt(new ByteArrayInputStream(cipherDataStream.toByteArray()), plainDataStream, decryptCipher);
        assertThat(plainDataStream.toByteArray()).isEqualTo(plainData);
    }

    @Test
    void testGetKeyPairGenerator_asymmetric() {
        CryptoAlgorithm.Asymmetric algorithm = CryptoAlgorithm.Asymmetric.RSA_NONE_PKCS1PADDING;

        KeyPairGenerator keyPairGenerator = CryptoMx.getKeyPairGenerator(algorithm);
        assertThat(keyPairGenerator.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testAsymmetricKeyPairGeneratorBuilder() {
        CryptoAlgorithm.Asymmetric algorithm = CryptoAlgorithm.Asymmetric.RSA_NONE_PKCS1PADDING;

        KeyPairGenerator keyPairGenerator = CryptoMx.AsymmetricKeyPairGeneratorBuilder.newBuilder(algorithm).build();
        assertThat(keyPairGenerator.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testAsymmetricKeyPairGeneratorBuilder_random() {
        CryptoAlgorithm.Asymmetric algorithm = CryptoAlgorithm.Asymmetric.RSA_NONE_PKCS1PADDING;
        CryptoAlgorithm.Random randomAlgorithm = CryptoAlgorithm.Random.DEFAULT;

        KeyPairGenerator keyPairGenerator = CryptoMx.AsymmetricKeyPairGeneratorBuilder.newBuilder(algorithm).setKeySize(1024).setSecureRandom(randomAlgorithm)
            .build();
        assertThat(keyPairGenerator.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGenerateKeyPair_rsa() {
        CryptoAlgorithm.Asymmetric algorithm = CryptoAlgorithm.Asymmetric.RSA_NONE_PKCS1PADDING;
        KeyPairGenerator keyPairGenerator = CryptoMx.AsymmetricKeyPairGeneratorBuilder.newBuilder(algorithm).build();

        KeyPair keyPair = CryptoMx.generateKeyPair(keyPairGenerator);
        assertThat(keyPair.getPrivate().getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGenerateKeyPair_sm2() {
        CryptoAlgorithm.Asymmetric algorithm = CryptoAlgorithm.Asymmetric.SM2_NONE_NOPADDING;
        KeyPairGenerator keyPairGenerator = CryptoMx.AsymmetricKeyPairGeneratorBuilder.newBuilder(algorithm).build();

        KeyPair keyPair = CryptoMx.generateKeyPair(keyPairGenerator);
        assertThat(keyPair.getPrivate().getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetKeyFactory_asymmetric() {
        CryptoAlgorithm.Asymmetric algorithm = CryptoAlgorithm.Asymmetric.RSA_NONE_PKCS1PADDING;

        KeyFactory keyFactory = CryptoMx.getKeyFactory(algorithm);
        assertThat(keyFactory.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetPrivateKey_rsa() {
        CryptoAlgorithm.Asymmetric algorithm = CryptoAlgorithm.Asymmetric.RSA_NONE_PKCS1PADDING;
        byte[] keyData = rsaKeyPair.getPrivate().getEncoded();

        PrivateKey privateKey = CryptoMx.getPrivateKey(keyData, algorithm);
        assertThat(privateKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetPrivateKey_sm2() {
        CryptoAlgorithm.Asymmetric algorithm = CryptoAlgorithm.Asymmetric.SM2_NONE_NOPADDING;
        byte[] keyData = sm2KeyPair.getPrivate().getEncoded();

        PrivateKey privateKey = CryptoMx.getPrivateKey(keyData, algorithm);
        assertThat(privateKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetPublicKey_rsa() {
        CryptoAlgorithm.Asymmetric algorithm = CryptoAlgorithm.Asymmetric.RSA_NONE_PKCS1PADDING;
        byte[] keyData = rsaKeyPair.getPublic().getEncoded();

        PublicKey publicKey = CryptoMx.getPublicKey(keyData, algorithm);
        assertThat(publicKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetPublicKey_sm2() {
        CryptoAlgorithm.Asymmetric algorithm = CryptoAlgorithm.Asymmetric.SM2_NONE_NOPADDING;
        byte[] keyData = sm2KeyPair.getPublic().getEncoded();

        PublicKey publicKey = CryptoMx.getPublicKey(keyData, algorithm);
        assertThat(publicKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetCipher_asymmetric() {
        CryptoAlgorithm.Asymmetric algorithm = CryptoAlgorithm.Asymmetric.RSA_NONE_PKCS1PADDING;

        Cipher cipher = CryptoMx.getCipher(algorithm);
        assertThat(cipher.getAlgorithm()).isEqualTo(algorithm.transformation);
    }

    @Test
    void testAsymmetricCipherBuilder() {
        CryptoAlgorithm.Asymmetric algorithm = CryptoAlgorithm.Asymmetric.RSA_NONE_PKCS1PADDING;
        byte[] privateKeyData = rsaKeyPair.getPrivate().getEncoded();

        Cipher cipher = CryptoMx.AsymmetricCipherBuilder.newBuilder(algorithm).setPrivateKey(privateKeyData, algorithm).buildForDecrypt();
        assertThat(cipher.getAlgorithm()).isEqualTo(algorithm.transformation);
    }

    @Test
    void testAsymmetricCipherBuilder_random() {
        CryptoAlgorithm.Asymmetric algorithm = CryptoAlgorithm.Asymmetric.RSA_NONE_PKCS1PADDING;
        CryptoAlgorithm.Random randomAlgorithm = CryptoAlgorithm.Random.DEFAULT;
        byte[] privateKeyData = rsaKeyPair.getPrivate().getEncoded();

        Cipher cipher = CryptoMx.AsymmetricCipherBuilder.newBuilder(algorithm).setPrivateKey(privateKeyData, algorithm).setSecureRandom(randomAlgorithm)
            .buildForDecrypt();
        assertThat(cipher.getAlgorithm()).isEqualTo(algorithm.transformation);
    }

    @Test
    void testAsymmetric_rsa() {
        CryptoAlgorithm.Asymmetric algorithm = CryptoAlgorithm.Asymmetric.RSA_NONE_PKCS1PADDING;
        byte[] privateKeyData = rsaKeyPair.getPrivate().getEncoded();
        byte[] publicKeyData = rsaKeyPair.getPublic().getEncoded();
        Cipher encryptCipher = CryptoMx.AsymmetricCipherBuilder.newBuilder(algorithm).setPublicKey(publicKeyData, algorithm).buildForEncrypt();
        Cipher decryptCipher = CryptoMx.AsymmetricCipherBuilder.newBuilder(algorithm).setPrivateKey(privateKeyData, algorithm).buildForDecrypt();
        byte[] plainData = UTF8.toBinary("沧海月明");

        byte[] cipherData = CryptoMx.encrypt(plainData, encryptCipher);
        assertThat(CryptoMx.decrypt(cipherData, decryptCipher)).isEqualTo(plainData);
    }

    @Test
    void testAsymmetric_sm2() {
        CryptoAlgorithm.Asymmetric algorithm = CryptoAlgorithm.Asymmetric.SM2_NONE_NOPADDING;
        byte[] privateKeyData = sm2KeyPair.getPrivate().getEncoded();
        byte[] publicKeyData = sm2KeyPair.getPublic().getEncoded();
        Cipher encryptCipher = CryptoMx.AsymmetricCipherBuilder.newBuilder(algorithm).setPublicKey(publicKeyData, algorithm).buildForEncrypt();
        Cipher decryptCipher = CryptoMx.AsymmetricCipherBuilder.newBuilder(algorithm).setPrivateKey(privateKeyData, algorithm).buildForDecrypt();
        byte[] plainData = UTF8.toBinary("沧海月明");

        byte[] cipherData = CryptoMx.encrypt(plainData, encryptCipher);
        assertThat(CryptoMx.decrypt(cipherData, decryptCipher)).isEqualTo(plainData);
    }

    @Test
    void testGetKeyPairGenerator_sign() {
        CryptoAlgorithm.Sign algorithm = CryptoAlgorithm.Sign.SM3_SM2;

        KeyPairGenerator keyPairGenerator = CryptoMx.getKeyPairGenerator(algorithm);
        assertThat(keyPairGenerator.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testSignKeyPairGeneratorBuilder() {
        CryptoAlgorithm.Sign algorithm = CryptoAlgorithm.Sign.SM3_SM2;

        KeyPairGenerator keyPairGenerator = CryptoMx.SignKeyPairGeneratorBuilder.newBuilder(algorithm).build();
        assertThat(keyPairGenerator.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testSignKeyPairGeneratorBuilder_random() {
        CryptoAlgorithm.Sign algorithm = CryptoAlgorithm.Sign.SM3_SM2;
        CryptoAlgorithm.Random randomAlgorithm = CryptoAlgorithm.Random.DEFAULT;

        KeyPairGenerator keyPairGenerator = CryptoMx.SignKeyPairGeneratorBuilder.newBuilder(algorithm).setKeySize(192).setSecureRandom(randomAlgorithm).build();
        assertThat(keyPairGenerator.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGenerateKeyPair_sign_rsa() {
        CryptoAlgorithm.Sign algorithm = CryptoAlgorithm.Sign.SHA1_RSA;
        KeyPairGenerator keyPairGenerator = CryptoMx.SignKeyPairGeneratorBuilder.newBuilder(algorithm).build();

        KeyPair keyPair = CryptoMx.generateKeyPair(keyPairGenerator);
        assertThat(keyPair.getPrivate().getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGenerateKeyPair_sign_sm2() {
        CryptoAlgorithm.Sign algorithm = CryptoAlgorithm.Sign.SM3_SM2;
        KeyPairGenerator keyPairGenerator = CryptoMx.SignKeyPairGeneratorBuilder.newBuilder(algorithm).build();

        KeyPair keyPair = CryptoMx.generateKeyPair(keyPairGenerator);
        assertThat(keyPair.getPrivate().getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetKeyFactory_sign() {
        CryptoAlgorithm.Sign algorithm = CryptoAlgorithm.Sign.SM3_SM2;

        KeyFactory keyFactory = CryptoMx.getKeyFactory(algorithm);
        assertThat(keyFactory.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetPrivateKey_sign_rsa() {
        CryptoAlgorithm.Sign algorithm = CryptoAlgorithm.Sign.SHA1_RSA;
        byte[] keyData = rsaKeyPair.getPrivate().getEncoded();

        PrivateKey privateKey = CryptoMx.getPrivateKey(keyData, algorithm);
        assertThat(privateKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetPrivateKey_sign_sm2() {
        CryptoAlgorithm.Sign algorithm = CryptoAlgorithm.Sign.SM3_SM2;
        byte[] keyData = sm2KeyPair.getPrivate().getEncoded();

        PrivateKey privateKey = CryptoMx.getPrivateKey(keyData, algorithm);
        assertThat(privateKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetPublicKey_sign_rsa() {
        CryptoAlgorithm.Sign algorithm = CryptoAlgorithm.Sign.SHA1_RSA;
        byte[] keyData = rsaKeyPair.getPublic().getEncoded();

        PublicKey publicKey = CryptoMx.getPublicKey(keyData, algorithm);
        assertThat(publicKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetPublicKey_sign_sm2() {
        CryptoAlgorithm.Sign algorithm = CryptoAlgorithm.Sign.SM3_SM2;
        byte[] keyData = sm2KeyPair.getPublic().getEncoded();

        PublicKey publicKey = CryptoMx.getPublicKey(keyData, algorithm);
        assertThat(publicKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetSignature() {
        CryptoAlgorithm.Sign algorithm = CryptoAlgorithm.Sign.SM3_SM2;

        Signature signature = CryptoMx.getSignature(algorithm);
        assertThat(signature.getAlgorithm()).isEqualTo(algorithm.signAlgorithm);
    }

    @Test
    void testSignatureBuilder() {
        CryptoAlgorithm.Sign algorithm = CryptoAlgorithm.Sign.SM3_SM2;
        byte[] privateKeyData = sm2KeyPair.getPrivate().getEncoded();

        Signature signature = CryptoMx.SignatureBuilder.newBuilder(algorithm).setPrivateKey(privateKeyData, algorithm).buildForSign();
        assertThat(signature.getAlgorithm()).isEqualTo(algorithm.signAlgorithm);
    }

    @Test
    void testSignatureBuilder_random() {
        CryptoAlgorithm.Sign algorithm = CryptoAlgorithm.Sign.SM3_SM2;
        CryptoAlgorithm.Random randomAlgorithm = CryptoAlgorithm.Random.DEFAULT;
        byte[] privateKeyData = sm2KeyPair.getPrivate().getEncoded();

        Signature signature = CryptoMx.SignatureBuilder.newBuilder(algorithm).setPrivateKey(privateKeyData, algorithm).setSecureRandom(randomAlgorithm)
            .buildForSign();
        assertThat(signature.getAlgorithm()).isEqualTo(algorithm.signAlgorithm);
    }

    @Test
    void testSign_rsa() {
        CryptoAlgorithm.Sign algorithm = CryptoAlgorithm.Sign.SHA1_RSA;
        byte[] privateKeyData = rsaKeyPair.getPrivate().getEncoded();
        byte[] publicKeyData = rsaKeyPair.getPublic().getEncoded();
        Signature signSignature = CryptoMx.SignatureBuilder.newBuilder(algorithm).setPrivateKey(privateKeyData, algorithm).buildForSign();
        Signature verifySignature = CryptoMx.SignatureBuilder.newBuilder(algorithm).setPublicKey(publicKeyData, algorithm).buildForVerify();
        byte[] data = UTF8.toBinary("沧海月明");

        byte[] signature = CryptoMx.sign(data, signSignature);
        assertThat(CryptoMx.verify(data, signature, verifySignature)).isTrue();
    }

    @Test
    void testSign_sm2() {
        CryptoAlgorithm.Sign algorithm = CryptoAlgorithm.Sign.SM3_SM2;
        byte[] privateKeyData = sm2KeyPair.getPrivate().getEncoded();
        byte[] publicKeyData = sm2KeyPair.getPublic().getEncoded();
        Signature signSignature = CryptoMx.SignatureBuilder.newBuilder(algorithm).setPrivateKey(privateKeyData, algorithm).buildForSign();
        Signature verifySignature = CryptoMx.SignatureBuilder.newBuilder(algorithm).setPublicKey(publicKeyData, algorithm).buildForVerify();
        byte[] data = UTF8.toBinary("沧海月明");

        byte[] signature = CryptoMx.sign(data, signSignature);
        assertThat(CryptoMx.verify(data, signature, verifySignature)).isTrue();
    }
}
