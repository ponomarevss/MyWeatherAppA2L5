package com.ponomarevss.myweatherapp;

public interface Constants {

    int INIT_INDEX = 15;
    long OBSOLESCENCE_TIME = 1000_000 * 60 * 30;

    String PLACE = "place";
    String SET_PLACE = "set place";

    //константы для shared preferences
    String INDEX = "index";
    String COORDINATES = "coordinates";
    String TEMPERATURE_DETAILS = "temperature details";
    String PRESSURE_AND_HUMIDITY = "atmospheric pressure and humidity";
    String VISIBILITY = "visibility";
    String WIND_SPEED_AND_DIRECTION = "wind speed and direction";
    String SUNRISE_AND_SUNSET = "sunrise and sunset";

    //ключи от SharedPreferences для сохранения состояния HomeFragment
    String DT2 = "dt2";
    String ID2 = "id2";
    String PLACE2 = "place2";
    String LAT2 = "lat2";
    String LON2 = "lon2";
    String ICON2 = "icon2";
    String TEMP2 = "temp2";
    String DESCR2 = "descr2";
    String FEELS2 = "feels2";
    String TMAX2 = "tmax2";
    String TMIN2 = "tmin2";
    String PRESS2 = "press2";
    String HUM2 = "hum2";
    String VIS2 = "vis2";
    String WSPEED2 = "wspeed2";
    String WDIR2 = "wdir2";
    String SRISE2 = "srise2";
    String SSET2 = "sset2";

    //константы для запросов
    String WEATHER_API_KEY = "024d1b99366c70a5b4bd878066cb6383";
 }
