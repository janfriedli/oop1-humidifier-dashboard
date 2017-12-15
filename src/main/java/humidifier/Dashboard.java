package humidifier;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart .Data("1", 1));
        series.getData().add(new XYChart.Data("2", 2));

        this.linechart.getData().addAll(series);

    }
}
