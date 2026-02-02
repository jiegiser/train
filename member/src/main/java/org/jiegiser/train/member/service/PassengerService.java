package org.jiegiser.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import org.jiegiser.train.common.util.SnowUtil;
import org.jiegiser.train.member.domain.Passenger;
import org.jiegiser.train.member.mapper.PassengerMapper;
import org.jiegiser.train.member.req.PassengerSaveReq;
import org.springframework.stereotype.Service;

@Service
public class PassengerService {
    @Resource
    private PassengerMapper passengerMapper;
    public void save(PassengerSaveReq req) {
        DateTime now = DateTime.now();
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
        if (ObjectUtil.isNull(passenger.getId())) {
            passenger.setMemberId(SnowUtil.getSnowflakeNextId());
            passenger.setId(SnowUtil.getSnowflakeNextId());
            passenger.setCreateTime(now);
            passenger.setUpdateTime(now);
            passengerMapper.insert(passenger);
        } else {
            passenger.setUpdateTime(now);
            passengerMapper.updateByPrimaryKey(passenger);
        }
    }
}
