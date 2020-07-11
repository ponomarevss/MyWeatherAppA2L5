package com.ponomarevss.myweatherapp.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWeatherRecord(WeatherRecord weatherRecord);

    @Update
    void updateWeatherRecord(WeatherRecord weatherRecord);

    @Delete
    void deleteWeatherRecord(WeatherRecord weatherRecord);

    @Query("DELETE FROM WeatherRecord WHERE id = :id")
    void deleteWeatherRecordById(long id);

    @Query("SELECT * FROM WeatherRecord")
    List<WeatherRecord> getAllWeatherRecords();

    @Query("SELECT * FROM WeatherRecord WHERE id = :id")
    WeatherRecord getWeatherRecordById(long id);

    @Query("SELECT COUNT() FROM WeatherRecord")
    long getWeatherRecordsCount();
}
