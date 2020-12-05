package ru.itis.zheleznov.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import ru.itis.zheleznov.model.Message;
import ru.itis.zheleznov.models.QuestionsRow;
import ru.itis.zheleznov.render.PlayerRender;
import ru.itis.zheleznov.render.QuestionsRowRender;
import ru.itis.zheleznov.service.ClientSocketService;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClientGameController implements Initializable {

    public ListView<QuestionsRow> questionsList;
    public ListView<Message> listView;
    public TextField textField;
    public Button sendButton;
    public static ObservableList<Message> players = FXCollections.observableList(new ArrayList<>());
    public static ObservableList<QuestionsRow> questions = FXCollections.observableList(new ArrayList<>());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setEditable(true);
        listView.setItems(players);
        listView.setCellFactory(new PlayerRender());

        questionsList.setEditable(true);
        questionsList.setItems(questions);
        questionsList.setCellFactory(new QuestionsRowRender());

        ClientSocketService.readQuestions(LoginController.player, questions);
        ClientSocketService.readAnswer(LoginController.player, players);
    }

    public void sendMsg(ActionEvent event) {
        //TODO проверка на непустое поле
        ClientSocketService.sendAnswer(LoginController.player, textField.getText());
    }
}
