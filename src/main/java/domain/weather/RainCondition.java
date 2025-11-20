package domain.weather;

import config.Config;

public class RainCondition extends AbstractWeatherCondition {

    private final double light;
    private final double annoying;
    private final double bad;
    private final double noGo;

    public RainCondition() {
        super(Config.getInt("rainWeight"));
        this.light = Config.getDouble("rainLight");
        this.annoying = Config.getDouble("rainAnnoying");
        this.bad = Config.getDouble("rainBad");
        this.noGo = Config.getDouble("rainNoGo");
    }

    @Override
    public int evaluate(WeatherData data) {
        double rain = data.getRain();
        double precip = data.getPrecipitation();
        int score = 0;

        double amount = Math.max(rain, precip);

        if (amount <= 0.05) return 0;

        if (amount < light) {
            score += weight;
        } else if (amount < annoying) {
            score += weight * 2;
        } else if (amount < bad) {
            score += weight * 3;
        } else if (amount < noGo) {
            score += weight * 4;
        } else {
            score += weight * 6;
        }

        return score;
    }
}
