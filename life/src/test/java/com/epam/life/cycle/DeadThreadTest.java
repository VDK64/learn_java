package com.epam.life.cycle;

import com.epam.life.context.ApplicationContext;
import com.epam.life.domain.Cell;
import com.epam.life.service.impl.ReproductionServiceImpl;
import com.epam.life.util.UtilityClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.epam.life.data.TestComponents.SQUARE_WITH_THREE_CELLS_SIDES;
import static org.junit.Assert.assertFalse;

@RunWith(MockitoJUnitRunner.class)
public class DeadThreadTest extends AbstractThreadTest {
    private DeadThread underTest;

    @Before
    public void init() {
        setApplicationContext(new ApplicationContext());
        setUtilityClass(new UtilityClass(getApplicationContext()));
        underTest = new DeadThread(getApplicationContext(),
                new ReproductionServiceImpl(getApplicationContext(), getMock()));
    }

    @Test
    public void testDeadWhenNeighboursLessThan2() {
        Cell[][] gameField = getUtilityClass().setRowAndColumnsAndGetGameField(SQUARE_WITH_THREE_CELLS_SIDES);
        gameField[1][1].setInfected(true);
        gameField[0][2].setInfected(true);
        underTest.setStep(1);
        underTest.run();
        assertFalse(gameField[1][1].isInfected());
    }

    @Test
    public void testDeadWhenNeighboursMoreThan4() {
        Cell[][] gameField = getUtilityClass().setRowAndColumnsAndGetGameField(SQUARE_WITH_THREE_CELLS_SIDES);
        gameField[1][1].setInfected(true);
        gameField[0][1].setInfected(true);
        gameField[0][0].setInfected(true);
        gameField[1][0].setInfected(true);
        gameField[1][2].setInfected(true);
        gameField[2][2].setInfected(true);
        underTest.setStep(1);
        underTest.run();
        assertFalse(gameField[1][1].isInfected());
    }

}