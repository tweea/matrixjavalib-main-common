/*
 * Copyright(C) 2020 Matrix
 * All right reserved.
 */
package net.matrix.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration2.Configuration;

import net.matrix.util.IterableIterator;

/**
 * 配置对象工具。
 */
public final class ConfigurationUtils2 {
    /**
     * 阻止实例化。
     */
    private ConfigurationUtils2() {
    }

    /**
     * 转换一个配置对象的内容为 {@code java.util.Map} 对象。
     * 
     * @param config
     *     配置对象
     * @return {@code java.util.Map} 对象
     */
    public static Map<String, String> parseAttributes(final Configuration config) {
        Map<String, String> parameters = new HashMap<>(config.size());
        for (String key : new IterableIterator<>(config.getKeys())) {
            String value = config.getString(key);
            parameters.put(key, value);
        }
        return parameters;
    }
}
