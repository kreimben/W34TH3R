package com.Kreimben;

import java.io.*;
import java.net.*;

import org.json.simple.*;
import org.json.simple.parser.*;
import org.json.simple.parser.JSONParser;

public class WTNetworkManager {
    private static WTNetworkManager instance;
    public static WTNetworkManager getInstance() {
        if (instance == null) instance = new WTNetworkManager();
        return instance;
    }

    public JSONObject fetchWeather(String cityName) throws Exception {
        var urlString = "https://api.openweathermap.org/data/2.5/weather" +
                "?" +
                "q=" + cityName +
                "&" +
                "appid=" + WTNetworkAPIKey.OpenWeatherApiKey +
                "&" +
                "units=" + "metric" +
                "&" +
                "lang=" + "kr";
        var obj = new URL(urlString);

        var connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10 * 1000);
        connection.getResponseCode();

        connection.connect();

        //System.out.format("URL is: %s\n", urlString);

        var br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        var result = parseJSONData(br);

        return result;
    }

    public JSONObject searchCities(String cityName) throws Exception {
        var urlString = "https://wft-geo-db.p.rapidapi.com/v1/geo/cities" +
                "?" +
                "namePrefix=" + cityName + "&" +
                "sort=" + "name" + "&" +
                "limit=" + "10";
        var obj = new URL(urlString);
        System.out.format("URL is %s\n", urlString);

        var connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10 * 1000);

        connection.setRequestProperty("x-rapidapi-key", WTNetworkAPIKey.RapidApiKey);
        connection.setRequestProperty("x-rapidapi-host", "wft-geo-db.p.rapidapi.com");
        connection.setRequestProperty("useQueryString", "true");

        connection.connect();

        var br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        var result = parseJSONData(br);

        return result;
    }

    private WTNetworkManager() { }

    private JSONObject parseJSONData(BufferedReader br) {

        JSONObject result = null;

        try {
            var jp = new JSONParser();
            var jsonObject = (JSONObject)jp.parse(br);
            result = jsonObject;

        } catch (Exception e) { e.getStackTrace(); }

        return result;
    }
}
