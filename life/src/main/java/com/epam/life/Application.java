package com.epam.life;

import com.epam.life.context.ApplicationContext;
import com.epam.life.controller.ApplicationController;
import com.epam.life.data.ApplicationComponents;
import com.epam.life.service.InterfaceService;
import com.epam.life.service.ReproductionService;
import com.epam.life.service.impl.InterfaceServiceImpl;
import com.epam.life.service.impl.ReproductionServiceImpl;
import com.epam.life.validate.FieldValidator;
import com.epam.life.view.ApplicationInterface;

import javax.swing.*;

/**
 * It's a main class of application which creates instances and set dependencies {@link #init}.
 * Also, has a {@link #main} method that is an entry point of application.
 */
public class Application {

    /**
     * Main method, that call static <code>SwingUtilities.invokeLater</code> to
     * execute asynchronously {@link ApplicationInterface#startApplication()} which paint GUI.
     * @param args This is console input.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> init().startApplication());
    }

    /**
     * This method initialize all classes and set dependencies, then
     * @return an instance of {@link ApplicationInterface}
     * class which has a {@link ApplicationInterface#startApplication()} method
     * that create main window of application.
     */
    public static ApplicationInterface init() {
        ApplicationContext applicationContext = new ApplicationContext();
        FieldValidator fieldValidator = new FieldValidator();
        ApplicationInterface applicationInterface = new ApplicationInterface(ApplicationComponents.APPLICATION_NAME);
        InterfaceService interfaceService = new InterfaceServiceImpl(applicationContext, fieldValidator, applicationInterface);
        ReproductionService reproductionService = new ReproductionServiceImpl(applicationContext, applicationInterface);
        ApplicationController controller = new ApplicationController(applicationInterface,
                reproductionService, interfaceService, fieldValidator);

        setDependencies(applicationContext, fieldValidator, applicationInterface, controller
        );
        return applicationInterface;
    }

    private static void setDependencies(ApplicationContext applicationContext, FieldValidator fieldValidator,
                                        ApplicationInterface applicationInterface, ApplicationController controller) {
        applicationInterface.setApplicationContext(applicationContext);
        applicationInterface.setController(controller);
        applicationContext.setFieldValidator(fieldValidator);
    }

}
