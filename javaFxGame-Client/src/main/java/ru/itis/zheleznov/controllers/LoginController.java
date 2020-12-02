package ru.itis.zheleznov.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.itis.zheleznov.Main;
import ru.itis.zheleznov.model.Player;
import ru.itis.zheleznov.service.SocketService;
import ru.itis.zheleznov.utils.Validation;
import ru.itis.zheleznov.window.WindowManager;

import java.io.DataOutputStream;
import java.io.IOException;

public class LoginController {

    public TextField loginField;
    public Button loginButton;
    public static Player player;

    public void login(ActionEvent event) {
        if (Validation.notEmpty(loginField)) {
            player = Player.builder()
                    .login(loginField.getText())
                    .socket(SocketService.getSocketConnection())
                    .build();
            WindowManager.renderLobbyWindow(Main.primaryStage);
        }
    }
}
