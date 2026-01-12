package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.statement.IStatement;
import controller.Controller;
import controller.IController;
import model.adt.*;
import model.state.ProgramState;
import model.statement.IStatement;
import model.value.StringValue;
import repository.IRepository;
import repository.Repository;

import java.io.BufferedReader;
import javafx.stage.Stage;

import java.util.List;

public class ProgramChooser {

    private final List<IStatement> programs = ExamplePrograms.getAll();

    public void show(Stage stage) {
        Label title = new Label("Select a program to execute:");

        ListView<String> listView = new ListView<>();
        for (IStatement st : programs) {
            listView.getItems().add(st.toString());
        }
        listView.getSelectionModel().selectFirst();

        Label selectedLabel = new Label("");
        Button selectButton = new Button("Select");

        selectButton.setOnAction(e -> {
            int idx = listView.getSelectionModel().getSelectedIndex();
            if (idx < 0) {
                selectedLabel.setText("Please select a program.");
                return;
            }
            IStatement chosen = programs.get(idx);
            selectedLabel.setText("Selected: " + chosen.toString());

            ProgramState state = new ProgramState(
                    new MyStack<>(),
                    new MyDictionary<>(),
                    new MyList<>(),
                    new MyDictionary<StringValue, BufferedReader>(),
                    new MyHeap(),
                    chosen
            );

            IRepository repo = new Repository(state, "log_gui.txt");
            IController ctrl = new Controller(repo);

            InterpreterService service = new InterpreterService(ctrl);

            new MainWindow(service).show();

            ((Stage) listView.getScene().getWindow()).close();

        });

        VBox root = new VBox(10, title, listView, selectButton, selectedLabel);
        root.setPadding(new Insets(12));

        stage.setTitle("Choose Program");
        stage.setScene(new Scene(root, 700, 500));
        stage.show();
    }
}
