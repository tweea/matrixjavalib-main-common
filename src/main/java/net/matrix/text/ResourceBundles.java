/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.text;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.matrix.java.util.LocaleMx;
import net.matrix.java.util.XMLPropertyResourceBundle;

/**
 * 多语言资源工具。
 */
public final class ResourceBundles {
    /**
     * 日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(ResourceBundles.class);

    /**
     * 阻止实例化。
     */
    private ResourceBundles() {
    }

    /**
     * 当读取资源失败后使用的 {@link ResourceBundle}，直接返回键值。
     */
    private static final ResourceBundle FALLBACK_BUNDLE = new ResourceBundle() {
        @Override
        protected Object handleGetObject(final String key) {
            return key;
        }

        @Override
        public Enumeration<String> getKeys() {
            return Collections.emptyEnumeration();
        }
    };

    /**
     * 使用与当前线程关联的区域和默认类加载器加载资源。
     * 
     * @param baseName
     *     资源名
     * @return 资源
     */
    public static ResourceBundle getBundle(final String baseName) {
        return getBundle(baseName, LocaleMx.current());
    }

    /**
     * 使用默认类加载器加载资源。
     * 
     * @param baseName
     *     资源名
     * @param locale
     *     区域
     * @return 资源
     */
    public static ResourceBundle getBundle(final String baseName, final Locale locale) {
        try {
            return ResourceBundle.getBundle(baseName, locale, XMLPropertyResourceBundle.Control.INSTANCE);
        } catch (MissingResourceException e) {
            LOG.warn("{} 资源加载失败", baseName, e);
            return FALLBACK_BUNDLE;
        }
    }

    /**
     * 加载资源。
     * 
     * @param baseName
     *     资源名
     * @param locale
     *     区域
     * @param loader
     *     类加载器
     * @return 资源
     */
    public static ResourceBundle getBundle(final String baseName, final Locale locale, final ClassLoader loader) {
        try {
            return ResourceBundle.getBundle(baseName, locale, loader, XMLPropertyResourceBundle.Control.INSTANCE);
        } catch (MissingResourceException e) {
            LOG.warn("{} 资源加载失败", baseName, e);
            return FALLBACK_BUNDLE;
        }
    }

    /**
     * 获取多语言字符串，如果失败直接返回键值。
     * 
     * @param bundle
     *     资源
     * @param key
     *     键值
     * @return 字符串
     */
    public static String getProperty(final ResourceBundle bundle, final String key) {
        if (bundle == null) {
            return key;
        }
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            LOG.warn("找不到名为 {} 的资源项", key, e);
            return key;
        }
    }
}
