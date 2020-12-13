package ru.itis.zheleznov.service;

import ru.itis.zheleznov.controllers.HostGameController;
import ru.itis.zheleznov.controllers.PreLobbyController;
import ru.itis.zheleznov.dto.Answer;
import ru.itis.zheleznov.dto.QuestionDto;
import ru.itis.zheleznov.handler.PlayerHandler;
import ru.itis.zheleznov.models.Host;
import ru.itis.zheleznov.models.Message;
import ru.itis.zheleznov.models.QuestionsRow;

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
    private static int playerIndex = -1;

    public GameServer(Host host) {
        this.host = host;
        this.serverSocket = host.getServerSocket();
    }

    public void startGame() {
        new Thread(() -> players.forEach(PlayerHandler::sendStart)).start();

    }

    public void sendQuestions(List<QuestionsRow> questionsRows) {
        players.forEach(p -> p.sendQuestions(questionsRows));
        System.out.println("send success");
    }

    public void readAnswers() {
        new Thread(() -> {
            players.forEach(p -> p.inGame = true);
            System.out.println("start Read");
            players.forEach(PlayerHandler::readAnswers);
        }).start();
    }

    public void stopAnswer() {
        new Thread(() -> {
            players.forEach(p -> p.inGame = false);
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
        System.out.println("broadcast");
        players.forEach(p -> p.sendAnswer(message));
    }

    public void nextMove() {
        if (playerIndex == -1) {
            Random random = new Random();
            playerIndex = random.nextInt(players.size());
        }
        for (int i = 0; i < players.size(); i++) {
            players.get(i).move(i == playerIndex);
        }
        waitChoose();
    }

    public void waitChoose() {
        players.get(playerIndex).waitChoose();
    }

    public void nextRound(QuestionDto questionDto) {
        players.forEach(p -> p.nextRound(questionDto));
    }

    public void checkAnswer(Answer answer) {
        players.forEach(p -> p.checkAnswer(answer));
    }
}
