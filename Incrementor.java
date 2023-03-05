import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Incrementor {
    public static double count = 0;

    public void start() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            Incrementor.count++;
            System.out.println("Count: " + count);
        }, 0, 1, TimeUnit.SECONDS);
    }
}