package humidifier.Mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Processes the callbacks
 */
public class CallbackHandler implements MqttCallback {

    /**
     * Handle losing connection
     * @param thrwbl
     */
    @Override
    public void connectionLost(Throwable thrwbl) {
        System.out.println("connection lost");
    }

    /**
     * Handle every new message
     * @param string
     * @param mm
     * @throws Exception
     */
    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        System.out.println(string);
        System.out.println(string +  ": " + new String(mm.getPayload()));
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