package services;

import domain.weather.WeatherData;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface WeatherService {
    List<WeatherData> getWeatherFor() throws URISyntaxException, IOException, InterruptedException;
}