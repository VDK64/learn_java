package com.epam.sax_dom.domain;

import java.util.Objects;

public class CourseTask {
    private String courseId;
    private String taskId;

    public CourseTask() {
    }

    public CourseTask(String courseId, String taskId) {
        this.courseId = courseId;
        this.taskId = taskId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseTask)) return false;
        CourseTask that = (CourseTask) o;
        return Objects.equals(getCourseId(), that.getCourseId()) &&
                Objects.equals(getTaskId(), that.getTaskId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourseId(), getTaskId());
    }
}
