package com.project.gpa_calculator.controllers;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
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

        lblTotalCredits.setText(String.format("%.2f", totalCredits));
        lblGPA.setText(String.format("%.2f", gpa));
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
        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdf = new PdfDocument(writer);
        com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdf);

        PdfFont poppins = PdfFontFactory.createFont(
                getClass().getResourceAsStream("/com/project/gpa_calculator/fonts/Poppins-Regular.ttf").readAllBytes(),
                PdfEncodings.IDENTITY_H,
                PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED
        );

        PdfFont poppinsBold = PdfFontFactory.createFont(
                getClass().getResourceAsStream("/com/project/gpa_calculator/fonts/Poppins-Bold.ttf").readAllBytes(),
                PdfEncodings.IDENTITY_H,
                PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED
        );

        PdfFont poppinsSemiBold = PdfFontFactory.createFont(
                getClass().getResourceAsStream("/com/project/gpa_calculator/fonts/Poppins-SemiBold.ttf").readAllBytes(),
                PdfEncodings.IDENTITY_H,
                PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED
        );

        document.setMargins(50, 50, 50, 50);

        Paragraph title = new Paragraph("ACADEMIC ACHIEVEMENT CERTIFICATE")
            .setFont(poppinsBold)
            .setFontSize(20)
            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
            .setFontColor(com.itextpdf.kernel.colors.ColorConstants.BLUE)
            .setBorder(new com.itextpdf.layout.borders.SolidBorder(com.itextpdf.kernel.colors.ColorConstants.BLUE, 2))
            .setPadding(10);
        document.add(title);
        
        Paragraph subtitle = new Paragraph("Grade Point Average Report")
            .setFont(poppinsSemiBold)
            .setFontSize(14)
            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
            .setFontColor(new com.itextpdf.kernel.colors.DeviceRgb(102, 126, 234))
            .setMarginBottom(5);
        document.add(subtitle);

        Paragraph date = new Paragraph(
            "Published: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy • hh:mm a")))
            .setFont(poppins)
            .setFontSize(10)
            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
            .setFontColor(com.itextpdf.kernel.colors.ColorConstants.DARK_GRAY)
            .setMarginBottom(20);
        document.add(date);

        Div gpaBox = new Div()
            .setBorder(new com.itextpdf.layout.borders.SolidBorder(new com.itextpdf.kernel.colors.DeviceRgb(102, 126, 234), 2))
            .setPadding(20)
            .setMarginBottom(25)
            .setBackgroundColor(new com.itextpdf.kernel.colors.DeviceRgb(245, 247, 255));
        
        Paragraph gpaLabel = new Paragraph("CUMULATIVE GPA")
            .setFont(poppinsSemiBold)
            .setFontSize(12)
            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
            .setFontColor(new com.itextpdf.kernel.colors.DeviceRgb(102, 126, 234))
            .setMarginBottom(5);
        gpaBox.add(gpaLabel);
        
        Paragraph gpaValue = new Paragraph(String.format("%.2f", currentGPA))
            .setFont(poppinsBold)
            .setFontSize(42)
            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
            .setFontColor(com.itextpdf.kernel.colors.ColorConstants.BLUE);
        gpaBox.add(gpaValue);
        
        Paragraph gpaOut = new Paragraph("out of 4.00")
            .setFont(poppins)
            .setFontSize(10)
            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
            .setFontColor(com.itextpdf.kernel.colors.ColorConstants.GRAY)
            .setMarginBottom(10);
        gpaBox.add(gpaOut);
        
        Paragraph credits = new Paragraph(
            "Total Credits: " + String.format("%.2f", currentTotalCredits) + " hours")
            .setFont(poppinsSemiBold)
            .setFontSize(12)
            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
            .setFontColor(new com.itextpdf.kernel.colors.DeviceRgb(118, 75, 162));
        gpaBox.add(credits);
        
        document.add(gpaBox);

        Paragraph tableTitle = new Paragraph("COURSE BREAKDOWN")
            .setFont(poppinsSemiBold)
            .setFontSize(14)
            .setFontColor(com.itextpdf.kernel.colors.ColorConstants.BLUE)
            .setMarginBottom(10);
        document.add(tableTitle);

        Table table = new Table(new float[]{3, 1.5f, 1, 1, 1.2f})
            .useAllAvailableWidth();

        com.itextpdf.kernel.colors.Color headerBg = new com.itextpdf.kernel.colors.DeviceRgb(30, 58, 138);
        String[] headers = {"Course Name", "Code", "Credit", "Grade", "Points"};
        for (String header : headers) {
            table.addHeaderCell(new Cell()
                .add(new Paragraph(header)
                    .setFont(poppinsSemiBold)
                    .setFontSize(11)
                    .setFontColor(com.itextpdf.kernel.colors.ColorConstants.WHITE))
                .setBackgroundColor(headerBg)
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                .setPadding(8));
        }

        boolean alternate = false;
        for (Course course : currentCourses) {
            com.itextpdf.kernel.colors.Color rowBg = alternate ? 
                new com.itextpdf.kernel.colors.DeviceRgb(240, 244, 255) : 
                com.itextpdf.kernel.colors.ColorConstants.WHITE;
            
            table.addCell(new Cell()
                .add(new Paragraph(course.getCourseName()).setFont(poppins).setFontSize(10))
                .setBackgroundColor(rowBg)
                .setPadding(6));
            
            table.addCell(new Cell()
                .add(new Paragraph(course.getCourseCode()).setFont(poppins).setFontSize(10))
                .setBackgroundColor(rowBg)
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                .setPadding(6));
            
            table.addCell(new Cell()
                .add(new Paragraph(String.format("%.2f", course.getCredit())).setFont(poppins).setFontSize(10))
                .setBackgroundColor(rowBg)
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                .setPadding(6));
            
            table.addCell(new Cell()
                .add(new Paragraph(course.getGrade()).setFont(poppinsSemiBold).setFontSize(10))
                .setBackgroundColor(rowBg)
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                .setPadding(6));
            
            table.addCell(new Cell()
                .add(new Paragraph(String.format("%.2f", Grade.toPoint(course.getGrade()))).setFont(poppins).setFontSize(10))
                .setBackgroundColor(rowBg)
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                .setPadding(6));
            
            alternate = !alternate;
        }
        
        document.add(table);

        Paragraph footer = new Paragraph("\nKeep up the excellent work!")
            .setFont(poppinsSemiBold)
            .setFontSize(11)
            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
            .setFontColor(new com.itextpdf.kernel.colors.DeviceRgb(102, 126, 234))
            .setMarginTop(20);
        document.add(footer);

        Paragraph footerInfo = new Paragraph(
            "This document was generated by GPA Calculator")
            .setFont(poppins)
            .setFontSize(9)
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
