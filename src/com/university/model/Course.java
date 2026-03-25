package com.university.model;

public class Course {
    private int id;
    private String title;
    private int credits;
    private int facultyId;

    public Course() {
    }

    public Course(String title, int credits, int facultyId) {
        this.title = title;
        this.credits = credits;
        this.facultyId = facultyId;
    }

    public Course(int id, String title, int credits, int facultyId) {
        this.id = id;
        this.title = title;
        this.credits = credits;
        this.facultyId = facultyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    @Override
    public String toString() {
        return "Course{id=" + id + ", title='" + title + "', credits=" + credits + ", facultyId=" + facultyId + "}";
    }
}
