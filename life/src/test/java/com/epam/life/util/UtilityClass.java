package com.epam.life.util;

import com.epam.life.context.ApplicationContext;
import com.epam.life.domain.Cell;

public class UtilityClass {
    private final ApplicationContext applicationContext;

    public UtilityClass(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void createFieldArray(int rows, int columns) {
        applicationContext.setGameField(new Cell[rows][columns]);
        Cell[][] gameField = applicationContext.getGameField();
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                gameField[i][j] = new Cell(false,0);
            }
        }
    }

    public Cell[][] setRowAndColumnsAndGetGameField(int cellSide) {
        applicationContext.setRows(cellSide);
        applicationContext.setColumns(cellSide);
        createFieldArray(cellSide, cellSide);
        return applicationContext.getGameField();
    }

}
