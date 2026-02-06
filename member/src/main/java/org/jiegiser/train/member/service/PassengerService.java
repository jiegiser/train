package org.jiegiser.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jiegiser.train.common.context.LoginMemberContext;
import org.jiegiser.train.common.resp.PageResp;
import org.jiegiser.train.common.util.SnowUtil;
import org.jiegiser.train.member.domain.Passenger;
import org.jiegiser.train.member.domain.PassengerExample;
import org.jiegiser.train.member.mapper.PassengerMapper;
import org.jiegiser.train.member.req.PassengerQueryReq;
import org.jiegiser.train.member.req.PassengerSaveReq;
import org.jiegiser.train.member.resp.PassengerQueryResp;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PassengerService {
    @Resource
    private PassengerMapper passengerMapper;
    public void save(PassengerSaveReq req) {
        DateTime now = DateTime.now();
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
        // 是否为编辑
        if (ObjectUtil.isNull(passenger.getId())) {
            // 获取线程本地变量的值
            passenger.setMemberId(LoginMemberContext.getId());
            passenger.setId(SnowUtil.getSnowflakeNextId());
            passenger.setCreateTime(now);
            passenger.setUpdateTime(now);
            passengerMapper.insert(passenger);
        } else {
            // 更新
            passenger.setUpdateTime(now);
            passengerMapper.updateByPrimaryKey(passenger);
        }
    }

    public PageResp<PassengerQueryResp> queryList(PassengerQueryReq req) {
        PassengerExample passengerExample = new PassengerExample();
        passengerExample.setOrderByClause("id desc");
        PassengerExample.Criteria criteria = passengerExample.createCriteria();
        if (ObjectUtil.isNotNull(req.getMemberId())) {
            criteria.andMemberIdEqualTo(req.getMemberId());
        }

        log.info("查询页码：{}", req.getPage());
        log.info("每页条数：{}", req.getSize());
        // 开始分页，页码是从 1 开始；在执行 sql 语句的上一行加上这个代码即可
        // 对下面一句的 sql 做拦截，增加分页 limit
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Passenger> passengerList = passengerMapper.selectByExample(passengerExample);

        // 获取分页结果 - 自动生成 count 查询总数
        PageInfo<Passenger> pageInfo = new PageInfo<>(passengerList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<PassengerQueryResp> list = BeanUtil.copyToList(passengerList, PassengerQueryResp.class);

        PageResp<PassengerQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        passengerMapper.deleteByPrimaryKey(id);
    }
}
