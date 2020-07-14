/*
 * Copyright(C) 2009 matrix
 * All right reserved.
 */
package net.matrix.configuration;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.AbstractFileConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.introspection.FieldSupport;
import org.junit.Test;
import org.springframework.core.io.Resource;

public class ConfigurationReloadingListenerTest {
    @Test
    public void testConfigurationReloadingListener() {
        TestContainer container = new TestContainer();
        ConfigurationListener listener = new ConfigurationReloadingListener(container);
        Assertions.assertThat(FieldSupport.extraction().fieldValue("container", ReloadableConfigurationContainer.class, listener)).isSameAs(container);
    }

    @Test
    public void testConfigurationChanged() {
        TestContainer container = new TestContainer();
        ConfigurationListener listener = new ConfigurationReloadingListener(container);
        Assertions.assertThat(container.isReloaded()).isFalse();
        listener.configurationChanged(new ConfigurationEvent(this, AbstractFileConfiguration.EVENT_RELOAD, null, null, false));
        Assertions.assertThat(container.isReloaded()).isTrue();
    }

    @Test
    public void testConfigurationChangedBeforeEvent() {
        TestContainer container = new TestContainer();
        ConfigurationListener listener = new ConfigurationReloadingListener(container);
        Assertions.assertThat(container.isReloaded()).isFalse();
        listener.configurationChanged(new ConfigurationEvent(this, AbstractFileConfiguration.EVENT_RELOAD, null, null, true));
        Assertions.assertThat(container.isReloaded()).isFalse();
    }

    @Test
    public void testConfigurationChangedOtherEvent() {
        TestContainer container = new TestContainer();
        ConfigurationListener listener = new ConfigurationReloadingListener(container);
        Assertions.assertThat(container.isReloaded()).isFalse();
        listener.configurationChanged(new ConfigurationEvent(this, AbstractConfiguration.EVENT_CLEAR, null, null, false));
        Assertions.assertThat(container.isReloaded()).isFalse();
    }

    private class TestContainer
        implements ReloadableConfigurationContainer<Object> {
        private boolean isReloaded = false;

        public boolean isReloaded() {
            return isReloaded;
        }

        @Override
        public void reset() {
            isReloaded = true;
        }

        @Override
        public void load(Resource resource)
            throws ConfigurationException {
        }

        @Override
        public void reload()
            throws ConfigurationException {
        }

        @Override
        public void checkReload() {
        }

        @Override
        public Object getConfig() {
            return null;
        }
    }
}
