module console.main {
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.google.gson;

    opens com.console.application to javafx.graphics, javafx.fxml;
    opens com.console.controller to javafx.controls, javafx.fxml;

    exports com.console.json to com.google.gson;
}