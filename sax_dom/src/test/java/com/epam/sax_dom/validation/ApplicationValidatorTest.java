package com.epam.sax_dom.validation;

import org.junit.Before;
import org.junit.Test;

import static com.epam.sax_dom.data.ApplicationComponents.SAX;
import static com.epam.sax_dom.data.ApplicationComponents.WHITE_SPACE;
import static com.epam.sax_dom.uti.TestingComponents.*;

public class ApplicationValidatorTest {
    private ApplicationValidator testInstance;

    @Before
    public void init() {
        testInstance = new ApplicationValidator();
    }

    @Test
    public void testValidateRightRequest() {
        String request = SAX + WHITE_SPACE + DEBUG_REQUEST_WITHOUT_FIRST_ARGUMENT;
        testInstance.validateCommands(request.split(WHITE_SPACE));
    }

    @Test(expected = ParserException.class)
    public void testValidateRequestWithoutParserTypeCommand() {
        testInstance.validateCommands(DEBUG_REQUEST_WITHOUT_FIRST_ARGUMENT.split(WHITE_SPACE));
    }

    @Test(expected = ParserException.class)
    public void testValidateRequestWithWrongFilePath() {
        String request = SAX + WHITE_SPACE + DEBUG_REQUEST_WITH_WRONG_FILE_PATH;
        testInstance.validateCommands(request.split(WHITE_SPACE));
    }

    @Test(expected = ParserException.class)
    public void testValidateRequestWithoutFilePath() {
        testInstance.validateCommands(DEBUG_REQUEST_WITHOUT_FILE_PATH.split(WHITE_SPACE));
    }

}