package com.ponomarevss.myweatherapp.request;

import androidx.annotation.NonNull;

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

    private MainActivity mainActivity;
    private String cityId;
    private RequestHandler requestHandler;

    public WeatherRequest(MainActivity mainActivity, String cityId) {
        this.mainActivity = mainActivity;
        this.cityId = cityId;
    }

    public void makeRequest() {
        mainActivity.showAlertMessage(mainActivity.getString(R.string.loading));
        OpenWeatherRepo
                .getInstance()
                .getAPI()
                .loadWeather(cityId, WEATHER_API_KEY, "metric")
                .enqueue(new Callback<WeatherRequestRestModel>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherRequestRestModel> call,
                                           @NonNull Response<WeatherRequestRestModel> response) {
                        mainActivity.hideAlertMessage();
                        if (response.body() != null && response.isSuccessful()) {
                            requestHandler = new RequestHandler(response.body());
                            EventBus.getDefault().post(requestHandler);
                        }
                        else {
                            mainActivity.showAlertMessage(mainActivity.getString(R.string.failed_to_get_data));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<WeatherRequestRestModel> call,
                                          @NonNull Throwable t) {
                        mainActivity.hideAlertMessage();
                        mainActivity.showAlertMessage(mainActivity.getString(R.string.failed_to_get_data));
                    }
                });

    }
}
