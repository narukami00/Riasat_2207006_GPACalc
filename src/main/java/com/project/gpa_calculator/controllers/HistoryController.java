package com.project.gpa_calculator.controllers;

import com.project.gpa_calculator.model.Course;
import com.project.gpa_calculator.model.HistoryRecord;
import com.project.gpa_calculator.service.DatabaseService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @FXML
    private Button clearAllBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button deleteBtn;

    private ObservableList<HistoryRecord> historyRecords;
    private DatabaseService dbService;
    private HistoryRecord selectedRecord;

    @FXML
    public void initialize() {
        dbService = DatabaseService.getInstance();
        historyRecords = FXCollections.observableArrayList();
        loadHistoryFromDatabase();

        historyListView.setCellFactory(param -> new ListCell<HistoryRecord>() {
            @Override
            protected void updateItem(HistoryRecord record, boolean empty) {
                super.updateItem(record, empty);
                if (empty || record == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
                    
                    Label textLabel = new Label(String.format("📅 %s | 📚 Credits: %.1f | ",
                            record.getTimestamp().format(formatter),
                            record.getTotalCredits()));
                    textLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 13px;");
                    
                    Label cgpaLabel = new Label(String.format("🎯 CGPA: %.2f", record.getCgpa()));
                    cgpaLabel.setStyle("-fx-text-fill: #667eea; -fx-font-size: 14px; -fx-font-weight: bold;");
                    
                    HBox contentBox = new HBox(10, textLabel, cgpaLabel);
                    contentBox.setAlignment(Pos.CENTER_LEFT);
                    
                    setGraphic(contentBox);
                }
            }
        });

        historyListView.setItems(historyRecords);

        historyListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedRecord = newValue;
                displayCourseDetails(newValue);
                updateBtn.setDisable(false);
                deleteBtn.setDisable(false);
            } else {
                selectedRecord = null;
                updateBtn.setDisable(true);
                deleteBtn.setDisable(true);
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

    private void loadHistoryFromDatabase() {
        historyRecords.clear();
        List<HistoryRecord> records = dbService.getAllHistoryRecords();
        historyRecords.addAll(records);
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
    public void onUpdate() {
        if (selectedRecord == null) return;
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/project/gpa_calculator/CourseEntry.fxml"));
            Parent root = fxmlLoader.load();
            CourseEntryController controller = fxmlLoader.getController();
            controller.loadHistoryRecord(selectedRecord);

            Stage stage = (Stage) historyListView.getScene().getWindow();
            Scene scene = new Scene(root, 1600, 750);
            scene.getStylesheets().add(getClass().getResource("/com/project/gpa_calculator/css/styles.css").toExternalForm());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Navigation Error");
            alert.setContentText("Could not open course entry page.");
            alert.showAndWait();
        }
    }

    @FXML
    public void onDelete() {
        if (selectedRecord == null) return;
        
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Record");
        confirm.setHeaderText("Are you sure you want to delete this record?");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
        confirm.setContentText(String.format("Record from: %s\nCGPA: %.2f",
                selectedRecord.getTimestamp().format(formatter),
                selectedRecord.getCgpa()));

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = dbService.deleteHistoryRecord(selectedRecord.getId());
            if (success) {
                historyRecords.remove(selectedRecord);
                courseTableView.getItems().clear();
                detailsLabel.setText("Select a history record to view course details");
                selectedRecord = null;
                updateBtn.setDisable(true);
                deleteBtn.setDisable(true);
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Record deleted successfully!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to delete record");
                alert.setContentText("An error occurred while deleting the record.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void onClearAll() {
        if (historyRecords.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Records");
            alert.setHeaderText(null);
            alert.setContentText("There are no history records to clear.");
            alert.showAndWait();
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Clear All History");
        confirm.setHeaderText("Are you sure you want to delete ALL history records?");
        confirm.setContentText("This action cannot be undone. All " + historyRecords.size() + " records will be permanently deleted.");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = dbService.clearAllHistory();
            if (success) {
                historyRecords.clear();
                courseTableView.getItems().clear();
                detailsLabel.setText("Select a history record to view course details");
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("All history records have been cleared!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to clear history");
                alert.setContentText("An error occurred while clearing the history.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void onBack() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/project/gpa_calculator/Home.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) backBtn.getScene().getWindow();
            Scene scene = new Scene(root, 1600, 750);
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
