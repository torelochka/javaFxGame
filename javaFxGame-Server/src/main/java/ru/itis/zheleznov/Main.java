package ru.itis.zheleznov;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //TODO запретить уменьшение экрана меньше минимально допустимого
        Main.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/preLobbyRoom.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        //TODO поставить фиксированный размер?
        scene.getStylesheets().add(getClass().getResource("/css/preLobbyRoom.css").toString());
        //TODO rename?
        primaryStage.setTitle("Pre lobby room");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
