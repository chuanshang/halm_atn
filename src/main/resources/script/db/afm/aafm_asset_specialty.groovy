package script.db

databaseChangeLog(logicalFilePath: 'script/db/aafm_asset_specialty.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-04-aafm_asset_specialty") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'aafm_asset_specialty_s', startValue:"1")
        }
        createTable(tableName: "aafm_asset_specialty", remarks: "资产专业定义(头)") {
            column(name: "asset_specialty_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "asset_specialty_name", type: "varchar(" + 60 * weight + ")",  remarks: "专业分类名称，必输，同租户下唯一")  {constraints(nullable:"false")}  
            column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "是否启用，必输，默认：1")  {constraints(nullable:"false")}  
            column(name: "description", type: "varchar(" + 240 * weight + ")",  remarks: "描述")   
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"asset_specialty_name,tenant_id",tableName:"aafm_asset_specialty",constraintName: "amdm_asset_specialty_u1")
    }
}