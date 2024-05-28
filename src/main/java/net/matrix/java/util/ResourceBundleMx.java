/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.matrix.text.ResourceBundleMessageFormatter;

/**
 * 区域相关资源工具。
 */
public final class ResourceBundleMx {
    /**
     * 日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(ResourceBundleMx.class);

    /**
     * 区域相关资源。
     */
    private static final ResourceBundleMessageFormatter RBMF = new ResourceBundleMessageFormatter(ResourceBundleMx.class).useCurrentLocale();

    /**
     * 阻止实例化。
     */
    private ResourceBundleMx() {
    }

    /**
     * 加载资源失败后使用的 {@link ResourceBundle}，返回 <code>null</code>。
     */
    private static final ResourceBundle FALLBACK_BUNDLE = new ResourceBundle() {
        @Override
        protected Object handleGetObject(String key) {
            if (key == null) {
                throw new IllegalArgumentException();
            }
            return null;
        }

        @Override
        public Enumeration<String> getKeys() {
            return Collections.emptyEnumeration();
        }
    };

    /**
     * 使用系统默认区域和调用方类加载器加载资源。
     * 
     * @param baseName
     *     资源基础名称。
     * @return 资源。
     */
    public static ResourceBundle getBundle(String baseName) {
        try {
            return ResourceBundle.getBundle(baseName, XMLPropertyResourceBundle.Control.INSTANCE);
        } catch (MissingResourceException e) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(RBMF.get("加载资源 {} 失败"), baseName, e);
            }
        }
        try {
            return ResourceBundle.getBundle(baseName);
        } catch (MissingResourceException e) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(RBMF.get("加载资源 {} 失败"), baseName, e);
            }
        }
        LOG.warn(RBMF.get("加载资源 {} 失败"), baseName);
        return FALLBACK_BUNDLE;
    }

    /**
     * 使用调用方类加载器加载资源。
     * 
     * @param baseName
     *     资源基础名称。
     * @param locale
     *     区域。
     * @return 资源。
     */
    public static ResourceBundle getBundle(String baseName, Locale locale) {
        try {
            return ResourceBundle.getBundle(baseName, locale, XMLPropertyResourceBundle.Control.INSTANCE);
        } catch (MissingResourceException e) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(RBMF.get("加载资源 {} 失败"), baseName, e);
            }
        }
        try {
            return ResourceBundle.getBundle(baseName, locale);
        } catch (MissingResourceException e) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(RBMF.get("加载资源 {} 失败"), baseName, e);
            }
        }
        LOG.warn(RBMF.get("加载资源 {} 失败"), baseName);
        return FALLBACK_BUNDLE;
    }

    /**
     * 加载资源。
     * 
     * @param baseName
     *     资源基础名称。
     * @param locale
     *     区域。
     * @param loader
     *     类加载器。
     * @return 资源。
     */
    public static ResourceBundle getBundle(String baseName, Locale locale, ClassLoader loader) {
        try {
            return ResourceBundle.getBundle(baseName, locale, loader, XMLPropertyResourceBundle.Control.INSTANCE);
        } catch (MissingResourceException e) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(RBMF.get("加载资源 {} 失败"), baseName, e);
            }
        }
        try {
            return ResourceBundle.getBundle(baseName, locale, loader);
        } catch (MissingResourceException e) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(RBMF.get("加载资源 {} 失败"), baseName, e);
            }
        }
        LOG.warn(RBMF.get("加载资源 {} 失败"), baseName);
        return FALLBACK_BUNDLE;
    }

    /**
     * 从资源中获取对象，如果失败返回 <code>null</code>。
     * 
     * @param bundle
     *     资源。
     * @param key
     *     键值。
     * @return 对象。
     */
    public static <T> T getObject(ResourceBundle bundle, String key) {
        return getObject(bundle, key, null);
    }

    /**
     * 从资源中获取对象，如果失败返回默认对象。
     * 
     * @param bundle
     *     资源。
     * @param key
     *     键值。
     * @param defaultObject
     *     默认对象。
     * @return 对象。
     */
    public static <T> T getObject(ResourceBundle bundle, String key, T defaultObject) {
        if (bundle == null) {
            return defaultObject;
        }
        try {
            return (T) bundle.getObject(key);
        } catch (MissingResourceException e) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(RBMF.get("资源中未找到键值 {}"), key, e);
            }
        }
        return defaultObject;
    }
}
