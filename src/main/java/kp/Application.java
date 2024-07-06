package kp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.IntStream;

import static kp.Constants.*;

/**
 * Characteristic of the earliest stage of the development.
 * <p>
 * "copy *.csv ..\data\city_temperatures.csv"
 * </p>
 */
public class Application {
    /**
     * The hidden constructor.
     */
    private Application() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        for (String city : Cities.list) {
            //System.out.println(DATA_FILE_CITY.apply(city));
            final List<String> list = createData(city);
            writeDataFile(city, list);
        }
    }

    /**
     * Creates the data.
     *
     * @return the list
     */
    private static List<String> createData(String city) {

        final List<String> list = IntStream.rangeClosed(0, TOTAL)
                .boxed().map(DATE_FUN)
                .map(date -> String.format("%s,%s,%.2f", city, date, TEMPERATURE_CREATOR.apply(city, date)))
                .toList();
        System.out.printf("city[%s], list size[%d]%n", city, list.size());
        //System.out.printf("%s%n%s%n", list.getFirst(), list.getLast());
        return list;
    }

    /**
     * Writes the data file.
     *
     * @param city the city
     * @param list the list
     */
    private static void writeDataFile(String city, List<String> list) {

        try (BufferedWriter bufferedWriter =
                     Files.newBufferedWriter(DATA_FILE_CITY.apply(city))) {
            for (String line : list) {
                bufferedWriter.write(line.concat(System.lineSeparator()));
            }
        } catch (IOException e) {
            System.out.printf("writeDataFile(): IOException[%s]%n", e.getMessage());
        }

    }
}
