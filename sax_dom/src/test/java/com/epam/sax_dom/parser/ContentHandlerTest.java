package com.epam.sax_dom.parser;

import com.epam.sax_dom.context.ApplicationContext;
import com.epam.sax_dom.domain.Course;
import com.epam.sax_dom.domain.CourseTask;
import com.epam.sax_dom.domain.Task;
import com.epam.sax_dom.parser.impl.ContentHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.epam.sax_dom.uti.TestingComponents.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ContentHandlerTest {
    private Parser testInstance;
    private Map<String, Course> courses;
    private Map<String, Task> tasks;
    private List<CourseTask> courseTasks;

    @Before
    public void init() {
        ApplicationContext contextMock = mock(ApplicationContext.class);
        UtilParser utilParserMock = mock(UtilParser.class);
        testInstance = new ContentHandler(contextMock, utilParserMock);
        courses = new LinkedHashMap<>();
        tasks = new LinkedHashMap<>();
        courseTasks = new ArrayList<>();
        File xmlFile = new File(PATH_TO_THE_XML_FILE);
        when(contextMock.getFile()).thenReturn(xmlFile);
        when(contextMock.getCourses()).thenReturn(courses);
        when(contextMock.getTasks()).thenReturn(tasks);
        when(contextMock.getCourseTasks()).thenReturn(courseTasks);
        when(contextMock.getFindText()).thenReturn(FIND_TEXT);
        doNothing().when(utilParserMock).clearContext();
        doNothing().when(utilParserMock).bindCoursesAndTasks();
        doNothing().when(utilParserMock).checkOnMaxLengthAndSetIfBigger(anyString());
    }

    @Test
    public void testParseXMLWithSAXToFindRightCourse() {
        testInstance.parse();
        assertEquals(COUNT_COURSES, courses.size());
    }

    @Test
    public void testParseXMLWithSAXToFindAllTasks() {
        testInstance.parse();
        assertEquals(COUNT_TASKS, tasks.size());
    }

    @Test
    public void testParseXMLWithSAXToFindAllCourseTasks() {
        testInstance.parse();
        assertEquals(COUNT_COURSE_TASKS, courseTasks.size());
    }

}