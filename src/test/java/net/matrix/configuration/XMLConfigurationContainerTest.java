/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.configuration;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class XMLConfigurationContainerTest {
    @Test
    public void testXMLConfigurationContainer()
        throws ConfigurationException {
        ReloadableConfigurationContainer<XMLConfiguration> container = new XMLConfigurationContainer();
        XMLConfiguration config = container.getConfig();
        assertThat(config).isNotNull();
        assertThat(config.isEmpty()).isTrue();
        assertThat(config.getListDelimiterHandler()).isInstanceOf(DefaultListDelimiterHandler.class);
    }

    @Test
    public void testLoadFile()
        throws ConfigurationException {
        ReloadableConfigurationContainer<XMLConfiguration> container = new XMLConfigurationContainer();
        container.load(new ClassPathResource("bar.xml"));
        XMLConfiguration config = container.getConfig();
        assertThat(config.getInt("[@length]")).isEqualTo(50);
    }

    @Test
    public void testLoadJarEntry()
        throws ConfigurationException {
        ReloadableConfigurationContainer<XMLConfiguration> container = new XMLConfigurationContainer();
        container.load(new ClassPathResource("pmd.xml"));
        XMLConfiguration config = container.getConfig();
        assertThat(config.getString("rule(0)[@ref]")).isEqualTo("category/java/bestpractices.xml/AccessorClassGeneration");
    }

    @Test
    public void testLoadNotExist() {
        ReloadableConfigurationContainer<XMLConfiguration> container = new XMLConfigurationContainer();
        assertThatExceptionOfType(ConfigurationException.class).isThrownBy(() -> container.load(new ClassPathResource("notExist.xml")));
    }
}
