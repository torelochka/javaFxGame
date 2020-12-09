package ru.itis.zheleznov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.zheleznov.models.QuestionsRow;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionsRowDto implements Serializable {
    private String categoryName;
    private List<QuestionDto> questions;

    public static QuestionsRowDto from(QuestionsRow questionsRow) {
        return QuestionsRowDto.builder()
                .categoryName(questionsRow.getCategoryName())
                .questions(QuestionDto.from(questionsRow.getQuestions()))
                .build();
    }

    public static List<QuestionsRowDto> from(List<QuestionsRow> questionsRows) {
        return questionsRows.stream().map(QuestionsRowDto::from).collect(Collectors.toList());
    }
}
