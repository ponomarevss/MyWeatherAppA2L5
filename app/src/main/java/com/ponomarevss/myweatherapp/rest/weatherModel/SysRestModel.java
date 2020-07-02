package com.ponomarevss.myweatherapp.rest.weatherModel;

import com.google.gson.annotations.SerializedName;

public class SysRestModel {
    @SerializedName("type") public int type;
    @SerializedName("id") public int id;
    @SerializedName("country") public String country;
    @SerializedName("sunrise") public long sunrise;
    @SerializedName("sunset") public long sunset;

}
