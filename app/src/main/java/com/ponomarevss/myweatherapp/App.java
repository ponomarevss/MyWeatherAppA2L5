package com.ponomarevss.myweatherapp;

import android.app.Application;

import androidx.room.Room;

import com.ponomarevss.myweatherapp.room.WeatherDao;
import com.ponomarevss.myweatherapp.room.WeatherDatabase;

public class App extends Application {

    private static App instance;

    private WeatherDatabase weatherDatabase;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        weatherDatabase = Room
                .databaseBuilder(getApplicationContext(), WeatherDatabase.class, "weather_database")
                .allowMainThreadQueries()
                .build();
    }

    public WeatherDao getWeatherDao() {
        return weatherDatabase.getWeatherDao();
    }
}
