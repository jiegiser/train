package org.jiegiser.train.member.service;

import jakarta.annotation.Resource;
import org.jiegiser.train.member.mapper.MemberMapper;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Resource
    private MemberMapper memberMapper;
     public int count() {
         return memberMapper.count();
     }
}
