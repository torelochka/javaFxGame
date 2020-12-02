package ru.itis.zheleznov.models;

import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.ServerSocket;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Host {
    @Builder.Default
    private String name = "Host";
    private ServerSocket serverSocket;
    private ObservableList<QuestionsRow> questions;
}
