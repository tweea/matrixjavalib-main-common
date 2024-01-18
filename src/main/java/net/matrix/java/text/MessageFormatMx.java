/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.text;

import java.text.MessageFormat;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息格式化工具。
 */
public final class MessageFormatMx {
    /**
     * 日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(MessageFormatMx.class);

    /**
     * 阻止实例化。
     */
    private MessageFormatMx() {
    }

    /**
     * 格式化消息。
     * 
     * @param pattern
     *     消息格式。
     * @param arguments
     *     消息参数。
     * @return 格式化的消息。
     */
    public static String format(String pattern, Object... arguments) {
        try {
            MessageFormat format = new MessageFormat(pattern);
            return format.format(arguments);
        } catch (IllegalArgumentException e) {
            LOG.warn("", e);
            return formatFallback(pattern, arguments);
        }
    }

    /**
     * 格式化消息。
     * 
     * @param pattern
     *     消息格式。
     * @param locale
     *     区域。
     * @param arguments
     *     消息参数。
     * @return 格式化的消息。
     */
    public static String format(String pattern, Locale locale, Object... arguments) {
        try {
            MessageFormat format = new MessageFormat(pattern, locale);
            return format.format(arguments);
        } catch (IllegalArgumentException e) {
            LOG.warn("", e);
            return formatFallback(pattern, arguments);
        }
    }

    /**
     * 格式化失败后，使用一般形式格式化消息。
     * 
     * @param pattern
     *     消息格式。
     * @param arguments
     *     消息参数。
     * @return 格式化的消息。
     */
    public static String formatFallback(String pattern, Object... arguments) {
        StringBuilder sb = new StringBuilder(pattern);
        for (Object argument : arguments) {
            sb.append(", ");
            sb.append(argument);
        }
        return sb.toString();
    }
}
