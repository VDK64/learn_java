package com.epam.life.controller;

import com.epam.life.data.ApplicationMessages;
import com.epam.life.service.InterfaceService;
import com.epam.life.service.ReproductionService;
import com.epam.life.validate.FieldException;
import com.epam.life.validate.FieldValidator;
import com.epam.life.view.ApplicationInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import static com.epam.life.domain.FieldType.*;

/**
 * This is the application controller. It adds listeners to buttons and text fields which calls services methods.
 * Also, there is an exception handler method in this class {@link this#exceptionHandler(Consumer)}.
 * {@link InterfaceService}
 * {@link ReproductionService}
 */
public class ApplicationController {
    private final ApplicationInterface applicationInterface;
    private final ReproductionService reproductionService;
    private final InterfaceService interfaceService;
    private final FieldValidator fieldValidator;

    /**
     * Constructs new class and inject dependencies on service layer and interface.
     *
     * @param applicationInterface class representing GUI {@link ApplicationInterface}
     * @param reproductionService  interface of service responsible for reproduction.
     * @param interfaceService     interface of service responsible for graphics.
     * @param fieldValidator       dependency on validation class
     */
    public ApplicationController(ApplicationInterface applicationInterface,
                                 ReproductionService reproductionService,
                                 InterfaceService interfaceService,
                                 FieldValidator fieldValidator) {
        this.applicationInterface = applicationInterface;
        this.reproductionService = reproductionService;
        this.interfaceService = interfaceService;
        this.fieldValidator = fieldValidator;
    }

    /**
     * Adds listeners to the buttons and text fields.
     * This method serves like entry point in this class.
     */
    public void addListeners() {
        addListenersOnTextFields();
        addListenersOnButtons();
    }

    private void addListenersOnTextFields() {
        applicationInterface.getRows().addFocusListener(new TextFieldFocusAdapter(
                applicationInterface.getRows(), ROW));

        applicationInterface.getColumns().addFocusListener(new TextFieldFocusAdapter
                (applicationInterface.getColumns(), COLUMN));

        applicationInterface.getSteps().addFocusListener(new TextFieldFocusAdapter(
                applicationInterface.getSteps(), STEP));
    }

    private void addListenersOnButtons() {
        applicationInterface.getPrintFieldButton().addActionListener(exceptionHandler(e -> interfaceService.viewField()));
        applicationInterface.getStartButton().addActionListener(e -> reproductionService.startReproduction());
        applicationInterface.getClearButton().addActionListener(exceptionHandler(e -> interfaceService.viewField()));
        applicationInterface.getStopButton().addActionListener(e -> reproductionService.stopReproduction());
        applicationInterface.getFullScreenButton().addActionListener(e -> interfaceService.viewFieldOnFullScreen());
        applicationInterface.getAlgorithmButton().addActionListener(e -> reproductionService.changeAlgorithm());
    }

    private ActionListener exceptionHandler(Consumer<ActionEvent> consumer) {
        return e -> {
            try {
                consumer.accept(e);
            } catch (NumberFormatException exception) {
                JOptionPane.showConfirmDialog(applicationInterface.getRootPanel(),
                        ApplicationMessages.WARNING_NOT_INTEGER,
                        ApplicationMessages.WARNING_TITLE, JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            } catch (
                    FieldException exception) {
                recognizeAnswer(JOptionPane.showConfirmDialog(applicationInterface.getRootPanel(),
                        ApplicationMessages.MANY_CELLS, ApplicationMessages.WARNING_TITLE,
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE));
            }
        };
    }

    private void recognizeAnswer(int answer) {
        if (answer == 0) {
            fieldValidator.setConfirm(true);
        } else {
            applicationInterface.setDefaultTextInFields();
        }
    }

}
