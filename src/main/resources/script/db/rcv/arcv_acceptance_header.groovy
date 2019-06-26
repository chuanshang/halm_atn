package script.db

databaseChangeLog(logicalFilePath: 'script/db/arcv_acceptance_header.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-26-arcv_acceptance_header") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'arcv_acceptance_header_s', startValue:"1")
        }
        createTable(tableName: "arcv_acceptance_header", remarks: "验收单") {
            column(name: "acceptance_header_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "acceptance_type_id", type: "bigint(20)",  remarks: "验收类型，URL值集（ARCV.ACCEPTANCE_ORDER_TYPE）")  {constraints(nullable:"false")}
            column(name: "acceptance_status_code", type: "varchar(" + 30 * weight + ")",  remarks: "验收状态，独立值集（ARCV.ACCEPTANCE_STATUS）")  {constraints(nullable:"false")}
            column(name: "principal_person_id", type: "bigint(20)",  remarks: "负责人，URL值集（HPFM.EMPLOYEE），人员值列表")  {constraints(nullable:"false")}
            column(name: "acceptance_num", type: "varchar(" + 30 * weight + ")",  remarks: "验收单编号 ")  {constraints(nullable:"false")}  
            column(name: "title", type: "varchar(" + 60 * weight + ")",  remarks: "标题概述")  {constraints(nullable:"false")}  
            column(name: "submit_date", type: "date",  remarks: "提交日期")   
            column(name: "complete_date", type: "date",  remarks: "完成日期")   
            column(name: "request_department_id", type: "bigint(20)",  remarks: "申请部门，URL值集（AMDM.ORGANIZATION）类型为部门的组织")  {constraints(nullable:"false")}
            column(name: "purchase_department_id", type: "bigint(20)",  remarks: "采购部门，URL值集（AMDM.ORGANIZATION）类型为部门的组织")  {constraints(nullable:"false")}
            column(name: "description", type: "varchar(" + 240 * weight + ")",  remarks: "描述")   
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"acceptance_num,tenant_id",tableName:"arcv_acceptance_header",constraintName: "arcv_acceptance_header_u1")
    }
}