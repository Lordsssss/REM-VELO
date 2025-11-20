package domain.weather;

import domain.weather.WeatherCondition;

public abstract class AbstractWeatherCondition implements WeatherCondition {
    protected int weight;

    public AbstractWeatherCondition(int weight) {
        this.weight = weight;
    }

    // subclasses override evaluate()
}
