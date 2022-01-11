package com.epam.life.service.impl;

import com.epam.life.context.ApplicationContext;
import com.epam.life.cycle.ControlThread;
import com.epam.life.domain.Cell;
import com.epam.life.service.ReproductionService;
import com.epam.life.view.ApplicationInterface;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.epam.life.data.ApplicationComponents.*;

/**
 * This class implements {@link ReproductionService} and using for
 * working with reproduction cycle.
 */
public class ReproductionServiceImpl implements ReproductionService {
    private final ApplicationInterface applicationInterface;
    private final ApplicationContext applicationContext;
    private final ExecutorService executorService;
    private final ControlThread controlThread;

    /**
     * Constructs a new instance of this class
     * @param applicationContext dependency on <code>ApplicationContext</code>
     * @param applicationInterface dependency on <code>ApplicationInterface</code>
     */
    public ReproductionServiceImpl(ApplicationContext applicationContext,
                                   ApplicationInterface applicationInterface) {
        this.applicationContext = applicationContext;
        this.executorService = Executors.newFixedThreadPool(COUNT_THREADS);
        this.controlThread = new ControlThread(applicationContext, executorService,
                this, applicationInterface);
        this.applicationInterface = applicationInterface;
    }

    /**
     * Starts reproduction, clearing all cells in field firstly, and
     * sets buttons of control panel on reproduction mode
     * involving {@link ApplicationInterface#buttonsWhenReproductionStart()} method.
     * Reproduction is started by creating a thread that executes asynchronously {@link ControlThread#run()}.
     */
    @Override
    public void startReproduction() {
        applicationContext.setRunningReproduction(true);
        applicationContext.setClearField(applicationContext.getCountBacteria() == 0);
        applicationInterface.buttonsWhenReproductionStart();
        CompletableFuture.runAsync(controlThread, executorService);
    }

    /**
     * Stops reproduction and set options to the stop button until cycle is not stopped.
     */
    @Override
    public void stopReproduction() {
        if (applicationContext.isRunningReproduction()) {
            applicationInterface.getStopButton().setEnabled(false);
            applicationInterface.getStopButton().setText(STOPPING);
            controlThread.stop();
        }
    }

    /**
     * This method counts neighbours around current cell and return it.
     * @param row of current cell.
     * @param column of current cell.
     * @param step of current cycle.
     * @return count of neighbours.
     */
    @Override
    public int countNeighbours(int row, int column, int step) {
        int countNeighbours = 0;
        if (haveNextColumn(column)) {
            countNeighbours = incrementIfInfected(row, column + 1, countNeighbours, step);
        }
        if (haveNextRow(row)) {
            countNeighbours = incrementIfInfected(row + 1, column, countNeighbours, step);
        }
        if (havePreviousColumn(column)) {
            countNeighbours = incrementIfInfected(row, column - 1, countNeighbours, step);
        }
        if (havePreviousRow(row)) {
            countNeighbours = incrementIfInfected(row - 1, column, countNeighbours, step);
        }
        if (haveNextRow(row) && haveNextColumn(column)) {
            countNeighbours = incrementIfInfected(row + 1, column + 1, countNeighbours, step);
        }
        if (havePreviousRow(row) && haveNextColumn(column)) {
            countNeighbours = incrementIfInfected(row - 1, column + 1, countNeighbours, step);
        }
        if (haveNextRow(row) && havePreviousColumn(column)) {
            countNeighbours = incrementIfInfected(row + 1, column - 1, countNeighbours, step);
        }
        if (havePreviousRow(row) && havePreviousColumn(column)) {
            countNeighbours = incrementIfInfected(row - 1, column - 1, countNeighbours, step);
        }
        return countNeighbours;
    }

    private int incrementIfInfected(int row, int column, int counter, int step) {
        Cell cell = applicationContext.getGameField()[row][column];
        if (cell.isInfectedSynchronized(step)) {
            return ++counter;
        }
        return counter;
    }

    /**
     * Implements changing algorithm from <code>ThreadApi</code> to <code>CompletableFuture</code>
     * and via versa.
     */
    @Override
    public void changeAlgorithm() {
        applicationContext.setThreadAlgorithm(!applicationContext.isThreadAlgorithm());
        if (applicationContext.isThreadAlgorithm()) {
            applicationInterface.getAlgorithmButton().setText(THREAD);
        } else {
            applicationInterface.getAlgorithmButton().setText(FUTURE);
        }
    }

    private boolean haveNextRow(int row) {
        return row < applicationContext.getRows() - 1;
    }

    private boolean haveNextColumn(int column) {
        return column < applicationContext.getColumns() - 1;
    }

    private boolean havePreviousRow(int row) {
        return row > 0;
    }

    private boolean havePreviousColumn(int column) {
        return column > 0;
    }

}
