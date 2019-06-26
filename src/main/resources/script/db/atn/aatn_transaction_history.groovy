package script.db

databaseChangeLog(logicalFilePath: 'script/db/aatn_transaction_history.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-04-aatn_transaction_history") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'aatn_transaction_history_s', startValue:"1")
        }
        createTable(tableName: "aatn_transaction_history", remarks: "资产处理历史信息") {
            column(name: "transaction_history_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "asset_id", type: "bigint(20)",  remarks: "资产ID")  {constraints(nullable:"false")}  
            column(name: "transaction_header_id", type: "bigint(20)",  remarks: "事务处理头id")  {constraints(nullable:"false")}  
            column(name: "transaction_line_id", type: "bigint(20)",  remarks: "事务处理行id")  {constraints(nullable:"false")}  
            column(name: "transaction_type_id", type: "bigint(20)",  remarks: "事务处理类型id")  {constraints(nullable:"false")}  
            column(name: "process_status_record", type: "varchar(" + 240 * weight + ")",  remarks: "资产处理状态记录，拼接json数据。")  {constraints(nullable:"false")}  
            column(name: "process_time_record", type: "varchar(" + 240 * weight + ")",  remarks: "资产处理时间记录，使用时间戳拼接成json数据。")  {constraints(nullable:"false")}  
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

    }
}