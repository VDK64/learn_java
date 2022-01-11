package com.epam.sax_dom.validation;

import java.io.File;

import static com.epam.sax_dom.data.ApplicationComponents.DOM;
import static com.epam.sax_dom.data.ApplicationComponents.SAX;
import static com.epam.sax_dom.data.ApplicationMessages.*;

public class ApplicationValidator {

    public void validateCommands(String[] requestArray) throws ParserException {
        if (requestArray.length < 3) {
            throw new ParserException(WRONG_COMMAND_FORMAT);
        }
        if (!requestArray[0].equals(SAX) && !requestArray[0].equals(DOM)) {
            throw new ParserException(FIRST_COMMAND);
        }
        File file = new File(requestArray[1]);
        if (!file.canRead()) {
            throw new ParserException(FILE_NOT_FOUND);
        }
    }

}
