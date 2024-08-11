/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.data;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

/**
 * 表示布尔数据的值。
 */
@Immutable
public class BooleanValue<V> {
    /**
     * 使用整形 0 和 1 表示。
     */
    public static final BooleanValue<Integer> INTEGER_0_1 = new BooleanValue<>(1, 0);

    /**
     * 使用字符串 0 和 1 表示。
     */
    public static final BooleanValue<String> STRING_0_1 = new BooleanValue<>("1", "0");

    /**
     * 表示真的值。
     */
    @Nonnull
    private final V trueValue;

    /**
     * 表示假的值。
     */
    @Nonnull
    private final V falseValue;

    /**
     * 表示 <code>null</code> 的值。
     */
    @Nullable
    private final V nullValue;

    /**
     * 构造器。
     * 
     * @param trueValue
     *     表示真的值。
     * @param falseValue
     *     表示假的值。
     */
    public BooleanValue(@Nonnull V trueValue, @Nonnull V falseValue) {
        this(trueValue, falseValue, null);
    }

    /**
     * 构造器。
     * 
     * @param trueValue
     *     表示真的值。
     * @param falseValue
     *     表示假的值。
     * @param nullValue
     *     表示 <code>null</code> 的值。
     */
    public BooleanValue(@Nonnull V trueValue, @Nonnull V falseValue, @Nullable V nullValue) {
        this.trueValue = trueValue;
        this.falseValue = falseValue;
        this.nullValue = nullValue;
    }

    /**
     * 获取表示真的值。
     * 
     * @return 表示真的值。
     */
    public V getTrueValue() {
        return trueValue;
    }

    /**
     * 获取表示假的值。
     * 
     * @return 表示假的值。
     */
    public V getFalseValue() {
        return falseValue;
    }

    /**
     * 获取表示 <code>null</code> 的值。
     * 
     * @return 表示 <code>null</code> 的值。
     */
    public V getNullValue() {
        return nullValue;
    }

    /**
     * 判断参数值是否等于表示真的值。
     * 
     * @param value
     *     参数值。
     * @return 是否等于。
     */
    public boolean isTrue(@Nullable V value) {
        return value != null && value.equals(trueValue);
    }

    /**
     * 判断参数值是否等于表示真的值或 <code>null</code> 或表示 <code>null</code> 的值。
     * 
     * @param value
     *     参数值。
     * @return 是否等于。
     */
    public boolean isTrueOrNull(@Nullable V value) {
        return value == null || value.equals(trueValue) || value.equals(nullValue);
    }

    /**
     * 判断参数值是否等于表示假的值。
     * 
     * @param value
     *     参数值。
     * @return 是否等于。
     */
    public boolean isFalse(@Nullable V value) {
        return value != null && value.equals(falseValue);
    }

    /**
     * 判断参数值是否等于表示假的值或 <code>null</code> 或表示 <code>null</code> 的值。
     * 
     * @param value
     *     参数值。
     * @return 是否等于。
     */
    public boolean isFalseOrNull(@Nullable V value) {
        return value == null || value.equals(falseValue) || value.equals(nullValue);
    }

    /**
     * 转换值类型为布尔类型。
     * 
     * @param value
     *     值类型。
     * @return 布尔类型。
     */
    public Boolean toBoolean(@Nullable V value) {
        if (value == null) {
            return null;
        }
        if (value.equals(trueValue)) {
            return Boolean.TRUE;
        }
        if (value.equals(falseValue)) {
            return Boolean.FALSE;
        }
        return null;
    }

    /**
     * 转换布尔类型为值类型。
     * 
     * @param value
     *     布尔类型。
     * @return 值类型。
     */
    public V toValue(@Nullable Boolean value) {
        if (value == null) {
            return nullValue;
        }
        if (value) {
            return trueValue;
        }
        return falseValue;
    }
}
