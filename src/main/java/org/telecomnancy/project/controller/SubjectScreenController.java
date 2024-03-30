package org.telecomnancy.project.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.telecomnancy.project.Main;
import org.telecomnancy.project.model.Subject;

public class SubjectScreenController {

    private final Main main;
    private final Subject subject;

    @FXML
    Label titleLabel;

    @FXML
    ImageView thumbnailLabel;

    @FXML
    Label descriptionLabel;

    public SubjectScreenController(Main main, Subject subject) {
        this.main = main;
        this.subject = subject;
    }

    @FXML
    void initialize() {
        titleLabel.setText(subject.name);
        descriptionLabel.setText(subject.description);
        thumbnailLabel.setImage(ImageLoader.load(subject.imagePath));
    }

    @FXML
    void onReturnButtonClick() {

    }

    @FXML
    void onClickBeginnerButton() {

    }

    @FXML
    void onClickIntermediateButton() {

    }

    @FXML
    void onClickAdvancedButton() {

    }


}
