package org.jiegiser.train.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    Environment env;
    @GetMapping("/test")
    public String test() {
        return "business test" + ":" + env.getProperty("server.port");
    }
}
