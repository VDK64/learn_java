package com.epam.life.service;

import com.epam.life.context.ApplicationContext;
import com.epam.life.service.impl.ReproductionServiceImpl;
import com.epam.life.util.UtilityClass;
import com.epam.life.view.ApplicationInterface;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.swing.*;

import static com.epam.life.data.ApplicationComponents.FUTURE;
import static com.epam.life.data.ApplicationComponents.THREAD;
import static com.epam.life.data.TestComponents.SQUARE_WITH_ONE_CELL_SIDES;
import static com.epam.life.data.TestComponents.SQUARE_WITH_THREE_CELLS_SIDES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReproductionServiceTest {
    private ApplicationContext applicationContext;
    private UtilityClass utilityClass;
    private ApplicationInterface mock;
    private ReproductionService underTest;

    @Before
    public void init() {
        applicationContext = new ApplicationContext();
        utilityClass = new UtilityClass(applicationContext);
        mock = Mockito.mock(ApplicationInterface.class);
        underTest = new ReproductionServiceImpl(applicationContext, mock);
    }

    @Test
    public void testCountNeighboursWhenAllAroundCellsAreNull() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_ONE_CELL_SIDES);
        applicationContext.getGameField()[0][0].setInfected(true);
        int neighbours = underTest.countNeighbours(0, 0, 1);
        assertEquals(0, neighbours);
    }

    @Test
    public void testCountNeighboursLeftUpperDiagonalInfectedOnPreviousStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[0][0].setInfected(true);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(1, neighbours);
    }

    @Test
    public void testCountNeighboursLeftUpperDiagonalDeadOnPreviousStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[0][0].setInfected(false);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(0, neighbours);
    }

    @Test
    public void testCountNeighboursLeftUpperDiagonalInfectedOnCurrentStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[0][0].changeInfectedStatusSynchronized(true, 1);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(0, neighbours);
    }

    @Test
    public void testCountNeighboursLeftUpperDiagonalDeadOnCurrentStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[0][0].changeInfectedStatusSynchronized(false, 1);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(1, neighbours);
    }

    @Test
    public void testCountNeighboursLeftLowerDiagonalInfectedOnPreviousStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[2][0].setInfected(true);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(1, neighbours);
    }

    @Test
    public void testCountNeighboursLeftLowerDiagonalDeadOnPreviousStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[2][0].setInfected(false);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(0, neighbours);
    }

    @Test
    public void testCountNeighboursLeftLowerDiagonalInfectedOnCurrentStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[2][0].changeInfectedStatusSynchronized(true, 1);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(0, neighbours);
    }

    @Test
    public void testCountNeighboursLeftLowerDiagonalDeadOnCurrentStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[2][0].changeInfectedStatusSynchronized(false, 1);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(1, neighbours);
    }

    @Test
    public void testCountNeighboursRightUpperDiagonalInfectedOnPreviousStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[0][2].setInfected(true);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(1, neighbours);
    }

    @Test
    public void testCountNeighboursRightUpperDiagonalDeadOnPreviousStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[0][2].setInfected(false);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(0, neighbours);
    }

    @Test
    public void testCountNeighboursRightUpperDiagonalInfectedOnCurrentStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[0][2].changeInfectedStatusSynchronized(true, 1);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(0, neighbours);
    }

    @Test
    public void testCountNeighboursRightUpperDiagonalDeadOnCurrentStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[0][2].changeInfectedStatusSynchronized(false, 1);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(1, neighbours);
    }

    @Test
    public void testCountNeighboursRightLowerDiagonalInfectedOnPreviousStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[2][2].setInfected(true);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(1, neighbours);
    }

    @Test
    public void testCountNeighboursRightLowerDiagonalDeadOnPreviousStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[2][2].setInfected(false);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(0, neighbours);
    }

    @Test
    public void testCountNeighboursRightLowerDiagonalInfectedOnCurrentStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[2][2].changeInfectedStatusSynchronized(true, 1);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(0, neighbours);
    }

    @Test
    public void testCountNeighboursRightLowerDiagonalDeadOnCurrentStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[2][2].changeInfectedStatusSynchronized(false, 1);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(1, neighbours);
    }

    @Test
    public void testCountNeighboursUpInfectedOnPreviousStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[0][1].setInfected(true);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(1, neighbours);
    }

    @Test
    public void testCountNeighboursUpDeadOnPreviousStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[0][1].setInfected(false);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(0, neighbours);
    }

    @Test
    public void testCountNeighboursUpInfectedOnCurrentStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[0][1].changeInfectedStatusSynchronized(true, 1);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(0, neighbours);
    }

    @Test
    public void testCountNeighboursUpDeadOnCurrentStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[0][1].changeInfectedStatusSynchronized(false, 1);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(1, neighbours);
    }

    @Test
    public void testCountNeighboursDownInfectedOnPreviousStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[2][1].setInfected(true);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(1, neighbours);
    }

    @Test
    public void testCountNeighboursDownDeadOnPreviousStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[2][1].setInfected(false);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(0, neighbours);
    }

    @Test
    public void testCountNeighboursDownInfectedOnCurrentStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[2][1].changeInfectedStatusSynchronized(true, 1);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(0, neighbours);
    }

    @Test
    public void testCountNeighboursDownDeadOnCurrentStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[2][1].changeInfectedStatusSynchronized(false, 1);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(1, neighbours);
    }

    @Test
    public void testCountNeighboursLeftInfectedOnPreviousStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[1][0].setInfected(true);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(1, neighbours);
    }

    @Test
    public void testCountNeighboursLeftDeadOnPreviousStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[1][0].setInfected(false);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(0, neighbours);
    }

    @Test
    public void testCountNeighboursLeftInfectedOnCurrentStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[1][0].changeInfectedStatusSynchronized(true, 1);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(0, neighbours);
    }

    @Test
    public void testCountNeighboursLeftDeadOnCurrentStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[1][0].changeInfectedStatusSynchronized(false, 1);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(1, neighbours);
    }

    @Test
    public void testCountNeighboursRightInfectedOnPreviousStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[1][2].setInfected(true);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(1, neighbours);
    }

    @Test
    public void testCountNeighboursRightDeadOnPreviousStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[1][2].setInfected(false);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(0, neighbours);
    }

    @Test
    public void testCountNeighboursRightInfectedOnCurrentStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[1][2].changeInfectedStatusSynchronized(true, 1);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(0, neighbours);
    }

    @Test
    public void testCountNeighboursRightDeadOnCurrentStep() {
        createFieldArrayAndSetRowsAndColumnsInApplicationContext(SQUARE_WITH_THREE_CELLS_SIDES);
        applicationContext.getGameField()[1][2].changeInfectedStatusSynchronized(false, 1);
        int neighbours = underTest.countNeighbours(1, 1, 1);
        assertEquals(1, neighbours);
    }

    @Test
    public void testChangeAlgorithmDefault() {
        assertFalse(applicationContext.isThreadAlgorithm());
    }

    @Test
    public void testChangeAlgorithmOnThreadApi() {
        when(mock.getAlgorithmButton()).thenReturn(new JButton());
        underTest.changeAlgorithm();
        assertEquals(THREAD, mock.getAlgorithmButton().getText());
    }

    @Test
    public void testChangeAlgorithmOnFutureApi() {
        when(mock.getAlgorithmButton()).thenReturn(new JButton());
        underTest.changeAlgorithm();
        underTest.changeAlgorithm();
        assertEquals(FUTURE, mock.getAlgorithmButton().getText());
    }

    private void createFieldArrayAndSetRowsAndColumnsInApplicationContext(int cellsCount) {
        utilityClass.createFieldArray(cellsCount, cellsCount);
        applicationContext.setRows(cellsCount);
        applicationContext.setColumns(cellsCount);
    }

}