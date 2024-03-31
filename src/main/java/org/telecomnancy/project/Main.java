package org.telecomnancy.project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.telecomnancy.project.controller.MainScreenController;
import org.telecomnancy.project.controller.QuestionsScreenController;
import org.telecomnancy.project.controller.SubjectScreenController;
import org.telecomnancy.project.model.Question;
import org.telecomnancy.project.model.Subject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;

public class Main extends Application {

    private Stage primaryStage;

    private Stack<Scene> scenes;

    private final int width = 800;
    private final int height = 600;

    private Subject[] subjects;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;
        this.scenes = new Stack<>();
        loadSubjects();
        stage.setTitle("Telecom Test");
        mainScreen();
    }

    private void loadSubjects() {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        String text = resourceAsString("questions.json");
        subjects = gson.fromJson(text, Subject[].class);
    }

    private String resourceAsString(String path) {
        InputStream stream =  Main.class.getResourceAsStream(path);
        return new Scanner(Objects.requireNonNull(stream), StandardCharsets.UTF_8).useDelimiter("\\A").next();
    }

    private void push(Parent p) {
        Scene scene = new Scene(p, width, height);
        scenes.push(scene);
        show(scene);
    }

    private void show(Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void returnToLastScreen() throws IOException {
        scenes.pop();
        if (scenes.empty()) {
            mainScreen();
            return;
        }
        show(scenes.peek());
    }

    public void mainScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-screen.fxml"));
        fxmlLoader.setControllerFactory(ic -> new MainScreenController(this, subjects));
        push(fxmlLoader.load());
    }

    public void subjectScreen(Subject entry) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("subject-screen.fxml"));
        fxmlLoader.setControllerFactory(ic -> new SubjectScreenController(this, entry));
        push(fxmlLoader.load());
    }

    public void questionsScreen(List<Question> questions) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("questions-screen.fxml"));
        fxmlLoader.setControllerFactory(ic -> new QuestionsScreenController(this, questions));
        push(fxmlLoader.load());
    }
}