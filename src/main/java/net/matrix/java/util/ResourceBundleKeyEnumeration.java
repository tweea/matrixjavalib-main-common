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

/**
 * 实现 {@link ResourceBundle#getKeys()} 方法。
 */
public class ResourceBundleKeyEnumeration
    implements Enumeration<String> {
    private Set<String> set;

    private Iterator<String> iterator;

    private Enumeration<String> enumeration;

    private String next;

    public ResourceBundleKeyEnumeration(Set<String> set, ResourceBundle parent) {
        this.set = set;
        this.iterator = set.iterator();
        if (parent == null) {
            this.enumeration = null;
        } else {
            this.enumeration = parent.getKeys();
        }
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
