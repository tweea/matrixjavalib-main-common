/*
 * 版权所有 2020 Matrix。
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

public class ConfigurationUtils2Test {
    private HierarchicalConfiguration config;

    @BeforeEach
    public void beforeEach()
        throws ConfigurationException {
        config = new Configurations().xml("./bar.xml");
    }

    @Test
    public void testParseAttributes() {
        HierarchicalConfiguration testConfig = config.configurationAt("senders.target(2).properties(0)");

        Map<String, String> parameter = ConfigurationUtils2.parseAttributes(testConfig);
        assertThat(parameter).hasSize(testConfig.size());
        assertThat(parameter).containsEntry("[@name]", "url");
    }
}
