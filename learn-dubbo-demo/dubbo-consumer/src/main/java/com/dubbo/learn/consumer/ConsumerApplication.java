package com.dubbo.learn.consumer;

import com.dubbo.learn.DemoService;
import com.tts.remote.dto.IovConfigDto;
import com.tts.remote.dto.IovSubscribeTaskVehicleDto;
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

//        System.out.println(application.doInitialIovConfig());

//        System.out.println(application.doStartSubscribeTask());

//        System.out.println(application.doStopSubscribeTask());

//        System.out.println(application.addSubscribeTaskVehicle());

        System.out.println(application.removeSubscribeTaskVehicle());
    }

    public boolean removeSubscribeTaskVehicle() {
        IovSubscribeTaskVehicleDto taskVehicleDto = new IovSubscribeTaskVehicleDto();
        taskVehicleDto.setIovType("G7");
        taskVehicleDto.setCarrierCode("001");
        taskVehicleDto.setVehicleNo("冀B5P185");

        return systemRemoteService.removeVehicleTask(taskVehicleDto);
    }

    public boolean addSubscribeTaskVehicle() {
        IovSubscribeTaskVehicleDto taskVehicleDto = new IovSubscribeTaskVehicleDto();
        taskVehicleDto.setIovType("G7");
        taskVehicleDto.setCarrierCode("001");
        taskVehicleDto.setVehicleNo("冀B5P185");

        return systemRemoteService.addVehicleTask(taskVehicleDto);
    }

    public boolean doStopSubscribeTask() {
        return systemRemoteService.stopSubscribeTask("001", "G7");
    }

    public boolean doStartSubscribeTask() {
        return systemRemoteService.startSubscribeTask("001", "G7");
    }

    public boolean doInitialIovConfig() {
        String content = "{\n" +
                "    \"accessId\": \"123\",\n" +
                "    \"secretKey\": \"123\",\n" +
                "    \"baseUrl\": \"openapi.huoyunren.com\"\n" +
                "}";
        IovConfigDto iovConfigDto = new IovConfigDto();

        iovConfigDto.setIovType("E6");
        iovConfigDto.setConfigInfo(content);

        return systemRemoteService.saveOrUpdateIovConfig(iovConfigDto);
    }

    public String doSayHello(String name) {
        return demoService.sayHello(name);
    }
}
