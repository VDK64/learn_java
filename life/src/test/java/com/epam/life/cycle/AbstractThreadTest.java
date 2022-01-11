package com.epam.life.cycle;

import com.epam.life.context.ApplicationContext;
import com.epam.life.util.UtilityClass;
import com.epam.life.view.ApplicationInterface;
import org.mockito.Mockito;

public class AbstractThreadTest {
    private final ApplicationInterface mock = Mockito.mock(ApplicationInterface.class);
    private ApplicationContext applicationContext;
    private UtilityClass utilityClass;

    public ApplicationInterface getMock() {
        return mock;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public UtilityClass getUtilityClass() {
        return utilityClass;
    }

    public void setUtilityClass(UtilityClass utilityClass) {
        this.utilityClass = utilityClass;
    }

}
