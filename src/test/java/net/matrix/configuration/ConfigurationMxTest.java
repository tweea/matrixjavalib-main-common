/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.configuration;

import java.util.Map;

import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigurationMxTest {
    HierarchicalConfiguration config;

    @BeforeEach
    void beforeEach()
        throws ConfigurationException {
        config = new Configurations().xml("./bar.xml");
    }

    @Test
    void testBuildMap() {
        HierarchicalConfiguration testConfig = config.configurationAt("senders.target(2).properties(0)");

        Map<String, String> configMap = ConfigurationMx.buildMap(testConfig);
        assertThat(configMap).hasSize(testConfig.size());
        assertThat(configMap).containsEntry("[@name]", "url");
    }
}
