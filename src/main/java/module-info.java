module org.telecomnancy.project {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.telecomnancy.project to javafx.fxml;
    exports org.telecomnancy.project;
}