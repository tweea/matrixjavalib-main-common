/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
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
     * @return 数量
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @return 货币
     */
    public Currency getCurrency() {
        return currency;
    }
}
