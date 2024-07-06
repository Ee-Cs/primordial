package kp;

import java.nio.file.Path;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * The constants.
 */
public class Constants {
    public static final int TOTAL = 876_573;
    public static final Random RANDOM = new Random();
    public static final double TEMPERATURE_MIN = -50d;
    public static final double TEMPERATURE_MAX = 50d;
    public static final BiFunction<String, String, Double> TEMPERATURE_CREATOR_RANDOM = (_, _) ->
            RANDOM.nextDouble(TEMPERATURE_MIN, TEMPERATURE_MAX);
    public static final AtomicInteger ATOMIC = new AtomicInteger();
    private static final int[] PREVIOUS_YEAR = {1970};
    public static final BiFunction<String, String, Double> TEMPERATURE_CREATOR_LINEAR = (city, date) -> {
        double totalForYear = (TOTAL - 1) / 50d;
        if (Integer.parseInt(date.substring(0, 4)) != PREVIOUS_YEAR[0]) {
            PREVIOUS_YEAR[0]++;
            if (PREVIOUS_YEAR[0] == 2020) {
                PREVIOUS_YEAR[0] = 1970;
            }
            ATOMIC.set(0);
        }
        double delta = -TEMPERATURE_MIN + TEMPERATURE_MAX;
        double cityShift = 0.5 * Cities.list.indexOf(city) - 25;
        return TEMPERATURE_MIN + (delta * ATOMIC.getAndIncrement() / totalForYear) + cityShift;
    };
    public static final BiFunction<String, String, Double> TEMPERATURE_CREATOR_STEPPED = (_, date) -> {
        if (Integer.parseInt(date.substring(0, 4)) != PREVIOUS_YEAR[0]) {
            PREVIOUS_YEAR[0]++;
            if (PREVIOUS_YEAR[0] == 2020) {
                PREVIOUS_YEAR[0] = 1970;
            }
            ATOMIC.set(0);
        }
        double delta = -TEMPERATURE_MIN + TEMPERATURE_MAX;
        return TEMPERATURE_MIN + delta * (PREVIOUS_YEAR[0] - 1970) / 50;
    };
    public static final BiFunction<String, String, Double> TEMPERATURE_CREATOR = TEMPERATURE_CREATOR_RANDOM;
    public static final Function<Integer, String> DATE_FUN = arg ->
            Instant.ofEpochMilli(1_800_000L * arg).atZone(ZoneId.systemDefault())
                    .toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    public static final Function<String, Path> DATA_FILE_CITY =
            city -> Path.of(String.format("..\\large-file-reading-challenge\\data_cities\\city_temperatures_%s.csv",
                    city.replace(" ", "_")
                            .replace("/", "_")));
}
