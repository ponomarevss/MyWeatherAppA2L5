package com.ponomarevss.myweatherapp.ui.history;

import java.util.Random;

public class HistoryItem {

    private String place;
    private int temperature;

    public HistoryItem(String place) {
        this.place = place;
        temperature = new Random().nextInt(15) + 15; // случайная температура в пределах 15 - 29
    }

    public String getPlace() {
        return place;
    }

    public int getTemperature() {
        return temperature;
    }
}
