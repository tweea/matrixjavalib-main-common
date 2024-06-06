/*
 * 版权所有 2023 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.util;

import java.util.TimeZone;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TimeZoneMxTest {
    @Test
    void testCurrent() {
        assertThat(TimeZoneMx.current()).isEqualTo(TimeZone.getDefault());

        TimeZoneMx.current(TimeZone.getTimeZone("GMT+8"));
        assertThat(TimeZoneMx.current()).isEqualTo(TimeZone.getTimeZone("GMT+8"));

        TimeZoneMx.current(TimeZone.getTimeZone("GMT+6"));
        assertThat(TimeZoneMx.current()).isEqualTo(TimeZone.getTimeZone("GMT+6"));

        TimeZoneMx.current(null);
        assertThat(TimeZoneMx.current()).isEqualTo(TimeZone.getDefault());
    }
}
