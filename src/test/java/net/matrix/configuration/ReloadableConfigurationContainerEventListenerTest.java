/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
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
    private FieldSupport fieldSupport = FieldSupport.extraction();

    @Test
    public void testNew() {
        TestContainer container = new TestContainer();

        EventListener listener = new ReloadableConfigurationContainerEventListener(container);
        assertThat(fieldSupport.fieldValue("container", ReloadableConfigurationContainer.class, listener)).isSameAs(container);
    }

    @Test
    public void testConfigurationRequestEvent() {
        TestContainer container = new TestContainer();
        EventListener listener = new ReloadableConfigurationContainerEventListener(container);
        ConfigurationBuilder configurationBuilder = new BasicConfigurationBuilder<>(ImmutableConfiguration.class);

        assertThat(container.isChecked()).isFalse();
        listener.onEvent(new ConfigurationBuilderEvent(configurationBuilder, ConfigurationBuilderEvent.CONFIGURATION_REQUEST));
        assertThat(container.isChecked()).isTrue();
    }

    @Test
    public void testConfigurationCreatedEvent() {
        TestContainer container = new TestContainer();
        EventListener listener = new ReloadableConfigurationContainerEventListener(container);
        ConfigurationBuilder configurationBuilder = new BasicConfigurationBuilder<>(ImmutableConfiguration.class);

        assertThat(container.isReseted()).isFalse();
        listener.onEvent(new ConfigurationBuilderEvent(configurationBuilder, ConfigurationBuilderResultCreatedEvent.RESULT_CREATED));
        assertThat(container.isReseted()).isTrue();
    }

    @Test
    public void testConfigurationOtherEvent() {
        TestContainer container = new TestContainer();
        EventListener listener = new ReloadableConfigurationContainerEventListener(container);
        ConfigurationBuilder configurationBuilder = new BasicConfigurationBuilder<>(ImmutableConfiguration.class);

        assertThat(container.isChecked()).isFalse();
        assertThat(container.isReseted()).isFalse();
        listener.onEvent(new ConfigurationBuilderEvent(configurationBuilder, ConfigurationBuilderEvent.RESET));
        assertThat(container.isChecked()).isFalse();
        assertThat(container.isReseted()).isFalse();
    }

    public static class TestContainer
        implements ReloadableConfigurationContainer<Object> {
        private boolean isChecked = false;

        private boolean isReseted = false;

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
        public boolean canCheckReload() {
            return true;
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
