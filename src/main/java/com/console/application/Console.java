package com.console.application;

import com.console.Starter;
import com.console.json.Preferences;
import com.console.launch.GamesManager;
import com.console.utils.ConsoleConstants;
import com.console.utils.GsonUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.nio.file.Files;

public class Console extends Application {
    public static Preferences preferences;
    public static GamesManager gamesManager;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws IOException {
        if (Files.exists(ConsoleConstants.PREFERENCES_JSON)) {
            preferences = GsonUtils.jsonToObject(Files.readString(ConsoleConstants.PREFERENCES_JSON), Preferences.class);
        }
        else {
            preferences = Preferences.getDefaultPreferences();
            GsonUtils.objectToFile(preferences, ConsoleConstants.PREFERENCES_JSON);
        }

        gamesManager = new GamesManager(ConsoleConstants.GAMES_DIRECTORY);

        FXMLLoader fxmlLoader = new FXMLLoader(Starter.class.getResource("views/launcher.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 960, 540);

        scene.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        stage.setMaxWidth(960);
        stage.setMaxHeight(540);

        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Console");
        //TODO stage.getIcons().add(new Image(Starter.class.getResourceAsStream("")));
        stage.setScene(scene);
        stage.show();
    }

    public static Font getPixelTimes(boolean bold, double size) {
        if (bold) {
            return Font.loadFont(Starter.class.getResourceAsStream("fonts/Pixel Times Bold.ttf"), size);
        } else {
            return Font.loadFont(Starter.class.getResourceAsStream("fonts/Pixel Times.ttf"), size);
        }
    }
}