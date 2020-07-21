/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.configuration;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.core.io.Resource;

import net.matrix.lang.Resettable;

/**
 * 支持重置的配置对象容器，用于从指定资源加载内容形成配置对象。
 * 
 * @param <CONFIG>
 *     配置对象类型。
 */
public interface ReloadableConfigurationContainer<CONFIG>
    extends Resettable {
    /**
     * 从资源加载配置。
     * 
     * @param resource
     *     资源
     * @throws ConfigurationException
     *     加载失败
     */
    void load(Resource resource)
        throws ConfigurationException;

    /**
     * 重新加载配置。
     * 
     * @throws ConfigurationException
     *     加载失败
     */
    void reload()
        throws ConfigurationException;

    /**
     * 检查是否需要重新加载，如果需要则重新加载。
     */
    void checkReload();

    /**
     * 获取配置对象。
     * 
     * @return 配置对象
     * @throws ConfigurationException
     *     获取失败
     */
    CONFIG getConfig()
        throws ConfigurationException;
}
