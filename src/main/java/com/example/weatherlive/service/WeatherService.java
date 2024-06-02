package com.example.weatherlive.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WeatherService {

    private static final String API_KEY = "5b1754515a608029bb99245d1dc8a659";
    private static final String URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";
    private final OkHttpClient httpClient = new OkHttpClient();

    public String getWeather(String city) throws IOException {
        String fullUrl = String.format(URL, city, API_KEY);
        Request request = new Request.Builder().url(fullUrl).build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response.code());
            }

            return response.body().string();
        }
    }
}
