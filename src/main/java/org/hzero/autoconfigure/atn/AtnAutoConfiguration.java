package org.hzero.autoconfigure.atn;

import io.choerodon.resource.annoation.EnableChoerodonResourceServer;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(value = {"org.hzero.halm"})
@EnableFeignClients({"org.hzero.halm"})
@EnableChoerodonResourceServer
public class AtnAutoConfiguration {

}
