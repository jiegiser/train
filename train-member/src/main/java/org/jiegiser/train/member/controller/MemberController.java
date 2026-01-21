package org.jiegiser.train.member.controller;

import jakarta.annotation.Resource;
import org.jiegiser.train.member.mapper.MemberMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Resource
    private MemberMapper memberMapper;
    @GetMapping("/count")
    public Integer Count() {
        return memberMapper.count();
    }
}
