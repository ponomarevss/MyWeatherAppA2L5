package com.ponomarevss.myweatherapp.ui.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.ponomarevss.myweatherapp.App;
import com.ponomarevss.myweatherapp.R;
import com.ponomarevss.myweatherapp.request.RequestHandler;
import com.ponomarevss.myweatherapp.request.WeatherRequest;
import com.ponomarevss.myweatherapp.room.WeatherDao;
import com.ponomarevss.myweatherapp.room.WeatherRecord;
import com.ponomarevss.myweatherapp.room.WeatherSource;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static android.content.Context.MODE_PRIVATE;
import static com.ponomarevss.myweatherapp.Constants.COORDINATES;
import static com.ponomarevss.myweatherapp.Constants.DESCR2;
import static com.ponomarevss.myweatherapp.Constants.DT2;
import static com.ponomarevss.myweatherapp.Constants.FEELS2;
import static com.ponomarevss.myweatherapp.Constants.HUM2;
import static com.ponomarevss.myweatherapp.Constants.ICON2;
import static com.ponomarevss.myweatherapp.Constants.ID2;
import static com.ponomarevss.myweatherapp.Constants.INDEX;
import static com.ponomarevss.myweatherapp.Constants.INIT_INDEX;
import static com.ponomarevss.myweatherapp.Constants.LAT2;
import static com.ponomarevss.myweatherapp.Constants.LON2;
import static com.ponomarevss.myweatherapp.Constants.OBSOLESCENCE_TIME;
import static com.ponomarevss.myweatherapp.Constants.PLACE2;
import static com.ponomarevss.myweatherapp.Constants.PRESS2;
import static com.ponomarevss.myweatherapp.Constants.PRESSURE_AND_HUMIDITY;
import static com.ponomarevss.myweatherapp.Constants.SRISE2;
import static com.ponomarevss.myweatherapp.Constants.SSET2;
import static com.ponomarevss.myweatherapp.Constants.SUNRISE_AND_SUNSET;
import static com.ponomarevss.myweatherapp.Constants.TEMP2;
import static com.ponomarevss.myweatherapp.Constants.TEMPERATURE_DETAILS;
import static com.ponomarevss.myweatherapp.Constants.TMAX2;
import static com.ponomarevss.myweatherapp.Constants.TMIN2;
import static com.ponomarevss.myweatherapp.Constants.VIS2;
import static com.ponomarevss.myweatherapp.Constants.VISIBILITY;
import static com.ponomarevss.myweatherapp.Constants.WDIR2;
import static com.ponomarevss.myweatherapp.Constants.WIND_SPEED_AND_DIRECTION;
import static com.ponomarevss.myweatherapp.Constants.WSPEED2;

public class HomeFragment extends Fragment {

    private RequestHandler requestHandler;

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //если первый запуск, делаем запрос по инициальному индексу. в дальнейшем запрос изменится на запрос по геоданным
        if (restoreStringData(PLACE2).equals(PLACE2)) {
            new WeatherRequest(this, view, getCityId(INIT_INDEX)).makeRequest();
        }

        //если данные в shared preferences актуальные, то инициализируем фрагмент сохраненными данными
        else if (actualDate() && actualPlace()) {
            setBackgroundView(view);
            setPlaceView(view, restoreStringData(PLACE2));
            showCoordinatesView(view, restoreStringData(LAT2),
                    restoreStringData(LON2));
            showWeatherIconView(view, restoreStringData(ICON2));
            showTemperatureView(view, restoreStringData(TEMP2));
            showWeatherDescriptionView(view, restoreStringData(DESCR2));
            showTemperatureDetailsView(view, restoreStringData(FEELS2),
                    restoreStringData(TMAX2),
                    restoreStringData(TMIN2));
            showPressureHumidityView(view, restoreStringData(PRESS2),
                    restoreStringData(HUM2));
            showWindView(view, restoreStringData(WSPEED2),
                    restoreStringData(WDIR2));
            showVisibilityView(view, restoreStringData(VIS2));
            showSunView(view, restoreStringData(SRISE2),
                    restoreStringData(SSET2));
            goToBrowserButton(view);
        }

        //если данные не актуальные выполняем новый запрос
        else {
            String cityId = getCityId(getIndex());
            if (cityId != null) {
                new WeatherRequest(this, view, cityId).makeRequest();
            }
        }

    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
        setBackgroundView(requireView());
        setPlaceView(requireView(), requestHandler.getPlace());
        showCoordinatesView(requireView(), requestHandler.getCoordLat(),
                requestHandler.getCoordLon());
        showWeatherIconView(requireView(), requestHandler.getIcon());
        showTemperatureView(requireView(), requestHandler.getTemp());
        showWeatherDescriptionView(requireView(), requestHandler.getWeatherDescription());
        showTemperatureDetailsView(requireView(), requestHandler.getTempFeelsLike(),
                requestHandler.getTempMax(),
                requestHandler.getTempMin());
        showPressureHumidityView(requireView(), requestHandler.getPressure(),
                requestHandler.getHumidity());
        showWindView(requireView(), requestHandler.getWindSpeed(),
                requestHandler.getWindDeg());
        showVisibilityView(requireView(), requestHandler.getVisibility());
        showSunView(requireView(), requestHandler.getSunrise(),
                requestHandler.getSunset());
        goToBrowserButton(requireView());

        if (!actualPlace()) addRecordToDatabase(requestHandler); //если меняется место, добавляем запись в историю
        saveFragmentData(); //сохраняем данные в Shared Preferences
    }

    private void addRecordToDatabase(RequestHandler requestHandler) {
        WeatherDao weatherDao = App
                .getInstance()
                .getWeatherDao();
        WeatherSource weatherSource = new WeatherSource(weatherDao);
        WeatherRecord weatherRecord = new WeatherRecord();
        weatherRecord.place = requestHandler.getPlace();
        weatherRecord.temperature = requestHandler.getTemp();
        weatherRecord.date = requestHandler.getDate();
        weatherSource.addWeatherRecord(weatherRecord);
    }

    private void setPlaceView(@NonNull View view, String place) {
        TextView placeTextView = view.findViewById(R.id.place);
        placeTextView.setText(place);
    }

    private void showCoordinatesView(@NonNull View view, String lat, String lon) {
        LinearLayout coordinatesLayout = view.findViewById(R.id.coordinates_layout);
        makeField(coordinatesLayout, R.string.latitude_field, lat, R.string.coordinates_unit);
        makeField(coordinatesLayout, R.string.longitude_field, lon, R.string.coordinates_unit);
        if (requireActivity()
                .getPreferences(MODE_PRIVATE)
                .getBoolean(COORDINATES, false)) {
            coordinatesLayout.setVisibility(View.VISIBLE);
        }
        else coordinatesLayout.setVisibility(View.GONE);
    }

    private void showWeatherIconView(@NonNull View view, String icon) {
        ImageView weatherIcon = view.findViewById(R.id.weather_icon);
        Picasso.get()
                .load(String.format(getString(R.string.icon_uri), icon))
                .into(weatherIcon);
    }

    private void showTemperatureView(@NonNull View view, String temp) {
        TextView temperatureView = view.findViewById(R.id.temperature_value);
        temperatureView.setText(temp);
        TextView temperatureUnitView = view.findViewById(R.id.temperature_unit);
        temperatureUnitView.setText(R.string.temperature_unit);
    }

    private void showWeatherDescriptionView(@NonNull View view, String description) {
        TextView weatherDescriptionView = view.findViewById(R.id.weather_description);
        weatherDescriptionView.setText(description);
    }

    private void showTemperatureDetailsView(@NonNull View view, String feels, String tmax, String tmin) {
        LinearLayout temperatureDetailsLayout = view.findViewById(R.id.temperature_details_layout);
        makeField(temperatureDetailsLayout, R.string.feels_like_field, feels, R.string.temperature_unit);
        makeField(temperatureDetailsLayout, R.string.temp_max_field, tmax, R.string.temperature_unit);
        makeField(temperatureDetailsLayout, R.string.temp_min_field, tmin, R.string.temperature_unit);
        if (requireActivity()
                .getPreferences(MODE_PRIVATE)
                .getBoolean(TEMPERATURE_DETAILS, false)) {
            temperatureDetailsLayout.setVisibility(View.VISIBLE);
        }
        else temperatureDetailsLayout.setVisibility(View.GONE);

    }

    private void showPressureHumidityView(@NonNull View view, String pressure, String humidity) {
        LinearLayout pressureHumidityLayout = view.findViewById(R.id.pressure_humidity_layout);
        makeField(pressureHumidityLayout, R.string.pressure_field, pressure, R.string.pressure_unit);
        makeField(pressureHumidityLayout, R.string.humidity_field, humidity, R.string.humidity_unit);
        if (requireActivity()
                .getPreferences(MODE_PRIVATE)
                .getBoolean(PRESSURE_AND_HUMIDITY, false)) {
            pressureHumidityLayout.setVisibility(View.VISIBLE);
        }
        else pressureHumidityLayout.setVisibility(View.GONE);
    }

    private void showWindView(@NonNull View view, String speed, String direction) {
        LinearLayout windLayout = view.findViewById(R.id.wind_layout);
        makeField(windLayout, R.string.wind_speed_field, speed, R.string.wind_speed_unit);
        makeField(windLayout, R.string.wind_direction_field, direction, R.string.wind_direction_unit);
        if (requireActivity().getPreferences(MODE_PRIVATE).getBoolean(WIND_SPEED_AND_DIRECTION, false)) {
            windLayout.setVisibility(View.VISIBLE);
        }
        else windLayout.setVisibility(View.GONE);
    }

    private void showVisibilityView(@NonNull View view, String visibility) {
        LinearLayout visibilityLayout = view.findViewById(R.id.visibility_layout);
        makeField(visibilityLayout, R.string.visibility_field, visibility, R.string.visibility_unit);
        if (requireActivity()
                .getPreferences(MODE_PRIVATE)
                .getBoolean(VISIBILITY, false)) {
            visibilityLayout.setVisibility(View.VISIBLE);
        }
        else visibilityLayout.setVisibility(View.GONE);
    }

    private void showSunView(@NonNull View view, String sunrise, String sunset) {
        LinearLayout sunLayout = view.findViewById(R.id.sun_layout);
        makeField(sunLayout, R.string.sunrise_field, sunrise, R.string.time_unit);
        makeField(sunLayout, R.string.sunset_field, sunset, R.string.time_unit);
        if (getActivity() != null && getActivity().getPreferences(MODE_PRIVATE).getBoolean(SUNRISE_AND_SUNSET, false)) {
            sunLayout.setVisibility(View.VISIBLE);
        }
        else sunLayout.setVisibility(View.GONE);
    }

    private void makeField(LinearLayout layout, int name, String value, int unit) {
        View view = getLayoutInflater().inflate(R.layout.detail_layout, layout, false);
        ((TextView) view.findViewById(R.id.detail_name))
                .setText(getResources().getString(name)); //имя
        ((TextView) view.findViewById(R.id.detail_value))
                .setText(value); //значение
        ((TextView) view.findViewById(R.id.detail_unit))
                .setText(getResources().getString(unit)); //единица измерения
        layout.addView(view);
    }

    private void goToBrowserButton(@NonNull View view) {
        TextView moreInfo = view.findViewById(R.id.more_info);
        moreInfo.setVisibility(View.VISIBLE);
        moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setMessage(R.string.lets_go_to_web)
                        .setCancelable(true)
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //не делать ничего
                            }
                        })
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String url = getResources().getString(R.string.url) + restoreLongData(ID2);
                                Uri uri = Uri.parse(url);
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                Context context = getContext();
                                if (context == null) return;
                                ActivityInfo activityInfo = intent.resolveActivityInfo(context.getPackageManager(), intent.getFlags());
                                if (activityInfo != null) {
                                    startActivity(intent);
                                }
                            }
                        })
                        .create()
                        .show();
            }
        });
    }

    private void setBackgroundView(View view) {
        ImageView background = view.findViewById(R.id.background);
        TypedArray images = getResources().obtainTypedArray(R.array.city_images);
        if (getIndex() == -1) {
            background.setImageResource(R.drawable.background_default);
        }
        else {
            background.setImageResource(images.getResourceId(getIndex(), -1));
        }
        background.setVisibility(View.VISIBLE);
        images.recycle();
    }

    private int getIndex() {
        return requireActivity().getPreferences(MODE_PRIVATE).getInt(INDEX, -1);
    }

    private String getCityId(int index) {
        String[] cityId = getResources().getStringArray(R.array.cities_id);
        if (index == -1) {
            return null;
        }
        return cityId[index];
    }

    private void saveFragmentData() {
        requireActivity()
                .getPreferences(MODE_PRIVATE)
                .edit()
                .putLong(DT2, requestHandler.getDt())
                .putLong(ID2, requestHandler.getId())
                .putString(PLACE2, requestHandler.getPlace())
                .putString(LAT2, requestHandler.getCoordLat())
                .putString(LON2, requestHandler.getCoordLon())
                .putString(ICON2, requestHandler.getIcon())
                .putString(TEMP2, requestHandler.getTemp())
                .putString(DESCR2, requestHandler.getWeatherDescription())
                .putString(FEELS2, requestHandler.getTempFeelsLike())
                .putString(TMAX2, requestHandler.getTempMax())
                .putString(TMIN2, requestHandler.getTempMin())
                .putString(PRESS2, requestHandler.getPressure())
                .putString(HUM2, requestHandler.getHumidity())
                .putString(VIS2, requestHandler.getVisibility())
                .putString(SRISE2, requestHandler.getSunrise())
                .putString(SSET2, requestHandler.getSunset())
                .apply();
    }

    private String restoreStringData(String key) {
        return requireActivity().getPreferences(MODE_PRIVATE).getString(key, key);
    }

    private long restoreLongData(String key) {
        return requireActivity().getPreferences(MODE_PRIVATE).getLong(key, 0);
    }

    private boolean actualDate() {
        return (System.currentTimeMillis() - restoreLongData(DT2) * 1000) < OBSOLESCENCE_TIME;
    }

    private boolean actualPlace() {
        return getIndex() == -1 || restoreStringData(PLACE2).equals(getResources().getStringArray(R.array.cities)[getIndex()]);
    }
}
