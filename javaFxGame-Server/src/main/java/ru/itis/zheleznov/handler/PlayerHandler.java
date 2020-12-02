package ru.itis.zheleznov.handler;

import javafx.application.Platform;
import ru.itis.zheleznov.controllers.LobbyController;
import ru.itis.zheleznov.models.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PlayerHandler extends Thread {

    private final Socket player;
    private final ServerSocket serverSocket;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;

    public PlayerHandler(Socket player, ServerSocket serverSocket) {
        this.player = player;
        this.serverSocket = serverSocket;
        try {
            this.outputStream = new DataOutputStream(player.getOutputStream());
            this.inputStream = new DataInputStream(player.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Platform.runLater(() -> {
            try {
                String nickname = inputStream.readUTF();
                LobbyController.observableList.add(new Player(nickname, 0, player));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void sendMsg(String msg) {
        try {
            outputStream.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
