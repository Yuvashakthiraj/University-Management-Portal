package com.university.service;

import com.university.db.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaInitializer {
    public void initialize() throws SQLException {
        createDatabaseIfNeeded();
        createTablesIfNeeded();
    }

    private void createDatabaseIfNeeded() throws SQLException {
        String sql = "CREATE DATABASE IF NOT EXISTS " + DBConnection.getDbName();

        try (Connection connection = DBConnection.getServerConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    private void createTablesIfNeeded() throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute("""
                    CREATE TABLE IF NOT EXISTS students (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        email VARCHAR(150) NOT NULL UNIQUE,
                        department VARCHAR(100) NOT NULL
                    )
                    """);

            statement.execute("""
                    CREATE TABLE IF NOT EXISTS faculty (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        email VARCHAR(150) NOT NULL UNIQUE,
                        department VARCHAR(100) NOT NULL
                    )
                    """);

            statement.execute("""
                    CREATE TABLE IF NOT EXISTS courses (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        title VARCHAR(150) NOT NULL,
                        credits INT NOT NULL,
                        faculty_id INT NOT NULL,
                        CONSTRAINT fk_courses_faculty
                        FOREIGN KEY (faculty_id) REFERENCES faculty(id)
                        ON DELETE RESTRICT
                    )
                    """);

            statement.execute("""
                    CREATE TABLE IF NOT EXISTS enrollments (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        student_id INT NOT NULL,
                        course_id INT NOT NULL,
                        enrollment_date DATE NOT NULL,
                        UNIQUE KEY uq_enrollment_student_course (student_id, course_id),
                        CONSTRAINT fk_enrollments_student
                        FOREIGN KEY (student_id) REFERENCES students(id)
                        ON DELETE CASCADE,
                        CONSTRAINT fk_enrollments_course
                        FOREIGN KEY (course_id) REFERENCES courses(id)
                        ON DELETE CASCADE
                    )
                    """);
        }
    }
}
