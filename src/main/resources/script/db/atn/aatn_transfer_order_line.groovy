package script.db

databaseChangeLog(logicalFilePath: 'script/db/aatn_transfer_order_line.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-04-aatn_transfer_order_line") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'aatn_transfer_order_line_s', startValue:"1")
        }
        createTable(tableName: "aatn_transfer_order_line", remarks: "调拨转移单行") {
            column(name: "transfer_line_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "transfer_header_id", type: "bigint(20)",  remarks: "调拨单头ID")  {constraints(nullable:"false")}  
            column(name: "asset_id", type: "bigint(20)",  remarks: "设备/资产ID")  {constraints(nullable:"false")}  
            column(name: "process_status", type: "varchar(" + 30 * weight + ")",  remarks: "处理状态，值集")  {constraints(nullable:"false")}  
            column(name: "current_asset_status_id", type: "bigint(20)",  remarks: "当前资产状态")   
            column(name: "target_asset_status_id", type: "bigint(20)",  remarks: "目标资产状态")   
            column(name: "current_owning_org", type: "bigint(20)",  remarks: "当前所属组织")   
            column(name: "target_owning_org", type: "bigint(20)",  remarks: "目标所属组织")   
            column(name: "current_cost_center", type: "varchar(" + 30 * weight + ")",  remarks: "当前成本中心")   
            column(name: "target_cost_center", type: "varchar(" + 30 * weight + ")",  remarks: "目标成本中心")   
            column(name: "description", type: "varchar(" + 240 * weight + ")",  remarks: "备注")  {constraints(nullable:"false")}  
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

    }
}