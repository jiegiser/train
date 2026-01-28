package org.jiegiser.train.member.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jiegiser.train.common.exception.BusinessException;
import org.jiegiser.train.common.util.SnowUtil;
import org.jiegiser.train.member.domain.Member;
import org.jiegiser.train.member.domain.MemberExample;
import org.jiegiser.train.member.mapper.MemberMapper;
import org.jiegiser.train.member.req.MemberRegistryReq;
import org.jiegiser.train.member.req.MemberSendCodeReq;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.jiegiser.train.common.exception.BusinessExceptionEnum.MEMBER_MOBILE_EXIST;

@Service
@Slf4j
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
        member.setId(SnowUtil.getSnowflakeNextId());
        member.setMobile(req.getMobile());
        memberMapper.insert(member);
        return member.getId();
    }

    public void sendCode(MemberSendCodeReq req) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(req.getMobile());
        List<Member> list = memberMapper.selectByExample(memberExample);

        //  如果手机号不存在，则插入一条记录
        if (CollUtil.isEmpty(list)) {
            log.info("手机号不存在，插入一条记录");
            Member member = new Member();
            member.setId(SnowUtil.getSnowflakeNextId());
            member.setMobile(req.getMobile());
            memberMapper.insert(member);
        } else {
            log.info("手机号已存在");
        }


        // 生成验证码
        String code = RandomUtil.randomNumbers(4);
        log.info("生成短信验证码：{}", code);
        // 保存短信记录表：手机号、短信验证码、有效期、是否已使用、业务类型、发送时间、使用时间
        log.info("保存短信记录表");
        // 对接短信通道
        log.info("对接短信通道");
    }
}
