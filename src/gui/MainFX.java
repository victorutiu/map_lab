package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainFX extends Application {

    @Override
    public void start(Stage stage) {
        new gui.ProgramChooser().show(stage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
