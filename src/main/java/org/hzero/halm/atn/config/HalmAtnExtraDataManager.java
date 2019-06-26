package org.hzero.halm.atn.config;

import io.choerodon.core.swagger.ChoerodonRouteData;
import io.choerodon.swagger.annotation.ChoerodonExtraData;
import io.choerodon.swagger.swagger.extra.ExtraData;
import io.choerodon.swagger.swagger.extra.ExtraDataManager;

@ChoerodonExtraData
public class HalmAtnExtraDataManager implements ExtraDataManager {

    @Override
    public ExtraData getData() {
        ChoerodonRouteData choerodonRouteData = new ChoerodonRouteData();
        choerodonRouteData.setName("aatn");
        choerodonRouteData.setPath("/aatn/**");
        choerodonRouteData.setServiceId("halm-atn");
        extraData.put(ExtraData.ZUUL_ROUTE_DATA, choerodonRouteData);
        return extraData;
    }
}
