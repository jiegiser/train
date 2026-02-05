package org.jiegiser.train.member.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.jiegiser.train.common.resp.CommonResp;
import org.jiegiser.train.member.req.MemberLoginReq;
import org.jiegiser.train.member.req.MemberRegistryReq;
import org.jiegiser.train.member.req.MemberSendCodeReq;
import org.jiegiser.train.member.resp.MemberLoginResp;
import org.jiegiser.train.member.service.MemberService;
import org.springframework.web.bind.annotation.*;

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

    // json body
    @PostMapping("/registry")
    public CommonResp<Long> registry(@Valid @RequestBody MemberRegistryReq req) {
        long registry = memberService.registry(req);
        return new CommonResp<>(registry);
    }

    // formdata body
    @PostMapping("/send-code")
    public CommonResp<Long> registry(@Valid MemberSendCodeReq req) {
        memberService.sendCode(req);
        return new CommonResp<>();
    }

    @PostMapping("/login")
    public CommonResp<MemberLoginResp> login(@Valid @RequestBody MemberLoginReq req) {
        MemberLoginResp resp = memberService.login(req);
        return new CommonResp<>(resp);
    }
}
