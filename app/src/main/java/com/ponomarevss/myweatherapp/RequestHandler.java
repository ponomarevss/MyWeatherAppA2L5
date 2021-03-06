package com.ponomarevss.myweatherapp;

import com.ponomarevss.myweatherapp.rest.entities.WeatherRequestRestModel;

public class RequestHandler {

    private WeatherRequestRestModel response;

    public RequestHandler(WeatherRequestRestModel response) {
        this.response = response;
    }

    public String getIcon() {
        return response.weather[0].icon;
    }

    public String getWeatherDescription() {
        return response.weather[0].description;
    }

    public String getCoordLat() {
        return String.valueOf(response.coordinates.lat);
    }

    public String getCoordLon() {
        return String.valueOf(response.coordinates.lon);
    }

    public String getTemp() {
        return String.valueOf(response.main.temp);
    }

    public String getTempFeelsLike() {
        return String.valueOf(response.main.feelsLike);
    }
    public String getTempMax() {
        return String.valueOf(response.main.tempMax);
    }
    public String getTempMin() {
        return String.valueOf(response.main.tempMin);
    }

    public String getPressure() {
        return String.valueOf(response.main.pressure);
    }
    public String getHumidity() {
        return String.valueOf(response.main.humidity);
    }

    public String getSunrise() {
        return String.valueOf(response.sys.sunrise);
    }
    public String getSunset() {
        return String.valueOf(response.sys.sunset);
    }

    public String getVisibility() {
        return String.valueOf(response.visibility);
    }

    public String getWindSpeed() {
        return String.valueOf(response.wind.speed);
    }
    public String getWindDeg() {
        return String.valueOf(response.wind.deg);
    }
}
