package humidifier.Mqtt;

import humidifier.Config;
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

    private String uniqueID = UUID.randomUUID().toString();
    private String limitTopic;
    private String broker;
    private String clientId;
    private MqttDefaultFilePersistence persistence = new MqttDefaultFilePersistence();
    private MqttClient client;

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
            System.out.println("connected");

        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }

    /**
     * publish the upper and lower levels of humidity to the raspberry pi
     */
    public void publishHumidityLimits(String limits) {
        MqttMessage message = new MqttMessage(limits.getBytes());
        message.setQos(1);
        try {
            this.client.publish(this.limitTopic, message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
