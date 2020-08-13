/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.data;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MoneyTest {
    @Test
    public void testMoney_currency() {
        Money money = new Money(BigDecimal.ONE, Currency.getInstance("CNY"));
        assertThat(money.getAmount()).isEqualByComparingTo(BigDecimal.ONE);
        assertThat(money.getCurrency()).isEqualTo(Currency.getInstance("CNY"));
    }
}
