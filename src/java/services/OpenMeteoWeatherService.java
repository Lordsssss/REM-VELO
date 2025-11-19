package services;

import domain.weather.WeatherData;

public class OpenMeteoWeatherService implements WeatherService {

    private static final String BASE_URL =
            "https://api.open-meteo.com/v1/forecast";

    @Override
    public WeatherData getWeatherFor(double lat, double lon) {
        URL url = queryBuilder(lat,lon);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        // YOU implement:
        // 1. Build the URL
        // 2. Send HTTP request
        // 3. Parse JSON
        // 4. Convert into WeatherData object
        return null;
    }

    private URI queryBuilder(double lat, double lon) {
        return uri = new URI(
                "https",
                "api.open-meteo.com",
                "/v1/forecast",
                "latitude=" + lat +
                        "&longitude=" + lon +
                        "&current_weather=true" +
                        "&hourly=temperature_2m,windspeed_10m,precipitation_probability",
                null
        );

    }
    // (Optional) helper methods you can add:
    // private String buildUrl(double lat, double lon) { ... }
    // private WeatherData parseJson(String json) { ... }
}