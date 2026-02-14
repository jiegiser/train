// package org.jiegiser.train.batch.config;// package com.jiawa.train.batch.config;
//
// import org.jiegiser.train.batch.job.TestJob;
// import org.quartz.*;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// @Configuration
// public class QuartzConfig {
//
//     /**
//      * 声明一个任务
//      * @return
//      */
//     @Bean
//     public JobDetail jobDetail() {
//         return JobBuilder.newJob(TestJob.class)
//                 .withIdentity("TestJob", "test")
//                 .storeDurably()
//                 .build();
//     }
//
//     /**
//      * 声明一个触发器，什么时候触发这个任务 -> 一般不会选用这种方式去做，
//      而是通过控制台界面去操作，也就是实现对应的接口然后进行任务的发起与暂停
//      * @return
//      */
//     @Bean
//     public Trigger trigger() {
//         return TriggerBuilder.newTrigger()
//                 .forJob(jobDetail())
//                 .withIdentity("trigger", "trigger")
//                 .startNow()
//                 .withSchedule(CronScheduleBuilder.cronSchedule("*/2 * * * * ?"))
//                 .build();
//     }
// }
