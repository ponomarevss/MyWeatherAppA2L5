package com.ponomarevss.myweatherapp.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ponomarevss.myweatherapp.App;
import com.ponomarevss.myweatherapp.R;
import com.ponomarevss.myweatherapp.room.WeatherDao;
import com.ponomarevss.myweatherapp.room.WeatherSource;

public class HistoryFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WeatherDao weatherDao = App
                .getInstance()
                .getWeatherDao();
        WeatherSource weatherSource = new WeatherSource(weatherDao);

        if (getActivity() == null) return;
        RecyclerView historyRecyclerView = getActivity().findViewById(R.id.history_recycler_view);
        historyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, true);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        historyRecyclerView.setLayoutManager(layoutManager);

        HistoryAdapter adapter = new HistoryAdapter(weatherSource);
        historyRecyclerView.setAdapter(adapter);
    }
}
