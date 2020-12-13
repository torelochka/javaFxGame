package ru.itis.zheleznov.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import ru.itis.zheleznov.dto.Answer;
import ru.itis.zheleznov.dto.PlayerDto;
import ru.itis.zheleznov.dto.QuestionDto;
import ru.itis.zheleznov.model.Message;
import ru.itis.zheleznov.models.QuestionsRow;
import ru.itis.zheleznov.render.MessageRender;
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
    public Label questionLabel;
    public Label timerLabel;
    public static ObservableList<Message> messages = FXCollections.observableList(new ArrayList<>());
    public static ObservableList<QuestionsRow> questions = FXCollections.observableList(new ArrayList<>());
    public static QuestionDto chosenQuestion;
    public static Label staticQuestionLabel;
    public static Label staticTimerLabel;
    private static TextField staticTextField;
    private static Button staticSendButton;
    private static Timeline timeline;
    private static int oldTime;
    private static int questionsCount = -1;
    public static boolean move = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        staticQuestionLabel = questionLabel;
        staticTimerLabel = timerLabel;
        staticTextField = textField;
        staticSendButton = sendButton;

        listView.setEditable(true);
        listView.setItems(messages);
        listView.setCellFactory(new MessageRender());

        textField.setDisable(true);

        questionsList.setEditable(true);
        questionsList.setItems(questions);
        questionsList.setCellFactory(new QuestionsRowRender());

        ClientSocketService.readQuestions(LoginController.player, questions);
        ClientSocketService.waitMove(LoginController.player, questions);
    }

    public static void nextRound(QuestionDto questionDto) {
        if (questionsCount == -1) {
            questionsCount = 0;
            questions.forEach(qr -> qr.getQuestions().forEach(q -> questionsCount++));
        }

        Platform.runLater(() -> {
            //questions.forEach(qr -> qr.getQuestions().removeIf(q -> QuestionDto.from(q).equals(questionDto)));
            //TODO во время клика проверять был ли такой вопрос
            questions.forEach(qr -> qr.getQuestions().forEach(q -> q.getButton().setDisable(true)));
            staticQuestionLabel.setText(questionDto.getQuestionName());
            staticTextField.setDisable(false);
            staticSendButton.setDisable(false);
        });

        ClientSocketService.startReadAnswer();
        ClientSocketService.readAnswer(LoginController.player);
        startTimer();

    }

    private static void startTimer() {
        int[] time = {30};
        timeline = new Timeline(
                new KeyFrame(Duration.millis(1000),
                        ae -> {
                            time[0]--;
                            oldTime = time[0];
                            staticTimerLabel.setText(time[0] + "");
                        }
                )
        );
        timeline.setCycleCount(30);
        timeline.play();
        //TODO дейсвие при остановке таймера
    }

    public static void checkAnswer(Message message) {
        Platform.runLater(() -> {
            staticTextField.setDisable(true);
            staticSendButton.setDisable(true);
            timeline.stop();
            staticQuestionLabel.setText("Отвечает игрок " + message.getPlayer().getLogin());
            messages.add(message);
        });

        ClientSocketService.checkAnswer(LoginController.player);
    }

    public static void nextMove(Answer answer) {
        String mess = "";
        ClientSocketService.stopReadAnswer();
        if (!answer.getResult()) {
            mess = "Lost " + ClientGameController.chosenQuestion.getPoints() + "points";
            int[] time = {oldTime};
            timeline = new Timeline(
                    new KeyFrame(Duration.millis(1000),
                            ae -> {
                                time[0]--;
                                oldTime = time[0];
                                staticTimerLabel.setText(time[0] + "");
                            }
                    )
            );
            if (answer.getMessage().getPlayer().equals(PlayerDto.from(LoginController.player))) {
                LoginController.player.setPoints(LoginController.player.getPoints() - ClientGameController.chosenQuestion.getPoints());
            }

            Platform.runLater(() -> {
                staticQuestionLabel.setText(ClientGameController.chosenQuestion.getQuestionName());
                staticTextField.setDisable(false);
                staticSendButton.setDisable(false);
            });

            timeline.setCycleCount(oldTime);
            timeline.play();

            ClientSocketService.startReadAnswer();
        } else {
            questionsCount--;
            mess = "Get " +
                    ClientGameController.chosenQuestion.getPoints() + "points";
            if (answer.getMessage().getPlayer().equals(PlayerDto.from(LoginController.player))) {
                LoginController.player.setPoints(LoginController.player.getPoints() + ClientGameController.chosenQuestion.getPoints());
                answer.getMessage().getPlayer().setPoints(LoginController.player.getPoints());
            }
            if (move) {
                Platform.runLater(() -> {
                    questions.forEach(qr -> qr.getQuestions().forEach(q -> {
                        q.getButton().setDisable(false);
                    }));
                });
            }
            if (questionsCount == 0) {
                Platform.runLater(() -> {
                    staticQuestionLabel.setText("Игра закончена, ваши очки " + LoginController.player.getPoints());
                });
            } else {
                Platform.runLater(() -> {
                    staticQuestionLabel.setText("Wait choose");
                });
                ClientSocketService.nextRound(LoginController.player);
            }
        }
        String finalMess = mess;
        Platform.runLater(() -> {
            messages.add(new Message(PlayerDto.from(LoginController.player), finalMess));
        });
    }

    public void sendMsg(ActionEvent event) {
        if (textField.getText().length() > 0) {
            ClientSocketService.sendAnswer(LoginController.player, textField.getText());
            textField.setText("");
        }
    }
}
