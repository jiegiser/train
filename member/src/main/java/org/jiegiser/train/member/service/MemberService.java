package org.jiegiser.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jiegiser.train.common.exception.BusinessException;
import org.jiegiser.train.common.exception.BusinessExceptionEnum;
import org.jiegiser.train.common.util.JwtUtil;
import org.jiegiser.train.common.util.SnowUtil;
import org.jiegiser.train.member.domain.Member;
import org.jiegiser.train.member.domain.MemberExample;
import org.jiegiser.train.member.mapper.MemberMapper;
import org.jiegiser.train.member.req.MemberLoginReq;
import org.jiegiser.train.member.req.MemberRegistryReq;
import org.jiegiser.train.member.req.MemberSendCodeReq;
import org.jiegiser.train.member.resp.MemberLoginResp;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MemberService {
    @Resource
    private MemberMapper memberMapper;
     public int count() {
         return Math.toIntExact(memberMapper.countByExample(null));
     }
     public long registry(MemberRegistryReq req) {
        String mobile = req.getMobile();
        Member memberDB = selectByMobile(mobile);
        if (ObjectUtil.isNotNull(memberDB)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }

        Member member = new Member();
        member.setId(SnowUtil.getSnowflakeNextId());
        member.setMobile(req.getMobile());
        memberMapper.insert(member);
        return member.getId();
    }

    public void sendCode(MemberSendCodeReq req) {
        String mobile = req.getMobile();
        Member memberDB = selectByMobile(mobile);

        //  如果手机号不存在，则插入一条记录
        if (ObjectUtil.isNull(memberDB)) {
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

    public MemberLoginResp login(MemberLoginReq req) {
         String mobile = req.getMobile();
         String code = req.getCode();
         Member memberDB = selectByMobile(mobile);


        // 如果手机号不存在，则插入一条记录
        if (ObjectUtil.isNull(memberDB)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_NOT_EXIST);
        }

        // 校验短信验证码
        if (!"8888".equals(code)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_CODE_ERROR);
        }

        // 不应该直接返回数据库实体，会存在密码这些敏感信息，需要自定义返回类型
        MemberLoginResp memberLoginResp = BeanUtil.copyProperties(memberDB, MemberLoginResp.class);
        // Map<String, Object> map = BeanUtil.beanToMap(memberLoginResp);
        String token = JwtUtil.createToken(memberLoginResp.getId(), memberLoginResp.getMobile());
        memberLoginResp.setToken(token);
        return memberLoginResp;
    }

    private Member selectByMobile(String mobile) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> list = memberMapper.selectByExample(memberExample);
        if (CollUtil.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
