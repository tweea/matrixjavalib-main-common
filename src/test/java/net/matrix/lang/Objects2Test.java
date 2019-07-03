/*
 * 版权所有 2013 Matrix。
 * 保留所有权利。
 */
package net.matrix.lang;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Objects2Test {
	@Test
	public void testIsNull() {
		Assertions.assertThat(Objects2.isNull("A", "B")).isEqualTo("A");
		Assertions.assertThat(Objects2.isNull(null, "B")).isEqualTo("B");
	}

	@Test
	public void testNullIf() {
		Object nullValue = null;
		Assertions.assertThat(Objects2.nullIf(nullValue, nullValue)).isNull();
		Assertions.assertThat(Objects2.nullIf("A", "A")).isNull();
		Assertions.assertThat(Objects2.nullIf("A", "B")).isEqualTo("A");
	}
}
