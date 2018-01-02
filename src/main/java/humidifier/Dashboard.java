package humidifier;

import com.google.gson.JsonObject;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import humidifier.DataManagement.HumidifierDataManager;
import humidifier.DataManagement.Limit;
import humidifier.Mqtt.LimitListener;
import humidifier.Mqtt.MqttLimit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import java.net.URL;
import java.util.ResourceBundle;

public class Dashboard implements Initializable, LimitListener {

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

    @FXML
    private JFXButton updateButton;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        this.lowerBound.setDisable(true);
        this.upperBound.setDisable(true);
        this.updateButton.setDisable(true);
        this.dataManager = HumidifierDataManager.getInstance();
        this.linechart.getData().addAll(this.dataManager.getSeries());
        this.limiter = new MqttLimit(new Config());
        // make sure only digits are prohibited
        this.lowerBound.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    lowerBound.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        this.upperBound.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    upperBound.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        this.dataManager.addLimitListener(this);
    }

    /**
     * Activate the text fields and set the limits
     * @param limit
     */
    public void updatedLimits(Limit limit) {
        this.upperBound.setText(String.valueOf(limit.getUpper()));
        this.lowerBound.setText(String.valueOf(limit.getLower()));
        this.lowerBound.setDisable(false);
        this.upperBound.setDisable(false);
        this.updateButton.setDisable(false);
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
