/*
 * $Id: ConfigurationReloadingListener.java 541 2013-03-05 07:00:06Z tweea $
 * Copyright(C) 2009 matrix
 * All right reserved.
 */
package net.matrix.configuration;

import org.apache.commons.configuration.AbstractFileConfiguration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;

/**
 * 监听配置对象容器原始配置对象的重新加载事件，事件发生后重置相应配置对象容器的状态。
 */
public class ConfigurationReloadingListener
	implements ConfigurationListener {
	/**
	 * 需要重置状态的配置对象容器。
	 */
	private final ReloadableConfigurationContainer container;

	/**
	 * 构造一个 {@code ConfigurationReloadingListener}，指定需要重置状态的配置对象容器。
	 * 
	 * @param container
	 *            需要重置状态的配置对象容器
	 */
	public ConfigurationReloadingListener(final ReloadableConfigurationContainer container) {
		this.container = container;
	}

	@Override
	public void configurationChanged(final ConfigurationEvent event) {
		// 当事件类型为重新加载并加载完毕后
		if (event.getType() == AbstractFileConfiguration.EVENT_RELOAD && !event.isBeforeUpdate()) {
			// 调用重新加载配置
			container.reset();
		}
	}
}
