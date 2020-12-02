package ru.itis.zheleznov.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.Socket;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Player {
    private String nickname;
    @Builder.Default
    private int points = 0;
    private Socket socket;
}
