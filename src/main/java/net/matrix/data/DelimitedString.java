/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang3.StringUtils;

/**
 * 表示一种特定格式的字符串，其内容由特定分隔符分隔成多个部分。默认的分隔符是英文逗号“,”。
 */
public class DelimitedString
    implements List<String> {
    /**
     * 默认的分隔符。
     */
    public static final String DEFAULT_DELIMITER = ",";

    /**
     * 分隔符。
     */
    private String delimiter;

    /**
     * 实际内容。
     */
    private List<String> content;

    /**
     * 构造使用默认分隔符的实例，内容为空。
     */
    public DelimitedString() {
        this.delimiter = DEFAULT_DELIMITER;
        this.content = new ArrayList<>();
    }

    /**
     * 构造使用默认分隔符的实例，内容从字符串分隔获取。
     * 
     * @param value
     *     使用默认分隔符分隔的字符串
     */
    public DelimitedString(final String value) {
        this(value, DEFAULT_DELIMITER);
    }

    /**
     * 构造使用指定分隔符的实例，内容从字符串分隔获取。
     * 
     * @param value
     *     使用指定分隔符分隔的字符串
     * @param delimiter
     *     分隔符
     */
    public DelimitedString(final String value, final String delimiter) {
        this.delimiter = delimiter;
        this.content = new ArrayList<>();
        String[] valueParts = StringUtils.split(value, delimiter);
        for (String valuePart : valueParts) {
            this.content.add(StringUtils.trim(valuePart));
        }
    }

    /**
     * 构造使用默认分隔符的实例，内容从字符串数组获取。
     * 
     * @param valueParts
     *     字符串数组
     */
    public DelimitedString(final String[] valueParts) {
        this(valueParts, DEFAULT_DELIMITER);
    }

    /**
     * 构造使用指定分隔符的实例，内容从字符串数组获取。
     * 
     * @param valueParts
     *     字符串数组
     * @param delimiter
     *     分隔符
     */
    public DelimitedString(final String[] valueParts, final String delimiter) {
        this.delimiter = delimiter;
        this.content = new ArrayList<>();
        for (String valuePart : valueParts) {
            this.content.add(StringUtils.trim(valuePart));
        }
    }

    /**
     * 构造使用默认分隔符的实例，内容从字符串列表获取。
     * 
     * @param valueParts
     *     字符串列表
     */
    public DelimitedString(final List<String> valueParts) {
        this(valueParts, DEFAULT_DELIMITER);
    }

    /**
     * 构造使用指定分隔符的实例，内容从字符串列表获取。
     * 
     * @param valueParts
     *     字符串列表
     * @param delimiter
     *     分隔符
     */
    public DelimitedString(final List<String> valueParts, final String delimiter) {
        this.delimiter = delimiter;
        this.content = new ArrayList<>();
        for (String valuePart : valueParts) {
            this.content.add(StringUtils.trim(valuePart));
        }
    }

    /**
     * 获取分隔符。
     * 
     * @return 分隔符
     */
    public String getDelimiter() {
        return delimiter;
    }

    /**
     * 设置分隔符。
     * 
     * @param delimiter
     *     分隔符
     */
    public void setDelimiter(final String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public void add(final int index, final String element) {
        content.add(index, element);
    }

    @Override
    public boolean add(final String o) {
        return content.add(o);
    }

    @Override
    public boolean addAll(final Collection<? extends String> c) {
        return content.addAll(c);
    }

    @Override
    public boolean addAll(final int index, final Collection<? extends String> c) {
        return content.addAll(index, c);
    }

    @Override
    public void clear() {
        content.clear();
    }

    @Override
    public boolean contains(final Object obj) {
        return content.contains(obj);
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        return content.containsAll(c);
    }

    @Override
    public boolean equals(final Object obj) {
        return content.equals(obj);
    }

    @Override
    public String get(final int i) {
        return content.get(i);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }

    @Override
    public int indexOf(final Object obj) {
        return content.indexOf(obj);
    }

    @Override
    public boolean isEmpty() {
        return content.isEmpty();
    }

    @Override
    public Iterator<String> iterator() {
        return content.iterator();
    }

    @Override
    public int lastIndexOf(final Object obj) {
        return content.lastIndexOf(obj);
    }

    @Override
    public ListIterator<String> listIterator() {
        return content.listIterator();
    }

    @Override
    public ListIterator<String> listIterator(final int i) {
        return content.listIterator(i);
    }

    @Override
    public String remove(final int i) {
        return content.remove(i);
    }

    @Override
    public boolean remove(final Object obj) {
        return content.remove(obj);
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        return content.removeAll(c);
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        return content.retainAll(c);
    }

    @Override
    public String set(final int index, final String element) {
        return content.set(index, element);
    }

    @Override
    public int size() {
        return content.size();
    }

    @Override
    public DelimitedString subList(final int i, final int j) {
        return new DelimitedString(content.subList(i, j), delimiter);
    }

    @Override
    public Object[] toArray() {
        return content.toArray();
    }

    @Override
    public <T> T[] toArray(final T[] a) {
        return content.toArray(a);
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < content.size(); i++) {
            buffer.append(content.get(i));
            if (i < content.size() - 1) {
                buffer.append(delimiter);
            }
        }
        return buffer.toString();
    }
}
