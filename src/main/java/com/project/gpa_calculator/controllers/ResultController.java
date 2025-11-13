package com.project.gpa_calculator.controllers;

import com.project.gpa_calculator.model.Course;
import com.project.gpa_calculator.model.Grade;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class ResultController {

    @FXML private TableView<Course> tableResult;
    @FXML private TableColumn<Course, String> rColName;
    @FXML private TableColumn<Course, String> rColCode;
    @FXML private TableColumn<Course, Double> rColCredit;
    @FXML private TableColumn<Course, String> rColGrade;
    @FXML private TableColumn<Course, Double> rColPoints;

    @FXML private Label lblTotalCredits;
    @FXML private Label lblGPA;

    public void initialize() {
        rColName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCourseName()));
        rColCode.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCourseCode()));
        rColCredit.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getCredit()));
        rColGrade.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getGrade()));
        rColPoints.setCellValueFactory(c -> new SimpleObjectProperty<>(Grade.toPoint(c.getValue().getGrade())));
    }

    public void setData(List<Course> courseList, double gpa, double totalCredits) {
        ObservableList<Course> items = FXCollections.observableArrayList(courseList);
        tableResult.setItems(items);

        lblTotalCredits.setText(String.format("%.1f", totalCredits));
        lblGPA.setText(String.format("%.3f", gpa));
    }

    @FXML
    private void onBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/project/gpa_calculator/Home.fxml"));
            Parent root = loader.load();
            Stage st = (Stage) lblGPA.getScene().getWindow();
            st.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
