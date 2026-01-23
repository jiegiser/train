package org.jiegiser.train.member.controller;

import jakarta.annotation.Resource;
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
    public Integer Count() {
        return Math.toIntExact(memberService.count());
    }

    @PostMapping("/registry")
    public Long registry(String mobile) {
        return memberService.registry(mobile);
    }
}
