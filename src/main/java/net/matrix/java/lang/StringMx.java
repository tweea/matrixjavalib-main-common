/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.lang;

import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具。
 */
public final class StringMx {
    /**
     * 阻止实例化。
     */
    private StringMx() {
    }

    /**
     * 在字符串中替换 {@link Map} 中的所有字符串键值对组合。
     * 
     * @param text
     *     替换前的字符串
     * @param replacementMap
     *     字符串键值对
     * @return 替换后的字符串
     */
    public static String replaceEach(String text, Map<String, String> replacementMap) {
        if (StringUtils.isEmpty(text) || MapUtils.isEmpty(replacementMap)) {
            return text;
        }

        int replacementLength = replacementMap.size();
        String[] searchList = new String[replacementLength];
        String[] replacementList = new String[replacementLength];

        int index = -1;
        for (Map.Entry<String, String> replacementEntry : replacementMap.entrySet()) {
            index++;
            searchList[index] = replacementEntry.getKey();
            replacementList[index] = replacementEntry.getValue();
        }

        return StringUtils.replaceEach(text, searchList, replacementList);
    }

    /**
     * 在字符串中替换 {@link Map} 中的所有字符串键值对组合。
     * 
     * @param text
     *     替换前的字符串
     * @param replacementMap
     *     字符串键值对
     * @return 替换后的字符串
     */
    public static String replaceEachRepeatedly(String text, Map<String, String> replacementMap) {
        if (StringUtils.isEmpty(text) || MapUtils.isEmpty(replacementMap)) {
            return text;
        }

        int replacementLength = replacementMap.size();
        String[] searchList = new String[replacementLength];
        String[] replacementList = new String[replacementLength];

        int index = -1;
        for (Map.Entry<String, String> replacementEntry : replacementMap.entrySet()) {
            index++;
            searchList[index] = replacementEntry.getKey();
            replacementList[index] = replacementEntry.getValue();
        }

        return StringUtils.replaceEachRepeatedly(text, searchList, replacementList);
    }
}
