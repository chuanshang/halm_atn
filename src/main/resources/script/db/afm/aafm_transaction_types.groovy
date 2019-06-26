package script.db

databaseChangeLog(logicalFilePath: 'script/db/aafm_transaction_types.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-04-aafm_transaction_types") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'aafm_transaction_types_s', startValue:"1")
        }
        createTable(tableName: "aafm_transaction_types", remarks: "资产事务处理类型") {
            column(name: "transaction_type_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "basic_type_code", type: "varchar(" + 30 * weight + ")",  remarks: "基础类型，独立值集：AAFM.ASSET_TRANSACTION_TYPE")  {constraints(nullable:"false")}  
            column(name: "transaction_type_code", type: "varchar(" + 30 * weight + ")",  remarks: "代码")   
            column(name: "short_name", type: "varchar(" + 60 * weight + ")",  remarks: "事件短名称")  {constraints(nullable:"false")}  
            column(name: "long_name", type: "varchar(" + 240 * weight + ")",  remarks: "时间完整名称")  {constraints(nullable:"false")}  
            column(name: "parent_type_id", type: "int(20)",  remarks: "父类型ID")   
            column(name: "level_path", type: "varchar(" + 480 * weight + ")",  remarks: "层级结构的节点路径,递归父级id/本级id")  {constraints(nullable:"false")}  
            column(name: "level_number", type: "int(11)",  remarks: "当前所处层级")  {constraints(nullable:"false")}  
            column(name: "code_rule", type: "varchar(" + 30 * weight + ")",  remarks: "自动编号")   
            column(name: "need_twice_confirm", type: "varchar(" + 30 * weight + ")",  remarks: "是否需要2步确认,独立值集： AAFM.NEED_TWICE_CONFIRM")  {constraints(nullable:"false")}  
            column(name: "description", type: "varchar(" + 240 * weight + ")",  remarks: "描述")   
            column(name: "icon", type: "varchar(" + 20 * weight + ")",  remarks: "图标")   
            column(name: "tag", type: "varchar(" + 20 * weight + ")",  remarks: "标记")   
            column(name: "organization_scope", type: "varchar(" + 240 * weight + ")",  remarks: "组织范围，URL值集：AMDM.ORGANIZATION")   
            column(name: "status_scope", type: "varchar(" + 240 * weight + ")",  remarks: "状态范围，URL值集，AAFM.ASSET_STATUS")   
            column(name: "specialty_scope", type: "varchar(" + 240 * weight + ")",  remarks: "专业范围，URL值集，AAFM.ASSET_SPECIALTY")   
            column(name: "special_asset_flag", type: "tinyint(1)",  remarks: "仅针对特殊资产")  {constraints(nullable:"false")}  
            column(name: "special_asset", type: "varchar(" + 20 * weight + ")",  remarks: "特殊资产，值集：AMDM.SPECIAL_ASSET")   
            column(name: "check_target_org_flag", type: "tinyint(1)",  remarks: "是否检查目标使用组织暂挂状态")  {constraints(nullable:"false")}  
            column(name: "check_current_org_flag", type: "tinyint(1)",  remarks: "是否检查当前所属组织暂挂状态")  {constraints(nullable:"false")}  
            column(name: "cross_legal_flag", type: "tinyint(1)",  remarks: "更改所属组织时，是否跨法人单位")  {constraints(nullable:"false")}  
            column(name: "status_update_flag", type: "tinyint(1)",  remarks: "是否需要修改资产状态，默认0")  {constraints(nullable:"false")}  
            column(name: "target_asset_status_id", type: "bigint(20)",  remarks: "目标资产状态，URL值集：AAFM.ASSET_STATUS")   
            column(name: "inprocess_asset_status_id", type: "bigint(20)",  remarks: "过程中资产状态，URL值集：AAFM.ASSET_STATUS")   
            column(name: "target_asset_status_scope", type: "varchar(" + 240 * weight + ")",  remarks: "目标资产状态范围，URL值集：AAFM.ASSET_STATUS")   
            column(name: "enabled_flag", type: "tinyint(1)",  remarks: "是否启用，默认1")  {constraints(nullable:"false")}  
            column(name: "basic_column_flag", type: "tinyint(1)",  remarks: "涉及基本信息变更，默认0")   
            column(name: "attribute_column_flag", type: "tinyint(1)",  remarks: "涉及属性描述变更，默认0")   
            column(name: "tracking_management_column_flag", type: "tinyint(1)",  remarks: "涉及跟踪与管理的变更，默认0")   
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"transaction_type_code,tenant_id",tableName:"aafm_transaction_types",constraintName: "apfm_transaction_types_u1")
    }
}