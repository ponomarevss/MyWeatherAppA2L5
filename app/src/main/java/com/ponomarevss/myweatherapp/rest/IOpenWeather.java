package com.ponomarevss.myweatherapp.rest;

import com.ponomarevss.myweatherapp.rest.weatherModel.WeatherRequestRestModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeather {
    @GET("data/2.5/weather")
    Call<WeatherRequestRestModel> loadWeather(@Query("id") String cityId,
                                              @Query("appid") String keyApi,
                                              @Query("units") String units);
}
