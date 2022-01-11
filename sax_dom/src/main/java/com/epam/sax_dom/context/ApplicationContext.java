package com.epam.sax_dom.context;

import com.epam.sax_dom.domain.Course;
import com.epam.sax_dom.domain.CourseTask;
import com.epam.sax_dom.domain.Task;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ApplicationContext {
    private final Map<String, Course> courses;
    private final Map<String, Task> tasks;
    private final List<CourseTask> courseTasks;
    private int maxLength;
    private File file;
    private String parseType;
    private String findText;

    public ApplicationContext() {
        this.courses = new LinkedHashMap<>();
        this.tasks = new LinkedHashMap<>();
        this.courseTasks = new ArrayList<>();
    }

    public Map<String, Course> getCourses() {
        return courses;
    }

    public Map<String, Task> getTasks() {
        return tasks;
    }

    public File getFile() {
        return file;
    }

    public List<CourseTask> getCourseTasks() {
        return courseTasks;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getParseType() {
        return parseType;
    }

    public void setParseType(String parseType) {
        this.parseType = parseType;
    }

    public String getFindText() {
        return findText;
    }

    public void setFindText(String findText) {
        this.findText = findText;
    }

}
