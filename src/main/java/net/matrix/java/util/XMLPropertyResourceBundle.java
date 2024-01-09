/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
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
            throw new NullPointerException();
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
}
