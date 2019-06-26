package script.db

databaseChangeLog(logicalFilePath: 'script/db/arcv_acceptance_asset.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-26-arcv_acceptance_asset") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'arcv_acceptance_asset_s', startValue:"1")
        }
        createTable(tableName: "arcv_acceptance_asset", remarks: "验收单资产明细") {
            column(name: "acceptance_asset_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "acceptance_line_id", type: "bigint(20)",  remarks: "验收单行id")  {constraints(nullable:"false")}  
            column(name: "asset_num", type: "varchar(" + 30 * weight + ")",  remarks: "资产编号")
            column(name: "asset_class_id", type: "bigint(20)",  remarks: "产品类别/资产分类，值集：AMDM.ASSET_CLASS")  {constraints(nullable:"false")}  
            column(name: "assets_set_id", type: "bigint(20)",  remarks: "产品组/资产组，值集：AMDM_ASSET_SET")  {constraints(nullable:"false")}  
            column(name: "supplier_header_id", type: "bigint(20)",  remarks: "供应商id")   
            column(name: "brand", type: "varchar(" + 120 * weight + ")",  remarks: "品牌/厂商")   
            column(name: "model", type: "varchar(" + 120 * weight + ")",  remarks: "规格型号")   
            column(name: "asset_status_id", type: "bigint(20)",  remarks: "资产状态，值集：AAFM.ASSET_STATUS")   
            column(name: "asset_location_id", type: "bigint(20)",  remarks: "资产位置，值集：AAFM.ASSET_LOCATION")   
            column(name: "owning_org_id", type: "bigint(20)",  remarks: "所属组织，值集：AAFM.ASSET_ORG")   
            column(name: "using_org_id", type: "bigint(20)",  remarks: "使用组织，值集：AAFM.ASSET_ORG")   
            column(name: "owning_person_id", type: "bigint(20)",  remarks: "资产管理员，值集：AAFM.ASSET_MANAGER")   
            column(name: "user_person_id", type: "bigint(20)",  remarks: "使用人，值集：AAFM.ASSET_USER")   
            column(name: "cost_center_id", type: "varchar(" + 30 * weight + ")",  remarks: "成本中心，值集：AAFM.ASSET_COST_CENTER")
            column(name: "original_cost", type: "decimal(20,10)",  remarks: "获取价格")
            column(name: "parent_asset_id", type: "bigint(20)",  remarks: "父资产，值集：AAFM.ASSET")
            column(name: "transfer_fixed_code", type: "varchar(" + 30 * weight + ")",  remarks: "是否转固，独立值集（ARCV.TRANSFER_FIXED）")  {constraints(nullable:"false")}
            column(name: "complete_flag", type: "tinyint(1)",  remarks: "资产信息是否完整")  {constraints(nullable:"false")}  
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"asset_num,tenant_id",tableName:"arcv_acceptance_asset",constraintName: "arcv_acceptance_asset_u1")
    }
}