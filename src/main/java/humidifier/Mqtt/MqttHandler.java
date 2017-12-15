package humidifier.Mqtt;

import humidifier.Config;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import java.util.UUID;

/**
 * Handles everything concerning the MQTT Protocol
 */
public class MqttHandler {

    private String uniqueID = UUID.randomUUID().toString();
    private String topic;
    private String broker;
    private String clientId;
    private MqttDefaultFilePersistence persistence = new MqttDefaultFilePersistence();

    /**
     * Init values
     * @param config
     */
    public MqttHandler(
            Config config
    ) {
        this.topic = config.getMqttTopic();
        this.broker = config.getMqttBroker();
        // make sure we never have the same client id - multiple instances running
        this.clientId = config.getMqttClientId() + '-' +this.uniqueID;
        System.out.println(this.clientId);
        this.subscribe();
    }

    /**
     * Subscribes to the Mqtt server
     */
    private void subscribe() {
        try {
            MqttClient client = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: "+broker);
            client.connect(connOpts);
            client.setCallback(new CallbackHandler());
            System.out.println("Connected");
            client.subscribe(this.topic, 1);
            System.out.println("subscribed to " + this.topic);

        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }

}
