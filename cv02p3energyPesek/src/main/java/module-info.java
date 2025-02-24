module cv03energy {
    requires javafx.controls;
    requires javafx.fxml;

    opens cv03.cv02p3energypesek to javafx.fxml;
    exports cv03.cv02p3energypesek;
}