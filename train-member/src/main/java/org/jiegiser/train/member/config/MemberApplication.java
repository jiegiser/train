package org.jiegiser.train.member.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.jiegiser"})
public class MemberApplication {

    public static void main(String[] args) {

        System.out.println("MemberApplication start...");
        SpringApplication.run(MemberApplication.class, args);
    }

}
