package org.jiegiser.train.member.service;

import cn.hutool.core.collection.CollUtil;
import jakarta.annotation.Resource;
import org.jiegiser.train.common.exception.BusinessException;
import org.jiegiser.train.member.domain.Member;
import org.jiegiser.train.member.domain.MemberExample;
import org.jiegiser.train.member.mapper.MemberMapper;
import org.jiegiser.train.member.req.MemberRegistryReq;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.jiegiser.train.common.exception.BusinessExceptionEnum.MEMBER_MOBILE_EXIST;

@Service
public class MemberService {
    @Resource
    private MemberMapper memberMapper;
     public int count() {
         return Math.toIntExact(memberMapper.countByExample(null));
     }
     public long registry(MemberRegistryReq req) {
         MemberExample memberExample = new MemberExample();
         memberExample.createCriteria().andMobileEqualTo(req.getMobile());
         List<Member> list = memberMapper.selectByExample(memberExample);
         if (CollUtil.isNotEmpty(list)) {
             throw new BusinessException(MEMBER_MOBILE_EXIST);
         }

         Member member = new Member();
         member.setId(System.currentTimeMillis());
         member.setMobile(req.getMobile());
         memberMapper.insert(member);
         return member.getId();
     }
}
