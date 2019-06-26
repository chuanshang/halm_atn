package org.hzero.halm.atn.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.choerodon.core.exception.CommonException;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import io.choerodon.mybatis.domain.AuditDomain;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hzero.halm.atn.infra.constant.AatnConstans;

import java.util.List;

/**
 * 资产事务处理单据动态字段
 *
 * @author like.zhang@hand-china.com 2019-03-26 11:49:44
 */
@ApiModel("资产事务处理单据动态字段")
@VersionAudit
@ModifyAudit
@Table(name = "aatn_order_dynamic_column")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDynamicColumn extends AuditDomain {

    public static final String FIELD_DYNAMIC_COLUMN_ID = "dynamicColumnId";
    public static final String FIELD_ORDER_HEADER_ID = "orderHeaderId";
    public static final String FIELD_ORDER_LINE_ID = "orderLineId";
    public static final String FIELD_ORDER_TYPE_CODE = "orderTypeCode";
    public static final String FIELD_CURRENT_TABLE_NAME = "currentTableName";
    public static final String FIELD_CURRENT_COLUMN_NAME = "currentColumnName";
    public static final String FIELD_CURRENT_COLUMN_DESC = "currentColumnDesc";
    public static final String FIELD_CURRENT_COLUMN_VALUE = "currentColumnValue";
    public static final String FIELD_TARGET_COLUMN_TYPE = "targetColumnType";
    public static final String FIELD_TARGET_COLUMN_VALUE = "targetColumnValue";
    public static final String FIELD_TARGET_COLUMN_DESC = "targetColumnDesc";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_TENANT_ID = "tenantId";


    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    /**
     * 字段值范围校验
     *
     * @param orgScope         所属组织范围
     * @param specialScope     专业资产范围
     * @param changeDetailList 字段明细
     */
    public static void validScope(List<String> orgScope, List<String> specialScope, List<OrderDynamicColumn> changeDetailList) {
        if (CollectionUtils.isEmpty(changeDetailList)) {
            return;
        }

        for (OrderDynamicColumn detail : changeDetailList) {
            String currentColumnName = detail.getCurrentColumnName();
            String targetColumnValue = detail.getTargetColumnValue();

            switch (currentColumnName){
                case AatnConstans.FieldsWithScope.OWNING_ORG_ID:
                    if (CollectionUtils.isNotEmpty(orgScope) && !orgScope.contains(targetColumnValue)) {
                        throw new CommonException(AatnConstans.AatnErrorCode.AATN_OWNING_ORG_OUT_OF_RANGE, targetColumnValue, orgScope.toString());
                    }
                    break;
                case AatnConstans.FieldsWithScope.ASSET_SPECIALTY_ID:
                    if (CollectionUtils.isNotEmpty(specialScope) && !specialScope.contains(targetColumnValue)){
                        throw new CommonException(AatnConstans.AatnErrorCode.AATN_OWNING_MAJOR_OUT_OF_RANGE, targetColumnValue, specialScope.toString());
                    }
                    break;
                default:
                    break ;
            }
        }
    }

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    private Long dynamicColumnId;
    @ApiModelProperty(value = "单据行ID")
    private Long orderLineId;
    @ApiModelProperty(value = "单据头ID")
    private Long orderHeaderId;
    @ApiModelProperty(value = "单据类型")
    @NotBlank
    @Length(max = 30)
    private String orderTypeCode;
    @ApiModelProperty(value = "当前表名")
    @NotBlank
    @Length(max = 30)
    private String currentTableName;
    @ApiModelProperty(value = "当前字段名")
    @NotBlank
    @Length(max = 30)
    private String currentColumnName;
    @ApiModelProperty(value = "当前字段值")
    @Length(max = 30)
    private String currentColumnValue;
    @ApiModelProperty(value = "目标字段属性")
    @NotBlank
    @Length(max = 30)
    private String targetColumnType;
    @ApiModelProperty(value = "目标字段值")
    @Length(max = 30)
    private String targetColumnValue;
    @ApiModelProperty(value = "备注")
    @Length(max = 240)
    private String description;
    @ApiModelProperty(value = "租户ID")
    @NotNull
    private Long tenantId;
	@ApiModelProperty(value = "当前字段含义")
	@Length(max = 240)
	private String currentColumnDesc;
    @ApiModelProperty(value = "目标字段含义")
    @Length(max = 240)
    private String targetColumnDesc;

	//
    // 非数据库字段
    // ------------------------------------------------------------------------------

    //
    // getter/setter
    // ------------------------------------------------------------------------------

    public Long getOrderHeaderId() {
        return orderHeaderId;
    }

    public void setOrderHeaderId(Long orderHeaderId) {
        this.orderHeaderId = orderHeaderId;
    }

    public String getOrderTypeCode() {
        return orderTypeCode;
    }

    public void setOrderTypeCode(String orderTypeCode) {
        this.orderTypeCode = orderTypeCode;
    }

    public String getCurrentColumnDesc() {
        return currentColumnDesc;
    }

    public void setCurrentColumnDesc(String currentColumnDesc) {
        this.currentColumnDesc = currentColumnDesc;
    }

    public String getTargetColumnDesc() {
        return targetColumnDesc;
    }

    public void setTargetColumnDesc(String targetColumnDesc) {
        this.targetColumnDesc = targetColumnDesc;
    }

    /**
     * @return 表ID，主键，供其他表做外键
     */
	public Long getDynamicColumnId() {
		return dynamicColumnId;
	}

	public void setDynamicColumnId(Long dynamicColumnId) {
		this.dynamicColumnId = dynamicColumnId;
	}
    /**
     * @return 资产移交归还单行ID
     */
	public Long getOrderLineId() {
		return orderLineId;
	}

	public void setOrderLineId(Long orderLineId) {
		this.orderLineId = orderLineId;
	}
    /**
     * @return 当前表名
     */
	public String getCurrentTableName() {
		return currentTableName;
	}

	public void setCurrentTableName(String currentTableName) {
		this.currentTableName = currentTableName;
	}
    /**
     * @return 当前字段名
     */
	public String getCurrentColumnName() {
		return currentColumnName;
	}

	public void setCurrentColumnName(String currentColumnName) {
		this.currentColumnName = currentColumnName;
	}
    /**
     * @return 当前字段值
     */
	public String getCurrentColumnValue() {
		return currentColumnValue;
	}

	public void setCurrentColumnValue(String currentColumnValue) {
		this.currentColumnValue = currentColumnValue;
	}
    /**
     * @return 目标字段属性
     */
	public String getTargetColumnType() {
		return targetColumnType;
	}

	public void setTargetColumnType(String targetColumnType) {
		this.targetColumnType = targetColumnType;
	}
    /**
     * @return 目标字段值
     */
	public String getTargetColumnValue() {
		return targetColumnValue;
	}

	public void setTargetColumnValue(String targetColumnValue) {
		this.targetColumnValue = targetColumnValue;
	}
    /**
     * @return 备注
     */
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    /**
     * @return 租户ID
     */
	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

}
