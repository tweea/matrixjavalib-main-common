/*
 * $Id: ReloadableConfigurationContainer.java 541 2013-03-05 07:00:06Z tweea $
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.configuration;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.core.io.Resource;

import net.matrix.lang.Resettable;

/**
 * 支持重置状态的配置对象容器，用于从指定资源加载内容形成配置对象。
 * 
 * @param <CONFIG>
 *            原始配置对象类型。
 */
public interface ReloadableConfigurationContainer<CONFIG>
	extends Resettable {
	/**
	 * 从资源加载配置。
	 * 
	 * @param resource
	 *            资源
	 * @throws ConfigurationException
	 *             加载失败
	 */
	void load(Resource resource)
		throws ConfigurationException;

	/**
	 * 从资源重新加载配置。
	 * 
	 * @throws ConfigurationException
	 *             重新加载失败
	 */
	void reload()
		throws ConfigurationException;

	/**
	 * 检查是否需要重载，如果需要就重载。
	 */
	void checkReload();

	/**
	 * 获得已加载的原始配置对象。
	 * 
	 * @return 原始配置对象
	 */
	CONFIG getConfig();
}
