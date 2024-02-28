package org.telecomnancy.project;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.telecomnancy.project.controller.MainScreenController;
import org.telecomnancy.project.model.Subject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

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
        Jsonb jsonb = JsonbBuilder.create();
        InputStream stream = Objects.requireNonNull(Main.class.getResource("questions.json")).openStream();
        subjects = jsonb.fromJson(stream, Subject[].class);
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

}