package com.ponomarevss.myweatherapp.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ponomarevss.myweatherapp.R;

import static android.content.Context.MODE_PRIVATE;
import static com.ponomarevss.myweatherapp.Constants.COORDINATES;
import static com.ponomarevss.myweatherapp.Constants.PRESSURE_AND_HUMIDITY;
import static com.ponomarevss.myweatherapp.Constants.SUNRISE_AND_SUNSET;
import static com.ponomarevss.myweatherapp.Constants.TEMPERATURE_DETAILS;
import static com.ponomarevss.myweatherapp.Constants.VISIBILITY;
import static com.ponomarevss.myweatherapp.Constants.WIND_SPEED_AND_DIRECTION;


public class SettingsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCoordinatesCheckBox(view);
        setTemperatureDetailsCheckBox(view);
        setPressureHumidityCheckBox(view);
        setVisibilityCheckBox(view);
        setWindCheckBox(view);
        setSunCheckBox(view);
    }

    private CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (getActivity() != null) {
                switch (buttonView.getId()) {
                    case R.id.coordinates_checkbox:
                        getActivity().getPreferences(MODE_PRIVATE).edit().putBoolean(COORDINATES, isChecked).apply();
                        return;
                    case R.id.temperature_details_checkbox:
                        getActivity().getPreferences(MODE_PRIVATE).edit().putBoolean(TEMPERATURE_DETAILS, isChecked).apply();
                        return;
                    case R.id.pressure_humidity_checkbox:
                        getActivity().getPreferences(MODE_PRIVATE).edit().putBoolean(PRESSURE_AND_HUMIDITY, isChecked).apply();
                        return;
                    case R.id.visibility_checkbox:
                        getActivity().getPreferences(MODE_PRIVATE).edit().putBoolean(VISIBILITY, isChecked).apply();
                        return;
                    case R.id.wind_checkbox:
                        getActivity().getPreferences(MODE_PRIVATE).edit().putBoolean(WIND_SPEED_AND_DIRECTION, isChecked).apply();
                        return;
                    case R.id.sun_checkbox:
                        getActivity().getPreferences(MODE_PRIVATE).edit().putBoolean(SUNRISE_AND_SUNSET, isChecked).apply();
                        return;
                    default:
                }
            }
        }
    };

    private void setCoordinatesCheckBox(@NonNull View view) {
        CheckBox coordinatesCheckBox = view.findViewById(R.id.coordinates_checkbox);
        if (getActivity() != null) {
            coordinatesCheckBox.setChecked(getActivity().getPreferences(MODE_PRIVATE).getBoolean(COORDINATES, false));
        }
        coordinatesCheckBox.setOnCheckedChangeListener(listener);
    }

    private void setTemperatureDetailsCheckBox(@NonNull View view) {
        CheckBox temperatureDetailsCheckBox = view.findViewById(R.id.temperature_details_checkbox);
        if (getActivity() != null) {
            temperatureDetailsCheckBox.setChecked(getActivity().getPreferences(MODE_PRIVATE).getBoolean(TEMPERATURE_DETAILS, false));
        }
        temperatureDetailsCheckBox.setOnCheckedChangeListener(listener);
    }

    private void setPressureHumidityCheckBox(@NonNull View view) {
        CheckBox pressureHumidityCheckBox = view.findViewById(R.id.pressure_humidity_checkbox);
        if (getActivity() != null) {
            pressureHumidityCheckBox.setChecked(getActivity().getPreferences(MODE_PRIVATE).getBoolean(PRESSURE_AND_HUMIDITY, false));
        }
        pressureHumidityCheckBox.setOnCheckedChangeListener(listener);
    }

    private void setVisibilityCheckBox(@NonNull View view) {
        CheckBox visibilityCheckBox = view.findViewById(R.id.visibility_checkbox);
        if (getActivity() != null) {
            visibilityCheckBox.setChecked(getActivity().getPreferences(MODE_PRIVATE).getBoolean(VISIBILITY, false));
        }
        visibilityCheckBox.setOnCheckedChangeListener(listener);
    }

    private void setWindCheckBox(@NonNull View view) {
        CheckBox windCheckBox = view.findViewById(R.id.wind_checkbox);
        if (getActivity() != null) {
            windCheckBox.setChecked(getActivity().getPreferences(MODE_PRIVATE).getBoolean(WIND_SPEED_AND_DIRECTION, false));
        }
        windCheckBox.setOnCheckedChangeListener(listener);
    }

    private void setSunCheckBox(@NonNull View view) {
        CheckBox sunCheckBox = view.findViewById(R.id.sun_checkbox);
        if (getActivity() != null) {
            sunCheckBox.setChecked(getActivity().getPreferences(MODE_PRIVATE).getBoolean(SUNRISE_AND_SUNSET, false));
        }
        sunCheckBox.setOnCheckedChangeListener(listener);
    }
}
