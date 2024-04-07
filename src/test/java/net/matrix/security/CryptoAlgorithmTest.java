/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CryptoAlgorithmTest {
    @Test
    public void testRandom() {
        CryptoAlgorithm.Random random = CryptoAlgorithm.Random.DEFAULT;

        assertThat(CryptoAlgorithm.Random.forCode(random.algorithm)).isSameAs(random);
    }

    @Test
    public void testDigest() {
        CryptoAlgorithm.Digest digest = CryptoAlgorithm.Digest.MD5;

        assertThat(CryptoAlgorithm.Digest.forCode(digest.algorithm)).isSameAs(digest);
    }

    @Test
    public void testSymmetric() {
        CryptoAlgorithm.Symmetric symmetric = CryptoAlgorithm.Symmetric.SM4_ECB_NOPADDING;

        assertThat(CryptoAlgorithm.Symmetric.forCode(symmetric.transformation)).isSameAs(symmetric);
    }

    @Test
    public void testMac() {
        CryptoAlgorithm.Mac mac = CryptoAlgorithm.Mac.HMAC_MD5;

        assertThat(CryptoAlgorithm.Mac.forCode(mac.algorithm)).isSameAs(mac);
    }

    @Test
    public void testAsymmetric() {
        CryptoAlgorithm.Asymmetric asymmetric = CryptoAlgorithm.Asymmetric.SM2_NONE_NOPADDING;

        assertThat(CryptoAlgorithm.Asymmetric.forCode(asymmetric.transformation)).isSameAs(asymmetric);
    }

    @Test
    public void testSign() {
        CryptoAlgorithm.Sign sign = CryptoAlgorithm.Sign.SM3_SM2;

        assertThat(CryptoAlgorithm.Sign.forCode(sign.signAlgorithm)).isSameAs(sign);
    }
}
