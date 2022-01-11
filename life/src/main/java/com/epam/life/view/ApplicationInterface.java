package com.epam.life.view;

import com.epam.life.context.ApplicationContext;
import com.epam.life.controller.ApplicationController;
import com.epam.life.data.ApplicationComponents;
import com.epam.life.data.ApplicationIcons;
import com.epam.life.layout.CustomLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

import static com.epam.life.data.ApplicationComponents.*;

/**
 * A class that responsible for GUI of the application. <code>ApplicationInterface</code>
 * extends <code>JFrame</code> class from Swing library and has in itself control buttons and text fields.
 */
public class ApplicationInterface extends JFrame {
    private ApplicationController controller;
    private ApplicationContext applicationContext;
    private JPanel rootPanel;
    private JPanel mainPanelField;
    private JPanel field;
    private JButton printField;
    private JButton startButton;
    private JButton fullScreenButton;
    private JButton stopButton;
    private JButton clearButton;
    private JButton algorithmButton;
    private TextField rows;
    private TextField columns;
    private TextField steps;

    /**
     * Constructs a new frame that is initially invisible.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see Component#setSize
     * @see Component#setVisible
     * @see JComponent#getDefaultLocale
     */
    public ApplicationInterface(String title) throws HeadlessException {
        super(title);
    }

    /**
     * Creates main panel with field of cells and control panel with buttons.
     * Also, sets strategy of window closing, set size of frame and set visible on true.
     */
    public void startApplication() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(this.getClass().getResource(ApplicationIcons.MAIN_ICON)).getImage());
        getContentPane().add(createGUI());
        setPreferredSize(new Dimension(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT));
        pack();
        setLocationRelativeTo(null);
        startButton.grabFocus();
        setVisible(true);
    }

    private JPanel createGUI() {
        rootPanel = createRootPanel();
        if (mainPanelField == null) {
            mainPanelField = createMainPanelField();
        } else {
            mainPanelField.removeAll();
            mainPanelField.setPreferredSize(new Dimension(MAIN_PANEL_FIELD_WIDTH, MAIN_PANEL_FIELD_HEIGHT));
            mainPanelField.add(BorderLayout.CENTER, new JScrollPane(field));
        }
        rootPanel.add(mainPanelField);
        rootPanel.add(createControlPanel());
        return rootPanel;
    }

    private JPanel createRootPanel() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout(FlowLayout.LEFT, ROOT_PANEL_GAP, ROOT_PANEL_GAP));
        return jPanel;
    }

    private JPanel createMainPanelField() {
        JPanel mainPanelField = new JPanel(new BorderLayout(MAIN_PANEL_FIELD_GAP, MAIN_PANEL_FIELD_GAP));
        mainPanelField.setPreferredSize(new Dimension(MAIN_PANEL_FIELD_WIDTH, MAIN_PANEL_FIELD_HEIGHT));
        mainPanelField.setBackground(new Color(BACKGROUND_COLOR));
        if (field == null) {
            field = new JPanel(new CustomLayout(DEFAULT_FIELD_ROWS, DEFAULT_FIELD_COLUMNS, FIELD_GAP, FIELD_GAP));
        }
        mainPanelField.setBorder(BorderFactory.createRaisedBevelBorder());
        field.setBorder(BorderFactory.createEmptyBorder(FIELD_GAP, FIELD_GAP, FIELD_GAP, FIELD_GAP));
        field.setBackground(new Color(BACKGROUND_COLOR));
        JScrollPane jScrollPane = new JScrollPane(field);
        mainPanelField.add(BorderLayout.CENTER, jScrollPane);
        return mainPanelField;
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new GridLayout(CONTROL_PANEL_ROWS, CONTROL_PANEL_COLUMNS,
                CONTROL_PANEL_GAP, CONTROL_PANEL_GAP));
        controlPanel.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH, CONTROL_PANEL_HEIGHT));
        controlPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), ApplicationComponents.CONTROL_PANEL_NAME,
                TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, null, null));
        createControls(controlPanel);
        return controlPanel;
    }

    private void createControls(JPanel controlPanel) {
        createControlButtons();
        createControlTextFields();
        setButtonsView();
        controlPanel.add(rows);
        controlPanel.add(columns);
        controlPanel.add(steps);
        controlPanel.add(printField);
        controlPanel.add(startButton);
        controlPanel.add(stopButton);
        controlPanel.add(clearButton);
        controlPanel.add(fullScreenButton);
        controlPanel.add(algorithmButton);
        controller.addListeners();
    }

    private void setButtonsView() {
        if (applicationContext.isRunningReproduction()) {
            buttonsWhenReproductionStart();
        }
        if (applicationContext.getGameField() == null) {
            setEnabledOfControlButtons(false);
        } else {
            if (!applicationContext.isRunningReproduction()) {
                buttonsWheReproductionStop();
            }
        }
    }

    /**
     * Sets control buttons to enabled or not.
     * @param enable could be true or false in depended of situation.
     */
    public void setEnabledOfControlButtons(boolean enable) {
        startButton.setEnabled(enable);
        stopButton.setEnabled(enable);
        clearButton.setEnabled(enable);
        fullScreenButton.setEnabled(enable);
        algorithmButton.setEnabled(enable);
    }

    private void createControlButtons() {
        startButton = new JButton(START);
        printField = new JButton(PRINT);
        fullScreenButton = new JButton(FULL_SCREEN);
        stopButton = new JButton(STOP);
        clearButton = new JButton(CLEAR);
        if (applicationContext.isThreadAlgorithm()) {
            algorithmButton = new JButton(THREAD_ALGORITHM);
        } else {
            algorithmButton = new JButton(FUTURE_ALGORITHM);
        }
    }

    /**
     * Sets buttons disabled, because when reproduction starts, user should not change anything.
     */
    public void buttonsWhenReproductionStart() {
        startButton.setEnabled(false);
        clearButton.setEnabled(false);
        algorithmButton.setEnabled(false);
        startButton.setText(RUNNING);
    }

    /**
     * Sets buttons enabled, because when reproduction stops, user could change anything.
     */
    public void buttonsWheReproductionStop() {
        startButton.setText(START);
        startButton.setEnabled(true);
        clearButton.setEnabled(true);
        algorithmButton.setEnabled(true);
    }

    private void createControlTextFields() {
        if (applicationContext.bothFieldsNotNull()) {
            rows.setText(String.valueOf(applicationContext.getRows()));
            columns.setText(String.valueOf(applicationContext.getColumns()));
            steps.setText(String.valueOf(applicationContext.getSteps()));
        } else {
            rows = new TextField(ApplicationComponents.ROWS);
            columns = new TextField(ApplicationComponents.COLUMNS);
            steps = new TextField(ApplicationComponents.STEPS);
            startButton.setEnabled(false);
        }
        rows.setName(ApplicationComponents.ROWS);
        columns.setName(ApplicationComponents.COLUMNS);
        steps.setName(ApplicationComponents.STEPS);
    }

    /**
     * This method serves for clear field of cells and repaint it.
     */
    public void clearField() {
        field.removeAll();
        field.revalidate();
        field.repaint();
    }

    /**
     * Sets default text into text filed just like when applications starts.
     */
    public void setDefaultTextInFields() {
        getRows().setText(ROWS);
        getColumns().setText(COLUMNS);
        getSteps().setText(STEPS);
    }

    /**
     * Changes infected status of the cell. If the cell was infected earlier,
     * then it will become uninfected and vice versa.
     * @param jButton is a button that represents cell in GUI.
     */
    public void turnInfectedStatus(JButton jButton) {
        if (jButton.getBackground().equals(Color.WHITE)) {
            jButton.setBackground(Color.RED);
        } else {
            jButton.setBackground(Color.WHITE);
        }
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public TextField getRows() {
        return rows;
    }

    public TextField getColumns() {
        return columns;
    }

    public JButton getPrintFieldButton() {
        return printField;
    }

    public JButton getFullScreenButton() {
        return fullScreenButton;
    }

    public JButton getAlgorithmButton() {
        return algorithmButton;
    }

    public JButton getStopButton() {
        return stopButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public TextField getSteps() {
        return steps;
    }

    public void setController(ApplicationController controller) {
        this.controller = controller;
    }

    public JPanel getField() {
        return field;
    }

}
