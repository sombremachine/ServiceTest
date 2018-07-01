package com.example.darkness.servicetest;

import java.util.ArrayList;
import java.util.Calendar;

public class Dummies {
    public static ArrayList<WeatherSnapshot> generateTestData(){
        ArrayList<WeatherSnapshot> res = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        WeatherSnapshot snap = new WeatherSnapshot();
        snap.date = calendar.getTime();
        snap.isRaining = true;
        snap.isSnowing = false;
        snap.humidity = 50;
        snap.temperature = 25;
        snap.pressure = 1000;
        res.add(snap);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        snap = new WeatherSnapshot();
        snap.date = calendar.getTime();
        snap.isRaining = false;
        snap.isSnowing = true;
        snap.humidity = 5;
        snap.temperature = -25;
        snap.pressure = 900;
        res.add(snap);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        snap = new WeatherSnapshot();
        snap.date = calendar.getTime();
        snap.isRaining = false;
        snap.isSnowing = false;
        snap.humidity = Integer.MIN_VALUE;
        snap.temperature = 0;
        snap.pressure = 1200;
        res.add(snap);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        snap = new WeatherSnapshot();
        snap.date = calendar.getTime();
        snap.isRaining = false;
        snap.isSnowing = true;
        snap.humidity = Integer.MIN_VALUE;
        snap.temperature = 10;
        snap.pressure = Integer.MIN_VALUE;
        res.add(snap);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        snap = new WeatherSnapshot();
        snap.date = calendar.getTime();
        snap.isRaining = false;
        snap.isSnowing = false;
        snap.humidity = 76;
        snap.temperature = 20;
        snap.pressure = 10000;
        res.add(snap);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        snap = new WeatherSnapshot();
        snap.date = calendar.getTime();
        snap.isRaining = true;
        snap.isSnowing = false;
        snap.humidity = 33;
        snap.temperature = 16;
        snap.pressure = 567;
        res.add(snap);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        snap = new WeatherSnapshot();
        snap.date = calendar.getTime();
        snap.isRaining = false;
        snap.isSnowing = false;
        snap.humidity = 10;
        snap.temperature = 21;
        snap.pressure = 987;
        res.add(snap);
        return res;
    }
}
