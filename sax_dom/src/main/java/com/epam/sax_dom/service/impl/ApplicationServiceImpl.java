package com.epam.sax_dom.service.impl;

import com.epam.sax_dom.context.ApplicationContext;
import com.epam.sax_dom.parser.Parser;
import com.epam.sax_dom.service.ApplicationService;
import com.epam.sax_dom.validation.ApplicationValidator;

import java.io.File;

import static com.epam.sax_dom.data.ApplicationComponents.*;

public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationContext applicationContext;
    private final ApplicationValidator applicationValidator;
    private final Parser domParser;
    private final Parser contentHandler;

    public ApplicationServiceImpl(ApplicationContext applicationContext,
                                  ApplicationValidator applicationValidator,
                                  Parser domParser, Parser contentHandler) {
        this.applicationContext = applicationContext;
        this.applicationValidator = applicationValidator;
        this.domParser = domParser;
        this.contentHandler = contentHandler;
    }

    @Override
    public void parseRequest(String request) {
        String[] requestArray = request.split(WHITE_SPACE);
        applicationValidator.validateCommands(requestArray);
        setValuesIntoContext(requestArray);
        defineParserAndParse();
    }

    @Override
    public void printResult() {
        printHead();
        applicationContext.getCourses().values().forEach(course -> {
            course.getTasks().forEach(task -> {
                System.out.printf("%-" + (applicationContext.getMaxLength() + 5) + "s", course.getId());
                System.out.printf("%-" + (applicationContext.getMaxLength() + 5) + "s", course.getCourseName());
                System.out.printf("%-" + (applicationContext.getMaxLength() + 5) + "s", task.getName());
                System.out.printf("%-" + (applicationContext.getMaxLength() + 5) + "s\n", task.getDuration());
            });
            printDelimiter();
        });
    }

    @Override
    public String trimRequest(String request) {
        return request.replaceAll("\\s{2,}", " ").trim();
    }

    private void printDelimiter() {
        for (int i = 0; i < applicationContext.getMaxLength() * 4; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    private void printHead() {
        System.out.printf("%-" + (applicationContext.getMaxLength() + 5) + "s", NUMBER);
        System.out.printf("%-" + (applicationContext.getMaxLength() + 5) + "s", CONSOLE_COURSE_NAME);
        System.out.printf("%-" + (applicationContext.getMaxLength() + 5) + "s", CONSOLE_TASK_NAME);
        System.out.printf("%-" + (applicationContext.getMaxLength() + 5) + "s", CONSOLE_DURATION);
        System.out.print("\n");
        printDelimiter();
    }

    private void defineParserAndParse() {
        if (applicationContext.getParseType().equals(SAX)) {
            contentHandler.parse();
        }
        if (applicationContext.getParseType().equals(DOM)) {
            domParser.parse();
        }
    }

    private void setValuesIntoContext(String[] requestArray) {
        StringBuilder requestBuilder = new StringBuilder();
        applicationContext.setParseType(requestArray[0]);
        applicationContext.setFile(new File(requestArray[1]));
        for (int i = 2; i < requestArray.length; i++) {
            requestBuilder.append(requestArray[i]);
            if (i + 1 < requestArray.length) {
                requestBuilder.append(WHITE_SPACE);
            }
        }
        applicationContext.setFindText(requestBuilder.toString());
    }

}
