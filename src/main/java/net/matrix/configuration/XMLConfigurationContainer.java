/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.configuration;

import java.io.IOException;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.ConfigurationBuilderEvent;
import org.apache.commons.configuration2.builder.ConfigurationBuilderResultCreatedEvent;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.ex.ConfigurationRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

/**
 * 读取 XML 格式的配置对象容器。
 */
public class XMLConfigurationContainer
    implements ReloadableConfigurationContainer<XMLConfiguration> {
    /**
     * 日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(XMLConfigurationContainer.class);

    private static final Parameters PARAMETERS = new Parameters();

    /**
     * XML 格式配置对象的构建器。
     */
    private final ReloadingFileBasedConfigurationBuilder<XMLConfiguration> configBuilder;

    /**
     * 尚未加载资源的配置对象容器。
     */
    public XMLConfigurationContainer() {
        this.configBuilder = new ReloadingFileBasedConfigurationBuilder<>(XMLConfiguration.class);
        ReloadableConfigurationContainerEventListener eventListener = new ReloadableConfigurationContainerEventListener(this);
        this.configBuilder.addEventListener(ConfigurationBuilderEvent.CONFIGURATION_REQUEST, eventListener);
        this.configBuilder.addEventListener(ConfigurationBuilderResultCreatedEvent.RESULT_CREATED, eventListener);
    }

    @Override
    public void load(final Resource resource)
        throws ConfigurationException {
        configBuilder.reset();
        LOG.debug("加载配置：{}", resource);
        try {
            configBuilder.configure(PARAMETERS.xml().setURL(resource.getURL()));
        } catch (IOException e) {
            throw new ConfigurationException(e);
        }
        configBuilder.getConfiguration();
    }

    @Override
    public void reload()
        throws ConfigurationException {
        configBuilder.resetResult();
        configBuilder.getConfiguration();
    }

    @Override
    public void checkReload() {
        configBuilder.getReloadingController().checkForReloading(null);
    }

    @Override
    public XMLConfiguration getConfig()
        throws ConfigurationException {
        return configBuilder.getConfiguration();
    }

    @Override
    public void reset() {
        LOG.debug("配置对象 {} 状态重置", this);
        XMLConfiguration config;
        try {
            config = configBuilder.getConfiguration();
        } catch (ConfigurationException e) {
            throw new ConfigurationRuntimeException(e);
        }
        if (!isDelimiterParsingDisabled()) {
            config.setListDelimiterHandler(new DefaultListDelimiterHandler(','));
        }
    }

    /**
     * 是否解析配置内容中的分隔符。如果是配置内容中带分隔符的内容会被解析为数组，否则解析为单个值。
     * 默认实现为解析。
     * 
     * @return true 为不解析
     */
    protected boolean isDelimiterParsingDisabled() {
        return false;
    }
}
