/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static net.matrix.data.convert.BinaryStringConverter.HEX;
import static net.matrix.data.convert.BinaryStringConverter.UTF8;

class CryptoMxTest {
    KeyPair rsaKeyPair = CryptoMx.generateKeyPair(CryptoAlgorithm.Asymmetric.RSA_NONE_PKCS1PADDING);

    KeyPair sm2KeyPair = CryptoMx.generateKeyPair(CryptoAlgorithm.Asymmetric.SM2_NONE_NOPADDING);

    @Test
    void testGetDigest() {
        CryptoAlgorithm.Digest algorithm = CryptoAlgorithm.Digest.MD5;

        MessageDigest digest = CryptoMx.getDigest(algorithm);
        Assertions.assertThat(digest.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testDigest_md5() {
        CryptoAlgorithm.Digest algorithm = CryptoAlgorithm.Digest.MD5;
        byte[] plainData = UTF8.toBinary("沧海月明");
        byte[] digestData = HEX.toBinary("5b95c94bbc42391c190ae5e91b26c007");

        Assertions.assertThat(CryptoMx.digest(plainData, algorithm)).isEqualTo(digestData);
    }

    @Test
    void testDigest_sha1() {
        CryptoAlgorithm.Digest algorithm = CryptoAlgorithm.Digest.SHA1;
        byte[] plainData = UTF8.toBinary("沧海月明");
        byte[] digestData = HEX.toBinary("0f88beb5d269a339887aca1d769edc7c88f7eab0");

        Assertions.assertThat(CryptoMx.digest(plainData, algorithm)).isEqualTo(digestData);
    }

    @Test
    void testDigest_sm3() {
        CryptoAlgorithm.Digest algorithm = CryptoAlgorithm.Digest.SM3;
        byte[] plainData = UTF8.toBinary("沧海月明");
        byte[] digestData = HEX.toBinary("e5aee80250131e14ca2a0e2165a7759b47b2285dadd5ece90251b79c2cd9f7b1");

        Assertions.assertThat(CryptoMx.digest(plainData, algorithm)).isEqualTo(digestData);
    }

    @Test
    void testDigest_stream()
        throws IOException {
        CryptoAlgorithm.Digest algorithm = CryptoAlgorithm.Digest.MD5;
        byte[] plainData = UTF8.toBinary("沧海月明");
        byte[] digestData = HEX.toBinary("5b95c94bbc42391c190ae5e91b26c007");

        Assertions.assertThat(CryptoMx.digest(new ByteArrayInputStream(plainData), algorithm)).isEqualTo(digestData);
    }

    @Test
    void testGetCipher_symmetric() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_ECB_NOPADDING;

        Cipher cipher = CryptoMx.getCipher(algorithm);
        Assertions.assertThat(cipher.getAlgorithm()).isEqualTo(algorithm.transformation);
    }

    @Test
    void testGetKeyGenerator() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_ECB_NOPADDING;

        KeyGenerator keyGenerator = CryptoMx.getKeyGenerator(algorithm);
        Assertions.assertThat(keyGenerator.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGenerateSecretKey_des() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_ECB_NOPADDING;

        SecretKey secretKey = CryptoMx.generateSecretKey(algorithm);
        Assertions.assertThat(secretKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGenerateSecretKey_desede() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DESEDE_ECB_NOPADDING;

        SecretKey secretKey = CryptoMx.generateSecretKey(algorithm);
        Assertions.assertThat(secretKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGenerateSecretKey_aes() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.AES_ECB_NOPADDING;

        SecretKey secretKey = CryptoMx.generateSecretKey(algorithm);
        Assertions.assertThat(secretKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGenerateSecretKey_sm4() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.SM4_ECB_NOPADDING;

        SecretKey secretKey = CryptoMx.generateSecretKey(algorithm);
        Assertions.assertThat(secretKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetSecretKey_des() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_ECB_NOPADDING;
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c");

        SecretKey secretKey = CryptoMx.getSecretKey(keyData, algorithm);
        Assertions.assertThat(secretKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetSecretKey_desede() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DESEDE_ECB_NOPADDING;
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");

        SecretKey secretKey = CryptoMx.getSecretKey(keyData, algorithm);
        Assertions.assertThat(secretKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetSecretKey_aes() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.AES_ECB_NOPADDING;
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");

        SecretKey secretKey = CryptoMx.getSecretKey(keyData, algorithm);
        Assertions.assertThat(secretKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetSecretKey_sm4() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.SM4_ECB_NOPADDING;
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5f");

        SecretKey secretKey = CryptoMx.getSecretKey(keyData, algorithm);
        Assertions.assertThat(secretKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testEncrypt_des_no() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_ECB_NOPADDING;
        byte[] plainData = Arrays.copyOf(UTF8.toBinary("沧海月明"), 16);
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c");
        byte[] cipherData = HEX.toBinary("34c6973019c198d96aabb6dcbb70854d");

        Assertions.assertThat(CryptoMx.encrypt(plainData, keyData, algorithm)).isEqualTo(cipherData);
    }

    @Test
    void testDecrypt_des_no() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_ECB_NOPADDING;
        byte[] plainData = Arrays.copyOf(UTF8.toBinary("沧海月明"), 16);
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c");
        byte[] cipherData = HEX.toBinary("34c6973019c198d96aabb6dcbb70854d");

        Assertions.assertThat(CryptoMx.decrypt(cipherData, keyData, algorithm)).isEqualTo(plainData);
    }

    @Test
    void testEncrypt_des_zero() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_ECB_ZEROBYTEPADDING;
        byte[] plainData = UTF8.toBinary("沧海月明");
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c");
        byte[] cipherData = HEX.toBinary("34c6973019c198d96aabb6dcbb70854d");

        Assertions.assertThat(CryptoMx.encrypt(plainData, keyData, algorithm)).isEqualTo(cipherData);
    }

    @Test
    void testDecrypt_des_zero() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_ECB_ZEROBYTEPADDING;
        byte[] plainData = UTF8.toBinary("沧海月明");
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c");
        byte[] cipherData = HEX.toBinary("34c6973019c198d96aabb6dcbb70854d");

        Assertions.assertThat(CryptoMx.decrypt(cipherData, keyData, algorithm)).isEqualTo(plainData);
    }

    @Test
    void testEncrypt_des_pkcs5() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_ECB_PKCS5PADDING;
        byte[] plainData = UTF8.toBinary("沧海月明");
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c");
        byte[] cipherData = HEX.toBinary("34c6973019c198d9e1b30a0356794508");

        Assertions.assertThat(CryptoMx.encrypt(plainData, keyData, algorithm)).isEqualTo(cipherData);
    }

    @Test
    void testDecrypt_des_pkcs5() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DES_ECB_PKCS5PADDING;
        byte[] plainData = UTF8.toBinary("沧海月明");
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c");
        byte[] cipherData = HEX.toBinary("34c6973019c198d9e1b30a0356794508");

        Assertions.assertThat(CryptoMx.decrypt(cipherData, keyData, algorithm)).isEqualTo(plainData);
    }

    @Test
    void testEncrypt_desede_no() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DESEDE_ECB_NOPADDING;
        byte[] plainData = Arrays.copyOf(UTF8.toBinary("沧海月明"), 16);
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");
        byte[] cipherData = HEX.toBinary("DB481C8419B7427F5317DED078263235");

        Assertions.assertThat(CryptoMx.encrypt(plainData, keyData, algorithm)).isEqualTo(cipherData);
    }

    @Test
    void testDecrypt_desede_no() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DESEDE_ECB_NOPADDING;
        byte[] plainData = Arrays.copyOf(UTF8.toBinary("沧海月明"), 16);
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");
        byte[] cipherData = HEX.toBinary("DB481C8419B7427F5317DED078263235");

        Assertions.assertThat(CryptoMx.decrypt(cipherData, keyData, algorithm)).isEqualTo(plainData);
    }

    @Test
    void testEncrypt_desede_zero() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DESEDE_ECB_ZEROBYTEPADDING;
        byte[] plainData = UTF8.toBinary("沧海月明");
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");
        byte[] cipherData = HEX.toBinary("DB481C8419B7427F5317DED078263235");

        Assertions.assertThat(CryptoMx.encrypt(plainData, keyData, algorithm)).isEqualTo(cipherData);
    }

    @Test
    void testDecrypt_desede_zero() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DESEDE_ECB_ZEROBYTEPADDING;
        byte[] plainData = UTF8.toBinary("沧海月明");
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");
        byte[] cipherData = HEX.toBinary("DB481C8419B7427F5317DED078263235");

        Assertions.assertThat(CryptoMx.decrypt(cipherData, keyData, algorithm)).isEqualTo(plainData);
    }

    @Test
    void testEncrypt_desede_pkcs5() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DESEDE_ECB_PKCS5PADDING;
        byte[] plainData = UTF8.toBinary("沧海月明");
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");
        byte[] cipherData = HEX.toBinary("DB481C8419B7427FEF9F9389A3F996AD");

        Assertions.assertThat(CryptoMx.encrypt(plainData, keyData, algorithm)).isEqualTo(cipherData);
    }

    @Test
    void testDecrypt_desede_pkcs5() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.DESEDE_ECB_PKCS5PADDING;
        byte[] plainData = UTF8.toBinary("沧海月明");
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");
        byte[] cipherData = HEX.toBinary("DB481C8419B7427FEF9F9389A3F996AD");

        Assertions.assertThat(CryptoMx.decrypt(cipherData, keyData, algorithm)).isEqualTo(plainData);
    }

    @Test
    void testEncrypt_aes_no() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.AES_ECB_NOPADDING;
        byte[] plainData = Arrays.copyOf(UTF8.toBinary("沧海月明"), 16);
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");
        byte[] cipherData = HEX.toBinary("034DE8D5305F57BB9732719D7AADBB4C");

        Assertions.assertThat(CryptoMx.encrypt(plainData, keyData, algorithm)).isEqualTo(cipherData);
    }

    @Test
    void testDecrypt_aes_no() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.AES_ECB_NOPADDING;
        byte[] plainData = Arrays.copyOf(UTF8.toBinary("沧海月明"), 16);
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");
        byte[] cipherData = HEX.toBinary("034DE8D5305F57BB9732719D7AADBB4C");

        Assertions.assertThat(CryptoMx.decrypt(cipherData, keyData, algorithm)).isEqualTo(plainData);
    }

    @Test
    void testEncrypt_aes_zero() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.AES_ECB_ZEROBYTEPADDING;
        byte[] plainData = UTF8.toBinary("沧海月明");
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");
        byte[] cipherData = HEX.toBinary("034DE8D5305F57BB9732719D7AADBB4C");

        Assertions.assertThat(CryptoMx.encrypt(plainData, keyData, algorithm)).isEqualTo(cipherData);
    }

    @Test
    void testDecrypt_aes_zero() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.AES_ECB_ZEROBYTEPADDING;
        byte[] plainData = UTF8.toBinary("沧海月明");
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");
        byte[] cipherData = HEX.toBinary("034DE8D5305F57BB9732719D7AADBB4C");

        Assertions.assertThat(CryptoMx.decrypt(cipherData, keyData, algorithm)).isEqualTo(plainData);
    }

    @Test
    void testEncrypt_aes_pkcs5() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.AES_ECB_PKCS5PADDING;
        byte[] plainData = UTF8.toBinary("沧海月明");
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");
        byte[] cipherData = HEX.toBinary("8A342C2709BC1093A379B868BB77FB26");

        Assertions.assertThat(CryptoMx.encrypt(plainData, keyData, algorithm)).isEqualTo(cipherData);
    }

    @Test
    void testDecrypt_aes_pkcs5() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.AES_ECB_PKCS5PADDING;
        byte[] plainData = UTF8.toBinary("沧海月明");
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5fae44c3716e699e8c");
        byte[] cipherData = HEX.toBinary("8A342C2709BC1093A379B868BB77FB26");

        Assertions.assertThat(CryptoMx.decrypt(cipherData, keyData, algorithm)).isEqualTo(plainData);
    }

    @Test
    void testEncrypt_sm4_no() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.SM4_ECB_NOPADDING;
        byte[] plainData = Arrays.copyOf(UTF8.toBinary("沧海月明"), 16);
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5f");
        byte[] cipherData = HEX.toBinary("84D11964A5607018F1EEBE87B764D8DB");

        Assertions.assertThat(CryptoMx.encrypt(plainData, keyData, algorithm)).isEqualTo(cipherData);
    }

    @Test
    void testDecrypt_sm4_no() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.SM4_ECB_NOPADDING;
        byte[] plainData = Arrays.copyOf(UTF8.toBinary("沧海月明"), 16);
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5f");
        byte[] cipherData = HEX.toBinary("84D11964A5607018F1EEBE87B764D8DB");

        Assertions.assertThat(CryptoMx.decrypt(cipherData, keyData, algorithm)).isEqualTo(plainData);
    }

    @Test
    void testEncrypt_sm4_zero() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.SM4_ECB_ZEROBYTEPADDING;
        byte[] plainData = UTF8.toBinary("沧海月明");
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5f");
        byte[] cipherData = HEX.toBinary("84D11964A5607018F1EEBE87B764D8DB");

        Assertions.assertThat(CryptoMx.encrypt(plainData, keyData, algorithm)).isEqualTo(cipherData);
    }

    @Test
    void testDecrypt_sm4_zero() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.SM4_ECB_ZEROBYTEPADDING;
        byte[] plainData = UTF8.toBinary("沧海月明");
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5f");
        byte[] cipherData = HEX.toBinary("84D11964A5607018F1EEBE87B764D8DB");

        Assertions.assertThat(CryptoMx.decrypt(cipherData, keyData, algorithm)).isEqualTo(plainData);
    }

    @Test
    void testEncrypt_sm4_pkcs5() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.SM4_ECB_PKCS5PADDING;
        byte[] plainData = UTF8.toBinary("沧海月明");
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5f");
        byte[] cipherData = HEX.toBinary("8B5941833E3BD724E109EF786A8729D8");

        Assertions.assertThat(CryptoMx.encrypt(plainData, keyData, algorithm)).isEqualTo(cipherData);
    }

    @Test
    void testDecrypt_sm4_pkcs5() {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.SM4_ECB_PKCS5PADDING;
        byte[] plainData = UTF8.toBinary("沧海月明");
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5f");
        byte[] cipherData = HEX.toBinary("8B5941833E3BD724E109EF786A8729D8");

        Assertions.assertThat(CryptoMx.decrypt(cipherData, keyData, algorithm)).isEqualTo(plainData);
    }

    @Test
    void testEncrypt_stream()
        throws IOException {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.SM4_ECB_PKCS5PADDING;
        byte[] plainData = UTF8.toBinary(RandomStringUtils.random(16 * 1024 + 200));
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5f");
        byte[] cipherData = CryptoMx.encrypt(plainData, keyData, algorithm);

        ByteArrayOutputStream cipherDataStream = new ByteArrayOutputStream();
        CryptoMx.encrypt(new ByteArrayInputStream(plainData), cipherDataStream, keyData, algorithm);
        Assertions.assertThat(cipherDataStream.toByteArray()).isEqualTo(cipherData);
    }

    @Test
    void testDecrypt_stream()
        throws IOException {
        CryptoAlgorithm.Symmetric algorithm = CryptoAlgorithm.Symmetric.SM4_ECB_PKCS5PADDING;
        byte[] plainData = UTF8.toBinary(RandomStringUtils.random(16 * 1024 + 200));
        byte[] keyData = HEX.toBinary("ae44c3716e699e8c7d2d9dea2ba24a5f");
        byte[] cipherData = CryptoMx.encrypt(plainData, keyData, algorithm);

        ByteArrayOutputStream plainDataStream = new ByteArrayOutputStream();
        CryptoMx.decrypt(new ByteArrayInputStream(cipherData), plainDataStream, keyData, algorithm);
        Assertions.assertThat(plainDataStream.toByteArray()).isEqualTo(plainData);
    }

    @Test
    void testGetCipher_asymmetric() {
        CryptoAlgorithm.Asymmetric algorithm = CryptoAlgorithm.Asymmetric.RSA_NONE_PKCS1PADDING;

        Cipher cipher = CryptoMx.getCipher(algorithm);
        Assertions.assertThat(cipher.getAlgorithm()).isEqualTo(algorithm.transformation);
    }

    @Test
    void testGetKeyPairGenerator_asymmetric() {
        CryptoAlgorithm.Asymmetric algorithm = CryptoAlgorithm.Asymmetric.RSA_NONE_PKCS1PADDING;

        KeyPairGenerator keyPairGenerator = CryptoMx.getKeyPairGenerator(algorithm);
        Assertions.assertThat(keyPairGenerator.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGenerateKeyPair_rsa() {
        CryptoAlgorithm.Asymmetric algorithm = CryptoAlgorithm.Asymmetric.RSA_NONE_PKCS1PADDING;

        KeyPair keyPair = CryptoMx.generateKeyPair(algorithm);
        Assertions.assertThat(keyPair.getPrivate().getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGenerateKeyPair_sm2() {
        CryptoAlgorithm.Asymmetric algorithm = CryptoAlgorithm.Asymmetric.SM2_NONE_NOPADDING;

        KeyPair keyPair = CryptoMx.generateKeyPair(algorithm);
        Assertions.assertThat(keyPair.getPrivate().getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetKeyFactory_asymmetric() {
        CryptoAlgorithm.Asymmetric algorithm = CryptoAlgorithm.Asymmetric.RSA_NONE_PKCS1PADDING;

        KeyFactory keyFactory = CryptoMx.getKeyFactory(algorithm);
        Assertions.assertThat(keyFactory.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetPrivateKey_rsa() {
        CryptoAlgorithm.Asymmetric algorithm = CryptoAlgorithm.Asymmetric.RSA_NONE_PKCS1PADDING;
        byte[] keyData = rsaKeyPair.getPrivate().getEncoded();

        PrivateKey privateKey = CryptoMx.getPrivateKey(keyData, algorithm);
        Assertions.assertThat(privateKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetPrivateKey_sm2() {
        CryptoAlgorithm.Asymmetric algorithm = CryptoAlgorithm.Asymmetric.SM2_NONE_NOPADDING;
        byte[] keyData = sm2KeyPair.getPrivate().getEncoded();

        PrivateKey privateKey = CryptoMx.getPrivateKey(keyData, algorithm);
        Assertions.assertThat(privateKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetPublicKey_rsa() {
        CryptoAlgorithm.Asymmetric algorithm = CryptoAlgorithm.Asymmetric.RSA_NONE_PKCS1PADDING;
        byte[] keyData = rsaKeyPair.getPublic().getEncoded();

        PublicKey publicKey = CryptoMx.getPublicKey(keyData, algorithm);
        Assertions.assertThat(publicKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetPublicKey_sm2() {
        CryptoAlgorithm.Asymmetric algorithm = CryptoAlgorithm.Asymmetric.SM2_NONE_NOPADDING;
        byte[] keyData = sm2KeyPair.getPublic().getEncoded();

        PublicKey publicKey = CryptoMx.getPublicKey(keyData, algorithm);
        Assertions.assertThat(publicKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testEncrypt_rsa() {
        CryptoAlgorithm.Asymmetric algorithm = CryptoAlgorithm.Asymmetric.RSA_NONE_PKCS1PADDING;
        byte[] plainData = UTF8.toBinary("沧海月明");
        byte[] privateKeyData = rsaKeyPair.getPrivate().getEncoded();
        byte[] publicKeyData = rsaKeyPair.getPublic().getEncoded();

        byte[] cipherData = CryptoMx.encryptPublic(plainData, publicKeyData, algorithm);
        Assertions.assertThat(CryptoMx.decryptPrivate(cipherData, privateKeyData, algorithm)).isEqualTo(plainData);
    }

    @Test
    void testEncrypt_sm2() {
        CryptoAlgorithm.Asymmetric algorithm = CryptoAlgorithm.Asymmetric.SM2_NONE_NOPADDING;
        byte[] plainData = UTF8.toBinary("沧海月明");
        byte[] privateKeyData = sm2KeyPair.getPrivate().getEncoded();
        byte[] publicKeyData = sm2KeyPair.getPublic().getEncoded();

        byte[] cipherData = CryptoMx.encryptPublic(plainData, publicKeyData, algorithm);
        Assertions.assertThat(CryptoMx.decryptPrivate(cipherData, privateKeyData, algorithm)).isEqualTo(plainData);
    }

    @Test
    void testGetSignature() {
        CryptoAlgorithm.Sign algorithm = CryptoAlgorithm.Sign.SM3_SM2;

        Signature signature = CryptoMx.getSignature(algorithm);
        Assertions.assertThat(signature.getAlgorithm()).isEqualTo(algorithm.signAlgorithm);
    }

    @Test
    void testGetKeyPairGenerator_sign() {
        CryptoAlgorithm.Sign algorithm = CryptoAlgorithm.Sign.SM3_SM2;

        KeyPairGenerator keyPairGenerator = CryptoMx.getKeyPairGenerator(algorithm);
        Assertions.assertThat(keyPairGenerator.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGenerateKeyPair_sign_rsa() {
        CryptoAlgorithm.Sign algorithm = CryptoAlgorithm.Sign.SHA1_RSA;

        KeyPair keyPair = CryptoMx.generateKeyPair(algorithm);
        Assertions.assertThat(keyPair.getPrivate().getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGenerateKeyPair_sign_sm2() {
        CryptoAlgorithm.Sign algorithm = CryptoAlgorithm.Sign.SM3_SM2;

        KeyPair keyPair = CryptoMx.generateKeyPair(algorithm);
        Assertions.assertThat(keyPair.getPrivate().getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetKeyFactory_sign() {
        CryptoAlgorithm.Sign algorithm = CryptoAlgorithm.Sign.SM3_SM2;

        KeyFactory keyFactory = CryptoMx.getKeyFactory(algorithm);
        Assertions.assertThat(keyFactory.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetPrivateKey_sign_rsa() {
        CryptoAlgorithm.Sign algorithm = CryptoAlgorithm.Sign.SHA1_RSA;
        byte[] keyData = rsaKeyPair.getPrivate().getEncoded();

        PrivateKey privateKey = CryptoMx.getPrivateKey(keyData, algorithm);
        Assertions.assertThat(privateKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetPrivateKey_sign_sm2() {
        CryptoAlgorithm.Sign algorithm = CryptoAlgorithm.Sign.SM3_SM2;
        byte[] keyData = sm2KeyPair.getPrivate().getEncoded();

        PrivateKey privateKey = CryptoMx.getPrivateKey(keyData, algorithm);
        Assertions.assertThat(privateKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetPublicKey_sign_rsa() {
        CryptoAlgorithm.Sign algorithm = CryptoAlgorithm.Sign.SHA1_RSA;
        byte[] keyData = rsaKeyPair.getPublic().getEncoded();

        PublicKey publicKey = CryptoMx.getPublicKey(keyData, algorithm);
        Assertions.assertThat(publicKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testGetPublicKey_sign_sm2() {
        CryptoAlgorithm.Sign algorithm = CryptoAlgorithm.Sign.SM3_SM2;
        byte[] keyData = sm2KeyPair.getPublic().getEncoded();

        PublicKey publicKey = CryptoMx.getPublicKey(keyData, algorithm);
        Assertions.assertThat(publicKey.getAlgorithm()).isEqualTo(algorithm.algorithm);
    }

    @Test
    void testSign_rsa() {
        CryptoAlgorithm.Sign algorithm = CryptoAlgorithm.Sign.SHA1_RSA;
        byte[] data = UTF8.toBinary("沧海月明");
        byte[] privateKeyData = rsaKeyPair.getPrivate().getEncoded();
        byte[] publicKeyData = rsaKeyPair.getPublic().getEncoded();

        byte[] signature = CryptoMx.signPrivate(data, privateKeyData, algorithm);
        Assertions.assertThat(CryptoMx.verifyPublic(data, signature, publicKeyData, algorithm)).isTrue();
    }

    @Test
    void testSign_sm2() {
        CryptoAlgorithm.Sign algorithm = CryptoAlgorithm.Sign.SM3_SM2;
        byte[] data = UTF8.toBinary("沧海月明");
        byte[] privateKeyData = sm2KeyPair.getPrivate().getEncoded();
        byte[] publicKeyData = sm2KeyPair.getPublic().getEncoded();

        byte[] signature = CryptoMx.signPrivate(data, privateKeyData, algorithm);
        Assertions.assertThat(CryptoMx.verifyPublic(data, signature, publicKeyData, algorithm)).isTrue();
    }
}
