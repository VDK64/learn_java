package com.epam.life.context;

import com.epam.life.domain.Cell;
import com.epam.life.validate.FieldValidator;

/**
 * This class is a context of the application. It consists of
 * values of rows, columns and steps which defined by user.
 * Also, this class contains matrix representation of a field
 * in GUI using array of <code>Cell[][]</code>.
 * In addition <code>ApplicationContext</code> has in itself
 * boolean flags which controls state of application.
 */
public class ApplicationContext {
    private int rows;
    private int columns;
    private int steps;
    private Cell[][] gameField;
    private boolean clearField;
    private int countBacteria;
    private int startStep;
    private boolean runningReproduction;
    private boolean threadAlgorithm;

    public boolean bothFieldsNotNull() {
        return rows != 0 && columns != 0;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public boolean isClearField() {
        return clearField;
    }

    public boolean isRunningReproduction() {
        return runningReproduction;
    }

    public int getCountBacteria() {
        return countBacteria;
    }

    public boolean isThreadAlgorithm() {
        return threadAlgorithm;
    }

    public void setThreadAlgorithm(boolean threadAlgorithm) {
        this.threadAlgorithm = threadAlgorithm;
    }

    public void setCountBacteria(int countBacteria) {
        this.countBacteria = countBacteria;
    }

    public int getStartStep() {
        return startStep;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public void setStartStep(int startStep) {
        this.startStep = startStep;
    }

    public void setRunningReproduction(boolean runningReproduction) {
        this.runningReproduction = runningReproduction;
    }

    public void setClearField(boolean clearField) {
        this.clearField = clearField;
    }

    public int getSteps() {
        return steps;
    }

    public Cell[][] getGameField() {
        return gameField;
    }

    public void setFieldValidator(FieldValidator fieldValidator) {
    }

    public void setGameField(Cell[][] gameField) {
        this.gameField = gameField;
    }

}
