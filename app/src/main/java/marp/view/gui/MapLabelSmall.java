package marp.view.gui;

import javafx.scene.control.Label;

public class MapLabelSmall extends Label {
    public MapLabelSmall(String name) {
        super(name);
        getStylesheets().add("CSS/stylesheet.css");
        getStyleClass().add("map-label-small");
    }
}
