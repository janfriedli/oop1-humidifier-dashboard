package humidifier;

import com.google.gson.JsonObject;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import humidifier.DataManagement.HumidifierDataManager;
import humidifier.DataManagement.Limit;
import humidifier.Event.EventHandler;
import humidifier.Mqtt.LimitListener;
import humidifier.Mqtt.MqttLimit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

/**
 * Controller that handles the gui actions
 */
public class Dashboard implements Initializable, LimitListener {

    /**
     * the line chart
     */
    @FXML
    private LineChart<?, ?> linechart;

    /**
     * the x axis
     */
    @FXML
    private CategoryAxis x;

    /**
     * the y axis
     */
    @FXML
    private NumberAxis y;

    /**
     * the lower bound text field
     */
    @FXML
    private JFXTextField lowerBound;

    /**
     * the upper bound text field
     */
    @FXML
    private JFXTextField upperBound;

    @FXML
    private Label error;

    /**
     * the data manager
     */
    private HumidifierDataManager dataManager;

    /**
     * the limit
     */
    private MqttLimit limiter;

    /**
     * the update button
     */
    @FXML
    private JFXButton updateButton;

    /**
     * the success color
     */
    final Paint successColor = Color.GREEN;

    /**
     * the error color
     */
    final Paint errorColor = Color.RED;

    /**
     * The event handler
     */
    protected EventHandler eventHandler = EventHandler.getInstance();

    /**
     * the connection status label
     */
    @FXML
    private Label statusDisplay;

    /**
     * Initialize the gui
     * @param url
     * @param rb
     */
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        this.dataManager = HumidifierDataManager.getInstance();
        this.linechart.getData().addAll(this.dataManager.getSeries());
        // make sure only digits are prohibited
        this.lowerBound.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    lowerBound.setText(newValue.replaceAll("[^\\d]", ""));
                }

                if (!lowerBound.getText().trim().isEmpty()) {
                    int lower = Integer.parseInt(lowerBound.getText());
                    if (lower >= 100) {
                        lowerBound.setText("99");
                    }
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

                if (!upperBound.getText().trim().isEmpty()) {
                    int upper = Integer.parseInt(upperBound.getText());
                    if (upper > 100) {
                        upperBound.setText("100");
                    }
                }
            }
        });

        this.dataManager.addLimitListener(this);
        this.eventHandler.humidityConnection().addListener(active -> {
            Platform.runLater(
                    () -> {
                        if (active) {
                            this.statusDisplay.setText("yes");
                        } else {
                            this.statusDisplay.setText("no");
                        }
                    }
            );
        });
        this.eventHandler.limitConnection().addListener(active -> {
            Platform.runLater(
                    () -> {
                        this.lowerBound.setDisable(!active);
                        this.upperBound.setDisable(!active);
                        this.updateButton.setDisable(!active);
                    }
            );
        });
        this.limiter = new MqttLimit(new Config());
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
        this.error.setTextFill(this.errorColor);
        if (!upperBound.getText().trim().isEmpty() && !lowerBound.getText().trim().isEmpty()) {
            int upper = Integer.parseInt(upperBound.getText());
            int lower = Integer.parseInt(lowerBound.getText());

            if (upper > lower) {
                JsonObject limits = new JsonObject();
                limits.addProperty("lower", this.lowerBound.getText());
                limits.addProperty("upper", this.upperBound.getText());

                this.limiter.publishHumidityLimits(limits.toString());
                this.error.setTextFill(this.successColor);
                this.error.setText("Updated!");
            } else {
                this.error.setText("The lower bound can't be greater than the upper.");
            }
        } else {
            this.error.setText("Both fields must have a value");
        }

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(4000),
                ae -> error.setText(""))
        );
        timeline.play();
    }
}
