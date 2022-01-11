package com.epam.life.cycle;

import com.epam.life.context.ApplicationContext;
import com.epam.life.domain.Cell;
import com.epam.life.service.ReproductionService;
import com.epam.life.view.ApplicationInterface;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import static com.epam.life.data.ApplicationComponents.STOP;

/**
 * <code>ControlThread</code> controls other threads like LifeThread, DeadThread and DrawThread.
 * Implements <code>Runnable</code> interface to start in other of main thread.
 */
public class ControlThread implements Runnable {
    private final ApplicationContext applicationContext;
    private final ExecutorService executorService;
    private final ApplicationInterface applicationInterface;
    private final LifeThread lifeThread;
    private final DeadThread deadThread;
    private final DrawThread drawThread;
    private boolean wasStopped;
    private boolean stop;

    /**
     * Constructs a new instance of <code>ControlThread</code> class.
     * @param applicationContext dependency of <code>ApplicationContext</code>
     * @param executorService dependency of <code>ExecutorService</code>
     * @param reproductionService dependency of <code>ReproductionService</code>
     * @param applicationInterface dependency of <code>ApplicationInterface</code>
     */
    public ControlThread(ApplicationContext applicationContext, ExecutorService executorService,
                         ReproductionService reproductionService, ApplicationInterface applicationInterface) {
        this.applicationContext = applicationContext;
        this.executorService = executorService;
        this.applicationInterface = applicationInterface;
        this.lifeThread = new LifeThread(applicationContext, reproductionService);
        this.deadThread = new DeadThread(applicationContext, reproductionService);
        this.drawThread = new DrawThread(applicationContext, applicationInterface, this);
    }

    /**
     * Main method of <code>ControlThread</code> that starts reproduction process
     * of bacteria in cells of field. Has stop boolean flag which stopped process.
     * Also, has <code>limitedCycle</code> and <code>unlimitedCycle</code> in depend of user
     * choice. If user wrote 0 in text field "steps" or didn't write anything then
     * it is situation for unlimited cycle, in other case limited cycle is a loop
     * that will works.
     *
     * All of two cycles has some same logic. If on first steps cell's field is empty,
     * <code>fillCellsRandomlyIfNeed</code> enables and filling all cells used random principle.
     * If all bacteria are dead - cycle stops.
     *
     * Additionally, in this class implemented two different thread working principles:
     * on ThreadApi and with <code>CompletableFuture</code>.
     */
    @Override
    public void run() {
        stop = false;
        if (applicationContext.getSteps() != 0) {
            limitedCycle();
        } else {
            unlimitedCycle();
        }
        applicationInterface.buttonsWheReproductionStop();
    }

    private void limitedCycle() {
        for (int i = applicationContext.getStartStep(); i < applicationContext.getSteps() + 1; i++) {
            if (wasStoppedOrAllBacteriasDead(i)) {
                break;
            }
            fillCellsRandomlyIfNeed(i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            wasStopped = false;
            if (applicationContext.isThreadAlgorithm()) {
                doCycleOnThreads(i);
            } else {
                doCycleOnFuture(i);
            }
        }
        if (!wasStopped) {
            applicationContext.setStartStep(applicationContext.getSteps() + 1);
            controlsOnStartMode();
        }
    }

    private void unlimitedCycle() {
        int currentStep = applicationContext.getStartStep();
        while (!wasStoppedOrAllBacteriasDead(currentStep)) {
            fillCellsRandomlyIfNeed(currentStep);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            wasStopped = false;
            if (applicationContext.isThreadAlgorithm()) {
                doCycleOnThreads(currentStep);
            } else {
                doCycleOnFuture(currentStep);
            }
            ++currentStep;
        }
    }

    private void fillCellsRandomlyIfNeed(int currentStep) {
        if (startWithEmptyField(currentStep)) {
            randomFillField();
            wasStopped = false;
            applicationContext.setClearField(false);
        }
    }

    private void controlsOnStartMode() {
        applicationContext.setRunningReproduction(false);
        applicationInterface.getStopButton().setText(STOP);
        applicationInterface.getStopButton().setEnabled(true);
        applicationInterface.buttonsWheReproductionStop();
    }

    private void doCycleOnFuture(int i) {
        try {
            prepareThreads(i);
            CompletableFuture<Void> lifeFuture = CompletableFuture.runAsync(lifeThread, executorService);
            CompletableFuture<Void> deadFuture = CompletableFuture.runAsync(deadThread, executorService);
            CompletableFuture.allOf(lifeFuture, deadFuture).get();
            CompletableFuture.runAsync(drawThread, executorService).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void doCycleOnThreads(int i) {
        try {
            prepareThreads(i);
            Thread lifeThread = new Thread(this.lifeThread);
            Thread deadThread = new Thread(this.deadThread);
            Thread drawThread = new Thread(this.drawThread);
            lifeThread.start();
            deadThread.start();
            lifeThread.join();
            deadThread.join();
            drawThread.start();
            drawThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean wasStoppedOrAllBacteriasDead(int currentStep) {
        if (stop) {
            wasStopped = true;
            applicationContext.setStartStep(currentStep);
            controlsOnStartMode();
            return true;
        }
        if (applicationContext.isClearField() && currentStep > 1 && !wasStopped) {
            applicationContext.setStartStep(currentStep);
            controlsOnStartMode();
            return true;
        }
        return false;
    }

    private boolean startWithEmptyField(int currentStep) {
        return applicationContext.isClearField() && (currentStep == 1 || wasStopped);
    }

    private void prepareThreads(int step) {
        lifeThread.setStep(step);
        deadThread.setStep(step);
    }

    private void randomFillField() {
        Random random = new Random();
        for (int i = 0; i < applicationContext.getRows(); i++) {
            for (int j = 0; j < applicationContext.getColumns(); j++) {
                if (stop) {
                    controlsOnStartMode();
                    break;
                }
                Cell cell = applicationContext.getGameField()[i][j];
                cell.setInfected(random.nextBoolean());
            }
        }
    }

    /**
     * Using for stop loop in {@link this#run()} method.
     */
    public void stop() {
        this.stop = true;
    }

}
