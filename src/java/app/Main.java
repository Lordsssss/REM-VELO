import java.io.IOException;
import java.net.URISyntaxException;
import services.OpenMeteoWeatherService;

public class Main {
    static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        System.out.println("Hello World");
        OpenMeteoWeatherService  service = new OpenMeteoWeatherService();
        service.getWeatherFor(45.573502,-73.872833);
    }
}