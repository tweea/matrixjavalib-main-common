/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

import net.matrix.java.util.CollectionMx;

/**
 * 树型结构工具。
 */
@ThreadSafe
public final class TreeMx {
    /**
     * 阻止实例化。
     */
    private TreeMx() {
    }

    /**
     * 构造树型结构。
     *
     * @param datas
     *     数据列表。
     * @param idFunction
     *     数据的唯一标识映射函数。
     * @param parentIdFunction
     *     上级数据的唯一标识映射函数。
     * @param childFunction
     *     下级数据列表映射函数。
     * @return 树型结构的数据列表。
     */
    @Nonnull
    public static <ID, DATA> List<DATA> buildTree(@Nonnull List<DATA> datas, @Nonnull Function<? super DATA, ? extends ID> idFunction,
        @Nonnull Function<? super DATA, ? extends ID> parentIdFunction, @Nonnull Function<? super DATA, ? extends List<DATA>> childFunction) {
        Map<ID, DATA> dataMap = CollectionMx.buildMap(datas, idFunction);

        List<DATA> treeList = new ArrayList<>();
        for (DATA data : datas) {
            ID parentId = parentIdFunction.apply(data);
            if (parentId == null) {
                treeList.add(data);
            } else {
                DATA parentData = dataMap.get(parentId);
                if (parentData == null) {
                    treeList.add(data);
                } else {
                    childFunction.apply(parentData).add(data);
                }
            }
        }
        return treeList;
    }

    /**
     * 构造树型结构。
     *
     * @param datas
     *     数据列表。
     * @return 树型结构的数据列表。
     */
    @Nonnull
    public static <DATA extends TreeData<?, DATA>> List<DATA> buildTree(@Nonnull List<DATA> datas) {
        return buildTree(datas, DATA::getId, DATA::getParentId, DATA::getChildren);
    }

    /**
     * 从数据源生成树型结构。
     *
     * @param source
     *     数据源。
     * @param childFunction
     *     下级数据列表映射函数。
     * @return 生成的树型结构。
     */
    @Nonnull
    public static <DATA> List<DATA> generateTree(@Nonnull TreeSource<DATA> source, @Nonnull Function<? super DATA, ? extends List<DATA>> childFunction) {
        List<DATA> datas = source.getRoots();
        for (DATA data : datas) {
            generateChildNode(source, childFunction, data);
        }
        return datas;
    }

    /**
     * 从数据源生成树型结构的下级节点。
     *
     * @param source
     *     数据源。
     * @param childFunction
     *     下级数据列表映射函数。
     * @param treeNode
     *     节点。
     */
    private static <DATA> void generateChildNode(TreeSource<DATA> source, Function<? super DATA, ? extends List<DATA>> childFunction, DATA data) {
        List<DATA> childDatas = source.getChildren(data);
        childFunction.apply(data).addAll(childDatas);
        for (DATA childData : childDatas) {
            generateChildNode(source, childFunction, childData);
        }
    }

    /**
     * 从数据源生成树型结构。
     *
     * @param source
     *     数据源。
     * @return 生成的树型结构。
     */
    @Nonnull
    public static <DATA extends TreeData<?, DATA>> List<DATA> generateTree(@Nonnull TreeSource<DATA> source) {
        return generateTree(source, DATA::getChildren);
    }
}
