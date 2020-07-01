package com.ponomarevss.myweatherapp.ui.place;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.ponomarevss.myweatherapp.R;

import static android.content.Context.MODE_PRIVATE;
import static com.ponomarevss.myweatherapp.Constants.INDEX;
import static com.ponomarevss.myweatherapp.Constants.PLACE;

public class PlaceFragment extends Fragment {

    private TextView placeTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_place, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //создаем список городов
        final String[] cities = getResources().getStringArray(R.array.cities);
        final TypedArray images = getResources().obtainTypedArray(R.array.city_images);
        if (getActivity() == null) return;
        RecyclerView citiesRecyclerView = getActivity().findViewById(R.id.cities_recycler_view);
        citiesRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        citiesRecyclerView.setLayoutManager(layoutManager);

        CitiesRecyclerAdapter adapter = new CitiesRecyclerAdapter(cities, images);

        //передаем интерфейс ClickListener'а
        adapter.setClickListener(new CitiesRecyclerAdapter.CitiesRecyclerClickListener() {
            @Override
            public void OnItemClick(String place, int index) {
                savePlace(place);
                saveIndex(index);
                requireActivity().onBackPressed();
            }
        });
        citiesRecyclerView.setAdapter(adapter);

    }

    private void savePlace(String place) {
        if (getActivity() != null) {
            getActivity().getPreferences(MODE_PRIVATE).edit().putString(PLACE, place).apply();
        }
    }

    private void saveIndex(int index) {
        if (getActivity() != null) {
            getActivity().getPreferences(MODE_PRIVATE).edit().putInt(INDEX, index).apply();
        }
    }
}
