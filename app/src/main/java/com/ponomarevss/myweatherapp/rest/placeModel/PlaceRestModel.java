package com.ponomarevss.myweatherapp.rest.placeModel;

import com.google.gson.annotations.SerializedName;
import com.ponomarevss.myweatherapp.rest.weatherModel.CoordRestModel;

public class PlaceRestModel {
    @SerializedName("id") public long id;
    @SerializedName("name") public String name;
    @SerializedName("country") public String country;
    @SerializedName("coord") public CoordRestModel coordinates;

}
