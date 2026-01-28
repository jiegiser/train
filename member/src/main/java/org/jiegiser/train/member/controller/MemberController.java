package org.jiegiser.train.member.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.jiegiser.train.common.resq.CommonResp;
import org.jiegiser.train.member.req.MemberRegistryReq;
import org.jiegiser.train.member.req.MemberSendCodeReq;
import org.jiegiser.train.member.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Resource
    private MemberService memberService;
    @GetMapping("/count")
    public CommonResp<Integer> Count() {
        int count = memberService.count();
        CommonResp<Integer> resp = new CommonResp<>();
        resp.setContent(count);
        return resp;
    }

    @PostMapping("/registry")
    public CommonResp<Long> registry(@Valid MemberRegistryReq req) {
        long registry = memberService.registry(req);
        return new CommonResp<>(registry);
    }

    @PostMapping("/send-code")
    public CommonResp<Long> registry(@Valid MemberSendCodeReq req) {
        memberService.sendCode(req);
        return new CommonResp<>();
    }
}
