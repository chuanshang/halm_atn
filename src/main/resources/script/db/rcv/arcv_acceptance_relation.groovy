package script.db

databaseChangeLog(logicalFilePath: 'script/db/arcv_acceptance_relation.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-26-arcv_acceptance_relation") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'arcv_acceptance_relation_s', startValue:"1")
        }
        createTable(tableName: "arcv_acceptance_relation", remarks: "验收单关联") {
            column(name: "acceptance_relation_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "acceptance_header_id", type: "bigint(20)",  remarks: "关联的验收单头id")  {constraints(nullable:"false")}
            column(name: "relate_acceptance_id", type: "bigint(20)",  remarks: "关联的验收单id")  {constraints(nullable:"false")}
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

    }
}