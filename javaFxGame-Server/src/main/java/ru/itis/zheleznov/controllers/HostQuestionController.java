package ru.itis.zheleznov.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import ru.itis.zheleznov.models.Message;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class HostQuestionController implements Initializable {

    public Label question;
    public Label timer;

    public Button noButton;
    public Button yesButton;
    public static Button staticNoButton;
    public static Button staticYesButton;
    public static Label staticQuestion;
    public static int durationInSeconds;
    public static Timeline timeline;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        staticQuestion = question;
        staticNoButton = noButton;
        staticYesButton = yesButton;

        question.setText(HostGameController.question.getQuestionName());
        LobbyController.server.readAnswers();

        durationInSeconds = 30;
        timer.setText(durationInSeconds + "");
        startTimer();
    }

    private void startTimer() {
        int[] time = {durationInSeconds};
        timeline = new Timeline (
                new KeyFrame(
                        Duration.millis(1000),
                        ae -> {
                            time[0]--;
                            timer.setText(time[0] + "");
                        }
                )
        );

        timeline.setCycleCount(durationInSeconds); //Ограничим число повторений
        timeline.play(); //Запускаем
    }

    public static void checkAnswer(Message message) {
        Platform.runLater(() -> {
            timeline.stop();
            staticYesButton.setVisible(true);
            staticNoButton.setVisible(true);
            staticQuestion.setText("Ответ: " + message.getText());
        });
    }


    public void check(ActionEvent event) {
        boolean answer = true;

        if (event.getSource().equals(noButton)) {
            answer = false;
        }
        LobbyController.server.checkAnswer(answer);
    }
}
