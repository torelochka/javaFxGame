package ru.itis.zheleznov.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import ru.itis.zheleznov.models.QuestionsRow;
import ru.itis.zheleznov.models.Question;
import ru.itis.zheleznov.render.QuestionsRowRender;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class PreLobbyController implements Initializable {

    public ListView<QuestionsRow> listView;
    public Button startButton;
    public Button addButton;

    private static ObservableList<QuestionsRow> observableList = FXCollections.observableList(new ArrayList<>());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setEditable(true);
        listView.setItems(observableList);
        listView.setCellFactory(new QuestionsRowRender());
    }

    public void addQuestion(ActionEvent event) {
        ArrayList<Question> arrayList = new ArrayList<>();
        arrayList.add(Question.builder()
                .questionName("test1")
                .points(100)
                .build());
        arrayList.add(Question.builder()
                .questionName("test2")
                .points(200)
                .build());
        arrayList.add(Question.builder()
                .questionName("test3")
                .points(300)
                .build());
        arrayList.add(Question.builder()
                .questionName("test4")
                .points(400)
                .build());
        arrayList.add(Question.builder()
                .questionName("test5")
                .points(500)
                .build());
        observableList.add(QuestionsRow.builder()
                .categoryName("testtesttesttesttesttesttesttesttest")
                .questions(arrayList)
                .build());
    }


}
