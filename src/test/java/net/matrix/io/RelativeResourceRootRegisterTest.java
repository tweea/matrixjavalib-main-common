/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.io;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class RelativeResourceRootRegisterTest {
	private static RelativeResourceRootRegister classRegister;

	@BeforeClass
	public static void setUp()
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
	public void registerRoot() {
		RelativeResourceRootRegister register = new RelativeResourceRootRegister();
		register.registerRoot("test", new ClassPathResource(""));
		Assertions.assertThat(register.getRoot("test")).isNotNull();
	}

	@Test
	public void getResource()
		throws IOException {
		RelativeResourceRootRegister register = new RelativeResourceRootRegister();
		register.registerRoot("test", new ClassPathResource(""));
		Assertions.assertThat(register.getResource(new RelativeResource("test", "bar.xml")).getFile()).exists();
	}

	@Test(expected = IllegalStateException.class)
	public void getResource1()
		throws IOException {
		RelativeResourceRootRegister register = new RelativeResourceRootRegister();
		register.getResource(new RelativeResource("test", "bar.xml")).getFile();
	}

	@Test
	public void getNewFile()
		throws IOException {
		RelativeResource src = new RelativeResource("test1", "getNewFile.txt");
		File srcFile = classRegister.getResource(src).getFile();
		srcFile.createNewFile();
		Assertions.assertThat(srcFile).exists();
		srcFile = classRegister.getNewFile(src);
		Assertions.assertThat(srcFile).doesNotExist();
	}

	@Test
	public void moveFile()
		throws IOException {
		RelativeResource src = new RelativeResource("test1", "move.txt");
		RelativeResource dest = new RelativeResource("test2", "move.txt");
		File srcFile = classRegister.getResource(src).getFile();
		File destFile = classRegister.getResource(dest).getFile();
		srcFile.createNewFile();
		classRegister.moveFile(src, dest);
		Assertions.assertThat(srcFile).doesNotExist();
		Assertions.assertThat(destFile).exists();
		destFile.delete();
	}

	@Test
	public void moveFileOverride()
		throws IOException {
		RelativeResource src = new RelativeResource("test1", "moveOverride.txt");
		RelativeResource dest = new RelativeResource("test2", "moveOverride.txt");
		File srcFile = classRegister.getResource(src).getFile();
		File destFile = classRegister.getResource(dest).getFile();
		srcFile.createNewFile();
		destFile.createNewFile();
		classRegister.moveFile(src, dest);
		Assertions.assertThat(srcFile).doesNotExist();
		Assertions.assertThat(destFile).exists();
		destFile.delete();
	}

	@Test
	public void copyFile()
		throws IOException {
		RelativeResource src = new RelativeResource("test1", "copy.txt");
		RelativeResource dest = new RelativeResource("test2", "copy.txt");
		File srcFile = classRegister.getResource(src).getFile();
		File destFile = classRegister.getResource(dest).getFile();

		String test = "Test!\nThis is a test!!\n测试！";
		FileUtils.writeStringToFile(srcFile, test);
		classRegister.copyFile(src, dest);
		Assertions.assertThat(destFile).exists();
		Assertions.assertThat(destFile).hasContent(test);
		srcFile.delete();
		destFile.delete();
	}
}
