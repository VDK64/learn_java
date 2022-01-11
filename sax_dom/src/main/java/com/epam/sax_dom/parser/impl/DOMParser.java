package com.epam.sax_dom.parser.impl;

import com.epam.sax_dom.context.ApplicationContext;
import com.epam.sax_dom.domain.Course;
import com.epam.sax_dom.domain.CourseTask;
import com.epam.sax_dom.domain.Task;
import com.epam.sax_dom.parser.Parser;
import com.epam.sax_dom.parser.UtilParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static com.epam.sax_dom.data.ApplicationComponents.*;

public class DOMParser implements Parser {
    private final ApplicationContext applicationContext;
    private final UtilParser utilParser;
    private final DocumentBuilder documentBuilder;

    public DOMParser(ApplicationContext applicationContext, UtilParser utilParser) {
        this.applicationContext = applicationContext;
        this.utilParser = utilParser;
        this.documentBuilder = initializeDocumentBuilder();
    }

    @Override
    public void parse() {
        try {
            utilParser.clearContext();
            Document document = documentBuilder.parse(applicationContext.getFile());
            document.getDocumentElement().normalize();
            parseNodes(document, TASK);
            parseNodes(document, COURSE);
            parseNodes(document, COURSE_TASK);
            utilParser.bindCoursesAndTasks();
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private void parseNodes(Document document, String tagName) {
        NodeList nList = document.getElementsByTagName(tagName);
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                switch (tagName) {
                    case TASK:
                        collectTasks((Element) node);
                        break;
                    case COURSE:
                        collectCourses((Element) node);
                        break;
                    case COURSE_TASK:
                        collectCourseTasks((Element) node);
                        break;
                }
            }
        }
    }

    private void collectTasks(Element node) {
        String id = node.getElementsByTagName(ID).item(FIRST_NODE).getTextContent();
        String taskName = node.getElementsByTagName(TASK_NAME).item(FIRST_NODE).getTextContent();
        String duration = node.getElementsByTagName(DURATION).item(FIRST_NODE).getTextContent();
        Task task = new Task();
        task.setId(Long.parseLong(id));
        task.setName(taskName);
        task.setDuration(Integer.parseInt(duration));
        utilParser.checkOnMaxLengthAndSetIfBigger(taskName);
        applicationContext.getTasks().put(id, task);
    }

    private void collectCourseTasks(Element node) {
        String courseID = node.getElementsByTagName(COURSE_ID).item(FIRST_NODE).getTextContent();
        String taskID = node.getElementsByTagName(TASK_ID).item(FIRST_NODE).getTextContent();
        CourseTask courseTask = new CourseTask();
        courseTask.setCourseId(courseID);
        courseTask.setTaskId(taskID);
        applicationContext.getCourseTasks().add(courseTask);
    }

    private void collectCourses(Element node) {
        String id = node.getElementsByTagName(ID).item(FIRST_NODE).getTextContent();
        String courseName = node.getElementsByTagName(COURSE_NAME).item(FIRST_NODE).getTextContent();
        if (courseName.contains(applicationContext.getFindText())) {
            Course course = new Course();
            course.setId(Long.parseLong(id));
            course.setCourseName(courseName);
            utilParser.checkOnMaxLengthAndSetIfBigger(courseName);
            applicationContext.getCourses().put(id, course);
        }
    }

    private DocumentBuilder initializeDocumentBuilder() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

}
