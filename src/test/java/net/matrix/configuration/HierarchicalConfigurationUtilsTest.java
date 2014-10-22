/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HierarchicalConfigurationUtilsTest {
	private HierarchicalConfiguration config;

	@Before
	public void setUp()
		throws ConfigurationException {
		config = new XMLConfiguration("./bar.xml");
	}

	@Test
	public void parseParameter() {
		Map<String, String> parameter = HierarchicalConfigurationUtils.parseParameter(config.configurationAt("senders.target(0)"), "properties", "[@name]",
			"[@value]");
		Assert.assertEquals("192.168.1.234", parameter.get("hostname"));
	}

	@Test
	public void updateParameter() {
		Map<String, String> parameter = HierarchicalConfigurationUtils.parseParameter(config.configurationAt("senders.target(0)"), "properties", "[@name]",
			"[@value]");
		parameter.put("hostname", "192.168.1.1");
		parameter.put("test", "abc");
		parameter.remove("port");
		parameter.remove("queueName");
		HierarchicalConfigurationUtils.updateParameter(config.configurationAt("senders.target(0)"), "properties", "[@name]", "[@value]", parameter);
		parameter = HierarchicalConfigurationUtils.parseParameter(config.configurationAt("senders.target(0)"), "properties", "[@name]", "[@value]");
		Assert.assertEquals("192.168.1.1", parameter.get("hostname"));
		Assert.assertNull(parameter.get("port"));
	}

	@Test
	public void parseAttributes() {
		Map<String, String> parameter = HierarchicalConfigurationUtils.parseAttributes(config.configurationAt("senders.target(2).properties(0)"));
		Assert.assertEquals("url", parameter.get("[@name]"));
	}

	@Test
	public void listAllNames() {
		List<String> names = HierarchicalConfigurationUtils.listAllNames(config.configurationAt("receivers.receiver(0)"), "properties", "[@name]");
		List<String> testNames = new ArrayList<String>();
		testNames.add("hostname");
		testNames.add("port");
		testNames.add("queueManagerName");
		testNames.add("queueName");
		testNames.add("ccsid");
		testNames.add("channelName");
		Assert.assertEquals(6, names.size());
		Assert.assertEquals(testNames, names);
	}

	@Test
	public void findForName()
		throws ConfigurationException {
		HierarchicalConfiguration subconfig = HierarchicalConfigurationUtils.findForName(config.configurationAt("senders"), "target", "[@name]", "SysA");
		Assert.assertEquals("WMQ", subconfig.getString("[@protocol]"));
	}

	@Test(expected = ConfigurationException.class)
	public void findForName2()
		throws ConfigurationException {
		HierarchicalConfigurationUtils.findForName(config.configurationAt("senders"), "target", "[@name]", "SysX");
	}
}
