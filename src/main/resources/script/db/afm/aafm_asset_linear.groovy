package script.db

databaseChangeLog(logicalFilePath: 'script/db/aafm_asset_linear.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-04-aafm_asset_linear") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'aafm_asset_linear_s', startValue:"1")
        }
        createTable(tableName: "aafm_asset_linear", remarks: "资产-线性属性") {
            column(name: "linear_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "linear_name", type: "bigint(20)",  remarks: "长度")   
            column(name: "linear_start_measure", type: "bigint(20)",  remarks: "开始端(A)计量位，必输，保留9位小数")   
            column(name: "linear_end_measure", type: "bigint(20)",  remarks: "结束端(B)端计量位，必输，保留10位小数")   
            column(name: "linear_start_desc", type: "varchar(" + 480 * weight + ")",  remarks: "开始端(A)描述")   
            column(name: "linear_end_desc", type: "varchar(" + 480 * weight + ")",  remarks: "开始端(A)描述")   
            column(name: "description", type: "varchar(" + 480 * weight + ")",  remarks: "描述")   
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "asset_id", type: "bigint(20)",  remarks: "资产ID")  {constraints(nullable:"false")}  

        }

    }
}