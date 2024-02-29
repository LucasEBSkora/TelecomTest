package org.telecomnancy.project.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.telecomnancy.project.Main;
import org.telecomnancy.project.model.Subject;

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
        /*
        conversationList.setCellFactory(new MessageCellFactory(user.UID, interlocutor.UID));*/
        subjectsView.setItems(FXCollections.observableList(Arrays.stream(subjects).toList()));

    }
}