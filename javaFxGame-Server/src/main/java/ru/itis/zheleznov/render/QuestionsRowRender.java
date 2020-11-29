package ru.itis.zheleznov.render;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Callback;
import ru.itis.zheleznov.models.QuestionsRow;

public class QuestionsRowRender implements Callback<ListView<QuestionsRow>, ListCell<QuestionsRow>> {

    @Override
    public ListCell<QuestionsRow> call(ListView<QuestionsRow> param) {
        return new ListCell<QuestionsRow>() {
            @Override
            protected void updateItem(QuestionsRow item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(null);
                setText(null);
                if (!empty) {
                    HBox hBox = new HBox();
                    hBox.setFillHeight(true);
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    QuestionRender.render(item.getCategoryName(), hBox, true);
                    item.getQuestions().forEach(q -> QuestionRender.render(String.valueOf(q.getPoints()), hBox, false));

                    VBox vBox = new VBox();
                    vBox.setMaxWidth(300);
                    //hBox.setMaxWidth(500);
                    hBox.getChildren().add(vBox);
                    hBox.getChildren().add(GlyphsDude.createIconButton(FontAwesomeIcons.MINUS_CIRCLE, "dd", "20px", "10px", ContentDisplay.LEFT));
                    setGraphic(hBox);
                }
            }
        };
    }
}
