package com.dubbo.learn.provider;

import com.dubbo.learn.DemoService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "Hello Dubbo";
    }
}
