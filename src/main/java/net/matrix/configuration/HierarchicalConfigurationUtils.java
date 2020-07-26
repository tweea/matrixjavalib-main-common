/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;

import net.matrix.text.MessageFormats;
import net.matrix.text.ResourceBundles;

/**
 * 分层配置对象工具。
 */
public final class HierarchicalConfigurationUtils {
    /**
     * 资源位置。
     */
    private static final String RESOURCE_BASENAME = "net.matrix.configuration.Messages";

    /**
     * 阻止实例化。
     */
    private HierarchicalConfigurationUtils() {
    }

    /**
     * 从一个存储名值对的分层配置对象中抽取信息转换为 {@link Map} 对象。
     * 
     * @param config
     *     分层配置对象
     * @param subKey
     *     子项配置键值
     * @param nameKey
     *     名配置键值
     * @param valueKey
     *     值配置键值
     * @return {@link Map} 对象
     */
    public static <T> Map<String, String> parseParameter(final HierarchicalConfiguration<T> config, final String subKey, final String nameKey,
        final String valueKey) {
        Map<String, String> parameters = new HashMap<>();
        for (HierarchicalConfiguration<T> subConfig : config.configurationsAt(subKey)) {
            String name = subConfig.getString(nameKey);
            String value = subConfig.getString(valueKey);
            parameters.put(name, value);
        }
        return parameters;
    }

    /**
     * 把一个 {@link Map} 对象的内容更新到存储名值对的分层配置对象中。
     * 
     * @param config
     *     分层配置对象
     * @param subKey
     *     子项配置键值
     * @param nameKey
     *     名配置键值
     * @param valueKey
     *     值配置键值
     * @param parameters
     *     {@link Map} 对象
     */
    public static <T> void updateParameter(final HierarchicalConfiguration<T> config, final String subKey, final String nameKey, final String valueKey,
        final Map<String, String> parameters) {
        config.clearTree(subKey);
        String newNameKey = subKey + "(-1)." + nameKey;
        String newValueKey = subKey + '.' + valueKey;
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            config.addProperty(newNameKey, parameter.getKey());
            config.addProperty(newValueKey, parameter.getValue());
        }
    }

    /**
     * 列出一个分层配置对象中所有子项配置的名配置。
     * 
     * @param config
     *     分层配置对象
     * @param subKey
     *     子项配置键值
     * @param nameKey
     *     名配置键值
     * @return 名配置列表
     */
    public static <T> List<String> listAllNames(final HierarchicalConfiguration<T> config, final String subKey, final String nameKey) {
        List<String> names = new ArrayList<>();
        for (HierarchicalConfiguration<T> subConfig : config.configurationsAt(subKey)) {
            names.add(subConfig.getString(nameKey));
        }
        return names;
    }

    /**
     * 从一个分层配置对象中找出匹配给定名配置的子项配置。
     * 
     * @param config
     *     分层配置对象
     * @param subKey
     *     子项配置键值
     * @param nameKey
     *     名配置键值
     * @param nameValue
     *     名配置值
     * @return 匹配的子项配置
     * @throws ConfigurationException
     *     找不到匹配的子项配置
     */
    public static <T> HierarchicalConfiguration<T> findForName(final HierarchicalConfiguration<T> config, final String subKey, final String nameKey,
        final String nameValue)
        throws ConfigurationException {
        for (HierarchicalConfiguration<T> subConfig : config.configurationsAt(subKey)) {
            String name = subConfig.getString(nameKey);
            if (name.equals(nameValue)) {
                return subConfig;
            }
        }
        // 没有找到
        throw new ConfigurationException(MessageFormats.format(ResourceBundles.getBundle(RESOURCE_BASENAME), "subnodeNotFound", nameKey, nameValue, subKey));
    }
}
