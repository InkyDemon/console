package com.console.controller;

import com.console.application.Console;
import com.console.launch.Game;
import com.console.launch.Launcher;
import com.console.utils.ConsoleConstants;
import com.console.utils.FileUtils;
import com.console.utils.GsonUtils;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConsoleController {
    @FXML
    private ImageView exit;

    @FXML
    private ImageView user;

    @FXML
    private ImageView settings;

    @FXML
    private ImageView graphic;

    @FXML
    private TextField nickname;

    @FXML
    private AnchorPane properties;

    @FXML
    private AnchorPane main;

    @FXML
    private AnchorPane graphics;

    @FXML
    private AnchorPane AnchorBackgrounds;

    @FXML
    private ListView<String> graphicList;

    @FXML
    private Text name;

    @FXML
    private Text description;

    @FXML
    private Text textGraphic;

    @FXML
    private TextField ram;

    @FXML
    private TextField javaArguments;

    @FXML
    private Text textRam;

    @FXML
    private Text textMB;

    @FXML
    private Text textJavaArguments;

    @FXML
    private VBox games;

    private static boolean isEditNickname = true;

    private static boolean isEditProperties = true;
    private static boolean isEditGraphics = true;

    private Game selectedGame = Console.gamesManager.getGames().get("test");
    private final List<ImageView> backgrounds = new ArrayList<>();

    @FXML
    public void initialize() {
        nickname.setText(Console.preferences.profile.nickname);
        javaArguments.setText(String.join(" ", Console.preferences.settings.java_arguments));

        updateGameData();

        Console.gamesManager.getGames().values().forEach(game -> {
            try {
                ImageView iconImage = new ImageView(new Image(new FileInputStream(game.ICON.toFile())));
                iconImage.setFitWidth(48);
                iconImage.setFitHeight(48);
                iconImage.setOnMouseClicked(mouseEvent -> {
                    selectedGame = game;
                    Thread[] threads = new Thread[Thread.activeCount()];
                    Thread.enumerate(threads);
                    for (Thread thread : threads) {
                        if (thread.getName().equals(selectedGame.instance.name)) {
                            thread.interrupt();
                        }
                    }
                    updateGameData();
                });
                games.getChildren().add(iconImage);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        name.setFont(Console.getPixelTimes(true, 48));
        description.setFont(Console.getPixelTimes(false, 16));

        nickname.setFont(Console.getPixelTimes(true, 12));
        textGraphic.setFont(Console.getPixelTimes(false, 12));
        graphicList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    setFont(Console.getPixelTimes(false, 12));
                }
            }
        });

        ram.setFont(Console.getPixelTimes(false, 12));
        textRam.setFont(Console.getPixelTimes(false, 12));
        textMB.setFont(Console.getPixelTimes(false, 12));
        textJavaArguments.setFont(Console.getPixelTimes(false, 12));
        javaArguments.setFont(Console.getPixelTimes(false, 12));
    }

    public void slideShow() {
        for (ImageView image : backgrounds) {
            image.setFitWidth(960);
            image.setFitHeight(540);
            image.setOpacity(1);

            AnchorBackgrounds.getChildren().addLast(image);
        }

        if (backgrounds.size() > 1) {
            playAnimation();
        }
    }

    private void playAnimation() {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(5000);

                if (getBackgrounds().getLast() instanceof ImageView image) {
                    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(5), image);

                    fadeTransition.setFromValue(1);
                    fadeTransition.setToValue(0);

                    fadeTransition.setOnFinished(actionEvent -> {
                        getBackgrounds().removeLast();

                        image.setOpacity(1);
                        image.setStyle("-fx-image-radius: 10");

                        getBackgrounds().addFirst(image);

                        playAnimation();
                    });

                    fadeTransition.play();
                }
            } catch (InterruptedException e) {
                return;
            }
        });
        thread.setName(selectedGame.instance.name);
        thread.start();
    }

    private List<Node> getBackgrounds() {
        return AnchorBackgrounds.getChildren();
    }

    @FXML
    public void onExitPressed() {
        updatePreferences();
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onDiscordPressed() throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://discord.gg/eVAwVvq4KK"));
    }

    private void updateGameData() {
        graphicList.getItems().clear();
        backgrounds.clear();
        AnchorBackgrounds.getChildren().clear();

        name.setText(selectedGame.instance.name);
        description.setText(selectedGame.instance.description);

        selectedGame.GRAPHICS_PATHS.keySet().forEach(s -> graphicList.getItems().add(s));
        FileUtils.getFilesList(selectedGame.BACKGROUNDS_DIRECTORY, path -> path.toString().endsWith(".png") || path.toString().endsWith(".jpg") || path.toString().endsWith(".gif")).forEach(path -> {
            try {
                backgrounds.add(new ImageView(new Image(new FileInputStream(path.toFile()))));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        slideShow();
        graphicList.getSelectionModel().select(graphicList.getItems().getFirst());
    }

    private void updatePreferences() {
        Console.preferences.profile.nickname = nickname.getText();
        Console.preferences.settings.ram = Integer.parseInt(ram.getText());
        Console.preferences.settings.java_arguments = new ArrayList<>(List.of(javaArguments.getText().split(" ")));

        try {
            GsonUtils.objectToFile(Console.preferences, ConsoleConstants.PREFERENCES_JSON);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onUserPressed() {
        if (isEditNickname) {
            isEditNickname = false;
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(400), nickname);

            if (!nickname.isVisible()) {
                nickname.setVisible(true);
                translateTransition.setFromY(-40);
                translateTransition.setToY(0);
            } else {
                translateTransition.setFromY(0);
                translateTransition.setToY(-40);
                translateTransition.setOnFinished(event -> nickname.setVisible(false));
            }

            translateTransition.play();

            PauseTransition pauseTransition = new PauseTransition(Duration.millis(400));

            pauseTransition.setOnFinished(event -> isEditNickname = true);

            pauseTransition.play();
        }
    }

    @FXML
    public void onPlayPressed() throws IOException {
        updatePreferences();
        Launcher.launch(Console.preferences, selectedGame, graphicList.getSelectionModel().getSelectedItem());
        System.exit(0);
    }

    @FXML
    public void onSettingsPressed() {
        if (isEditProperties) {
            isEditProperties = false;
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), properties);

            if (!properties.isVisible()) {
                if (graphics.isVisible()) {
                    onGraphicsPressed();
                }
                properties.setVisible(true);

                fadeTransition.setFromValue(0);
                fadeTransition.setToValue(1);
            } else {
                fadeTransition.setFromValue(1);
                fadeTransition.setToValue(0);
                fadeTransition.setOnFinished(event -> properties.setVisible(false));
            }

            fadeTransition.play();

            PauseTransition pauseTransition = new PauseTransition(Duration.millis(300));

            pauseTransition.setOnFinished(event -> isEditProperties = true);

            pauseTransition.play();
        }
    }

    @FXML
    public void onGraphicsPressed() {
        if (isEditGraphics) {
            isEditGraphics = false;

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), graphics);

            if (!graphics.isVisible()) {
                if (properties.isVisible()) {
                    onSettingsPressed();
                }
                graphics.setVisible(true);

                fadeTransition.setFromValue(0);
                fadeTransition.setToValue(1);
            } else {
                fadeTransition.setFromValue(1);
                fadeTransition.setToValue(0);
                fadeTransition.setOnFinished(event -> graphics.setVisible(false));
            }

            fadeTransition.play();

            PauseTransition pauseTransition = new PauseTransition(Duration.millis(300));

            pauseTransition.setOnFinished(event -> isEditGraphics = true);

            pauseTransition.play();
        }
    }
}