package script.db

databaseChangeLog(logicalFilePath: 'script/db/aatn_transaction_history_detail.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-04-aatn_transaction_history_detail") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'aatn_transaction_history_detail_s', startValue:"1")
        }
        createTable(tableName: "aatn_transaction_history_detail", remarks: "资产字段历史明细") {
            column(name: "history_detail_id", type: "bigint(20)", autoIncrement: true ,   remarks: "资产字段事务变化明细")  {constraints(primaryKey: true)} 
            column(name: "transaction_history_id", type: "bigint(20)",  remarks: "资产信息主键ID")  {constraints(nullable:"false")}  
            column(name: "field_name", type: "varchar(" + 30 * weight + ")",  remarks: "发生改变的字段")  {constraints(nullable:"false")}  
            column(name: "field_value", type: "varchar(" + 30 * weight + ")",  remarks: "改变的字段值")  {constraints(nullable:"false")}  
            column(name: "occur_time", type: "date",  remarks: "发生改变的时间")  {constraints(nullable:"false")}  
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

    }
}