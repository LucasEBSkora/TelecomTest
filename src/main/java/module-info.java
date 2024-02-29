module project.main {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    exports org.telecomnancy.project;
    opens org.telecomnancy.project.controller to javafx.fxml;
    opens org.telecomnancy.project.model to com.google.gson;
}