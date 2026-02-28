package org.jiegiser.train.business.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @SentinelResource("test2")
    public void test2() throws InterruptedException {
        Thread.sleep(500);
    }
}
