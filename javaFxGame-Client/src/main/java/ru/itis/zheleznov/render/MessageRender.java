package ru.itis.zheleznov.render;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Callback;
import ru.itis.zheleznov.controllers.ClientGameController;
import ru.itis.zheleznov.model.Message;
import ru.itis.zheleznov.models.Player;

public class MessageRender implements Callback<ListView<Message>, ListCell<Message>> {

    @Override
    public ListCell<Message> call(ListView<Message> param) {
        return new ListCell<Message>() {
            @Override
            protected void updateItem(Message item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(null);
                setText(null);
                if (!empty) {
                    HBox hBox = new HBox();
                    hBox.setFillHeight(true);
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    Label label = new Label("Player " + item.getPlayer().getLogin() + " Points " + item.getPlayer().getPoints() + ": " + item.getText());
                    CornerRadii cr = new CornerRadii(0);
                    Color color = Color.web("#000051");

                    label.setBackground(new Background(new BackgroundFill(color, cr, Insets.EMPTY)));
                    label.getStyleClass().add("player");
                    label.setPadding(new Insets(15,15,15,15));
                    label.setTextFill(Paint.valueOf("#fcfcfc"));

                    HBox.setMargin(label, new Insets(0, 5, 0, 0));
                    hBox.getChildren().add(label);

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