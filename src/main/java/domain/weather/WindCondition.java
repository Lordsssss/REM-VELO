package domain.weather;

import config.Config;

public class WindCondition extends AbstractWeatherCondition {

    private final double moderate;
    private final double strong;
    private final double veryStrong;

    private final double coldMultiplier;
    private final double heatReliefMultiplier;

    private final double coldThreshold;
    private final double hotThreshold;

    public WindCondition() {
        super(Config.getInt("windWeight"));
        this.moderate = Config.getDouble("windModerate");
        this.strong = Config.getDouble("windStrong");
        this.veryStrong = Config.getDouble("windVeryStrong");
        this.coldMultiplier = Config.getDouble("coldWindMultiplier");
        this.heatReliefMultiplier = Config.getDouble("heatReliefMultiplier");
        this.coldThreshold = Config.getDouble("coldThreshold");
        this.hotThreshold = Config.getDouble("hotThreshold");
    }

    @Override
    public int evaluate(WeatherData data) {
        double w = data.getWindSpeed();
        double t = data.getTemperature();
        int score = 0;
        double base = 0;

        if (w < moderate) base = 0;
        else if (w < strong) base = 1;
        else if (w < veryStrong) base = 2;
        else base = 3;

        if (t < coldThreshold) {
            score += (int) (weight * base * coldMultiplier);
        } else if (t > hotThreshold) {
            score -= (int) (weight * base * heatReliefMultiplier);
            if (score < 0) score = 0;
        } else {
            score += (int) (weight * base);
        }

        return score;
    }
}
