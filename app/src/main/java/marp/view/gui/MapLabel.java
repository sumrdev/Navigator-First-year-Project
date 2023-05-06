package marp.view.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MapLabel extends Label {
    public MapLabel(String name) {
        super(name);
        getStylesheets().add("CSS/stylesheet.css");
        getStyleClass().add("map-label");
    }
}
