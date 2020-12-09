package ru.itis.zheleznov.window;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowManager {

    public static void renderWindow(Stage primaryStage, String title, String name, int width, int height){
        FXMLLoader loader = new FXMLLoader();
        Parent root;
        try {
            root = loader.load(WindowManager.class.getResourceAsStream("/fxml/" + name));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle(title);
        primaryStage.setMinWidth(width);
        primaryStage.setMinHeight(height);
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
