package ru.itis.zheleznov.controllers;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import ru.itis.zheleznov.Main;
import ru.itis.zheleznov.model.Player;
import ru.itis.zheleznov.service.ClientSocketService;
import ru.itis.zheleznov.window.WindowManager;

import java.net.URL;
import java.util.ResourceBundle;

public class LobbyController implements Initializable {

    private final static Player player = LoginController.player;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ClientSocketService.waitStartGame(player);
    }

    public static void start() {
        Platform.runLater(() -> WindowManager.renderWindow(Main.primaryStage, "Game", "gameClient.fxml", 1050, 760));
    }
}
