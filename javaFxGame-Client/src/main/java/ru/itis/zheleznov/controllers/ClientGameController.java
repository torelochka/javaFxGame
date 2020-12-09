package ru.itis.zheleznov.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import ru.itis.zheleznov.Main;
import ru.itis.zheleznov.dto.QuestionDto;
import ru.itis.zheleznov.model.Message;
import ru.itis.zheleznov.models.Question;
import ru.itis.zheleznov.models.QuestionsRow;
import ru.itis.zheleznov.render.MessageRender;
import ru.itis.zheleznov.render.QuestionsRowRender;
import ru.itis.zheleznov.service.ClientSocketService;
import ru.itis.zheleznov.window.WindowManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClientGameController implements Initializable {

    public ListView<QuestionsRow> questionsList;
    public ListView<Message> listView;
    public TextField textField;
    public static ListView<QuestionsRow> questionListCopy;
    public Button sendButton;
    public static ObservableList<Message> players = FXCollections.observableList(new ArrayList<>());
    public static ObservableList<QuestionsRow> questions = FXCollections.observableList(new ArrayList<>());
    public static QuestionDto chosenQuestion;

    public static void nextRound(QuestionDto questionDto) {
        Platform.runLater(() ->
            WindowManager.renderWindow(Main.primaryStage, "Game", "question.fxml", 800, 900));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        questionListCopy = questionsList;
        listView.setEditable(true);
        listView.setItems(players);
        listView.setCellFactory(new MessageRender());

        textField.setDisable(true);

        questionsList.setEditable(true);
        questionsList.setItems(questions);
        questionsList.setCellFactory(new QuestionsRowRender());

        ClientSocketService.readQuestions(LoginController.player, questions);
        ClientSocketService.waitMove(LoginController.player, questions);
    }

    public void sendMsg(ActionEvent event) {
        if (textField.getText().length() > 0) {
            ClientSocketService.sendAnswer(LoginController.player, textField.getText());
            textField.setText("");
        }
    }

    public static void sendChoose(Question question) {
        ClientSocketService.sendChoose(LoginController.player, QuestionDto.from(question));
        //ClientSocketService.readQuestions(LoginController.player, questions);
    }
}
