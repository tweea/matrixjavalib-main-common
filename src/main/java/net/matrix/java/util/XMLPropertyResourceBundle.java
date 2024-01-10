/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * 读取 XML 格式属性内容的 {@link ResourceBundle} 实现。
 * 
 * @see Properties#loadFromXML(InputStream)
 */
public class XMLPropertyResourceBundle
    extends ResourceBundle {
    /**
     * 属性内容。
     */
    private final Map<String, Object> lookup;

    /**
     * 从输入流读取属性内容。
     * 
     * @param stream
     *     输入流。
     * @throws IOException
     *     出现输入输出错误。
     */
    public XMLPropertyResourceBundle(InputStream stream)
        throws IOException {
        Properties properties = new Properties();
        properties.loadFromXML(stream);
        this.lookup = new HashMap(properties);
    }

    @Override
    protected Object handleGetObject(String key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        return lookup.get(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        return new ResourceBundleEnumeration(lookup.keySet(), parent);
    }

    @Override
    protected Set<String> handleKeySet() {
        return lookup.keySet();
    }

    /**
     * ResourceBundle.Control 实现。
     */
    public static class Control
        extends ResourceBundle.Control {
        /**
         * 支持的资源格式。
         */
        private static final List<String> FORMATS = Collections.singletonList("xml");

        public static final Control INSTANCE = new Control();

        private Control() {
        }

        @Override
        public List<String> getFormats(String baseName) {
            if (baseName == null) {
                throw new IllegalArgumentException();
            }
            return FORMATS;
        }

        @Override
        public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
            throws IOException {
            if (baseName == null || locale == null || format == null || loader == null) {
                throw new IllegalArgumentException();
            }
            ResourceBundle bundle = null;
            if ("xml".equals(format)) {
                String bundleName = toBundleName(baseName, locale);
                String resourceName = toResourceName(bundleName, format);
                try (InputStream is = getResourceAsStream(loader, reload, resourceName)) {
                    if (is != null) {
                        InputStream bis = new BufferedInputStream(is);
                        bundle = new XMLPropertyResourceBundle(bis);
                    }
                }
            }
            return bundle;
        }

        private static InputStream getResourceAsStream(ClassLoader loader, boolean reload, String resourceName)
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
}
