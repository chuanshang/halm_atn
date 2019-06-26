package org.hzero.halm.atn.infra.util;

import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.DetailsHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hzero.boot.platform.lov.adapter.LovAdapter;
import org.hzero.boot.platform.lov.dto.LovValueDTO;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.helper.LanguageHelper;
import org.hzero.halm.atn.api.dto.EmployeeDTO;
import org.hzero.halm.atn.infra.constant.AatnConstans;
import org.hzero.halm.atn.infra.constant.AatnFieldController;
import org.hzero.halm.atn.infra.mapper.OrderDynamicColumnMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 事务处理通用方法类
 *
 * @author like.zhang@hand-china.com 2019/03/26 10:57
 */
@Component
public class CommonProcessUtils {

    @Autowired
    private LovAdapter lovAdapter;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private OrderDynamicColumnMapper orderDynamicColumnMapper;


    /**
     * 校验单据执行时间的合理性
     *
     * @param startDate 计划开始执行日期
     * @param endDate   计划完成日期
     */
    public void validDate(Date startDate, Date endDate) {

        Date current = new Date();

        Date start = (Date) ObjectUtils.defaultIfNull(startDate, current);
        Date end = (Date) ObjectUtils.defaultIfNull(endDate, current);

        // 开始执行时间要大于等于当前日期
        if (DateUtils.truncatedCompareTo(start, current, Calendar.DATE) < 0 || DateUtils.truncatedCompareTo(end, current, Calendar.DATE) < 0) {
            throw new CommonException(BaseConstants.ErrorCode.DATA_INVALID);
        }

        // 完成日期大于等于开始日期
        if (DateUtils.truncatedCompareTo(start, end, Calendar.DATE) < 0) {
            throw new CommonException(BaseConstants.ErrorCode.DATA_INVALID);
        }
    }

    /**
     * 校验插入数据与数据库中数据 是否存在重复资产（同一单据资产不重复）
     *
     * @param insertAssetIdSet   插入的行中资产ID集合
     * @param entitiesAssetIdSet 数据库中的行的资产ID集合
     * @param insertLineSize     插入的行的条数
     */
    public static void validAsset(Set<Long> insertAssetIdSet, Set<Long> entitiesAssetIdSet, Integer insertLineSize) {
        if (insertAssetIdSet.size() < insertLineSize || CollectionUtils.containsAny(entitiesAssetIdSet, insertAssetIdSet)) {
            throw new CommonException(AatnConstans.AatnErrorCode.AATN_DUPLICATE_ASSET_ID);
        }
    }

    /**
     * 处理condition中的process_status
     *
     * @param tenantId  租户ID
     * @param condition condition查询条件
     * @return 特定条件
     */
    public List<String> processStatusCondition(Long tenantId, String condition) {
        // 处理查询condition参数
        List<String> codeList = new ArrayList<>();
        if (StringUtils.isNotEmpty(condition)) {
            // 处理状态
            List<LovValueDTO> processStatusLov = lovAdapter.queryLovValue(AatnConstans.AatnLovCode.APPROVE_STATUS, tenantId);
            if (CollectionUtils.isNotEmpty(processStatusLov)) {
                processStatusLov.forEach(location -> {
                    if (StringUtils.contains(location.getMeaning(), condition)) {
                        codeList.add(location.getValue());
                    }
                });
            }
        }
        return codeList;
    }

    /**
     * 根据当前用户ID获取默认员工ID作为负责人ID
     *
     * @return 员工ID/负责人ID
     */
    public Long getDefaultEmployeeId() {
        Long userId = DetailsHelper.getUserDetails().getUserId();
        Long tenantId = DetailsHelper.getUserDetails().getTenantId();
        EmployeeDTO employeeDTO = orderDynamicColumnMapper.selectEmployeeByUserId(tenantId, userId);
        return Objects.isNull(employeeDTO) ? null : employeeDTO.getEmployeeId();
    }

    /**
     * 校验资产事务处理类型中定义的字段修改规则
     *
     * @param dataList      数据集
     * @param fieldRulesMap 规则映射 (key, value) -> (字段名， 修改规则)
     * @param fieldEnum     字段枚举
     */
    public <T, K extends AatnFieldController<T, K>> void validTransactionTypeRules(List<T> dataList, Map<String, String> fieldRulesMap, K[] fieldEnum) {
        try {
            String errorMessage = StringUtils.EMPTY;
            for (K field : fieldEnum) {
                errorMessage = field.validControlFieldsWithRules(fieldRulesMap, dataList, messageSource, errorMessage, field);
            }

            if (StringUtils.isNotEmpty(errorMessage)) {
                throw new CommonException(errorMessage);
            }
        } catch (IllegalAccessException e) {
            throw new CommonException(e);
        }
    }

    /**
     * 具体处理
     *
     * @param messageSource MessageSource
     * @param errorMessage  目前错误信息
     * @param fieldEnum     包含字段信息的枚举
     * @param rule          规则
     * @param targetField   目标字段
     * @param t             当前对象
     * @param current       字段当前值
     * @param target        字段目标值
     * @param <T>           数据类泛型
     * @param <K>           枚举类泛型
     * @return 错误信息
     * @throws IllegalAccessException 反射错误
     */
    public static <T, K extends AatnFieldController<T, K>> String exactProcess(MessageSource messageSource, String errorMessage, K fieldEnum, String rule, String targetField, T t, Object current, Object target) throws IllegalAccessException {
        switch (rule) {
            case AatnConstans.AatnChangeRule.MUST_MODIFY:
                if (Objects.isNull(target) || Objects.equals(current, target)) {
                    errorMessage = CommonProcessUtils.getErrorMessage(messageSource, errorMessage, fieldEnum, rule);
                }
                break;
            case AatnConstans.AatnChangeRule.CLEAN:
                FieldUtils.writeDeclaredField(t, targetField, null, true);
                break;
            case AatnConstans.AatnChangeRule.READ_ONLY:
                FieldUtils.writeDeclaredField(t, targetField, current, true);
                break;
            case AatnConstans.AatnChangeRule.ARBITRARY_MODIFY:
                if (Objects.isNull(target)) {
                    FieldUtils.writeDeclaredField(t, targetField, current, true);
                }
                break;
            default:
                break;
        }
        return errorMessage;
    }

    /**
     * 获取报错信息
     *
     * @param messageSource MessageSource
     * @param errorMessage  当前已有报错
     * @param field         字段
     * @param rule          规则
     * @return 报错信息
     */
    private static <T, K extends AatnFieldController<T, K>> String getErrorMessage(MessageSource messageSource, String errorMessage, K field, String rule) {
        if (BaseConstants.DEFAULT_LOCALE.equals(LanguageHelper.locale())) {
            errorMessage = String.join(BaseConstants.Symbol.SEMICOLON, generateErrorMessage(field.getZhFieldName(), rule, messageSource), errorMessage);
        } else {
            errorMessage = String.join(BaseConstants.Symbol.SEMICOLON, generateErrorMessage(field.getEnumName(), rule, messageSource), errorMessage);
        }
        return errorMessage;
    }

    /**
     * 拼接字段修改记录备注
     *
     * @param current     当前字段值
     * @param target      修改后字段值
     * @param fieldName   字段名
     * @param description 备注
     * @return 拼接后备注
     */
    public static String generateDescription(Object current, Object target, String fieldName, String description, MessageSource messageSource) {
        String[] args = {fieldName, current.toString(), target.toString()};
        String message = messageSource.getMessage(AatnConstans.FIELD_CHANGE_DESCRIPTION_TEMPLATE, args, AatnConstans.FIELD_CHANGE_DESCRIPTION_TEMPLATE, LanguageHelper.locale());
        return String.join(BaseConstants.Symbol.SEMICOLON, message, description);
    }

    /**
     * 产生报错信息
     *
     * @param fieldName     字段名称
     * @param ruleName      规则名称
     * @param messageSource MessageSource
     * @return 错误信息
     */
    private static String generateErrorMessage(String fieldName, String ruleName, MessageSource messageSource) {
        String[] args = {fieldName, ruleName};
        return messageSource.getMessage(AatnConstans.AatnErrorCode.AATN_FIELD_CHANGE_MISMATCH_RULE, args, AatnConstans.AatnErrorCode.AATN_FIELD_CHANGE_MISMATCH_RULE, LanguageHelper.locale());
    }


}
