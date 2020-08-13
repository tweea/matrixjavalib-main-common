/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.configuration;

import java.io.IOException;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class XMLConfigurationContainerTest {
    @Test
    public void testXMLConfigurationContainer()
        throws ConfigurationException {
        XMLConfigurationContainer container = new XMLConfigurationContainer();
        assertThat(container.canCheckReload()).isFalse();
        XMLConfiguration config = container.getConfig();
        assertThat(config).isNotNull();
        assertThat(config.isEmpty()).isTrue();
        assertThat(config.getListDelimiterHandler()).isInstanceOf(DefaultListDelimiterHandler.class);
    }

    @Test
    public void testLoadFile()
        throws ConfigurationException {
        Resource resource = new ClassPathResource("bar.xml");
        XMLConfigurationContainer container = new XMLConfigurationContainer();

        container.load(resource);
        assertThat(container.canCheckReload()).isTrue();
        XMLConfiguration config = container.getConfig();
        assertThat(config.getInt("[@length]")).isEqualTo(50);
    }

    @Test
    public void testLoadJarEntry()
        throws ConfigurationException {
        Resource resource = new ClassPathResource("pmd.xml");
        XMLConfigurationContainer container = new XMLConfigurationContainer();

        container.load(resource);
        assertThat(container.canCheckReload()).isTrue();
        XMLConfiguration config = container.getConfig();
        assertThat(config.getString("rule(0)[@ref]")).isEqualTo("category/java/bestpractices.xml/AccessorClassGeneration");
    }

    @Test
    public void testLoadInputStream()
        throws ConfigurationException, IOException {
        Resource resource = new InputStreamResource(new ClassPathResource("bar.xml").getInputStream());
        XMLConfigurationContainer container = new XMLConfigurationContainer();

        container.load(resource);
        assertThat(container.canCheckReload()).isFalse();
        XMLConfiguration config = container.getConfig();
        assertThat(config.getInt("[@length]")).isEqualTo(50);
    }

    @Test
    public void testLoadNotExist()
        throws ConfigurationException {
        Resource resource = new ClassPathResource("notExist.xml");
        XMLConfigurationContainer container = new XMLConfigurationContainer();

        assertThatExceptionOfType(ConfigurationException.class).isThrownBy(() -> container.load(resource));
        assertThat(container.canCheckReload()).isFalse();
        XMLConfiguration config = container.getConfig();
        assertThat(config.isEmpty()).isTrue();
    }

    @Test
    public void testLoadMany()
        throws ConfigurationException, IOException {
        Resource resource1 = new ClassPathResource("bar.xml");
        Resource resource2 = new ClassPathResource("pmd.xml");
        Resource resource3 = new InputStreamResource(resource1.getInputStream());
        Resource resource4 = new ClassPathResource("notExist.xml");
        XMLConfigurationContainer container = new XMLConfigurationContainer();

        container.load(resource1);
        assertThat(container.canCheckReload()).isTrue();
        XMLConfiguration config = container.getConfig();
        assertThat(config.getInt("[@length]")).isEqualTo(50);

        container.load(resource2);
        assertThat(container.canCheckReload()).isTrue();
        config = container.getConfig();
        assertThat(config.getString("rule(0)[@ref]")).isEqualTo("category/java/bestpractices.xml/AccessorClassGeneration");

        container.load(resource3);
        assertThat(container.canCheckReload()).isFalse();
        config = container.getConfig();
        assertThat(config.getInt("[@length]")).isEqualTo(50);

        assertThatExceptionOfType(ConfigurationException.class).isThrownBy(() -> container.load(resource4));
        assertThat(container.canCheckReload()).isFalse();
        config = container.getConfig();
        assertThat(config.isEmpty()).isTrue();
    }

    @Test
    public void testReloadFile()
        throws ConfigurationException {
        Resource resource = new ClassPathResource("bar.xml");
        XMLConfigurationContainer container = new XMLConfigurationContainer();
        container.load(resource);

        XMLConfiguration config = container.getConfig();
        config.setProperty("[@length]", 100);
        assertThat(config.getInt("[@length]")).isEqualTo(100);
        container.reload();
        config = container.getConfig();
        assertThat(config.getInt("[@length]")).isEqualTo(50);
    }

    @Test
    public void testReloadJarEntry()
        throws ConfigurationException {
        Resource resource = new ClassPathResource("pmd.xml");
        XMLConfigurationContainer container = new XMLConfigurationContainer();
        container.load(resource);

        XMLConfiguration config = container.getConfig();
        config.setProperty("rule(0)[@ref]", 100);
        assertThat(config.getInt("rule(0)[@ref]")).isEqualTo(100);
        container.reload();
        config = container.getConfig();
        assertThat(config.getString("rule(0)[@ref]")).isEqualTo("category/java/bestpractices.xml/AccessorClassGeneration");
    }

    @Test
    public void testReloadInputStream()
        throws ConfigurationException, IOException {
        Resource resource = new ByteArrayResource(IOUtils.toByteArray(new ClassPathResource("bar.xml").getInputStream()));
        XMLConfigurationContainer container = new XMLConfigurationContainer();
        container.load(resource);

        XMLConfiguration config = container.getConfig();
        config.setProperty("[@length]", 100);
        assertThat(config.getInt("[@length]")).isEqualTo(100);
        container.reload();
        config = container.getConfig();
        assertThat(config.getInt("[@length]")).isEqualTo(50);
    }

    @Test
    public void testReloadNotExist()
        throws ConfigurationException {
        Resource resource = new ClassPathResource("notExist.xml");
        XMLConfigurationContainer container = new XMLConfigurationContainer();
        assertThatExceptionOfType(ConfigurationException.class).isThrownBy(() -> container.load(resource));

        XMLConfiguration config = container.getConfig();
        assertThat(config.isEmpty()).isTrue();
        assertThatExceptionOfType(ConfigurationException.class).isThrownBy(() -> container.reload());
        config = container.getConfig();
        assertThat(config.isEmpty()).isTrue();
    }
}
