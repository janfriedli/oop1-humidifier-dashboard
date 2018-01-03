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
public class MqttLimit {

    /**
     * uuid
     */
    private String uniqueID = UUID.randomUUID().toString();

    /**
     * mqtt limit topic
     */
    private String limitTopic;

    /**
     * mqtt broker
     */
    private String broker;

    /**
     * client id
     */
    private String clientId;

    /**
     * persistence
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
    public MqttLimit(
            Config config
    ) {
        this.limitTopic = config.getMqttLimitTopic();
        this.broker = config.getMqttBroker();
        this.clientId = config.getMqttClientId() + '-' + this.uniqueID;
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
            client.connect(connOpts);
            client.setCallback(new LimitCallbackHandler());
            client.subscribe(this.limitTopic, 1);
            this.eventHandler.limitConnectionActive(true);
        } catch(MqttException me) {
            this.eventHandler.limitConnectionActive(false);
            System.out.println("Connecting limit failed "+me.getCause());
        }
    }

    /**
     * publish the upper and lower levels of humidity to the raspberry pi
     */
    public void publishHumidityLimits(String limits) {
        try {
            this.client.publish(this.limitTopic, limits.getBytes(), 1, true);
        } catch (MqttException e) {
            this.eventHandler.limitConnectionActive(false);
        }
    }

}
