package com.epam.life.data;

/**
 * Constants that provides warning messages to the user in dialog pane.
 */
public interface ApplicationMessages {
    String WARNING_NOT_INTEGER = PropertyLoader.getProperties().getProperty("warning.not.integer");
    String WARNING_TITLE = PropertyLoader.getProperties().getProperty("warning");
    String MANY_CELLS = PropertyLoader.getProperties().getProperty("many.cells");

}
