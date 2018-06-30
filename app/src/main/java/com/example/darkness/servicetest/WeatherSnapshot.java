package com.example.darkness.servicetest;


import android.content.Context;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Данный объект содержит в себе информацию о погоде, собранную с сайта.
 *Потом информация с нескольких сайтов собирается в презентере и усредняется также в объекте данного класса
 *
 */

public class WeatherSnapshot implements Serializable{
    //todo РЕАЛИЗОВАТЬ. Определить в виде каком виде передавать в презентер дату, облачность
    protected String weatherSource; // с какакого сайта
    protected int temperature;
    protected int windSpeed;
    protected String windDirection;
    protected int humidity;
    protected int pressure;
    protected int cloudCover;
    protected Date date;
    protected boolean isRaining;
    protected boolean isSnowing;

    public boolean isRaining() {
        return isRaining;
    }

    public void setRaining(boolean raining) {
        isRaining = raining;
    }

    public boolean isSnowing() {
        return isSnowing;
    }

    public void setSnowing(boolean snowing) {
        isSnowing = snowing;
    }

    public void setWeatherSource(String weatherSource) {
        this.weatherSource = weatherSource;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public void setCloudCover(int cloudCover) {
        this.cloudCover = cloudCover;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getWeatherSource() {
        return weatherSource;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public int getCloudCover() {
        return cloudCover;
    }

    public Date getDate() {
        return date;
    }
    public enum WindDirections {
        N {},
        W {},
        E {},
        S {},
        NW {},
        NE {},
        SW {},
        SE {}

    }
    public enum Cloudness {
        CLEAR, CLOUDY, NO_SUN
    }
}
