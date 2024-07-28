package com.console.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ConsoleController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}