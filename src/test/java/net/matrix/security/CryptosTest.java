/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.security;

import java.security.GeneralSecurityException;
import java.security.Provider;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CryptosTest {
    @Test
    public void testHMAC()
        throws GeneralSecurityException {
        String input = "foo message";

        byte[] key = Cryptos.generateHmacSha1Key();
        assertThat(key).hasSize(20);

        byte[] macResult = Cryptos.hmacSha1(input.getBytes(), key);
        assertThat(Cryptos.isMacValid(macResult, input.getBytes(), key)).isTrue();
    }

    @Test
    public void testAbc() {
        Provider PROVIDER = new BouncyCastleProvider();
        Set<String> names = new TreeSet<>();
        for (Provider.Service s : PROVIDER.getServices()) {
            if ("AlgorithmParameters".equals(s.getType())) {
                names.add(s.getAlgorithm());
            }
        }
        System.out.println(StringUtils.join(names, '\n'));
    }
}
