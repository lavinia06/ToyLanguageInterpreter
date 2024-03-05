package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader1 = new FXMLLoader(MainApp.class.getResource("main.fxml"));
        Scene scene1 = new Scene(fxmlLoader1.load(), 1050, 650);
        MainController mainController = fxmlLoader1.getController();

        FXMLLoader fxmlLoader2 = new FXMLLoader(MainApp.class.getResource("select.fxml"));
        Scene scene2 = new Scene(fxmlLoader2.load(), 1050, 650);
        SelectController selectController = fxmlLoader2.getController();

        mainController.setSelectController(selectController);

        Stage stage2 = new Stage();
        stage2.setTitle("Select Window");
        stage2.setScene(scene2);
        stage2.show();

        stage.setTitle("Program Window");
        stage.setScene(scene1);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
