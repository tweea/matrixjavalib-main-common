/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.text;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Supplier;

import net.matrix.java.text.MessageFormatMx;
import net.matrix.java.util.LocaleMx;
import net.matrix.java.util.ResourceBundleMx;

/**
 * 使用区域相关资源的消息格式化工具。
 */
public class ResourceBundleMessageFormatter {
    /**
     * 资源基础名称。
     */
    private String baseName;

    /**
     * 区域获取方式。
     */
    private Supplier<Locale> localeSupplier;

    /**
     * 构造器，使用系统默认区域。
     * 
     * @param baseName
     *     资源基础名称。
     */
    public ResourceBundleMessageFormatter(String baseName) {
        this.baseName = baseName;
        this.localeSupplier = () -> Locale.getDefault(Locale.Category.FORMAT);
    }

    /**
     * 构造器，使用类名作为资源基础名称，使用系统默认区域。
     * 
     * @param clazz
     *     类。
     */
    public ResourceBundleMessageFormatter(Class clazz) {
        this(clazz.getName());
    }

    /**
     * 使用特定区域。
     * 
     * @param locale
     *     区域。
     * @return 自身。
     */
    public ResourceBundleMessageFormatter useLocale(Locale locale) {
        this.localeSupplier = () -> locale;
        return this;
    }

    /**
     * 使用线程相关区域。
     * 
     * @return 自身。
     */
    public ResourceBundleMessageFormatter useCurrentLocale() {
        this.localeSupplier = () -> LocaleMx.current(Locale.Category.FORMAT);
        return this;
    }

    /**
     * 资源基础名称。
     */
    public String getBaseName() {
        return baseName;
    }

    /**
     * 区域。
     */
    public Locale getLocale() {
        return localeSupplier.get();
    }

    /**
     * 区域相关资源，使用调用方类加载器。
     */
    public ResourceBundle getResourceBundle() {
        return ResourceBundleMx.getBundle(baseName, localeSupplier.get());
    }

    /**
     * 区域相关资源。
     * 
     * @param loader
     *     类加载器。
     */
    public ResourceBundle getResourceBundle(ClassLoader loader) {
        return ResourceBundleMx.getBundle(baseName, localeSupplier.get(), loader);
    }

    /**
     * 格式化消息，从区域相关资源加载消息格式，使用调用方类加载器。
     * 
     * @param key
     *     在区域相关资源中，消息格式对应的键值。
     * @param arguments
     *     消息参数。
     * @return 格式化的消息。
     */
    public String format(String key, Object... arguments) {
        return format(getResourceBundle(), key, arguments);
    }

    /**
     * 格式化消息，从区域相关资源加载消息格式。
     * 
     * @param loader
     *     类加载器。
     * @param key
     *     在区域相关资源中，消息格式对应的键值。
     * @param arguments
     *     消息参数。
     * @return 格式化的消息。
     */
    public String format(ClassLoader loader, String key, Object... arguments) {
        return format(getResourceBundle(loader), key, arguments);
    }

    /**
     * 格式化消息，从区域相关资源加载消息格式。
     * 
     * @param bundle
     *     区域相关资源。
     * @param key
     *     在区域相关资源中，消息格式对应的键值。
     * @param arguments
     *     消息参数。
     * @return 格式化的消息。
     */
    public static String format(ResourceBundle bundle, String key, Object... arguments) {
        String pattern = ResourceBundleMx.getObject(bundle, key);
        if (pattern == null) {
            return MessageFormatMx.formatFallback(key, arguments);
        }
        return MessageFormatMx.format(pattern, bundle.getLocale(), arguments);
    }
}
