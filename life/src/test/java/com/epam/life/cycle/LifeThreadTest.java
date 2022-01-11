package com.epam.life.cycle;

import com.epam.life.context.ApplicationContext;
import com.epam.life.domain.Cell;
import com.epam.life.service.impl.ReproductionServiceImpl;
import com.epam.life.util.UtilityClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.epam.life.data.TestComponents.SQUARE_WITH_TWO_CELLS_SIDES;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class LifeThreadTest extends AbstractThreadTest {
    private LifeThread underTest;

    @Before
    public void init() {
        setApplicationContext(new ApplicationContext());
        setUtilityClass(new UtilityClass(getApplicationContext()));
        underTest = new LifeThread(getApplicationContext(),
                new ReproductionServiceImpl(getApplicationContext(), getMock()));
    }

    @Test
    public void testLifeStarts() {
        Cell[][] cellArray = getUtilityClass().setRowAndColumnsAndGetGameField(SQUARE_WITH_TWO_CELLS_SIDES);
        cellArray[0][0].setInfected(true);
        cellArray[0][1].setInfected(true);
        cellArray[1][1].setInfected(true);
        underTest.setStep(1);
        underTest.run();
        assertTrue(cellArray[1][0].isInfected());
    }

}