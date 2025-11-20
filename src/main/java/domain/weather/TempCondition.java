package domain.weather;

import config.Config;

public class TempCondition extends AbstractWeatherCondition {

    private final double coldThreshold;
    private final double hotThreshold;

    public TempCondition() {
        super(Config.getInt("tempWeight"));
        this.coldThreshold = Config.getDouble("coldThreshold");
        this.hotThreshold = Config.getDouble("hotThreshold");
    }

    @Override
    public int evaluate(WeatherData data) {
        double t = data.getTemperature();
        int score = 0;

        if (t < coldThreshold) {
            double delta = coldThreshold - t;
            score += (int) (weight * delta * 0.5);
        } else if (t > hotThreshold) {
            double delta = t - hotThreshold;
            if (delta <= 2) {
                score += (int) (weight * delta);
            } else {
                score += (int) (weight * (2 + (delta - 2) * 3));
            }
        }

        return score;
    }
}
