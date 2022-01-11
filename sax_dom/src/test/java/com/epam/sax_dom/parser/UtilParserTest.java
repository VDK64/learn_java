package com.epam.sax_dom.parser;

import com.epam.sax_dom.context.ApplicationContext;
import com.epam.sax_dom.domain.Course;
import com.epam.sax_dom.domain.CourseTask;
import com.epam.sax_dom.domain.Task;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class UtilParserTest {
    private ApplicationContext contextMock;
    private UtilParser testInstance;
    private Map<String, Course> courses;
    private Map<String, Task> tasks;
    private List<CourseTask> courseTasks;

    @Before
    public void init() {
        contextMock = Mockito.mock(ApplicationContext.class);
        testInstance = new UtilParser(contextMock);
        courses = new LinkedHashMap<>();
        tasks = new LinkedHashMap<>();
        courseTasks = new ArrayList<>();
        fillResultCollections(courses, tasks, courseTasks);
        when(contextMock.getCourses()).thenReturn(courses);
        when(contextMock.getTasks()).thenReturn(tasks);
        when(contextMock.getCourseTasks()).thenReturn(courseTasks);
    }

    @Test
    public void testCheckOnMaxLengthAndSetNewMaxLength() {
        String testString = "123456";
        when(contextMock.getMaxLength()).thenReturn(1);
        doNothing().when(contextMock).setMaxLength(testString.length());

        testInstance.checkOnMaxLengthAndSetIfBigger(testString);
        verify(contextMock).setMaxLength(testString.length());
    }

    @Test
    public void testCheckOnMaxLengthAndDoNotSetNewMaxLength() {
        String testString = "123456";
        when(contextMock.getMaxLength()).thenReturn(50);
        doNothing().when(contextMock).setMaxLength(testString.length());

        testInstance.checkOnMaxLengthAndSetIfBigger(testString);
        verify(contextMock, times(0)).setMaxLength(testString.length());
    }

    @Test
    public void testBindCoursesAndTasks() {
        testInstance.bindCoursesAndTasks();
        courses.values().forEach(course -> {
            if (course.getId() == 1) {
                assertEquals(1, course.getTasks().size());
                assertTrue(course.getTasks().stream().anyMatch(task -> task.getId() == 3));
            } else {
                if (course.getId() == 2) {
                    assertEquals(3, course.getTasks().size());
                    assertTrue(course.getTasks().stream().anyMatch(task -> task.getId() == 1));
                    assertTrue(course.getTasks().stream().anyMatch(task -> task.getId() == 2));
                    assertTrue(course.getTasks().stream().anyMatch(task -> task.getId() == 4));
                } else {
                    assertEquals(0, course.getTasks().size());
                }
            }
        });
    }

    @Test
    public void testClearContext() {
        doNothing().when(contextMock).setMaxLength(0);
        testInstance.clearContext();
        assertEquals(0, courses.size());
        assertEquals(0, tasks.size());
        assertEquals(0, courseTasks.size());
    }

    private void fillResultCollections(Map<String, Course> courses, Map<String, Task> tasks, List<CourseTask> courseTasks) {
        courses.put("1", new Course(1, "First course"));
        courses.put("2", new Course(2, "Second course"));
        courses.put("3", new Course(3, "Third course"));
        courses.put("4", new Course(4, "Fourth course"));
        tasks.put("1", new Task(1, "First task", 10));
        tasks.put("2", new Task(2, "Second task", 20));
        tasks.put("3", new Task(3, "Third task", 30));
        tasks.put("4", new Task(4, "Fourth task", 40));
        courseTasks.add(new CourseTask("1", "3"));
        courseTasks.add(new CourseTask("2", "1"));
        courseTasks.add(new CourseTask("2", "2"));
        courseTasks.add(new CourseTask("2", "4"));
    }

}