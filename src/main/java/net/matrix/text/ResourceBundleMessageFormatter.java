/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.text;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import net.matrix.java.text.MessageFormatMx;
import net.matrix.java.util.LocaleMx;
import net.matrix.java.util.ResourceBundleMx;

/**
 * 使用区域相关资源的消息格式化工具。
 */
@ThreadSafe
public class ResourceBundleMessageFormatter {
    /**
     * 资源基础名称。
     */
    @Nonnull
    private final String baseName;

    /**
     * 区域获取方式。
     */
    @Nonnull
    private Supplier<Locale> localeSupplier;

    /**
     * 构造器，使用系统默认区域。
     *
     * @param baseName
     *     资源基础名称。
     */
    public ResourceBundleMessageFormatter(@Nonnull String baseName) {
        this.baseName = baseName;
        this.localeSupplier = () -> Locale.getDefault(Locale.Category.FORMAT);
    }

    /**
     * 构造器，使用类名作为资源基础名称，使用系统默认区域。
     *
     * @param clazz
     *     类。
     */
    public ResourceBundleMessageFormatter(@Nonnull Class clazz) {
        this(clazz.getName());
    }

    /**
     * 使用特定区域。
     *
     * @param locale
     *     区域。
     * @return 自身。
     */
    @Nonnull
    public ResourceBundleMessageFormatter useLocale(@Nonnull Locale locale) {
        this.localeSupplier = () -> locale;
        return this;
    }

    /**
     * 使用线程相关区域。
     *
     * @return 自身。
     */
    @Nonnull
    public ResourceBundleMessageFormatter useCurrentLocale() {
        this.localeSupplier = () -> LocaleMx.current(Locale.Category.FORMAT);
        return this;
    }

    /**
     * 资源基础名称。
     */
    @Nonnull
    public String getBaseName() {
        return baseName;
    }

    /**
     * 区域。
     */
    @Nonnull
    public Locale getLocale() {
        return localeSupplier.get();
    }

    /**
     * 区域相关资源，使用调用方类加载器。
     */
    @Nonnull
    public ResourceBundle getResourceBundle() {
        return ResourceBundleMx.getBundle(baseName, localeSupplier.get());
    }

    /**
     * 区域相关资源。
     *
     * @param loader
     *     类加载器。
     */
    @Nonnull
    public ResourceBundle getResourceBundle(@Nonnull ClassLoader loader) {
        return ResourceBundleMx.getBundle(baseName, localeSupplier.get(), loader);
    }

    /**
     * 获取消息，从区域相关资源加载，使用调用方类加载器。
     *
     * @param key
     *     在区域相关资源中，消息对应的键值。
     * @return 消息。
     */
    @Nonnull
    public String get(@Nonnull String key) {
        return get(getResourceBundle(), key);
    }

    /**
     * 获取消息，从区域相关资源加载。
     *
     * @param loader
     *     类加载器。
     * @param key
     *     在区域相关资源中，消息对应的键值。
     * @return 消息。
     */
    @Nonnull
    public String get(@Nonnull ClassLoader loader, @Nonnull String key) {
        return get(getResourceBundle(loader), key);
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
    @Nonnull
    public String format(@Nonnull String key, @Nonnull Object... arguments) {
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
    @Nonnull
    public String format(@Nonnull ClassLoader loader, @Nonnull String key, @Nonnull Object... arguments) {
        return format(getResourceBundle(loader), key, arguments);
    }

    /**
     * 获取消息，从区域相关资源加载。
     *
     * @param bundle
     *     区域相关资源。
     * @param key
     *     在区域相关资源中，消息对应的键值。
     * @return 消息。
     */
    @Nonnull
    public String get(@Nullable ResourceBundle bundle, @Nonnull String key) {
        return ResourceBundleMx.getObject(bundle, key, key);
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
    @Nonnull
    public String format(@Nullable ResourceBundle bundle, @Nonnull String key, @Nonnull Object... arguments) {
        String pattern = ResourceBundleMx.getObject(bundle, key, key);
        Locale locale = Optional.ofNullable(bundle).map(ResourceBundle::getLocale).orElseGet(localeSupplier);
        return MessageFormatMx.format(pattern, locale, arguments);
    }
}
