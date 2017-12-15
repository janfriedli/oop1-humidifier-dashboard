package humidifier;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Handles everything concerning the MQTT Protocol
 */
public class MqttHandler {

    private String topic;
    private String broker;
    private String clientId;
    private MemoryPersistence persistence = new MemoryPersistence();

    /**
     * Init values
     * @param config
     */
    public MqttHandler(
            Config config
    ) {
        this.topic = config.getMqttTopic();
        this.broker = config.getMqttBroker();
        this.clientId = config.getMqttClientId();
        this.subscribe();
    }

    /**
     * Subscribes to the mqtt server
     */
    private void subscribe() {
        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: "+broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
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
