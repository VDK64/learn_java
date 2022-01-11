package com.epam.sax_dom.data;

public interface ApplicationMessages {

    String FIRST_COMMAND = "First command must be sax or dom!";
    String FILE_NOT_FOUND = "File not found!";
    String WRONG_COMMAND_FORMAT = "Wrong command format. Must be: [type of parser (sax || dom)] [path to xml file] [phrase to find]";

}
