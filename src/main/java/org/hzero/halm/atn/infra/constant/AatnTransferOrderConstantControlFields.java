package org.hzero.halm.atn.infra.constant;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.commons.lang3.StringUtils;
import org.hzero.halm.atn.domain.entity.TransferOrderLine;
import org.hzero.halm.atn.infra.util.CommonProcessUtils;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 资产变更单固定控制字段（在行上）
 *
 * @author like.zhang@hand-china.com 2019/03/21 14:42
 */
public enum AatnTransferOrderConstantControlFields implements AatnFieldController<TransferOrderLine, AatnTransferOrderConstantControlFields> {
    /**
     * 资产状态
     */
    ASSET_STATUS("资产状态", "current_asset_status_id", "target_asset_status_id"),
    /**
     * 所属组织
     */
    OWNING_ORG_ID("所属组织", "current_owning_org", "target_owning_org"),
    /**
     * 成本中心
     */
//    COST_CENTER_CODE("成本中心", "current_cost_center", "target_cost_center"),
    // /**
    //  * 资产位置
    //  */
    // ASSET_LOCATION_ID("资产位置", "current_location_id", "target_location_id"),
    // /**
    //  * 所属人
    //  */
    // OWNING_PERSON_ID("所属人", "current_owning_person_id", "target_owning_person_id"),
    // /**
    //  * 使用人
    //  */
    // USER_PERSON_ID("使用人", "current_using_person_id", "target_using_person_id"),
    ;

    private String zhFieldName;
    private String currentFieldName;
    private String targetFieldName;


    AatnTransferOrderConstantControlFields(String zhFieldName, String currentFieldName, String targetFieldName) {
        this.zhFieldName = zhFieldName;
        this.currentFieldName = currentFieldName;
        this.targetFieldName = targetFieldName;
    }


    @Override
    public String validControlFieldsWithRules(Map<String, String> fieldRules, List<TransferOrderLine> list, MessageSource messageSource, String errorMessage, AatnTransferOrderConstantControlFields fieldEnum) throws IllegalAccessException {
        String rule = fieldRules.get(fieldEnum.name().toLowerCase());

        if (StringUtils.isEmpty(rule)) {
            // 如果规则为空，则表示这个字段不进行控制
            return StringUtils.EMPTY;
        }

        String currentField = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, fieldEnum.currentFieldName);
        if (Objects.isNull(currentField)) {
            return StringUtils.EMPTY;
        }
        String targetField = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, fieldEnum.targetFieldName);

        // 必须修改
        for (TransferOrderLine t : list) {
            Object current = FieldUtils.readField(t, currentField, true);
            Object target = FieldUtils.readField(t, targetField, true);

            errorMessage = CommonProcessUtils.exactProcess(messageSource, errorMessage, fieldEnum, rule, targetField, t, current, target);
        }
        return errorMessage;
    }

    @Override
    public String getZhFieldName() {
        return zhFieldName;
    }

    @Override
    public String getEnumName() {
        return this.name();
    }
}
