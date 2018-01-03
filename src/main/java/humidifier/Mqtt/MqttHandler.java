package humidifier.Mqtt;

import humidifier.Config;
import humidifier.Event.EventHandler;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import java.util.UUID;

/**
 * Handles everything concerning the MQTT Protocol
 */
public class MqttHandler {

    /**
     * unique Id
     */
    private String uniqueID = UUID.randomUUID().toString();

    /**
     * mqtt topic name
     */
    private String topic;

    /**
     * mqtt broker
     */
    private String broker;

    /**
     * mqtt client id
     */
    private String clientId;

    /**
     * Persistence
     */
    private MqttDefaultFilePersistence persistence = new MqttDefaultFilePersistence();

    /**
     * the mqtt client
     */
    private MqttClient client;

    /**
     * The event handler
     */
    protected EventHandler eventHandler = EventHandler.getInstance();

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
            this.client = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: "+broker);
            client.connect(connOpts);
            client.setCallback(new CallbackHandler());
            System.out.println("Connected");
            this.eventHandler.humdityConnectionActive(true);
            client.subscribe(this.topic, 1);
            System.out.println("subscribed to " + this.topic);

        } catch(MqttException me) {
            this.eventHandler.humdityConnectionActive(false);
            System.out.println("Connecting failed "+me.getCause());
        }
    }
}
