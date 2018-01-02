package humidifier;

import com.jfoenix.controls.JFXButton;
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
    private Label humidityLabel;

    @FXML
    private LineChart<?, ?> linechart;

    @FXML
    private CategoryAxis x;

    @FXML
    private JFXButton updateButton;

    @FXML
    private NumberAxis y;

    private HumidifierDataManager dataManager;

    private MqttLimit limiter;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        this.dataManager = HumidifierDataManager.getInstance();
        this.linechart.getData().addAll(this.dataManager.getSeries());
        this.limiter = new MqttLimit(new Config());
    }

    public void updateButtonClicked() {
        this.limiter.publishHumidityLimits("test");
    }
}
