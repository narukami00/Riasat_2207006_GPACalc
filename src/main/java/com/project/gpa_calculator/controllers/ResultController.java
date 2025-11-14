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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    
    private List<Course> currentCourses;
    private double currentGPA;
    private double currentTotalCredits;

    public void initialize() {
        rColName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCourseName()));
        rColCode.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCourseCode()));
        rColCredit.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getCredit()));
        rColGrade.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getGrade()));
        rColPoints.setCellValueFactory(c -> new SimpleObjectProperty<>(Grade.toPoint(c.getValue().getGrade())));
        
        tableResult.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void setData(List<Course> courseList, double gpa, double totalCredits) {
        this.currentCourses = courseList;
        this.currentGPA = gpa;
        this.currentTotalCredits = totalCredits;
        
        ObservableList<Course> items = FXCollections.observableArrayList(courseList);
        tableResult.setItems(items);

        lblTotalCredits.setText(String.format("%.1f", totalCredits));
        lblGPA.setText(String.format("%.3f", gpa));
    }

    @FXML
    private void onExport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save GPA Results");
        fileChooser.setInitialFileName("GPA_Results_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".docx");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Word Document", "*.docx"));
        
        Stage stage = (Stage) lblGPA.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        
        if (file != null) {
            try {
                exportToWord(file);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Export Successful");
                alert.setHeaderText(null);
                alert.setContentText("Results exported successfully to:\n" + file.getAbsolutePath());
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Export Failed");
                alert.setHeaderText("Failed to export results");
                alert.setContentText("Error: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }
    
    private void exportToWord(File file) throws Exception {
        XWPFDocument document = new XWPFDocument();
        
        // Title
        XWPFParagraph titlePara = document.createParagraph();
        titlePara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = titlePara.createRun();
        titleRun.setText("GPA Calculation Results");
        titleRun.setBold(true);
        titleRun.setFontSize(24);
        titleRun.setColor("667eea");
        titleRun.addBreak();
        
        // Date
        XWPFParagraph datePara = document.createParagraph();
        datePara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun dateRun = datePara.createRun();
        dateRun.setText("Generated on: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm a")));
        dateRun.setItalic(true);
        dateRun.setFontSize(11);
        dateRun.addBreak();
        dateRun.addBreak();
        
        // Summary Section
        XWPFParagraph summaryTitle = document.createParagraph();
        XWPFRun summaryTitleRun = summaryTitle.createRun();
        summaryTitleRun.setText("Summary");
        summaryTitleRun.setBold(true);
        summaryTitleRun.setFontSize(16);
        summaryTitleRun.setColor("667eea");
        
        XWPFParagraph summaryPara = document.createParagraph();
        XWPFRun summaryRun = summaryPara.createRun();
        summaryRun.setText("Total Credits: " + String.format("%.1f", currentTotalCredits));
        summaryRun.setFontSize(12);
        summaryRun.addBreak();
        summaryRun.setText("Final GPA: " + String.format("%.3f", currentGPA));
        summaryRun.setBold(true);
        summaryRun.setFontSize(14);
        summaryRun.setColor("f907dc");
        summaryRun.addBreak();
        summaryRun.addBreak();
        
        // Course Details Table
        XWPFParagraph tableTitle = document.createParagraph();
        XWPFRun tableTitleRun = tableTitle.createRun();
        tableTitleRun.setText("Course Details");
        tableTitleRun.setBold(true);
        tableTitleRun.setFontSize(16);
        tableTitleRun.setColor("667eea");
        
        // Create table
        XWPFTable table = document.createTable();
        table.setWidth("100%");
        
        // Header row
        XWPFTableRow headerRow = table.getRow(0);
        headerRow.getCell(0).setText("Course Name");
        headerRow.addNewTableCell().setText("Code");
        headerRow.addNewTableCell().setText("Credit");
        headerRow.addNewTableCell().setText("Grade");
        headerRow.addNewTableCell().setText("Grade Points");
        
        // Style header
        for (XWPFTableCell cell : headerRow.getTableCells()) {
            cell.setColor("667eea");
            XWPFParagraph para = cell.getParagraphs().get(0);
            para.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = para.getRuns().get(0);
            run.setBold(true);
            run.setColor("FFFFFF");
        }
        
        // Data rows
        for (Course course : currentCourses) {
            XWPFTableRow row = table.createRow();
            row.getCell(0).setText(course.getCourseName());
            row.getCell(1).setText(course.getCourseCode());
            row.getCell(2).setText(String.format("%.1f", course.getCredit()));
            row.getCell(3).setText(course.getGrade());
            row.getCell(4).setText(String.format("%.2f", Grade.toPoint(course.getGrade())));
            
            // Center align cells
            for (int i = 1; i < row.getTableCells().size(); i++) {
                row.getCell(i).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
            }
        }
        
        // Footer
        XWPFParagraph footerPara = document.createParagraph();
        footerPara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun footerRun = footerPara.createRun();
        footerRun.addBreak();
        footerRun.addBreak();
        footerRun.setText("Generated by GPA Calculator");
        footerRun.setItalic(true);
        footerRun.setFontSize(10);
        footerRun.setColor("999999");
        
        // Write to file
        try (FileOutputStream out = new FileOutputStream(file)) {
            document.write(out);
        }
        document.close();
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
