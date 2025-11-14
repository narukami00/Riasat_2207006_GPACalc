package com.project.gpa_calculator.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/project/gpa_calculator/CourseEntry.fxml"));
                    Parent root = fxmlLoader.load();
                    CourseEntryController controller = fxmlLoader.getController();
                    controller.setTotalCredits(total);

                    Stage stage = (Stage)  startBtn.getScene().getWindow();
                    Scene scene = new Scene(root, 1000, 750);
                    scene.getStylesheets().add(getClass().getResource("/com/project/gpa_calculator/css/styles.css").toExternalForm());
                    stage.setScene(scene);
                }catch(Exception ex){
                    ex.printStackTrace();
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setHeaderText("Course Entry");
                    a.setContentText("Total credits: " + total);
                    a.showAndWait();
                }
            }catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Invalid number");
                alert.setContentText("Please enter a valid numeric credit value.");
                alert.showAndWait();
            }
        }
    }
}
