package org.jiegiser.train;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrainApplication {

    public static void main(String[] args) {

        System.out.println("TrainApplication start...");
        SpringApplication.run(TrainApplication.class, args);
    }

}
