package humidifier.Mqtt;

import com.google.gson.Gson;
import humidifier.DataManagement.Entry;
import humidifier.DataManagement.HumidifierDataManager;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Processes the callbacks
 */
public class CallbackHandler implements MqttCallback {

    protected HumidifierDataManager dataManager;

    public CallbackHandler() {
        this.dataManager = HumidifierDataManager.getInstance();
        this.dataManager.loadDataFromFile();
    }

    /**
     * Handle losing connection
     * @param thrwbl
     */
    @Override
    public void connectionLost(Throwable thrwbl) {
        System.out.println("connection lost " + thrwbl.getLocalizedMessage());
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
        Entry parsed = g.fromJson(new String(mm.getPayload()), Entry.class);
        this.dataManager.insertValue(parsed);
        System.out.println(parsed.getHumidity());
    }

    /**
     * Handle complete delivery
     * @param imdt
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        // todo yaaay do smth here
    }
}