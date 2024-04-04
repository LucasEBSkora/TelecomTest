package org.telecomnancy.project.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.telecomnancy.project.Main;
import org.telecomnancy.project.model.Difficulty;
import org.telecomnancy.project.model.Question;
import org.telecomnancy.project.model.Subject;

import java.io.IOException;
import java.util.*;

public class SubjectScreenController {

    @FXML
    Label titleLabel;

    @FXML
    ImageView thumbnailLabel;

    @FXML
    Label descriptionLabel;

    private final Main main;
    private final Subject subject;
    @FXML
    Button beginnerButton;
    @FXML
    Button intermediateButton;
    @FXML
    Button advancedButton;
    Map<Difficulty, List<Question>> questionsByLevel;

    private final static int nQuestions = 5;

    public SubjectScreenController(Main main, Subject subject) {
        this.main = main;
        this.subject = subject;
        this.questionsByLevel = questionsByDifficulty();
    }

    private Map<Difficulty, List<Question>> questionsByDifficulty() {
        Map<Difficulty, List<Question>> nQuestionsByLevel = new HashMap<>();
        for (Difficulty d : Difficulty.values()) {
            List<Question> questions = Arrays.stream(subject.questions).filter(q -> (q.level == d)).toList();
            nQuestionsByLevel.put(d, questions);
        }
        return nQuestionsByLevel;
    }

    @FXML
    void initialize() {
        titleLabel.setText(subject.name);
        descriptionLabel.setText(subject.description);
        thumbnailLabel.setImage(ImageLoader.load(subject.imagePath));
        if (questionsByLevel.get(Difficulty.Beginner).isEmpty())
            beginnerButton.setDisable(true);

        if (questionsByLevel.get(Difficulty.Intermediate).isEmpty())
            intermediateButton.setDisable(true);

        if (questionsByLevel.get(Difficulty.Advanced).isEmpty())
            advancedButton.setDisable(true);
    }

    @FXML
    void onReturnButtonClick() throws IOException {
        main.returnToLastScreen();
    }

    private List<Question> pickQuestions(Difficulty level, int n) {
        List<Question> randomQuestions = new ArrayList<>(questionsByLevel.get(level));

        Collections.shuffle(randomQuestions);

        while (randomQuestions.size() > n)
            randomQuestions.remove(0);

        return randomQuestions;
    }

    @FXML
    void onClickBeginnerButton() throws IOException {
        main.questionsScreen(pickQuestions(Difficulty.Beginner, nQuestions));
    }

    @FXML
    void onClickIntermediateButton() throws IOException {
        main.questionsScreen(pickQuestions(Difficulty.Intermediate, nQuestions));
    }

    @FXML
    void onClickAdvancedButton() throws IOException {
        main.questionsScreen(pickQuestions(Difficulty.Advanced, nQuestions));
    }


}
