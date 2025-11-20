package domain.weather;

public class WeatherData {
    private String time;
    private double temperature;
    private double precipitationProbability;
    private double windSpeed;
    private final double precipitation;
    private final double rain;
    private final double snowfall;
    private final double snowDepth;
    private final int weatherCode;

    public WeatherData(
            String time,
            double temperature,
            double windSpeed,
            double precipitationProb,
            double precipitation,
            double rain,
            double snowfall,
            double snowDepth,
            int weatherCode
    ) {
        this.time = time;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.precipitationProbability = precipitationProb;
        this.precipitation = precipitation;
        this.rain = rain;
        this.snowfall = snowfall;
        this.snowDepth = snowDepth;
        this.weatherCode = weatherCode;
    }

    public String getTime() { return time; }
    public double getTemperature() {
        return temperature;
    }
    public double getPrecipitationProbability() {
        return precipitationProbability;
    }
    public double getWindSpeed() {
        return windSpeed;
    }
    public double getPrecipitation() { return precipitation; }
    public double getRain() { return rain; }
    public double getSnowfall() { return snowfall; }
    public double getSnowDepth() { return snowDepth; }
    public int getWeatherCode() { return weatherCode; }

    @java.lang.Override
    public java.lang.String toString() {
        return "WeatherData{" +
                "temperature=" + temperature +
                ", precipitationProbability=" + precipitationProbability +
                ", windSpeed=" + windSpeed +
                '}';
    }
}