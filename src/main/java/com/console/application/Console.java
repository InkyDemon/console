package com.console.application;

import com.console.Starter;
import com.console.json.Preferences;
import com.console.launch.Downloader;
import com.console.launch.GamesManager;
import com.console.utils.ConsoleConstants;
import com.console.utils.GsonUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class Console extends Application {
    public static Preferences preferences;
    public static GamesManager gamesManager;

    private double xOffset = 0;
    private double yOffset = 0;
    private final Rectangle2D SCREEN_BOUNDS = Screen.getPrimary().getVisualBounds();

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.showDownload(primaryStage);

        new Thread(() -> {
            try {
                if (!Files.exists(ConsoleConstants.ASSETS_DIRECTORY)) {
                    Downloader.downloadGithubRep("https://github.com/InkyDemon/Console-games/archive/refs/heads/assets.zip", ConsoleConstants.ASSETS_DIRECTORY);
                }
                Downloader.downloadGithubRep("https://github.com/InkyDemon/Console-games/archive/refs/heads/games.zip", ConsoleConstants.GAMES_DIRECTORY);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            Platform.runLater(() -> {
                primaryStage.close();

                try {
                    if (Files.exists(ConsoleConstants.PREFERENCES_JSON)) {
                        preferences = GsonUtils.jsonToObject(Files.readString(ConsoleConstants.PREFERENCES_JSON), Preferences.class);
                    }
                    else {
                        preferences = Preferences.getDefaultPreferences();
                        GsonUtils.objectToFile(preferences, ConsoleConstants.PREFERENCES_JSON);
                    }

                    gamesManager = new GamesManager(ConsoleConstants.GAMES_DIRECTORY);

                    this.showLauncher(primaryStage);
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }).start();
    }

    private void showDownload(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Starter.class.getResource("views/download.fxml"));
        BorderPane borderPane = fxmlLoader.load();
        Scene scene = new Scene(borderPane);
        scene.setFill(Color.TRANSPARENT);

        primaryStage.setTitle("Console: Download");
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(true);

        InputStream stream = Starter.class.getResourceAsStream("icons/logo.png");

        if (stream != null) {
            primaryStage.getIcons().add(new Image(stream));
        }

        this.centerStage(primaryStage);
        primaryStage.requestFocus();
        primaryStage.show();
    }

    private void showLauncher(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Starter.class.getResource("views/launcher.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 540);

        scene.setFill(Color.TRANSPARENT);
        scene.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        scene.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        primaryStage.setTitle("Console");
        primaryStage.setScene(scene);

        this.centerStage(primaryStage);
        primaryStage.requestFocus();
        primaryStage.show();
    }

    private void centerStage(Stage stage) {
        stage.widthProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number width, Number newWidth) {
                if (!Double.isNaN(newWidth.doubleValue())) {
                    stage.setX((SCREEN_BOUNDS.getWidth() - newWidth.doubleValue()) / 2);
                    stage.widthProperty().removeListener(this);
                }
            }
        });

        stage.heightProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number height, Number newHeight) {
                if (!Double.isNaN(newHeight.doubleValue())) {
                    stage.setY((SCREEN_BOUNDS.getHeight() - newHeight.doubleValue()) / 2);
                    stage.heightProperty().removeListener(this);
                }
            }
        });
    }

    public static Font getPixelTimes(boolean bold, double size) {
        if (bold) {
            return Font.loadFont(Starter.class.getResourceAsStream("fonts/PixelTimesBold.ttf"), size);
        } else {
            return Font.loadFont(Starter.class.getResourceAsStream("fonts/PixelTimes.ttf"), size);
        }
    }
}