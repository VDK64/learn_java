package com.epam.sax_dom.service;

public interface ApplicationService {

    void parseRequest(String request);

    void printResult();

    String trimRequest(String request);
}
