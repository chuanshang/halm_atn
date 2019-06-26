package org.hzero.halm.atn.infra.constant;

/**
 * @author WangSen
 * @Description
 * @date: 2019/3/25 0025 10:31
 */
public enum AatnHandoverFields {

    ASSET_NAME("asset name","资产状态","current_asset_status","target_asset_status"),
    OWNING_PERSON("owning person","所属人","current_asset_status","target_asset_status"),
    USING_PERSON("using person","使用人","current_asset_status","target_asset_status");
    private String enName;
    private String zhName;
    private String currentName;
    private String targetName;

    AatnHandoverFields(String enName, String zhName, String currentName, String targetName) {
        this.enName = enName;
        this.zhName = zhName;
        this.currentName = currentName;
        this.targetName = targetName;
    }

    public String getEnName() {
        return enName;
    }

    public String getZhName() {
        return zhName;
    }

    public String getCurrentName() {
        return currentName;
    }

    public String getTargetName() {
        return targetName;
    }
}
