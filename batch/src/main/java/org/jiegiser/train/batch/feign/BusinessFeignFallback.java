package org.jiegiser.train.batch.feign;

import org.jiegiser.train.common.resp.CommonResp;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BusinessFeignFallback implements BusinessFeign {
    @Override
    public String test() {
        return "Fallback";
    }

    @Override
    public CommonResp<Object> genDaily(Date date) {
        return null;
    }
}
