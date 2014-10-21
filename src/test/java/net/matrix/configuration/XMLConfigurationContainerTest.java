/*
 * $Id: XMLConfigurationContainerTest.java 680 2013-09-04 06:36:26Z tweea@263.net $
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.configuration;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

public class XMLConfigurationContainerTest {
	@Test
	public void testXMLConfigurationContainer() {
		ReloadableConfigurationContainer<XMLConfiguration> container = new XMLConfigurationContainer();
		Assert.assertNotNull(container.getConfig());
		Assert.assertTrue(container.getConfig().isEmpty());
		Assert.assertFalse(container.getConfig().isDelimiterParsingDisabled());
	}

	@Test
	public void testLoadFile()
		throws ConfigurationException {
		ReloadableConfigurationContainer<XMLConfiguration> container = new XMLConfigurationContainer();
		container.load(new ClassPathResource("bar.xml"));
		Assert.assertEquals(50, container.getConfig().getInt("[@length]"));
	}

	@Test
	public void testLoadStream()
		throws ConfigurationException {
		ReloadableConfigurationContainer<XMLConfiguration> container = new XMLConfigurationContainer();
		container.load(new ClassPathResource("digesterRules.xml"));
		Assert.assertEquals("configuration/properties", container.getConfig().getString("pattern(0)[@value]"));
	}

	@Test(expected = ConfigurationException.class)
	public void testLoadNotExist()
		throws ConfigurationException {
		ReloadableConfigurationContainer<XMLConfiguration> container = new XMLConfigurationContainer();
		container.load(new ClassPathResource("notExist.xml"));
	}
}
