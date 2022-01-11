package com.epam.life.domain;

/**
 * It is a POJO that represents the cell of the field.
 * <p>
 * This class contains two fields:
 * - boolean <code>boolean infected</code> that tell about infected status of the cell;
 * - <code>int step</code> that tell a step of latest modification of this cell.
 * <p>
 * Modification means a change in status of <code>boolean infected</code> field.
 */
public class Cell {
    private boolean infected;
    private int step;

    /**
     * Creates new Cell.
     *
     * @param infected status of this cell. May be true ar false.
     * @param step     on which latest modification was.
     */
    public Cell(boolean infected, int step) {
        this.infected = infected;
        this.step = step;
    }

    /**
     * This method needs for change infected status and step of the current cell
     * synchronously to prevent condition race.
     *
     * @param infected status of this cell. May be true ar false.
     * @param step     on which latest modification was.
     */
    public synchronized void changeInfectedStatusSynchronized(boolean infected, int step) {
        if (step > this.step) {
            this.infected = infected;
            this.step = step;
        }
        if (step < this.step) {
            throw new RuntimeException("Bad Step");
        }
    }

    /**
     * Check infected status of the cell synchronously.
     *
     * @param step of cycle on which status should checked.
     * @return <code>boolean infected</code> which can be true or false.
     */
    public synchronized boolean isInfectedSynchronized(int step) {
        if (this.infected && this.step < step) {
            return true;
        }
        return !this.infected && this.step == step;
    }

    public int getStep() {
        return step;
    }

    public boolean isInfected() {
        return infected;
    }

    public void setInfected(boolean infected) {
        this.infected = infected;
    }

}
