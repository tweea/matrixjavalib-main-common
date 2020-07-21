/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.configuration;

import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 * 可配置接口，实现此接口的类可以使用特定类型的配置对象进行配置。
 * 
 * @param <CONFIG>
 *     可以应用的配置对象类型
 */
public interface Configurable<CONFIG> {
    /**
     * 应用一个配置对象。
     * 
     * @param config
     *     配置对象
     * @throws ConfigurationException
     *     应用配置对象失败
     */
    void configure(CONFIG config)
        throws ConfigurationException;
}
