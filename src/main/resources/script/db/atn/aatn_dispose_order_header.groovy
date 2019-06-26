package script.db

databaseChangeLog(logicalFilePath: 'script/db/aatn_dispose_order_header.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-04-aatn_dispose_order_header") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'aatn_dispose_order_header_s', startValue:"1")
        }
        createTable(tableName: "aatn_dispose_order_header", remarks: "资产处置单头") {
            column(name: "dispose_header_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "transaction_type_id", type: "bigint(20)",  remarks: "事务类型，URL值集，AATN.ASSET_TRANSACTION_TYPE")  {constraints(nullable:"false")}  
            column(name: "process_status", type: "varchar(" + 30 * weight + ")",  remarks: "处理状态，值集")  {constraints(nullable:"false")}  
            column(name: "principal_person_id", type: "bigint(20)",  remarks: "负责人,员工id,值集：")   
            column(name: "dispose_num", type: "varchar(" + 30 * weight + ")",  remarks: "资产处置单编号")  {constraints(nullable:"false")}  
            column(name: "title_overview", type: "varchar(" + 240 * weight + ")",  remarks: "标题概述")   
            column(name: "plan_start_date", type: "date",  remarks: "计划执行日期")   
            column(name: "plan_end_date", type: "date",  remarks: "计划完成日期")   
            column(name: "description", type: "varchar(" + 240 * weight + ")",  remarks: "描述")   
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"dispose_num,tenant_id",tableName:"aatn_dispose_order_header",constraintName: "aatn_dispose_order_header_u1")
    }
}