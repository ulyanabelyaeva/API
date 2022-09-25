package com.belyaeva;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class MainController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/")
    public String form(){
        return "index";
    }

    @PostMapping("/")
    public String weather(@RequestParam("city") String city, @RequestParam("btn") String btn, Model model) throws IOException,
            InterruptedException, JSONException {
        if (btn.equals("temp")){
            model.addAttribute("city", weatherService.getWeather(city));
            return "weather";
        } else {
            model.addAttribute("city", weatherService.getWeatherYandex(city));
            return "weatherYandex";
        }

    }
}
