package services;

import domain.weather.WeatherData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenMeteoWeatherService implements services.WeatherService {

    private static final String BASE_URL =
            "https://api.open-meteo.com/v1/forecast";
    private final HttpClient client = HttpClient.newHttpClient();


    @Override
    public WeatherData getWeatherFor(double lat, double lon) throws URISyntaxException, IOException, InterruptedException {
        JSONObject root = new JSONObject(getWeatherJson(lat, lon));
        JSONObject weatherData = root.getJSONObject("weather");

        JSONArray times = weatherData.getJSONArray("time");
        JSONArray temps = weatherData.getJSONArray("temperature_2m");
        JSONArray winds = weatherData.getJSONArray("windspeed_10m");
        JSONArray precs = weatherData.getJSONArray("precipitation_probability");

        return null;
    }

    private String getWeatherJson(double lat,double lon) throws IOException, InterruptedException, URISyntaxException {
        URI uri = new URI(
                "https",
                "api.open-meteo.com",
                "/v1/forecast",
                "latitude=" + lat +
                        "&longitude=" + lon +
                        "&current_weather=true" +
                        "&hourly=temperature_2m,windspeed_10m,precipitation_probability",
                null
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    };

    private WeatherData buildWeatherDataAt(JSONArray times,
                                           JSONArray temps,
                                           JSONArray winds,
                                           JSONArray precs,
                                           int index) throws URISyntaxException, IOException, InterruptedException {

        return null;
    }

    ;

    // (Optional) helper methods you can add:
    // private String buildUrl(double lat, double lon) { ... }
    // private WeatherData parseJson(String json) { ... }

}