package script.db

databaseChangeLog(logicalFilePath: 'script/db/arcv_acceptance_line.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-26-arcv_acceptance_line") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'arcv_acceptance_line_s', startValue:"1")
        }
        createTable(tableName: "arcv_acceptance_line", remarks: "验收单行") {
            column(name: "acceptance_line_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "acceptance_header_id", type: "bigint(20)",  remarks: "验收单头id")  {constraints(nullable:"false")}  
            column(name: "contract_id", type: "bigint(20)",  remarks: "合同id，URL值集(开发人员补充值集名称)")  {constraints(nullable:"false")}  
            column(name: "contract_line_id", type: "bigint(20)",  remarks: "合同行id，URL值集(开发人员补充值集名称)")   
            column(name: "project_id", type: "bigint(20)",  remarks: "项目id，URL值集(APPM.PROJECT)")
            column(name: "wbs_line_id", type: "bigint(20)",  remarks: "WBS计划行ID，URL值集(APPM.PRO_WBS)")
            column(name: "budget_header_id", type: "bigint(20)",  remarks: "项目预算ID，URL值集(开发人员补充值集名称)")   
            column(name: "budget_line_id", type: "bigint(20)",  remarks: "项目预算行ID，URL值集(开发人员补充值集名称)")   
            column(name: "product_category_id", type: "bigint(20)",  remarks: "产品类别")   
            column(name: "assets_set_id", type: "bigint(20)",  remarks: "资产组")   
            column(name: "acceptance_line_name", type: "varchar(" + 240 * weight + ")",  remarks: "名称")  {constraints(nullable:"false")}  
            column(name: "delivery_list_id", type: "bigint(20)",  remarks: "交付清单行id，URL值集(ARCV.DELIVERY_LIST)")
            column(name: "specifications", type: "varchar(" + 480 * weight + ")",  remarks: "规格/型号")   
            column(name: "unit_price", type: "decimal(20,10)",  remarks: "单价")  {constraints(nullable:"false")}  
            column(name: "uom_id", type: "bigint(20)",  remarks: "单位Id，值集：AMDM.UOM")  {constraints(nullable:"false")}  
            column(name: "delivery_quantity", type: "bigint(20)",  remarks: "数量")  {constraints(nullable:"false")}
            column(name: "description", type: "varchar(" + 240 * weight + ")",  remarks: "备注")   
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

    }
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-05-22-arcv_acceptance_line"){
        dropColumn(tableName: "arcv_acceptance_line", columnName: "assets_set_id")
        dropColumn(tableName: "arcv_acceptance_line", columnName: "product_category_id")
        addColumn(tableName: "arcv_acceptance_line"){
            column(name: "product_category_id", type: "bigint(20)",  remarks: "产品类别"){constraints(nullable:"false")}
            column(name: "assets_set_id", type: "bigint(20)",  remarks: "资产组"){constraints(nullable:"false")}
        }
        dropNotNullConstraint(schemaName: 'halm_atn', tableName: 'arcv_acceptance_line', columnName: 'contract_id', columnDataType: 'bigint(20)')
    }
}