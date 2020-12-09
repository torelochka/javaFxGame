package ru.itis.zheleznov.handler;

import javafx.application.Platform;
import ru.itis.zheleznov.controllers.HostGameController;
import ru.itis.zheleznov.controllers.HostQuestionController;
import ru.itis.zheleznov.controllers.LobbyController;
import ru.itis.zheleznov.dto.QuestionDto;
import ru.itis.zheleznov.dto.QuestionsRowDto;
import ru.itis.zheleznov.models.Message;
import ru.itis.zheleznov.models.Player;
import ru.itis.zheleznov.models.Question;
import ru.itis.zheleznov.models.QuestionsRow;
import ru.itis.zheleznov.protocol.Protocol;
import ru.itis.zheleznov.service.GameServer;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.List;

public class PlayerHandler extends Thread {

    private final Socket player;
    private final GameServer gameServer;
    private OutputStream outputStream;
    private InputStream inputStream;

    public PlayerHandler(Socket player, GameServer gameServer) {
        this.player = player;
        this.gameServer = gameServer;
        try {
            this.outputStream = player.getOutputStream();
            this.inputStream = player.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String login = (String) Protocol.read(Protocol.NEW_PLAYER, inputStream, outputStream, String.class);
        if (login != null) {
            Platform.runLater(() -> LobbyController.observableList.add(new Player(login, 0, player)));
        }
    }

    public void sendStart() {
        Protocol.write(Protocol.START, outputStream);
    }

    public void readAnswers() {
        new Thread(() -> {
            while (true) {
                Message message = (Message) Protocol.read(Protocol.SEND_ANSWER, inputStream, outputStream, Message.class);
                if (message != null) {
                    gameServer.broadcast(message);
                    HostQuestionController.checkAnswer(message);
                }
            }
        }).start();
    }

    public void checkAnswer(boolean answer) {
        Protocol.write(Protocol.CHECK_ANSWER, answer, outputStream);
    }

    public void sendAnswer(Message message) {
        new Thread(() -> {
            Protocol.write(Protocol.SEND_ANSWER, message, outputStream);
        }).start();
    }

    public void sendQuestions(List<QuestionsRow> questionsRows) {
        new Thread(() -> {
            Protocol.write(Protocol.PUT_QUESTIONS, QuestionsRowDto.from(questionsRows), outputStream);
        }).start();
    }

    public void waitChoose(List<QuestionsRow> questionsRows) {
        new Thread(() -> {
            while (true) {
                QuestionDto questionDto = Protocol.readQuestion(Protocol.CHOOSE, inputStream, outputStream);
                if (questionDto != null) {
                    gameServer.nextRound(questionDto);
                    gameServer.questionWindow(questionDto);
                    break;
                }
            }
        }).start();
    }

    public void move(Boolean flag) {
        Protocol.write(Protocol.MOVE, flag, outputStream);
    }

    public void nextRound(QuestionDto questionDto) {
        Protocol.write(Protocol.NEXT_ROUND, questionDto, outputStream);
    }
}
