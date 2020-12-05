package ru.itis.zheleznov.service;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import ru.itis.zheleznov.controllers.LobbyController;
import ru.itis.zheleznov.controllers.LoginController;
import ru.itis.zheleznov.dto.PlayerDto;
import ru.itis.zheleznov.model.Message;
import ru.itis.zheleznov.model.Player;
import ru.itis.zheleznov.models.QuestionsRow;
import ru.itis.zheleznov.protocol.Protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.List;
import java.util.Properties;

public class ClientSocketService {
    public static Socket getSocketConnection() {
        Properties properties = new Properties();
        try {
            properties.load(ClientSocketService.class.getResourceAsStream("/socket.properties"));
            return new Socket(properties.getProperty("server.host"), Integer.parseInt(properties.getProperty("server.port")));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void sendPlayer(Player player) {
        try {
            Protocol.write(Protocol.NEW_PLAYER, player.getLogin(), player.getSocket().getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void waitStartGame(Player player) {
        new Thread(() -> {
            while (true) {
                try {
                    if (Protocol.read(Protocol.START, player.getSocket().getInputStream())) {
                        LobbyController.start();
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void sendAnswer(Player player, String msg) {
        new Thread(() -> {
            Message message = new Message(PlayerDto.from(player), msg);
            try {
                Protocol.write(Protocol.SEND_ANSWER, message, player.getSocket().getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void readAnswer(Player player, ObservableList<Message> list) {
        new Thread(() -> {
            while (true) {
                Message message = null;
                try {
                    message = (Message) Protocol.read(Protocol.SEND_ANSWER, player.getSocket().getInputStream(), Message.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (message != null) {
                    System.out.println("hello");
                    Message finalMessage = message;
                    Platform.runLater(() -> list.add(finalMessage));
                }
            }
        }).start();
    }

    public static void readQuestions(Player player, ObservableList<QuestionsRow> list) {
        while (true) {
            try {
                List<QuestionsRow> questions = Protocol.readQuestions(Protocol.QUESTIONS, player.getSocket().getInputStream());
                System.out.println(questions);
                if (questions != null) {
                    Platform.runLater(() -> questions.forEach(q -> list.add(q)));
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
