/*
 * 版权所有 2013 Matrix。
 * 保留所有权利。
 */
package net.matrix.text;

import java.util.Locale;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class LocalesTest {
	@Test
	public void testCurrent() {
		Assertions.assertThat(Locales.current()).isEqualTo(Locale.getDefault());

		Locales.current(Locale.CHINA);
		Assertions.assertThat(Locales.current()).isEqualTo(Locale.CHINA);

		Locales.current(Locale.getDefault());
		Assertions.assertThat(Locales.current()).isEqualTo(Locale.getDefault());
	}
}
