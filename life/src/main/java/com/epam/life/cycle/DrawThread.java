package com.epam.life.cycle;

import com.epam.life.context.ApplicationContext;
import com.epam.life.domain.Cell;
import com.epam.life.view.ApplicationInterface;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

/**
 * Class which responsibility is draw updates in cell's field.
 * Implements <code>Runnable</code> interface to start in other of main thread.
 */
public class DrawThread implements Runnable {
    private final ApplicationContext applicationContext;
    private final ApplicationInterface applicationInterface;
    private final ControlThread controlThread;

    public DrawThread(ApplicationContext applicationContext,
                      ApplicationInterface applicationInterface,
                      ControlThread controlThread) {
        this.applicationContext = applicationContext;
        this.applicationInterface = applicationInterface;
        this.controlThread = controlThread;
    }

    /**
     * Goes on <code>Cell[][]</code> array and print cell if <code>cellArray[i][j]</code>
     * is infected. If <code>cellArray[i][j]</code> isn't infected - print empty cell.
     * Also, stops cycle if all bacteria dead.
     */
    @Override
    public void run() {
        Component[] components = applicationInterface.getField().getComponents();
        int numberOfComponents = applicationContext.getRows() * applicationContext.getColumns();
        int currentComponent = 0;
        int countEmptyCells = 0;
        for (int i = 0; i < applicationContext.getRows(); i++) {
            for (int j = 0; j < applicationContext.getColumns(); j++) {
                Cell cell = applicationContext.getGameField()[i][j];
                if (currentComponent < numberOfComponents) {
                    countEmptyCells = printCellAndCountIfEmpty(components[currentComponent], cell, countEmptyCells);
                    currentComponent++;
                }
            }
        }
        if (countEmptyCells == numberOfComponents) {
            controlThread.stop();
        }
    }

    private int printCellAndCountIfEmpty(Component component, Cell cell, int countEmptyCells) {
        Optional<JButton> optionalJButton = castComponentToJButton(component);
        boolean infected = cell.isInfected();
        if (optionalJButton.isPresent()) {
            JButton jButton = optionalJButton.get();
            if (infected) {
                if (jButton.getBackground().equals(Color.WHITE)) {
                    jButton.setBackground(Color.RED);
                }
            } else {
                jButton.setBackground(Color.WHITE);
                ++countEmptyCells;
            }
        }
        return countEmptyCells;
    }

    private Optional<JButton> castComponentToJButton(Component component) {
        if (component instanceof JButton) {
            return Optional.of((JButton) component);
        }
        return Optional.empty();
    }

}
