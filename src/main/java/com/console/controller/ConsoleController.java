package com.console.controller;

import com.console.application.Console;
import com.console.launch.Game;
import com.console.launch.Launcher;
import com.console.utils.ConsoleConstants;
import com.console.utils.FileUtils;
import com.console.utils.GsonUtils;
import javafx.animation.*;
import javafx.collections.ObservableList;
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
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

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
    private SequentialTransition backgroundsTransition = this.getBackgroundsTransition();

    @FXML
    public void initialize() {
        ram.setText(String.valueOf(Console.preferences.settings.ram));
        nickname.setText(Console.preferences.profile.nickname);
        javaArguments.setText(String.join(" ", Console.preferences.settings.java_arguments));
        this.updateGameData();

        this.updateBackgroundsNodes();
        if (getBackgrounds().size() > 1) {
            backgroundsTransition.playFromStart();
        }

        Console.gamesManager.getGames().values().forEach(game -> {
            try (InputStream iconIS = Files.newInputStream(game.ICON)) {
                ImageView iconImage = new ImageView(new Image(iconIS));
                iconImage.setFitWidth(48);
                iconImage.setFitHeight(48);

                iconImage.setOnMouseClicked(mouseEvent -> {
                    if (selectedGame != game) {
                        selectedGame = game;
                        this.updateGameData();

                        backgroundsTransition.pause();
                        this.updateBackgroundsNodes();

                        if (getBackgrounds().size() > 1) {
                            backgroundsTransition.playFromStart();
                        }
                    }
                });

                games.getChildren().add(iconImage);
            } catch (IOException ioException) {
                throw new RuntimeException(ioException);
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

    @FXML
    public void onExitPressed() {
        updatePreferences();
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
        System.exit(0);
    }

    @FXML
    public void onDiscordPressed() throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://discord.gg/eVAwVvq4KK"));
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

    private ObservableList<Node> getBackgrounds() {
        return AnchorBackgrounds.getChildren();
    }

    private SequentialTransition getBackgroundsTransition() {
        SequentialTransition sequentialTransition = new SequentialTransition();

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(5));
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);

        fadeTransition.setOnFinished(actionEvent -> {
            if (sequentialTransition.getStatus() == Animation.Status.RUNNING) {
                getBackgrounds().removeLast();

                getBackgrounds().addFirst(sequentialTransition.getNode());

                getBackgrounds().getFirst().setOpacity(1);

                this.backgroundsTransition = getBackgroundsTransition();

                if (getBackgrounds().size() > 1) {
                    backgroundsTransition.setNode(getBackgrounds().getLast());
                    backgroundsTransition.playFromStart();
                }
            }
        });

        sequentialTransition.setAutoReverse(false);
        sequentialTransition.setDelay(Duration.seconds(5));
        sequentialTransition.setRate(1);

        sequentialTransition.getChildren().add(fadeTransition);
        sequentialTransition.getChildren().add(new PauseTransition(Duration.seconds(5)));

        return sequentialTransition;
    }


    private void updateBackgroundsNodes() {
        ObservableList<Animation> animations = backgroundsTransition.getChildren();
        animations.clear();
        getBackgrounds().clear();

        FileUtils.getFilesList(selectedGame.BACKGROUNDS_DIRECTORY, path -> !Files.isDirectory(path)).forEach(path -> {
            try (InputStream imageIS = Files.newInputStream(path)) {
                Image image = new Image(imageIS);

                if (!image.isError()) {
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(960);
                    imageView.setFitHeight(540);
                    imageView.setOpacity(1);

                    getBackgrounds().add(imageView);
                }
            }
            catch (IOException ioException) {
                throw new RuntimeException(ioException);
            }
        });

        this.backgroundsTransition = getBackgroundsTransition();

        if (!getBackgrounds().isEmpty()) {
            backgroundsTransition.setNode(getBackgrounds().getLast());
        }
    }

    private void updateGameData() {
        ObservableList<String> graphics = graphicList.getItems();

        name.setText(selectedGame.instance.name);
        description.setText(selectedGame.instance.description);

        graphics.clear();
        graphics.addAll(selectedGame.GRAPHICS_PATHS.keySet());
        graphicList.getSelectionModel().select(graphics.getFirst());
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
}