package humidifier.Mqtt;

import com.google.gson.Gson;
import humidifier.DataManagement.Entry;
import humidifier.DataManagement.HumidifierDataManager;
import humidifier.DataManagement.Limit;
import humidifier.Event.EventHandler;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Processes the callbacks
 */
public class LimitCallbackHandler implements MqttCallback {

    /**
     * The data manager
     */
    protected HumidifierDataManager dataManager;

    /**
     * The event handler
     */
    protected EventHandler eventHandler = EventHandler.getInstance();

    /**
     * init object
     */
    public LimitCallbackHandler() {
        this.dataManager = HumidifierDataManager.getInstance();
    }

    /**
     * Handle losing connection
     * @param thrwbl
     */
    @Override
    public void connectionLost(Throwable thrwbl) {
        this.eventHandler.limitConnectionActive(false);
        System.out.println("limit connection lost " + thrwbl.getLocalizedMessage());
    }

    /**
     * Handle every new message
     * @param string
     * @param mm
     * @throws Exception
     */
    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        Gson g = new Gson();
        Limit limit = g.fromJson(new String(mm.getPayload()), Limit.class);
        this.dataManager.setLimit(limit);
    }

    /**
     * Handle complete delivery
     * @param imdt
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        // just chill
    }
}