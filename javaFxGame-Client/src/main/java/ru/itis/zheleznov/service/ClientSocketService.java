package ru.itis.zheleznov.service;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.itis.zheleznov.controllers.ClientGameController;
import ru.itis.zheleznov.controllers.ClientQuestionController;
import ru.itis.zheleznov.controllers.LobbyController;
import ru.itis.zheleznov.controllers.LoginController;
import ru.itis.zheleznov.dto.PlayerDto;
import ru.itis.zheleznov.dto.QuestionDto;
import ru.itis.zheleznov.eventhandlers.QuestionButtonEventHandler;
import ru.itis.zheleznov.model.Message;
import ru.itis.zheleznov.model.Player;
import ru.itis.zheleznov.models.Question;
import ru.itis.zheleznov.models.QuestionsRow;
import ru.itis.zheleznov.protocol.Protocol;

import java.io.*;
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
                    if (Protocol.read(Protocol.START, player.getSocket().getInputStream(), player.getSocket().getOutputStream())) {
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
                System.out.println("send");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void readAnswer(Player player, ObservableList<Message> list, TextField text, Button sendButton, Label question) {
        new Thread(() -> {
            while (true) {
                Message message = null;
                try {
                    message = (Message) Protocol.read(Protocol.SEND_ANSWER, player.getSocket().getInputStream(), player.getSocket().getOutputStream(), Message.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (message != null) {
                    Message finalMessage = message;

                    Platform.runLater(() -> {
                        text.setDisable(true);
                        sendButton.setDisable(true);
                        ClientQuestionController.timeline.stop();
                        question.setText("Отвечает игрок " + finalMessage.getPlayer().getLogin());
                        list.add(finalMessage);
                    });
                }
            }
        }).start();
    }

    public static void readQuestions(Player player, ObservableList<QuestionsRow> list) {
        try {
            while (true) {
                List<QuestionsRow> questions = Protocol.readQuestions(Protocol.PUT_QUESTIONS, player.getSocket().getInputStream(), player.getSocket().getOutputStream());
                if (questions != null) {
                    System.out.println("client " + questions);
                    Platform.runLater(() -> list.addAll(questions));
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void waitMove(Player player, List<QuestionsRow> questionsRows) {
        new Thread(() -> {
            while (true) {
                try {
                    Boolean bool = (Boolean) Protocol.read(Protocol.MOVE, player.getSocket().getInputStream(), player.getSocket().getOutputStream(), Boolean.class);
                    System.out.println("Check");
                    if (bool != null) {
                        if (bool) {
                            Platform.runLater(() ->
                                    questionsRows.forEach(qr -> qr.getQuestions().forEach(q -> {
                                        q.getButton().setDisable(false);
                                        q.getButton().setCursor(Cursor.HAND);
                                        q.getButton().setOnAction(new QuestionButtonEventHandler(q));
                                    }))
                            );
                        }
                        ClientSocketService.nextRound(LoginController.player);
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
}

    public static void sendChoose(Player player, QuestionDto questionDto) {
        try {
            Protocol.write(Protocol.CHOOSE, questionDto, player.getSocket().getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void nextRound(Player player) {
        new Thread(() -> {
           while (true) {
               try {
                   QuestionDto questionDto = (QuestionDto) Protocol.read(Protocol.NEXT_ROUND, player.getSocket().getInputStream(), player.getSocket().getOutputStream(), QuestionDto.class);
                   if (questionDto != null) {
                       ClientGameController.nextRound(questionDto);
                       ClientGameController.chosenQuestion = questionDto;
                       break;
                   }
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
        }).start();
    }
}
