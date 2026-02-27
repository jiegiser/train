package org.jiegiser.train.business.controller;


import jakarta.annotation.Resource;
import org.jiegiser.train.business.resp.TrainQueryResp;
import org.jiegiser.train.business.service.TrainService;
import org.jiegiser.train.common.resp.CommonResp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/train")
public class TrainController {

    @Resource
    private TrainService trainService;

    @GetMapping("/query-all")
    public CommonResp<List<TrainQueryResp>> queryList() throws InterruptedException {
        List<TrainQueryResp> list = trainService.queryAll();
        return new CommonResp<>(list);
    }

}
