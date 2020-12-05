package ru.itis.zheleznov.handler;

import javafx.application.Platform;
import lombok.SneakyThrows;
import ru.itis.zheleznov.controllers.LobbyController;
import ru.itis.zheleznov.models.Message;
import ru.itis.zheleznov.models.Player;
import ru.itis.zheleznov.models.QuestionsRow;
import ru.itis.zheleznov.protocol.Protocol;
import ru.itis.zheleznov.service.GameServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigestSpi;
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
        String login = (String) Protocol.read(Protocol.NEW_PLAYER, inputStream, String.class);
        if (login != null) {
            Platform.runLater(() -> LobbyController.observableList.add(new Player(login, 0, player)));
        }
    }

    public void sendStart() {
        Protocol.write(Protocol.START, outputStream);
    }

    public void readAnswers() {
        new Thread(() -> {
            Message message = (Message) Protocol.read(Protocol.SEND_ANSWER, inputStream, Message.class);
            if (message != null) {
                gameServer.broadcast(message);
            }
        }).start();
    }

    public void sendAnswer(Message message) {
        new Thread(() -> {
            Protocol.write(Protocol.SEND_ANSWER, message, outputStream);
        }).start();
    }

    public void sendQuestions(List<QuestionsRow> questionsRows) {
        new Thread(() -> {
            Protocol.write(Protocol.QUESTIONS, questionsRows, outputStream);
        }).start();
    }
}
