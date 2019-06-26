package org.hzero.halm.atn.app.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.helper.LanguageHelper;
import org.hzero.halm.atn.app.service.HandoverOrderDetailService;
import org.hzero.halm.atn.domain.entity.HandoverOrderDetail;
import org.hzero.halm.atn.domain.entity.HandoverOrderLine;
import org.hzero.halm.atn.infra.constant.AatnConstans;
import org.hzero.halm.atn.infra.constant.AatnHandoverFields;
import org.hzero.halm.atn.infra.mapper.HandoverOrderDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 资产移交归还明细应用服务默认实现
 *
 * @author sen.wang@hand-china.com 2019-03-20 16:49:11
 */
@Service
public class HandoverOrderDetailServiceImpl implements HandoverOrderDetailService {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private HandoverOrderDetailMapper detailMapper;


    @Override
    public String generateDes(HandoverOrderDetail orderDetail, AatnHandoverFields[] handoverFields) {
        StringBuilder builder = new StringBuilder();
        for (AatnHandoverFields handoverField : handoverFields) {
            try {
                String currentStr = (String) FieldUtils.readDeclaredField(orderDetail, handoverField.getCurrentName(), true);
                String targetStr = (String) FieldUtils.readDeclaredField(orderDetail, handoverField.getTargetName(), true);
                String message = null;
                if (BaseConstants.DEFAULT_LOCALE.equals(LanguageHelper.locale())) {
                    String[] args = {handoverField.getZhName(), currentStr, targetStr};
                    message = messageSource.getMessage(AatnConstans.FIELD_CHANGE_DESCRIPTION_TEMPLATE, args, AatnConstans.FIELD_CHANGE_DESCRIPTION_TEMPLATE, LanguageHelper.locale());
                } else {
                    String[] args = {handoverField.getEnName(), currentStr, targetStr};
                    message = messageSource.getMessage(AatnConstans.FIELD_CHANGE_DESCRIPTION_TEMPLATE, args, AatnConstans.FIELD_CHANGE_DESCRIPTION_TEMPLATE, LanguageHelper.locale());
                }
                builder.append(message + BaseConstants.Symbol.SEMICOLON);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        builder.append(orderDetail.getDescription());
        return builder.toString();
    }

    @Override
    public List<HandoverOrderDetail> selectDetailList(HandoverOrderLine line) {
        if (line == null) {
            return null;
        }
        HandoverOrderDetail detail = new HandoverOrderDetail();
        detail.setTenantId(line.getTenantId());
        detail.setHandoverLineId(line.getHandoverLineId());
        List<HandoverOrderDetail> detailList = detailMapper.selectDetail(detail);
        if(CollectionUtils.isNotEmpty(detailList)){
            detailList.stream().map(d -> {
                d.setName(line.getName());
                d.setAssetDesc(line.getAssetDesc());
                d.setProcessStatusMeaning(line.getProcessStatusMeaning());
                d.setProcessStatus(line.getProcessStatus());
                return d;
            }).collect(Collectors.toList());
        }
        return detailList;
    }
}
