package humidifier.DataManagement;

/**
 * Created by fribim on 29.12.17.
 */
public class Entry {

    /**
     * The humidity fro 0 to 100 which represents percent
     */
    private double humidity;

    /**
     * Set humidity
     * @return
     */
    public double getHumidity() {
        return humidity;
    }

    /**
     * get humidity
     * @param humidity
     */
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}
