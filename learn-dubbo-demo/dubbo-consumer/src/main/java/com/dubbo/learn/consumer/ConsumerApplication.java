package com.dubbo.learn.consumer;

import com.dubbo.learn.DemoService;
import com.tts.remote.dto.CoordinatePointResultDto;
import com.tts.remote.dto.IovConfigDto;
import com.tts.remote.dto.IovSubscribeTaskVehicleDto;
import com.tts.remote.dto.IovVehicleQueryDto;
import com.tts.remote.enums.IovTypeEnums;
import com.tts.remote.service.SystemRemoteService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

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

//        System.out.println(application.removeSubscribeTaskVehicle());

//        System.out.println(application.queryIovVehicleTrackDirectly().size());

        System.out.println(application.queryIovVehicleLastLocationDirectly().size());
    }

    public List<CoordinatePointResultDto> queryIovVehicleLastLocationDirectly() {
        IovVehicleQueryDto queryDto = new IovVehicleQueryDto();
        queryDto.setIovTypeEnum(IovTypeEnums.G7);
        queryDto.setVehicleNo("苏H1864P");
        queryDto.setTimeStart(parseTime("2022-07-13 22:15:01"));
        queryDto.setTimeEnd(parseTime("2022-07-20 22:14:59"));

        return systemRemoteService.queryIovVehicleLastLocationDirectly(queryDto);
    }

    public List<CoordinatePointResultDto> queryIovVehicleTrackDirectly() {
        IovVehicleQueryDto queryDto = new IovVehicleQueryDto();
        queryDto.setIovTypeEnum(IovTypeEnums.G7);
        queryDto.setVehicleNo("苏H1864P");
        queryDto.setTimeStart(parseTime("2022-07-13 22:15:01"));
        queryDto.setTimeEnd(parseTime("2022-07-20 22:14:59"));


        return systemRemoteService.queryIovVehicleTrackDirectly(queryDto);
    }

    private LocalDateTime parseTime(String time) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
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
        return systemRemoteService.startSubscribeTask("002", "G7");
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
