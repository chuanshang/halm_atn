package script.db

databaseChangeLog(logicalFilePath: 'script/db/aatn_handover_order_line.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-04-aatn_handover_order_line") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'aatn_handover_order_line_s', startValue:"1")
        }
        createTable(tableName: "aatn_handover_order_line", remarks: "资产移交归还单行") {
            column(name: "handover_line_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "handover_header_id", type: "bigint(20)",  remarks: "资产移交归还单头ID")  {constraints(nullable:"false")}  
            column(name: "line_num", type: "bigint(10)",  remarks: "事务处理行编号")  {constraints(nullable:"false")}  
            column(name: "asset_id", type: "bigint(20)",  remarks: "设备/资产ID")  {constraints(nullable:"false")}  
            column(name: "process_status", type: "varchar(" + 30 * weight + ")",  remarks: "处理状态，值集")  {constraints(nullable:"false")}  
            column(name: "description", type: "varchar(" + 240 * weight + ")",  remarks: "描述")  {constraints(nullable:"false")}  
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"handover_header_id,line_num,tenant_id",tableName:"aatn_handover_order_line",constraintName: "aatn_handover_order_line_u1")
    }
}