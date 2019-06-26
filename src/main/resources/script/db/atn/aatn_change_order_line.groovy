package script.db

databaseChangeLog(logicalFilePath: 'script/db/aatn_change_order_line.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-04-aatn_change_order_line") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'aatn_change_order_line_s', startValue:"1")
        }
        createTable(tableName: "aatn_change_order_line", remarks: "资产信息变更单行") {
            column(name: "change_line_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "change_header_id", type: "bigint(20)",  remarks: "处置单头ID")  {constraints(nullable:"false")}  
            column(name: "asset_id", type: "bigint(20)",  remarks: "设备/资产ID")  {constraints(nullable:"false")}  
            column(name: "line_num", type: "int(10)",  remarks: "资产信息变更行编号")  {constraints(nullable:"false")}  
            column(name: "process_status", type: "varchar(" + 30 * weight + ")",  remarks: "处理状态，值集")  {constraints(nullable:"false")}  
            column(name: "current_asset_status_id", type: "bigint(20)",  remarks: "当前资产状态")   
            column(name: "target_asset_status_id", type: "bigint(20)",  remarks: "目标资产状态")   
            column(name: "current_location_id", type: "bigint(20)",  remarks: "当前位置")   
            column(name: "target_location_id", type: "bigint(20)",  remarks: "目标位置")   
            column(name: "current_owning_person_id", type: "bigint(20)",  remarks: "当前所属人")   
            column(name: "target_owning_person_id", type: "bigint(20)",  remarks: "目标所属人")   
            column(name: "current_using_person_id", type: "bigint(20)",  remarks: "当前使用人")   
            column(name: "target_using_person_id", type: "bigint(20)",  remarks: "目标使用人")   
            column(name: "description", type: "varchar(" + 240 * weight + ")",  remarks: "描述")  {constraints(nullable:"false")}  
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"change_header_id,line_num,tenant_id",tableName:"aatn_change_order_line",constraintName: "aatn_change_order_line_u1")
    }
}