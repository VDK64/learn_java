package com.epam.sax_dom;

import com.epam.sax_dom.context.ApplicationContext;
import com.epam.sax_dom.controller.ApplicationController;
import com.epam.sax_dom.parser.Parser;
import com.epam.sax_dom.parser.UtilParser;
import com.epam.sax_dom.parser.impl.ContentHandler;
import com.epam.sax_dom.parser.impl.DOMParser;
import com.epam.sax_dom.service.ApplicationService;
import com.epam.sax_dom.service.impl.ApplicationServiceImpl;
import com.epam.sax_dom.validation.ApplicationValidator;

public class ParserApplication {

    public static void main(String[] args) {
        new ParserApplication()
                .init()
                .listenConsole();
    }

    public ApplicationController init() {
        ApplicationContext applicationContext = new ApplicationContext();
        UtilParser utilParser = new UtilParser(applicationContext);
        Parser contentHandler = new ContentHandler(applicationContext, utilParser);
        Parser domParser = new DOMParser(applicationContext, utilParser);
        ApplicationValidator applicationValidator = new ApplicationValidator();
        ApplicationService applicationService
                = new ApplicationServiceImpl(applicationContext, applicationValidator, domParser, contentHandler);
        return new ApplicationController(applicationService);
    }

}
