package ru.itis.zheleznov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.zheleznov.models.Player;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerDto {
    private String login;
    private int points = 0;

    public static PlayerDto from(Player player) {
        return PlayerDto.builder()
                .login(player.getLogin())
                .points(player.getPoints())
                .build();
    }

    public static List<PlayerDto> from(List<Player> players) {
        return players.stream().map(PlayerDto::from).collect(Collectors.toList());
    }
}
