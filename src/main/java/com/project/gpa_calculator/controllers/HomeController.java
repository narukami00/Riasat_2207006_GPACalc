package com.project.gpa_calculator.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.util.Optional;

public class HomeController {
    @FXML
    private Button startBtn;

    @FXML
    public void onStart(){
        TextInputDialog dialog = new TextInputDialog("15");
        dialog.setTitle("Total Credits");
        dialog.setHeaderText("Define total credits for this GPA session");
        dialog.setContentText("Please enter the total credits for this GPA session:");

        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()){
            String totalCredits = result.get().trim();
            try{
                double total = Double.parseDouble(totalCredits);
                try{
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CourseEntry.fxml"));
                    Parent root = fxmlLoader.load();
                    CourseController controller = fxmlLoader.<CourseController>getController();
                    controller.setTotalCredits(total);

                    Stage stage = (Stage)  startBtn.getScene().getWindow();
                    Scene scene = new Scene(root,900,600);
                    scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
                    stage.setScene(scene);
                }catch(Exception ex){
                    ex.printStackTrace();
                    javafx.scene.control.Alert a = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                    a.setHeaderText("Course Entry not ready yet");
                    a.setContentText("CourseEntry.fxml/controller will be implemented in next step.\nTotal credits: " + total);
                    a.showAndWait();
                }
            }catch(NumberFormatException e){
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                alert.setHeaderText("Invalid number");
                alert.setContentText("Please enter a valid numeric credit value.");
                alert.showAndWait();
            }
        }
    }
}
