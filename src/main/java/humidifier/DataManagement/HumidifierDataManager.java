package humidifier.DataManagement;

import javafx.application.Platform;
import javafx.scene.chart.XYChart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
     *
     * @return
     */
    public static HumidifierDataManager getInstance() {
        return HumidifierDataManagerHolder.INSTANCE;
    }


    /**
     * Insert a new mqtt message into the chart data
     *
     * @param unixTimeStamp a unix time stamp
     * @param humidity      the humidity value between 0 - 1
     */
    public void insertValue(String unixTimeStamp, int humidity) {
        Platform.runLater(
                () -> {
                    this.series.getData().add(new XYChart.Data(unixTimeStamp, humidity));
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

}
