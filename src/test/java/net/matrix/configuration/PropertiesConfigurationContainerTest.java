/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.configuration;

import java.io.IOException;

import org.apache.commons.configuration2.PropertiesConfiguration;
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

public class PropertiesConfigurationContainerTest {
    @Test
    public void testNew()
        throws ConfigurationException {
        PropertiesConfigurationContainer container = new PropertiesConfigurationContainer();
        assertThat(container.canCheckReload()).isFalse();
        PropertiesConfiguration config = container.getConfig();
        assertThat(config).isNotNull();
        assertThat(config.isEmpty()).isTrue();
        assertThat(config.getListDelimiterHandler()).isInstanceOf(DefaultListDelimiterHandler.class);
    }

    @Test
    public void testLoadFile()
        throws ConfigurationException {
        Resource resource = new ClassPathResource("bar.properties");
        PropertiesConfigurationContainer container = new PropertiesConfigurationContainer();

        container.load(resource);
        assertThat(container.canCheckReload()).isTrue();
        PropertiesConfiguration config = container.getConfig();
        assertThat(config.getInt("[@length]")).isEqualTo(50);
    }

    @Test
    public void testLoadInputStream()
        throws ConfigurationException, IOException {
        Resource resource = new InputStreamResource(new ClassPathResource("bar.properties").getInputStream());
        PropertiesConfigurationContainer container = new PropertiesConfigurationContainer();

        container.load(resource);
        assertThat(container.canCheckReload()).isFalse();
        PropertiesConfiguration config = container.getConfig();
        assertThat(config.getInt("[@length]")).isEqualTo(50);
    }

    @Test
    public void testLoadNotExist()
        throws ConfigurationException {
        Resource resource = new ClassPathResource("notExist.properties");
        PropertiesConfigurationContainer container = new PropertiesConfigurationContainer();

        assertThatExceptionOfType(ConfigurationException.class).isThrownBy(() -> container.load(resource));
        assertThat(container.canCheckReload()).isFalse();
        PropertiesConfiguration config = container.getConfig();
        assertThat(config.isEmpty()).isTrue();
    }

    @Test
    public void testLoadMany()
        throws ConfigurationException, IOException {
        Resource resource1 = new ClassPathResource("bar.properties");
        Resource resource2 = new ClassPathResource("pmd.properties");
        Resource resource3 = new InputStreamResource(resource1.getInputStream());
        Resource resource4 = new ClassPathResource("notExist.properties");
        PropertiesConfigurationContainer container = new PropertiesConfigurationContainer();

        container.load(resource1);
        assertThat(container.canCheckReload()).isTrue();
        PropertiesConfiguration config = container.getConfig();
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
        Resource resource = new ClassPathResource("bar.properties");
        PropertiesConfigurationContainer container = new PropertiesConfigurationContainer();
        container.load(resource);

        PropertiesConfiguration config = container.getConfig();
        config.setProperty("[@length]", 100);
        assertThat(config.getInt("[@length]")).isEqualTo(100);
        container.reload();
        config = container.getConfig();
        assertThat(config.getInt("[@length]")).isEqualTo(50);
    }

    @Test
    public void testReloadInputStream()
        throws ConfigurationException, IOException {
        Resource resource = new ByteArrayResource(IOUtils.toByteArray(new ClassPathResource("bar.properties").getInputStream()));
        PropertiesConfigurationContainer container = new PropertiesConfigurationContainer();
        container.load(resource);

        PropertiesConfiguration config = container.getConfig();
        config.setProperty("[@length]", 100);
        assertThat(config.getInt("[@length]")).isEqualTo(100);
        container.reload();
        config = container.getConfig();
        assertThat(config.getInt("[@length]")).isEqualTo(50);
    }

    @Test
    public void testReloadNotExist()
        throws ConfigurationException {
        Resource resource = new ClassPathResource("notExist.properties");
        PropertiesConfigurationContainer container = new PropertiesConfigurationContainer();
        assertThatExceptionOfType(ConfigurationException.class).isThrownBy(() -> container.load(resource));

        PropertiesConfiguration config = container.getConfig();
        assertThat(config.isEmpty()).isTrue();
        assertThatExceptionOfType(ConfigurationException.class).isThrownBy(() -> container.reload());
        config = container.getConfig();
        assertThat(config.isEmpty()).isTrue();
    }
}
