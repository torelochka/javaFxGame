package ru.itis.zheleznov.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import ru.itis.zheleznov.models.Player;
import ru.itis.zheleznov.service.GameServer;
import ru.itis.zheleznov.service.SocketService;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HostGameController implements Initializable {

    public ListView listView;
    public HBox playersField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LobbyController.server.sendQuestions(PreLobbyController.observableList);
        LobbyController.server.readAnswers();
    }
}
