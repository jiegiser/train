package org.jiegiser.train.business.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = {"org.jiegiser"})
@MapperScan("org.jiegiser.train.*.mapper")
@EnableFeignClients("org.jiegiser.train.business.feign")
// 开启 Spring boot 内置缓存的功能
@EnableCaching
public class BusinessApplication {
    private static final Logger LOG = LoggerFactory.getLogger(BusinessApplication.class);
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BusinessApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("启动成功！！");
        LOG.info("地址: \thttp://127.0.0.1:{}", env.getProperty("server.port"));

        // // 限流规则
        // initFlowRules();
        // LOG.info("已定义限流规则");
    }

    private static void initFlowRules(){
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("doConfirm");
        // FLOW_GRADE_QPS 表示 1 sec 允许的访问次数
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule.setCount(1);

        FlowRule rule1 = new FlowRule();
        rule1.setResource("stationQueryAll");
        // FLOW_GRADE_QPS 表示 1 sec 允许的访问次数
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule1.setCount(1);

        rules.add(rule);
        rules.add(rule1);
        FlowRuleManager.loadRules(rules);
    }
}
