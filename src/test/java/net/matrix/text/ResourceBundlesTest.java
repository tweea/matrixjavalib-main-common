/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.text;

import java.util.Locale;
import java.util.ResourceBundle;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ResourceBundlesTest {
	@Test
	public void testGetBundle() {
		Locales.current(Locale.CHINA);
		ResourceBundle bundle = ResourceBundles.getBundle("global");
		Assertions.assertThat(bundle.getString("male")).isEqualTo("男性");
	}

	@Test
	public void testGetBundle_locale() {
		ResourceBundle bundle = ResourceBundles.getBundle("global", Locale.US);
		Assertions.assertThat(bundle.getString("male")).isEqualTo("爷们");
	}

	@Test
	public void testGetBundle_fallback() {
		ResourceBundle bundle = ResourceBundles.getBundle("fallback");
		Assertions.assertThat(bundle.getString("male")).isEqualTo("male");
	}

	@Test
	public void testGetProperty() {
		ResourceBundle bundle = ResourceBundles.getBundle("global", Locale.US);
		Assertions.assertThat(ResourceBundles.getProperty(bundle, "male")).isEqualTo("爷们");
		Assertions.assertThat(ResourceBundles.getProperty(bundle, "yes")).isEqualTo("yes");
		Assertions.assertThat(ResourceBundles.getProperty(bundle, "OK")).isEqualTo("OK");
	}
}
