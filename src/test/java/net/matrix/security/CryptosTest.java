/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.security;

import java.security.GeneralSecurityException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CryptosTest {
    @Test
    public void testHMAC()
        throws GeneralSecurityException {
        String input = "foo message";

        byte[] key = Cryptos.generateHmacSha1Key();
        assertThat(key).hasSize(20);

        byte[] macResult = Cryptos.hmacSha1(input.getBytes(), key);
        assertThat(Cryptos.isMacValid(macResult, input.getBytes(), key)).isTrue();
    }

    @Test
    public void testAES()
        throws GeneralSecurityException {
        String input = "foo message";

        byte[] key = Cryptos.generateAesKey();
        assertThat(key).hasSize(16);

        byte[] encryptResult = Cryptos.aesEncrypt(input.getBytes(), key);
        String descryptResult = new String(Cryptos.aesDecrypt(encryptResult, key));
        assertThat(descryptResult).isEqualTo(input);
    }

    @Test
    public void testAESWithIV()
        throws GeneralSecurityException {
        String input = "foo message";

        byte[] key = Cryptos.generateAesKey();
        byte[] iv = Cryptos.generateIV();
        assertThat(key).hasSize(16);
        assertThat(iv).hasSize(16);

        byte[] encryptResult = Cryptos.aesEncrypt(input.getBytes(), key, iv);
        String descryptResult = new String(Cryptos.aesDecrypt(encryptResult, key, iv));
        assertThat(descryptResult).isEqualTo(input);
    }
}
