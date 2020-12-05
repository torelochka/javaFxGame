package ru.itis.zheleznov.window;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowManager {

    public static void renderQuestionMakerWindow(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(WindowManager.class.getResourceAsStream("/fxml/questionMakerRoom.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add((WindowManager.class.getResource("/css/questionMakerRoom.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Question Maker");
        primaryStage.setMinHeight(450);
        primaryStage.setMinWidth(650);
        primaryStage.show();
    }

    public static void renderPreLobbyWindow(Stage primaryStage) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(WindowManager.class.getResourceAsStream("/fxml/preLobbyRoom.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("pre lobby room");
        primaryStage.setMinHeight(450);
        primaryStage.setMinWidth(650);
        primaryStage.show();
    }

    public static void renderLobbyWindow(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        Parent root;
        try {
            root = loader.load(WindowManager.class.getResourceAsStream("/fxml/hostLobby.fxml"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("lobby");
        primaryStage.setMinHeight(450);
        primaryStage.setMinWidth(650);
        primaryStage.show();
    }

    public static void renderGameWindow(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        Parent root;
        try {
            root = loader.load(WindowManager.class.getResourceAsStream("/fxml/gameHost.fxml"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("game");
        primaryStage.setMinHeight(450);
        primaryStage.setMinWidth(650);
        primaryStage.show();
    }
}
