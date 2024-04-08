package org.telecomnancy.project.controller;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.telecomnancy.project.model.Subject;

public class SubjectCellFactory implements Callback<ListView<Subject>, ListCell<Subject>> {

    private static final double thumbnailHeight = 100;
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
                    setText(entry.name + " - " + entry.description);
                }
            }

            private void loadImage(String path) {
                Image imageLoaded = ImageLoader.load(path);
                if (imageLoaded == null) return;
                ImageView iView = new ImageView(imageLoaded);
                iView.setPreserveRatio(true);
                iView.setFitHeight(thumbnailHeight);
                setGraphic(iView);
            }

        };

    }
}
