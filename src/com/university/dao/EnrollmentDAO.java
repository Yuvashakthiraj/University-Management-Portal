package com.university.dao;

import com.university.db.DBConnection;
import com.university.model.Enrollment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {
    public void addEnrollment(Enrollment enrollment) throws SQLException {
        String sql = "INSERT INTO enrollments(student_id, course_id, enrollment_date) VALUES(?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, enrollment.getStudentId());
            statement.setInt(2, enrollment.getCourseId());
            statement.setDate(3, Date.valueOf(enrollment.getEnrollmentDate()));
            statement.executeUpdate();
        }
    }

    public List<Enrollment> getAllEnrollments() throws SQLException {
        String sql = "SELECT id, student_id, course_id, enrollment_date FROM enrollments ORDER BY id";
        List<Enrollment> enrollments = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                enrollments.add(new Enrollment(
                        resultSet.getInt("id"),
                        resultSet.getInt("student_id"),
                        resultSet.getInt("course_id"),
                        resultSet.getDate("enrollment_date").toLocalDate()
                ));
            }
        }

        return enrollments;
    }
}
