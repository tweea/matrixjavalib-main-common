/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.configuration;

import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.assertj.core.api.Assertions;
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
        Assertions.assertThat(parameter).containsEntry("hostname", "192.168.1.234");
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
        Assertions.assertThat(parameter).containsEntry("hostname", "192.168.1.1");
        Assertions.assertThat(parameter).doesNotContainKey("port");
    }

    @Test
    public void parseAttributes() {
        Map<String, String> parameter = HierarchicalConfigurationUtils.parseAttributes(config.configurationAt("senders.target(2).properties(0)"));
        Assertions.assertThat(parameter).containsEntry("[@name]", "url");
    }

    @Test
    public void listAllNames() {
        List<String> names = HierarchicalConfigurationUtils.listAllNames(config.configurationAt("receivers.receiver(0)"), "properties", "[@name]");
        Assertions.assertThat(names).hasSize(6);
        Assertions.assertThat(names).containsExactly("hostname", "port", "queueManagerName", "queueName", "ccsid", "channelName");
    }

    @Test
    public void findForName()
        throws ConfigurationException {
        HierarchicalConfiguration subconfig = HierarchicalConfigurationUtils.findForName(config.configurationAt("senders"), "target", "[@name]", "SysA");
        Assertions.assertThat(subconfig.getString("[@protocol]")).isEqualTo("WMQ");
    }

    @Test(expected = ConfigurationException.class)
    public void findForName2()
        throws ConfigurationException {
        HierarchicalConfigurationUtils.findForName(config.configurationAt("senders"), "target", "[@name]", "SysX");
    }
}
