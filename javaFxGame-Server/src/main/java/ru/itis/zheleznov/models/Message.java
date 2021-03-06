package ru.itis.zheleznov.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.zheleznov.dto.PlayerDto;
import ru.itis.zheleznov.render.PlayersRender;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message implements Serializable {
    PlayerDto player;
    String text;
}
