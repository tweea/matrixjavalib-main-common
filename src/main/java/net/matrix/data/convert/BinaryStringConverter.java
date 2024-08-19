/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.data.convert;

import java.nio.charset.StandardCharsets;

import javax.annotation.Nullable;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.BinaryCodec;
import org.apache.commons.codec.binary.Hex;

/**
 * 在字节数组和字符串之间转换。
 */
public interface BinaryStringConverter {
    /**
     * 使用 UTF-8 编码。
     */
    BinaryStringConverter UTF8 = new BinaryStringConverter() {
        @Override
        public String toString(byte[] binary) {
            if (binary == null) {
                return null;
            }

            return new String(binary, StandardCharsets.UTF_8);
        }

        @Override
        public byte[] toBinary(String string) {
            if (string == null) {
                return null;
            }

            return string.getBytes(StandardCharsets.UTF_8);
        }
    };

    /**
     * 由 0 和 1 组成的字符串。
     */
    BinaryStringConverter BIT = new BinaryStringConverter() {
        @Override
        public String toString(byte[] binary) {
            if (binary == null) {
                return null;
            }

            return BinaryCodec.toAsciiString(binary);
        }

        @Override
        public byte[] toBinary(String string) {
            if (string == null) {
                return null;
            }

            return BinaryCodec.fromAscii(string.toCharArray());
        }
    };

    /**
     * 十六进制字符串。
     */
    BinaryStringConverter HEX = new BinaryStringConverter() {
        @Override
        public String toString(byte[] binary) {
            if (binary == null) {
                return null;
            }

            return Hex.encodeHexString(binary);
        }

        @Override
        public byte[] toBinary(String string) {
            if (string == null) {
                return null;
            }

            try {
                return Hex.decodeHex(string);
            } catch (DecoderException e) {
                throw new IllegalArgumentException(e);
            }
        }
    };

    /**
     * Base64 字符串。
     */
    BinaryStringConverter BASE64 = new BinaryStringConverter() {
        @Override
        public String toString(byte[] binary) {
            if (binary == null) {
                return null;
            }

            return Base64.encodeBase64String(binary);
        }

        @Override
        public byte[] toBinary(String string) {
            if (string == null) {
                return null;
            }

            return Base64.decodeBase64(string);
        }
    };

    /**
     * URL 安全变体的 Base64 字符串。
     */
    BinaryStringConverter BASE64_URL_SAFE = new BinaryStringConverter() {
        @Override
        public String toString(byte[] binary) {
            if (binary == null) {
                return null;
            }

            return Base64.encodeBase64URLSafeString(binary);
        }

        @Override
        public byte[] toBinary(String string) {
            if (string == null) {
                return null;
            }

            return Base64.decodeBase64(string);
        }
    };

    /**
     * 字节数组转换为字符串。
     *
     * @param binary
     *     字节数组。
     * @return 字符串。
     */
    @Nullable
    String toString(@Nullable byte[] binary);

    /**
     * 字符串转换为字节数组。
     *
     * @param string
     *     字符串。
     * @return 字节数组。
     */
    @Nullable
    byte[] toBinary(@Nullable String string);
}
