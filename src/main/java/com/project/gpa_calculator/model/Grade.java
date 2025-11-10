package com.project.gpa_calculator.model;
import java.util.Map;
import java.util.HashMap;

public class Grade{
    private static final Map<String,Double> gradePointMap = new HashMap<>();
    static{
        gradePointMap.put("A+", 4.0);
        gradePointMap.put("A", 3.75);
        gradePointMap.put("A-", 3.50);
        gradePointMap.put("B+", 3.25);
        gradePointMap.put("B", 3.0);
        gradePointMap.put("B-", 2.75);
        gradePointMap.put("C+", 2.5);
        gradePointMap.put("C", 2.25);
        gradePointMap.put("D", 2.0);
        gradePointMap.put("F", 0.0);
    }

    public static double toPoint(String grade){
        return gradePointMap.getOrDefault(grade, 0.0);
    }

    public static Map<String, Double> getGradeMap(){
        return gradePointMap;
    }
}
