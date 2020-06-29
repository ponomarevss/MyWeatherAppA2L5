package com.ponomarevss.myweatherapp;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ponomarevss.myweatherapp.rest.OpenWeatherRepo;
import com.ponomarevss.myweatherapp.rest.entities.WeatherRequestRestModel;
import com.ponomarevss.myweatherapp.ui.home.HomeFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ponomarevss.myweatherapp.Constants.WEATHER_API_KEY;

public class WeatherRequest {

//    private WeatherModel weatherModel;
    private Fragment fragment;
    private View view;
    private String cityId;
//    private String uri;
    private RequestHandler requestHandler;

    public WeatherRequest(Fragment fragment, View view, String cityId) {
//    public WeatherRequest(Fragment fragment, View view, String uri) {
        this.fragment = fragment;
        this.view = view;
        this.cityId = cityId;
//        this.uri = uri;
    }

    public void makeRequest() {
        ((MainActivity) fragment.requireActivity()).showAlertMessage(fragment.getString(R.string.loading));
        OpenWeatherRepo.getInstance().getAPI().loadWeather(cityId, WEATHER_API_KEY, "metric")
                .enqueue(new Callback<WeatherRequestRestModel>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherRequestRestModel> call,
                                           @NonNull Response<WeatherRequestRestModel> response) {
                        ((MainActivity) fragment.requireActivity()).hideAlertMessage();
                        if (response.body() != null && response.isSuccessful()) {
                            requestHandler = new RequestHandler(response.body());
                            ((HomeFragment) fragment).init(view, requestHandler);
                        }
                        else {
                            ((MainActivity) fragment.requireActivity()).showAlertMessage(fragment.getString(R.string.failed_to_get_data));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<WeatherRequestRestModel> call,
                                          @NonNull Throwable t) {
                        ((MainActivity) fragment.requireActivity()).hideAlertMessage();
                        ((MainActivity) fragment.requireActivity()).showAlertMessage(fragment.getString(R.string.failed_to_get_data));
                    }
                });

    }
}
