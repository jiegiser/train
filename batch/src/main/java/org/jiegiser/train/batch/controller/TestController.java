package org.jiegiser.train.batch.controller;

import jakarta.annotation.Resource;
import org.jiegiser.train.batch.feign.BusinessFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Resource
    private BusinessFeign businessFeign;
    @GetMapping("/test")
    public String test() {
        String result = businessFeign.test();
        System.out.println("result:" + result);
        return "batch test " + result;
    }
}
