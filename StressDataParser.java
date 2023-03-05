import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import org.knowm.xchart.*;
import org.knowm.xchart.style.*;


public class StressDataParser {
    public static int stress; // 0 = low stress, 1 = medium stress, 2 = high stress
    public static double heartRate;
    public static double avgTemperature;

    public static ArrayList<Integer> parseCSV(String path) {
        File csvFile = new File(path);
        String csvLine = "";
        ArrayList<Integer> list = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(csvFile);

            // Read the single line of the CSV file
            csvLine = scanner.nextLine();

            // Split the CSV line into an array of strings
            String[] values = csvLine.split(",");

            // Convert the array to an ArrayList
            for (String value : values) {
                list.add(Integer.valueOf(value));
            }

            // Close the scanner
            scanner.close();
            return list;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return null;
    }



    public static void analyzeHeartRate() {
        ArrayList<Integer> list = parseCSV("heartratedata.csv");
        int i = 0, totalBeats = 0;
        if (list.get(0) == 1) totalBeats++;
        for (int j = 1; j < list.size(); j++) {
            if (list.get(i) == 0 && list.get(j) == 1) totalBeats++;
            i++;
        }
        StressDataParser.heartRate = (totalBeats / (Incrementor.count / 60));
    }

    public static void analyzeTemperature() {
        ArrayList<Integer> list = parseCSV("temperaturedata.csv");
        arrayListToGraph(list, "Body temperature versus time", "Body temp", "temperaturegraph.png");
        StressDataParser.avgTemperature = avgOfLast(list.stream().mapToInt(Integer::intValue).toArray(), 10);
    }

    public static void arrayListToGraph(ArrayList<Integer> data, String chartName, String yAxisLabel, String fileName) {


        // Create Chart
        XYChart chart = new XYChartBuilder().width(600).height(400).title(chartName).xAxisTitle("Time").yAxisTitle(yAxisLabel).build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add data to Chart
        XYSeries series = chart.addSeries("Data", null, data);

        // Save Chart as Image File
        try {
            BitmapEncoder.saveBitmap(chart, fileName, BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException ex) {
            System.out.println("Failed to save chart as image file: " + ex.getMessage());
        }
    }

    public static double avgOfLast(int[] data, int points) {
        if (data.length < points) return average(data);
        else return average(Arrays.copyOfRange(data, data.length - points, data.length));
    }

    public static double average(int[] data) {
        double total = 0;
        for (int i : data) total += i;
        return total;
    }

    public static void startIncrementor() {
        Incrementor incrementor = new Incrementor();
        incrementor.start();
    }
}
