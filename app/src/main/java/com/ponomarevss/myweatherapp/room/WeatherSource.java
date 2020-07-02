package com.ponomarevss.myweatherapp.room;

import java.util.List;

public class WeatherSource {

    private final WeatherDao weatherDao;

    private List<WeatherRecord> records;

    public WeatherSource(WeatherDao weatherDao) {
        this.weatherDao = weatherDao;
    }

    public List<WeatherRecord> getWeatherRecords() {
        if (records == null) {
            loadWeatherRecords();
        }
        return records;
    }

    public void loadWeatherRecords() {
        records = weatherDao.getAllWeatherRecords();
    }

    public long getWeatherRecordsCount() {
        return weatherDao.getWeatherRecordsCount();
    }

    public void addWeatherRecord(WeatherRecord record) {
        weatherDao.insertWeatherRecord(record);
        loadWeatherRecords();
    }

    public void updateWeatherRecord(WeatherRecord record) {
        weatherDao.updateWeatherRecord(record);
        loadWeatherRecords();
    }

    public void removeWeatherRecord(long id) {
        weatherDao.deleteWeatherRecordById(id);
        loadWeatherRecords();
    }
}
