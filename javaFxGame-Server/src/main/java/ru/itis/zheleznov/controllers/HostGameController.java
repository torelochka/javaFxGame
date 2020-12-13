package ru.itis.zheleznov.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import ru.itis.zheleznov.dto.Answer;
import ru.itis.zheleznov.dto.QuestionDto;
import ru.itis.zheleznov.models.Message;
import ru.itis.zheleznov.service.GameServer;

import java.net.URL;
import java.util.ResourceBundle;

public class HostGameController implements Initializable {

    public HBox playersField;
    public Button startButton;
    public Button yesButton;
    public Button noButton;
    public Label questionLabel;
    public Label timerLabel;
    public static Timeline timeline;
    public static GameServer server;
    public static QuestionDto question;
    public static Button staticNoButton;
    public static Button staticYesButton;
    public static Label staticQuestion;
    public static Message staticMessage;
    public static Label staticTimerLabel;

    public static void nextRound(QuestionDto questionDto) {
        PreLobbyController.observableList.forEach(qr -> qr.getQuestions().removeIf(q -> QuestionDto.from(q).equals(questionDto)));
        PreLobbyController.observableList.removeIf(qr -> qr.getQuestions().isEmpty());
        question = questionDto;
        Platform.runLater(() -> staticQuestion.setText(questionDto.getQuestionName()));
        server.readAnswers();
        startTimer();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        staticQuestion = questionLabel;
        staticNoButton = noButton;
        staticYesButton = yesButton;
        staticTimerLabel = timerLabel;

        server = LobbyController.server;
        server.sendQuestions(PreLobbyController.observableList);
    }

    public static void checkAnswer(Message message) {
        server.stopAnswer();
        Platform.runLater(() -> {
            timeline.stop();
            staticYesButton.setVisible(true);
            staticNoButton.setVisible(true);
            staticMessage = message;
            staticQuestion.setText("Ответ: " + message.getText());
        });
    }

    private static void startTimer() {
        int[] time = {30};
        timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(1000),
                        ae -> {
                            time[0]--;
                            staticTimerLabel.setText(time[0] + "");
                        }
                )
        );

        timeline.setCycleCount(30);
        timeline.play();
    }

    public void start(ActionEvent event) {
        startButton.setVisible(false);
        server.nextMove();
    }

    public void check(ActionEvent event) {
        boolean answer = true;
        yesButton.setVisible(false);
        noButton.setVisible(false);

        if (event.getSource().equals(noButton)) {
            answer = false;
            server.readAnswers();
            Platform.runLater(() -> questionLabel.setText(question.getQuestionName()));
            timeline.play();
        }
        LobbyController.server.checkAnswer(new Answer(answer, staticMessage));

        if (event.getSource().equals(yesButton)) {
            server.waitChoose();
        } else {
            //server.readAnswers();
        }
    }
}
