package services;

import config.Config;
import domain.weather.WeatherData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class OpenMeteoWeatherService implements WeatherService {

    private final HttpClient client = HttpClient.newHttpClient();

    private final double lat = Config.getDouble("latitude");
    private final double lon = Config.getDouble("longitude");
    private final String timezone = Config.get("timezone");
    private final int startHour = Config.getInt("startHour");
    private final int endHour = Config.getInt("endHour");

    @Override
    public List<WeatherData> getWeatherFor() throws URISyntaxException, IOException, InterruptedException {
        JSONObject root = new JSONObject(getWeatherJson());
        JSONObject hourly = root.getJSONObject("hourly");

        JSONArray times = hourly.getJSONArray("time");
        JSONArray temps = hourly.getJSONArray("temperature_2m");
        JSONArray winds = hourly.getJSONArray("windspeed_10m");
        JSONArray precProb = hourly.getJSONArray("precipitation_probability");
        JSONArray precipitation = hourly.getJSONArray("precipitation");
        JSONArray rain = hourly.getJSONArray("rain");
        JSONArray snowfall = hourly.getJSONArray("snowfall");
        JSONArray snowDepth = hourly.getJSONArray("snow_depth");
        JSONArray weathercode = hourly.getJSONArray("weathercode");

        return buildWeatherDataBetweenHours(
                times, temps, winds, precProb,
                precipitation, rain, snowfall, snowDepth, weathercode,
                startHour, endHour
        );
    }

    private String getWeatherJson() throws IOException, InterruptedException, URISyntaxException {
        URI uri = new URI(
                "https",
                "api.open-meteo.com",
                "/v1/forecast",
                "latitude=" + lat +
                        "&longitude=" + lon +
                        "&hourly=temperature_2m," +
                        "windspeed_10m," +
                        "precipitation_probability," +
                        "precipitation," +
                        "rain," +
                        "snowfall," +
                        "snow_depth," +
                        "weathercode" +
                        "&timezone=auto" +
                        "&start_date=2025-11-22&end_date=2025-11-22",
                null
        );

        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Debug print (optional)
        System.out.println(response.body());

        return response.body();
    }

    private WeatherData buildWeatherDataAt(
            JSONArray times,
            JSONArray temps,
            JSONArray winds,
            JSONArray precProb,
            JSONArray precipitation,
            JSONArray rain,
            JSONArray snowfall,
            JSONArray snowDepth,
            JSONArray weathercode,
            int index
    ) {
        return new WeatherData(
                times.getString(index),
                temps.getDouble(index),
                winds.getDouble(index),
                precProb.getDouble(index),
                precipitation.getDouble(index),
                rain.getDouble(index),
                snowfall.getDouble(index),
                snowDepth.getDouble(index),
                weathercode.getInt(index)
        );
    }

    private List<WeatherData> buildWeatherDataBetweenHours(
            JSONArray times,
            JSONArray temps,
            JSONArray winds,
            JSONArray precProb,
            JSONArray precipitation,
            JSONArray rain,
            JSONArray snowfall,
            JSONArray snowDepth,
            JSONArray weathercode,
            int startHour,
            int endHour
    ) {
        List<WeatherData> result = new ArrayList<>();

        for (int hour = startHour; hour <= endHour; hour++) {
            result.add(buildWeatherDataAt(
                    times, temps, winds, precProb,
                    precipitation, rain, snowfall, snowDepth, weathercode,
                    hour
            ));
        }

        return result;
    }
}
