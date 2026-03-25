package com.university.dao;

import com.university.db.DBConnection;
import com.university.model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    public void addCourse(Course course) throws SQLException {
        String sql = "INSERT INTO courses(title, credits, faculty_id) VALUES(?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, course.getTitle());
            statement.setInt(2, course.getCredits());
            statement.setInt(3, course.getFacultyId());
            statement.executeUpdate();
        }
    }

    public List<Course> getAllCourses() throws SQLException {
        String sql = "SELECT id, title, credits, faculty_id FROM courses ORDER BY id";
        List<Course> courses = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                courses.add(new Course(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("credits"),
                        resultSet.getInt("faculty_id")
                ));
            }
        }

        return courses;
    }
}
