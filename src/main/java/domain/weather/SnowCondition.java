package domain.weather;

import config.Config;

public class SnowCondition extends AbstractWeatherCondition {

    private final double prettyFlakesMax;
    private final double moderateSnowMax;
    private final double slipperyTempLow;
    private final double slipperyTempHigh;

    private final int mixedPenalty;
    private final int accumulationRisk;

    public SnowCondition() {
        super(Config.getInt("snowWeight"));

        this.prettyFlakesMax   = Config.getDouble("snowPrettyMax");
        this.moderateSnowMax   = Config.getDouble("snowModerateMax");
        this.slipperyTempLow   = Config.getDouble("snowSlipperyLow");
        this.slipperyTempHigh  = Config.getDouble("snowSlipperyHigh");
        this.mixedPenalty      = Config.getInt("snowMixedPenalty");
        this.accumulationRisk  = Config.getInt("snowAccumulationRisk");
    }

    @Override
    public int evaluate(WeatherData data) {

        double snowfall  = data.getSnowfall();
        double snowDepth = data.getSnowDepth();
        double rain      = data.getRain();
        int code         = data.getWeatherCode();
        double temp      = data.getTemperature();

        int score = 0;

        if (snowfall <= 0.05 && code != 66 && code != 67) {
            return 0;
        }

        if (code == 66 || code == 67 || (snowfall > 0 && rain > 0)) {
            score += weight * mixedPenalty;
        }

        if (snowfall <= prettyFlakesMax) {
            score += weight;
        }
        else if (snowfall <= moderateSnowMax) {
            score += weight * 2;
        }
        else {
            score += weight * 4;
        }

        if (temp >= slipperyTempLow && temp <= slipperyTempHigh) {
            score += weight * 3;
        }
        if (snowDepth >= accumulationRisk) {
            score += weight * 2;
        }

        return score;
    }
}
