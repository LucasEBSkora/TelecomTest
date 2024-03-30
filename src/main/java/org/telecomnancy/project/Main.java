package org.telecomnancy.project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.telecomnancy.project.controller.MainScreenController;
import org.telecomnancy.project.controller.SubjectScreenController;
import org.telecomnancy.project.model.Subject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

public class Main extends Application {

    private Stage primaryStage;

    private final int width = 800;
    private final int height = 600;

    private Subject[] subjects;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;
        loadSubjects();
        stage.setTitle("Telecom Test");
        mainScreen();
    }

    private void loadSubjects() throws IOException {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        String text = resourceAsString("questions.json");
        subjects = gson.fromJson(text, Subject[].class);
    }

    private String resourceAsString(String path) {
        InputStream stream =  Main.class.getResourceAsStream(path);
        return new Scanner(Objects.requireNonNull(stream), StandardCharsets.UTF_8).useDelimiter("\\A").next();
    }

    private void show(Parent p) {
        Scene scene = new Scene(p, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void mainScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-screen.fxml"));
        fxmlLoader.setControllerFactory(ic -> new MainScreenController(this, subjects));
        show(fxmlLoader.load());
    }

    public void SubjectScreen(Subject entry) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("subject-screen.fxml"));
        fxmlLoader.setControllerFactory(ic -> new SubjectScreenController(this, entry));
        show(fxmlLoader.load());
    }
}