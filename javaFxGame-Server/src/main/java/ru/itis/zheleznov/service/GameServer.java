package ru.itis.zheleznov.service;

import javafx.application.Platform;
import ru.itis.zheleznov.controllers.LobbyController;
import ru.itis.zheleznov.handler.PlayerHandler;
import ru.itis.zheleznov.models.Host;
import ru.itis.zheleznov.models.Player;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
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
        players.forEach(c -> c.sendMsg("/start"));
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket player = serverSocket.accept();
                System.out.println("new Connection");
                PlayerHandler playerHandler = new PlayerHandler(player, serverSocket);
                playerHandler.start();
                players.add(playerHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
