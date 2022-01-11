package com.epam.sax_dom.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Course {
    private long id;
    private String courseName;
    private List<Task> tasks;

    public Course() {
        this.tasks = new LinkedList<>();
    }

    public Course(long id, String courseName) {
        this.id = id;
        this.courseName = courseName;
        this.tasks = new LinkedList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return getId() == course.getId() &&
                Objects.equals(getCourseName(), course.getCourseName()) &&
                Objects.equals(getTasks(), course.getTasks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCourseName(), getTasks());
    }

}
