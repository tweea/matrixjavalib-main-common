/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.security;

import java.security.GeneralSecurityException;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class CryptosTest {
	@Test
	public void mac()
		throws GeneralSecurityException {
		String input = "foo message";

		// key 可为任意字符串
		// byte[] key = "a foo key".getBytes();
		byte[] key = Cryptos.generateHmacSha1Key();
		Assertions.assertThat(key).hasSize(20);

		byte[] macResult = Cryptos.hmacSha1(input.getBytes(), key);
		Assertions.assertThat(Cryptos.isMacValid(macResult, input.getBytes(), key)).isTrue();
	}

	@Test
	public void aes()
		throws GeneralSecurityException {
		String input = "foo message";

		byte[] key = Cryptos.generateAesKey();
		Assertions.assertThat(key).hasSize(16);

		byte[] encryptResult = Cryptos.aesEncrypt(input.getBytes(), key);
		String descryptResult = new String(Cryptos.aesDecrypt(encryptResult, key));
		Assertions.assertThat(descryptResult).isEqualTo(input);
	}

	@Test
	public void aesWithIV()
		throws GeneralSecurityException {
		String input = "foo message";

		byte[] key = Cryptos.generateAesKey();
		byte[] iv = Cryptos.generateIV();
		Assertions.assertThat(key).hasSize(16);
		Assertions.assertThat(iv).hasSize(16);

		byte[] encryptResult = Cryptos.aesEncrypt(input.getBytes(), key, iv);
		String descryptResult = new String(Cryptos.aesDecrypt(encryptResult, key, iv));
		Assertions.assertThat(descryptResult).isEqualTo(input);
	}
}
