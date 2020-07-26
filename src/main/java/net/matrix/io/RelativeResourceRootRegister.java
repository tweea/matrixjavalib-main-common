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
 * 管理相对定位资源的根资源，提供将相对定位资源转换为基于对应根资源的实际资源的方法。
 * 操作相对定位资源前，需要先注册对应根资源。
 */
public class RelativeResourceRootRegister {
    /**
     * 日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(RelativeResourceRootRegister.class);

    /**
     * 所有注册的根资源。
     */
    private final Map<String, Resource> roots;

    /**
     * 构造未注册根资源的实例。
     */
    public RelativeResourceRootRegister() {
        this.roots = new HashMap<>();
    }

    /**
     * 注册根资源到根路径名。
     * 
     * @param name
     *     根路径名
     * @param root
     *     根资源
     */
    public void registerRoot(final String name, final Resource root) {
        roots.put(name, root);
    }

    /**
     * 获取注册的根资源。
     * 
     * @param name
     *     根路径名
     * @return 根资源
     */
    public Resource getRoot(final String name) {
        return roots.get(name);
    }

    /**
     * 获取相对定位资源对应的实际资源。
     * 
     * @param relativeResource
     *     相对定位资源
     * @return 实际资源
     * @throws IOException
     *     获取失败
     * @throws IllegalStateException
     *     根路径未注册
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
     * 获取相对定位文件对应的 {@link File} 对象，并删除已存在文件。
     * 
     * @param relativeFile
     *     相对定位文件
     * @return {@link File} 对象
     * @throws IOException
     *     获取失败
     */
    public File getNewFile(final RelativeResource relativeFile)
        throws IOException {
        File file = getResource(relativeFile).getFile();
        deleteOldFile(file);
        return file;
    }

    /**
     * 移动相对定位文件。
     * 
     * @param src
     *     源相对定位文件
     * @param dest
     *     目标相对定位文件
     * @throws IOException
     *     移动失败
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
        File destFile = getNewFile(dest);
        FileUtils.moveFile(srcFile, destFile);
    }

    /**
     * 复制相对定位文件。
     * 
     * @param src
     *     源相对定位文件
     * @param dest
     *     目标相对定位文件
     * @throws IOException
     *     复制失败
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
        File destFile = getNewFile(dest);
        FileUtils.copyFile(srcFile, destFile);
    }

    /**
     * 删除旧文件。
     * 
     * @param file
     *     旧文件
     */
    private static void deleteOldFile(final File file) {
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
