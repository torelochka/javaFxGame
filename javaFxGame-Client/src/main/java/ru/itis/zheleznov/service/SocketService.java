package ru.itis.zheleznov.service;

import ru.itis.zheleznov.controllers.LobbyController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Properties;

public class SocketService {
    public static Socket getSocketConnection() {
        Properties properties = new Properties();
        try {
            properties.load(SocketService.class.getResourceAsStream("/socket.properties"));
            return new Socket(properties.getProperty("server.host"), Integer.parseInt(properties.getProperty("server.port")));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    //TODO в протокол?
    public static void sendStringToServer(Socket socket, String msg) {
        try {
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void waitStartGame(Socket socket) {
        new Thread(() -> {
            while(true) {
                try {
                    DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                    //TODO вынести в enum
                    if (inputStream.readUTF().equals("/start")){
                        LobbyController.start();
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
