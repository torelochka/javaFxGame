package ru.itis.zheleznov.service;

import javafx.application.Platform;
import lombok.SneakyThrows;
import ru.itis.zheleznov.Main;
import ru.itis.zheleznov.controllers.HostGameController;
import ru.itis.zheleznov.controllers.PreLobbyController;
import ru.itis.zheleznov.dto.QuestionDto;
import ru.itis.zheleznov.handler.PlayerHandler;
import ru.itis.zheleznov.models.Host;
import ru.itis.zheleznov.models.Message;
import ru.itis.zheleznov.models.QuestionsRow;
import ru.itis.zheleznov.protocol.Protocol;
import ru.itis.zheleznov.window.WindowManager;
import sun.awt.windows.ThemeReader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameServer extends Thread {

    private final Host host;
    private final ServerSocket serverSocket;
    //TODO сделать потокобезопасным
    private final List<PlayerHandler> players = new ArrayList<>();

    public GameServer(Host host) {
        this.host = host;
        this.serverSocket = host.getServerSocket();
    }

    public void startGame() {
        new Thread(() -> players.forEach(PlayerHandler::sendStart)).start();

    }

    public void sendQuestions(List<QuestionsRow> questionsRows) {
        new Thread(() -> players.forEach(p -> p.sendQuestions(questionsRows))).start();
    }

    public void readAnswers() {
        new Thread(() -> {
            players.forEach(PlayerHandler::readAnswers);
        }).start();
    }

    @Override
    public void run() {
        while (true) {
            //TODO ограничение в количестве игроков
            try {
                if (players.size() < 5) {
                    Socket player = serverSocket.accept();
                    System.out.println("new Connection");
                    PlayerHandler playerHandler = new PlayerHandler(player, this);
                    playerHandler.start();
                    players.add(playerHandler);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcast(Message message) {
        new Thread(() -> players.forEach(p -> p.sendAnswer(message))).start();
    }

    public void nextMove() {
        Random random = new Random();
        int playerIndex = random.nextInt(players.size());
        System.out.println("player index" + playerIndex);
        for (int i = 0; i < players.size(); i++) {
            players.get(i).move(i == playerIndex);
        }
        waitChoose(players.get(playerIndex));
    }

    private void waitChoose(PlayerHandler playerHandler) {
        playerHandler.waitChoose(PreLobbyController.observableList);
    }

    public void nextRound(QuestionDto questionDto) {
        players.forEach(p -> p.nextRound(questionDto));
    }

    public void checkAnswer(boolean answer) {
        players.forEach(p -> p.checkAnswer(answer));
    }

    public void nextMove(PlayerHandler player) {
        for (PlayerHandler playerHandler : players) {
            playerHandler.move(playerHandler.equals(player));
            if (playerHandler.equals(player)) {
                waitChoose(playerHandler);
            }
        }
    }

    public void questionWindow(QuestionDto questionDto) {
        HostGameController.question = questionDto;
        Platform.runLater(() ->
                WindowManager.renderWindow(Main.primaryStage, "question", "question.fxml", 800, 700));

    }
}
