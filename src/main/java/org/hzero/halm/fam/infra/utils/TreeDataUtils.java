package org.hzero.halm.fam.infra.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.hzero.core.algorithm.tree.Child;
import org.springframework.beans.BeanUtils;

import java.util.List;

import io.choerodon.core.exception.CommonException;

/**
 * description
 *
 * @author like.zhang@hand-china.com 2019/03/06 19:30
 */
public class TreeDataUtils<T extends Child<T>> {
    /**
     * 数据转换
     *
     * @param treeData 树形结构数据
     * @param clazz    结果类型
     */
    public <K> void treeDataToList(List<T> treeData, Class<K> clazz, List<K> targetList) {
        if (CollectionUtils.isNotEmpty(treeData)) {
            for (T t : treeData) {
                K k;
                try {
                    k = clazz.newInstance();
                } catch (Exception e) {
                    throw new CommonException(e);
                }
                BeanUtils.copyProperties(t, k);
                targetList.add(k);
                List<T> childList = t.getChildren();
                if (CollectionUtils.isNotEmpty(childList)) {
                    treeDataToList(childList, clazz, targetList);
                }
            }
        }
    }
}
