package script.db

databaseChangeLog(logicalFilePath: 'script/db/aafm_asset.groovy') {
    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-04-04-aafm_asset") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'aafm_asset_s', startValue:"1")
        }
        createTable(tableName: "aafm_asset", remarks: "资产/设备基本信息") {
            column(name: "asset_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)}
            column(name: "asset_num", type: "varchar(" + 30 * weight + ")",  remarks: "资产编号，必输且租户内唯一，非中文")  {constraints(nullable:"false")}
            column(name: "assets_set_id", type: "bigint(20)",  remarks: "产品组/资产组，值集：AMDM_ASSET_SET,必输")  {constraints(nullable:"false")}
            column(name: "asset_class_id", type: "bigint(20)",  remarks: "产品类别/资产分类，值集：AMDM.ASSET_CLASS 必输")  {constraints(nullable:"false")}
            column(name: "asset_desc", type: "varchar(" + 240 * weight + ")",  remarks: "资产全称，只读,当前命名格式：产品名称.品牌/厂商.规格型号")  {constraints(nullable:"false")}
            column(name: "special_asset_code", type: "varchar(" + 20 * weight + ")",  remarks: "所属特殊资产，值集：AMDM.SPECIAL_ASSET")
            column(name: "brand", type: "varchar(" + 120 * weight + ")",  remarks: "品牌/厂商")
            column(name: "model", type: "varchar(" + 120 * weight + ")",  remarks: "规格型号")
            column(name: "name_rule_code", type: "varchar(" + 30 * weight + ")",  remarks: "资产标签/铭牌规则，值集：AAFM.NAMEPLATE_RULE 必输")  {constraints(nullable:"false")}
            column(name: "name", type: "varchar(" + 30 * weight + ")",  remarks: "可视标签/铭牌， 必输且租户内唯一")  {constraints(nullable:"false")}
            column(name: "serial_num", type: "varchar(" + 120 * weight + ")",  remarks: "序列号,非中文")
            column(name: "tracking_num", type: "varchar(" + 30 * weight + ")",  remarks: "其他跟踪编号")
            column(name: "maintain_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "是否可维修，默认：1，必输")  {constraints(nullable:"false")}
            column(name: "asset_name", type: "varchar(" + 240 * weight + ")",  remarks: "产品名称，必输")  {constraints(nullable:"false")}
            column(name: "description", type: "varchar(" + 480 * weight + ")",  remarks: "描述")
            column(name: "asset_icon", type: "varchar(" + 20 * weight + ")",  remarks: "图标")
            column(name: "attribute_values", type: "varchar(" + 480 * weight + ")",  remarks: "属性行值，格式[{attrCode: 'aaa', attrValue:'bbb'},{}...] 对象数组")
            column(name: "linear_name", type: "bigint(20)",  remarks: "线性描述-长度，必输，保留8位小数(视所属特殊资产选项而定)")
            column(name: "linear_start_measure", type: "bigint(20)",  remarks: "线性描述-开始端(A)计量位，必输，保留9位小数(视所属特殊资产选项而定)")
            column(name: "linear_end_measure", type: "bigint(20)",  remarks: "线性描述-结束端(B)端计量位，必输，保留10位小数(视所属特殊资产选项而定)")
            column(name: "linear_start_desc", type: "varchar(" + 480 * weight + ")",  remarks: "线性描述-开始端(A)描述")
            column(name: "linear_end_desc", type: "varchar(" + 480 * weight + ")",  remarks: "线性描述-开始端(A)描述")
            column(name: "car_num", type: "varchar(" + 30 * weight + ")",  remarks: "车牌号，必输(视所属特殊资产选项而定)")
            column(name: "vin_num", type: "varchar(" + 30 * weight + ")",  remarks: "车架号")
            column(name: "engine_num", type: "varchar(" + 30 * weight + ")",  remarks: "发动机号")
            column(name: "supplier_org_id", type: "bigint(20)",  remarks: "供应商")
            column(name: "asset_source_detail", type: "varchar(" + 480 * weight + ")",  remarks: "来源明细")
            column(name: "currency_code", type: "varchar(" + 30 * weight + ")",  remarks: "货币，值集：HPFM.CURRENCY")
            column(name: "original_cost", type: "decimal(20,10)",  remarks: "获取价格")
            column(name: "received_date", type: "datetime",  remarks: "交付日期")
            column(name: "start_date", type: "datetime",  remarks: "启用日期")
            column(name: "warranty_type_code", type: "varchar(" + 30 * weight + ")",  remarks: "质保类型，值集：AAFM.ASSET_WARRANTY_TYPE")
            column(name: "warranty_type_rule", type: "varchar(" + 30 * weight + ")",  remarks: "质保起始日规则，值集：AAFM.ASSET_WARRANTY_RULE [下拉列表]")
            column(name: "warranty_start_date", type: "datetime",  remarks: "质保起始日")
            column(name: "warranty_expire_date", type: "datetime",  remarks: "质保终止日")
            column(name: "parent_asset_id", type: "varchar(" + 30 * weight + ")",  remarks: "父资产，值集：AAFM.ASSET")
            column(name: "asset_criticality", type: "varchar(" + 30 * weight + ")",  remarks: "资产重要性，值集：AMDM.ASSET_CRITICALITY")
            column(name: "asset_location_id", type: "bigint(20)",  remarks: "资产位置")  {constraints(nullable:"false")}
            column(name: "asset_location_desc", type: "varchar(" + 480 * weight + ")",  remarks: "资产位置详细说明")
            column(name: "map_source", type: "varchar(" + 30 * weight + ")",  remarks: "地图来源，必输[下拉列表]")  {constraints(nullable:"false")}
            column(name: "owning_org_detail", type: "varchar(" + 480 * weight + ")",  remarks: "所属组织明细")
            column(name: "cost_center_id", type: "bigint(20)",  remarks: "成本中心，值集：AAFM.ASSET_COST_CENTER")
            column(name: "using_org_detail", type: "varchar(" + 480 * weight + ")",  remarks: "使用组织明细")
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}
            column(name: "asset_source_id", type: "bigint(20)",  remarks: "其他来源相关文档号，值集：AAFM.ASSET_SOURCE_DOC")
            column(name: "aos_receiving_report_id", type: "bigint(20)",  remarks: "来源验收单，值集：AAFM.ASSET_SOURCE_RECEIPT")
            column(name: "source_contract_id", type: "bigint(20)",  remarks: "来源合同，值集：AAFM.ASSET_SOURCE_CONTRACT")
            column(name: "source_project_id", type: "bigint(20)",  remarks: "来源项目，值集：AAFM.ASSET_SOURCE_PROJECT")
            column(name: "using_org_id", type: "bigint(20)",  remarks: "使用组织，值集：AAFM.ASSET_ORG")
            column(name: "owning_org_id", type: "bigint(20)",  remarks: "所属组织，值集：AAFM.ASSET_ORG")
            column(name: "user_person_id", type: "bigint(20)",  remarks: "使用人，值集：AAFM.ASSET_USER")
            column(name: "owning_person_id", type: "bigint(20)",  remarks: "资产管理员，值集：AAFM.ASSET_MANAGER")
            column(name: "asset_source_type_code", type: "varchar(" + 30 * weight + ")",  remarks: "资产来源, 值集：AAFM.ASSET_SOURCE")  {constraints(nullable:"false")}
            column(name: "asset_status_id", type: "bigint(20)",  remarks: "资产状态，值集：AAFM.ASSET_STATUS")
            column(name: "asset_specialty_id", type: "bigint(20)",  remarks: "所属资产专业分类，值集：AAFM.SPECIAL_ASSET_CLASS")

        }

        addUniqueConstraint(columnNames:"asset_num,tenant_id",tableName:"aafm_asset",constraintName: "aafm_asset_u1")
    }

    changeSet(author: "zhisheng.zhang@hand-china.com", id: "2019-05-22-aafm_asset"){
        dropColumn(tableName: "aafm_asset", columnName: "asset_status_id")
        addColumn(tableName: "aafm_asset"){
            column(name: "asset_status_id", type: "bigint(20)",  remarks: "资产状态，值集：AAFM.ASSET_STATUS"){constraints(nullable:"false")}
        }
        addColumn(tableName: "aafm_asset"){
            column(name: "manufacturer_id", type: "bigint(20)",  remarks: "制造厂商(总装)")
            column(name: "warranty_notes", type: "varchar(" + 480 * weight + ")",  remarks: "质保详细说明")
        }
    }
}