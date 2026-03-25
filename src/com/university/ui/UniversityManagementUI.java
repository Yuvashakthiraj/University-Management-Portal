package com.university.ui;

import com.university.dao.CourseDAO;
import com.university.dao.EnrollmentDAO;
import com.university.dao.FacultyDAO;
import com.university.dao.StudentDAO;
import com.university.model.Course;
import com.university.model.Enrollment;
import com.university.model.Faculty;
import com.university.model.Student;
import com.university.service.DatabaseInspectorService;
import com.university.service.SchemaInitializer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class UniversityManagementUI extends JFrame {
    private final SchemaInitializer schemaInitializer = new SchemaInitializer();
    private final StudentDAO studentDAO = new StudentDAO();
    private final FacultyDAO facultyDAO = new FacultyDAO();
    private final CourseDAO courseDAO = new CourseDAO();
    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private final DatabaseInspectorService inspectorService = new DatabaseInspectorService();

    private final JTextArea studentArea = createOutputArea();
    private final JTextArea facultyArea = createOutputArea();
    private final JTextArea courseArea = createOutputArea();
    private final JTextArea enrollmentArea = createOutputArea();
    private final JTextArea dbProofArea = createOutputArea();

    private final JTextField studentNameField = new JTextField();
    private final JTextField studentEmailField = new JTextField();
    private final JTextField studentDepartmentField = new JTextField();

    private final JTextField facultyNameField = new JTextField();
    private final JTextField facultyEmailField = new JTextField();
    private final JTextField facultyDepartmentField = new JTextField();

    private final JTextField courseTitleField = new JTextField();
    private final JTextField courseCreditsField = new JTextField();
    private final JTextField courseFacultyIdField = new JTextField();

    private final JTextField enrollmentStudentIdField = new JTextField();
    private final JTextField enrollmentCourseIdField = new JTextField();

    public UniversityManagementUI() {
        super("University Management Portal");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(960, 640);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        try {
            schemaInitializer.initialize();
        } catch (SQLException ex) {
            showError("Startup failed: " + ex.getMessage());
        }

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Students", createStudentsPanel());
        tabs.addTab("Faculty", createFacultyPanel());
        tabs.addTab("Courses", createCoursesPanel());
        tabs.addTab("Enrollments", createEnrollmentsPanel());
        tabs.addTab("MySQL Proof", createDatabaseProofPanel());

        add(tabs, BorderLayout.CENTER);

        refreshStudents();
        refreshFaculty();
        refreshCourses();
        refreshEnrollments();
        refreshDatabaseProof();
    }

    private JPanel createStudentsPanel() {
        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel form = createFormPanel();
        form.add(new JLabel("Name"));
        form.add(studentNameField);
        form.add(new JLabel("Email"));
        form.add(studentEmailField);
        form.add(new JLabel("Department"));
        form.add(studentDepartmentField);

        JButton addButton = new JButton("Add Student");
        addButton.addActionListener(e -> addStudent());
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshStudents());

        JPanel actions = createActionPanel(addButton, refreshButton);

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(form);
        top.add(actions);

        root.add(top, BorderLayout.NORTH);
        root.add(new JScrollPane(studentArea), BorderLayout.CENTER);
        return root;
    }

    private JPanel createFacultyPanel() {
        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel form = createFormPanel();
        form.add(new JLabel("Name"));
        form.add(facultyNameField);
        form.add(new JLabel("Email"));
        form.add(facultyEmailField);
        form.add(new JLabel("Department"));
        form.add(facultyDepartmentField);

        JButton addButton = new JButton("Add Faculty");
        addButton.addActionListener(e -> addFaculty());
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshFaculty());

        JPanel actions = createActionPanel(addButton, refreshButton);

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(form);
        top.add(actions);

        root.add(top, BorderLayout.NORTH);
        root.add(new JScrollPane(facultyArea), BorderLayout.CENTER);
        return root;
    }

    private JPanel createCoursesPanel() {
        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel form = createFormPanel();
        form.add(new JLabel("Title"));
        form.add(courseTitleField);
        form.add(new JLabel("Credits"));
        form.add(courseCreditsField);
        form.add(new JLabel("Faculty ID"));
        form.add(courseFacultyIdField);

        JButton addButton = new JButton("Add Course");
        addButton.addActionListener(e -> addCourse());
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshCourses());

        JPanel actions = createActionPanel(addButton, refreshButton);

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(form);
        top.add(actions);

        root.add(top, BorderLayout.NORTH);
        root.add(new JScrollPane(courseArea), BorderLayout.CENTER);
        return root;
    }

    private JPanel createEnrollmentsPanel() {
        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel form = createFormPanel();
        form.add(new JLabel("Student ID"));
        form.add(enrollmentStudentIdField);
        form.add(new JLabel("Course ID"));
        form.add(enrollmentCourseIdField);

        JButton addButton = new JButton("Enroll Student");
        addButton.addActionListener(e -> addEnrollment());
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshEnrollments());

        JPanel actions = createActionPanel(addButton, refreshButton);

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(form);
        top.add(actions);

        root.add(top, BorderLayout.NORTH);
        root.add(new JScrollPane(enrollmentArea), BorderLayout.CENTER);
        return root;
    }

    private JPanel createDatabaseProofPanel() {
        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JButton refreshButton = new JButton("Show DB Info + Tables");
        refreshButton.addActionListener(e -> refreshDatabaseProof());

        JButton structureButton = new JButton("Show Table Structures");
        structureButton.addActionListener(e -> loadTableStructures());

        JPanel actions = createActionPanel(refreshButton, structureButton);

        root.add(actions, BorderLayout.NORTH);
        root.add(new JScrollPane(dbProofArea), BorderLayout.CENTER);
        return root;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Input"));
        return panel;
    }

    private JPanel createActionPanel(JButton primary, JButton secondary) {
        JPanel panel = new JPanel(new GridLayout(1, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        panel.add(primary);
        panel.add(secondary);
        return panel;
    }

    private JTextArea createOutputArea() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    private void addStudent() {
        String name = studentNameField.getText().trim();
        String email = studentEmailField.getText().trim();
        String department = studentDepartmentField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || department.isEmpty()) {
            showError("Please fill all student fields.");
            return;
        }

        try {
            studentDAO.addStudent(new Student(name, email, department));
            clearStudentFields();
            refreshStudents();
        } catch (SQLException ex) {
            showError("Failed to add student: " + ex.getMessage());
        }
    }

    private void addFaculty() {
        String name = facultyNameField.getText().trim();
        String email = facultyEmailField.getText().trim();
        String department = facultyDepartmentField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || department.isEmpty()) {
            showError("Please fill all faculty fields.");
            return;
        }

        try {
            facultyDAO.addFaculty(new Faculty(name, email, department));
            clearFacultyFields();
            refreshFaculty();
        } catch (SQLException ex) {
            showError("Failed to add faculty: " + ex.getMessage());
        }
    }

    private void addCourse() {
        String title = courseTitleField.getText().trim();
        if (title.isEmpty()) {
            showError("Course title is required.");
            return;
        }

        int credits;
        int facultyId;
        try {
            credits = Integer.parseInt(courseCreditsField.getText().trim());
            facultyId = Integer.parseInt(courseFacultyIdField.getText().trim());
        } catch (NumberFormatException ex) {
            showError("Credits and Faculty ID must be numbers.");
            return;
        }

        try {
            courseDAO.addCourse(new Course(title, credits, facultyId));
            clearCourseFields();
            refreshCourses();
        } catch (SQLException ex) {
            showError("Failed to add course: " + ex.getMessage());
        }
    }

    private void addEnrollment() {
        int studentId;
        int courseId;
        try {
            studentId = Integer.parseInt(enrollmentStudentIdField.getText().trim());
            courseId = Integer.parseInt(enrollmentCourseIdField.getText().trim());
        } catch (NumberFormatException ex) {
            showError("Student ID and Course ID must be numbers.");
            return;
        }

        try {
            enrollmentDAO.addEnrollment(new Enrollment(studentId, courseId, LocalDate.now()));
            clearEnrollmentFields();
            refreshEnrollments();
        } catch (SQLException ex) {
            showError("Failed to add enrollment: " + ex.getMessage());
        }
    }

    private void refreshStudents() {
        try {
            List<Student> students = studentDAO.getAllStudents();
            studentArea.setText(formatRows(students));
        } catch (SQLException ex) {
            showError("Failed to load students: " + ex.getMessage());
        }
    }

    private void refreshFaculty() {
        try {
            List<Faculty> faculty = facultyDAO.getAllFaculty();
            facultyArea.setText(formatRows(faculty));
        } catch (SQLException ex) {
            showError("Failed to load faculty: " + ex.getMessage());
        }
    }

    private void refreshCourses() {
        try {
            List<Course> courses = courseDAO.getAllCourses();
            courseArea.setText(formatRows(courses));
        } catch (SQLException ex) {
            showError("Failed to load courses: " + ex.getMessage());
        }
    }

    private void refreshEnrollments() {
        try {
            List<Enrollment> enrollments = enrollmentDAO.getAllEnrollments();
            enrollmentArea.setText(formatRows(enrollments));
        } catch (SQLException ex) {
            showError("Failed to load enrollments: " + ex.getMessage());
        }
    }

    private void refreshDatabaseProof() {
        try {
            dbProofArea.setText(inspectorService.getDatabaseProofReport());
        } catch (SQLException ex) {
            showError("Failed to inspect database: " + ex.getMessage());
        }
    }

    private void loadTableStructures() {
        try {
            dbProofArea.setText(inspectorService.getTableStructureReport());
        } catch (SQLException ex) {
            showError("Failed to load table structures: " + ex.getMessage());
        }
    }

    private String formatRows(List<?> rows) {
        if (rows.isEmpty()) {
            return "No records found.";
        }

        StringBuilder sb = new StringBuilder();
        for (Object row : rows) {
            sb.append(row).append(System.lineSeparator());
        }
        return sb.toString();
    }

    private void clearStudentFields() {
        studentNameField.setText("");
        studentEmailField.setText("");
        studentDepartmentField.setText("");
    }

    private void clearFacultyFields() {
        facultyNameField.setText("");
        facultyEmailField.setText("");
        facultyDepartmentField.setText("");
    }

    private void clearCourseFields() {
        courseTitleField.setText("");
        courseCreditsField.setText("");
        courseFacultyIdField.setText("");
    }

    private void clearEnrollmentFields() {
        enrollmentStudentIdField.setText("");
        enrollmentCourseIdField.setText("");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "University Management Portal", JOptionPane.ERROR_MESSAGE);
    }

    public static void launch() {
        SwingUtilities.invokeLater(() -> {
            UniversityManagementUI ui = new UniversityManagementUI();
            ui.setVisible(true);
        });
    }
}
