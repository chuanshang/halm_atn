/*
 * Copyright (C) HAND Enterprise Solutions Company Ltd.
 * All Rights Reserved
 */

package org.hzero.halm.afm.infra.utils;

import io.choerodon.core.exception.CommonException;
import org.apache.commons.lang.reflect.FieldUtils;
import org.hzero.mybatis.base.BaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author WangSen
 * @Title DtoUtils
 * @Description 与实体类相关的工具类
 * @date: 2019-1-24 14:55
 */
public class DtoUtils {
    private static final Logger logger = LoggerFactory.getLogger(DtoUtils.class);

    private static String REFLECT_ERRO = "reflect_erro";

    private static final String STR_SET = "set";

    private DtoUtils() {
    }

    /**
     * 唯一性索引的效验
     *
     * @param repository repository
     * @param erroMsg    错误信息
     * @param dtoData    被效验的实体类  （该实体类，必须要有空参的构造器。）
     * @param args       唯一性索引的若干字段。（租户id的字段不需要手动写，默认添加到索引字段里面。）
     * @param <T>        被效验对象的类型
     */
    @SuppressWarnings("unchecked")
    public static <T> void validUniqueIndex(BaseRepository repository, String erroMsg, T dtoData, String... args) {

        T dto = null;
        List<String> argList = new ArrayList<>(Arrays.asList(args));
        argList.add("tenantId");
        try {
            Class dtoDataClass = dtoData.getClass();
            dto = (T) dtoDataClass.getConstructor().newInstance();
            for (String arg : argList) {
                Object result = FieldUtils.readDeclaredField(dtoData, arg, true);
                FieldUtils.writeDeclaredField(dto,arg,result,true);
            }
        } catch (Exception e) {
            logger.debug("{}", e);
            throw new CommonException(REFLECT_ERRO);
        }
        if (repository.selectCount(dto) != 0) {
            throw new CommonException(erroMsg);
        }
    }



}