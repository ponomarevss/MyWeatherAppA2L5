package com.ponomarevss.myweatherapp.ui.home;

import android.content.Context;
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
import com.ponomarevss.myweatherapp.MainActivity;
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
import static com.ponomarevss.myweatherapp.Constants.*;

public class HomeFragment extends Fragment {

    private RequestHandler requestHandler;
    private ImageView background;
    private TextView placeTextView;
    private LinearLayout coordinatesLayout;
    private ImageView weatherIcon;
    private TextView temperatureView;
    private TextView temperatureUnitView;
    private TextView weatherDescriptionView;
    private LinearLayout temperatureDetailsLayout;
    private LinearLayout pressureHumidityLayout;
    private LinearLayout windLayout;
    private LinearLayout visibilityLayout;
    private LinearLayout sunLayout;
    private TextView moreInfo;

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
        init(view);

        //если первый запуск, делаем запрос по инициальному индексу. в дальнейшем запрос изменится на запрос по геоданным
        if (restoreStringData(PLACE2).equals(PLACE2)) {
            new WeatherRequest((MainActivity) getActivity(), getCityId(INIT_INDEX)).makeRequest();
//            new WeatherRequest(this, getCityId(INIT_INDEX)).makeRequest();
        }

        //если данные в shared preferences актуальные, то инициализируем фрагмент сохраненными данными
        else if (actualDate() && actualPlace()) {
            setBackgroundView();
            setPlaceView(restoreStringData(PLACE2));
            setCoordinatesView(restoreStringData(LAT2),
                    restoreStringData(LON2));
            setWeatherIconView(restoreStringData(ICON2));
            setTemperatureView(restoreStringData(TEMP2));
            setWeatherDescriptionView(restoreStringData(DESCR2));
            setTemperatureDetailsView(restoreStringData(FEELS2),
                    restoreStringData(TMAX2),
                    restoreStringData(TMIN2));
            setPressureHumidityView(restoreStringData(PRESS2),
                    restoreStringData(HUM2));
            setWindView(restoreStringData(WSPEED2),
                    restoreStringData(WDIR2));
            setVisibilityView(restoreStringData(VIS2));
            setSunView(restoreStringData(SRISE2),
                    restoreStringData(SSET2));
            goToBrowserButton();
        }

        //если данные не актуальные выполняем новый запрос
        else {
            String cityId = getCityId(getIndex());
            if (cityId != null) {
                new WeatherRequest((MainActivity) getActivity(), cityId).makeRequest();
//                new WeatherRequest(this, cityId).makeRequest();
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
        setBackgroundView();
        setPlaceView(requestHandler.getPlace());
        setCoordinatesView(requestHandler.getCoordLat(),
                requestHandler.getCoordLon());
        setWeatherIconView(requestHandler.getIcon());
        setTemperatureView(requestHandler.getTemp());
        setWeatherDescriptionView(requestHandler.getWeatherDescription());
        setTemperatureDetailsView(requestHandler.getTempFeelsLike(),
                requestHandler.getTempMax(),
                requestHandler.getTempMin());
        setPressureHumidityView(requestHandler.getPressure(),
                requestHandler.getHumidity());
        setWindView(requestHandler.getWindSpeed(),
                requestHandler.getWindDeg());
        setVisibilityView(requestHandler.getVisibility());
        setSunView(requestHandler.getSunrise(),
                requestHandler.getSunset());
        goToBrowserButton();

        if (!actualPlace()) addRecordToDatabase(requestHandler); //если меняется место, добавляем запись в историю
        saveFragmentData(); //сохраняем данные в Shared Preferences
    }

    private void init(@NonNull View view) {
        background = view.findViewById(R.id.background);
        placeTextView = view.findViewById(R.id.place);
        coordinatesLayout = view.findViewById(R.id.coordinates_layout);
        weatherIcon = view.findViewById(R.id.weather_icon);
        temperatureView = view.findViewById(R.id.temperature_value);
        temperatureUnitView = view.findViewById(R.id.temperature_unit);
        weatherDescriptionView = view.findViewById(R.id.weather_description);
        temperatureDetailsLayout = view.findViewById(R.id.temperature_details_layout);
        pressureHumidityLayout = view.findViewById(R.id.pressure_humidity_layout);
        windLayout = view.findViewById(R.id.wind_layout);
        visibilityLayout = view.findViewById(R.id.visibility_layout);
        sunLayout = view.findViewById(R.id.sun_layout);
        moreInfo = view.findViewById(R.id.more_info);
    }

    private void setPlaceView(String place) {
        placeTextView.setText(place);
    }

    private void setCoordinatesView(String lat, String lon) {
        makeField(coordinatesLayout, R.string.latitude_field, lat, R.string.coordinates_unit);
        makeField(coordinatesLayout, R.string.longitude_field, lon, R.string.coordinates_unit);
        if (requireActivity()
                .getPreferences(MODE_PRIVATE)
                .getBoolean(COORDINATES, false)) {
            coordinatesLayout.setVisibility(View.VISIBLE);
        }
        else coordinatesLayout.setVisibility(View.GONE);
    }

    private void setWeatherIconView(String icon) {
        Picasso.get()
                .load(String.format(getString(R.string.icon_uri), icon))
                .into(weatherIcon);
    }

    private void setTemperatureView(String temp) {
        temperatureView.setText(temp);
        temperatureUnitView.setText(R.string.temperature_unit);
    }

    private void setWeatherDescriptionView(String description) {
        weatherDescriptionView.setText(description);
    }

    private void setTemperatureDetailsView(String feels, String tmax, String tmin) {
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

    private void setPressureHumidityView(String pressure, String humidity) {
        makeField(pressureHumidityLayout, R.string.pressure_field, pressure, R.string.pressure_unit);
        makeField(pressureHumidityLayout, R.string.humidity_field, humidity, R.string.humidity_unit);
        if (requireActivity()
                .getPreferences(MODE_PRIVATE)
                .getBoolean(PRESSURE_AND_HUMIDITY, false)) {
            pressureHumidityLayout.setVisibility(View.VISIBLE);
        }
        else pressureHumidityLayout.setVisibility(View.GONE);
    }

    private void setWindView(String speed, String direction) {
        makeField(windLayout, R.string.wind_speed_field, speed, R.string.wind_speed_unit);
        makeField(windLayout, R.string.wind_direction_field, direction, R.string.wind_direction_unit);
        if (requireActivity().getPreferences(MODE_PRIVATE).getBoolean(WIND_SPEED_AND_DIRECTION, false)) {
            windLayout.setVisibility(View.VISIBLE);
        }
        else windLayout.setVisibility(View.GONE);
    }

    private void setVisibilityView(String visibility) {
        makeField(visibilityLayout, R.string.visibility_field, visibility, R.string.visibility_unit);
        if (requireActivity()
                .getPreferences(MODE_PRIVATE)
                .getBoolean(VISIBILITY, false)) {
            visibilityLayout.setVisibility(View.VISIBLE);
        }
        else visibilityLayout.setVisibility(View.GONE);
    }

    private void setSunView(String sunrise, String sunset) {
        makeField(sunLayout, R.string.sunrise_field, sunrise, R.string.time_unit);
        makeField(sunLayout, R.string.sunset_field, sunset, R.string.time_unit);
        if (getActivity() != null && getActivity().getPreferences(MODE_PRIVATE).getBoolean(SUNRISE_AND_SUNSET, false)) {
            sunLayout.setVisibility(View.VISIBLE);
        }
        else sunLayout.setVisibility(View.GONE);
    }

    private void makeField(LinearLayout layout, int name, String value, int unit) {
        View view = getLayoutInflater()
                .inflate(R.layout.detail_layout, layout, false);
        ((TextView) view.findViewById(R.id.detail_name))
                .setText(getResources().getString(name));
        ((TextView) view.findViewById(R.id.detail_value))
                .setText(value);
        ((TextView) view.findViewById(R.id.detail_unit))
                .setText(getResources().getString(unit));
        layout.addView(view);
    }

    private void goToBrowserButton() {
        moreInfo.setVisibility(View.VISIBLE);
        moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setMessage(R.string.lets_go_to_web)
                        .setCancelable(true)
                        .setNegativeButton(R.string.no, (dialog, which) -> {
                            //не делать ничего
                        })
                        .setPositiveButton(R.string.yes, (dialog, which) -> {
                            String url = getResources().getString(R.string.url) + restoreLongData(ID2);
                            Uri uri = Uri.parse(url);
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            Context context = getContext();
                            if (context == null) return;
                            ActivityInfo activityInfo = intent.resolveActivityInfo(context.getPackageManager(), intent.getFlags());
                            if (activityInfo != null) {
                                startActivity(intent);
                            }
                        })
                        .create()
                        .show();
            }
        });
    }

    private void setBackgroundView() {
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
