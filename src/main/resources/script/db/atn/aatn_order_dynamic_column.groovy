package script.db

databaseChangeLog(logicalFilePath: 'script/db/aatn_order_dynamic_column.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-04-aatn_order_dynamic_column") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'aatn_order_dynamic_column_s', startValue:"1")
        }
        createTable(tableName: "aatn_order_dynamic_column", remarks: "资产事务处理单据动态字段") {
            column(name: "dynamic_column_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "order_header_id", type: "bigint(20)",  remarks: "单据头id")  {constraints(nullable:"false")}  
            column(name: "order_line_id", type: "bigint(20)",  remarks: "单据行id")  {constraints(nullable:"false")}  
            column(name: "order_type_code", type: "varchar(" + 30 * weight + ")",  remarks: "单据类型")  {constraints(nullable:"false")}  
            column(name: "current_table_name", type: "varchar(" + 30 * weight + ")",  remarks: "当前表名")  {constraints(nullable:"false")}  
            column(name: "current_column_name", type: "varchar(" + 30 * weight + ")",  remarks: "当前字段名")  {constraints(nullable:"false")}  
            column(name: "current_column_desc", type: "varchar(" + 240 * weight + ")",  remarks: "当前字段含义")   
            column(name: "current_column_value", type: "varchar(" + 30 * weight + ")",  remarks: "当前字段值")   
            column(name: "target_column_type", type: "varchar(" + 30 * weight + ")",  remarks: "目标字段属性")  {constraints(nullable:"false")}  
            column(name: "target_column_value", type: "varchar(" + 30 * weight + ")",  remarks: "目标字段值")   
            column(name: "target_column_desc", type: "varchar(" + 240 * weight + ")",  remarks: "目标字段含义")   
            column(name: "description", type: "varchar(" + 240 * weight + ")",  remarks: "备注")   
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

    }
}