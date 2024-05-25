package com.example.demo.proj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensor")
public class SensorController {

    @Autowired
    private SensorDataRepository repository;

    @PostMapping
    public SensorData addSensorData(@RequestBody SensorData sensorData) {
        repository.save(sensorData);
        return sensorData;
    }

    @GetMapping
    public List<SensorData> getAllSensorData() {
        return repository.findAll();
    }
}