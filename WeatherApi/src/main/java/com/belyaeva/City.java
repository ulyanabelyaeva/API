package com.belyaeva;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
public class City {

    private String city;

    private String country;

    private String lat;

    private String lon;

    private String weather;

    private String description;

    private String temp;

    private String sunrise;

    private String sunset;

    public City(String city, String country, String lat, String lon) {
        this.city = city;
        this.country = country;
        this.lat = lat;
        this.lon = lon;
        this.weather = "";
        this.description = "";
        this.temp = "";
        this.sunrise = "";
        this.sunset = "";
    }
}
