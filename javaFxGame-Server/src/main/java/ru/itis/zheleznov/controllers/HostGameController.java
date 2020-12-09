package ru.itis.zheleznov.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import ru.itis.zheleznov.dto.QuestionDto;
import ru.itis.zheleznov.models.Player;
import ru.itis.zheleznov.service.GameServer;
import ru.itis.zheleznov.service.SocketService;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class HostGameController implements Initializable {

    public HBox playersField;
    public Button startButton;
    public static GameServer server;
    public static QuestionDto question;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        server = LobbyController.server;
        server.sendQuestions(PreLobbyController.observableList);
    }

    public void start(ActionEvent event) {
        startButton.setVisible(false);
        server.nextMove();
    }
}
