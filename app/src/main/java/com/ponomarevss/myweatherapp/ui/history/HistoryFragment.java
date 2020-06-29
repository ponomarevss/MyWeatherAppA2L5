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

import com.ponomarevss.myweatherapp.R;

public class HistoryFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // список HistoryItem
        HistoryItem[] items = {
                new HistoryItem("Астрахань"), new HistoryItem("Барнаул"),
                new HistoryItem("Владивосток"), new HistoryItem("Волгоград"),
                new HistoryItem("Воронеж"), new HistoryItem("Екатеринбург"),
                new HistoryItem("Ижевск"), new HistoryItem("Иркутск"),
                new HistoryItem("Казань"), new HistoryItem("Кемерово")
        };


        if (getActivity() == null) return;
        RecyclerView historyRecyclerView = getActivity().findViewById(R.id.history_recycler_view);
        historyRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        historyRecyclerView.setLayoutManager(layoutManager);

        HistoryAdapter adapter = new HistoryAdapter(items);
        historyRecyclerView.setAdapter(adapter);

    }
}
