package domain.weather;

public class WeatherData {
    private double temperature;
    private double precipitationProbability;
    private double windSpeed;

    public WeatherData(double temperature, double precipitationProbability, double windSpeed) {
        this.temperature = temperature;
        this.precipitationProbability = precipitationProbability;
        this.windSpeed = windSpeed;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getPrecipitationProbability() {
        return precipitationProbability;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "WeatherData{" +
                "temperature=" + temperature +
                ", precipitationProbability=" + precipitationProbability +
                ", windSpeed=" + windSpeed +
                '}';
    }
}