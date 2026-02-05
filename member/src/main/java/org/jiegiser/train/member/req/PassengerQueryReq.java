package org.jiegiser.train.member.req;


import lombok.Data;
import org.jiegiser.train.common.req.PageReq;

@Data
public class PassengerQueryReq extends PageReq {

    private Long memberId;
}
