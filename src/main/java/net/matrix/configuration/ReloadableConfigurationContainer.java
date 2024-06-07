/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.configuration;

import javax.annotation.Nonnull;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.core.io.Resource;

import net.matrix.lang.Resettable;

/**
 * 配置对象容器，用于从指定资源加载内容形成配置对象，支持重新加载。
 * 
 * @param <CONFIG>
 *     配置对象。
 */
public interface ReloadableConfigurationContainer<CONFIG>
    extends Resettable {
    /**
     * 从资源加载配置。
     * 
     * @param resource
     *     资源。
     * @throws ConfigurationException
     *     加载失败。
     */
    void load(@Nonnull Resource resource)
        throws ConfigurationException;

    /**
     * 重新加载配置。
     * 
     * @throws ConfigurationException
     *     加载失败。
     */
    void reload()
        throws ConfigurationException;

    /**
     * 是否支持检查配置是否需要重新加载。
     */
    boolean canCheckReload();

    /**
     * 检查配置是否需要重新加载，如果需要则重新加载配置。
     */
    void checkReload();

    /**
     * 获取配置对象。
     * 
     * @return 配置对象。
     * @throws ConfigurationException
     *     获取失败。
     */
    @Nonnull
    CONFIG getConfig()
        throws ConfigurationException;
}
