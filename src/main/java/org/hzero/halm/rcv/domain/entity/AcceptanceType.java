package org.hzero.halm.rcv.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;

import java.util.List;

import io.choerodon.mybatis.domain.AuditDomain;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 验收单类型
 *
 * @author zhiguang.guo@hand-china.com 2019-04-17 17:55:24
 */
@ApiModel("验收单类型")
@VersionAudit
@ModifyAudit
@Table(name = "arcv_acceptance_type")
@ExcelSheet(zh = "验收单类型",en = "Acceptance Type")
public class AcceptanceType extends AuditDomain {

    public static final String FIELD_ACCEPTANCE_TYPE_ID = "acceptanceTypeId";
    public static final String FIELD_CODE_RULE = "codeRule";
    public static final String FIELD_ACCEPTANCE_TYPE_CODE = "acceptanceTypeCode";
    public static final String FIELD_CODE = "code";
    public static final String FIELD_FULL_NAME = "fullName";
    public static final String FIELD_SHORT_NAME = "shortName";
    public static final String FIELD_PROJECT_FLAG = "projectFlag";
    public static final String FIELD_BUDGET_FLAG = "budgetFlag";
    public static final String FIELD_TRANSFER_FIXED_CODE = "transferFixedCode";
    public static final String FIELD_APPROVE_FLOW_FLAG = "approveFlowFlag";
    public static final String FIELD_TAG = "tag";
    public static final String FIELD_COMPLETE_ASSET_STATUS_ID = "completeAssetStatusId";
    public static final String FIELD_TRANSFER_INTERFACE_FLAG = "transferInterfaceFlag";
    public static final String FIELD_DIRECTLY_COMPLETE_FLAG = "directlyCompleteFlag";
    public static final String FIELD_IN_CONTRACT_FLAG = "inContractFlag";
    public static final String FIELD_CREATE_FA_FLAG = "createFaFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_DESCRIPTION = "description";
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
    private Long acceptanceTypeId;
    @ApiModelProperty(value = "编码规则，SQL值集（HALM.CODE_RULE）")
    private String codeRule;
    @ApiModelProperty(value = "验收基础类型，独立值集（开发人员补充值集名称）")
	@LovValue(value = "ARCV.ACCEPTANCE_TYPE", meaningField = "acceptanceTypeCodeMeaning")
    @NotBlank
    private String acceptanceTypeCode;
    @ApiModelProperty(value = "代码")
    @NotBlank
    @ExcelColumn(zh = "代码", en = "Code")
    private String code;
    @ApiModelProperty(value = "事件完整名称")
    @NotBlank
    @ExcelColumn(zh = "事件完整名称", en = "Full Name")
    private String fullName;
    @ApiModelProperty(value = "事件短名称")
    @NotBlank
    @ExcelColumn(zh = "事件短名称", en = "Short Name")
    private String shortName;
    @ApiModelProperty(value = "是否来源于项目")
	@LovValue(value = "HPFM.FLAG", meaningField = "projectFlagMeaning")
    private Integer projectFlag;
    @ApiModelProperty(value = "是否有对应预算")
	@LovValue(value = "HPFM.FLAG", meaningField = "budgetFlagMeaning")
    private Integer budgetFlag;
    @ApiModelProperty(value = "是否转固，独立值集（开发人员补充值集名称）")
	@LovValue(value = "ARCV.TRANSFER_FIXED", meaningField = "transferFixedCodeMeaning")
    @NotBlank
    private String transferFixedCode;
    @ApiModelProperty(value = "是否有审批流")
	@LovValue(value = "HPFM.FLAG", meaningField = "approveFlowFlagMeaning")
    private Integer approveFlowFlag;
    @ApiModelProperty(value = "标记")
    @ExcelColumn(zh = "标记", en = "Tag")
    private String tag;
    @ApiModelProperty(value = "验收完成时默认的资产状态，URL值集（开发人员补充值集名称）")
    private Long completeAssetStatusId;
    @ApiModelProperty(value = "是否触发外部备品备件传输接口程序")
	@LovValue(value = "HPFM.FLAG", meaningField = "transferInterfaceFlagMeaning")
    private Integer transferInterfaceFlag;
    @ApiModelProperty(value = "是否直接完成物料事务处理")
	@LovValue(value = "HPFM.FLAG", meaningField = "directlyCompleteFlagMeaning")
    private Integer directlyCompleteFlag;
    @ApiModelProperty(value = "是否合同内收货")
	@LovValue(value = "HPFM.FLAG", meaningField = "inContractFlagMeaning")
    private Integer inContractFlag;
    @ApiModelProperty(value = "是否始终生成无价值的FA卡片")
	@LovValue(value = "HPFM.FLAG", meaningField = "createFaFlagMeaning")
    private Integer createFaFlag;
    @ApiModelProperty(value = "是否启用")
	@LovValue(value = "HPFM.ENABLED_FLAG", meaningField = "enabledFlagMeaning")
    private Integer enabledFlag;
    @ApiModelProperty(value = "描述")
    @ExcelColumn(zh = "描述", en = "Description")
    private String description;
    @ApiModelProperty(value = "租户ID")
    @NotNull
    private Long tenantId;

	//
    // 非数据库字段
    // ------------------------------------------------------------------------------

	/**
	 * 获取独立值集的中文含义
	 */
	@Transient
	@ExcelColumn(zh = "是否转固", en = "Transfer Fixed")
	private String transferFixedCodeMeaning;
	@Transient
	@ExcelColumn(zh = "验收基础类型", en = "Acceptance Type")
	private String acceptanceTypeCodeMeaning;
	@Transient
	@ExcelColumn(zh = "是否始终生成无价值的FA卡片", en = "Create Fa Flag")
	private String createFaFlagMeaning;
	@Transient
	@ExcelColumn(zh = "是否合同内收货", en = "In Contract Flag")
	private String inContractFlagMeaning;
	@Transient
	@ExcelColumn(zh = "是否直接完成物料事务处理", en = "Directly Complete Flag")
	private String directlyCompleteFlagMeaning;
	@Transient
	@ExcelColumn(zh = "是否触发外部备品备件传输接口程序", en = "Transfer Interface Flag")
	private String transferInterfaceFlagMeaning;
	@Transient
	@ExcelColumn(zh = "是否有审批流", en = "Approve Flow Flag")
	private String approveFlowFlagMeaning;
	@Transient
	@ExcelColumn(zh = "是否有对应预算", en = "Budget Flag")
	private String budgetFlagMeaning;
	@Transient
	@ExcelColumn(zh = "是否来源于项目", en = "Project Flag")
	private String projectFlagMeaning;
	@Transient
	@ExcelColumn(zh = "是否启用", en = "Enabled Flag")
	private String enabledFlagMeaning;
    /**
     * 获取url，sql值集的中文含义
     */
	@Transient
    @ExcelColumn(zh = "资产状态", en = "Asset Status")
	private String assetStatusMeaning;
    @Transient
    @ExcelColumn(zh = "编码规则", en = "Code Rule")
    private String codeRuleMeaning;
	/**
	 * 明细页面的检索使用
	 */
	@Transient
	@ApiModelProperty(value = "明细列表的查询条件")
	private String condition;
	@Transient
	private List<String> acceptanceTypeCodeList;
	@Transient
	private List<String> enabledFlagList;
	@Transient
	@ApiModelProperty(value = "多基础类型查询条件")
	private String accTypeCondition;
	@Transient
	private List<String> accTypeCodeList;
    //
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return 表ID，主键，供其他表做外键
     */
	public Long getAcceptanceTypeId() {
		return acceptanceTypeId;
	}

	public void setAcceptanceTypeId(Long acceptanceTypeId) {
		this.acceptanceTypeId = acceptanceTypeId;
	}
    /**
     * @return 编码规则，SQL值集（HALM.CODE_RULE）
     */
	public String getCodeRule() {
		return codeRule;
	}

	public void setCodeRule(String codeRule) {
		this.codeRule = codeRule;
	}
    /**
     * @return 验收基础类型，独立值集（开发人员补充值集名称）
     */
	public String getAcceptanceTypeCode() {
		return acceptanceTypeCode;
	}

	public void setAcceptanceTypeCode(String acceptanceTypeCode) {
		this.acceptanceTypeCode = acceptanceTypeCode;
	}
    /**
     * @return 代码
     */
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
    /**
     * @return 事件完整名称
     */
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
    /**
     * @return 事件短名称
     */
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
    /**
     * @return 是否来源于项目
     */
	public Integer getProjectFlag() {
		return projectFlag;
	}

	public void setProjectFlag(Integer projectFlag) {
		this.projectFlag = projectFlag;
	}
    /**
     * @return 是否有对应预算
     */
	public Integer getBudgetFlag() {
		return budgetFlag;
	}

	public void setBudgetFlag(Integer budgetFlag) {
		this.budgetFlag = budgetFlag;
	}
    /**
     * @return 是否转固，独立值集（开发人员补充值集名称）
     */
	public String getTransferFixedCode() {
		return transferFixedCode;
	}

	public void setTransferFixedCode(String transferFixedCode) {
		this.transferFixedCode = transferFixedCode;
	}
    /**
     * @return 是否有审批流
     */
	public Integer getApproveFlowFlag() {
		return approveFlowFlag;
	}

	public void setApproveFlowFlag(Integer approveFlowFlag) {
		this.approveFlowFlag = approveFlowFlag;
	}
    /**
     * @return 标记
     */
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
    /**
     * @return 验收完成时默认的资产状态，URL值集（开发人员补充值集名称）
     */
	public Long getCompleteAssetStatusId() {
		return completeAssetStatusId;
	}

	public void setCompleteAssetStatusId(Long completeAssetStatusId) {
		this.completeAssetStatusId = completeAssetStatusId;
	}
    /**
     * @return 是否触发外部备品备件传输接口程序
     */
	public Integer getTransferInterfaceFlag() {
		return transferInterfaceFlag;
	}

	public void setTransferInterfaceFlag(Integer transferInterfaceFlag) {
		this.transferInterfaceFlag = transferInterfaceFlag;
	}
    /**
     * @return 是否直接完成物料事务处理
     */
	public Integer getDirectlyCompleteFlag() {
		return directlyCompleteFlag;
	}

	public void setDirectlyCompleteFlag(Integer directlyCompleteFlag) {
		this.directlyCompleteFlag = directlyCompleteFlag;
	}
    /**
     * @return 是否合同内收货
     */
	public Integer getInContractFlag() {
		return inContractFlag;
	}

	public void setInContractFlag(Integer inContractFlag) {
		this.inContractFlag = inContractFlag;
	}
    /**
     * @return 是否始终生成无价值的FA卡片
     */
	public Integer getCreateFaFlag() {
		return createFaFlag;
	}

	public void setCreateFaFlag(Integer createFaFlag) {
		this.createFaFlag = createFaFlag;
	}
    /**
     * @return 是否启用
     */
	public Integer getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(Integer enabledFlag) {
		this.enabledFlag = enabledFlag;
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
     * @return 租户ID
     */
	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public String getAssetStatusMeaning() {
		return assetStatusMeaning;
	}

	public void setAssetStatusMeaning(String assetStatusMeaning) {
		this.assetStatusMeaning = assetStatusMeaning;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public List<String> getAcceptanceTypeCodeList() {
		return acceptanceTypeCodeList;
	}

	public void setAcceptanceTypeCodeList(List<String> acceptanceTypeCodeList) {
		this.acceptanceTypeCodeList = acceptanceTypeCodeList;
	}

    public String getCodeRuleMeaning() {
        return codeRuleMeaning;
    }

    public void setCodeRuleMeaning(String codeRuleMeaning) {
        this.codeRuleMeaning = codeRuleMeaning;
    }

	public String getCreateFaFlagMeaning() {
		return createFaFlagMeaning;
	}

	public void setCreateFaFlagMeaning(String createFaFlagMeaning) {
		this.createFaFlagMeaning = createFaFlagMeaning;
	}

	public String getInContractFlagMeaning() {
		return inContractFlagMeaning;
	}

	public void setInContractFlagMeaning(String inContractFlagMeaning) {
		this.inContractFlagMeaning = inContractFlagMeaning;
	}

	public String getDirectlyCompleteFlagMeaning() {
		return directlyCompleteFlagMeaning;
	}

	public void setDirectlyCompleteFlagMeaning(String directlyCompleteFlagMeaning) {
		this.directlyCompleteFlagMeaning = directlyCompleteFlagMeaning;
	}

	public String getTransferInterfaceFlagMeaning() {
		return transferInterfaceFlagMeaning;
	}

	public void setTransferInterfaceFlagMeaning(String transferInterfaceFlagMeaning) {
		this.transferInterfaceFlagMeaning = transferInterfaceFlagMeaning;
	}

	public String getApproveFlowFlagMeaning() {
		return approveFlowFlagMeaning;
	}

	public void setApproveFlowFlagMeaning(String approveFlowFlagMeaning) {
		this.approveFlowFlagMeaning = approveFlowFlagMeaning;
	}

	public String getBudgetFlagMeaning() {
		return budgetFlagMeaning;
	}

	public void setBudgetFlagMeaning(String budgetFlagMeaning) {
		this.budgetFlagMeaning = budgetFlagMeaning;
	}

	public String getProjectFlagMeaning() {
		return projectFlagMeaning;
	}

	public void setProjectFlagMeaning(String projectFlagMeaning) {
		this.projectFlagMeaning = projectFlagMeaning;
	}

	public String getEnabledFlagMeaning() {
		return enabledFlagMeaning;
	}

	public void setEnabledFlagMeaning(String enabledFlagMeaning) {
		this.enabledFlagMeaning = enabledFlagMeaning;
	}

	public String getTransferFixedCodeMeaning() {
		return transferFixedCodeMeaning;
	}

	public void setTransferFixedCodeMeaning(String transferFixedCodeMeaning) {
		this.transferFixedCodeMeaning = transferFixedCodeMeaning;
	}

	public String getAcceptanceTypeCodeMeaning() {
		return acceptanceTypeCodeMeaning;
	}

	public void setAcceptanceTypeCodeMeaning(String acceptanceTypeCodeMeaning) {
		this.acceptanceTypeCodeMeaning = acceptanceTypeCodeMeaning;
	}

	public List<String> getEnabledFlagList() {
		return enabledFlagList;
	}

	public void setEnabledFlagList(List<String> enabledFlagList) {
		this.enabledFlagList = enabledFlagList;
	}

	public String getAccTypeCondition() {
		return accTypeCondition;
	}

	public void setAccTypeCondition(String accTypeCondition) {
		this.accTypeCondition = accTypeCondition;
	}

	public List<String> getAccTypeCodeList() {
		return accTypeCodeList;
	}

	public void setAccTypeCodeList(List<String> accTypeCodeList) {
		this.accTypeCodeList = accTypeCodeList;
	}
}
