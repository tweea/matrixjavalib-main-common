/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.org.slf4j;

import org.junit.jupiter.api.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;

import static org.assertj.core.api.Assertions.assertThat;

public class SLF4JMxTest {
    @Test
    public void testBridgeJUL() {
        assertThat(SLF4JBridgeHandler.isInstalled()).isFalse();
        SLF4JMx.bridgeJUL();
        assertThat(SLF4JBridgeHandler.isInstalled()).isTrue();
    }
}
