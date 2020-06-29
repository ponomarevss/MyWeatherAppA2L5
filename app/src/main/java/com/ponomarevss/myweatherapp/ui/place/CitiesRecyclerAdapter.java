package com.ponomarevss.myweatherapp.ui.place;

import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ponomarevss.myweatherapp.R;

public class CitiesRecyclerAdapter extends RecyclerView.Adapter<CitiesRecyclerAdapter.CitiesRecyclerViewHolder> {

    private String[] cities;
    private TypedArray images;
    //поле интерфейса ClickListener'а
    private CitiesRecyclerClickListener clickListener = null;

    CitiesRecyclerAdapter(String[] cities, TypedArray images) {
        this.cities = cities;
        this.images = images;
    }

    @NonNull
    @Override
    public CitiesRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_city, parent, false);
        return new CitiesRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesRecyclerViewHolder holder, int position) {
        holder.getTextView().setText(cities[position]);
        holder.getImageView().setImageResource(images.getResourceId(position, -1));
    }

    @Override
    public int getItemCount() {
        return cities.length;
    }

    class CitiesRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView imageView;

        CitiesRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recycler_textview);
            imageView = itemView.findViewById(R.id.recycler_imageview);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickListener != null){
                        clickListener.OnItemClick(textView.getText().toString(), getAdapterPosition());
                    }
                }
            });
        }

        TextView getTextView() {
            return textView;
        }

        ImageView getImageView() {
            return imageView;
        }
    }

    //определяем интерфейс ClicklListener'а
    public interface CitiesRecyclerClickListener {
        void OnItemClick(String place, int index);
    }

    //сеттер поля ClickListener'а
    void setClickListener(CitiesRecyclerClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
