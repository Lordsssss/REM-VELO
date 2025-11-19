package services;

import domain.weather.WeatherData;

import java.io.IOException;
import java.net.URISyntaxException;

public interface WeatherService {
    WeatherData getWeatherFor(double latitude, double longitude) throws URISyntaxException, IOException, InterruptedException;
}