/*
 * Copyright(C) 2009 matrix
 * All right reserved.
 */
package net.matrix.configuration;

import org.apache.commons.configuration2.builder.ConfigurationBuilderEvent;
import org.apache.commons.configuration2.builder.ConfigurationBuilderResultCreatedEvent;
import org.apache.commons.configuration2.event.EventListener;
import org.apache.commons.configuration2.event.EventType;

/**
 * 在配置对象容器中监听配置对象的相关事件。
 */
public class ReloadableConfigurationContainerEventListener
    implements EventListener<ConfigurationBuilderEvent> {
    /**
     * 配置对象容器。
     */
    private final ReloadableConfigurationContainer container;

    /**
     * 指定配置对象容器。
     * 
     * @param container
     *     配置对象容器
     */
    public ReloadableConfigurationContainerEventListener(final ReloadableConfigurationContainer container) {
        this.container = container;
    }

    @Override
    public void onEvent(final ConfigurationBuilderEvent event) {
        EventType eventType = event.getEventType();
        // 访问事件，在访问配置对象前检查是否需要重新加载
        if (EventType.isInstanceOf(eventType, ConfigurationBuilderEvent.CONFIGURATION_REQUEST)) {
            container.checkReload();
        }
        // 创建事件，在创建配置对象后重置配置对象容器
        else if (EventType.isInstanceOf(eventType, ConfigurationBuilderResultCreatedEvent.RESULT_CREATED)) {
            container.reset();
        }
    }
}
