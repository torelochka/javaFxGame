package ru.itis.zheleznov.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.itis.zheleznov.Main;
import ru.itis.zheleznov.model.Player;
import ru.itis.zheleznov.protocol.Protocol;
import ru.itis.zheleznov.service.ClientSocketService;
import ru.itis.zheleznov.utils.Validation;
import ru.itis.zheleznov.window.WindowManager;

import java.io.IOException;

public class LoginController {

    public TextField loginField;
    public Button loginButton;
    public static Player player;

    public void login(ActionEvent event) {
        //TODO вывод красиво окон, если сервер не найден или переполнен
        if (Validation.notEmpty(loginField)) {
            player = new Player(loginField.getText(), ClientSocketService.getSocketConnection());
            ClientSocketService.sendPlayer(player);
            WindowManager.renderWindow(Main.primaryStage, "lobby", "clientLobby.fxml", 450, 650);
        }
    }
}
