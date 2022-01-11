package com.epam.life.controller;

import com.epam.life.data.ApplicationComponents;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import static com.epam.life.data.ApplicationComponents.EMPTY_STRING;
import static com.epam.life.domain.FieldType.*;

/**
 * A class that receiving focus events. It extends <code>FocusAdapter</code>
 * to create a {@code FocusEvent} listener.
 * Contains overridden methods for the events when focus gained or lost.
 */
public class TextFieldFocusAdapter extends FocusAdapter {
    private final TextField field;
    private final String type;

    /**
     * Constructs new class.
     * @param field on which the listener should be added.
     * @param type of field. For example:
     * {@link ApplicationComponents#ROWS} or
     * {@link ApplicationComponents#COLUMNS} or
     * {@link ApplicationComponents#STEPS}.
     */
    public TextFieldFocusAdapter(TextField field, String type) {
        super();
        this.field = field;
        this.type = type;
    }

    /**
     * Invoked when a component gains the keyboard focus.
     * @param e the focus event
     */
    @Override
    public void focusGained(FocusEvent e) {
        if (field.getText().equals(ROW) || field.getText().equals(COLUMN) || field.getText().equals(STEP)) {
            field.setText(null);
        }
    }

    /**
     * Invoked when a component loses the keyboard focus.
     * @param e the focus event
     */
    @Override
    public void focusLost(FocusEvent e) {
        if (field.getText() == null || field.getText().equals(EMPTY_STRING)) {
            field.setText(type);
        }
    }

}
