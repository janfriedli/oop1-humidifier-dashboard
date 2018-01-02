package humidifier.DataManagement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import humidifier.Mqtt.LimitListener;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Handle the data
 */
public class HumidifierDataManager {

    /**
     * List of listeners
     */
    private List<LimitListener> listeners = new ArrayList<LimitListener>();

    /**
     * chart data is a synchronized observable
     */
    private XYChart.Series series = new XYChart.Series();

    /**
     * Placeholder list to simplify data handling
     */
    private ArrayList<Entry> entries = new ArrayList<>();

    /**
     *  the limit
     */
    private Limit limit;

    /**
     * Inner singleton class
     */
    private static class HumidifierDataManagerHolder {
        private static final HumidifierDataManager INSTANCE = new HumidifierDataManager();
    }

    /**
     * Gets the singleton instance
     *
     * @return
     */
    public static HumidifierDataManager getInstance() {
        return HumidifierDataManagerHolder.INSTANCE;
    }


    /**
     * Insert a new mqtt message into the chart data
     */
    public void insertValue(Entry entry) {
        Platform.runLater(
                () -> {
                    this.entries.add(entry);
                    this.series.getData().add(new XYChart.Data(entry.getCreatedAt().toString(), entry.getHumidity()));
                    this.saveDataToFile();
                }
        );
    }

    /**
     * Return the whole data set
     *
     * @return
     */
    public XYChart.Series getSeries() {
        return this.series;
    }


    /**
     * Load data saved in a local json file
     */
    public void loadDataFromFile() {
        Gson gson = new Gson();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader("humidity.json"));
            Entry[] data = gson.fromJson(reader, Entry[].class);
            Collections.addAll(this.entries, data);
            for (Entry entry : data) {
                this.series.getData().add(new XYChart.Data(entry.getCreatedAt().toString(), entry.getHumidity()));
            }
        } catch (FileNotFoundException e) {
            //ignore that it will be created later
        }
    }

    /**
     * Save the humidity values to a json file
     */
    private void saveDataToFile() {
        try (Writer writer = new FileWriter("humidity.json")) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(this.entries, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * method for registering as listener
     * @param toAdd
     */
    public void addLimitListener(LimitListener toAdd) {
        listeners.add(toAdd);
    }

    /**
     * set limit
     * @return
     */
    public Limit getLimit() {
        return limit;
    }

    /**
     * get Limit
     * @param limit
     */
    public void setLimit(Limit limit) {
        this.limit = limit;
        for (LimitListener listener : listeners) {
            listener.updatedLimits(limit);
        }
    }
}
