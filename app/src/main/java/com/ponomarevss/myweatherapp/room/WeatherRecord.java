package com.ponomarevss.myweatherapp.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"place", "temperature", "date"})})
public class WeatherRecord {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "place")
    public String place;

    @ColumnInfo(name = "temperature")
    public String temperature;

    @ColumnInfo(name = "date")
    public String date;
}
