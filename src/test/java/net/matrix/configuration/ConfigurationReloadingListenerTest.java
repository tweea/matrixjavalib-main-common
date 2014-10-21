/*
 * $Id: ConfigurationReloadingListenerTest.java 680 2013-09-04 06:36:26Z tweea@263.net $
 * Copyright(C) 2009 matrix
 * All right reserved.
 */
package net.matrix.configuration;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.AbstractFileConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.fest.reflect.core.Reflection;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.Resource;

public class ConfigurationReloadingListenerTest {
	@Test
	public void testConfigurationReloadingListener() {
		TestContainer container = new TestContainer();
		ConfigurationListener listener = new ConfigurationReloadingListener(container);
		Assert.assertSame(container, Reflection.field("container").ofType(ReloadableConfigurationContainer.class).in(listener).get());
	}

	@Test
	public void testConfigurationChanged() {
		TestContainer container = new TestContainer();
		ConfigurationListener listener = new ConfigurationReloadingListener(container);
		Assert.assertFalse(container.isReloaded());
		listener.configurationChanged(new ConfigurationEvent(this, AbstractFileConfiguration.EVENT_RELOAD, null, null, false));
		Assert.assertTrue(container.isReloaded());
	}

	@Test
	public void testConfigurationChangedBeforeEvent() {
		TestContainer container = new TestContainer();
		ConfigurationListener listener = new ConfigurationReloadingListener(container);
		Assert.assertFalse(container.isReloaded());
		listener.configurationChanged(new ConfigurationEvent(this, AbstractFileConfiguration.EVENT_RELOAD, null, null, true));
		Assert.assertFalse(container.isReloaded());
	}

	@Test
	public void testConfigurationChangedOtherEvent() {
		TestContainer container = new TestContainer();
		ConfigurationListener listener = new ConfigurationReloadingListener(container);
		Assert.assertFalse(container.isReloaded());
		listener.configurationChanged(new ConfigurationEvent(this, AbstractConfiguration.EVENT_CLEAR, null, null, false));
		Assert.assertFalse(container.isReloaded());
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
