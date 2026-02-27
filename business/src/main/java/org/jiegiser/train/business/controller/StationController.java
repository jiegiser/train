package org.jiegiser.train.business.controller;


import jakarta.annotation.Resource;
import org.apache.seata.core.context.RootContext;
import org.jiegiser.train.business.resp.StationQueryResp;
import org.jiegiser.train.business.service.StationService;
import org.jiegiser.train.common.resp.CommonResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/station")
public class StationController {

    private static final Logger LOG = LoggerFactory.getLogger(StationController.class);
    @Resource
    private StationService stationService;

    @GetMapping("/query-all")
    public CommonResp<List<StationQueryResp>> queryList() {
        LOG.info("seata 全局事务ID: {}", RootContext.getXID());
        List<StationQueryResp> list = stationService.queryAll();
        return new CommonResp<>(list);
    }

}
