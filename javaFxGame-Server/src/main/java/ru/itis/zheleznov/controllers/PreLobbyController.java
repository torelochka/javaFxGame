package ru.itis.zheleznov.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import ru.itis.zheleznov.Main;
import ru.itis.zheleznov.models.Host;
import ru.itis.zheleznov.models.QuestionsRow;
import ru.itis.zheleznov.render.QuestionsRowRender;
import ru.itis.zheleznov.service.SocketService;
import ru.itis.zheleznov.window.WindowManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PreLobbyController implements Initializable {

    public ListView<QuestionsRow> listView;
    public Button startButton;
    public Button addButton;
    public static ObservableList<QuestionsRow> observableList = FXCollections.observableList(new ArrayList<>());
    public static Host host;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setEditable(true);
        listView.setItems(observableList);
        listView.setCellFactory(new QuestionsRowRender());
    }

    public void addQuestion(ActionEvent event) throws IOException {
        if (observableList.size() > 4) {
            //TODO запретить клик на кнопку
            addButton.setDisable(true);
        } else {
            WindowManager.renderWindow(Main.primaryStage, "Question maker", "questionMakerRoom.fxml", 450, 650);
        }
    }


    public void start(ActionEvent event) {
        //TODO выводить алерт если список вопросв пуст
        if (observableList.size() > 0) {
            host = Host.builder()
                    .questions(observableList)
                    .serverSocket(SocketService.getServerSocketConnection())
                    .build();
            WindowManager.renderWindow(Main.primaryStage, "Lobby", "hostLobby.fxml", 450, 650);
        }
    }
}
