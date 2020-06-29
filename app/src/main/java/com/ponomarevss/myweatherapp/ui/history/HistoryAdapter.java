package com.ponomarevss.myweatherapp.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ponomarevss.myweatherapp.R;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private HistoryItem[] items;

    HistoryAdapter(HistoryItem[] items) {
        this.items = items;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_history_item, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.getPlaceView().setText(items[position].getPlace());
        holder.getTemperatureView().setText(String.valueOf(items[position].getTemperature()));
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView placeView;
        TextView temperatureView;

        HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            placeView = itemView.findViewById(R.id.hu_place);
            temperatureView = itemView.findViewById(R.id.hu_temperature);
        }

        TextView getPlaceView() {
            return placeView;
        }

        TextView getTemperatureView() {
            return temperatureView;
        }
    }
}
