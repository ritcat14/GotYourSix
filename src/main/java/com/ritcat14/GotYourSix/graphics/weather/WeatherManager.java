package com.ritcat14.GotYourSix.graphics.weather;

import com.ritcat14.GotYourSix.graphics.weather.types.Weather;

import java.util.ArrayList;

public abstract class WeatherManager {
  
    public ArrayList<Weather> weather = new ArrayList<Weather>();
  
    public WeatherManager(){
      initWeather();
    }

    public abstract void initWeather();
  
    public void addWeather(Weather weather){
        this.weather.add(weather);
    }
  
    public void removeWeather(Weather weather){
      this.weather.remove(weather);
    }
  
    public void startWeather(String name){
        for (Weather s : weather) {
            if (s.name.equals(name)) {
              //display weather
            }
        }
    }
  
}
