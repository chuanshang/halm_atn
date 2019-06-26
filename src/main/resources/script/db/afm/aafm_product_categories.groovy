package script.db

databaseChangeLog(logicalFilePath: 'script/db/aafm_product_categories.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-04-aafm_product_categories") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'aafm_product_categories_s', startValue:"1")
        }
        createTable(tableName: "aafm_product_categories", remarks: "产品类别/资产分类") {
            column(name: "product_category_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "product_category_name", type: "varchar(" + 120 * weight + ")",  remarks: "名称，必输，租户内唯一")  {constraints(nullable:"false")}  
            column(name: "product_category_code", type: "varchar(" + 30 * weight + ")",  remarks: "代码，必输且租户内唯一 或 非必输")   
            column(name: "parent_category_id", type: "bigint(20)",  remarks: "父级类别ID，父级类别ID为空时表示顶层类别")   
            column(name: "category_description", type: "varchar(" + 240 * weight + ")",  remarks: "全称，非必输")   
            column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "启用标记，默认：1（启用）")  {constraints(nullable:"false")}  
            column(name: "code_rule", type: "varchar(" + 30 * weight + ")",  remarks: "编码规则")   
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")   
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "level_path", type: "varchar(" + 480 * weight + ")",  remarks: "")  {constraints(nullable:"false")}  
            column(name: "level_number", type: "int(11)",  remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"product_category_name,tenant_id",tableName:"aafm_product_categories",constraintName: "amdm_product_categories_u1")
    }
}