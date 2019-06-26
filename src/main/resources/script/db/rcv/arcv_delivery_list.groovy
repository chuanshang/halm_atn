package script.db

databaseChangeLog(logicalFilePath: 'script/db/arcv_delivery_list.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-17-arcv_delivery_list") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'arcv_delivery_list_s', startValue:"1")
        }
        createTable(tableName: "arcv_delivery_list", remarks: "交付清单行") {
            column(name: "delivery_list_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "delivery_list_name", type: "varchar(" + 60 * weight + ")",  remarks: "名称")  {constraints(nullable:"false")}  
            column(name: "contract_id", type: "bigint(20)",  remarks: "合同id，URL值集(开发人员补充值集名称)")  {constraints(nullable:"false")}  
            column(name: "contract_line_id", type: "bigint(20)",  remarks: "合同行id，URL值集(开发人员补充值集名称)")   
            column(name: "project_id", type: "bigint(20)",  remarks: "项目id，URL值集(APPM.PROJECT)")
            column(name: "wbs_line_id", type: "bigint(20)",  remarks: "WBS计划行ID，URL值集(APPM.PRO_WBS)")
            column(name: "budget_header_id", type: "bigint(20)",  remarks: "项目预算ID，URL值集(开发人员补充值集名称)")   
            column(name: "budget_line_id", type: "bigint(20)",  remarks: "项目预算行ID，URL值集(开发人员补充值集名称)")   
            column(name: "product_category_id", type: "bigint(20)",  remarks: "产品类别")   
            column(name: "assets_set_id", type: "bigint(20)",  remarks: "资产组")   
            column(name: "specifications", type: "varchar(" + 480 * weight + ")",  remarks: "规格/型号")   
            column(name: "unit_price", type: "decimal(20,10)",  remarks: "单价")  {constraints(nullable:"false")}  
            column(name: "uom_id", type: "bigint(20)",  remarks: "单位Id，值集：AMDM.UOM")   
            column(name: "need_delivery_quantity", type: "bigint(20)",  remarks: "应交付数量")  {constraints(nullable:"false")}
            column(name: "delivered_quantity", type: "bigint(20)",  remarks: "已交付数量")
            column(name: "delivery_complete_date", type: "date",  remarks: "交付完成日期")   
            column(name: "delivery_complete_flag", type: "tinyint(1)",   defaultValue:"0",   remarks: "是否交付完成")   
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}
            column(name: "description", type: "varchar(" + 240 * weight + ")",  remarks: "描述")

        }

    }
}