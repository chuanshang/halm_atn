package script.db

databaseChangeLog(logicalFilePath: 'script/db/aatn_scrap_order_line.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-04-aatn_scrap_order_line") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'aatn_scrap_order_line_s', startValue:"1")
        }
        createTable(tableName: "aatn_scrap_order_line", remarks: "资产报废单行") {
            column(name: "scrap_line_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "scrap_header_id", type: "bigint(20)",  remarks: "报废单头ID")  {constraints(nullable:"false")}  
            column(name: "line_num", type: "int(10)",  remarks: "事务处理行号")   
            column(name: "asset_id", type: "bigint(20)",  remarks: "设备/资产ID")  {constraints(nullable:"false")}  
            column(name: "process_status", type: "varchar(" + 30 * weight + ")",  remarks: "处理状态，值集")  {constraints(nullable:"false")}  
            column(name: "current_asset_status_id", type: "bigint(20)",  remarks: "当前资产状态")  {constraints(nullable:"false")}  
            column(name: "target_asset_status_id", type: "bigint(20)",  remarks: "目标资产状态")   
            column(name: "current_location_id", type: "bigint(20)",  remarks: "当前位置")  {constraints(nullable:"false")}  
            column(name: "target_location_id", type: "bigint(20)",  remarks: "目标位置")   
            column(name: "original_cost", type: "int(10)",  remarks: "资产原值")
            column(name: "remain_cost", type: "int(10)",  remarks: "剩余价值")  {constraints(nullable:"false")}  
            column(name: "capitalization_date", type: "date",  remarks: "资本化日期")  {constraints(nullable:"false")}  
            column(name: "remain_depreciation_mouth", type: "int(3)",  remarks: "剩余折旧日期")  {constraints(nullable:"false")}  
            column(name: "scrap_type_code", type: "varchar(" + 30 * weight + ")",  remarks: "资产报废类型")   
            column(name: "dispose_type_code", type: "varchar(" + 30 * weight + ")",  remarks: "资产处置类型")   
            column(name: "description", type: "varchar(" + 240 * weight + ")",  remarks: "描述")  {constraints(nullable:"false")}  
            column(name: "remark", type: "varchar(" + 240 * weight + ")",  remarks: "备注")   
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"line_num,tenant_id,scrap_header_id",tableName:"aatn_scrap_order_line",constraintName: "aatn_scrap_order_line_u1")
    }
}