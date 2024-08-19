/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.configuration;

import java.io.IOException;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.ConfigurationBuilderEvent;
import org.apache.commons.configuration2.builder.ConfigurationBuilderResultCreatedEvent;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.builder.fluent.XMLBuilderParameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import net.matrix.text.ResourceBundleMessageFormatter;

/**
 * 读取 XML 格式的配置对象容器。
 */
public class XMLConfigurationContainer
    implements ReloadableConfigurationContainer<XMLConfiguration> {
    /**
     * 日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(XMLConfigurationContainer.class);

    /**
     * 区域相关资源。
     */
    private static final ResourceBundleMessageFormatter RBMF = new ResourceBundleMessageFormatter(XMLConfigurationContainer.class).useCurrentLocale();

    /**
     * 配置对象构建器初始参数。
     */
    private static final Parameters PARAMETERS = new Parameters();

    /**
     * 配置对象构建器。
     */
    private final ReloadingFileBasedConfigurationBuilder<XMLConfiguration> configBuilder;

    /**
     * 资源。
     */
    private Resource resource;

    /**
     * 是否支持检查配置是否需要重新加载。
     */
    private boolean canCheckReload;

    /**
     * 构造器，未加载资源。
     */
    public XMLConfigurationContainer() {
        this.configBuilder = new ReloadingFileBasedConfigurationBuilder<>(XMLConfiguration.class);
        ReloadableConfigurationContainerEventListener eventListener = new ReloadableConfigurationContainerEventListener(this);
        this.configBuilder.addEventListener(ConfigurationBuilderEvent.CONFIGURATION_REQUEST, eventListener);
        this.configBuilder.addEventListener(ConfigurationBuilderResultCreatedEvent.RESULT_CREATED, eventListener);
        XMLBuilderParameters configBuilderParameters = buildConfigBuilderParameters();
        this.configBuilder.configure(configBuilderParameters);
    }

    @Override
    public void load(Resource newResource)
        throws ConfigurationException {
        if (LOG.isDebugEnabled()) {
            LOG.debug(RBMF.get("从资源 {} 加载配置"), newResource);
        }

        configBuilder.reset();
        resource = newResource;
        canCheckReload = false;

        XMLBuilderParameters configBuilderParameters = buildConfigBuilderParameters();
        try {
            configBuilderParameters.setURL(resource.getURL());
            canCheckReload = true;
        } catch (IOException e) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(RBMF.get("从资源 {} 尝试使用 URL 方式加载配置失败"), resource, e);
            }
        }
        configBuilder.configure(configBuilderParameters);
        loadConfig();
    }

    /**
     * 在配置对象构建器重置后加载配置。
     */
    private void loadConfig()
        throws ConfigurationException {
        // 通过获取配置对象触发加载
        XMLConfiguration config = configBuilder.getConfiguration();
        if (!canCheckReload()) {
            if (resource != null) {
                try {
                    FileHandler fileHandler = new FileHandler(config);
                    fileHandler.load(resource.getInputStream());
                } catch (IOException e) {
                    throw new ConfigurationException(e);
                }
            }
            reset();
        }
    }

    @Override
    public void reload()
        throws ConfigurationException {
        configBuilder.resetResult();
        loadConfig();
    }

    @Override
    public boolean canCheckReload() {
        return canCheckReload;
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
        if (LOG.isDebugEnabled()) {
            LOG.debug(RBMF.get("资源 {} 的配置对象 {} 状态重置"), resource, this);
        }
    }

    /**
     * 构建配置对象构建器参数。
     *
     * @return 配置对象构建器参数。
     */
    protected XMLBuilderParameters buildConfigBuilderParameters() {
        XMLBuilderParameters configBuilderParameters = PARAMETERS.xml();
        if (!isDelimiterParsingDisabled()) {
            configBuilderParameters.setListDelimiterHandler(new DefaultListDelimiterHandler(getDelimiter()));
        }
        return configBuilderParameters;
    }

    /**
     * 在解析配置内容时是否禁用分隔符。如果使用分隔符，配置内容中包含分隔符的内容会被解析为数组，否则解析为单个值。
     * 默认实现为使用分隔符。
     *
     * @return 是否禁用分隔符。
     */
    protected boolean isDelimiterParsingDisabled() {
        return false;
    }

    /**
     * 在解析配置内容时使用的分隔符。
     * 默认实现为英文逗号。
     *
     * @return 分隔符。
     */
    protected char getDelimiter() {
        return ',';
    }
}
