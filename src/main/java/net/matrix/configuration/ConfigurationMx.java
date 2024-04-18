/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.configuration;

import java.util.Map;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.configuration2.Configuration;

import com.google.common.collect.Maps;

/**
 * 配置对象工具。
 */
public final class ConfigurationMx {
    /**
     * 阻止实例化。
     */
    private ConfigurationMx() {
    }

    /**
     * 从配置对象内容构造 {@link Map} 形式映射关系。
     * 
     * @param config
     *     配置对象。
     * @return 映射关系。
     */
    public static Map<String, String> buildMap(Configuration config) {
        Map<String, String> map = Maps.newHashMapWithExpectedSize(config.size());
        for (String key : IteratorUtils.asIterable(config.getKeys())) {
            String value = config.getString(key);
            map.put(key, value);
        }
        return map;
    }
}
