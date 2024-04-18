/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration2.HierarchicalConfiguration;

import com.google.common.collect.Maps;

/**
 * 分层配置对象工具。
 */
public final class HierarchicalConfigurationMx {
    /**
     * 阻止实例化。
     */
    private HierarchicalConfigurationMx() {
    }

    /**
     * 将分层配置对象中特定子项包含的名值对内容转换为 {@link Map} 形式映射关系。
     * 
     * @param config
     *     分层配置对象。
     * @param subKey
     *     子项配置键值。
     * @param nameKey
     *     名配置键值。
     * @param valueKey
     *     值配置键值。
     * @return 映射关系。
     */
    public static Map<String, String> buildNameValueMap(HierarchicalConfiguration config, String subKey, String nameKey, String valueKey) {
        List<HierarchicalConfiguration> subConfigs = config.configurationsAt(subKey);

        Map<String, String> nameValueMap = Maps.newHashMapWithExpectedSize(subConfigs.size());
        for (HierarchicalConfiguration subConfig : subConfigs) {
            String name = subConfig.getString(nameKey);
            String value = subConfig.getString(valueKey);
            nameValueMap.put(name, value);
        }
        return nameValueMap;
    }

    /**
     * 使用 {@link Map} 形式映射关系更新分层配置对象中特定子项包含的名值对。
     * 
     * @param config
     *     分层配置对象。
     * @param subKey
     *     子项配置键值。
     * @param nameKey
     *     名配置键值。
     * @param valueKey
     *     值配置键值。
     * @param nameValueMap
     *     映射关系。
     */
    public static void updateNameValueMap(HierarchicalConfiguration config, String subKey, String nameKey, String valueKey, Map<String, String> nameValueMap) {
        config.clearTree(subKey);
        String newNameKey = subKey + "(-1)." + nameKey;
        String newValueKey = subKey + '.' + valueKey;
        for (Map.Entry<String, String> nameValueEntry : nameValueMap.entrySet()) {
            config.addProperty(newNameKey, nameValueEntry.getKey());
            config.addProperty(newValueKey, nameValueEntry.getValue());
        }
    }

    /**
     * 列出分层配置对象中特定子项包含的名配置。
     * 
     * @param config
     *     分层配置对象。
     * @param subKey
     *     子项配置键值。
     * @param nameKey
     *     名配置键值。
     * @return 名配置列表。
     */
    public static List<String> buildNameList(HierarchicalConfiguration config, String subKey, String nameKey) {
        List<HierarchicalConfiguration> subConfigs = config.configurationsAt(subKey);

        List<String> names = new ArrayList<>(subConfigs.size());
        for (HierarchicalConfiguration subConfig : subConfigs) {
            names.add(subConfig.getString(nameKey));
        }
        return names;
    }

    /**
     * 从分层配置对象中特定子项中找出匹配特定名配置的子项配置。
     * 
     * @param config
     *     分层配置对象。
     * @param subKey
     *     子项配置键值。
     * @param nameKey
     *     名配置键值。
     * @param nameValue
     *     名配置。
     * @return 子项配置。
     */
    public static HierarchicalConfiguration forName(HierarchicalConfiguration config, String subKey, String nameKey, String nameValue) {
        List<HierarchicalConfiguration> subConfigs = config.configurationsAt(subKey);
        for (HierarchicalConfiguration subConfig : subConfigs) {
            String name = subConfig.getString(nameKey);
            if (name.equals(nameValue)) {
                return subConfig;
            }
        }
        // 没有找到
        return null;
    }
}
