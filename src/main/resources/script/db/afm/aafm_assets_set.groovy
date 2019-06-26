package script.db

databaseChangeLog(logicalFilePath: 'script/db/aafm_assets_set.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-04-aafm_assets_set") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'aafm_assets_set_s', startValue:"1")
        }
        createTable(tableName: "aafm_assets_set", remarks: "资产组") {
            column(name: "assets_set_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "assets_set_num", type: "varchar(" + 30 * weight + ")",  remarks: "编号，必输，租户内唯一 （根据启用编号规则状态，决定是否为必输/唯一，为1时，只读；为0时，必输且唯一）")  {constraints(nullable:"false")}  
            column(name: "assets_set_name", type: "varchar(" + 60 * weight + ")",  remarks: "名称，必输，租户内唯一")  {constraints(nullable:"false")}  
            column(name: "asset_name", type: "varchar(" + 60 * weight + ")",  remarks: "资产名称")  {constraints(nullable:"false")}
            column(name: "special_asset_code", type: "varchar(" + 20 * weight + ")",  remarks: "所属特殊资产，值集：AMDM.SPECIAL_ASSET")
            column(name: "brand", type: "varchar(" + 150 * weight + ")",  remarks: "品牌/厂商")   
            column(name: "specifications", type: "varchar(" + 480 * weight + ")",  remarks: "规格/型号")   
            column(name: "asset_class_id", type: "bigint(20)",  remarks: "产品类别/资产分类，值集：AMDM.ASSET_CLASS 必输")  {constraints(nullable:"false")}
            column(name: "url", type: "varchar(" + 480 * weight + ")",  remarks: "URL")   
            column(name: "asset_criticality", type: "varchar(" + 30 * weight + ")",  remarks: "资产重要性，值集：AMAMD.ASSET_CRITICALITY")   
            column(name: "attribute_set_id", type: "bigint(20)",  remarks: "属性组，值集： AMDM.ATTRIBUTE_SET")   
            column(name: "special_asset_class_id", type: "bigint(20)",  remarks: "专业资产分类，值集：AMDM.SPECIAL_ASSET_CLASS")
            column(name: "fixed_asset_type_code", type: "varchar(" + 30 * weight + ")",  remarks: "固定资产类别，值集：AMDM.FIXED_ASSET_TYPE")   
            column(name: "uom_id", type: "bigint(20)",  remarks: "单位Id，值集：AMDM.UOM")   
            column(name: "description", type: "varchar(" + 120 * weight + ")",  remarks: "说明")   
            column(name: "icon", type: "varchar(" + 20 * weight + ")",  remarks: "图标")   
            column(name: "code_rule", type: "varchar(" + 30 * weight + ")",  remarks: "编号规则，值集：AMDM.CODE_RULE （根据启用编号规则状态，决定是否为必输，为1时，必输；为0时，清空原始值")   
            column(name: "trade_in_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "是否可在合同中交易，默认：0")  {constraints(nullable:"false")}  
            column(name: "only_maint_sites_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "限用于分配的服务区域，默认：1")  {constraints(nullable:"false")}  
            column(name: "maintain_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "是否可维修，默认：1")  {constraints(nullable:"false")}  
            column(name: "product_url", type: "varchar(" + 480 * weight + ")",  remarks: "产品图片")   
            column(name: "nameplate_rule_code", type: "varchar(" + 30 * weight + ")",  remarks: "资产标签/铭牌规则，值集：AMDM.NAMEPLATE_RULE 必输")  {constraints(nullable:"false")}  
            column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "启用标记，默认：1（启用）")  {constraints(nullable:"false")}  
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "product_name", type: "varchar(" + 120 * weight + ")",  remarks: "产品名称")   

        }

        addUniqueConstraint(columnNames:"assets_set_num,tenant_id",tableName:"aafm_assets_set",constraintName: "amdm_assets_set_u1")
        addUniqueConstraint(columnNames:"assets_set_name,tenant_id",tableName:"aafm_assets_set",constraintName: "amdm_assets_set_u2")
    }
}