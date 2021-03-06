/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.util;

import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * SLF4J 工具。
 */
public final class SLF4Js {
    /**
     * 阻止实例化。
     */
    private SLF4Js() {
    }

    /**
     * 将 {@link java.util.logging} 的日志代理到 SLF4J。
     */
    public static void bridgeJUL() {
        if (SLF4JBridgeHandler.isInstalled()) {
            return;
        }

        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
}
