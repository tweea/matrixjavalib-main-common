/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
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

public class HierarchicalConfigurationMxTest {
    private HierarchicalConfiguration config;

    @BeforeEach
    public void beforeEach()
        throws ConfigurationException {
        config = new Configurations().xml("./bar.xml");
    }

    @Test
    public void testBuildNameValueMap() {
        HierarchicalConfiguration testConfig = config.configurationAt("senders.target(0)");

        Map<String, String> nameValueMap = HierarchicalConfigurationMx.buildNameValueMap(testConfig, "properties", "[@name]", "[@value]");
        assertThat(nameValueMap).hasSize(6);
        assertThat(nameValueMap).containsEntry("hostname", "192.168.1.234");
    }

    @Test
    public void testUpdateNameValueMap() {
        HierarchicalConfiguration testConfig = config.configurationAt("senders.target(0)", true);
        Map<String, String> nameValueMap = HierarchicalConfigurationMx.buildNameValueMap(testConfig, "properties", "[@name]", "[@value]");
        nameValueMap.put("hostname", "192.168.1.1");
        nameValueMap.put("test", "abc");
        nameValueMap.remove("port");
        nameValueMap.remove("queueName");

        HierarchicalConfigurationMx.updateNameValueMap(testConfig, "properties", "[@name]", "[@value]", nameValueMap);
        nameValueMap = HierarchicalConfigurationMx.buildNameValueMap(testConfig, "properties", "[@name]", "[@value]");
        assertThat(nameValueMap).hasSize(5);
        assertThat(nameValueMap).containsEntry("hostname", "192.168.1.1");
        assertThat(nameValueMap).doesNotContainKey("port");
    }

    @Test
    public void testBuildNameList() {
        HierarchicalConfiguration testConfig = config.configurationAt("receivers.receiver(0)");

        List<String> names = HierarchicalConfigurationMx.buildNameList(testConfig, "properties", "[@name]");
        assertThat(names).hasSize(6);
        assertThat(names).containsExactly("hostname", "port", "queueManagerName", "queueName", "ccsid", "channelName");
    }

    @Test
    public void testForName() {
        HierarchicalConfiguration testConfig = config.configurationAt("senders");

        HierarchicalConfiguration subConfig = HierarchicalConfigurationMx.forName(testConfig, "target", "[@name]", "SysA");
        assertThat(subConfig.getString("[@protocol]")).isEqualTo("WMQ");
    }

    @Test
    public void testForName2() {
        HierarchicalConfiguration testConfig = config.configurationAt("senders");

        HierarchicalConfiguration subConfig = HierarchicalConfigurationMx.forName(testConfig, "target", "[@name]", "SysX");
        assertThat(subConfig).isNull();
    }
}
