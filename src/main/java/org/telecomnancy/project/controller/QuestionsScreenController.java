package org.telecomnancy.project.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;
import org.telecomnancy.project.Main;
import org.telecomnancy.project.model.Question;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class QuestionsScreenController {
    @FXML
    private Label questionField;
    private final Main main;
    private final List<Question> questions;
    private final List<Pair<Boolean, Boolean>> answeredQuestions;
    @FXML
    private Label currentQuestionLabel;
    @FXML
    private Label answeredQuestionsLabel;
    @FXML
    private Label rightAnswersLabel;
    @FXML
    private ImageView questionImage;
    @FXML
    private Button leftButton;
    @FXML
    private Button rightButton;
    @FXML
    private Button finishTestButton;
    @FXML
    private Button checkAnswerButton;
    private int currentQuestion;

    public QuestionsScreenController(Main main, List<Question> questions) {
        this.main = main;
        this.questions = questions;
        answeredQuestions = Collections.nCopies(questions.size(), new Pair<>(false, false));
        currentQuestion = 0;
    }

    @FXML
    void initialize() {
        showQuestion();
        updateAnsweredQuestions();
    }

    private void updateAnsweredQuestions() {
        long nAnswered = answeredQuestions.stream().filter(Pair::getKey).count();
        answeredQuestionsLabel.setText(String.format("Answered Questions: %d/%d", nAnswered, questions.size()));
        long correctAnswers = answeredQuestions.stream().filter(Pair::getKey).filter(Pair::getValue).count();
        int rightAnswersPercentage = (int) ((100f * correctAnswers) / nAnswered);
        rightAnswersLabel.setText(String.format("Right answers: %d%%", rightAnswersPercentage));
    }

    private void showQuestion() {
        updateChangeQuestionButtons();
        updateQuestionTextAndImage();
        updateOptions();
        updateCurrentQuestionLabel();
    }

    private void updateChangeQuestionButtons() {
        leftButton.setDisable(currentQuestion == 0);
        rightButton.setDisable(currentQuestion == questions.size() - 1);
    }

    private void updateQuestionTextAndImage() {
        Question question = questions.get(currentQuestion);
        questionField.setText(question.text);
        questionImage.setImage(null);

        System.out.println(question.imagePath);
        if (question.imagePath == null || question.imagePath.isBlank()) return;
        Image img = ImageLoader.load(question.imagePath);
        if (img == null) return;
        questionImage.setImage(img);
    }

    private void updateOptions() {

    }

    private void updateCurrentQuestionLabel() {
        currentQuestionLabel.setText("Current Question: " + (currentQuestion + 1) + "/" + questions.size());
    }

    @FXML
    protected void onReturnButtonClick() throws IOException {
        main.returnToLastScreen();
    }
    @FXML
    protected void toLeft() {
        --currentQuestion;
        showQuestion();
    }

    @FXML
    protected void toRight() {
        ++currentQuestion;
        showQuestion();
    }

    @FXML
    void onCheckAnswerClick() {

    }

    @FXML
    void onFinishTestClick() {

    }
}