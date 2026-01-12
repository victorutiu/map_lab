package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import model.state.ProgramState;
import model.value.IValue;

import java.util.Map;
import java.util.stream.Collectors;

public class MainWindow {

    private final InterpreterService service;
    private final TextField nrPrgStatesField = new TextField();

    private final TableView<HeapRow> heapTable = new TableView<>();
    private final TableColumn<HeapRow, Integer> heapAddrCol = new TableColumn<>("Address");
    private final TableColumn<HeapRow, String> heapValCol = new TableColumn<>("Value");

    private final ListView<String> outListView = new ListView<>();

    private final ListView<String> fileTableListView = new ListView<>();

    private final ListView<Integer> prgStateIdsListView = new ListView<>();

    private final TableView<SymbolTableRow> symTable = new TableView<>();
    private final TableColumn<SymbolTableRow, String> symVarCol = new TableColumn<>("Variable");
    private final TableColumn<SymbolTableRow, String> symValCol = new TableColumn<>("Value");

    private final ListView<String> exeStackListView = new ListView<>();

    private final Button oneStepButton = new Button("Run one step");

    public MainWindow(InterpreterService service) {
        this.service = service;
    }

    public void show() {
        Stage stage = new Stage();

        nrPrgStatesField.setEditable(false);

        HBox topRow = new HBox(10,
                new Label("Number of Program States:"),
                nrPrgStatesField,
                oneStepButton
        );

        heapAddrCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        heapValCol.setCellValueFactory(new PropertyValueFactory<>("value"));

        heapAddrCol.setPrefWidth(120);
        heapValCol.setPrefWidth(400);

        heapTable.getColumns().setAll(heapAddrCol, heapValCol);
        heapTable.setPrefHeight(150);

        outListView.setPrefHeight(150);

        fileTableListView.setPrefHeight(120);

        prgStateIdsListView.setPrefHeight(120);

        symVarCol.setCellValueFactory(new PropertyValueFactory<>("variable"));
        symValCol.setCellValueFactory(new PropertyValueFactory<>("value"));

        symVarCol.setPrefWidth(200);
        symValCol.setPrefWidth(300);

        symTable.getColumns().setAll(symVarCol, symValCol);
        symTable.setPrefHeight(200);

        exeStackListView.setPrefHeight(180);


        VBox root = new VBox(
                15,
                topRow,
                new Label("Program State IDs:"),
                prgStateIdsListView,
                new Label("Symbol Table (selected PrgState):"),
                symTable,
                new Label("Execution Stack (selected PrgState):"),
                exeStackListView,
                new Label("Heap Table:"),
                heapTable,
                new Label("Out:"),
                outListView,
                new Label("File Table:"),
                fileTableListView
        );


        root.setPadding(new Insets(12));

        stage.setTitle("Toy Interpreter - Main");
        stage.setScene(new Scene(root, 800, 700));
        stage.show();

        oneStepButton.setOnAction(e -> {
            try {
                service.getController().oneStep();
                refreshAll();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
            }
        });

        prgStateIdsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            refreshSymTable();
            refreshExeStack();
        });

        /*refreshNrPrgStates();
        refreshPrgStateIds();
        refreshSymTable();
        refreshExeStack();
        refreshHeapTable();
        refreshOut();
        refreshFileTable();*/
        refreshAll();



    }

    private void refreshNrPrgStates() {
        nrPrgStatesField.setText(String.valueOf(service.getProgramStates().size()));
    }

    private void refreshHeapTable() {
        if (service.getProgramStates().isEmpty()) {
            heapTable.setItems(FXCollections.observableArrayList());
            return;
        }

        ProgramState first = service.getProgramStates().get(0);
        Map<Integer, IValue> heap = first.getHeap().getContent();

        var rows = heap.entrySet().stream()
                .map(e -> new HeapRow(e.getKey(), e.getValue().toString()))
                .collect(Collectors.toList());

        heapTable.setItems(FXCollections.observableArrayList(rows));
    }

    private void refreshOut() {
        if (service.getProgramStates().isEmpty()) {
            outListView.setItems(FXCollections.observableArrayList());
            return;
        }

        ProgramState first = service.getProgramStates().get(0);
        outListView.setItems(FXCollections.observableArrayList(
                first.getOutputList().getAll().stream()
                        .map(Object::toString)
                        .toList()
        ));
    }

    private void refreshFileTable() {
        if (service.getProgramStates().isEmpty()) {
            fileTableListView.setItems(FXCollections.observableArrayList());
            return;
        }

        ProgramState first = service.getProgramStates().get(0);

        var files = first.getFileTable().getContent().keySet().stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        fileTableListView.setItems(FXCollections.observableArrayList(files));
    }

    private void refreshPrgStateIds() {
        var ids = service.getProgramStates().stream()
                .map(ProgramState::getId)
                .sorted()
                .collect(Collectors.toList());

        prgStateIdsListView.setItems(FXCollections.observableArrayList(ids));

        if (!ids.isEmpty() && prgStateIdsListView.getSelectionModel().getSelectedItem() == null) {
            prgStateIdsListView.getSelectionModel().selectFirst();
        }
    }


    private ProgramState getSelectedProgramState() {
        Integer selectedId = prgStateIdsListView.getSelectionModel().getSelectedItem();
        if (selectedId == null) return null;

        return service.getProgramStates().stream()
                .filter(p -> p.getId() == selectedId)
                .findFirst()
                .orElse(null);
    }

    private void refreshSymTable() {
        ProgramState selected = getSelectedProgramState();
        if (selected == null) {
            symTable.setItems(FXCollections.observableArrayList());
            return;
        }

        var rows = selected.getSymbolTable().getContent().entrySet().stream()
                .map(e -> new SymbolTableRow(e.getKey(), e.getValue().toString()))
                .collect(Collectors.toList());

        symTable.setItems(FXCollections.observableArrayList(rows));
    }

    private void refreshExeStack() {
        ProgramState selected = getSelectedProgramState();
        if (selected == null) {
            exeStackListView.setItems(FXCollections.observableArrayList());
            return;
        }

        var items = selected.getExecutionStack().getAll().stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        exeStackListView.setItems(FXCollections.observableArrayList(items));
    }

    private void refreshAll() {
        refreshNrPrgStates();
        refreshPrgStateIds();

        if (prgStateIdsListView.getItems().size() > 0 &&
                prgStateIdsListView.getSelectionModel().getSelectedItem() == null) {
            prgStateIdsListView.getSelectionModel().selectFirst();
        }

        refreshSymTable();
        refreshExeStack();
        refreshHeapTable();
        refreshOut();
        refreshFileTable();
    }


}
