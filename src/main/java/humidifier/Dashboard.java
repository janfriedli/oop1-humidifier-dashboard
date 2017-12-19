package humidifier;

import humidifier.DataManagement.HumidifierDataManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import java.net.URL;
import java.util.ResourceBundle;

public class Dashboard implements Initializable{

    @FXML
    private Slider humidityRuler;

    @FXML
    private Label humidityLabel;

    @FXML
    private LineChart<?, ?> linechart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    private HumidifierDataManager dataManager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.dataManager = HumidifierDataManager.getInstance();
        this.linechart.getData().addAll(this.dataManager.getSeries());
    }
}
