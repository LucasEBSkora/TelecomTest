package org.telecomnancy.project.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.telecomnancy.project.Main;
import org.telecomnancy.project.model.Subject;

import java.io.IOException;
import java.util.Arrays;

public class MainScreenController {
    @FXML
    private ListView<Subject> subjectsView;

    private final Main main;
    private final Subject[] subjects;

    public MainScreenController(Main main, Subject[] subjects) {
        this.main = main;
        this.subjects = subjects;
    }

    @FXML
    void initialize() {
        subjectsView.setCellFactory(new SubjectCellFactory());
        subjectsView.setItems(FXCollections.observableList(Arrays.stream(subjects).toList()));
    }

    @FXML
    void selectSubject(MouseEvent event) throws IOException {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            Subject entry = subjectsView.getSelectionModel().getSelectedItem();
            main.SubjectScreen(entry);
        }

    }
}