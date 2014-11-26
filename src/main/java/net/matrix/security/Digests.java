/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import net.matrix.lang.ImpossibleException;

/**
 * 支持 SHA-1/MD5 消息摘要的工具类。
 */
public final class Digests {
	/**
	 * SHA-1 算法名。
	 */
	private static final String SHA1_NAME = "SHA-1";

	/**
	 * MD5 算法名。
	 */
	private static final String MD5_NAME = "MD5";

	/**
	 * 内部随机量。
	 */
	private static final SecureRandom RANDOM = new SecureRandom();

	/**
	 * 阻止实例化。
	 */
	private Digests() {
	}

	/**
	 * 对输入字节数组进行 sha1 散列。
	 * 
	 * @param input
	 *            输入
	 * @return 散列码
	 */
	public static byte[] sha1(final byte[] input) {
		return digest(input, SHA1_NAME, null, 1);
	}

	/**
	 * 对输入字节数组进行 sha1 散列。
	 * 
	 * @param input
	 *            输入
	 * @param salt
	 *            扰码
	 * @return 散列码
	 */
	public static byte[] sha1(final byte[] input, final byte[] salt) {
		return digest(input, SHA1_NAME, salt, 1);
	}

	/**
	 * 对输入字节数组进行 sha1 散列。
	 * 
	 * @param input
	 *            输入
	 * @param salt
	 *            扰码
	 * @param iterations
	 *            计算轮数
	 * @return 散列码
	 */
	public static byte[] sha1(final byte[] input, final byte[] salt, final int iterations) {
		return digest(input, SHA1_NAME, salt, iterations);
	}

	/**
	 * 对字节数组进行散列，支持 md5 与 sha1 算法。
	 * 
	 * @param input
	 *            输入
	 * @param algorithm
	 *            算法名
	 * @param salt
	 *            扰码
	 * @param iterations
	 *            计算轮数
	 * @return 散列码
	 */
	private static byte[] digest(final byte[] input, final String algorithm, final byte[] salt, final int iterations) {
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);

			if (salt != null) {
				digest.update(salt);
			}

			byte[] result = digest.digest(input);

			for (int i = 1; i < iterations; i++) {
				digest.reset();
				result = digest.digest(result);
			}
			return result;
		} catch (NoSuchAlgorithmException e) {
			throw new ImpossibleException(e);
		}
	}

	/**
	 * 生成随机的 Byte[] 作为 salt。
	 * 
	 * @param numBytes
	 *            byte 数组的大小
	 * @return 扰码
	 */
	public static byte[] generateSalt(final int numBytes) {
		byte[] bytes = new byte[numBytes];
		RANDOM.nextBytes(bytes);
		return bytes;
	}

	/**
	 * 对输入流进行 md5 散列。
	 * 
	 * @param input
	 *            输入流
	 * @return 散列码
	 * @throws IOException
	 *             从输入流读取数据失败
	 */
	public static byte[] md5(final InputStream input)
		throws IOException {
		return digest(input, MD5_NAME);
	}

	/**
	 * 对输入流进行 sha1 散列。
	 * 
	 * @param input
	 *            输入流
	 * @return 散列码
	 * @throws IOException
	 *             从输入流读取数据失败
	 */
	public static byte[] sha1(final InputStream input)
		throws IOException {
		return digest(input, SHA1_NAME);
	}

	/**
	 * 对输入流进行散列。
	 * 
	 * @param input
	 *            输入流
	 * @param algorithm
	 *            算法名
	 * @return 散列码
	 * @throws IOException
	 *             从输入流读取数据失败
	 */
	private static byte[] digest(final InputStream input, final String algorithm)
		throws IOException {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			final int bufferLength = 8 * 1024;
			byte[] buffer = new byte[bufferLength];
			int read = input.read(buffer, 0, bufferLength);

			while (read > -1) {
				messageDigest.update(buffer, 0, read);
				read = input.read(buffer, 0, bufferLength);
			}

			return messageDigest.digest();
		} catch (NoSuchAlgorithmException e) {
			throw new ImpossibleException(e);
		}
	}
}
