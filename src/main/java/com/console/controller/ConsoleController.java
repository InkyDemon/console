package com.console.controller;

import com.console.application.Console;
import com.console.json.GameInstance;
import com.console.launch.Game;
import com.console.launch.Launcher;
import com.console.utils.ConsoleConstants;
import com.console.utils.FileUtils;
import com.console.utils.GsonUtils;
import javafx.animation.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConsoleController {
    @FXML
    public ImageView play;

    @FXML
    public ImageView settings;

    @FXML
    public AnchorPane leftBar;

    @FXML
    public ImageView user;

    @FXML
    public AnchorPane main;

    @FXML
    public AnchorPane rightBar;

    @FXML
    public AnchorPane mainBar;

    @FXML
    public Text version;

    @FXML
    private ImageView exit;

    @FXML
    private TextField nickname;

    @FXML
    private AnchorPane properties;

    @FXML
    private AnchorPane AnchorBackgrounds;

    @FXML
    private Text name;

    @FXML
    private Text description;

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

    private Game selectedGame = Game.EMPTY_GAME;
    private SequentialTransition backgroundsTransition = this.getBackgroundsTransition();

    @FXML
    public void initialize() {
        ram.setText(String.valueOf(Console.preferences.settings.ram));
        nickname.setText(Console.preferences.profile.nickname);
        javaArguments.setText(String.join(" ", Console.preferences.settings.java_arguments));

        Console.gamesManager.getGames().values().forEach(game -> {
            if (Objects.equals(selectedGame, Game.EMPTY_GAME)) {
                selectedGame = game;
            }

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

        this.updateGameData();
        this.updateBackgroundsNodes();
        if (getBackgrounds().size() > 1) {
            backgroundsTransition.playFromStart();
        }

        name.setFont(Console.getPixelTimes(true, 48));
        description.setFont(Console.getPixelTimes(false, 16));
        version.setFont(Console.getPixelTimes(false, 16));

        nickname.setFont(Console.getPixelTimes(true, 12));

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
        Launcher.launch(Console.preferences, selectedGame);
    }

    @FXML
    public void onSettingsPressed() {
        if (isEditProperties) {
            isEditProperties = false;
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), properties);

            if (!properties.isVisible()) {
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
        name.setText(selectedGame.instance.name);
        description.setText(selectedGame.instance.description);
        version.setText("Версия: " + selectedGame.instance.version);
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