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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        fileChooser.setInitialFileName("GPA_Results_" + timestamp + ".pdf");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Document", "*.pdf"));
        
        Stage stage = (Stage) lblGPA.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        
        if (file != null) {
            try {
                exportToPDF(file);
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
    
    private void exportToPDF(File file) throws Exception {
        com.itextpdf.kernel.pdf.PdfWriter writer = new com.itextpdf.kernel.pdf.PdfWriter(file);
        com.itextpdf.kernel.pdf.PdfDocument pdf = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdf);
        
        // Set margins
        document.setMargins(50, 50, 50, 50);
        
        // Trophy icon
        com.itextpdf.layout.element.Paragraph trophy = new com.itextpdf.layout.element.Paragraph("🏆")
            .setFontSize(48)
            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
            .setMarginBottom(10);
        document.add(trophy);
        
        // Title with border
        com.itextpdf.layout.element.Paragraph title = new com.itextpdf.layout.element.Paragraph("ACADEMIC ACHIEVEMENT CERTIFICATE")
            .setFontSize(20)
            .setBold()
            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
            .setFontColor(com.itextpdf.kernel.colors.ColorConstants.BLUE)
            .setBorder(new com.itextpdf.layout.borders.SolidBorder(com.itextpdf.kernel.colors.ColorConstants.BLUE, 2))
            .setPadding(10);
        document.add(title);
        
        com.itextpdf.layout.element.Paragraph subtitle = new com.itextpdf.layout.element.Paragraph("Grade Point Average Report")
            .setFontSize(14)
            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
            .setFontColor(new com.itextpdf.kernel.colors.DeviceRgb(102, 126, 234))
            .setMarginBottom(5);
        document.add(subtitle);
        
        // Date
        com.itextpdf.layout.element.Paragraph date = new com.itextpdf.layout.element.Paragraph(
            "Generated: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy • hh:mm a")))
            .setFontSize(10)
            .setItalic()
            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
            .setFontColor(com.itextpdf.kernel.colors.ColorConstants.DARK_GRAY)
            .setMarginBottom(20);
        document.add(date);
        
        // GPA Box
        com.itextpdf.layout.element.Div gpaBox = new com.itextpdf.layout.element.Div()
            .setBorder(new com.itextpdf.layout.borders.SolidBorder(new com.itextpdf.kernel.colors.DeviceRgb(102, 126, 234), 2))
            .setPadding(20)
            .setMarginBottom(25)
            .setBackgroundColor(new com.itextpdf.kernel.colors.DeviceRgb(245, 247, 255));
        
        com.itextpdf.layout.element.Paragraph gpaLabel = new com.itextpdf.layout.element.Paragraph("CUMULATIVE GPA")
            .setFontSize(12)
            .setBold()
            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
            .setFontColor(new com.itextpdf.kernel.colors.DeviceRgb(102, 126, 234))
            .setMarginBottom(5);
        gpaBox.add(gpaLabel);
        
        com.itextpdf.layout.element.Paragraph gpaValue = new com.itextpdf.layout.element.Paragraph(String.format("%.3f", currentGPA))
            .setFontSize(42)
            .setBold()
            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
            .setFontColor(com.itextpdf.kernel.colors.ColorConstants.BLUE);
        gpaBox.add(gpaValue);
        
        com.itextpdf.layout.element.Paragraph gpaOut = new com.itextpdf.layout.element.Paragraph("out of 4.00")
            .setFontSize(10)
            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
            .setFontColor(com.itextpdf.kernel.colors.ColorConstants.GRAY)
            .setMarginBottom(10);
        gpaBox.add(gpaOut);
        
        com.itextpdf.layout.element.Paragraph credits = new com.itextpdf.layout.element.Paragraph(
            "Total Credits: " + String.format("%.1f", currentTotalCredits) + " hours")
            .setFontSize(12)
            .setBold()
            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
            .setFontColor(new com.itextpdf.kernel.colors.DeviceRgb(118, 75, 162));
        gpaBox.add(credits);
        
        document.add(gpaBox);
        
        // Course breakdown title
        com.itextpdf.layout.element.Paragraph tableTitle = new com.itextpdf.layout.element.Paragraph("📚  DETAILED COURSE BREAKDOWN")
            .setFontSize(14)
            .setBold()
            .setFontColor(com.itextpdf.kernel.colors.ColorConstants.BLUE)
            .setMarginBottom(10);
        document.add(tableTitle);
        
        // Create table
        com.itextpdf.layout.element.Table table = new com.itextpdf.layout.element.Table(new float[]{3, 1.5f, 1, 1, 1.2f})
            .useAllAvailableWidth();
        
        // Header row
        com.itextpdf.kernel.colors.Color headerBg = new com.itextpdf.kernel.colors.DeviceRgb(30, 58, 138);
        String[] headers = {"Course Name", "Code", "Credit", "Grade", "Points"};
        for (String header : headers) {
            table.addHeaderCell(new com.itextpdf.layout.element.Cell()
                .add(new com.itextpdf.layout.element.Paragraph(header)
                    .setFontSize(11)
                    .setBold()
                    .setFontColor(com.itextpdf.kernel.colors.ColorConstants.WHITE))
                .setBackgroundColor(headerBg)
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                .setPadding(8));
        }
        
        // Data rows with alternating colors
        boolean alternate = false;
        for (Course course : currentCourses) {
            com.itextpdf.kernel.colors.Color rowBg = alternate ? 
                new com.itextpdf.kernel.colors.DeviceRgb(240, 244, 255) : 
                com.itextpdf.kernel.colors.ColorConstants.WHITE;
            
            table.addCell(new com.itextpdf.layout.element.Cell()
                .add(new com.itextpdf.layout.element.Paragraph(course.getCourseName()).setFontSize(10))
                .setBackgroundColor(rowBg)
                .setPadding(6));
            
            table.addCell(new com.itextpdf.layout.element.Cell()
                .add(new com.itextpdf.layout.element.Paragraph(course.getCourseCode()).setFontSize(10))
                .setBackgroundColor(rowBg)
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                .setPadding(6));
            
            table.addCell(new com.itextpdf.layout.element.Cell()
                .add(new com.itextpdf.layout.element.Paragraph(String.format("%.1f", course.getCredit())).setFontSize(10))
                .setBackgroundColor(rowBg)
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                .setPadding(6));
            
            table.addCell(new com.itextpdf.layout.element.Cell()
                .add(new com.itextpdf.layout.element.Paragraph(course.getGrade()).setFontSize(10))
                .setBackgroundColor(rowBg)
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                .setPadding(6));
            
            table.addCell(new com.itextpdf.layout.element.Cell()
                .add(new com.itextpdf.layout.element.Paragraph(String.format("%.2f", Grade.toPoint(course.getGrade()))).setFontSize(10))
                .setBackgroundColor(rowBg)
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                .setPadding(6));
            
            alternate = !alternate;
        }
        
        document.add(table);
        
        // Footer
        com.itextpdf.layout.element.Paragraph footer = new com.itextpdf.layout.element.Paragraph("\n✓  Keep up the excellent work!")
            .setFontSize(11)
            .setBold()
            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
            .setFontColor(new com.itextpdf.kernel.colors.DeviceRgb(102, 126, 234))
            .setMarginTop(20);
        document.add(footer);
        
        com.itextpdf.layout.element.Paragraph footerInfo = new com.itextpdf.layout.element.Paragraph(
            "This document was automatically generated by GPA Calculator")
            .setFontSize(9)
            .setItalic()
            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
            .setFontColor(com.itextpdf.kernel.colors.ColorConstants.GRAY);
        document.add(footerInfo);
        
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
