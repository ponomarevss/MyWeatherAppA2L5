package com.ponomarevss.myweatherapp.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {WeatherRecord.class}, version = 1)
public abstract class WeatherDatabase extends RoomDatabase {
    public abstract WeatherDao getWeatherDao();
}
