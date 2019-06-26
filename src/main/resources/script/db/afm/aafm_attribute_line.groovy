package script.db

databaseChangeLog(logicalFilePath: 'script/db/aafm_attribute_line.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-04-aafm_attribute_line") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'aafm_attribute_line_s', startValue:"1")
        }
        createTable(tableName: "aafm_attribute_line", remarks: "属性组行信息") {
            column(name: "line_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "attribute_set_id", type: "bigint(20)",  remarks: "属性组头ID")  {constraints(nullable:"false")}  
            column(name: "line_num", type: "bigint(20)",  remarks: "编号，必输，同一属性组下唯一")  {constraints(nullable:"false")}  
            column(name: "attribute_name", type: "varchar(" + 30 * weight + ")",  remarks: "属性名,必输，同一属性组下唯一")  {constraints(nullable:"false")}  
            column(name: "required_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "是否必须，必输，默认：1")  {constraints(nullable:"false")}  
            column(name: "attribute_type", type: "varchar(" + 30 * weight + ")",  remarks: "字段类型, 必输，值集：AAFM.FIELD_TYPE")  {constraints(nullable:"false")}  
            column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "启用标识，必输，默认：1")  {constraints(nullable:"false")}  
            column(name: "uom_id", type: "bigint(20)",  remarks: "单位ID，必输，值集：AMDM.UOM")  {constraints(nullable:"false")}  
            column(name: "description", type: "varchar(" + 120 * weight + ")",  remarks: "描述")   
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "lov_value", type: "varchar(" + 30 * weight + ")",  remarks: "值集编码, 当attribute_type为值集时，该字段必输")   

        }

        addUniqueConstraint(columnNames:"line_num,attribute_set_id,tenant_id",tableName:"aafm_attribute_line",constraintName: "amdm_attribute_line_u1")
        addUniqueConstraint(columnNames:"attribute_name,attribute_set_id,tenant_id",tableName:"aafm_attribute_line",constraintName: "amdm_attribute_line_u2")
    }
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-05-22-aafm_attribute_line"){
        dropNotNullConstraint(schemaName: 'halm_atn', tableName: 'aafm_attribute_line', columnName: 'uom_id', columnDataType: 'bigint(20)')
    }
}