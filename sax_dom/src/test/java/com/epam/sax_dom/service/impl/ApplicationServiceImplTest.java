package com.epam.sax_dom.service.impl;

import com.epam.sax_dom.context.ApplicationContext;
import com.epam.sax_dom.parser.impl.ContentHandler;
import com.epam.sax_dom.parser.impl.DOMParser;
import com.epam.sax_dom.validation.ApplicationValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;

import static com.epam.sax_dom.data.ApplicationComponents.*;
import static com.epam.sax_dom.uti.TestingComponents.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationServiceImplTest {
    private ApplicationValidator validatorMock;
    private ApplicationContext contextMock;
    private DOMParser domParserMock;
    private ContentHandler contentHandlerMock;
    private ApplicationServiceImpl testInstance;

    @Before
    public void init() {
        validatorMock = mock(ApplicationValidator.class);
        contextMock = mock(ApplicationContext.class);
        domParserMock= mock(DOMParser.class);
        contentHandlerMock = mock(ContentHandler.class);
        testInstance = new ApplicationServiceImpl(contextMock, validatorMock, domParserMock, contentHandlerMock);
    }

    @Test
    public void testParseRequestBySAXParser() {
        String request = SAX + WHITE_SPACE + DEBUG_REQUEST_WITHOUT_FIRST_ARGUMENT;
        String[] splitRequest = request.split(WHITE_SPACE);
        File xmlFile = new File(splitRequest[1]);

        doNothing().when(validatorMock).validateCommands(splitRequest);
        doNothing().when(contextMock).setParseType(splitRequest[0]);
        doNothing().when(contextMock).setFile(xmlFile);
        doNothing().when(contextMock).setFindText(splitRequest[2]);
        doNothing().when(contentHandlerMock).parse();
        when(contextMock.getParseType()).thenReturn(SAX);

        testInstance.parseRequest(request);
        verify(contextMock).setFindText(splitRequest[2]);
    }

    @Test
    public void testParseRequestByDOMParser() {
        String request = DOM + WHITE_SPACE + DEBUG_REQUEST_WITHOUT_FIRST_ARGUMENT2;
        String[] splitRequest = request.split(WHITE_SPACE);
        File xmlFile = new File(splitRequest[1]);

        doNothing().when(validatorMock).validateCommands(splitRequest);
        doNothing().when(contextMock).setParseType(splitRequest[0]);
        doNothing().when(contextMock).setFile(xmlFile);
        doNothing().when(contextMock).setFindText(FIND_TEXT);
        doNothing().when(domParserMock).parse();
        when(contextMock.getParseType()).thenReturn(DOM);
        
        testInstance.parseRequest(request);
        verify(contextMock).setFindText(FIND_TEXT);
    }

    @Test
    public void testTrimRequest() {
        String trimmedRequest = testInstance.trimRequest(DEBUG_REQUEST_WITH_MANY_WHITE_SPACES);
        assertEquals(REQUEST_WITH_MANY_WHITE_SPACES_AFTER_EDIT, trimmedRequest);
    }

}