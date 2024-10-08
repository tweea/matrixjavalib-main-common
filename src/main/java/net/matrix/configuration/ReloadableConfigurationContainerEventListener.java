/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.configuration;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import org.apache.commons.configuration2.builder.ConfigurationBuilderEvent;
import org.apache.commons.configuration2.builder.ConfigurationBuilderResultCreatedEvent;
import org.apache.commons.configuration2.event.EventListener;
import org.apache.commons.configuration2.event.EventType;

/**
 * 监听配置对象容器中配置对象构建器相关事件的事件监听器。
 */
@Immutable
public class ReloadableConfigurationContainerEventListener
    implements EventListener<ConfigurationBuilderEvent> {
    /**
     * 配置对象容器。
     */
    private final ReloadableConfigurationContainer container;

    /**
     * 构造器，使用指定配置对象容器。
     *
     * @param container
     *     配置对象容器。
     */
    public ReloadableConfigurationContainerEventListener(@Nonnull ReloadableConfigurationContainer container) {
        this.container = container;
    }

    @Override
    public void onEvent(ConfigurationBuilderEvent event) {
        EventType eventType = event.getEventType();
        // 访问事件，在访问配置对象前检查是否需要重新加载
        if (EventType.isInstanceOf(eventType, ConfigurationBuilderEvent.CONFIGURATION_REQUEST)) {
            if (container.canCheckReload()) {
                container.checkReload();
            }
        }
        // 创建事件，在创建配置对象后重置配置对象容器
        else if (EventType.isInstanceOf(eventType, ConfigurationBuilderResultCreatedEvent.RESULT_CREATED)) {
            if (container.canCheckReload()) {
                container.reset();
            }
        }
    }
}
