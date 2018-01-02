package humidifier.Mqtt;

import humidifier.DataManagement.Limit;

/**
 * Interface for the limit listeners
 */
public interface LimitListener {
    /**
     * called when the limit has been update and on startup when gettting the retained message
     * @param limit
     */
    void updatedLimits(Limit limit);
}
