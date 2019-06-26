package script.db

databaseChangeLog(logicalFilePath: 'script/db/aafm_asset_dynamic_column.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-04-aafm_asset_dynamic_column") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'aafm_asset_dynamic_column_s', startValue:"1")
        }
        createTable(tableName: "aafm_asset_dynamic_column", remarks: "资产动态字段配置") {
            column(name: "column_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "column_code", type: "varchar(" + 30 * weight + ")",  remarks: "字段，来自INFORMATION_SCHEMA.COLUMNS")  {constraints(nullable:"false")}  
            column(name: "column_name", type: "varchar(" + 60 * weight + ")",  remarks: "字段名称，作为动态字段的显示名称")  {constraints(nullable:"false")}  
            column(name: "column_class", type: "varchar(" + 30 * weight + ")",  remarks: "分类，必输，独立值集（BASIC/基本信息、TRACK/跟踪信息）")  {constraints(nullable:"false")}  
            column(name: "desc_code", type: "varchar(" + 30 * weight + ")",  remarks: "描述名称")  {constraints(nullable:"false")}  
            column(name: "desc_source_type", type: "varchar(" + 30 * weight + ")",  remarks: "描述来源类型，独立值集（TABLE/表、IDP/独立值集）")  {constraints(nullable:"false")}  
            column(name: "desc_source", type: "varchar(" + 30 * weight + ")",  remarks: "描述来源，填写表名或者值集名")  {constraints(nullable:"false")}  
            column(name: "lov_name", type: "varchar(" + 30 * weight + ")",  remarks: "值集，必输，SQL值集，来自HZERO_PLATFORM.HPFM_LOV")   
            column(name: "lov_type", type: "varchar(" + 30 * weight + ")",  remarks: "取值类型，独立值集（SQL、IDP、URL【待前端补充】）")  {constraints(nullable:"false")}  
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"column_code,tenant_id",tableName:"aafm_asset_dynamic_column",constraintName: "aafm_asset_dynamic_column_u1")
    }
}