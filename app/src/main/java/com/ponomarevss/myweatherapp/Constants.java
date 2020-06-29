package com.ponomarevss.myweatherapp;

public interface Constants {

    int INIT_INDEX = -1;
    String WEATHER_ICON_PREFIX = "wi_";

    String PLACE = "place";
    String SET_PLACE = "set place";

    //константы для shared preferences
    String INDEX = "index";
    String COORDINATES = "coordinates";
    String WEATHER_DESCRIPTION = "weather description";
    String TEMPERATURE_DETAILS = "temperature details";
    String PRESSURE_AND_HUMIDITY = "atmospheric pressure and humidity";
    String VISIBILITY = "visibility";
    String WIND_SPEED_AND_DIRECTION = "wind speed and direction";
    String SUNRISE_AND_SUNSET = "sunrise and sunset";
    String IS_DARK_THEME = "is dark theme";

    //константы для запросов
    String TAG = "weather";
    String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?id=%s&units=metric&appid=";
    String WEATHER_API_KEY = "024d1b99366c70a5b4bd878066cb6383";

 }
