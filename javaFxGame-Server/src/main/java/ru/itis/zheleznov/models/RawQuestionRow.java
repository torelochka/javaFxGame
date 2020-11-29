package ru.itis.zheleznov.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javafx.scene.control.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RawQuestionRow {
    private TextArea question;
    private TextField points;
}
