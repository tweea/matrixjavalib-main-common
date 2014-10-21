/*
 * $Id: RelativeResource.java 881 2014-01-22 06:01:36Z tweea@263.net $
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.io;

import javax.annotation.concurrent.Immutable;

/**
 * 相对定位的资源。
 */
@Immutable
public class RelativeResource {
	/**
	 * 根路径名。
	 */
	private final String root;

	/**
	 * 相对路径。
	 */
	private final String path;

	/**
	 * 基于指定根路径名构造资源。
	 * 
	 * @param root
	 *            根路径名
	 * @param path
	 *            相对路径
	 */
	public RelativeResource(final String root, final String path) {
		this.root = root;
		this.path = path;
	}

	/**
	 * 基于已有资源构造。
	 * 
	 * @param parent
	 *            关联资源
	 * @param path
	 *            相对路径
	 */
	public RelativeResource(final RelativeResource parent, final String path) {
		this.root = parent.root;
		this.path = parent.path + '/' + path;
	}

	/**
	 * 获取根路径名。
	 * 
	 * @return 根路径名
	 */
	public String getRoot() {
		return root;
	}

	/**
	 * 获取相对路径。
	 * 
	 * @return 相对路径
	 */
	public String getPath() {
		return path;
	}

	@Override
	public String toString() {
		return "RelativeResource[" + root + "/" + path + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((root == null) ? 0 : root.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		RelativeResource other = (RelativeResource) obj;
		if (path == null) {
			if (other.path != null) {
				return false;
			}
		} else if (!path.equals(other.path)) {
			return false;
		}
		if (root == null) {
			if (other.root != null) {
				return false;
			}
		} else if (!root.equals(other.root)) {
			return false;
		}
		return true;
	}
}
