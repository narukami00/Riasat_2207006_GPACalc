package com.project.gpa_calculator.service;

import com.project.gpa_calculator.model.Course;
import com.project.gpa_calculator.model.Grade;

import java.util.List;
import java.util.concurrent.*;

public class GPACalculationService {
    private static final int PARALLEL_THRESHOLD = 10;
    private final ExecutorService executorService;

    public GPACalculationService() {
        this.executorService = Executors.newWorkStealingPool();
    }

    public double calculateGPA(List<Course> courses, double totalCredits) {
        if (courses.isEmpty()) {
            return 0.0;
        }

        if (courses.size() < PARALLEL_THRESHOLD) {
            return calculateGPASequential(courses, totalCredits);
        } else {
            return calculateGPAParallel(courses, totalCredits);
        }
    }

    private double calculateGPASequential(List<Course> courses, double totalCredits) {
        double totalWeighted = 0.0;
        for (Course c : courses) {
            double gp = Grade.toPoint(c.getGrade());
            totalWeighted += gp * c.getCredit();
        }
        return totalWeighted / totalCredits;
    }

    private double calculateGPAParallel(List<Course> courses, double totalCredits) {
        try {
            double totalWeighted = courses.parallelStream()
                .mapToDouble(course -> Grade.toPoint(course.getGrade()) * course.getCredit())
                .sum();
            return totalWeighted / totalCredits;
        } catch (Exception e) {
            return calculateGPASequential(courses, totalCredits);
        }
    }

    public CompletableFuture<Double> calculateGPAAsync(List<Course> courses, double totalCredits) {
        return CompletableFuture.supplyAsync(() -> calculateGPA(courses, totalCredits), executorService);
    }

    public record GPAResult(double gpa, double totalWeighted, double totalCredits) {}

    public CompletableFuture<GPAResult> calculateDetailedGPAAsync(List<Course> courses, double totalCredits) {
        return CompletableFuture.supplyAsync(() -> {
            double totalWeighted = courses.parallelStream()
                .mapToDouble(course -> Grade.toPoint(course.getGrade()) * course.getCredit())
                .sum();
            double gpa = totalWeighted / totalCredits;
            return new GPAResult(gpa, totalWeighted, totalCredits);
        }, executorService);
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
