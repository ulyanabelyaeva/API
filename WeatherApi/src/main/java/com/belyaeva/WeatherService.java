package com.belyaeva;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.*;

import java.io.IOException;

@Service
public class WeatherService {

    private final String APIkey = "2be7a8f0ddc7c6b1066698cd4d7c79ab";

    private final String yandexApiKey = "a03ddd44-ba54-4250-afcb-be71676455ca";

    public City getWeather(String cityName) throws IOException, InterruptedException, JSONException {

        //запрос на получение координат города
        String urlStringForCityGeo = "http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=1&appid=%s";
        urlStringForCityGeo = String.format(urlStringForCityGeo, cityName, APIkey);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlStringForCityGeo))
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        JSONArray jsonArray = new JSONArray(response.body());
        JSONObject jsonObject = jsonArray.getJSONObject(0);

        City city = new City(
                jsonObject.getString("name"),
                jsonObject.getString("country"),
                jsonObject.getString("lat"),
                jsonObject.getString("lon")
        );


        //запрос на получение текущей погоды
        String urlStringForWeather = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s";
        urlStringForWeather = String.format(urlStringForWeather, city.getLat(), city.getLon(), APIkey);

        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(URI.create(urlStringForWeather))
                .GET()
                .build();
        HttpResponse<String> response2 = httpClient.send(request2, HttpResponse.BodyHandlers.ofString());


        jsonObject = new JSONObject(response2.body());
        JSONArray weather = jsonObject.getJSONArray("weather");
        city.setWeather(weather.getJSONObject(0).getString("main"));
        city.setDescription(weather.getJSONObject(0).getString("description"));
        JSONObject temp = jsonObject.getJSONObject("main");

        city.setTemp(String.valueOf(Float.parseFloat(temp.getString("temp")) - 273));

        return city;
    }

    public City getWeatherYandex(String cityName) throws IOException, InterruptedException, JSONException {

        //запрос на получение координат города
        String urlStringForCityGeo = "http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=1&appid=%s";
        urlStringForCityGeo = String.format(urlStringForCityGeo, cityName, APIkey);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlStringForCityGeo))
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        JSONArray jsonArray = new JSONArray(response.body());
        JSONObject jsonObject = jsonArray.getJSONObject(0);

        City city = new City(
                jsonObject.getString("name"),
                jsonObject.getString("country"),
                jsonObject.getString("lat"),
                jsonObject.getString("lon")
        );


        //запрос на получение погоды от Яндекс
        String urlStringForCityWeather = "https://api.weather.yandex.ru/v2/forecast?lat=%s&lon=%s";
        urlStringForCityWeather = String.format(urlStringForCityWeather, city.getLat(), city.getLon());

        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(URI.create(urlStringForCityWeather))
                .header("X-Yandex-API-Key", yandexApiKey)
                .GET()
                .build();
        HttpResponse<String> response2 = httpClient.send(request2, HttpResponse.BodyHandlers.ofString());


        jsonObject = new JSONObject(response2.body());
        JSONObject tempObject = jsonObject.getJSONObject("fact");

        city.setTemp(tempObject.getString("temp"));
        city.setWeather(tempObject.getString("condition"));
        city.setDescription(tempObject.getString("season"));

        JSONArray temp2Object = jsonObject.getJSONArray("forecasts");
        city.setSunrise(temp2Object.getJSONObject(0).getString("sunrise"));
        city.setSunset(temp2Object.getJSONObject(0).getString("sunset"));

        return city;
    }
}
