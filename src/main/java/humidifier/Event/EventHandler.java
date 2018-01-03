package humidifier.Event;

import com.pploder.events.Event;
import com.pploder.events.SimpleEvent;

/**
 * The event handler for "global" events
 */
public class EventHandler {

    /**
     * singleton instance
     */
    private static EventHandler singleton = new EventHandler( );

    /**
     *  A private Constructor prevents any other
     * class from instantiating.
     */
    private EventHandler() {}

    /**
     * get the singelton instance
     * @return
     */
    public static EventHandler getInstance( ) {
        return singleton;
    }

    /**
     * The connection event
     */
    private final Event<Boolean> humidityConnectionEvent = new SimpleEvent<>();

    /**
     * The limit connection event
     */
    private final Event<Boolean> limitConnectionEvent = new SimpleEvent<>();

    /**
     * listen to that event
     * @return
     */
    public Event<Boolean> humidityConnection() {
        return humidityConnectionEvent;
    }

    /**
     * listen to the limit event
     * @return
     */
    public Event<Boolean> limitConnection() {
        return limitConnectionEvent;
    }

    /**
     * Publish this event
     * @param active
     */
    public void humdityConnectionActive(Boolean active) {
        humidityConnection().trigger(active);
    }

    /**
     * Publish to the limit event
     * @param active
     */
    public void limitConnectionActive(Boolean active) {
        limitConnection().trigger(active);
    }

}
