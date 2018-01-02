package humidifier;

import com.google.gson.JsonObject;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import humidifier.DataManagement.HumidifierDataManager;
import humidifier.Mqtt.MqttLimit;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {

    @FXML
    private LineChart<?, ?> linechart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private JFXTextField lowerBound;

    @FXML
    private JFXTextField upperBound;

    private HumidifierDataManager dataManager;

    private MqttLimit limiter;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        this.dataManager = HumidifierDataManager.getInstance();
        this.linechart.getData().addAll(this.dataManager.getSeries());
        this.limiter = new MqttLimit(new Config());
    }

    /**
     * create a json object and publish it
     */
    public void updateButtonClicked() {
        JsonObject limits = new JsonObject();
        limits.addProperty("lower", this.lowerBound.getText());
        limits.addProperty("upper", this.upperBound.getText());

        this.limiter.publishHumidityLimits(limits.toString());
    }
}
