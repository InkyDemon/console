module console.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.console.application to javafx.graphics, javafx.fxml;
    opens com.console.controller to javafx.controls, javafx.fxml;
    opens com.console to javafx.graphics;
}