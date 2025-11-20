package app;

import config.Config;
import domain.transit.CommuteAdvice;
import domain.weather.*;
import services.OpenMeteoWeatherService;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {
            // 1. Weather service
            OpenMeteoWeatherService service = new OpenMeteoWeatherService();



            // 2. Fetch hourly forecast (06h → 19h)
            List<WeatherData> hours = service.getWeatherFor();

            System.out.println("Fetched hours = " + hours.size());
            for (WeatherData wd : hours) {
                System.out.println(wd.getTime());
            }

            // 3. Build your scoring engine using all conditions
            WeatherScoreCalculator calc = new WeatherScoreCalculator(
                    List.of(
                            new TempCondition(),
                            new RainCondition(),
                            new SnowCondition(),
                            new WindCondition()
                    )
            );

            // 4. Compute decision
            int worstScore = calc.worstScore(hours);
            CommuteAdvice advice = calc.adviceForDay(hours);

            // 5. Output
            System.out.println("========== REM-VELO WEATHER ADVICE ==========");
            System.out.println("Worst score: " + worstScore);
            System.out.println("Final decision: " + advice);
            System.out.println();
            System.out.println("-------- Detailed Justification --------");
            System.out.println(calc.explainDecision(hours));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
