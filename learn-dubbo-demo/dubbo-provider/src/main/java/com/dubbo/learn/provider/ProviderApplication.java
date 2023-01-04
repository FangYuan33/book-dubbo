package com.dubbo.learn.provider;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@EnableDubbo
public class ProviderApplication {
    public static void main(String[] args) throws Exception {
        new EmbeddedZooKeeper(2181, false).start();

        SpringApplication.run(ProviderApplication.class, args);
        System.out.println("dubbo service started");
        new CountDownLatch(1).await();
    }
}
