package com.epam.life.service;

import com.epam.life.context.ApplicationContext;
import com.epam.life.service.impl.InterfaceServiceImpl;
import com.epam.life.validate.FieldException;
import com.epam.life.validate.FieldValidator;
import com.epam.life.view.ApplicationInterface;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.swing.*;
import java.awt.*;

import static com.epam.life.data.ApplicationComponents.START;
import static com.epam.life.data.TestComponents.*;
import static com.epam.life.domain.FieldType.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InterfaceServiceTest {
    private ApplicationContext applicationContext;
    private ApplicationInterface mock;
    private InterfaceService underTest;

    @Before
    public void init() {
        applicationContext = new ApplicationContext();
        mock = Mockito.mock(ApplicationInterface.class);
        underTest = new InterfaceServiceImpl(applicationContext, new FieldValidator(), mock);
    }

    @Test(expected = NumberFormatException.class)
    public void testViewFieldWithInvalidRows() {
        TextField rowsTextField = new TextField(WRONG_VALUE_IN_TEXT_FIELD);
        rowsTextField.setName(ROW);
        when(mock.getRows()).thenReturn(rowsTextField);
        underTest.viewField();
    }

    @Test(expected = FieldException.class)
    public void testViewFieldWhenRowsInTextFieldMore100() {
        TextField rowsTextField = new TextField(TEXT_FIELD_IS_HUNDRED_OR_MORE);
        rowsTextField.setName(ROW);
        when(mock.getRows()).thenReturn(rowsTextField);
        underTest.viewField();
    }

    @Test(expected = NumberFormatException.class)
    public void testViewFieldWithInvalidColumns() {
        TextField rowsTextField = new TextField(TEXT_FIELD_LESS_THEN_HUNDRED);
        TextField columnsTextField = new TextField(WRONG_VALUE_IN_TEXT_FIELD);
        rowsTextField.setName(ROW);
        columnsTextField.setName(COLUMN);
        when(mock.getRows()).thenReturn(rowsTextField);
        when(mock.getColumns()).thenReturn(columnsTextField);
        underTest.viewField();
    }

    @Test(expected = FieldException.class)
    public void testViewFieldWhenColumnsInTextFieldMore100() {
        TextField rowsTextField = new TextField(TEXT_FIELD_LESS_THEN_HUNDRED);
        TextField columnsTextField = new TextField(TEXT_FIELD_IS_HUNDRED_OR_MORE);
        rowsTextField.setName(ROW);
        rowsTextField.setName(COLUMN);
        when(mock.getRows()).thenReturn(rowsTextField);
        when(mock.getColumns()).thenReturn(columnsTextField);
        underTest.viewField();
    }

    @Test(expected = NumberFormatException.class)
    public void testViewFieldWithInvalidSteps() {
        TextField rowsTextField = new TextField(TEXT_FIELD_LESS_THEN_HUNDRED);
        TextField columnsTextField = new TextField(TEXT_FIELD_LESS_THEN_HUNDRED);
        TextField stepsTextField = new TextField(WRONG_VALUE_IN_TEXT_FIELD);
        rowsTextField.setName(ROW);
        columnsTextField.setName(COLUMN);
        stepsTextField.setName(STEP);
        when(mock.getRows()).thenReturn(rowsTextField);
        when(mock.getColumns()).thenReturn(columnsTextField);
        when(mock.getSteps()).thenReturn(stepsTextField);
        underTest.viewField();
    }

    @Test()
    public void testViewFieldWithTextSteps() {
        JPanel jPanel = new JPanel(new GridLayout());
        TextField rowsTextField = new TextField(TEXT_FIELD_LESS_THEN_HUNDRED);
        TextField columnsTextField = new TextField(TEXT_FIELD_LESS_THEN_HUNDRED);
        TextField stepsTextField = new TextField(STEP);
        rowsTextField.setName(ROW);
        columnsTextField.setName(COLUMN);
        stepsTextField.setName(STEP);
        when(mock.getRows()).thenReturn(rowsTextField);
        when(mock.getColumns()).thenReturn(columnsTextField);
        when(mock.getSteps()).thenReturn(stepsTextField);
        when(mock.getStartButton()).thenReturn(new JButton());
        doNothing().when(mock).setEnabledOfControlButtons(true);
        when(mock.getField()).thenReturn(jPanel);
        underTest.viewField();
        assertEquals(UNLIMITED_STEPS, applicationContext.getSteps());
    }

    @Test()
    public void testGameFieldIsNullWhenViewFieldWithSomeOfTextFieldIsNull() {
        TextField rowsTextField = new TextField(TEXT_FIELD_IS_NULL);
        TextField columnsTextField = new TextField(TEXT_FIELD_LESS_THEN_HUNDRED);
        TextField stepsTextField = new TextField(STEP);
        rowsTextField.setName(ROW);
        columnsTextField.setName(COLUMN);
        stepsTextField.setName(STEP);
        when(mock.getRows()).thenReturn(rowsTextField);
        when(mock.getColumns()).thenReturn(columnsTextField);
        when(mock.getSteps()).thenReturn(stepsTextField);
        when(mock.getStartButton()).thenReturn(new JButton());
        doNothing().when(mock).clearField();
        underTest.viewField();
        assertNull(applicationContext.getGameField());
    }

    @Test()
    public void testStartButtonIsDisabledWhenViewFieldWithSomeOfTextFieldIsNull() {
        TextField rowsTextField = new TextField(TEXT_FIELD_IS_NULL);
        TextField columnsTextField = new TextField(TEXT_FIELD_LESS_THEN_HUNDRED);
        TextField stepsTextField = new TextField(STEP);
        rowsTextField.setName(ROW);
        columnsTextField.setName(COLUMN);
        stepsTextField.setName(STEP);
        when(mock.getRows()).thenReturn(rowsTextField);
        when(mock.getColumns()).thenReturn(columnsTextField);
        when(mock.getSteps()).thenReturn(stepsTextField);
        when(mock.getStartButton()).thenReturn(new JButton());
        doNothing().when(mock).clearField();
        underTest.viewField();
        assertFalse(mock.getStartButton().isEnabled());
    }

    @Test
    public void testCountOfRowsInLayoutAndTextFieldIsEqualsWhenViewField() {
        JPanel jPanel = loadTextFieldsByLessThenHundredValueAndMockMethods();
        underTest.viewField();
        GridLayout layout = (GridLayout) jPanel.getLayout();
        assertEquals(LAYOUT_ROWS_AND_COLUMNS, layout.getRows());
    }

    @Test
    public void testCountOfColumnsInLayoutAndTextFieldIsEqualsWhenViewField() {
        JPanel jPanel = loadTextFieldsByLessThenHundredValueAndMockMethods();
        underTest.viewField();
        GridLayout layout = (GridLayout) jPanel.getLayout();
        assertEquals(LAYOUT_ROWS_AND_COLUMNS, layout.getColumns());
    }

    @Test
    public void testCountOfRowsInApplicationContextAndTextFieldIsEqualsWhenViewField() {
        loadTextFieldsByLessThenHundredValueAndMockMethods();
        underTest.viewField();
        assertEquals(LAYOUT_ROWS_AND_COLUMNS, applicationContext.getRows());
    }

    @Test
    public void testCountOfColumnsInApplicationContextAndTextFieldIsEqualsWhenViewField() {
        loadTextFieldsByLessThenHundredValueAndMockMethods();
        underTest.viewField();
        assertEquals(LAYOUT_ROWS_AND_COLUMNS, applicationContext.getColumns());
    }

    @Test
    public void testCountOfStepsFromApplicationContextAndTextFieldIsEqualsWhenViewField() {
        loadTextFieldsByLessThenHundredValueAndMockMethods();
        underTest.viewField();
        assertEquals(STEPS_COUNT, applicationContext.getSteps());
    }

    @Test
    public void testTextOfStartButtonIsStartWhenViewField() {
        loadTextFieldsByLessThenHundredValueAndMockMethods();
        underTest.viewField();
        assertEquals(START, mock.getStartButton().getText());
    }

    @Test
    public void testMadeClearFieldOperationWhenViewField() {
        loadTextFieldsByLessThenHundredValueAndMockMethods();
        underTest.viewField();
        assertTrue(applicationContext.isClearField());
    }

    @Test
    public void testNoAliveBacteriaWhenViewField() {
        loadTextFieldsByLessThenHundredValueAndMockMethods();
        underTest.viewField();
        assertEquals(BACTERIA_ARE_DEAD, applicationContext.getCountBacteria());
    }

    @Test
    public void testStartsFromFirstStepWhenViewField() {
        loadTextFieldsByLessThenHundredValueAndMockMethods();
        underTest.viewField();
        assertEquals(STEPS_STARTS_FROM_ONE, applicationContext.getStartStep());
    }

    @Test
    public void testRowsInLayoutEqualsToLengthOfGameFieldWhenViewField() {
        loadTextFieldsByLessThenHundredValueAndMockMethods();
        underTest.viewField();
        assertEquals(LAYOUT_ROWS_AND_COLUMNS, applicationContext.getGameField().length);
    }

    @Test
    public void testColumnsInLayoutEqualsToLengthOfGameFieldWhenViewField() {
        loadTextFieldsByLessThenHundredValueAndMockMethods();
        underTest.viewField();
        assertEquals(LAYOUT_ROWS_AND_COLUMNS, applicationContext.getGameField()[0].length);
    }

    private JPanel loadTextFieldsByLessThenHundredValueAndMockMethods() {
        JPanel jPanel = new JPanel(new GridLayout());
        TextField rowsTextField = new TextField(TEXT_FIELD_LESS_THEN_HUNDRED);
        TextField columnsTextField = new TextField(TEXT_FIELD_LESS_THEN_HUNDRED);
        TextField stepsTextField = new TextField(TEXT_FIELD_LESS_THEN_HUNDRED);
        rowsTextField.setName(ROW);
        columnsTextField.setName(COLUMN);
        stepsTextField.setName(STEP);
        when(mock.getRows()).thenReturn(rowsTextField);
        when(mock.getColumns()).thenReturn(columnsTextField);
        when(mock.getSteps()).thenReturn(stepsTextField);
        when(mock.getStartButton()).thenReturn(new JButton());
        doNothing().when(mock).setEnabledOfControlButtons(true);
        when(mock.getField()).thenReturn(jPanel);
        return jPanel;
    }

}