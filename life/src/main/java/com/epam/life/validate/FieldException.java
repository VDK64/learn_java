package com.epam.life.validate;

/**
 * A class that represents exception which occur when <code>FieldValidator</code>
 * throw it in it's {@link FieldValidator#validateValue(int)} method.
 */
public class FieldException extends RuntimeException {

    /**
     * Creates new <code>FieldException</code>.
     * @param message will be view on <code>JOptionPane</code>.
     * @param cause of the exception occur.
     */
    public FieldException(String message, Throwable cause) {
        super(message, cause);
    }

}
