package ru.itis.zheleznov.listeners;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class PointsFieldListener implements ChangeListener<String> {

    private final TextField pointsField;

    public PointsFieldListener(TextField pointsField) {
        this.pointsField = pointsField;
    }


    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (!newValue.matches("\\d*")) {
            pointsField.setText(newValue.replaceAll("[^\\d]", ""));
        } else if (pointsField.getLength() > 4) {
            pointsField.deletePreviousChar();
        }
    }
}
