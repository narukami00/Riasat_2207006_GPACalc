package com.project.gpa_calculator.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;

import java.io.IOException;

import com.project.gpa_calculator.model.Course;
import com.project.gpa_calculator.model.Grade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;

public class CourseEntryController {

    @FXML private TextField tfCourseName;
    @FXML private TextField tfCourseCode;
    @FXML private TextField tfCredit;
    @FXML private TextField tfTeacher1;
    @FXML private TextField tfTeacher2;
    @FXML private ComboBox<String> cbGrade;

    @FXML private TableView<Course> tableCourses;
    @FXML private TableColumn<Course, String> colName;
    @FXML private TableColumn<Course, String> colCode;
    @FXML private TableColumn<Course, Double> colCredit;
    @FXML private TableColumn<Course, String> colT1;
    @FXML private TableColumn<Course, String> colT2;
    @FXML private TableColumn<Course, String> colGrade;
    @FXML private TableColumn<Course, Void> colAction;

    @FXML private Label lblCreditsEntered;
    @FXML private Label lblTotalCredits;
    @FXML private Button btnCalculate;

    private ObservableList<Course> courses = FXCollections.observableArrayList();
    private double totalCredits = 0.0;
    private double currentCredits = 0.0;

    @FXML
    public void initialize() {
        cbGrade.setItems(FXCollections.observableArrayList(Grade.gradeKeys()));

        colName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCourseName()));
        colCode.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCourseCode()));
        colCredit.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getCredit()));
        colT1.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTeacher1()));
        colT2.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTeacher2()));
        colGrade.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getGrade()));

        tableCourses.setItems(courses);
        tableCourses.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        addDeleteButtonToTable();
    }

    private void addDeleteButtonToTable() {
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button deleteBtn = new Button("🗑 Delete");

            {
                deleteBtn.getStyleClass().add("delete-btn");
                deleteBtn.setOnAction(event -> {
                    Course course = getTableView().getItems().get(getIndex());
                    deleteCourse(course);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox box = new HBox(deleteBtn);
                    box.setSpacing(5);
                    setGraphic(box);
                }
            }
        });
    }

    private void deleteCourse(Course course) {
        if (course == null) return;
        courses.remove(course);
        currentCredits -= course.getCredit();
        updateCreditLabels();
    }

    private void updateCreditLabels() {
        lblCreditsEntered.setText(String.format("%.1f", currentCredits));
        lblTotalCredits.setText(String.format("%.1f", totalCredits));
        btnCalculate.setDisable(currentCredits < totalCredits);
    }

    public void setTotalCredits(double totalCredits) {
        this.totalCredits = totalCredits;
        updateCreditLabels();
    }

    @FXML
    private void onAddCourse(ActionEvent event) {
        String name = tfCourseName.getText().trim();
        String code = tfCourseCode.getText().trim();
        String credStr = tfCredit.getText().trim();
        String t1 = tfTeacher1.getText().trim();
        String t2 = tfTeacher2.getText().trim();
        String grade = cbGrade.getValue();

        if (name.isEmpty() || code.isEmpty() || credStr.isEmpty() || grade == null) {
            showAlert(Alert.AlertType.ERROR, "Validation", "Please fill course name, code, credit and select grade.");
            return;
        }

        double credit;
        try {
            credit = Double.parseDouble(credStr);
            if (credit <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Validation", "Credit must be a positive number (e.g., 3 or 1.5).");
            return;
        }

        //Allow exceeding total credits
//        if (currentCredits + credit > totalCredits) {
//            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
//            confirm.setTitle("Confirm Credit Exceed");
//            confirm.setHeaderText("Adding this course will exceed the total defined credits.");
//            confirm.setContentText("Total: " + totalCredits + " | Current: " + currentCredits + " | This course: " + credit + "\nProceed?");
//            Optional<ButtonType> res = confirm.showAndWait();
//            if (res.isEmpty() || res.get() != ButtonType.OK) {
//                return;
//            }
//        }

        // STRICT: Do NOT allow exceeding total credits
        if (currentCredits + credit > totalCredits) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Credit Limit Exceeded");
            alert.setHeaderText("Cannot Add Course");
            alert.setContentText(
                    "Adding this course exceeds the total credit limit.\n\n" +
                            "Total Allowed Credits: " + totalCredits + "\n" +
                            "Currently Added: " + currentCredits + "\n" +
                            "This Course Credit: " + credit + "\n\n" +
                            "Please modify or delete some previous courses."
            );
            alert.showAndWait();
            return;
        }


        Course c = new Course(name, code, credit, t1, t2, grade);
        courses.add(c);
        currentCredits += credit;
        updateCreditLabels();

        tfCourseName.clear();
        tfCourseCode.clear();
        tfCredit.clear();
        tfTeacher1.clear();
        tfTeacher2.clear();
        cbGrade.getSelectionModel().clearSelection();

        showAlert(Alert.AlertType.INFORMATION, "Success", "Course added successfully!");
    }

    @FXML
    private void onReset(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setHeaderText("Reset all entered courses?");
        a.setContentText("This will clear the table and reset entered credits.");
        Optional<ButtonType> r = a.showAndWait();
        if (r.isPresent() && r.get() == ButtonType.OK) {
            courses.clear();
            currentCredits = 0.0;
            updateCreditLabels();
        }
    }

    @FXML
    private void onCalculate(ActionEvent event) {
        if (currentCredits < totalCredits) {
            showAlert(Alert.AlertType.ERROR, "Not enough credits", "Please add courses until total credits are reached.");
            return;
        }

        double totalWeighted = 0.0;
        for (Course c : courses) {
            double gp = Grade.toPoint(c.getGrade());
            totalWeighted += gp * c.getCredit();
        }
        double gpa = totalWeighted / totalCredits;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/project/gpa_calculator/Result.fxml"));
            Parent root = loader.load();
            ResultController rc = loader.getController();
            rc.setData(courses, gpa, totalCredits);

            Stage stage = (Stage) btnCalculate.getScene().getWindow();
            Scene scene = new Scene(root, 1000, 750);
            URL css = getClass().getResource("/com/project/gpa_calculator/css/styles.css");
            if (css != null) scene.getStylesheets().add(css.toExternalForm());

            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.INFORMATION, "GPA Result", String.format("GPA: %.3f", gpa));
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
