package org.jiegiser.train.member.prop;/*
 * @Auther: changjie
 * @Date:2026/2/26
 * @Description:
 * @VERSON:1.8
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "test") // 注意 prefix 要小写，和配置中的key对应
@RefreshScope // 核心：开启配置刷新
@Data
public class TestProperties {
    private String nacos;
}
