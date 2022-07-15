package com.example.gateway.controller;

import com.example.gateway.model.LifeData;
import com.example.gateway.service.GeneratedLifeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gateway")
public class GatewayController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private GeneratedLifeData generatedLifeData;

    @GetMapping("/generate-life-data")
    public List<LifeData> generateLifeData() {
        List<LifeData> lifeDataList = generatedLifeData.generateLifeData();
        jmsTemplate.convertAndSend("Test", lifeDataList.toString());
        return lifeDataList;
    }
}
