/*
 * Copyright(C) 2009 matrix
 * All right reserved.
 */
package net.matrix.configuration;

import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.commons.configuration2.builder.BasicConfigurationBuilder;
import org.apache.commons.configuration2.builder.ConfigurationBuilder;
import org.apache.commons.configuration2.builder.ConfigurationBuilderEvent;
import org.apache.commons.configuration2.builder.ConfigurationBuilderResultCreatedEvent;
import org.apache.commons.configuration2.event.EventListener;
import org.assertj.core.util.introspection.FieldSupport;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;

public class ReloadableConfigurationContainerEventListenerTest {
    @Test
    public void testConfigurationCheckReloadingListener() {
        TestContainer container = new TestContainer();
        EventListener listener = new ReloadableConfigurationContainerEventListener(container);
        assertThat(FieldSupport.extraction().fieldValue("container", ReloadableConfigurationContainer.class, listener)).isSameAs(container);
    }

    @Test
    public void testConfigurationRequestEvent() {
        TestContainer container = new TestContainer();
        EventListener listener = new ReloadableConfigurationContainerEventListener(container);
        assertThat(container.isChecked()).isFalse();
        ConfigurationBuilder configurationBuilder = new BasicConfigurationBuilder<>(ImmutableConfiguration.class);
        listener.onEvent(new ConfigurationBuilderEvent(configurationBuilder, ConfigurationBuilderEvent.CONFIGURATION_REQUEST));
        assertThat(container.isChecked()).isTrue();
    }

    @Test
    public void testConfigurationCreatedEvent() {
        TestContainer container = new TestContainer();
        EventListener listener = new ReloadableConfigurationContainerEventListener(container);
        assertThat(container.isReseted()).isFalse();
        ConfigurationBuilder configurationBuilder = new BasicConfigurationBuilder<>(ImmutableConfiguration.class);
        listener.onEvent(new ConfigurationBuilderEvent(configurationBuilder, ConfigurationBuilderResultCreatedEvent.RESULT_CREATED));
        assertThat(container.isReseted()).isTrue();
    }

    @Test
    public void testConfigurationOtherEvent() {
        TestContainer container = new TestContainer();
        EventListener listener = new ReloadableConfigurationContainerEventListener(container);
        assertThat(container.isChecked()).isFalse();
        assertThat(container.isReseted()).isFalse();
        ConfigurationBuilder configurationBuilder = new BasicConfigurationBuilder<>(ImmutableConfiguration.class);
        listener.onEvent(new ConfigurationBuilderEvent(configurationBuilder, ConfigurationBuilderEvent.RESET));
        assertThat(container.isChecked()).isFalse();
        assertThat(container.isReseted()).isFalse();
    }

    private static class TestContainer
        implements ReloadableConfigurationContainer<Object> {
        private boolean isChecked = false;

        private boolean isReseted = false;

        public TestContainer() {
            // 提升可见性，优化访问速度
        }

        public boolean isChecked() {
            return isChecked;
        }

        public boolean isReseted() {
            return isReseted;
        }

        @Override
        public void load(Resource resource) {
        }

        @Override
        public void reload() {
        }

        @Override
        public void checkReload() {
            isChecked = true;
        }

        @Override
        public Object getConfig() {
            return null;
        }

        @Override
        public void reset() {
            isReseted = true;
        }
    }
}
