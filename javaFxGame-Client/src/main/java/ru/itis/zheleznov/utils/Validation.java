package ru.itis.zheleznov.utils;

import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

public class Validation {

    public static boolean notEmpty(TextInputControl ... inputs) {
        boolean result = true;

        for (TextInputControl control : inputs) {
            if (control.getText().equals("")) {
                control.getStyleClass().add("error");
                result = false;
            }
        }

        return result;
    }
}
