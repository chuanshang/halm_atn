package org.hzero.halm.atn.infra.constant;

import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Map;

/**
 * 字段控制接口
 *
 * @author like.zhang@hand-china.com 2019/03/28 10:51
 */
public interface AatnFieldController<T, K> {

    /**
     * 字段验证
     *
     * @param fieldRules    规则
     * @param list      数据
     * @param messageSource MessageSource
     * @param errorMessage  当前已有错误
     * @param fieldEnum     当前枚举值
     * @return 报错信息
     * @throws IllegalAccessException 反射错误
     */
    String validControlFieldsWithRules(Map<String, String> fieldRules, List<T> list, MessageSource messageSource, String errorMessage, K fieldEnum) throws IllegalAccessException;

    /**
     * 获取中文字段名
     *
     * @return 字段名
     */
    String getZhFieldName();

    /**
     * 获取枚举名
     *
     * @return 枚举名
     */
    String getEnumName();
}
