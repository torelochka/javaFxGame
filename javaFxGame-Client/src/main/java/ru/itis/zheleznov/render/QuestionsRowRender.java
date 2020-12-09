package ru.itis.zheleznov.render;

import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
                    System.out.println("hello");
                    HBox hBox = new HBox();
                    hBox.setFillHeight(true);
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    QuestionRender.render(item, hBox);

                    VBox vBox = new VBox();
                    vBox.setMaxWidth(300);
                    //hBox.setMaxWidth(500);
                    hBox.getChildren().add(vBox);
                    setGraphic(hBox);
                }
            }
        };
    }
}
