package com.console.application;

import com.console.Starter;
import com.console.json.Preferences;
import com.console.utils.ConsoleConstants;
import com.console.utils.GsonUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;

public class Console extends Application {
    public static Preferences preferences;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            this.preferences = GsonUtils.jsonToObject(Files.readString(ConsoleConstants.PREFERENCES_JSON), Preferences.class);
        }
        catch (NoSuchFileException noFileException) {
            this.preferences = Preferences.getDefaultPreferences();
            GsonUtils.objectToJson(preferences, ConsoleConstants.PREFERENCES_JSON);
        }

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