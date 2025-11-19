package services;

import domain.weather.WeatherData;

public interface WeatherService {
    WeatherData getWeatherFor(double latitude, double longitude);
}