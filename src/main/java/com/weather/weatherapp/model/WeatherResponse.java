package com.weather.weatherapp.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class WeatherResponse {
    // Getters and setters
    private Location location;
    private Current current;
    private Error error;

    @Setter
    @Getter
    public static class Location {
        // Getters and setters
        private String name;
        private String country;
        private String region;

    }

    @Setter
    @Getter
    public static class Current {
        // Getters and setters
        private double temperature;
        private int humidity;
        private double wind_speed;
        private List<String> weather_descriptions;
        private List<String> weather_icons;

    }

    @Setter
    @Getter
    public static class Error {
        private String info;

    }
}