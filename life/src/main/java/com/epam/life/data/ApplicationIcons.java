package com.epam.life.data;

/**
 * Stores path of main icon of the application.
 */
public interface ApplicationIcons {
    String MAIN_ICON = PropertyLoader.getProperties().getProperty("main.icon");

}
