/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.data;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import javax.annotation.concurrent.Immutable;

/**
 * 金额，包括数量和货币。
 */
@Immutable
public class Money {
    /**
     * 数量。
     */
    private final BigDecimal amount;

    /**
     * 货币。
     */
    private final Currency currency;

    /**
     * 构造使用默认货币的实例。
     * 
     * @param amount
     *     数量
     */
    public Money(final BigDecimal amount) {
        this(amount, Currency.getInstance(Locale.getDefault()));
    }

    /**
     * 构造使用指定货币的实例。
     * 
     * @param amount
     *     数量
     * @param currency
     *     货币
     */
    public Money(final BigDecimal amount, final Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    /**
     * 获取数量。
     * 
     * @return 数量
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 获取货币。
     * 
     * @return 货币
     */
    public Currency getCurrency() {
        return currency;
    }
}
