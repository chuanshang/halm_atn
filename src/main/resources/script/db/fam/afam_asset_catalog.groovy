package script.db

databaseChangeLog(logicalFilePath: 'script/db/afam_asset_catalog.groovy') {
    changeSet(author: "zhiguang.guo@hand-china.com", id: "2019-04-10-afam_asset_catalog") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'afam_asset_catalog_s', startValue:"1")
        }
        createTable(tableName: "afam_asset_catalog", remarks: "资产目录") {
            column(name: "asset_catalog_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "catalog_name", type: "varchar(" + 60 * weight + ")",  remarks: "名称")  {constraints(nullable:"false")}  
            column(name: "product_category_id", type: "bigint(20)",  remarks: "产品类别，URL值集，AAFM.ASSET_CLASS")  {constraints(nullable:"false")}  
            column(name: "catalog_code", type: "varchar(" + 30 * weight + ")",  remarks: "资产目录代码")   
            column(name: "residual_value_rate", type: "decimal(10,2)",  remarks: "残值率")   
            column(name: "depreciation_month", type: "int(3)",  remarks: "折旧月份")   
            column(name: "depreciation_type_code", type: "varchar(" + 30 * weight + ")",  remarks: "折旧类型，独立值集，（开发人员补充名字）")   
            column(name: "account_type_code", type: "varchar(" + 30 * weight + ")",  remarks: "资产入账会计科目类型,独立值集，（开发人员补充名字）")   
            column(name: "parent_catalog_id", type: "bigint(20)",  remarks: "上级组织/父组织,为空时表示顶层组织")   
            column(name: "level_path", type: "varchar(" + 480 * weight + ")",  remarks: "层级结构的节点路径,递归父级id/本级id")  {constraints(nullable:"false")}  
            column(name: "level_number", type: "int(11)",  remarks: "当前所处层级")  {constraints(nullable:"false")}  
            column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "启用")  {constraints(nullable:"false")}  
            column(name: "description", type: "varchar(" + 240 * weight + ")",  remarks: "描述")   
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

    }
}