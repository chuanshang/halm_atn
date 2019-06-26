package script.db

databaseChangeLog(logicalFilePath: 'script/db/aafm_transaction_type_fields.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-04-aafm_transaction_type_fields") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'aafm_transaction_type_fields_s', startValue:"1")
        }
        createTable(tableName: "aafm_transaction_type_fields", remarks: "资产事务处理类型扩展控制字段") {
            column(name: "field_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "transation_type_id", type: "int(20)",  remarks: "事务处理类型ID")  {constraints(nullable:"false")}  
            column(name: "field_column", type: "varchar(" + 30 * weight + ")",  remarks: "字段")  {constraints(nullable:"false")}  
            column(name: "field_type", type: "varchar(" + 30 * weight + ")",  remarks: "类型，独立值集：AAFM.ASSET_COLUMN_PROPERTY")  {constraints(nullable:"false")}  
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"transation_type_id,tenant_id,field_column",tableName:"aafm_transaction_type_fields",constraintName: "aafm_transaction_type_fields_u1")
    }
}