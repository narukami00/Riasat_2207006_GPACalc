package com.project.gpa_calculator;

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
        Scene scene = new Scene(root,800,560);
        scene.getStylesheets().add(getClass().getResource("/com/project/gpa_calculator/css/styles.css").toExternalForm());
        stage.setTitle("GPA Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
