package org.jiegiser.train.batch.job;/*
 * @Auther: changjie
 * @Date:2026/2/13
 * @Description:
 * @VERSON:1.8
 */

/**
 * 适合单体应用，不适合集群
 * 没法实时更改定时任务状态和策略
 */
// @Component
// @EnableScheduling
public class SpringBootTestJob {
    // @Scheduled(cron = "0/5 * * * * ?")
    private void test() {
        // 增加分布式锁，解决集群问题
        System.out.println("job test");
    }
}
