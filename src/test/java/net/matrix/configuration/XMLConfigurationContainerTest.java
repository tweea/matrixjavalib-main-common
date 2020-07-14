/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.configuration;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

public class XMLConfigurationContainerTest {
    @Test
    public void testXMLConfigurationContainer() {
        ReloadableConfigurationContainer<XMLConfiguration> container = new XMLConfigurationContainer();
        Assertions.assertThat(container.getConfig()).isNotNull();
        Assertions.assertThat(container.getConfig().isEmpty()).isTrue();
        Assertions.assertThat(container.getConfig().isDelimiterParsingDisabled()).isFalse();
    }

    @Test
    public void testLoadFile()
        throws ConfigurationException {
        ReloadableConfigurationContainer<XMLConfiguration> container = new XMLConfigurationContainer();
        container.load(new ClassPathResource("bar.xml"));
        Assertions.assertThat(container.getConfig().getInt("[@length]")).isEqualTo(50);
    }

    @Test
    public void testLoadStream()
        throws ConfigurationException {
        ReloadableConfigurationContainer<XMLConfiguration> container = new XMLConfigurationContainer();
        container.load(new ClassPathResource("digesterRules.xml"));
        Assertions.assertThat(container.getConfig().getString("pattern(0)[@value]")).isEqualTo("configuration/properties");
    }

    @Test(expected = ConfigurationException.class)
    public void testLoadNotExist()
        throws ConfigurationException {
        ReloadableConfigurationContainer<XMLConfiguration> container = new XMLConfigurationContainer();
        container.load(new ClassPathResource("notExist.xml"));
    }
}
