package com.ponomarevss.myweatherapp.request;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ponomarevss.myweatherapp.MainActivity;
import com.ponomarevss.myweatherapp.R;
import com.ponomarevss.myweatherapp.rest.OpenWeatherRepo;
import com.ponomarevss.myweatherapp.rest.weatherModel.WeatherRequestRestModel;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ponomarevss.myweatherapp.Constants.WEATHER_API_KEY;

public class WeatherRequest {

    private Fragment fragment;
    private View view;
    private String cityId;
    private RequestHandler requestHandler;

    public WeatherRequest(Fragment fragment, View view, String cityId) {
        this.fragment = fragment;
        this.view = view;
        this.cityId = cityId;
    }

    public void makeRequest() {
        ((MainActivity) fragment.requireActivity()).showAlertMessage(fragment.getString(R.string.loading));
        OpenWeatherRepo
                .getInstance()
                .getAPI()
                .loadWeather(cityId, WEATHER_API_KEY, "metric")
                .enqueue(new Callback<WeatherRequestRestModel>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherRequestRestModel> call,
                                           @NonNull Response<WeatherRequestRestModel> response) {
                        ((MainActivity) fragment.requireActivity()).hideAlertMessage();
                        if (response.body() != null && response.isSuccessful()) {
                            requestHandler = new RequestHandler(response.body());
                            EventBus.getDefault().post(requestHandler);
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
