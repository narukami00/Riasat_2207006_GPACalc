package com.project.gpa_calculator;

import com.project.gpa_calculator.service.DatabaseService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Home.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1600, 750);
        scene.getStylesheets().add(getClass().getResource("/com/project/gpa_calculator/css/styles.css").toExternalForm());
        stage.setTitle("GPA Calculator");
        stage.setMinWidth(1440);
        stage.setMinHeight(700);
        stage.setScene(scene);
        
        stage.setOnCloseRequest(event -> {
            DatabaseService.getInstance().shutdown();
        });
        
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        DatabaseService.getInstance().shutdown();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
