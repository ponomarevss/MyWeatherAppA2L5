package com.ponomarevss.myweatherapp.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ponomarevss.myweatherapp.R;
import com.ponomarevss.myweatherapp.room.WeatherRecord;
import com.ponomarevss.myweatherapp.room.WeatherSource;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private WeatherSource weatherSource;

    HistoryAdapter(WeatherSource weatherSource) {
        this.weatherSource = weatherSource;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_history_item, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        List<WeatherRecord> records = weatherSource.getWeatherRecords();
        WeatherRecord weatherRecord = records.get(position);
        holder.getPlaceView().setText(weatherRecord.place);
        holder.getTemperatureView().setText(weatherRecord.temperature);
        holder.getDateView().setText(weatherRecord.date);
    }

    @Override
    public int getItemCount() {
        return (int) weatherSource.getWeatherRecordsCount();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView placeView;
        TextView temperatureView;
        TextView dateView;

        HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            placeView = itemView.findViewById(R.id.hu_place);
            temperatureView = itemView.findViewById(R.id.hu_temperature);
            dateView = itemView.findViewById(R.id.hu_date);
        }

        TextView getPlaceView() {
            return placeView;
        }

        TextView getTemperatureView() {
            return temperatureView;
        }

        public TextView getDateView() {
            return dateView;
        }
    }
}
