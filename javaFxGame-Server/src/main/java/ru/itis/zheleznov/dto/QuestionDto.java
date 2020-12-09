package ru.itis.zheleznov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.zheleznov.models.Question;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDto implements Serializable {
    private String questionName;
    private int points;

    public static QuestionDto from(Question question) {
        return QuestionDto.builder()
                .questionName(question.getQuestionName())
                .points(question.getPoints())
                .build();
    }

    public static List<QuestionDto> from(List<Question> questionList) {
        return questionList.stream().map(QuestionDto::from).collect(Collectors.toList());
    }
}
