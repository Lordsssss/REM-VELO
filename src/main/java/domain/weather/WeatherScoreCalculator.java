package domain.weather;

import config.Config;
import domain.transit.CommuteAdvice;

import java.util.List;

public class WeatherScoreCalculator {

    private final List<WeatherCondition> conditions;
    private final int bikeMax;
    private final int maybeMax;

    public WeatherScoreCalculator(List<WeatherCondition> conditions) {
        this.conditions = conditions;
        this.bikeMax = Config.getInt("bikeMaxScore");
        this.maybeMax = Config.getInt("maybeMaxScore");
    }

    public int scoreForHour(WeatherData data) {
        int total = 0;
        for (WeatherCondition c : conditions) {
            total += c.evaluate(data);
        }
        return total;
    }

    public int worstScore(List<WeatherData> hours) {
        int worst = 0;
        for (WeatherData wd : hours) {
            int s = scoreForHour(wd);
            if (s > worst) {
                worst = s;
            }
        }
        return worst;
    }

    public CommuteAdvice adviceForDay(List<WeatherData> hours) {
        int worst = worstScore(hours);

        if (worst <= bikeMax) {
            return CommuteAdvice.BIKE;
        } else if (worst <= maybeMax) {
            return CommuteAdvice.MAYBE;
        } else {
            return CommuteAdvice.BUS;
        }
    }

    private int debugEvaluateCondition(WeatherCondition c, WeatherData wd, ScoreBreakdown breakdown) {

        int sc = c.evaluate(wd);

        if (c instanceof TempCondition) breakdown.tempScore = sc;
        else if (c instanceof RainCondition) breakdown.rainScore = sc;
        else if (c instanceof SnowCondition) breakdown.snowScore = sc;
        else if (c instanceof WindCondition) breakdown.windScore = sc;

        return sc;
    }

    public String explainDecision(List<WeatherData> hours) {

        int worst = -1;
        WeatherData worstHour = null;
        ScoreBreakdown worstBreakdown = null;

        for (WeatherData wd : hours) {

            ScoreBreakdown breakdown = new ScoreBreakdown();
            int total = 0;

            for (WeatherCondition c : conditions) {
                total += debugEvaluateCondition(c, wd, breakdown);
            }

            if (total > worst) {
                worst = total;
                worstHour = wd;
                worstBreakdown = breakdown;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Worst hour: ").append(worstHour.getTime()).append("\n")
                .append("Temperature score: ").append(worstBreakdown.tempScore).append("\n")
                .append("Rain score: ").append(worstBreakdown.rainScore).append("\n")
                .append("Snow score: ").append(worstBreakdown.snowScore).append("\n")
                .append("Wind score: ").append(worstBreakdown.windScore).append("\n")
                .append("\nTotal score: ").append(worst).append("\n");

        if (worst <= bikeMax) {
            sb.append("Decision: BIKE\n");
        } else if (worst <= maybeMax) {
            sb.append("Decision: MAYBE\n");
        } else {
            sb.append("Decision: BUS\n");
        }

        return sb.toString();
    }

}
