package script.db

databaseChangeLog(logicalFilePath: 'script/db/aatn_dispose_order_line.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-04-aatn_dispose_order_line") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'aatn_dispose_order_line_s', startValue:"1")
        }
        createTable(tableName: "aatn_dispose_order_line", remarks: "资产处置单行") {
            column(name: "dispose_line_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "dispose_header_id", type: "bigint(20)",  remarks: "处置单头ID")  {constraints(nullable:"false")}  
            column(name: "asset_id", type: "bigint(20)",  remarks: "设备/资产ID")  {constraints(nullable:"false")}  
            column(name: "line_num", type: "int(10)",  remarks: "")  {constraints(nullable:"false")}  
            column(name: "process_status", type: "varchar(" + 30 * weight + ")",  remarks: "处理状态，值集")  {constraints(nullable:"false")}  
            column(name: "current_asset_status_id", type: "bigint(20)",  remarks: "当前资产状态")   
            column(name: "target_asset_status_id", type: "bigint(20)",  remarks: "目标资产状态")   
            column(name: "dispose_type_code", type: "varchar(" + 30 * weight + ")",  remarks: "资产处置类型")   
            column(name: "dispose_person_id", type: "bigint(20)",  remarks: "处置人员")   
            column(name: "dispose_price", type: "decimal(20,10)",  remarks: "处置价格")   
            column(name: "dispose_cost", type: "decimal(20,10)",  remarks: "处置成本")   
            column(name: "dispose_rate", type: "int(3)",  remarks: "处置税率")   
            column(name: "dispose_income", type: "decimal(20,10)",  remarks: "处置收益")   
            column(name: "description", type: "varchar(" + 240 * weight + ")",  remarks: "描述")   
            column(name: "remark", type: "varchar(" + 240 * weight + ")",  remarks: "备注")   
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

    }
}