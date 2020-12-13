package ru.itis.zheleznov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.zheleznov.models.Message;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer {
    private Boolean result;
    private Message message;
}
