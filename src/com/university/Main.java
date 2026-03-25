package com.university;

import com.university.dao.CourseDAO;
import com.university.dao.EnrollmentDAO;
import com.university.dao.FacultyDAO;
import com.university.dao.StudentDAO;
import com.university.model.Course;
import com.university.model.Enrollment;
import com.university.model.Faculty;
import com.university.model.Student;
import com.university.service.SchemaInitializer;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);

    private final SchemaInitializer schemaInitializer = new SchemaInitializer();
    private final StudentDAO studentDAO = new StudentDAO();
    private final FacultyDAO facultyDAO = new FacultyDAO();
    private final CourseDAO courseDAO = new CourseDAO();
    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        System.out.println("University Management Portal (JDBC + MySQL)");

        try {
            schemaInitializer.initialize();
            System.out.println("Database and tables are ready.");
        } catch (SQLException ex) {
            System.out.println("Startup failed: " + ex.getMessage());
            return;
        }

        while (true) {
            printMenu();
            int choice = readInt("Choose option: ");

            try {
                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> listStudents();
                    case 3 -> addFaculty();
                    case 4 -> listFaculty();
                    case 5 -> addCourse();
                    case 6 -> listCourses();
                    case 7 -> addEnrollment();
                    case 8 -> listEnrollments();
                    case 0 -> {
                        System.out.println("Exiting.");
                        return;
                    }
                    default -> System.out.println("Invalid option.");
                }
            } catch (SQLException ex) {
                System.out.println("Database error: " + ex.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("1. Add Student");
        System.out.println("2. List Students");
        System.out.println("3. Add Faculty");
        System.out.println("4. List Faculty");
        System.out.println("5. Add Course");
        System.out.println("6. List Courses");
        System.out.println("7. Enroll Student");
        System.out.println("8. List Enrollments");
        System.out.println("0. Exit");
    }

    private void addStudent() throws SQLException {
        System.out.println("-- Add Student --");
        String name = readText("Name: ");
        String email = readText("Email: ");
        String department = readText("Department: ");
        studentDAO.addStudent(new Student(name, email, department));
        System.out.println("Student added.");
    }

    private void listStudents() throws SQLException {
        System.out.println("-- Students --");
        List<Student> students = studentDAO.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student student : students) {
            System.out.println(student);
        }
    }

    private void addFaculty() throws SQLException {
        System.out.println("-- Add Faculty --");
        String name = readText("Name: ");
        String email = readText("Email: ");
        String department = readText("Department: ");
        facultyDAO.addFaculty(new Faculty(name, email, department));
        System.out.println("Faculty added.");
    }

    private void listFaculty() throws SQLException {
        System.out.println("-- Faculty --");
        List<Faculty> faculty = facultyDAO.getAllFaculty();
        if (faculty.isEmpty()) {
            System.out.println("No faculty found.");
            return;
        }

        for (Faculty entry : faculty) {
            System.out.println(entry);
        }
    }

    private void addCourse() throws SQLException {
        System.out.println("-- Add Course --");
        String title = readText("Title: ");
        int credits = readInt("Credits: ");
        int facultyId = readInt("Faculty ID: ");
        courseDAO.addCourse(new Course(title, credits, facultyId));
        System.out.println("Course added.");
    }

    private void listCourses() throws SQLException {
        System.out.println("-- Courses --");
        List<Course> courses = courseDAO.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }

        for (Course course : courses) {
            System.out.println(course);
        }
    }

    private void addEnrollment() throws SQLException {
        System.out.println("-- Enroll Student --");
        int studentId = readInt("Student ID: ");
        int courseId = readInt("Course ID: ");
        enrollmentDAO.addEnrollment(new Enrollment(studentId, courseId, LocalDate.now()));
        System.out.println("Enrollment added.");
    }

    private void listEnrollments() throws SQLException {
        System.out.println("-- Enrollments --");
        List<Enrollment> enrollments = enrollmentDAO.getAllEnrollments();
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found.");
            return;
        }

        for (Enrollment enrollment : enrollments) {
            System.out.println(enrollment);
        }
    }

    private int readInt(String label) {
        while (true) {
            System.out.print(label);
            String value = SCANNER.nextLine().trim();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private String readText(String label) {
        while (true) {
            System.out.print(label);
            String value = SCANNER.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Value cannot be empty.");
        }
    }
}
