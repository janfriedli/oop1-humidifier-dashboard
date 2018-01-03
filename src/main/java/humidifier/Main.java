package humidifier;

import humidifier.DataManagement.HumidifierDataManager;
import humidifier.Mqtt.MqttHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sun.text.normalizer.Trie;

public class Main extends Application {

    /**
     * start the application
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        HumidifierDataManager dataManager = HumidifierDataManager.getInstance();
        dataManager.loadDataFromFile();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/dashboard.fxml"));
        primaryStage.setTitle("Air Humidifier");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
        new MqttHandler(new Config());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
