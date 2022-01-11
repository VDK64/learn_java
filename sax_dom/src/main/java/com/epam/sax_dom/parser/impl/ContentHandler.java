package com.epam.sax_dom.parser.impl;

import com.epam.sax_dom.context.ApplicationContext;
import com.epam.sax_dom.domain.Course;
import com.epam.sax_dom.domain.CourseTask;
import com.epam.sax_dom.domain.Task;
import com.epam.sax_dom.parser.Parser;
import com.epam.sax_dom.parser.UtilParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

import static com.epam.sax_dom.data.ApplicationComponents.*;

public class ContentHandler extends DefaultHandler implements Parser {
    private final SAXParser saxParser;
    private final ApplicationContext applicationContext;
    private final UtilParser utilParser;
    private Course currentCourse;
    private Task currentTask;
    private CourseTask currentCourseTask;
    private boolean course;
    private boolean courseID;
    private boolean courseName;
    private boolean task;
    private boolean taskID;
    private boolean taskName;
    private boolean duration;
    private boolean courseTask;

    public ContentHandler(ApplicationContext applicationContext, UtilParser utilParser) {
        super();
        this.saxParser = initSAXParser();
        this.applicationContext = applicationContext;
        this.utilParser = utilParser;
    }

    private SAXParser initSAXParser() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            return factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void parse() {
        try {
            saxParser.parse(applicationContext.getFile(), this);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startDocument() {
        currentCourse = new Course();
        currentTask = new Task();
        currentCourseTask = new CourseTask();
        utilParser.clearContext();
        resetAllElementFields();
    }

    @Override
    public void endDocument() {
        utilParser.bindCoursesAndTasks();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        defineTag(qName, true);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        defineTag(qName, false);
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (course && courseID) {
            currentCourse.setId(Long.parseLong(createStringFromElementCharacters(ch, start, length)));
        }
        if (course && courseName) {
            currentCourse.setCourseName(createStringFromElementCharacters(ch, start, length));
        }
        if (task && taskID) {
            currentTask.setId(Long.parseLong(createStringFromElementCharacters(ch, start, length)));
        }
        if (task && taskName) {
            currentTask.setName(createStringFromElementCharacters(ch, start, length));
        }
        if (task && duration) {
            currentTask.setDuration(Integer.parseInt(createStringFromElementCharacters(ch, start, length)));
        }
        if (courseTask && courseID) {
            currentCourseTask.setCourseId(createStringFromElementCharacters(ch, start, length));
        }
        if (courseTask && taskID) {
            currentCourseTask.setTaskId(createStringFromElementCharacters(ch, start, length));
        }
    }

    private String createStringFromElementCharacters(char[] ch, int start, int length) {
        StringBuilder tagInfo = new StringBuilder();
        for (int i = start; i < start + length; i++) {
            tagInfo.append(ch[i]);
        }
        String tagInfoString = tagInfo.toString();
        utilParser.checkOnMaxLengthAndSetIfBigger(tagInfoString);
        return tagInfoString;
    }

    private void defineTag(String qName, boolean started) {
        switch (qName) {
            case COURSE:
                course = started;
                commitCurrentCourse(started);
                break;
            case TASK:
                task = started;
                commitCurrentTask(started);
                break;
            case COURSE_TASK:
                courseTask = started;
                task = started;
                commitCurrentCourseTask(started);
                break;
            case ID:
                if (course) {
                    courseID = started;
                }
                if (task) {
                    taskID = started;
                }
                break;
            case COURSE_NAME:
                courseName = started;
                break;
            case TASK_NAME:
                taskName = started;
                break;
            case DURATION:
                duration = started;
                break;
            case COURSE_ID:
                courseID = started;
                break;
            case TASK_ID:
                taskID = started;
                break;
        }
    }

    private void commitCurrentTask(boolean started) {
        if (!started) {
            applicationContext.getTasks().put(String.valueOf(currentTask.getId()), currentTask);
        }
        currentTask = new Task();
    }

    private void commitCurrentCourse(boolean started) {
        if (!started && currentCourse.getCourseName().contains(applicationContext.getFindText())) {
            applicationContext.getCourses().put(String.valueOf(currentCourse.getId()), currentCourse);
        }
        currentCourse = new Course();
    }

    private void commitCurrentCourseTask(boolean started) {
        if (!started) {
            applicationContext.getCourseTasks().add(currentCourseTask);
        }
        currentCourseTask = new CourseTask();
    }

    private void resetAllElementFields() {
        course = false;
        courseID = false;
        courseName = false;
        task = false;
        taskID = false;
        taskName = false;
        duration = false;
        courseTask = false;
    }

}
