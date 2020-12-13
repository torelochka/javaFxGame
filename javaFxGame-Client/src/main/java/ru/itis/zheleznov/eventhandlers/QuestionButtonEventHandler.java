package ru.itis.zheleznov.eventhandlers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ru.itis.zheleznov.controllers.ClientGameController;
import ru.itis.zheleznov.controllers.LoginController;
import ru.itis.zheleznov.dto.QuestionDto;
import ru.itis.zheleznov.models.Question;
import ru.itis.zheleznov.service.ClientSocketService;

public class QuestionButtonEventHandler implements EventHandler<ActionEvent> {

    private final Question question;

    public QuestionButtonEventHandler(Question question) {
        this.question = question;
    }

    @Override
    public void handle(ActionEvent event) {
        ClientSocketService.sendChoose(LoginController.player, QuestionDto.from(question));
    }
}
