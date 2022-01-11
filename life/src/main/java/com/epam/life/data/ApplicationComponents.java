package com.epam.life.data;

/**
 * This is a constants used to set names to control components. Also, <code>ApplicationComponents</code>
 * stores logical values.
 */
public interface ApplicationComponents {
    String APPLICATION_NAME = PropertyLoader.getProperties().getProperty("application.name");
    String CONTROL_PANEL_NAME = PropertyLoader.getProperties().getProperty("control.panel.name");
    String ROWS = PropertyLoader.getProperties().getProperty("rows");
    String COLUMNS = PropertyLoader.getProperties().getProperty("columns");
    String STEPS = PropertyLoader.getProperties().getProperty("steps");
    String START = PropertyLoader.getProperties().getProperty("start");
    String RUNNING = PropertyLoader.getProperties().getProperty("running");
    String STOPPING = PropertyLoader.getProperties().getProperty("stopping");
    String STOP = PropertyLoader.getProperties().getProperty("stop");
    String CLEAR = PropertyLoader.getProperties().getProperty("clear");
    String PRINT = PropertyLoader.getProperties().getProperty("print");
    String FULL_SCREEN = PropertyLoader.getProperties().getProperty("full.screen");
    String EMPTY_STRING = "";
    String THREAD = PropertyLoader.getProperties().getProperty("thread");
    String FUTURE = PropertyLoader.getProperties().getProperty("future");
    int MINIMUM_NEIGHBOURS_TO_DEATH = Integer.parseInt(PropertyLoader.getProperties().getProperty("minimum.neighbours.to.death"));
    int MAXIMUM_NEIGHBOURS_TO_DEATH = Integer.parseInt(PropertyLoader.getProperties().getProperty("maximum.neighbours.to.death"));
    int EMPTINESS_TO_BIRTH = Integer.parseInt(PropertyLoader.getProperties().getProperty("emptiness.to.birth"));
    int COUNT_THREADS = Integer.parseInt(PropertyLoader.getProperties().getProperty("count.threads"));
    int MAIN_FRAME_WIDTH = Integer.parseInt(PropertyLoader.getProperties().getProperty("main.frame.width"));
    int MAIN_FRAME_HEIGHT = Integer.parseInt(PropertyLoader.getProperties().getProperty("main.frame.height"));
    int MAIN_PANEL_FIELD_WIDTH = Integer.parseInt(PropertyLoader.getProperties().getProperty("main.panel.field.width"));
    int MAIN_PANEL_FIELD_HEIGHT = Integer.parseInt(PropertyLoader.getProperties().getProperty("main.panel.field.height"));
    int ROOT_PANEL_GAP = Integer.parseInt(PropertyLoader.getProperties().getProperty("root.panel.gap"));
    int MAIN_PANEL_FIELD_GAP = Integer.parseInt(PropertyLoader.getProperties().getProperty("main.panel.field.gap"));
    int BACKGROUND_COLOR = Integer.parseInt(PropertyLoader.getProperties().getProperty("background.color"));
    int DEFAULT_FIELD_ROWS = Integer.parseInt(PropertyLoader.getProperties().getProperty("default.field.rows"));
    int DEFAULT_FIELD_COLUMNS = Integer.parseInt(PropertyLoader.getProperties().getProperty("default.field.columns"));
    int FIELD_GAP = Integer.parseInt(PropertyLoader.getProperties().getProperty("field.gap"));
    int CONTROL_PANEL_GAP = Integer.parseInt(PropertyLoader.getProperties().getProperty("control.panel.gap"));
    int CONTROL_PANEL_ROWS = Integer.parseInt(PropertyLoader.getProperties().getProperty("control.panel.rows"));
    int CONTROL_PANEL_COLUMNS = Integer.parseInt(PropertyLoader.getProperties().getProperty("control.panel.columns"));
    int CONTROL_PANEL_WIDTH = Integer.parseInt(PropertyLoader.getProperties().getProperty("control.panel.width"));
    int CONTROL_PANEL_HEIGHT = Integer.parseInt(PropertyLoader.getProperties().getProperty("control.panel.height"));
    String THREAD_ALGORITHM = PropertyLoader.getProperties().getProperty("thread.algorithm");
    String FUTURE_ALGORITHM = PropertyLoader.getProperties().getProperty("feature.algorithm");
    String BUTTON_NAME = PropertyLoader.getProperties().getProperty("button.name");
    int BUTTON_PREFERRED_SIZE = Integer.parseInt(PropertyLoader.getProperties().getProperty("button.preferred.size"));

}
