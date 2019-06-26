package org.hzero.halm.afm.infra.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ids导出转换，将String类型的ids转换为List<Long>
 * ids格式为：1,2,3
 * @author jiaxu.cui@hand-china.com 2018/12/19 17:47
 */
public class IdsExportConvert {

    public static List<Long> stringToList(String ids){
        if (StringUtils.isBlank(ids)){
            return new ArrayList<>();
        }
        return Arrays.stream(StringUtils.split(ids, ',')).map(Long::parseLong).collect(Collectors.toList());
    }
}