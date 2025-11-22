package com.project.gpa_calculator.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.gpa_calculator.model.Course;
import com.project.gpa_calculator.model.HistoryRecord;

import java.lang.reflect.Type;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {
    private static final String DB_URL = "jdbc:sqlite:gpa_history.db";
    private static final Gson gson = new Gson();
    private static DatabaseService instance;

    private DatabaseService() {
        initializeDatabase();
    }

    public static DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }

    private void initializeDatabase() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS history_records (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                timestamp TEXT NOT NULL,
                total_credits REAL NOT NULL,
                cgpa REAL NOT NULL,
                courses_json TEXT NOT NULL
            )
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    public long saveHistoryRecord(HistoryRecord record) {
        String sql = "INSERT INTO history_records (timestamp, total_credits, cgpa, courses_json) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, record.getTimestamp().toString());
            pstmt.setDouble(2, record.getTotalCredits());
            pstmt.setDouble(3, record.getCgpa());
            pstmt.setString(4, gson.toJson(record.getCourses()));
            
            pstmt.executeUpdate();
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save history record", e);
        }
        return -1;
    }

    public List<HistoryRecord> getAllHistoryRecords() {
        List<HistoryRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM history_records ORDER BY timestamp DESC";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            Type courseListType = new TypeToken<List<Course>>(){}.getType();
            
            while (rs.next()) {
                HistoryRecord record = new HistoryRecord();
                record.setId(rs.getLong("id"));
                record.setTimestamp(LocalDateTime.parse(rs.getString("timestamp")));
                record.setTotalCredits(rs.getDouble("total_credits"));
                record.setCgpa(rs.getDouble("cgpa"));
                
                String coursesJson = rs.getString("courses_json");
                List<Course> courses = gson.fromJson(coursesJson, courseListType);
                record.setCourses(courses);
                
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return records;
    }

    public HistoryRecord getHistoryRecordById(long id) {
        String sql = "SELECT * FROM history_records WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Type courseListType = new TypeToken<List<Course>>(){}.getType();
                    
                    HistoryRecord record = new HistoryRecord();
                    record.setId(rs.getLong("id"));
                    record.setTimestamp(LocalDateTime.parse(rs.getString("timestamp")));
                    record.setTotalCredits(rs.getDouble("total_credits"));
                    record.setCgpa(rs.getDouble("cgpa"));
                    
                    String coursesJson = rs.getString("courses_json");
                    List<Course> courses = gson.fromJson(coursesJson, courseListType);
                    record.setCourses(courses);
                    
                    return record;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public boolean updateHistoryRecord(long id, HistoryRecord record) {
        String sql = "UPDATE history_records SET timestamp = ?, total_credits = ?, cgpa = ?, courses_json = ? WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, record.getTimestamp().toString());
            pstmt.setDouble(2, record.getTotalCredits());
            pstmt.setDouble(3, record.getCgpa());
            pstmt.setString(4, gson.toJson(record.getCourses()));
            pstmt.setLong(5, id);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteHistoryRecord(long id) {
        String sql = "DELETE FROM history_records WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean clearAllHistory() {
        String sql = "DELETE FROM history_records";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
