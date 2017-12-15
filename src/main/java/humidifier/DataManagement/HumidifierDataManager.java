package humidifier.DataManagement;

import javafx.collections.ObservableList;

/**
 * Handle the data
 */
public class HumidifierDataManager {

    private ObservableList<String> humidityValues;

    /**
     * Insert a new mqtt message into the chart data
     * @param unixTimeStamp a unix time stamp
     * @param humidity the humidity value between 0 - 1
     */
    public void insertValue(String unixTimeStamp, int humidity) {
        // todo correct mapping
        this.humidityValues.add(unixTimeStamp);
    }
}
