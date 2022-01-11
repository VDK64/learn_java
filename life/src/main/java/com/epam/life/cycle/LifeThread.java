package com.epam.life.cycle;

import com.epam.life.context.ApplicationContext;
import com.epam.life.data.ApplicationComponents;
import com.epam.life.domain.Cell;
import com.epam.life.service.ReproductionService;

import static com.epam.life.data.ApplicationComponents.EMPTINESS_TO_BIRTH;

/**
 * Class which responsibility is generate bacteria in cell in certain situation.
 * Implements <code>Runnable</code> interface to start in other of main thread.
 */
public class LifeThread implements Runnable {
    private final ApplicationContext applicationContext;
    private final ReproductionService reproductionService;
    private int step;

    /**
     * Constructor of this class that receives instances to set dependencies.
     * @param applicationContext dependency of <code>ApplicationContext</code>
     * @param reproductionService dependency of <code>ReproductionService</code>
     */
    public LifeThread(ApplicationContext applicationContext, ReproductionService reproductionService) {
        this.applicationContext = applicationContext;
        this.reproductionService = reproductionService;
    }

    /**
     * Goes on <code>Cell[][]</code> array and generates bacterium in the cell if
     * count of empty cells around equal {@link ApplicationComponents#EMPTINESS_TO_BIRTH}
     * For counting uses {@link ReproductionService#countNeighbours(int, int, int)} method.
     */
    @Override
    public void run() {
        for (int i = 0; i < applicationContext.getRows(); i++) {
            for (int j = 0; j < applicationContext.getColumns(); j++) {
                Cell currentCell = applicationContext.getGameField()[i][j];
                int neighbours = reproductionService.countNeighbours(i, j, step);
                if (!currentCell.isInfectedSynchronized(step)) {
                    if (neighbours == EMPTINESS_TO_BIRTH) {
                        currentCell.changeInfectedStatusSynchronized(true, step);
                    }
                }
            }
        }
    }

    public void setStep(int step) {
        this.step = step;
    }

}
