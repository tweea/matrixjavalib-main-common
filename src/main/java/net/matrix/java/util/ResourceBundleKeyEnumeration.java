/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.matrix.java.lang.ObjectMx;

/**
 * 实现 {@link ResourceBundle#getKeys()} 方法的枚举器。
 */
public class ResourceBundleKeyEnumeration
    implements Enumeration<String> {
    /**
     * 本级键值集合。
     */
    @Nonnull
    private final Set<String> set;

    /**
     * 本级键值集合对应迭代器。
     */
    @Nonnull
    private final Iterator<String> iterator;

    /**
     * 上级键值集合对应枚举器。
     */
    @Nullable
    private final Enumeration<String> enumeration;

    private String next;

    /**
     * 构造器。
     *
     * @param set
     *     本级键值集合。
     * @param parent
     *     上级。
     */
    public ResourceBundleKeyEnumeration(@Nonnull Set<String> set, @Nullable ResourceBundle parent) {
        this.set = set;
        this.iterator = set.iterator();
        this.enumeration = ObjectMx.ifNotNullMap(parent, ResourceBundle::getKeys);
    }

    @Override
    public boolean hasMoreElements() {
        if (next == null) {
            if (iterator.hasNext()) {
                next = iterator.next();
            } else if (enumeration != null) {
                while (next == null && enumeration.hasMoreElements()) {
                    next = enumeration.nextElement();
                    if (set.contains(next)) {
                        next = null;
                    }
                }
            }
        }
        return next != null;
    }

    @Override
    public String nextElement() {
        if (hasMoreElements()) {
            String result = next;
            next = null;
            return result;
        } else {
            throw new NoSuchElementException();
        }
    }
}
