package script.db

databaseChangeLog(logicalFilePath: 'script/db/aafm_asset_status.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-04-aafm_asset_status") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'aafm_asset_status_s', startValue:"1")
        }
        createTable(tableName: "aafm_asset_status", remarks: "资产状态") {
            column(name: "asset_status_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "sys_status_name", type: "varchar(" + 60 * weight + ")",  remarks: "系统状态名称，租户初始化时系统自动创建，不可修改")  {constraints(nullable:"false")}  
            column(name: "initial_status_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "新增时可用")   
            column(name: "maintain_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "可维护")   
            column(name: "transaction_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "可事务处理")   
            column(name: "edit_basic_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "可编辑基本信息")   
            column(name: "edit_source_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "可编辑来源信息")   
            column(name: "edit_manage_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "可编辑管理信息")   
            column(name: "edit_attribute_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "可编辑属性信息")   
            column(name: "edit_warranty_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "可编辑质保信息")   
            column(name: "pm_start_trigger_flag", type: "tinyint(1)",   defaultValue:"0",   remarks: "触发预防性维护开启")   
            column(name: "pm_stop_trigger_flag", type: "tinyint(1)",   defaultValue:"0",   remarks: "触发预防性维护停止")   
            column(name: "next_status", type: "varchar(" + 480 * weight + ")",  remarks: "")   
            column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "tenant_id", type: "bigint(20)",  remarks: "")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "asset_status_code", type: "varchar(" + 30 * weight + ")",  remarks: "系统状态编码")  {constraints(nullable:"false")}  
            column(name: "user_status_name", type: "varchar(" + 60 * weight + ")",  remarks: "用户自定义名称，默认同系统状态名，租户可修改，不重名")  {constraints(nullable:"false")}  

        }

    }
}