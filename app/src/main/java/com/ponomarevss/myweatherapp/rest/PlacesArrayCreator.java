package com.ponomarevss.myweatherapp.rest;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ponomarevss.myweatherapp.rest.placeModel.PlaceRestModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PlacesArrayCreator {

    private static final String CITY_LIST_JSON = "city.list.json";

    private String getJsonFromAsset(Context context) {
        String jsonArrayString = null;

        try {
            InputStream inputStream = context.getAssets().open(CITY_LIST_JSON);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonArrayString = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonArrayString;
    }

    private ArrayList<String> getJsonArrayList(Context context) throws JSONException {
        String jsonArrayString = getJsonFromAsset(context);
        ArrayList<String> jsonArrayList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonArrayString);
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonArrayList.add(jsonArray.getString(i));
        }

        return jsonArrayList;
    }

    public ArrayList<PlaceRestModel> create(Context context) throws JSONException {
        ArrayList<String> jsonArrayList = getJsonArrayList(context);
        ArrayList<PlaceRestModel> placesArrayList = new ArrayList<>();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        for (String json : jsonArrayList) {
            placesArrayList.add(gson.fromJson(json, PlaceRestModel.class));
        }
        return placesArrayList;
    }

}
