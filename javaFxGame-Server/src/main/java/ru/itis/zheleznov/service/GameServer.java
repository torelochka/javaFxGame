package ru.itis.zheleznov.service;

import lombok.SneakyThrows;
import ru.itis.zheleznov.handler.PlayerHandler;
import ru.itis.zheleznov.models.Host;
import ru.itis.zheleznov.models.Message;
import ru.itis.zheleznov.models.QuestionsRow;
import sun.awt.windows.ThemeReader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
        players.forEach(PlayerHandler::sendStart);
    }

    public void sendQuestions(List<QuestionsRow> questionsRows) {
        new Thread(() -> {
            players.forEach(p -> p.sendQuestions(questionsRows));
        }).start();
    }

    public void readAnswers() {
        new Thread(() -> {
            players.forEach(p -> p.readAnswers());
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
}
