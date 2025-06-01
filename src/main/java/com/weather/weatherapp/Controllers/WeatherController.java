package com.weather.weatherapp.Controllers;

import com.weather.weatherapp.model.WeatherResponse;
import com.weather.weatherapp.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class WeatherController {

    @Value("${weatherstack.api.key}") // Changed to match weatherstack
    private String apiKey;




    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/weather")
    public String getWeather(@RequestParam("city") String city,
                             @RequestParam(value = "country", required = false) String country,
                             Model model) {
        String query = city;
        if (country != null && !country.isEmpty()) {
            query = city + "," + country;
        }
        String url = "http://api.weatherstack.com/current?access_key=" + apiKey + "&query=" + query;

        try {

            RestTemplate restTemplate = new RestTemplate();
            WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);

            if (response != null && response.getCurrent() != null) {
                // Location data
                model.addAttribute("city", response.getLocation().getName());
                model.addAttribute("country", response.getLocation().getCountry());

                // Weather data
                model.addAttribute("temperature", response.getCurrent().getTemperature());
                model.addAttribute("humidity", response.getCurrent().getHumidity());
                model.addAttribute("windSpeed", response.getCurrent().getWind_speed());

                // Weather descriptions and icons
                if (!response.getCurrent().getWeather_descriptions().isEmpty()) {
                    model.addAttribute("description", response.getCurrent().getWeather_descriptions().get(0));
                }
                if (!response.getCurrent().getWeather_icons().isEmpty()) {
                    model.addAttribute("weatherIcon", response.getCurrent().getWeather_icons().get(0));
                }

            } else if (response != null && response.getError() != null) {
                model.addAttribute("error", response.getError().getInfo());
            } else {
                model.addAttribute("error", "Unable to fetch weather data");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error fetching weather data: " + e.getMessage());
        }

        return "weather";
    }
}