package org.hzero.halm.afm.api.dto;

import io.choerodon.mybatis.domain.AuditDomain;
import io.swagger.annotations.ApiModelProperty;
import org.hzero.halm.afm.domain.entity.AttributeLine;
import java.util.List;

public class AttributeSetDTO extends AuditDomain {

    /**
     * 属性组ID
     */
    @ApiModelProperty("表ID，主键，供其他表做外键")
    private Long attributeSetId;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称，必输，租户内唯一")
    private String attributeSetName;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
    /**
     * 启用标识
     */
    @ApiModelProperty(value = "启用标识")
    private Integer enabledFlag;

    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    /**
     * 属性组行
     */
    private List<AttributeLine> attributeLinesList;

    /**
     * 所勾选的条数
     */
    private String attributeSetIds;
    private List<Long> attributeSetIdList;

    /**
     * @return 表ID，主键，供其他表做外键
     */
    public Long getAttributeSetId() {
        return attributeSetId;
    }

    public void setAttributeSetId(Long attributeSetId) {
        this.attributeSetId = attributeSetId;
    }
    /**
     * @return 名称，必输，租户内唯一
     */
    public String getAttributeSetName() {
        return attributeSetName;
    }

    public void setAttributeSetName(String attributeSetName) {
        this.attributeSetName = attributeSetName;
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
     * @return 启用标识
     */
    public Integer getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
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


    public List<AttributeLine> getAttributeLinesList() {
        return attributeLinesList;
    }

    public void setAttributeLinesList(List<AttributeLine> attributeLinesList) {
        this.attributeLinesList = attributeLinesList;
    }

    public String getAttributeSetIds() {
        return attributeSetIds;
    }

    public void setAttributeSetIds(String attributeSetIds) {
        this.attributeSetIds = attributeSetIds;
    }

    public List<Long> getAttributeSetIdList() {
        return attributeSetIdList;
    }

    public void setAttributeSetIdList(List<Long> attributeSetIdList) {
        this.attributeSetIdList = attributeSetIdList;
    }

}