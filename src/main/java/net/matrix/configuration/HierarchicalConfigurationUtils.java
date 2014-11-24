/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;

import net.matrix.text.MessageFormats;
import net.matrix.text.ResourceBundles;
import net.matrix.util.IterableIterator;

/**
 * 配置对象实用工具。
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
	 * 从一个表示名字、数值对的配置对象中抽取信息转换为 {@code java.util.Map} 对象。
	 * 
	 * @param config
	 *            配置对象
	 * @param subKey
	 *            子项配置键值
	 * @param nameKey
	 *            名字配置键值
	 * @param valueKey
	 *            值配置键值
	 * @return {@code java.util.Map} 对象
	 */
	public static Map<String, String> parseParameter(final HierarchicalConfiguration config, final String subKey, final String nameKey, final String valueKey) {
		Map<String, String> parameters = new HashMap<>();
		for (HierarchicalConfiguration subConfig : config.configurationsAt(subKey)) {
			String name = subConfig.getString(nameKey);
			String value = subConfig.getString(valueKey);
			parameters.put(name, value);
		}
		return parameters;
	}

	/**
	 * 把一个 {@code java.util.Map} 对象的内容更新到配置对象中。
	 * 
	 * @param config
	 *            配置对象
	 * @param subKey
	 *            子项配置键值
	 * @param nameKey
	 *            名字配置键值
	 * @param valueKey
	 *            值配置键值
	 * @param parameters
	 *            {@code java.util.Map} 对象
	 */
	public static void updateParameter(final HierarchicalConfiguration config, final String subKey, final String nameKey, final String valueKey,
		final Map<String, String> parameters) {
		for (HierarchicalConfiguration subConfig : config.configurationsAt(subKey)) {
			subConfig.clear();
		}
		String newNameKey = subKey + "(-1)." + nameKey;
		String newValueKey = subKey + "." + valueKey;
		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			config.addProperty(newNameKey, parameter.getKey());
			config.addProperty(newValueKey, parameter.getValue());
		}
	}

	/**
	 * 从一个配置对象中抽取信息转换为 {@code java.util.Map} 对象。
	 * 
	 * @param config
	 *            配置对象
	 * @return {@code java.util.Map} 对象
	 */
	public static Map<String, String> parseAttributes(final HierarchicalConfiguration config) {
		Map<String, String> parameters = new HashMap<>();
		for (String key : new IterableIterator<>(config.getKeys())) {
			String value = config.getString(key);
			parameters.put(key, value);
		}
		return parameters;
	}

	/**
	 * 列出所有配置对象的名字。
	 * 
	 * @param config
	 *            配置对象
	 * @param subKey
	 *            子项配置键值
	 * @param nameKey
	 *            名字配置键值
	 * @return 所有配置对象的名字列表
	 */
	public static List<String> listAllNames(final HierarchicalConfiguration config, final String subKey, final String nameKey) {
		List<String> names = new ArrayList<>();
		for (HierarchicalConfiguration subConfig : config.configurationsAt(subKey)) {
			String name = subConfig.getString(nameKey);
			names.add(name);
		}
		return names;
	}

	/**
	 * 从多个配置对象中找出符合给定名字的配置对象。
	 * 
	 * @param config
	 *            配置对象
	 * @param subKey
	 *            子项配置键值
	 * @param nameKey
	 *            名字配置键值
	 * @param nameValue
	 *            属性值
	 * @throws ConfigurationException
	 *             找不到指定配置
	 * @return 匹配的配置对象
	 */
	public static HierarchicalConfiguration findForName(final HierarchicalConfiguration config, final String subKey, final String nameKey,
		final String nameValue)
		throws ConfigurationException {
		for (HierarchicalConfiguration subConfig : config.configurationsAt(subKey)) {
			String name = subConfig.getString(nameKey);
			if (name.equals(nameValue)) {
				return subConfig;
			}
		}
		// 没有找到
		throw new ConfigurationException(MessageFormats.format(ResourceBundles.getBundle(RESOURCE_BASENAME), "subnodeNotFound", nameKey, nameValue, subKey));
	}
}
