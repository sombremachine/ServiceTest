package com.example.darkness.servicetest;

import java.util.ArrayList;
import java.util.Calendar;

public class Dummus {
    public static ArrayList<WeatherSnapshot> generateTestData(){
        ArrayList<WeatherSnapshot> res = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        WeatherSnapshot snap = new WeatherSnapshot();
        snap.date = calendar.getTime();
        snap.isRaining = true;
        snap.isSnowing = false;
        snap.humidity = 50;
        snap.temperature = 25;
        res.add(snap);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        snap = new WeatherSnapshot();
        snap.date = calendar.getTime();
        snap.isRaining = false;
        snap.isSnowing = true;
        snap.humidity = 5;
        snap.temperature = -25;
        res.add(snap);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        snap = new WeatherSnapshot();
        snap.date = calendar.getTime();
        snap.isRaining = false;
        snap.isSnowing = false;
        snap.humidity = 100;
        snap.temperature = 0;
        res.add(snap);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        snap = new WeatherSnapshot();
        snap.date = calendar.getTime();
        snap.isRaining = false;
        snap.isSnowing = true;
        snap.humidity = 100;
        snap.temperature = 10;
        res.add(snap);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        snap = new WeatherSnapshot();
        snap.date = calendar.getTime();
        snap.isRaining = false;
        snap.isSnowing = false;
        snap.humidity = 76;
        snap.temperature = 20;
        res.add(snap);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        snap = new WeatherSnapshot();
        snap.date = calendar.getTime();
        snap.isRaining = true;
        snap.isSnowing = false;
        snap.humidity = 33;
        snap.temperature = 16;
        res.add(snap);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        snap = new WeatherSnapshot();
        snap.date = calendar.getTime();
        snap.isRaining = false;
        snap.isSnowing = false;
        snap.humidity = 10;
        snap.temperature = 21;
        res.add(snap);
        return res;
    }
}
