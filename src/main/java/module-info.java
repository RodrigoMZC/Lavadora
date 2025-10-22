module org.rmr.lavadora {
    requires javafx.controls;
    requires javafx.fxml;
    requires eu.hansolo.tilesfx;

    opens org.rmr.lavadora to javafx.fxml;
    opens org.rmr.lavadora.view to javafx.fxml;
    exports org.rmr.lavadora;
}