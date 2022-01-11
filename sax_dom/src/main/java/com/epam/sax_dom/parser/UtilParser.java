package com.epam.sax_dom.parser;

import com.epam.sax_dom.context.ApplicationContext;
import com.epam.sax_dom.domain.Course;
import com.epam.sax_dom.domain.Task;

public class UtilParser {
    private final ApplicationContext applicationContext;

    public UtilParser(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void checkOnMaxLengthAndSetIfBigger(String tagInfo) {
        int length = tagInfo.length();
        if (applicationContext.getMaxLength() < length) {
            applicationContext.setMaxLength(length);
        }
    }

    public void bindCoursesAndTasks() {
        applicationContext.getCourseTasks().forEach(courseTask -> {
            Course course = applicationContext.getCourses().get(courseTask.getCourseId());
            Task task = applicationContext.getTasks().get(courseTask.getTaskId());
            if (course != null && task != null) {
                course.getTasks().add(task);
            }
        });
    }

    public void clearContext() {
        applicationContext.getTasks().clear();
        applicationContext.getCourses().clear();
        applicationContext.getCourseTasks().clear();
        applicationContext.setMaxLength(0);
    }

}
