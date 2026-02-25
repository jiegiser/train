package org.jiegiser.train.member.controller.admin;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.jiegiser.train.common.resp.CommonResp;
import org.jiegiser.train.common.resp.PageResp;
import org.jiegiser.train.member.req.TicketQueryReq;
import org.jiegiser.train.member.resp.TicketQueryResp;
import org.jiegiser.train.member.service.TicketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/ticket")
public class TicketAdminController {

    @Resource
    private TicketService ticketService;

    @GetMapping("/query-list")
    public CommonResp<PageResp<TicketQueryResp>> queryList(@Valid TicketQueryReq req) {
        PageResp<TicketQueryResp> list = ticketService.queryList(req);
        return new CommonResp<>(list);
    }

}
