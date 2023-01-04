package com.dubbo.learn.consumer;

import com.dubbo.learn.DemoService;
import com.tts.remote.dto.IovConfigDto;
import com.tts.remote.service.SystemRemoteService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

@SpringBootApplication
@Service
@EnableDubbo
public class ConsumerApplication {

    @DubboReference
    private DemoService demoService;
    @DubboReference
    private SystemRemoteService systemRemoteService;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ConsumerApplication.class, args);
        ConsumerApplication application = context.getBean(ConsumerApplication.class);
        String content = "{\n" +
                "    \"accessId\": \"123\",\n" +
                "    \"secretKey\": \"123\",\n" +
                "    \"baseUrl\": \"openapi.huoyunren.com\"\n" +
                "}";
        IovConfigDto iovConfigDto = new IovConfigDto();

        iovConfigDto.setIovType("G7");
        iovConfigDto.setConfigInfo(content);

        System.out.println(application.doInitialIovConfig(iovConfigDto));
    }

    public boolean doInitialIovConfig(IovConfigDto iovConfigDto) {
        return systemRemoteService.saveOrUpdateIovConfig(iovConfigDto);
    }

    public String doSayHello(String name) {
        return demoService.sayHello(name);
    }
}
