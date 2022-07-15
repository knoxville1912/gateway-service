package com.example.gateway.service;

import com.example.gateway.model.LifeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class GeneratedLifeData {

    @Autowired
    private RestTemplate restTemplate;

    public List<LifeData> generateLifeData() {
        List<Long> patientId = getAllPatientIdWithLifeData();
        List<LifeData> lifeDataList = new ArrayList<>();
        Random random = new Random();
        DecimalFormat dF = new DecimalFormat("#.##");

        for (Long aLong : patientId) {
            double temperature;
            int diastolicPressure;
            int systolicPressure;
            int saturation;
            int pulse;
            if ((random.nextInt(100) + 1) < 2) {
                temperature = (ThreadLocalRandom.current().nextDouble(39.01, 41.00));
            } else {
                temperature = (ThreadLocalRandom.current().nextDouble(35.40, 39.00));
            }
            if ((random.nextInt(100) + 1) < 2) {
                diastolicPressure = random.nextInt(21) + 100;
            } else {
                diastolicPressure = random.nextInt(51) + 50;
            }
            if ((random.nextInt(100) + 1) < 2) {
                systolicPressure = random.nextInt(31) + 160;
            } else {
                systolicPressure = random.nextInt(71) + 90;
            }
            if ((random.nextInt(100) + 1) < 2) {
                saturation = random.nextInt(43) + 50;
            } else {
                saturation = random.nextInt(8) + 92;
            }
            if ((random.nextInt(100) + 1) < 2) {
                pulse = random.nextInt(71) + 150;
            } else {
                pulse = random.nextInt(101) + 50;
            }
            temperature = Double.parseDouble(dF.format(temperature).replace(",", "."));
            lifeDataList.add(new LifeData(
                    LocalDateTime.now().withNano(0),
                    temperature,
                    diastolicPressure,
                    systolicPressure,
                    saturation,
                    pulse,
                    aLong
            ));
        }
        return lifeDataList;
    }

    private List<Long> getAllPatientIdWithLifeData() {
        return restTemplate.exchange(
                "http://localhost:8081/regular/get-all-patient-id-with-life-data",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Long>>() {
                }
        ).getBody();
    }

    public boolean isDataEmergency(List<LifeData> lifeDataList) {
        for (LifeData lifeData : lifeDataList) {
            if (lifeData.getTemperature() > 39.00 ||
                    lifeData.getDiastolicPressure() > 100 ||
                    lifeData.getSystolicPressure() > 160 ||
                    lifeData.getSaturation() < 92 ||
                    lifeData.getPulse() > 150) {
                return true;
            }
        }
        return false;
    }

    public List<LifeData> dataEmergency(List<LifeData> lifeDataList) {
        List<LifeData> lifeDataEmergencyList = new ArrayList<>();
        for (LifeData lifeData : lifeDataList) {
            if (lifeData.getTemperature() > 39.00 ||
                    lifeData.getDiastolicPressure() > 100 ||
                    lifeData.getSystolicPressure() > 160 ||
                    lifeData.getSaturation() < 92 ||
                    lifeData.getPulse() > 150) {
                lifeDataEmergencyList.add(lifeData);
            }
        }
        return lifeDataEmergencyList;
    }

    public List<LifeData> dataUsual(List<LifeData> lifeDataList) {
        List<LifeData> lifeDataUsualList = new ArrayList<>();
        for (LifeData lifeData : lifeDataList) {
            if (lifeData.getTemperature() <= 39.00 &&
                    lifeData.getDiastolicPressure() <= 100 &&
                    lifeData.getSystolicPressure() <= 160 &&
                    lifeData.getSaturation() >= 92 &&
                    lifeData.getPulse() <= 150) {
                lifeDataUsualList.add(lifeData);
            }
        }
        return lifeDataUsualList;
    }
}
