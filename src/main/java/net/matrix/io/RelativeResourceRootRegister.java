/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

/**
 * 相对定位资源的根注册，所有相对定位资源都需要从这里获取根路径的绝对位置。
 * 操作资源时，需要先在此注册根路径名。
 */
public class RelativeResourceRootRegister {
	/**
	 * 日志记录器。
	 */
	private static final Logger LOG = LoggerFactory.getLogger(RelativeResourceRootRegister.class);

	/**
	 * 保存所有注册的根路径的绝对位置。
	 */
	private final Map<String, Resource> roots;

	/**
	 * 构造空的根注册。
	 */
	public RelativeResourceRootRegister() {
		roots = new HashMap<>();
	}

	/**
	 * 将某资源注册为资源根路径。
	 * 
	 * @param name
	 *            根路径名
	 * @param root
	 *            根路径资源
	 */
	public void registerRoot(final String name, final Resource root) {
		roots.put(name, root);
	}

	/**
	 * 获取根路径代表的资源。
	 * 
	 * @param name
	 *            根路径名
	 * @return 根路径资源
	 */
	public Resource getRoot(final String name) {
		return roots.get(name);
	}

	/**
	 * 定位相对资源。
	 * 
	 * @param relativeResource
	 *            需要定位的资源
	 * @return 已定位的绝对路径资源
	 * @throws IOException
	 *             定位失败
	 * @throws IllegalStateException
	 *             根路径没有注册
	 */
	public Resource getResource(final RelativeResource relativeResource)
		throws IOException {
		Resource root = getRoot(relativeResource.getRoot());
		if (root == null) {
			throw new IllegalStateException("根路径 " + relativeResource.getRoot() + " 未注册");
		}
		return root.createRelative(relativeResource.getPath());
	}

	/**
	 * 根据文件路径名称和文件名称得到文件实际对应的 <code>File</code> 对象。如果文件存在先删除文件。
	 * 
	 * @param relativeFile
	 *            抽象定位文件
	 * @return <code>File</code> 对象
	 * @throws IOException
	 *             文件操作异常
	 */
	public File getNewFile(final RelativeResource relativeFile)
		throws IOException {
		File file = getResource(relativeFile).getFile();
		deleteOldFile(file);
		return file;
	}

	/**
	 * 移动文件。
	 * 
	 * @param src
	 *            源抽象定位文件
	 * @param dest
	 *            目标抽象定位文件
	 * @throws IOException
	 *             文件操作异常
	 */
	public void moveFile(final RelativeResource src, final RelativeResource dest)
		throws IOException {
		if (src.equals(dest)) {
			return;
		}
		LOG.debug("搬移文件从 {} 到 {}", src, dest);
		File srcFile = getResource(src).getFile();
		if (!srcFile.exists()) {
			throw new FileNotFoundException(srcFile.getAbsolutePath());
		}
		File destFile = getResource(dest).getFile();
		deleteOldFile(destFile);
		FileUtils.moveFile(srcFile, destFile);
	}

	/**
	 * 复制文件。
	 * 
	 * @param src
	 *            源抽象定位文件
	 * @param dest
	 *            目标抽象定位文件
	 * @throws IOException
	 *             文件操作异常
	 */
	public void copyFile(final RelativeResource src, final RelativeResource dest)
		throws IOException {
		if (src.equals(dest)) {
			return;
		}
		LOG.debug("复制文件从 {} 到 {}", src, dest);
		File srcFile = getResource(src).getFile();
		if (!srcFile.exists()) {
			throw new FileNotFoundException(srcFile.getAbsolutePath());
		}
		File destFile = getResource(dest).getFile();
		deleteOldFile(destFile);
		FileUtils.copyFile(srcFile, destFile);
	}

	/**
	 * 删除旧文件。
	 * 
	 * @param file
	 *            旧文件
	 */
	private void deleteOldFile(final File file) {
		if (!file.exists()) {
			return;
		}

		LOG.debug("删除旧文件：{}", file);
		boolean success = file.delete();
		if (!success) {
			LOG.warn("删除文件失败：{}", file);
		}
	}
}
