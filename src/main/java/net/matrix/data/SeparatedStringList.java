/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.RandomAccess;

import org.apache.commons.lang3.StringUtils;

/**
 * 由多个字符串组成的列表，可以使用分隔符连接成一个完整字符串。默认的分隔符是英文逗号“,”。
 */
public class SeparatedStringList
    implements List<String>, RandomAccess {
    /**
     * 默认的分隔符。
     */
    public static final String DEFAULT_DELIMITER = ",";

    /**
     * 分隔符。
     */
    private String delimiter;

    /**
     * 字符串列表。
     */
    private List<String> strings;

    /**
     * 构造器，使用默认的分隔符，字符串列表为空。
     */
    public SeparatedStringList() {
        this.delimiter = DEFAULT_DELIMITER;
        this.strings = new ArrayList<>();
    }

    /**
     * 构造器，使用默认的分隔符，字符串列表从完整字符串分隔获取。
     * 
     * @param wholeString
     *     完整字符串。
     */
    public SeparatedStringList(String wholeString) {
        this(wholeString, DEFAULT_DELIMITER);
    }

    /**
     * 构造器，使用指定的分隔符，字符串列表从完整字符串分隔获取。
     * 
     * @param wholeString
     *     完整字符串。
     * @param delimiter
     *     分隔符。
     */
    public SeparatedStringList(String wholeString, String delimiter) {
        this(StringUtils.splitByWholeSeparator(wholeString, delimiter), delimiter);
    }

    /**
     * 构造器，使用默认的分隔符，字符串列表从字符串数组获取。
     * 
     * @param separatedStrings
     *     字符串数组。
     */
    public SeparatedStringList(String[] separatedStrings) {
        this(separatedStrings, DEFAULT_DELIMITER);
    }

    /**
     * 构造器，使用指定的分隔符，字符串列表从字符串数组获取。
     * 
     * @param separatedStrings
     *     字符串数组。
     * @param delimiter
     *     分隔符。
     */
    public SeparatedStringList(String[] separatedStrings, String delimiter) {
        this.delimiter = delimiter;
        this.strings = new ArrayList<>(separatedStrings.length);
        for (String separatedString : separatedStrings) {
            this.strings.add(StringUtils.trimToEmpty(separatedString));
        }
    }

    /**
     * 构造器，使用默认的分隔符，字符串列表从字符串列表获取。
     * 
     * @param separatedStrings
     *     字符串列表。
     */
    public SeparatedStringList(List<String> separatedStrings) {
        this(separatedStrings, DEFAULT_DELIMITER);
    }

    /**
     * 构造器，使用指定的分隔符，字符串列表从字符串列表获取。
     * 
     * @param separatedStrings
     *     字符串列表。
     * @param delimiter
     *     分隔符。
     */
    public SeparatedStringList(List<String> separatedStrings, String delimiter) {
        this.delimiter = delimiter;
        this.strings = new ArrayList<>(separatedStrings.size());
        for (String separatedString : separatedStrings) {
            this.strings.add(StringUtils.trimToEmpty(separatedString));
        }
    }

    /**
     * 获取分隔符。
     * 
     * @return 分隔符。
     */
    public String getDelimiter() {
        return delimiter;
    }

    /**
     * 设置分隔符。
     * 
     * @param delimiter
     *     分隔符。
     */
    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public int size() {
        return strings.size();
    }

    @Override
    public boolean isEmpty() {
        return strings.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return strings.contains(o);
    }

    @Override
    public Iterator<String> iterator() {
        return strings.iterator();
    }

    @Override
    public Object[] toArray() {
        return strings.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return strings.toArray(a);
    }

    @Override
    public boolean add(String e) {
        return strings.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return strings.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return strings.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        return strings.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        return strings.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return strings.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return strings.retainAll(c);
    }

    @Override
    public void clear() {
        strings.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SeparatedStringList)) {
            return false;
        }
        SeparatedStringList other = (SeparatedStringList) o;
        return Objects.equals(delimiter, other.delimiter) && Objects.equals(strings, other.strings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(delimiter, strings);
    }

    @Override
    public String get(int index) {
        return strings.get(index);
    }

    @Override
    public String set(int index, String element) {
        return strings.set(index, element);
    }

    @Override
    public void add(int index, String element) {
        strings.add(index, element);
    }

    @Override
    public String remove(int index) {
        return strings.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return strings.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return strings.lastIndexOf(o);
    }

    @Override
    public ListIterator<String> listIterator() {
        return strings.listIterator();
    }

    @Override
    public ListIterator<String> listIterator(int index) {
        return strings.listIterator(index);
    }

    @Override
    public SeparatedStringList subList(int fromIndex, int toIndex) {
        return new SeparatedStringList(strings.subList(fromIndex, toIndex), delimiter);
    }

    @Override
    public String toString() {
        return StringUtils.join(strings, delimiter);
    }
}
