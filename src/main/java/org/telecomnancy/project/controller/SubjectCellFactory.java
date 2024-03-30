package org.telecomnancy.project.controller;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.telecomnancy.project.Main;
import org.telecomnancy.project.model.Subject;

import java.net.URL;

public class SubjectCellFactory implements Callback<ListView<Subject>, ListCell<Subject>> {
    @Override
    public ListCell<Subject> call(ListView<Subject> subjectListView) {
        return new ListCell<>() {

            @Override
            public void updateItem(Subject entry, boolean empty) {
                super.updateItem(entry, empty);
                if (empty || entry == null) {
                    setText(null);
                } else {
                    loadImage(entry.imagePath);
                    setText(entry.name);
                }
            }

            private void loadImage(String path) {
                if (path == null || path.isBlank())
                    return;

                // calls from Main.class because otherwise we would have to add it to a useless subfolder
                URL fullPath = Main.class.getResource(path);
                if (fullPath == null)
                    return;

                Image imageLoaded = new Image(fullPath.toExternalForm());
                ImageView iView = new ImageView(imageLoaded);
                setGraphic(iView);
            }

        };

    }
}
