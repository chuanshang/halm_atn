package org.hzero.halm.atn.infra.constant;

import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.commons.lang3.StringUtils;
import org.hzero.halm.atn.domain.entity.OrderDynamicColumn;
import org.hzero.halm.atn.infra.util.CommonProcessUtils;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 资产变更单扩展控制字段（在行明细上）
 *
 * @author like.zhang@hand-china.com 2019/03/26 16:34
 */
public enum AatnChangeOrderExternalControlFields implements AatnFieldController<OrderDynamicColumn, AatnChangeOrderExternalControlFields> {
    /**
     * 扩展控制字段
     */
    ASSET_CRITICALITY("资产重要性"),
    PARENT_ASSET_ID("父资产"),
    // ASSET_STATUS_ID("资产状态"),
    // ASSET_LOCATION_ID("资产位置"),
    ASSET_LOCATION_DESC("位置详细说明"),
    MAP_SOURCE("地图来源"),
    OWNING_ORG_ID("所属组织"),
    OWNING_ORG_DETAIL("所属组织明细"),
    OWNING_PERSON_ID("资产管理员"),
    COST_CENTER_ID("成本中心"),
    ASSET_SPECIALTY_ID("所属资产行业"),
    USING_ORG_ID("使用组织"),
    USING_ORG_DETAIL("使用组织明细"),
    TRACKING_NUM("其他跟踪编号"),
    // USER_PERSON_ID("使用人"),
    ;

    private String zhFieldName;

    AatnChangeOrderExternalControlFields(String zhFieldName) {
        this.zhFieldName = zhFieldName;
    }

    @Override
    public String validControlFieldsWithRules(Map<String, String> fieldRules, List<OrderDynamicColumn> list, MessageSource messageSource, String errorMessage, AatnChangeOrderExternalControlFields fieldEnum) throws IllegalAccessException {
        String rule = fieldRules.get(fieldEnum.name().toLowerCase());

        if (StringUtils.isEmpty(rule)) {
            // 如果规则为空，则表示这个字段不进行控制
            return StringUtils.EMPTY;
        }

        for (OrderDynamicColumn t : list) {
            Object currentColumnName = FieldUtils.readDeclaredField(t, "currentColumnName", true);
            if (!Objects.equals(currentColumnName, fieldEnum.name().toLowerCase())) {
                continue;
            }

            Object currentValue = FieldUtils.readField(t, "currentColumnValue", true);
            Object targetValue = FieldUtils.readField(t, "targetColumnValue", true);

            errorMessage = CommonProcessUtils.exactProcess(messageSource, errorMessage, fieldEnum, rule, "targetColumnValue", t, currentValue, targetValue);
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
