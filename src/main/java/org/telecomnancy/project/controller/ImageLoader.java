package org.telecomnancy.project.controller;

import javafx.scene.image.Image;
import org.telecomnancy.project.Main;

import java.net.URL;

public class ImageLoader {
    public static Image load(String path) {
        if (path == null || path.isBlank())
            return null;

        // calls from Main.class because otherwise we would have to add it to a useless subfolder
        URL fullPath = Main.class.getResource(path);
        if (fullPath == null)
            return null;

        return new Image(fullPath.toExternalForm());
    }
}
