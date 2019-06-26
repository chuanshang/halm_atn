package script.db

databaseChangeLog(logicalFilePath: 'script/db/aafm_attribute_set.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-04-aafm_attribute_set") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'aafm_attribute_set_s', startValue:"1")
        }
        createTable(tableName: "aafm_attribute_set", remarks: "属性组头信息") {
            column(name: "attribute_set_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "attribute_set_name", type: "varchar(" + 30 * weight + ")",  remarks: "名称，必输，租户内唯一")  {constraints(nullable:"false")}  
            column(name: "description", type: "varchar(" + 120 * weight + ")",  remarks: "描述")   
            column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "启用标识")  {constraints(nullable:"false")}  
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"attribute_set_name,tenant_id",tableName:"aafm_attribute_set",constraintName: "amdm_attribute_set_u1")
    }
}