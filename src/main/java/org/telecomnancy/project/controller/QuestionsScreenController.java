package org.telecomnancy.project.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import org.telecomnancy.project.Main;
import org.telecomnancy.project.model.Option;
import org.telecomnancy.project.model.Question;

import java.io.IOException;
import java.util.ArrayList;
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
    @FXML
    private RadioButton option1, option2, option3, option4;

    private ToggleGroup optionsGroup;

    private int currentQuestion;

    public QuestionsScreenController(Main main, List<Question> questions) {
        this.main = main;
        this.questions = questions;
        randomizeAlternatives();
        answeredQuestions = new ArrayList<>(Collections.nCopies(questions.size(), new Pair<>(false, false)));
        currentQuestion = 0;
    }

    private void randomizeAlternatives() {
        for (Question q : this.questions) {
            List<Option> options = new ArrayList<>(List.of(q.options));
            Collections.shuffle(options);
            options.toArray(q.options);
        }
    }

    @FXML
    void initialize() {
        setUpRadioGroup();
        showQuestion();
        updateAnsweredQuestions();
    }

    private void setUpRadioGroup() {
        optionsGroup = new ToggleGroup();
        option1.setToggleGroup(optionsGroup);
        option2.setToggleGroup(optionsGroup);
        option3.setToggleGroup(optionsGroup);
        option4.setToggleGroup(optionsGroup);
    }

    private void updateAnsweredQuestions() {
        long nAnswered = answeredQuestions.stream().filter(Pair::getKey).count();
        answeredQuestionsLabel.setText(String.format("Answered Questions: %d/%d", nAnswered, questions.size()));
        long correctAnswers = answeredQuestions.stream().filter(Pair::getKey).filter(Pair::getValue).count();
        int rightAnswersPercentage = (int) ((100f * correctAnswers) / nAnswered);
        rightAnswersLabel.setText(String.format("Right answers: %d%%", rightAnswersPercentage));
        if (correctAnswers == questions.size())
            enableFinishButton();
    }

    private void enableFinishButton() {
        finishTestButton.setOpacity(1);
        finishTestButton.setDisable(false);
    }

    private void showQuestion() {
        checkAnswerButton.setDisable(true);
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
        questionImage.setFitHeight(0);

        if (question.imagePath == null || question.imagePath.isBlank()) return;
        Image img = ImageLoader.load(question.imagePath);
        if (img == null) return;
        questionImage.setFitWidth(400);
        questionImage.setImage(img);
    }

    private void updateOptions() {
        RadioButton[] buttons = {option1, option2, option3, option4};
        Question current = questions.get(currentQuestion);

        boolean questionAnswered = answeredQuestions.get(currentQuestion).getKey();
        for (int i = 0; i < buttons.length; ++i) {
            buttons[i].setText(current.options[i].text);
            buttons[i].setUserData(current.options[i].correct);
            Image img = ImageLoader.load(current.options[i].imagePath);
            ImageView view = new ImageView(img);
            view.setPreserveRatio(true);
            view.setFitWidth(100);
            buttons[i].setGraphic(view);
            if (questionAnswered) {
                Color fillColor = ((boolean) buttons[i].getUserData()) ? Color.LIGHTGREEN : Color.DEEPPINK;
                buttons[i].setTextFill(fillColor);
            } else
                buttons[i].setTextFill(Color.BLACK);
        }
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
        optionsGroup.selectToggle(null);
        showQuestion();
    }

    @FXML
    protected void toRight() {
        ++currentQuestion;
        optionsGroup.selectToggle(null);
        showQuestion();
    }

    @FXML
    protected void onOptionSelected() {
        if (!answeredQuestions.get(currentQuestion).getKey())
            checkAnswerButton.setDisable(false);
    }

    @FXML
    void onCheckAnswerClick() {
        RadioButton selected = (RadioButton) optionsGroup.getSelectedToggle();
        if (selected == null) {
            checkAnswerButton.setDisable(true);
            return;
        }
        boolean wasCorrect = (boolean) selected.getUserData();
        answeredQuestions.set(currentQuestion, new Pair<>(true, wasCorrect));
        checkAnswerButton.setDisable(true);
        updateAnsweredQuestions();
        updateOptions();
    }

    @FXML
    void onFinishTestClick() throws IOException {
        main.returnToLastScreen();
    }
}