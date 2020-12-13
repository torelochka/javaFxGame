package ru.itis.zheleznov.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class SocketService {
    public static Socket getSocketConnection() {
        Properties properties = new Properties();
        try {
            properties.load(SocketService.class.getResourceAsStream("socket.properties"));
            return new Socket(properties.getProperty("server.host"), Integer.parseInt(properties.getProperty("server.port")));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static ServerSocket getServerSocketConnection() {
        Properties properties = new Properties();
        try {
            properties.load(SocketService.class.getResourceAsStream("/socket.properties"));
            int port = Integer.parseInt(properties.getProperty("server.port"));
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server deploy on " + port + " " + serverSocket.getInetAddress());
            return serverSocket;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
