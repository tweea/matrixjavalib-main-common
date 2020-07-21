/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.configuration;

import java.util.List;
import java.util.Map;

import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class HierarchicalConfigurationUtilsTest {
    private HierarchicalConfiguration config;

    @BeforeEach
    public void setUp()
        throws ConfigurationException {
        config = new Configurations().xml("./bar.xml");
    }

    @Test
    public void parseParameter() {
        Map<String, String> parameter = HierarchicalConfigurationUtils.parseParameter(config.configurationAt("senders.target(0)"), "properties", "[@name]",
            "[@value]");
        assertThat(parameter).containsEntry("hostname", "192.168.1.234");
    }

    @Test
    public void updateParameter() {
        Map<String, String> parameter = HierarchicalConfigurationUtils.parseParameter(config.configurationAt("senders.target(0)"), "properties", "[@name]",
            "[@value]");
        parameter.put("hostname", "192.168.1.1");
        parameter.put("test", "abc");
        parameter.remove("port");
        parameter.remove("queueName");
        HierarchicalConfigurationUtils.updateParameter(config.configurationAt("senders.target(0)", true), "properties", "[@name]", "[@value]", parameter);
        parameter = HierarchicalConfigurationUtils.parseParameter(config.configurationAt("senders.target(0)"), "properties", "[@name]", "[@value]");
        assertThat(parameter).containsEntry("hostname", "192.168.1.1");
        assertThat(parameter).doesNotContainKey("port");
    }

    @Test
    public void parseAttributes() {
        Map<String, String> parameter = HierarchicalConfigurationUtils.parseAttributes(config.configurationAt("senders.target(2).properties(0)"));
        assertThat(parameter).containsEntry("[@name]", "url");
    }

    @Test
    public void listAllNames() {
        List<String> names = HierarchicalConfigurationUtils.listAllNames(config.configurationAt("receivers.receiver(0)"), "properties", "[@name]");
        assertThat(names).hasSize(6);
        assertThat(names).containsExactly("hostname", "port", "queueManagerName", "queueName", "ccsid", "channelName");
    }

    @Test
    public void findForName()
        throws ConfigurationException {
        HierarchicalConfiguration subconfig = HierarchicalConfigurationUtils.findForName(config.configurationAt("senders"), "target", "[@name]", "SysA");
        assertThat(subconfig.getString("[@protocol]")).isEqualTo("WMQ");
    }

    @Test
    public void findForName2() {
        assertThatExceptionOfType(ConfigurationException.class)
            .isThrownBy(() -> HierarchicalConfigurationUtils.findForName(config.configurationAt("senders"), "target", "[@name]", "SysX"));
    }
}
