/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.security;

import java.security.Provider;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.Test;

public class CryptosTest {
    @Test
    public void testAbc() {
        Provider PROVIDER = new BouncyCastleProvider();
        Set<String> names = new TreeSet<>();
        for (Provider.Service s : PROVIDER.getServices()) {
            if ("Mac".equals(s.getType())) {
                names.add(s.getAlgorithm());
            }
        }
        System.out.println(StringUtils.join(names, '\n'));
    }
}
