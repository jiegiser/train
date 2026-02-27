package org.jiegiser.train.business.controller;


import jakarta.validation.Valid;
import org.jiegiser.train.business.req.SeatSellReq;
import org.jiegiser.train.business.resp.SeatSellResp;
import org.jiegiser.train.business.service.DailyTrainSeatService;
import org.jiegiser.train.common.resp.CommonResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

;

@RestController
@RequestMapping("/seat-sell")
public class SeatSellController {

    @Autowired
    private DailyTrainSeatService dailyTrainSeatService;

    @GetMapping("/query")
    public CommonResp<List<SeatSellResp>> query(@Valid SeatSellReq req) {
        List<SeatSellResp> seatList = dailyTrainSeatService.querySeatSell(req);
        return new CommonResp<>(seatList);
    }

}
