/*
 * Copyright(C) 2011 Matrix
 * All right reserved.
 */
package net.matrix.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import net.matrix.lang.ReflectionRuntimeException;

/**
 * Collection 工具。
 * 在 JDK 的 Collections 和 Guava 的 Collections2 后，命名为 Collections3。
 */
public final class Collections3 {
    /**
     * 阻止实例化。
     */
    private Collections3() {
    }

    /**
     * 提取集合中的对象的两个属性（通过 Getter 方法），组合成 Map。
     * 
     * @param collection
     *     来源集合
     * @param keyPropertyName
     *     要提取为 Map 中的 Key 值的属性名
     * @param valuePropertyName
     *     要提取为 Map 中的 Value 值的属性名
     * @return 属性集合
     */
    public static Map extractToMap(final Collection collection, final String keyPropertyName, final String valuePropertyName) {
        Map map = new HashMap(collection.size());

        try {
            for (Object obj : collection) {
                map.put(PropertyUtils.getProperty(obj, keyPropertyName), PropertyUtils.getProperty(obj, valuePropertyName));
            }
        } catch (ReflectiveOperationException e) {
            throw new ReflectionRuntimeException(e);
        }

        return map;
    }

    /**
     * 提取集合中的对象的一个属性（通过 Getter 函数），组合成 List。
     * 
     * @param collection
     *     来源集合
     * @param propertyName
     *     要提取的属性名
     * @return 属性列表
     */
    public static List extractToList(final Collection collection, final String propertyName) {
        List list = new ArrayList(collection.size());

        try {
            for (Object obj : collection) {
                list.add(PropertyUtils.getProperty(obj, propertyName));
            }
        } catch (ReflectiveOperationException e) {
            throw new ReflectionRuntimeException(e);
        }

        return list;
    }

    /**
     * 提取集合中的对象的一个属性（通过 Getter 函数），组合成由分割符分隔的字符串。
     * 
     * @param collection
     *     来源集合
     * @param propertyName
     *     要提取的属性名
     * @param separator
     *     分隔符
     * @return 组合字符串
     */
    public static String extractToString(final Collection collection, final String propertyName, final String separator) {
        List list = extractToList(collection, propertyName);
        return StringUtils.join(list, separator);
    }

    /**
     * 转换 Collection 所有元素（通过 toString()）为 String，每个元素的前面加入 prefix，后面加入
     * postfix。如：
     * {mymessage}
     * 
     * @param collection
     *     来源集合
     * @param prefix
     *     前缀
     * @param postfix
     *     后缀
     * @return 组合字符串
     */
    public static String convertToString(final Collection collection, final String prefix, final String postfix) {
        StringBuilder builder = new StringBuilder();
        for (Object o : collection) {
            builder.append(prefix).append(o).append(postfix);
        }
        return builder.toString();
    }
}
