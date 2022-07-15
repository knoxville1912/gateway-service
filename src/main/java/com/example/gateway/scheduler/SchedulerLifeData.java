package com.example.gateway.scheduler;

import com.example.gateway.model.LifeData;
import com.example.gateway.service.GeneratedLifeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class SchedulerLifeData {

    @Autowired
    private GeneratedLifeData generatedLifeData;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 5000)
    public void generateLifeDataByScheduler() {
        List<LifeData> lifeDataList = generatedLifeData.generateLifeData();
        if (!generatedLifeData.dataEmergency(lifeDataList).isEmpty()) {
            restTemplate.postForObject(
                    "http://localhost:8083/emergency/",
                    generatedLifeData.dataEmergency(lifeDataList),
                    List.class
            );
        }
        jmsTemplate.convertAndSend("LifeDataUsual", lifeDataList);
//        restTemplate.postForObject(
//                "http://localhost:8081/medicine/regular/update-life-data-with-rest-by-scheduler",
//                lifeDataList,
//                List.class
//        );
    }
}
