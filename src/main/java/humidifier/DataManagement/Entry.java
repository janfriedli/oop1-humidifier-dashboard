package humidifier.DataManagement;

import java.util.Date;

/**
 * Created by fribim on 29.12.17.
 */
public class Entry {

    /**

     * The humidity fro 0 to 100 which represents percent
     */
    private double humidity;

    /**
     * The date when the object was created
     */
    private Date createdAt;

    /**
     * init object with the actual date
     */
    public Entry() {
        this.createdAt = new Date();
    }

    /**
     * get created at
     * @return Date
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * set created at
     * @param createdAt Date
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

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
