package org.jiegiser.train.member.controller;

import jakarta.annotation.Resource;
import org.jiegiser.train.member.prop.TestProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Resource
    private TestProperties testProperties;

    @GetMapping("/test")
    public String test() {
        return "member test " + testProperties.getNacos();
    }
}
