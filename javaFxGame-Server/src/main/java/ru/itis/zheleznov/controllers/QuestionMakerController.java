package ru.itis.zheleznov.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.itis.zheleznov.Main;
import ru.itis.zheleznov.listeners.PointsFieldListener;
import ru.itis.zheleznov.models.Question;
import ru.itis.zheleznov.models.QuestionsRow;
import ru.itis.zheleznov.models.RawQuestionRow;
import ru.itis.zheleznov.validators.TextFieldValidator;
import ru.itis.zheleznov.window.WindowManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class QuestionMakerController implements Initializable {

    public Button addQuestionButton;
    public TextField categoryName;
    public TextField points;
    public TextArea question;
    public VBox vBoxPane;
    public Button createButton;

    public List<RawQuestionRow> rawQuestion = new ArrayList<>();
    private static int index = 2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        points.textProperty().addListener(new PointsFieldListener(points));

        rawQuestion.add(RawQuestionRow.builder()
                .question(question)
                .points(points)
                .build());
    }


    public void addQuestion(ActionEvent event) {
        //TODO проверка на то, что "Очки" - число
        //TODO добавить placeholder

        if(rawQuestion.size() == 4) {
            addQuestionButton.setDisable(true);
        }

        VBox vBox = new VBox();
        if (index % 2 != 0) {
            vBox.getStyleClass().add("questionBoxOdd");
        }
        vBox.setAlignment(Pos.TOP_CENTER);

        HBox questionBox = new HBox();
        questionBox.setAlignment(Pos.CENTER);
        TextArea questionArea = new TextArea();
        //TODO раскрасить задние части vBox
        questionArea.setWrapText(true);
        questionArea.setPrefSize(290, 100);
        questionArea.setMinSize(290, 100);
        Label questionLabel = new Label("Вопрос " + index++);
        questionLabel.setLabelFor(questionArea);
        HBox.setMargin(questionArea, new Insets(20,0,20,20));
        questionBox.getChildren().addAll(questionLabel, questionArea);

        HBox pointsBox = new HBox();
        pointsBox.setAlignment(Pos.CENTER);
        TextField pointsField = new TextField();
        Label pointsLabel = new Label("Очки");
        pointsLabel.setLabelFor(pointsField);
        HBox.setMargin(pointsField, new Insets(20,0,20,20));
        pointsBox.getChildren().addAll(pointsLabel, pointsField);

        pointsField.textProperty().addListener(new PointsFieldListener(pointsField));

        vBox.getChildren().addAll(questionBox, pointsBox);
        rawQuestion.add(RawQuestionRow.builder()
                .question(questionArea)
                .points(pointsField)
                .build());
        vBoxPane.getChildren().add(vBox);
    }

    //TODO валидатор

    public void create(ActionEvent event) throws IOException {
        if (TextFieldValidator.validateQuestionMaker(rawQuestion, categoryName)) {
            ArrayList<Question> questions = new ArrayList<>();
            rawQuestion.forEach(r -> {
                questions.add(new Question(r.getQuestion().getText(), new Button(), Integer.parseInt(r.getPoints().getText())));
            });

            PreLobbyController.observableList.add(new QuestionsRow(categoryName.getText(), new Button(), questions));

            index = 2;

            WindowManager.renderWindow(Main.primaryStage, "Pre lobby room", "preLobbyRoom.fxml", 450, 650);
        }
    }
}
