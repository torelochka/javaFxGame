package ru.itis.zheleznov.render;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class QuestionRender {

    static void render(String title, HBox hBox, boolean isCategory) {
        Button button = new Button(title);
        CornerRadii cr = new CornerRadii(0);
        Color color = Color.web("#000051");

        button.setBackground(new Background(new BackgroundFill(color, cr, Insets.EMPTY)));
        if (isCategory) {
            button.getStyleClass().add("question-category");
        }
        else {
            button.getStyleClass().add("question-button");
        }
        button.setPadding(new Insets(15,15,15,15));
        button.setTextFill(Paint.valueOf("#fcfcfc"));

        HBox.setMargin(button, new Insets(0, 5, 0, 0));
        hBox.getChildren().add(button);

    }
}
