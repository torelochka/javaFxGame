package ru.itis.zheleznov.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import ru.itis.zheleznov.model.Message;
import ru.itis.zheleznov.render.MessageRender;
import ru.itis.zheleznov.service.ClientSocketService;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClientQuestionController implements Initializable {

    public TextField textField;
    public Button sendButton;
    public Label question;
    public Label timer;
    public ListView<Message> listView;
    public static ObservableList<Message> messages = FXCollections.observableList(new ArrayList<>());
    public static Timeline timeline;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setEditable(true);
        listView.setItems(messages);
        listView.setCellFactory(new MessageRender());

        question.setText(ClientGameController.chosenQuestion.getQuestionName());

        ClientSocketService.readAnswer(LoginController.player, messages, textField, sendButton, question);

        startTimer();
    }

    private void startTimer() {
        int[] time = {30};
        timeline = new Timeline(
                new KeyFrame( Duration.millis(1000),
                        ae -> {
                            time[0]--;
                            timer.setText(time[0] + "");
                        }
                )
        );
        timeline.setCycleCount(30);
        timeline.play();
    }

    public void sendMsg(ActionEvent event) {
        if (textField.getText().length() > 0) {
            ClientSocketService.sendAnswer(LoginController.player, textField.getText().trim());
            textField.setText("");
        }
    }
}
