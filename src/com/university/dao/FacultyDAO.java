package com.university.dao;

import com.university.db.DBConnection;
import com.university.model.Faculty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacultyDAO {
    public void addFaculty(Faculty faculty) throws SQLException {
        String sql = "INSERT INTO faculty(name, email, department) VALUES(?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, faculty.getName());
            statement.setString(2, faculty.getEmail());
            statement.setString(3, faculty.getDepartment());
            statement.executeUpdate();
        }
    }

    public List<Faculty> getAllFaculty() throws SQLException {
        String sql = "SELECT id, name, email, department FROM faculty ORDER BY id";
        List<Faculty> faculty = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                faculty.add(new Faculty(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("department")
                ));
            }
        }

        return faculty;
    }
}
