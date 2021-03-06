/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class RelativeResourceRootRegisterTest {
    private static RelativeResourceRootRegister classRegister;

    @BeforeAll
    public static void beforeAll()
        throws IOException {
        classRegister = new RelativeResourceRootRegister();
        Resource test1 = new FileSystemResource("target/test1/");
        test1.getFile().mkdirs();
        Resource test2 = new FileSystemResource("target/test2/");
        test2.getFile().mkdirs();
        classRegister.registerRoot("test1", test1);
        classRegister.registerRoot("test2", test2);
    }

    @Test
    public void testRegisterRoot() {
        RelativeResourceRootRegister register = new RelativeResourceRootRegister();

        register.registerRoot("test", new ClassPathResource(""));
        assertThat(register.getRoot("test")).isNotNull();
    }

    @Test
    public void testGetResource()
        throws IOException {
        RelativeResourceRootRegister register = new RelativeResourceRootRegister();
        register.registerRoot("test", new ClassPathResource(""));
        RelativeResource relativeResource = new RelativeResource("test", "bar.xml");

        assertThat(register.getResource(relativeResource).exists()).isTrue();
    }

    @Test
    public void testGetResource1() {
        RelativeResourceRootRegister register = new RelativeResourceRootRegister();
        RelativeResource relativeResource = new RelativeResource("test", "bar.xml");

        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> register.getResource(relativeResource));
    }

    @Test
    public void testGetNewFile()
        throws IOException {
        RelativeResource src = new RelativeResource("test1", "getNewFile.txt");
        File srcFile = classRegister.getResource(src).getFile();
        srcFile.createNewFile();
        assertThat(srcFile).exists();

        srcFile = classRegister.getNewFile(src);
        assertThat(srcFile).doesNotExist();
    }

    @Test
    public void testMoveFile()
        throws IOException {
        RelativeResource src = new RelativeResource("test1", "move.txt");
        RelativeResource dest = new RelativeResource("test2", "move.txt");
        File srcFile = classRegister.getResource(src).getFile();
        File destFile = classRegister.getResource(dest).getFile();
        srcFile.createNewFile();

        classRegister.moveFile(src, dest);
        assertThat(srcFile).doesNotExist();
        assertThat(destFile).exists();
        destFile.delete();
    }

    @Test
    public void testMoveFile_override()
        throws IOException {
        RelativeResource src = new RelativeResource("test1", "moveOverride.txt");
        RelativeResource dest = new RelativeResource("test2", "moveOverride.txt");
        File srcFile = classRegister.getResource(src).getFile();
        File destFile = classRegister.getResource(dest).getFile();
        srcFile.createNewFile();
        destFile.createNewFile();

        classRegister.moveFile(src, dest);
        assertThat(srcFile).doesNotExist();
        assertThat(destFile).exists();
        destFile.delete();
    }

    @Test
    public void testCopyFile()
        throws IOException {
        RelativeResource src = new RelativeResource("test1", "copy.txt");
        RelativeResource dest = new RelativeResource("test2", "copy.txt");
        File srcFile = classRegister.getResource(src).getFile();
        File destFile = classRegister.getResource(dest).getFile();
        String test = "Test!\nThis is a test!!\n测试！";
        FileUtils.writeStringToFile(srcFile, test, StandardCharsets.UTF_8);

        classRegister.copyFile(src, dest);
        assertThat(destFile).exists();
        assertThat(destFile).hasContent(test);
        srcFile.delete();
        destFile.delete();
    }
}
