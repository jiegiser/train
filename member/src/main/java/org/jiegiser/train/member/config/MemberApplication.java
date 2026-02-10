package org.jiegiser.train.member.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@SpringBootApplication
@ComponentScan(basePackages = {"org.jiegiser"})
@MapperScan("org.jiegiser.train.*.mapper")
public class MemberApplication {
    private static final Logger LOG = LoggerFactory.getLogger(MemberApplication.class);
    public static void main(String[] args) {
        // 计算程序执行时间
        Instant start = Instant.now();
        SpringApplication app = new SpringApplication(MemberApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("启动成功！！");
        LOG.info("地址: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
        CompletableFuture.runAsync(() -> {
           System.out.println("sss");
        }).exceptionally(ex -> {
            // 打印异常信息
            System.out.println(ex.toString());
            return null;
        }).whenComplete((result, error) -> {
            Duration duration = Duration.between(start, Instant.now());

            LOG.info("异步任务执行完成，耗时: {}ms", duration.toMillis());

            if (error != null) {
                LOG.error("异步任务执行失败", error);
            }
        });;

        // 调用方式
        executeTask(() -> System.out.println("Hello11"));
        executeTask(MemberApplication::someMethod);

        processData("Hello33", str -> System.out.println(str.toUpperCase()));
    }
    public static void executeTask(Runnable task) {
        task.run();
    }

    public static void someMethod() {
        System.out.println("Hello22");
    }

    public static void processData(String input, Consumer<String> processor) {
        processor.accept(input);
    }
}

