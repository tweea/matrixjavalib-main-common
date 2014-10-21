/*
 * $Id: CryptosTest.java 800 2013-12-26 06:22:14Z tweea@263.net $
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.security;

import java.security.GeneralSecurityException;

import org.junit.Assert;
import org.junit.Test;

public class CryptosTest {
	@Test
	public void mac()
		throws GeneralSecurityException {
		String input = "foo message";

		// key 可为任意字符串
		// byte[] key = "a foo key".getBytes();
		byte[] key = Cryptos.generateHmacSha1Key();
		Assert.assertEquals(20, key.length);

		byte[] macResult = Cryptos.hmacSha1(input.getBytes(), key);

		Assert.assertTrue(Cryptos.isMacValid(macResult, input.getBytes(), key));
	}

	@Test
	public void aes()
		throws GeneralSecurityException {
		byte[] key = Cryptos.generateAesKey();
		Assert.assertEquals(16, key.length);
		String input = "foo message";

		byte[] encryptResult = Cryptos.aesEncrypt(input.getBytes(), key);
		String descryptResult = new String(Cryptos.aesDecrypt(encryptResult, key));

		Assert.assertEquals(input, descryptResult);
	}

	@Test
	public void aesWithIV()
		throws GeneralSecurityException {
		byte[] key = Cryptos.generateAesKey();
		byte[] iv = Cryptos.generateIV();
		Assert.assertEquals(16, key.length);
		Assert.assertEquals(16, iv.length);
		String input = "foo message";

		byte[] encryptResult = Cryptos.aesEncrypt(input.getBytes(), key, iv);
		String descryptResult = new String(Cryptos.aesDecrypt(encryptResult, key, iv));

		Assert.assertEquals(input, descryptResult);
	}
}
