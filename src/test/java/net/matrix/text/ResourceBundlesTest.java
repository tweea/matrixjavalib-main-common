/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.text;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Test;

public class ResourceBundlesTest {
	@Test
	public void testGetBundle() {
		Locales.current(Locale.CHINA);
		ResourceBundle bundle = ResourceBundles.getBundle("global");
		Assert.assertEquals("男性", bundle.getString("male"));
	}

	@Test
	public void testGetBundle_locale() {
		ResourceBundle bundle = ResourceBundles.getBundle("global", Locale.US);
		Assert.assertEquals("爷们", bundle.getString("male"));
	}

	@Test
	public void testGetBundle_fallback() {
		ResourceBundle bundle = ResourceBundles.getBundle("fallback");
		Assert.assertEquals("male", bundle.getString("male"));
	}

	@Test
	public void testGetProperty() {
		ResourceBundle bundle = ResourceBundles.getBundle("global", Locale.US);
		Assert.assertEquals("爷们", ResourceBundles.getProperty(bundle, "male"));
		Assert.assertEquals("yes", ResourceBundles.getProperty(bundle, "yes"));
		Assert.assertEquals("OK", ResourceBundles.getProperty(bundle, "OK"));
	}
}
