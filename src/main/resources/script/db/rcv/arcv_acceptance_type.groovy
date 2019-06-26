package script.db

databaseChangeLog(logicalFilePath: 'script/db/arcv_acceptance_type.groovy') {
    changeSet(author: "zhiguang.guo@hand-china.com", id: "2019-04-17-arcv_acceptance_type") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'arcv_acceptance_type_s', startValue:"1")
        }
        createTable(tableName: "arcv_acceptance_type", remarks: "验收单类型") {
            column(name: "acceptance_type_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "code_rule", type: "varchar(" + 30 * weight + ")",  remarks: "编码规则，SQL值集（HALM.CODE_RULE）")   
            column(name: "acceptance_type_code", type: "varchar(" + 30 * weight + ")",  remarks: "验收基础类型，独立值集（开发人员补充值集名称）")  {constraints(nullable:"false")}  
            column(name: "code", type: "varchar(" + 30 * weight + ")",  remarks: "代码")  {constraints(nullable:"false")}  
            column(name: "full_name", type: "varchar(" + 240 * weight + ")",  remarks: "事件完整名称")  {constraints(nullable:"false")}  
            column(name: "short_name", type: "varchar(" + 60 * weight + ")",  remarks: "事件短名称")  {constraints(nullable:"false")}  
            column(name: "project_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "是否来源于项目")   
            column(name: "budget_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "是否有对应预算")   
            column(name: "transfer_fixed_code", type: "varchar(" + 30 * weight + ")",  remarks: "是否转固，独立值集（开发人员补充值集名称）")  {constraints(nullable:"false")}  
            column(name: "approve_flow_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "是否有审批流")   
            column(name: "tag", type: "varchar(" + 30 * weight + ")",  remarks: "标记")   
            column(name: "complete_asset_status_id", type: "bigint(20)",  remarks: "验收完成时默认的资产状态，URL值集（开发人员补充值集名称）")   
            column(name: "transfer_interface_flag", type: "tinyint(1)",   defaultValue:"0",   remarks: "是否触发外部备品备件传输接口程序")   
            column(name: "directly_complete_flag", type: "tinyint(1)",   defaultValue:"0",   remarks: "是否直接完成物料事务处理")   
            column(name: "in_contract_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "是否合同内收货")   
            column(name: "create_fa_flag", type: "tinyint(1)",   defaultValue:"0",   remarks: "是否始终生成无价值的FA卡片")   
            column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "是否启用")   
            column(name: "description", type: "varchar(" + 240 * weight + ")",  remarks: "描述")   
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"full_name,tenant_id",tableName:"arcv_acceptance_type",constraintName: "arcv_acceptance_type_u1")
    }
}