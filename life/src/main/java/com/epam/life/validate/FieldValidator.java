package com.epam.life.validate;

import com.epam.life.data.ApplicationMessages;

/**
 * This is a validator of the application. It validate value in it's single method.
 */
public class FieldValidator {
    private boolean confirm;

    /**
     * Checks if this number is greater than 100 or not.
     * If yes then throw <code>FieldException</code>.
     * @param value that will be checked
     */
    public void validateValue(int value) {
        if (value >= 100 && !confirm) {
            throw new FieldException(ApplicationMessages.WARNING_TITLE, null);
        }
    }

    /**
     * Set <code>boolean confirm</code> field on true.
     * It means than user confirmed information about {@link ApplicationMessages#WARNING_TITLE}.
     * @param confirm it is a boolean input value that will be assigned to <code>boolean confirm</code>.
     */
    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

}
