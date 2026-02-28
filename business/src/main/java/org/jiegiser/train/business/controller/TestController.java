package org.jiegiser.train.business.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import jakarta.annotation.Resource;
import org.jiegiser.train.business.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    // @Autowired
    // Environment env;
    // @GetMapping("/test")
    // public String test() {
    //     return "business test" + ":" + env.getProperty("server.port");
    // }

    @Resource
    private TestService testService;

    @SentinelResource("test")
    @GetMapping("/test")
    public String test() throws InterruptedException {
        int i = RandomUtil.randomInt(1, 10);
        if (i <= 2) {
            throw new RuntimeException("测试异常");
        }
        return "Hello World! Business!";
    }

    @SentinelResource("test1")
    @GetMapping("/test1")
    public String test1() throws InterruptedException {
        testService.test2();
        return "Hello World! Business1!";
    }
}
