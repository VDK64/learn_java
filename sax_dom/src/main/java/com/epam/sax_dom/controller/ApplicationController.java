package com.epam.sax_dom.controller;

import com.epam.sax_dom.service.ApplicationService;
import com.epam.sax_dom.validation.ParserException;

import java.util.Scanner;

import static com.epam.sax_dom.data.ApplicationComponents.STOP;

public class ApplicationController {
    private final ApplicationService service;
    private final Scanner scanner;

    public ApplicationController(ApplicationService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void listenConsole() {
        while (true) {
            try {
                String request = scanner.nextLine();
                String trimmedRequest = service.trimRequest(request);
                if (trimmedRequest.equalsIgnoreCase(STOP)) {
                    break;
                }
                service.parseRequest(trimmedRequest);
                service.printResult();
            } catch (ParserException exception) {
                System.err.println(exception.getMessage());
            }
        }

    }

}
