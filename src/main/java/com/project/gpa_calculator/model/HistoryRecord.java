package com.project.gpa_calculator.model;

import java.time.LocalDateTime;
import java.util.List;

public class HistoryRecord {
    private long id;
    private LocalDateTime timestamp;
    private double totalCredits;
    private double cgpa;
    private List<Course> courses;

    public HistoryRecord() {}

    public HistoryRecord(LocalDateTime timestamp, double totalCredits, double cgpa, List<Course> courses) {
        this.timestamp = timestamp;
        this.totalCredits = totalCredits;
        this.cgpa = cgpa;
        this.courses = courses;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(double totalCredits) {
        this.totalCredits = totalCredits;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
