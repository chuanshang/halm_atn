package script.db

databaseChangeLog(logicalFilePath: 'script/db/aafm_asset_to_org.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-04-aafm_asset_to_org") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'aafm_asset_to_org_s', startValue:"1")
        }
        createTable(tableName: "aafm_asset_to_org", remarks: "资产专业-组织分配(行)") {
            column(name: "asset_org_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "asset_specialty_id", type: "bigint(20)",  remarks: "资产专业ID")  {constraints(nullable:"false")}  
            column(name: "maint_sites_id", type: "bigint(20)",  remarks: "服务区域， AMDM.ASSET_MAINT_SITE, 关联""服务区域""")  {constraints(nullable:"false")}  
            column(name: "major_department_id", type: "bigint(20)",  remarks: "专业归口部门，AMDM.ORGANIZATION, 关联""组织""")   
            column(name: "manage_department_id", type: "bigint(20)",  remarks: "管理部门，AMDM.ORGANIZATION,关联""组织""")   
            column(name: "priority", type: "int(3)",   defaultValue:"1",   remarks: "优先级，数值越大，优先级越高，上限：100, 不可重复")  {constraints(nullable:"false")}  
            column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "是否启用，默认：1")   
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }
   createIndex(tableName: "aafm_asset_to_org", indexName: "amdm_asset_to_org_n1") {
            column(name: "asset_specialty_id")
        }

    }
}