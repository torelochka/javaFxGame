package ru.itis.zheleznov.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import ru.itis.zheleznov.Main;
import ru.itis.zheleznov.models.QuestionsRow;
import ru.itis.zheleznov.models.Question;
import ru.itis.zheleznov.render.QuestionsRowRender;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setEditable(true);
        listView.setItems(observableList);
        listView.setCellFactory(new QuestionsRowRender());
    }

    public void addQuestion(ActionEvent event) throws IOException {
        if (observableList.size() > 4) {
            System.out.println("sss");
            addButton.setDisable(true);
        } else {
            WindowManager.renderQuestionMakerWindow(Main.primaryStage);
        }
    }


}
