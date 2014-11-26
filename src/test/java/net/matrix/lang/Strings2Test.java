/*
 * 版权所有 2013 Matrix。
 * 保留所有权利。
 */
package net.matrix.lang;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Strings2Test {
	@Test
	public void testReplaceAllBetweenDelimiter() {
		Assertions.assertThat(Strings2.replaceAllBetweenDelimiter("abc<Bad>xyz", "<", ">", "Bad", "Good")).isEqualTo("abc<Good>xyz");
	}
}
