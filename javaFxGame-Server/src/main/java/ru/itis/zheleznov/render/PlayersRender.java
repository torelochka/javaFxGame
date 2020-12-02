package ru.itis.zheleznov.render;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Callback;
import ru.itis.zheleznov.models.Player;

public class PlayersRender implements Callback<ListView<Player>, ListCell<Player>> {

    @Override
    public ListCell<Player> call(ListView<Player> param) {
        return new ListCell<Player>() {
            @Override
            protected void updateItem(Player item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(null);
                setText(null);
                if (!empty) {
                    HBox hBox = new HBox();
                    hBox.setFillHeight(true);
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    Label label = new Label("Player: " + item.getNickname());
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
