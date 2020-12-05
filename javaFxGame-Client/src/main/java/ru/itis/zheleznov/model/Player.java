package ru.itis.zheleznov.model;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.net.Socket;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player implements Serializable {
    private String login;
    @Builder.Default
    private int points = 0;
    private Socket socket;

    public Player(String login, Socket socket) {
        this(login, 0, socket);
    }
}
