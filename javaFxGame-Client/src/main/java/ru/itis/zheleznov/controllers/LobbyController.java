package ru.itis.zheleznov.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import ru.itis.zheleznov.Main;
import ru.itis.zheleznov.model.Player;
import ru.itis.zheleznov.service.SocketService;
import ru.itis.zheleznov.window.WindowManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LobbyController implements Initializable {

    public static ObservableList<Player> observableList = FXCollections.observableList(new ArrayList<>());
    private static Player player = LoginController.player;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SocketService.sendStringToServer(player.getSocket(), player.getLogin());
        SocketService.waitStartGame(player.getSocket());
    }

    public static void start() {
        Platform.runLater(() -> WindowManager.renderGameWindow(Main.primaryStage));
    }
}
