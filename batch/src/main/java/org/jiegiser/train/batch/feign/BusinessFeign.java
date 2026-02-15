package org.jiegiser.train.batch.feign;

import org.jiegiser.train.common.resp.CommonResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

// value 为服务名
// @FeignClient(value = "business", fallback = BusinessFeignFallback.class)
@FeignClient(name = "business", url = "http://127.0.0.1:8889/")
public interface BusinessFeign {

    @GetMapping("/business/test")
    String test();

    @GetMapping("/business/admin/daily-train/gen-daily/{date}")
    CommonResp<Object> genDaily(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date);
}
