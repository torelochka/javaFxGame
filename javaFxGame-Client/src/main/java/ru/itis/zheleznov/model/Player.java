package ru.itis.zheleznov.model;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.Socket;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {
    private String login;
    @Builder.Default
    private int points = 0;
    private Socket socket;
}
