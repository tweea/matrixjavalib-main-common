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
    public void beforeEach()
        throws ConfigurationException {
        config = new Configurations().xml("./bar.xml");
    }

    @Test
    public void parseParameter() {
        HierarchicalConfiguration testConfig = config.configurationAt("senders.target(0)");

        Map<String, String> parameter = HierarchicalConfigurationUtils.parseParameter(testConfig, "properties", "[@name]", "[@value]");
        assertThat(parameter).hasSize(6);
        assertThat(parameter).containsEntry("hostname", "192.168.1.234");
    }

    @Test
    public void updateParameter() {
        HierarchicalConfiguration testConfig = config.configurationAt("senders.target(0)", true);
        Map<String, String> parameter = HierarchicalConfigurationUtils.parseParameter(testConfig, "properties", "[@name]", "[@value]");
        parameter.put("hostname", "192.168.1.1");
        parameter.put("test", "abc");
        parameter.remove("port");
        parameter.remove("queueName");

        HierarchicalConfigurationUtils.updateParameter(testConfig, "properties", "[@name]", "[@value]", parameter);
        parameter = HierarchicalConfigurationUtils.parseParameter(testConfig, "properties", "[@name]", "[@value]");
        assertThat(parameter).hasSize(5);
        assertThat(parameter).containsEntry("hostname", "192.168.1.1");
        assertThat(parameter).doesNotContainKey("port");
    }

    @Test
    public void listAllNames() {
        HierarchicalConfiguration testConfig = config.configurationAt("receivers.receiver(0)");

        List<String> names = HierarchicalConfigurationUtils.listAllNames(testConfig, "properties", "[@name]");
        assertThat(names).hasSize(6);
        assertThat(names).containsExactly("hostname", "port", "queueManagerName", "queueName", "ccsid", "channelName");
    }

    @Test
    public void findForName()
        throws ConfigurationException {
        HierarchicalConfiguration testConfig = config.configurationAt("senders");

        HierarchicalConfiguration subConfig = HierarchicalConfigurationUtils.findForName(testConfig, "target", "[@name]", "SysA");
        assertThat(subConfig.getString("[@protocol]")).isEqualTo("WMQ");
    }

    @Test
    public void findForName2() {
        HierarchicalConfiguration testConfig = config.configurationAt("senders");

        assertThatExceptionOfType(ConfigurationException.class)
            .isThrownBy(() -> HierarchicalConfigurationUtils.findForName(testConfig, "target", "[@name]", "SysX"));
    }
}
