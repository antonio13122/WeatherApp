package com.example.weatherlive.controller;

import com.example.weatherlive.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
@CrossOrigin
@RestController
@RequestMapping("/api")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather")
    public String getWeather(@RequestParam String city) {
        try {
            return weatherService.getWeather(city);
        } catch (IOException e) {
            return "Error fetching weather data: " + e.getMessage();
        }
    }
}
