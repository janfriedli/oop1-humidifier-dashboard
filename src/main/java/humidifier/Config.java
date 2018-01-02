package humidifier;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Handles the whole configuration in this project
 */
public class Config {

    /**
     * The properties
     */
    private Properties prop = new Properties();

    /**
     * An input stream
     */
    private InputStream input = null;

    /**
     * Initially load the config file
     */
    public Config() {
        try {
            input = Config.class.getClassLoader().getResourceAsStream("config.properties");

            if(input==null){
                throw new Exception("Unable to find config file");
            }

            prop.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Get Topic
     * @return string
     */
    public String getMqttTopic() {
        return this.prop.getProperty("mqttTopic");
    }

    /**
     * Get Limit Topic
     * @return string
     */
    public String getMqttLimitTopic() {
        return this.prop.getProperty("mqttLimitTopic");
    }

    /**
     * Get Broker name
     * @return string
     */
    public String getMqttBroker() {
        return this.prop.getProperty("mqttBroker");
    }

    /**
     * Get client name
     * @return string
     */
    public String getMqttClientId() {
        return this.prop.getProperty("mqttClientId");
    }
}
