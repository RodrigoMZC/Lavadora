package org.rmr.lavadora;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/org/rmr/lavadora/view/lavadora-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 620, 400);
        stage.setTitle("Simulador de Lavadora Difusa");
        stage.setScene(scene);
        stage.show();
    }

    static void main() {
        launch();
    }
}
