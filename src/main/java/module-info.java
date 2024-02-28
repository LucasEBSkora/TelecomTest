module project.main {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.json.bind;

    exports org.telecomnancy.project;
    opens org.telecomnancy.project.controller to javafx.fxml;
    opens org.telecomnancy.project.model to jakarta.json.bind;
}