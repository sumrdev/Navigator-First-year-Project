package marp.view.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MapLabel extends Label {
    public MapLabel(String name) {
        super(name);
        getStylesheets().addAll("CSS/darkmodesheet.css", "CSS/stylesheet.css");
        getStyleClass().add("map-label");
    }
}
