package ru.itis.zheleznov.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import ru.itis.zheleznov.Main;
import ru.itis.zheleznov.models.Player;
import ru.itis.zheleznov.protocol.Protocol;
import ru.itis.zheleznov.render.PlayersRender;
import ru.itis.zheleznov.service.GameServer;
import ru.itis.zheleznov.window.WindowManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LobbyController implements Initializable {

    public Button startButton;
    public ListView<Player> listView;
    public static ObservableList<Player> observableList = FXCollections.observableList(new ArrayList<>());
    public static GameServer server;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setEditable(true);
        listView.setItems(observableList);
        listView.setCellFactory(new PlayersRender());
        server = new GameServer(PreLobbyController.host);
        server.start();
    }

    public void startGame(ActionEvent event) {
        System.out.println(observableList.size());
        if (observableList.size() >= 2) {
            server.startGame();
            WindowManager.renderGameWindow(Main.primaryStage);
        }
    }
}
