/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.text;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * 加载 XML 资源的控制对象。
     */
    private static final ResourceBundle.Control XML_BUNDLE_CONTROL = new XMLResourceBundleControl();

    /**
     * 支持读取 XML 资源。
     */
    private static class XMLResourceBundleControl
        extends ResourceBundle.Control {
        /**
         * 支持资源格式。
         */
        private static final List<String> FORMATS = Arrays.asList("xml");

        public XMLResourceBundleControl() {
            // 提升可见性，优化访问速度
        }

        @Override
        public List<String> getFormats(final String baseName) {
            if (baseName == null) {
                throw new IllegalArgumentException("baseName");
            }
            return FORMATS;
        }

        @Override
        public ResourceBundle newBundle(final String baseName, final Locale locale, final String format, final ClassLoader loader, final boolean reload)
            throws IllegalAccessException, InstantiationException, IOException {
            if (baseName == null || locale == null || format == null || loader == null) {
                throw new IllegalArgumentException("参数不能为空");
            }
            ResourceBundle bundle = null;
            if ("xml".equals(format)) {
                String bundleName = toBundleName(baseName, locale);
                String resourceName = toResourceName(bundleName, format);
                try (InputStream is = getResourceAsStream(loader, reload, resourceName)) {
                    if (is != null) {
                        BufferedInputStream bis = new BufferedInputStream(is);
                        bundle = new XMLResourceBundle(bis);
                    }
                }
            }
            return bundle;
        }

        private static InputStream getResourceAsStream(final ClassLoader loader, final boolean reload, final String resourceName)
            throws IOException {
            if (reload) {
                URL url = loader.getResource(resourceName);
                if (url == null) {
                    return null;
                }

                URLConnection connection = url.openConnection();
                if (connection == null) {
                    return null;
                }

                // Disable caches to get fresh data for reloading.
                connection.setUseCaches(false);
                return connection.getInputStream();
            } else {
                return loader.getResourceAsStream(resourceName);
            }
        }
    }

    /**
     * 支持读取 XML 资源。
     */
    private static final class XMLResourceBundle
        extends ResourceBundle {
        /**
         * 资源中的属性。
         */
        private final Properties props;

        /**
         * 从输入流读取资源。
         * 
         * @param stream
         *     输入流
         * @throws IOException
         *     读取失败
         */
        public XMLResourceBundle(final InputStream stream)
            throws IOException {
            this.props = new Properties();
            this.props.loadFromXML(stream);
        }

        @Override
        protected Object handleGetObject(final String key) {
            return props.getProperty(key);
        }

        @Override
        public Enumeration<String> getKeys() {
            return Collections.enumeration(props.stringPropertyNames());
        }
    }

    /**
     * 使用与当前线程关联的区域和默认类加载器加载资源。
     * 
     * @param baseName
     *     资源名
     * @return 资源
     */
    public static ResourceBundle getBundle(final String baseName) {
        return getBundle(baseName, Locales.current());
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
            return ResourceBundle.getBundle(baseName, locale, XML_BUNDLE_CONTROL);
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
            return ResourceBundle.getBundle(baseName, locale, loader, XML_BUNDLE_CONTROL);
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
