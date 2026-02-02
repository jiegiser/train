package org.jiegiser.train.member.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.jiegiser.train.common.resq.CommonResp;
import org.jiegiser.train.member.req.PassengerSaveReq;
import org.jiegiser.train.member.service.PassengerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passenger")
public class PassengerController {
    @Resource
    private PassengerService passengerService;
    @PostMapping("/save")
    public CommonResp<Integer> save(@Valid @RequestBody PassengerSaveReq req) {
        passengerService.save(req);
        return new CommonResp<>();
    }
}
