package com.epam.life.service;

/**
 * Interface that responsible for reproduction representing.
 */
public interface ReproductionService {

    /**
     * Starts reproduction by starts cycle of life.
     */
    void startReproduction();

    /**
     * Stops reproduction be stops cycle of life.
     */
    void stopReproduction();

    /**
     * This method counts neighbours in around cells.
     * @param row of current cell.
     * @param column of current cell.
     * @param step of current cycle.
     * @return <code>int</code> count of neighbours of current cell.
     */
    int countNeighbours(int row, int column, int step);

    /**
     * Changes algorithm from <code>ThreadApi</code> to <code>CompletableFuture</code>.
     */
    void changeAlgorithm();
}
