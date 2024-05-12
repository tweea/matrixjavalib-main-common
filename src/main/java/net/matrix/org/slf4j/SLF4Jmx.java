/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.org.slf4j;

import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * SLF4J 工具。
 */
public final class SLF4Jmx {
    /**
     * 阻止实例化。
     */
    private SLF4Jmx() {
    }

    /**
     * 桥接所有 {@link java.util.logging} 的日志到 SLF4J。
     */
    public static void bridgeJUL() {
        if (SLF4JBridgeHandler.isInstalled()) {
            return;
        }

        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
}
