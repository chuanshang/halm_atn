package org.hzero.halm.atn.infra.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.base.CaseFormat;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.hzero.boot.platform.lov.adapter.LovAdapter;
import org.hzero.boot.platform.lov.dto.LovValueDTO;
import org.hzero.halm.afm.infra.annotation.FieldMessage;
import org.hzero.halm.atn.infra.constant.AatnConstans;
import org.hzero.halm.atn.infra.constant.TransactionLineEnum;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 事务处理历史记录的工具类。
 * @author WangSen 2019/3/30 0030 14:36
 */
public class TransactionHistoryUtils {

    private TransactionHistoryUtils() {
    }


    /**
     * 获取json字符串
     *
     * @param originalJson originalJson
     * @param newStr       newStr
     * @return 结果
     */
    public static String getStringJson(String originalJson, String newStr) {
        if (originalJson == null) {
            return null;
        }
        List<String> list;
        if (StringUtils.isEmpty(originalJson)) {
            list = new ArrayList<>();
        } else {
            list =  JSON.parseArray(originalJson.trim(), String.class);
        }
        list.add(newStr);
        return JSONArray.toJSONString(list);
    }

    /**
     * 获取一个对象中，所有含有current，和target 字符的字段名称。并以map形式返回。key->字段名称，value->current和target对应的字段名称。
     *
     * @param c object
     * @return map数据。
     */
    public static Map<String, List<String>> getChangedFieldsMap(Class c) {
        // 将类型按照 FieldMessage里面的tag分类。
        Map<String, List<Field>> fieldMap =
                Arrays.stream(c.getDeclaredFields())
                        .filter(field -> field.isAnnotationPresent(FieldMessage.class))
                        .collect(Collectors.groupingBy(field -> field.getAnnotation(FieldMessage.class).tag()));

        Map<String, List<String>> resultMap = new HashMap<>(5);
        for (Map.Entry<String, List<Field>> entry : fieldMap.entrySet()) {
            List<Field> list = entry.getValue();
            List<String> dataList = new ArrayList<>();
            dataList.add(getTheField(list, AatnConstans.FieldMessageDes.CURRENT));
            dataList.add(getTheField(list, AatnConstans.FieldMessageDes.CURRENT_MEANING));
            dataList.add(getTheField(list, AatnConstans.FieldMessageDes.TARGET));
            dataList.add(getTheField(list, AatnConstans.FieldMessageDes.TARGET_MEANING));
            resultMap.put(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entry.getKey()), dataList);
        }
        return resultMap;
    }


    /**
     * 获取字段的对应值
     *
     * @param ob  ob
     * @param map 要查询的字段  key--> 字段名称 如 ：asset_status_id ，
     *            value--> current和target对应的字段名称。如currentAssetStatusId，targetAssetStatusId
     * @return 结果
     */
    public static Map<String, List> getFieldsValue(Object ob, Map<String, List<String>> map) throws IllegalAccessException {
        Map<String, List> resultMap = new HashMap<>(10);
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            List<String> fields = map.get(key);
            List<Object> fieldValues = new ArrayList<>();
            for (String field : fields) {
                if (field != null) {
                    fieldValues.add(FieldUtils.readDeclaredField(ob, field, true));
                } else {
                    fieldValues.add(null);
                }

            }
            resultMap.put(key, fieldValues);
        }
        return resultMap;
    }


    /**
     * 返回所有的事务处理状态
     *
     * @param adapter 查询lov数据的对象
     * @return 返回所有的事务状态 key-> lov的valule,value->lov的meaning。
     */
    public static Map<String, String> getTheAllProcessStatus(LovAdapter adapter) {
        Map<String, String> allStatus = new HashMap<>(20);
        for (TransactionLineEnum e : TransactionLineEnum.values()) {
            List<LovValueDTO> list = adapter.queryLovValue(e.getProcessLovCode(), null);
            if (CollectionUtils.isNotEmpty(list)) {
                Map<String,String> newStatus =
                        list.stream()
                        .filter(lovValueDTO -> !allStatus.keySet().contains(lovValueDTO.getValue()))
                        .collect(Collectors.toMap(LovValueDTO::getValue, LovValueDTO::getMeaning, (a, b) -> a));
                allStatus.putAll(newStatus);
            }
        }
        return allStatus;
    }

    /**
     * 获取某种类型的字段名
     * @param fields 字段集合
     * @param type   {@link AatnConstans.FieldMessageDes}
     * @return entity里字段的名字。
     */
    private static String getTheField(List<Field> fields, String type) {
        for (Field field : fields) {
            if (field.isAnnotationPresent(FieldMessage.class)
                    && StringUtils.equals(field.getAnnotation(FieldMessage.class).des(), type)) {
                return field.getName();
            }
        }
        return null;
    }


}
