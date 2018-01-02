package humidifier.DataManagement;

/**
 * Used to parse the limits from json
 */
public class Limit {

    /**
     * the upper bound of humidity
     */
    private int upper;

    /**
     * the lower bound of humidity
     */
    private int lower;

    /**
     * get upper
     * @return
     */
    public int getUpper() {
        return upper;
    }

    /**
     * set upper
     * @param upper
     */
    public void setUpper(int upper) {
        this.upper = upper;
    }

    /**
     * get lower
     * @return
     */
    public int getLower() {
        return lower;
    }

    /**
     * set lower
     * @param lower
     */
    public void setLower(int lower) {
        this.lower = lower;
    }
}
