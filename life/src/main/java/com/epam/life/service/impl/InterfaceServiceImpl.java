package com.epam.life.service.impl;

import com.epam.life.context.ApplicationContext;
import com.epam.life.data.ApplicationComponents;
import com.epam.life.data.ApplicationIcons;
import com.epam.life.domain.Cell;
import com.epam.life.service.InterfaceService;
import com.epam.life.validate.FieldException;
import com.epam.life.validate.FieldValidator;
import com.epam.life.view.ApplicationInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.epam.life.data.ApplicationComponents.BUTTON_NAME;
import static com.epam.life.data.ApplicationComponents.BUTTON_PREFERRED_SIZE;
import static com.epam.life.domain.FieldType.*;
import static java.awt.Frame.MAXIMIZED_BOTH;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/**
 * This class is a implementation of {@link InterfaceService}.
 * Main functionality of this class is working with main window.
 * There is two <code>public</code> methods for working with GUI here:
 * {@link this#viewField()} and {@link this#viewFieldOnFullScreen()}.
 */
public class InterfaceServiceImpl implements InterfaceService {
    private final ApplicationInterface applicationInterface;
    private final FieldValidator fieldValidator;
    private final ApplicationContext applicationContext;

    /**
     * Creates new instance of <code>InterfaceServiceImpl</code> class.
     *
     * @param applicationContext   dependency on <code>ApplicationContext</code>
     * @param fieldValidator       dependency on <code>FieldValidator</code>
     * @param applicationInterface dependency on <code>ApplicationInterface</code>
     */
    public InterfaceServiceImpl(ApplicationContext applicationContext, FieldValidator fieldValidator,
                                ApplicationInterface applicationInterface) {
        this.applicationContext = applicationContext;
        this.fieldValidator = fieldValidator;
        this.applicationInterface = applicationInterface;
    }

    /**
     * Check text fields on write values and if all ok - view
     * cell's field and create array of <code>Cell[][]</code>.
     */
    @Override
    public void viewField() throws NumberFormatException, FieldException {
        checkTextFieldAndSet(applicationInterface.getRows());
        checkTextFieldAndSet(applicationInterface.getColumns());
        checkTextFieldAndSet(applicationInterface.getSteps());
        if (applicationContext.bothFieldsNotNull()) {
            fillFieldByCells();
            createCellArray();
        } else {
            applicationInterface.getStartButton().setEnabled(false);
            applicationInterface.clearField();
            applicationContext.setGameField(null);
        }
    }

    /**
     * Method <code>checkTextFieldAndSet</code> serves for
     * checking input <code>TextField</code>
     * on integer value and validate this value in
     * {@link FieldValidator#validateValue(int)} method.
     *
     * @param field of <code>TextField</code> type which needs to be checked and validated
     * @throws NumberFormatException Exception that will be thrown when value
     *                               in the field not an integer.
     * @throws FieldException        Exception that will be thrown when value
     *                               * in the field more than 100.
     */
    public void checkTextFieldAndSet(TextField field) throws NumberFormatException, FieldException {
        int value = getValueFromTextField(field);
        if (!field.getName().equals(STEP)) {
            fieldValidator.validateValue(value);
        }
        switch (field.getName()) {
            case (ROW):
                applicationContext.setRows(value);
                break;
            case (COLUMN):
                applicationContext.setColumns(value);
                break;
            case (STEP):
                applicationContext.setSteps(value);
        }
    }

    private int getValueFromTextField(TextField field) throws NumberFormatException {
        if (field.getText().equals(STEP)) {
            return 0;
        }
        return Integer.parseInt(field.getText());
    }

    private void fillFieldByCells() {
        applicationInterface.getStartButton().setText(ApplicationComponents.START);
        applicationInterface.setEnabledOfControlButtons(true);
        applicationInterface.clearField();
        applicationContext.setClearField(true);
        applicationContext.setCountBacteria(0);
        applicationContext.setStartStep(1);
        GridLayout layout = (GridLayout) applicationInterface.getField().getLayout();
        layout.setRows(applicationContext.getRows());
        layout.setColumns(applicationContext.getColumns());
    }

    private void createCellArray() {
        applicationContext.setGameField(new Cell[applicationContext.getRows()][applicationContext.getColumns()]);
        for (int i = 0; i < applicationContext.getRows(); i++) {
            for (int j = 0; j < applicationContext.getColumns(); j++) {
                applicationContext.getGameField()[i][j] = new Cell(false, 0);
                createCell(i, j);
            }
        }
    }

    private void createCell(int row, int column) {
        JButton jButton = createJButton(row, column);
        jButton.addActionListener(e -> markCell(row, column, jButton));
        applicationInterface.getField().add(jButton);
    }

    private JButton createJButton(int row, int column) {
        JButton jButton = new JButton();
        jButton.setText(null);
        jButton.setPreferredSize(new Dimension(BUTTON_PREFERRED_SIZE, BUTTON_PREFERRED_SIZE));
        jButton.setName(String.format(BUTTON_NAME, row, column));
        jButton.setBackground(Color.white);
        return jButton;
    }

    private void markCell(int row, int column, JButton jButton) {
        applicationInterface.turnInfectedStatus(jButton);
        boolean infected = applicationContext.getGameField()[row][column].isInfected();
        if (infected) {
            applicationContext.setCountBacteria(applicationContext.getCountBacteria() + 1);
        } else {
            applicationContext.setCountBacteria(applicationContext.getCountBacteria() - 1);
        }
        applicationContext.getGameField()[row][column]
                .setInfected(!infected);
    }

    /**
     * Creates new window and set it on full screen. Window has only one pane - pane with cell's field.
     */
    @Override
    public void viewFieldOnFullScreen() {
        JFrame jFrame = createWindow();
        jFrame.setVisible(true);
        applicationInterface.setVisible(false);
        applicationInterface.dispose();
        applicationInterface.getContentPane().remove(applicationInterface.getRootPanel());
        jFrame.getContentPane().add(applicationInterface.getField());
    }

    private JFrame createWindow() {
        JFrame jFrame = new JFrame(ApplicationComponents.APPLICATION_NAME);
        jFrame.setIconImage(new ImageIcon(this.getClass().getResource(ApplicationIcons.MAIN_ICON)).getImage());
        jFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        jFrame.setExtendedState(MAXIMIZED_BOTH);
        jFrame.getContentPane().setBackground(new Color(-1040));
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                applicationInterface.startApplication();
                jFrame.dispose();
                jFrame.getContentPane().removeAll();
                jFrame.setVisible(false);
            }
        });
        return jFrame;
    }

}
