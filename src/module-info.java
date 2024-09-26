module myjfx {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    opens MainPackage to javafx.graphics, javafx.fxml, javafx.base;
    opens Database to javafx.graphics, javafx.fxml, javafx.base;
}