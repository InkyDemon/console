module com.console {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.console to javafx.fxml;
    exports com.console;
}