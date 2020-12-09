package ru.itis.zheleznov.render;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import ru.itis.zheleznov.models.QuestionsRow;

public class QuestionRender {

    static void render(QuestionsRow questionsRow, HBox hBox) {
        Button button = questionsRow.getButton();
        configButton(button, questionsRow.getCategoryName());
        button.getStyleClass().add("question-category");
        HBox.setMargin(button, new Insets(0, 5, 0, 0));
        hBox.getChildren().add(button);
        questionsRow.getQuestions().forEach(q -> {
            Button Qbutton = q.getButton();
            configButton(Qbutton, String.valueOf(q.getPoints()));
            Qbutton.getStyleClass().add("question-button");
            HBox.setMargin(Qbutton, new Insets(0, 5, 0, 0));
            hBox.getChildren().add(Qbutton);
        });
    }

    private static void configButton(Button button, String text) {
        button.setText(text);
        CornerRadii cr = new CornerRadii(0);
        Color color = Color.web("#000051");
        button.setBackground(new Background(new BackgroundFill(color, cr, Insets.EMPTY)));
        button.setPadding(new Insets(15,15,15,15));
        button.setTextFill(Paint.valueOf("#fcfcfc"));
        button.setDisable(true);
    }
}
