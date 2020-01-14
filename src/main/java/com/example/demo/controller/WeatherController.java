package com.example.demo.controller;

import com.example.demo.model.WeatherRequest;
import com.example.demo.model.dto.WeatherDto;
import com.example.demo.service.WeatherService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping(value = "/health", produces = "application/json")
    public ResponseEntity getHealth() {
        return ResponseEntity.ok(new ImmutableMap.Builder<String, String>()
                .put("status", "UP")
                .build());
    }

    @GetMapping(value = "/weather", produces = "application/json")
    public ResponseEntity<WeatherDto> getWeatherByCity(
            @RequestParam(value = "city") String city) {
        return ResponseEntity.ok().body(weatherService.getWeather(city));
    }

    @PostMapping(value = "/weather", produces = "application/json", consumes = "application/json")
    public ResponseEntity sendWeatherToUser(
            @RequestBody WeatherRequest weatherRequest) {
        weatherService.getWeather(weatherRequest.getCity(), weatherRequest.getName(),weatherRequest.getEmail());
        return ResponseEntity.ok().body("Your email will be sent in the next minute");
    }
}
