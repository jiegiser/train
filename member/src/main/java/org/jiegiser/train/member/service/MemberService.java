package org.jiegiser.train.member.service;

import cn.hutool.core.collection.CollUtil;
import jakarta.annotation.Resource;
import org.jiegiser.train.member.domain.Member;
import org.jiegiser.train.member.domain.MemberExample;
import org.jiegiser.train.member.mapper.MemberMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    @Resource
    private MemberMapper memberMapper;
     public int count() {
         return Math.toIntExact(memberMapper.countByExample(null));
     }
     public long registry(String mobile) {
         MemberExample memberExample = new MemberExample();
         memberExample.createCriteria().andMobileEqualTo(mobile);
         List<Member> list = memberMapper.selectByExample(memberExample);
         if (CollUtil.isNotEmpty(list)) {
//             return list.get(0).getId();
             throw new RuntimeException("手机号已存在");
         }

         Member member = new Member();
         member.setId(System.currentTimeMillis());
         member.setMobile(mobile);
         memberMapper.insert(member);
         return member.getId();
     }
}
