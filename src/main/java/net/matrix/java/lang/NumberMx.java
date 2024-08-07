/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.lang;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * 数字工具。
 */
@ThreadSafe
public final class NumberMx {
    /**
     * 阻止实例化。
     */
    private NumberMx() {
    }

    /**
     * 解析字符串为 {@code int} 类型。
     * 
     * @param string
     *     字符串。
     * @return {@code int} 类型。
     */
    public static int toInt(@Nullable String string) {
        return toInt(string, 0);
    }

    /**
     * 解析字符串为 {@code int} 类型。
     * 
     * @param string
     *     字符串。
     * @param defaultValue
     *     默认值。
     * @return {@code int} 类型。
     */
    public static int toInt(@Nullable String string, int defaultValue) {
        if (StringUtils.isBlank(string)) {
            return defaultValue;
        }

        return Integer.parseInt(string);
    }

    /**
     * 解析字符串为 {@code long} 类型。
     * 
     * @param string
     *     字符串。
     * @return {@code long} 类型。
     */
    public static long toLong(@Nullable String string) {
        return toLong(string, 0);
    }

    /**
     * 解析字符串为 {@code long} 类型。
     * 
     * @param string
     *     字符串。
     * @param defaultValue
     *     默认值。
     * @return {@code long} 类型。
     */
    public static long toLong(@Nullable String string, long defaultValue) {
        if (StringUtils.isBlank(string)) {
            return defaultValue;
        }

        return Long.parseLong(string);
    }

    /**
     * 解析字符串为 {@code float} 类型。
     * 
     * @param string
     *     字符串。
     * @return {@code float} 类型。
     */
    public static float toFloat(@Nullable String string) {
        return toFloat(string, 0);
    }

    /**
     * 解析字符串为 {@code float} 类型。
     * 
     * @param string
     *     字符串。
     * @param defaultValue
     *     默认值。
     * @return {@code float} 类型。
     */
    public static float toFloat(@Nullable String string, float defaultValue) {
        if (StringUtils.isBlank(string)) {
            return defaultValue;
        }

        return Float.parseFloat(string);
    }

    /**
     * 解析字符串为 {@code double} 类型。
     * 
     * @param string
     *     字符串。
     * @return {@code double} 类型。
     */
    public static double toDouble(@Nullable String string) {
        return toDouble(string, 0);
    }

    /**
     * 解析字符串为 {@code double} 类型。
     * 
     * @param string
     *     字符串。
     * @param defaultValue
     *     默认值。
     * @return {@code double} 类型。
     */
    public static double toDouble(@Nullable String string, double defaultValue) {
        if (StringUtils.isBlank(string)) {
            return defaultValue;
        }

        return Double.parseDouble(string);
    }

    /**
     * 解析字符串为 {@link Integer} 类型。
     * 
     * @param string
     *     字符串。
     * @return {@link Integer} 类型。
     */
    @Nullable
    public static Integer parseInteger(@Nullable String string) {
        return parseInteger(string, null);
    }

    /**
     * 解析字符串为 {@link Integer} 类型。
     * 
     * @param string
     *     字符串。
     * @param defaultValue
     *     默认值。
     * @return {@link Integer} 类型。
     */
    @Nullable
    public static Integer parseInteger(@Nullable String string, @Nullable Integer defaultValue) {
        if (StringUtils.isBlank(string)) {
            return defaultValue;
        }

        return Integer.decode(string);
    }

    /**
     * 解析字符串为 {@link Long} 类型。
     * 
     * @param string
     *     字符串。
     * @return {@link Long} 类型。
     */
    @Nullable
    public static Long parseLong(@Nullable String string) {
        return parseLong(string, null);
    }

    /**
     * 解析字符串为 {@link Long} 类型。
     * 
     * @param string
     *     字符串。
     * @param defaultValue
     *     默认值。
     * @return {@link Long} 类型。
     */
    @Nullable
    public static Long parseLong(@Nullable String string, @Nullable Long defaultValue) {
        if (StringUtils.isBlank(string)) {
            return defaultValue;
        }

        return Long.decode(string);
    }

    /**
     * 解析字符串为 {@link Float} 类型。
     * 
     * @param string
     *     字符串。
     * @return {@link Float} 类型。
     */
    @Nullable
    public static Float parseFloat(@Nullable String string) {
        return parseFloat(string, null);
    }

    /**
     * 解析字符串为 {@link Float} 类型。
     * 
     * @param string
     *     字符串。
     * @param defaultValue
     *     默认值。
     * @return {@link Float} 类型。
     */
    @Nullable
    public static Float parseFloat(@Nullable String string, @Nullable Float defaultValue) {
        if (StringUtils.isBlank(string)) {
            return defaultValue;
        }

        return Float.valueOf(string);
    }

    /**
     * 解析字符串为 {@link Double} 类型。
     * 
     * @param string
     *     字符串。
     * @return {@link Double} 类型。
     */
    @Nullable
    public static Double parseDouble(@Nullable String string) {
        return parseDouble(string, null);
    }

    /**
     * 解析字符串为 {@link Double} 类型。
     * 
     * @param string
     *     字符串。
     * @param defaultValue
     *     默认值。
     * @return {@link Double} 类型。
     */
    @Nullable
    public static Double parseDouble(@Nullable String string, @Nullable Double defaultValue) {
        if (StringUtils.isBlank(string)) {
            return defaultValue;
        }

        return Double.valueOf(string);
    }

    /**
     * 解析字符串为 {@link BigInteger} 类型。
     * 
     * @param string
     *     字符串。
     * @return {@link BigInteger} 类型。
     */
    @Nullable
    public static BigInteger parseBigInteger(@Nullable String string) {
        return parseBigInteger(string, null);
    }

    /**
     * 解析字符串为 {@link BigInteger} 类型。
     * 
     * @param string
     *     字符串。
     * @param defaultValue
     *     默认值。
     * @return {@link BigInteger} 类型。
     */
    @Nullable
    public static BigInteger parseBigInteger(@Nullable String string, @Nullable BigInteger defaultValue) {
        if (StringUtils.isBlank(string)) {
            return defaultValue;
        }

        return NumberUtils.createBigInteger(string);
    }

    /**
     * 解析字符串为 {@link BigDecimal} 类型。
     * 
     * @param string
     *     字符串。
     * @return {@link BigDecimal} 类型。
     */
    @Nullable
    public static BigDecimal parseBigDecimal(@Nullable String string) {
        return parseBigDecimal(string, null);
    }

    /**
     * 解析字符串为 {@link BigDecimal} 类型。
     * 
     * @param string
     *     字符串。
     * @param defaultValue
     *     默认值。
     * @return {@link BigDecimal} 类型。
     */
    @Nullable
    public static BigDecimal parseBigDecimal(@Nullable String string, @Nullable BigDecimal defaultValue) {
        if (StringUtils.isBlank(string)) {
            return defaultValue;
        }

        return new BigDecimal(string);
    }

    /**
     * 将 {@link Integer} 类型 <code>null</code> 值转换为 0。
     * 
     * @param value
     *     数值。
     * @return {@link Integer} 类型。
     */
    @Nonnull
    public static Integer zeroIfNull(@Nullable Integer value) {
        return ObjectUtils.defaultIfNull(value, NumberUtils.INTEGER_ZERO);
    }

    /**
     * 将 {@link Long} 类型 <code>null</code> 值转换为 0。
     * 
     * @param value
     *     数值。
     * @return {@link Long} 类型。
     */
    @Nonnull
    public static Long zeroIfNull(@Nullable Long value) {
        return ObjectUtils.defaultIfNull(value, NumberUtils.LONG_ZERO);
    }

    /**
     * 将 {@link Float} 类型 <code>null</code> 值转换为 0。
     * 
     * @param value
     *     数值。
     * @return {@link Float} 类型。
     */
    @Nonnull
    public static Float zeroIfNull(@Nullable Float value) {
        return ObjectUtils.defaultIfNull(value, NumberUtils.FLOAT_ZERO);
    }

    /**
     * 将 {@link Double} 类型 <code>null</code> 值转换为 0。
     * 
     * @param value
     *     数值。
     * @return {@link Double} 类型。
     */
    @Nonnull
    public static Double zeroIfNull(@Nullable Double value) {
        return ObjectUtils.defaultIfNull(value, NumberUtils.DOUBLE_ZERO);
    }

    /**
     * 将 {@link BigInteger} 类型 <code>null</code> 值转换为 0。
     * 
     * @param value
     *     数值。
     * @return {@link BigInteger} 类型。
     */
    @Nonnull
    public static BigInteger zeroIfNull(@Nullable BigInteger value) {
        return ObjectUtils.defaultIfNull(value, BigInteger.ZERO);
    }

    /**
     * 将 {@link BigDecimal} 类型 <code>null</code> 值转换为 0。
     * 
     * @param value
     *     数值。
     * @return {@link BigDecimal} 类型。
     */
    @Nonnull
    public static BigDecimal zeroIfNull(@Nullable BigDecimal value) {
        return ObjectUtils.defaultIfNull(value, BigDecimal.ZERO);
    }
}
