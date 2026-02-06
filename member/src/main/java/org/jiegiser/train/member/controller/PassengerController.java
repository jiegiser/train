package org.jiegiser.train.member.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.jiegiser.train.common.context.LoginMemberContext;
import org.jiegiser.train.common.resp.CommonResp;
import org.jiegiser.train.common.resp.PageResp;
import org.jiegiser.train.member.req.PassengerQueryReq;
import org.jiegiser.train.member.req.PassengerSaveReq;
import org.jiegiser.train.member.resp.PassengerQueryResp;
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

    @GetMapping("/query-list")
    public CommonResp<PageResp<PassengerQueryResp>> queryList(@Valid PassengerQueryReq req) {
        req.setMemberId(LoginMemberContext.getId());
        PageResp<PassengerQueryResp> list = passengerService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        passengerService.delete(id);
        return new CommonResp<>();
    }
}
