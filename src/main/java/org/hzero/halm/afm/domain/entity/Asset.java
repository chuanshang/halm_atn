package org.hzero.halm.afm.domain.entity;

import com.google.common.base.CaseFormat;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.core.base.BaseConstants;
import org.hzero.halm.afm.infra.annotation.FieldMessage;
import org.hzero.halm.afm.infra.constant.Constants;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * 资产/设备基本信息
 *
 * @author zhisheng.zhang@hand-china.com 2019-01-24 16:43:37
 */
@ApiModel("资产/设备基本信息")
@VersionAudit
@ModifyAudit
@Table(name = "aafm_asset")
public class Asset extends AuditDomain {
    public static final String FIELD_ASSET_ID = "assetId";
    public static final String FIELD_ASSET_NUM = "assetNum";
    public static final String FIELD_ASSETS_SET_ID = "assetsSetId";
    public static final String FIELD_ASSET_CLASS_ID = "assetClassId";
    public static final String FIELD_ASSET_DESC = "assetDesc";
    public static final String FIELD_SPECIAL_ASSET_CODE = "specialAssetCode";
    public static final String FIELD_BRAND = "brand";
    public static final String FIELD_MODEL = "model";
    public static final String FIELD_NAME_RULE_CODE = "nameRuleCode";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_SERIAL_NUM = "serialNum";
    public static final String FIELD_TRACKING_NUM = "trackingNum";
    public static final String FIELD_MAINTAIN_FLAG = "maintainFlag";
    public static final String FIELD_ASSET_NAME = "assetName";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_ASSET_ICON = "assetIcon";
    public static final String FIELD_ATTRIBUTE_VALUES = "attributeValues";
    public static final String FIELD_LINEAR_NAME = "linearName";
    public static final String FIELD_LINEAR_START_MEASURE = "linearStartMeasure";
    public static final String FIELD_LINEAR_END_MEASURE = "linearEndMeasure";
    public static final String FIELD_LINEAR_START_DESC = "linearStartDesc";
    public static final String FIELD_LINEAR_END_DESC = "linearEndDesc";
    public static final String FIELD_CAR_NUM = "carNum";
    public static final String FIELD_VIN_NUM = "vinNum";
    public static final String FIELD_ENGINE_NUM = "engineNum";
    public static final String FIELD_ASSET_SOURCE_TYPE_CODE = "assetSourceTypeCode";
    public static final String FIELD_ASSET_SOURCE_ID = "assetSourceId";
    public static final String FIELD_AOS_RECEIVING_REPORT_ID = "aosReceivingReportId";
    public static final String FIELD_SOURCE_CONTRACT_ID = "sourceContractId";
    public static final String FIELD_SOURCE_PROJECT_ID = "sourceProjectId";
    public static final String FIELD_SUPPLIER_ORG_ID = "supplierOrgId";
    public static final String FIELD_MANUFACTURER_ID = "manufacturerId";
    public static final String FIELD_ASSET_SOURCE_DETAIL = "assetSourceDetail";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_ORIGINAL_COST = "originalCost";
    public static final String FIELD_RECEIVED_DATE = "receivedDate";
    public static final String FIELD_START_DATE = "startDate";
    public static final String FIELD_WARRANTY_TYPE_CODE = "warrantyTypeCode";
    public static final String FIELD_WARRANTY_TYPE_RULE = "warrantyTypeRule";
    public static final String FIELD_WARRANTY_START_DATE = "warrantyStartDate";
    public static final String FIELD_WARRANTY_EXPIRE_DATE = "warrantyExpireDate";
    public static final String FIELD_PARENT_ASSET_ID = "parentAssetId";
    public static final String FIELD_ASSET_CRITICALITY = "assetCriticality";
    public static final String FIELD_ASSET_STATUS_ID = "assetStatusId";
    public static final String FIELD_ASSET_LOCATION_ID = "assetLocationId";
    public static final String FIELD_ASSET_LOCATION_DESC = "assetLocationDesc";
    public static final String FIELD_MAP_SOURCE = "mapSource";
    public static final String FIELD_OWNING_ORG_ID = "owningOrgId";
    public static final String FIELD_OWNING_ORG_DETAIL = "owningOrgDetail";
    public static final String FIELD_COST_CENTER_ID = "costCenterId";
    public static final String FIELD_ASSET_SPECIALTY_ID = "assetSpecialtyId";
    public static final String FIELD_OWNING_PERSON_ID = "owningPersonId";
    public static final String FIELD_USING_ORG_ID = "usingOrgId";
    public static final String FIELD_USING_ORG_DETAIL = "usingOrgDetail";
    public static final String FIELD_USER_PERSON_ID = "userPersonId";
    public static final String FIELD_WARRANTY_NOTES = "warrantyNotes";
    public static final String FIELD_TENANT_ID = "tenantId";


    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    private Long assetId;
    @ApiModelProperty(value = "资产编号，必输且租户内唯一，非中文")
    @NotBlank
    @FieldMessage(fieldName = "资产编号", tag = Constants.AssetClassify.BASE_MSG)
    private String assetNum;

    @ApiModelProperty(value = "产品组/资产组，值集：AMDM_ASSET_SET,必输")
    @NotBlank
    @FieldMessage(fieldName = "产品组/资产组", tag = Constants.AssetClassify.BASE_MSG)
    private Long assetsSetId;

    @ApiModelProperty(value = "产品类别/资产分类，值集：AAFM.ASSET_CLASS 必输")
    @NotBlank
    @FieldMessage(fieldName = "产品类别/资产分类", tag = Constants.AssetClassify.BASE_MSG)
    private Long assetClassId;

    @ApiModelProperty(value = "资产全称，只读,当前命名格式：产品名称.品牌/厂商.规格型号")
    @NotBlank
    @FieldMessage(fieldName = "资产全称", tag = Constants.AssetClassify.BASE_MSG)
    private String assetDesc;

    @ApiModelProperty(value = "所属特殊资产，值集：AAFM.SPECIAL_ASSET")
    @LovValue(value = "AAFM.SPECIAL_ASSET", meaningField = "specialAssetMeaning")
    @FieldMessage(fieldName = "所属特殊资产", tag = Constants.AssetClassify.BASE_MSG)
    private String specialAssetCode;

    @ApiModelProperty(value = "所属特殊资产meaning")
    @Transient
    private String specialAssetMeaning;

    @ApiModelProperty(value = "品牌/厂商")
    @FieldMessage(fieldName = "品牌/厂商", tag = Constants.AssetClassify.BASE_MSG)
    private String brand;

    @ApiModelProperty(value = "规格型号")
    @FieldMessage(fieldName = "规格型号", tag = Constants.AssetClassify.BASE_MSG)
    private String model;

    @ApiModelProperty(value = "资产标签/铭牌规则，值集：AAFM.NAMEPLATE_RULE 必输")
    @LovValue(value = "AAFM.NAMEPLATE_RULE", meaningField = "nameRuleMeaning")
    @NotBlank
    @FieldMessage(fieldName = "资产标签/铭牌规则", tag = Constants.AssetClassify.BASE_MSG)
    private String nameRuleCode;

    @ApiModelProperty(value = "资产标签/铭牌规则meaning")
    @Transient
    private String nameRuleMeaning;

    @ApiModelProperty(value = "可视标签/铭牌， 必输且租户内唯一")
    @NotBlank
    @FieldMessage(fieldName = "可视标签/铭牌", tag = Constants.AssetClassify.BASE_MSG)
    private String name;
    @ApiModelProperty(value = "序列号,非中文")
    @FieldMessage(fieldName = "序列号", tag = Constants.AssetClassify.BASE_MSG)
    private String serialNum;
    @ApiModelProperty(value = "其他跟踪编号")
    @FieldMessage(fieldName = "其他跟踪编号", tag = Constants.AssetClassify.BASE_MSG)
    private String trackingNum;
    @FieldMessage(fieldName = "是否可维修", tag = Constants.AssetClassify.BASE_MSG)
    @ApiModelProperty(value = "是否可维修，默认：1，必输")
    @NotNull
    private Integer maintainFlag;

    @ApiModelProperty(value = "产品名称，必输")
    @NotBlank
    @FieldMessage(fieldName = "产品名称", tag = Constants.AssetClassify.BASE_MSG)
    private String assetName;

    @ApiModelProperty(value = "描述")
    @FieldMessage(fieldName = "描述", tag = Constants.AssetClassify.BASE_MSG)
    private String description;

    @ApiModelProperty(value = "图标")
    private String assetIcon;




    @ApiModelProperty(value = "属性行值，格式[{attrCode: 'aaa', attrValue:'bbb'},{}...] 对象数组")
    private String attributeValues;
    @ApiModelProperty(value = "线性描述-长度，必输，保留8位小数(视所属特殊资产选项而定)")
    private Long linearName;
    @ApiModelProperty(value = "线性描述-开始端(A)计量位，必输，保留9位小数(视所属特殊资产选项而定)")
    private Long linearStartMeasure;
    @ApiModelProperty(value = "线性描述-结束端(B)端计量位，必输，保留10位小数(视所属特殊资产选项而定)")
    private Long linearEndMeasure;
    @ApiModelProperty(value = "线性描述-开始端(A)描述")
    private String linearStartDesc;
    @ApiModelProperty(value = "线性描述-开始端(A)描述")
    private String linearEndDesc;
    @ApiModelProperty(value = "车牌号，必输(视所属特殊资产选项而定)")


    private String carNum;

    @ApiModelProperty(value = "车架号")
    private String vinNum;

    @ApiModelProperty(value = "发动机号")
    private String engineNum;



    @ApiModelProperty(value = "资产来源, 值集：AAFM.ASSET_SOURCE")
    @LovValue(value = "AAFM.ASSET_SOURCE", meaningField = "assetSourceTypeMeaning")
    @NotBlank
    @FieldMessage(fieldName = "资产来源",tag = Constants.AssetClassify.SOURCE_TRACK)
    private String assetSourceTypeCode;

    @ApiModelProperty(value = "资产来源meaning")
    @Transient
    private String assetSourceTypeMeaning;

    @ApiModelProperty(value = "其他来源相关文档号，值集：AAFM.ASSET_SOURCE_DOC")
    @FieldMessage(fieldName = "其他来源相关文档号",tag = Constants.AssetClassify.SOURCE_TRACK)
    private Long assetSourceId;

    @ApiModelProperty(value = "来源验收单，值集：AAFM.ASSET_SOURCE_RECEIPT")
    @FieldMessage(fieldName = "来源验收单",tag = Constants.AssetClassify.SOURCE_TRACK)
    private Long aosReceivingReportId;

    @ApiModelProperty(value = "来源合同，值集：AAFM.ASSET_SOURCE_CONTRACT")
    @FieldMessage(fieldName = "来源合同",tag = Constants.AssetClassify.SOURCE_TRACK)
    private Long sourceContractId;

    @ApiModelProperty(value = "来源项目，值集：AAFM.ASSET_SOURCE_PROJECT")
    @FieldMessage(fieldName = "来源项目",tag = Constants.AssetClassify.SOURCE_TRACK)
    private Long sourceProjectId;

    @ApiModelProperty(value = "供应商")
    @FieldMessage(fieldName = "供应商",tag = Constants.AssetClassify.SOURCE_TRACK)
    private Long supplierOrgId;

    @ApiModelProperty(value = "制造厂商(总装)")
    @FieldMessage(fieldName = "制造厂商(总装)",tag = Constants.AssetClassify.SOURCE_TRACK)
    private Long manufacturerId;

    @ApiModelProperty(value = "来源明细")
    @FieldMessage(fieldName = "来源明细",tag = Constants.AssetClassify.SOURCE_TRACK)
    private String assetSourceDetail;

    @ApiModelProperty(value = "货币，值集：HPFM.CURRENCY")
    @FieldMessage(fieldName = "货币",tag = Constants.AssetClassify.SOURCE_TRACK)
    private String currencyCode;

    @ApiModelProperty(value = "获取价格")
    @FieldMessage(fieldName = "获取价格",tag = Constants.AssetClassify.SOURCE_TRACK)
    private BigDecimal originalCost;

    @ApiModelProperty(value = "交付日期")
    @FieldMessage(fieldName = "交付日期",tag = Constants.AssetClassify.SOURCE_TRACK)
    private Date receivedDate;

    @ApiModelProperty(value = "启用日期")
    @FieldMessage(fieldName = "启用日期",tag = Constants.AssetClassify.SOURCE_TRACK)
    private Date startDate;



    @ApiModelProperty(value = "质保类型，值集：AAFM.ASSET_WARRANTY_TYPE")
    @LovValue(value = "AAFM.ASSET_WARRANTY_TYPE", meaningField = "warrantyTypeMeaning")
    private String warrantyTypeCode;
    @ApiModelProperty("质保类型Meaning")
    @Transient
    private String warrantyTypeMeaning;
    @ApiModelProperty(value = "质保起始日规则，值集：AAFM.ASSET_WARRANTY_RULE [下拉列表]")
    @LovValue(value = "AAFM.ASSET_WARRANTY_RULE", meaningField = "warrantyTypeRuleMeaning")
    private String warrantyTypeRule;
    @ApiModelProperty(value = "质保起始日规则meaning")
    @Transient
    private String warrantyTypeRuleMeaning;
    @ApiModelProperty(value = "质保起始日")
    private Date warrantyStartDate;
    @ApiModelProperty(value = "质保终止日")
    private Date warrantyExpireDate;



    @FieldMessage(fieldName = "父资产",tag = Constants.AssetClassify.MANAGE_TRACK)
    @ApiModelProperty(value = "父资产，值集：AAFM.ASSET")
    private Long parentAssetId;

    @ApiModelProperty(value = "资产重要性，值集：AAFM.ASSET_CRITICALITY")
    @FieldMessage(fieldName = "资产重要性",tag = Constants.AssetClassify.MANAGE_TRACK)
    @LovValue(value = "AAFM.ASSET_CRITICALITY", meaningField = "assetCriticalityMeaning")
    private String assetCriticality;

    @ApiModelProperty(value = "资产状态，值集：AAFM.ASSET_STATUS")
    @FieldMessage(fieldName = "资产状态",tag = Constants.AssetClassify.MANAGE_TRACK)
    @NotNull
    private Long assetStatusId;

    @ApiModelProperty(value = "资产位置，必输，值集：AAFM.ASSET_LOCATION")
    @NotBlank
    @FieldMessage(fieldName = "资产位置",tag = Constants.AssetClassify.MANAGE_TRACK)
    private Long assetLocationId;

    @ApiModelProperty(value = "资产位置详细说明")
    @FieldMessage(fieldName = "资产位置详细说明",tag = Constants.AssetClassify.MANAGE_TRACK)
    private String assetLocationDesc;

    @ApiModelProperty(value = "地图来源，必输[下拉列表]")
    @NotBlank
    @FieldMessage(fieldName = "地图来源",tag = Constants.AssetClassify.MANAGE_TRACK)
    private String mapSource;

    @ApiModelProperty(value = "所属组织，值集：AMDM.ORGANIZATION")
    @FieldMessage(fieldName = "所属组织",tag = Constants.AssetClassify.MANAGE_TRACK)
    private Long owningOrgId;

    @ApiModelProperty(value = "所属组织meaning")
    @Transient
    private String owningOrgName;

    @ApiModelProperty(value = "所属组织明细")
    @FieldMessage(fieldName = "所属组织明细",tag = Constants.AssetClassify.MANAGE_TRACK)
    private String owningOrgDetail;

    @ApiModelProperty(value = "成本中心，值集：AAFM.ASSET_COST_CENTER")
    @FieldMessage(fieldName = "成本中心",tag = Constants.AssetClassify.MANAGE_TRACK)
    private Long costCenterId;

    @ApiModelProperty(value = "所属资产专业分类，值集：AAFM.SPECIAL_ASSET_CLASS")
    @FieldMessage(fieldName = "所属资产专业分类",tag = Constants.AssetClassify.MANAGE_TRACK)
    private Long assetSpecialtyId;

    @ApiModelProperty(value = "资产管理员，值集：HALM.EMPLOYEE")
    @FieldMessage(fieldName = "资产管理员",tag = Constants.AssetClassify.MANAGE_TRACK)
    private Long owningPersonId;

    @ApiModelProperty(value = "使用组织，值集：AMDM.ORGANIZATION")
    @FieldMessage(fieldName = "使用组织",tag = Constants.AssetClassify.MANAGE_TRACK)
    private Long usingOrgId;

    @ApiModelProperty(value = "使用组织meaning")
    @Transient
    private String usingOrgIdMeaning;

    @ApiModelProperty(value = "使用组织明细")
    @FieldMessage(fieldName = "使用组织明细",tag = Constants.AssetClassify.MANAGE_TRACK)
    private String usingOrgDetail;

    @ApiModelProperty(value = "使用人，值集：HALM.EMPLOYEE")
    @FieldMessage(fieldName = "使用人",tag = Constants.AssetClassify.MANAGE_TRACK)
    private Long userPersonId;

    @ApiModelProperty(value = "质保详细说明")
    @FieldMessage(fieldName = "质保详细说明",tag = Constants.AssetClassify.MANAGE_TRACK)
    private String warrantyNotes;

    @ApiModelProperty(value = "租户ID")
    @NotNull
    private Long tenantId;

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    /**
     * 根据资产分类，获取不同类别下的资产字段集合。
     * 资产字段分类的code,code值集见 {@link org.hzero.halm.afm.infra.constant.Constants.AssetClassify}
     *
     * @return 分类字段集合
     */
    public static Map<String, List> getClassifyFields() {
        Field[] fields = Asset.class.getDeclaredFields();
        Map<String, List> resultMap = new HashMap(4);
        for (Field f : fields) {
            FieldMessage fieldMessage = f.getAnnotation(FieldMessage.class);
            Set set = resultMap.keySet();
            if (fieldMessage != null) {
                Map msg = new HashMap(3);
                msg.put("meaning", fieldMessage.fieldName());
                msg.put("value", CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, f.getName()));
                msg.put("classifyCode", fieldMessage.tag());
                if (set.contains(fieldMessage.tag())) {
                    resultMap.get(fieldMessage.tag()).add(msg);
                } else {
                    List list = new ArrayList();
                    list.add(msg);
                    resultMap.put(fieldMessage.tag(), list);
                }
            }


        }

        return resultMap;
    }

    /**
     * 获取某字段对应的含义
     * @param field 字段的英文值
     * @return 对应字段含义
     * @throws NoSuchFieldException 异常
     */
    public static String getFieldMeaning(String field) throws NoSuchFieldException {
        if (field == null) {
            return null;
        } else if (StringUtils.contains(field, BaseConstants.Symbol.LOWER_LINE)) {
            // 如果字段是下划线格式，将其转换成小驼峰格式。
            field = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, field);
        }
        String re = null;
        FieldMessage fieldMessage = Asset.class.getDeclaredField(field).getAnnotation(FieldMessage.class);
        if (fieldMessage != null) {
            return fieldMessage.fieldName();
        }
        return re;
    }

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------
    @Transient
    /**
     * 线性描述行
     */
    private AssetLinear assetLinear;


    @Transient
    /**
     * 属性组ID
     */
    private Long attributeSetId;

    /**
     * 启用日期从
     */
    @ApiModelProperty(value = "启用日期从")
    @Transient
    private Date startDateFrom;
    /**
     * 启用日期至
     */
    @ApiModelProperty(value = "启用日期至")
    @Transient
    private Date startDateTo;
    /**
     * 交付日期从
     */
    @ApiModelProperty(value = "交付日期从")
    @Transient
    private Date receivedDateFrom;
    /**
     * 交付日期至
     */
    @ApiModelProperty(value = "交付日期至")
    @Transient
    private Date receivedDateTo;
    /**
     * 资产状态Code
     */
    @Transient
    private String assetStatus;
    /**
     * 资产状态系统描述
     */
    @Transient
    private String sysStatusName;
    /**
     * 资产状态用户自定义描述
     */
    @Transient
    private String userStatusName;
    /**
     * 资产管理员描述
     */
    @Transient
    private String owningPersonName;
    /**
     * 使用人描述
     */
    @Transient
    private String userPersonName;
    /**
     * 父资产
     */
    @Transient
    private String parentAssetName;
    /**
     * 资产状态
     */
    @Transient
    private String assetStatusName;
    /**
     * 资产位置
     */
    @Transient
    private String assetLocationName;
    /**
     * 使用组织
     */
    @Transient
    private String usingOrgName;
    /**
     * 所属资产行业
     */
    @Transient
    private String assetSpecialtyName;
    /**
     * 产品类别/资产分类
     */
    @Transient
    private String assetClassMeaning;
    /**
     * 资产组
     */
    @Transient
    private String assetsSetName;
    /**
     * 供应商
     */
    @Transient
    private String supplierOrgMeaning;
    /**
     * 制造厂商(总装)
     */
    @Transient
    private String manufacturerName;
    /**
     * 成本中心
     */
    @Transient
    private String costCenterMeaning;
    /**
     * 资产重要性Meaning
     */
    @Transient
    private String assetCriticalityMeaning;
    //
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return 表ID，主键，供其他表做外键
     */
    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    /**
     * @return 资产编号，必输且租户内唯一，非中文
     */
    public String getAssetNum() {
        return assetNum;
    }

    public void setAssetNum(String assetNum) {
        this.assetNum = assetNum;
    }

    /**
     * @return 产品组/资产组，值集：AMDM_ASSET_SET,必输
     */
    public Long getAssetsSetId() {
        return assetsSetId;
    }

    public void setAssetsSetId(Long assetsSetId) {
        this.assetsSetId = assetsSetId;
    }

    /**
     * @return 产品类别/资产分类，值集：AAFM.ASSET_CLASS 必输
     */
    public Long getAssetClassId() {
        return assetClassId;
    }

    public void setAssetClassId(Long assetClassId) {
        this.assetClassId = assetClassId;
    }

    /**
     * @return 资产全称，只读,当前命名格式：产品名称.品牌/厂商.规格型号
     */
    public String getAssetDesc() {
        return assetDesc;
    }

    public void setAssetDesc(String assetDesc) {
        this.assetDesc = assetDesc;
    }

    /**
     * @return 所属特殊资产，值集：AAFM.SPECIAL_ASSET
     */
    public String getSpecialAssetCode() {
        return specialAssetCode;
    }

    public void setSpecialAssetCode(String specialAssetCode) {
        this.specialAssetCode = specialAssetCode;
    }

    /**
     * @return 品牌/厂商
     */
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return 规格型号
     */
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return 资产标签/铭牌规则，值集：AAFM.NAMEPLATE_RULE 必输
     */
    public String getNameRuleCode() {
        return nameRuleCode;
    }

    public void setNameRuleCode(String nameRuleCode) {
        this.nameRuleCode = nameRuleCode;
    }

    /**
     * @return 可视标签/铭牌， 必输且租户内唯一
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 序列号, 非中文
     */
    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    /**
     * @return 其他跟踪编号
     */
    public String getTrackingNum() {
        return trackingNum;
    }

    public void setTrackingNum(String trackingNum) {
        this.trackingNum = trackingNum;
    }

    /**
     * @return 是否可维修，默认：1，必输
     */
    public Integer getMaintainFlag() {
        return maintainFlag;
    }

    public void setMaintainFlag(Integer maintainFlag) {
        this.maintainFlag = maintainFlag;
    }

    /**
     * @return 产品名称，必输
     */
    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    /**
     * @return 描述
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return 图标
     */
    public String getAssetIcon() {
        return assetIcon;
    }

    public void setAssetIcon(String assetIcon) {
        this.assetIcon = assetIcon;
    }

    /**
     * @return 属性行值，格式[{attrCode: "aaa", attrValue:"bbb"},{}...] 对象数组
     */
    public String getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(String attributeValues) {
        this.attributeValues = attributeValues;
    }

    /**
     * @return 线性描述-长度，必输，保留8位小数(视所属特殊资产选项而定)
     */
    public Long getLinearName() {
        return linearName;
    }

    public void setLinearName(Long linearName) {
        this.linearName = linearName;
    }

    /**
     * @return 线性描述-开始端(A)计量位，必输，保留9位小数(视所属特殊资产选项而定)
     */
    public Long getLinearStartMeasure() {
        return linearStartMeasure;
    }

    public void setLinearStartMeasure(Long linearStartMeasure) {
        this.linearStartMeasure = linearStartMeasure;
    }

    /**
     * @return 线性描述-结束端(B)端计量位，必输，保留10位小数(视所属特殊资产选项而定)
     */
    public Long getLinearEndMeasure() {
        return linearEndMeasure;
    }

    public void setLinearEndMeasure(Long linearEndMeasure) {
        this.linearEndMeasure = linearEndMeasure;
    }

    /**
     * @return 线性描述-开始端(A)描述
     */
    public String getLinearStartDesc() {
        return linearStartDesc;
    }

    public void setLinearStartDesc(String linearStartDesc) {
        this.linearStartDesc = linearStartDesc;
    }

    /**
     * @return 线性描述-开始端(A)描述
     */
    public String getLinearEndDesc() {
        return linearEndDesc;
    }

    public void setLinearEndDesc(String linearEndDesc) {
        this.linearEndDesc = linearEndDesc;
    }

    /**
     * @return 车牌号，必输(视所属特殊资产选项而定)
     */
    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    /**
     * @return 车架号
     */
    public String getVinNum() {
        return vinNum;
    }

    public void setVinNum(String vinNum) {
        this.vinNum = vinNum;
    }

    /**
     * @return 发动机号
     */
    public String getEngineNum() {
        return engineNum;
    }

    public void setEngineNum(String engineNum) {
        this.engineNum = engineNum;
    }

    /**
     * @return 资产来源, 值集：AAFM.ASSET_SOURCE
     */
    public String getAssetSourceTypeCode() {
        return assetSourceTypeCode;
    }

    public void setAssetSourceTypeCode(String assetSourceTypeCode) {
        this.assetSourceTypeCode = assetSourceTypeCode;
    }

    /**
     * @return 其他来源相关文档号，值集：AAFM.ASSET_SOURCE_DOC
     */
    public Long getAssetSourceId() {
        return assetSourceId;
    }

    public void setAssetSourceId(Long assetSourceId) {
        this.assetSourceId = assetSourceId;
    }

    /**
     * @return 来源验收单，值集：AAFM.ASSET_SOURCE_RECEIPT
     */
    public Long getAosReceivingReportId() {
        return aosReceivingReportId;
    }

    public void setAosReceivingReportId(Long aosReceivingReportId) {
        this.aosReceivingReportId = aosReceivingReportId;
    }

    /**
     * @return 来源合同，值集：AAFM.ASSET_SOURCE_CONTRACT
     */
    public Long getSourceContractId() {
        return sourceContractId;
    }

    public void setSourceContractId(Long sourceContractId) {
        this.sourceContractId = sourceContractId;
    }

    /**
     * @return 来源项目，值集：AAFM.ASSET_SOURCE_PROJECT
     */
    public Long getSourceProjectId() {
        return sourceProjectId;
    }

    public void setSourceProjectId(Long sourceProjectId) {
        this.sourceProjectId = sourceProjectId;
    }

    /**
     * @return 供应商
     */
    public Long getSupplierOrgId() {
        return supplierOrgId;
    }

    public void setSupplierOrgId(Long supplierOrgId) {
        this.supplierOrgId = supplierOrgId;
    }

    /**
     * @return 来源明细
     */
    public String getAssetSourceDetail() {
        return assetSourceDetail;
    }

    public void setAssetSourceDetail(String assetSourceDetail) {
        this.assetSourceDetail = assetSourceDetail;
    }

    /**
     * @return 货币，值集：HPFM.CURRENCY
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * @return 获取价格
     */
    public BigDecimal getOriginalCost() {
        return originalCost;
    }

    public void setOriginalCost(BigDecimal originalCost) {
        this.originalCost = originalCost;
    }

    /**
     * @return 交付日期
     */
    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    /**
     * @return 启用日期
     */
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return 质保类型，值集：AAFM.ASSET_WARRANTY_TYPE
     */
    public String getWarrantyTypeCode() {
        return warrantyTypeCode;
    }

    public void setWarrantyTypeCode(String warrantyTypeCode) {
        this.warrantyTypeCode = warrantyTypeCode;
    }

    /**
     * @return 质保起始日规则，值集：AAFM.ASSET_WARRANTY_RULE [下拉列表]
     */
    public String getWarrantyTypeRule() {
        return warrantyTypeRule;
    }

    public void setWarrantyTypeRule(String warrantyTypeRule) {
        this.warrantyTypeRule = warrantyTypeRule;
    }

    /**
     * @return 质保起始日
     */
    public Date getWarrantyStartDate() {
        return warrantyStartDate;
    }

    public void setWarrantyStartDate(Date warrantyStartDate) {
        this.warrantyStartDate = warrantyStartDate;
    }

    /**
     * @return 质保终止日
     */
    public Date getWarrantyExpireDate() {
        return warrantyExpireDate;
    }

    public void setWarrantyExpireDate(Date warrantyExpireDate) {
        this.warrantyExpireDate = warrantyExpireDate;
    }

    /**
     * @return 父资产，值集：AAFM.ASSET
     */
    public Long getParentAssetId() {
        return parentAssetId;
    }

    public void setParentAssetId(Long parentAssetId) {
        this.parentAssetId = parentAssetId;
    }

    /**
     * @return 资产重要性，值集：AMDM.ASSET_CRITICALITY
     */
    public String getAssetCriticality() {
        return assetCriticality;
    }

    public void setAssetCriticality(String assetCriticality) {
        this.assetCriticality = assetCriticality;
    }

    /**
     * @return 资产状态，值集：AAFM.ASSET_STATUS
     */
    public Long getAssetStatusId() {
        return assetStatusId;
    }

    public void setAssetStatusId(Long assetStatusId) {
        this.assetStatusId = assetStatusId;
    }

    /**
     * @return 资产位置，必输，值集：AAFM.ASSET_LOCATION
     */
    public Long getAssetLocationId() {
        return assetLocationId;
    }

    public void setAssetLocationId(Long assetLocationId) {
        this.assetLocationId = assetLocationId;
    }

    /**
     * @return 资产位置详细说明
     */
    public String getAssetLocationDesc() {
        return assetLocationDesc;
    }

    public void setAssetLocationDesc(String assetLocationDesc) {
        this.assetLocationDesc = assetLocationDesc;
    }

    /**
     * @return 地图来源，必输[下拉列表]
     */
    public String getMapSource() {
        return mapSource;
    }

    public void setMapSource(String mapSource) {
        this.mapSource = mapSource;
    }

    /**
     * @return 所属组织，值集：AMDM.ORGANIZATION
     */
    public Long getOwningOrgId() {
        return owningOrgId;
    }

    public void setOwningOrgId(Long owningOrgId) {
        this.owningOrgId = owningOrgId;
    }

    /**
     * @return 所属组织明细
     */
    public String getOwningOrgDetail() {
        return owningOrgDetail;
    }

    public void setOwningOrgDetail(String owningOrgDetail) {
        this.owningOrgDetail = owningOrgDetail;
    }

    /**
     * @return 所属资产专业分类，值集：AAFM.SPECIAL_ASSET_CLASS
     */
    public Long getAssetSpecialtyId() {
        return assetSpecialtyId;
    }

    public void setAssetSpecialtyId(Long assetSpecialtyId) {
        this.assetSpecialtyId = assetSpecialtyId;
    }

    /**
     * @return 资产管理员，值集：AAFM.ASSET_MANAGER
     */
    public Long getOwningPersonId() {
        return owningPersonId;
    }

    public void setOwningPersonId(Long owningPersonId) {
        this.owningPersonId = owningPersonId;
    }

    /**
     * @return 使用组织，值集：AAFM.ASSET_USING_ORG
     */
    public Long getUsingOrgId() {
        return usingOrgId;
    }

    public void setUsingOrgId(Long usingOrgId) {
        this.usingOrgId = usingOrgId;
    }

    /**
     * @return 使用组织明细
     */
    public String getUsingOrgDetail() {
        return usingOrgDetail;
    }

    public void setUsingOrgDetail(String usingOrgDetail) {
        this.usingOrgDetail = usingOrgDetail;
    }

    /**
     * @return 使用人，值集：AAFM.ASSET_USER
     */
    public Long getUserPersonId() {
        return userPersonId;
    }

    public void setUserPersonId(Long userPersonId) {
        this.userPersonId = userPersonId;
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


    public AssetLinear getAssetLinear() {
        return assetLinear;
    }

    public void setAssetLinear(AssetLinear assetLinear) {
        this.assetLinear = assetLinear;
    }


    public Date getStartDateFrom() {
        return startDateFrom;
    }

    public void setStartDateFrom(Date startDateFrom) {
        this.startDateFrom = startDateFrom;
    }

    public Date getStartDateTo() {
        return startDateTo;
    }

    public void setStartDateTo(Date startDateTo) {
        this.startDateTo = startDateTo;
    }

    public Date getReceivedDateFrom() {
        return receivedDateFrom;
    }

    public void setReceivedDateFrom(Date receivedDateFrom) {
        this.receivedDateFrom = receivedDateFrom;
    }

    public Date getReceivedDateTo() {
        return receivedDateTo;
    }

    public void setReceivedDateTo(Date receivedDateTo) {
        this.receivedDateTo = receivedDateTo;
    }

    public String getSpecialAssetMeaning() {
        return specialAssetMeaning;
    }

    public void setSpecialAssetMeaning(String specialAssetMeaning) {
        this.specialAssetMeaning = specialAssetMeaning;
    }

    public String getNameRuleMeaning() {
        return nameRuleMeaning;
    }

    public void setNameRuleMeaning(String nameRuleMeaning) {
        this.nameRuleMeaning = nameRuleMeaning;
    }

    public String getAssetSourceTypeMeaning() {
        return assetSourceTypeMeaning;
    }

    public void setAssetSourceTypeMeaning(String assetSourceTypeMeaning) {
        this.assetSourceTypeMeaning = assetSourceTypeMeaning;
    }

    public String getWarrantyTypeMeaning() {
        return warrantyTypeMeaning;
    }

    public void setWarrantyTypeMeaning(String warrantyTypeMeaning) {
        this.warrantyTypeMeaning = warrantyTypeMeaning;
    }

    public String getWarrantyTypeRuleMeaning() {
        return warrantyTypeRuleMeaning;
    }

    public void setWarrantyTypeRuleMeaning(String warrantyTypeRuleMeaning) {
        this.warrantyTypeRuleMeaning = warrantyTypeRuleMeaning;
    }

    public Long getAttributeSetId() {
        return attributeSetId;
    }

    public void setAttributeSetId(Long attributeSetId) {
        this.attributeSetId = attributeSetId;
    }

    public String getowningOrgName() {
        return owningOrgName;
    }

    public void setowningOrgName(String owningOrgName) {
        this.owningOrgName = owningOrgName;
    }

    public String getUsingOrgIdMeaning() {
        return usingOrgIdMeaning;
    }

    public void setUsingOrgIdMeaning(String usingOrgIdMeaning) {
        this.usingOrgIdMeaning = usingOrgIdMeaning;
    }

    public String getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getSysStatusName() {
        return sysStatusName;
    }

    public void setSysStatusName(String sysStatusName) {
        this.sysStatusName = sysStatusName;
    }

    public String getUserStatusName() {
        return userStatusName;
    }

    public void setUserStatusName(String userStatusName) {
        this.userStatusName = userStatusName;
    }

    public String getowningPersonName() {
        return owningPersonName;
    }

    public void setowningPersonName(String owningPersonName) {
        this.owningPersonName = owningPersonName;
    }

    public String getParentAssetName() {
        return parentAssetName;
    }

    public void setParentAssetName(String parentAssetName) {
        this.parentAssetName = parentAssetName;
    }

    public String getAssetStatusName() {
        return assetStatusName;
    }

    public void setAssetStatusName(String assetStatusName) {
        this.assetStatusName = assetStatusName;
    }

    public String getAssetLocationName() {
        return assetLocationName;
    }

    public void setAssetLocationName(String assetLocationName) {
        this.assetLocationName = assetLocationName;
    }

    public String getUsingOrgName() {
        return usingOrgName;
    }

    public void setUsingOrgName(String usingOrgName) {
        this.usingOrgName = usingOrgName;
    }

    public String getAssetSpecialtyName() {
        return assetSpecialtyName;
    }

    public void setAssetSpecialtyName(String assetSpecialtyName) {
        this.assetSpecialtyName = assetSpecialtyName;
    }

    public String getUserPersonName() {
        return userPersonName;
    }

    public void setUserPersonName(String userPersonName) {
        this.userPersonName = userPersonName;
    }

    public String getAssetClassMeaning() {
        return assetClassMeaning;
    }

    public void setAssetClassMeaning(String assetClassMeaning) {
        this.assetClassMeaning = assetClassMeaning;
    }


    public String getSupplierOrgMeaning() {
        return supplierOrgMeaning;
    }

    public void setSupplierOrgMeaning(String supplierOrgMeaning) {
        this.supplierOrgMeaning = supplierOrgMeaning;
    }

    public String getAssetsSetName() {
        return assetsSetName;
    }

    public void setAssetsSetName(String assetsSetName) {
        this.assetsSetName = assetsSetName;
    }

    public Long getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(Long costCenterId) {
        this.costCenterId = costCenterId;
    }

    public String getCostCenterMeaning() {
        return costCenterMeaning;
    }

    public void setCostCenterMeaning(String costCenterMeaning) {
        this.costCenterMeaning = costCenterMeaning;
    }

    public Long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getAssetCriticalityMeaning() {
        return assetCriticalityMeaning;
    }

    public void setAssetCriticalityMeaning(String assetCriticalityMeaning) {
        this.assetCriticalityMeaning = assetCriticalityMeaning;
    }

    public String getWarrantyNotes() {
        return warrantyNotes;
    }

    public void setWarrantyNotes(String warrantyNotes) {
        this.warrantyNotes = warrantyNotes;
    }
}
