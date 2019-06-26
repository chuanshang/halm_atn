package script.db

databaseChangeLog(logicalFilePath: 'script/db/aatn_handover_order_detail.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-04-aatn_handover_order_detail") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'aatn_handover_order_detail_s', startValue:"1")
        }
        createTable(tableName: "aatn_handover_order_detail", remarks: "资产移交归还明细") {
            column(name: "handover_detail_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "handover_line_id", type: "bigint(20)",  remarks: "资产移交归还单行ID")  {constraints(nullable:"false")}  
            column(name: "handover_type_code", type: "varchar(" + 30 * weight + ")",  remarks: "移交确认/移交归还")  {constraints(nullable:"false")}  
            column(name: "current_asset_status_id", type: "bigint(20)",  remarks: "当前资产状态")  {constraints(nullable:"false")}  
            column(name: "target_asset_status_id", type: "bigint(20)",  remarks: "目标资产状态")  {constraints(nullable:"false")}  
            column(name: "current_owning_person_id", type: "bigint(20)",  remarks: "当前所属人")  {constraints(nullable:"false")}  
            column(name: "target_owning_person_id", type: "bigint(20)",  remarks: "目标所属人")  {constraints(nullable:"false")}  
            column(name: "current_using_person_id", type: "bigint(20)",  remarks: "当前使用人")  {constraints(nullable:"false")}  
            column(name: "target_using_person_id", type: "bigint(20)",  remarks: "目标使用人")  {constraints(nullable:"false")}  
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