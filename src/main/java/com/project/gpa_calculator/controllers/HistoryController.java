package com.project.gpa_calculator.controllers;

import com.project.gpa_calculator.model.Course;
import com.project.gpa_calculator.model.HistoryRecord;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HistoryController {
    @FXML
    private ListView<HistoryRecord> historyListView;

    @FXML
    private TableView<Course> courseTableView;

    @FXML
    private TableColumn<Course, String> courseNameCol;

    @FXML
    private TableColumn<Course, String> courseCodeCol;

    @FXML
    private TableColumn<Course, Double> creditCol;

    @FXML
    private TableColumn<Course, String> teacher1Col;

    @FXML
    private TableColumn<Course, String> teacher2Col;

    @FXML
    private TableColumn<Course, String> gradeCol;

    @FXML
    private Label detailsLabel;

    @FXML
    private Button backBtn;

    private ObservableList<HistoryRecord> historyRecords;

    @FXML
    public void initialize() {
        historyRecords = FXCollections.observableArrayList();
        loadDummyData();

        historyListView.setCellFactory(param -> new ListCell<HistoryRecord>() {
            @Override
            protected void updateItem(HistoryRecord record, boolean empty) {
                super.updateItem(record, empty);
                if (empty || record == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
                    String displayText = String.format("📅 %s | 📚 Credits: %.1f | 🎯 CGPA: %.2f",
                            record.getTimestamp().format(formatter),
                            record.getTotalCredits(),
                            record.getCgpa());
                    setText(displayText);
                }
            }
        });

        historyListView.setItems(historyRecords);

        historyListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                displayCourseDetails(newValue);
            }
        });

        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseCodeCol.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        creditCol.setCellValueFactory(new PropertyValueFactory<>("credit"));
        teacher1Col.setCellValueFactory(new PropertyValueFactory<>("teacher1"));
        teacher2Col.setCellValueFactory(new PropertyValueFactory<>("teacher2"));
        gradeCol.setCellValueFactory(new PropertyValueFactory<>("grade"));

        detailsLabel.setText("Select a history record to view course details");
    }

    private void loadDummyData() {
        List<Course> courses1 = new ArrayList<>();
        courses1.add(new Course("Data Structures", "CSE201", 3.0, "Dr. Smith", "Dr. Johnson", "A"));
        courses1.add(new Course("Algorithms", "CSE202", 3.0, "Dr. Williams", "Dr. Brown", "A-"));
        courses1.add(new Course("Database Systems", "CSE301", 3.0, "Dr. Davis", "Dr. Miller", "B+"));
        courses1.add(new Course("Web Development", "CSE305", 3.0, "Dr. Wilson", "Dr. Moore", "A"));
        courses1.add(new Course("Software Engineering", "CSE401", 3.0, "Dr. Taylor", "Dr. Anderson", "A-"));

        List<Course> courses2 = new ArrayList<>();
        courses2.add(new Course("Operating Systems", "CSE303", 3.0, "Dr. Thomas", "Dr. Jackson", "B+"));
        courses2.add(new Course("Computer Networks", "CSE304", 3.0, "Dr. White", "Dr. Harris", "A"));
        courses2.add(new Course("Machine Learning", "CSE402", 3.0, "Dr. Martin", "Dr. Thompson", "A"));
        courses2.add(new Course("Artificial Intelligence", "CSE403", 3.0, "Dr. Garcia", "Dr. Martinez", "A-"));

        List<Course> courses3 = new ArrayList<>();
        courses3.add(new Course("Mobile App Development", "CSE306", 3.0, "Dr. Robinson", "Dr. Clark", "A"));
        courses3.add(new Course("Cloud Computing", "CSE404", 3.0, "Dr. Rodriguez", "Dr. Lewis", "B+"));
        courses3.add(new Course("Cybersecurity", "CSE405", 3.0, "Dr. Lee", "Dr. Walker", "A-"));

        historyRecords.add(new HistoryRecord(
                LocalDateTime.now().minusDays(15),
                15.0,
                3.75,
                courses1
        ));

        historyRecords.add(new HistoryRecord(
                LocalDateTime.now().minusDays(45),
                12.0,
                3.67,
                courses2
        ));

        historyRecords.add(new HistoryRecord(
                LocalDateTime.now().minusDays(90),
                9.0,
                3.83,
                courses3
        ));
    }

    private void displayCourseDetails(HistoryRecord record) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
        detailsLabel.setText(String.format("Calculation from %s | Total Credits: %.1f | CGPA: %.2f",
                record.getTimestamp().format(formatter),
                record.getTotalCredits(),
                record.getCgpa()));

        ObservableList<Course> courses = FXCollections.observableArrayList(record.getCourses());
        courseTableView.setItems(courses);
    }

    @FXML
    public void onBack() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/project/gpa_calculator/Home.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) backBtn.getScene().getWindow();
            Scene scene = new Scene(root, 1000, 750);
            scene.getStylesheets().add(getClass().getResource("/com/project/gpa_calculator/css/styles.css").toExternalForm());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Navigation Error");
            alert.setContentText("Could not navigate back to home page.");
            alert.showAndWait();
        }
    }
}
