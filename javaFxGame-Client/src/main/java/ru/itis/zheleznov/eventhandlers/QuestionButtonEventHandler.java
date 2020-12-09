package ru.itis.zheleznov.eventhandlers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import ru.itis.zheleznov.controllers.ClientGameController;
import ru.itis.zheleznov.models.Question;

public class QuestionButtonEventHandler implements EventHandler<ActionEvent> {

    private final Question question;

    public QuestionButtonEventHandler(Question question) {
        this.question = question;
    }

    @Override
    public void handle(ActionEvent event) {
        ClientGameController.sendChoose(question);
    }
}
