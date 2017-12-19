package humidifier.DataManagement;

import javafx.scene.chart.XYChart;

/**
 * Handle the data
 */
public class HumidifierDataManager {

    /**
     * chart data is a synchronized observable
     */
    private XYChart.Series series = new XYChart.Series();

    /**
     * Inner singleton class
     */
    private static class HumidifierDataManagerHolder {
        private static final HumidifierDataManager INSTANCE = new HumidifierDataManager();
    }

    /**
     * Gets the singleton instance
     * @return
     */
    public static HumidifierDataManager getInstance() {
        return HumidifierDataManagerHolder.INSTANCE;
    }


    /**
     * Init data
     */
    public HumidifierDataManager() {
        for (int i = 0; i < 10; i++) {
            this.series.getData().add(new XYChart.Data(String.valueOf(System.currentTimeMillis()), i));
        }
    }

    /**
     * Insert a new mqtt message into the chart data
     * @param unixTimeStamp a unix time stamp
     * @param humidity the humidity value between 0 - 1
     */
    public void insertValue(String unixTimeStamp, int humidity) {
        // todo correct mapping
        this.series.getData().add(new XYChart.Data(unixTimeStamp, humidity));
    }

    public XYChart.Series getSeries() {
        return this.series;
    }
}
