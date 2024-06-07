/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.configuration;

import javax.annotation.Nonnull;

import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 * 可配置接口，实现此接口的类可以使用特定类型的配置对象进行配置。
 */
public interface Configurable<CONFIG> {
    /**
     * 使用配置对象进行配置。
     * 
     * @param config
     *     配置对象。
     * @throws ConfigurationException
     *     配置失败。
     */
    void configure(@Nonnull CONFIG config)
        throws ConfigurationException;
}
