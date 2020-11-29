package ru.itis.zheleznov.validators;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.itis.zheleznov.models.RawQuestionRow;

import java.util.List;

public class TextFieldValidator {

    public static boolean validateQuestionMaker(List<RawQuestionRow> rawQuestion, TextField categoryName) {
        boolean result = true;
        for (RawQuestionRow questionRow : rawQuestion) {
            if (questionRow.getPoints().getLength() == 0) {
                showError(questionRow.getPoints());
                result = false;
            }
            if (questionRow.getPoints().getLength() == 0) {
                showError(questionRow.getQuestion());
                result = false;
            }
        }
        if (categoryName.getLength() == 0) {
            showError(categoryName);
            result = false;
        }
        return result;
    }

    private static void showError(TextArea area) {
        area.getStyleClass().add("error");
    }

    private static void showError(TextField field) {
        field.getStyleClass().add("error");
    }
}
