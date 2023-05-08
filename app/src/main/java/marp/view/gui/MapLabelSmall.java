package marp.view.gui;

import javafx.scene.control.Label;

public class MapLabelSmall extends Label {
    public MapLabelSmall(String name) {
        super(name);
        getStylesheets().addAll("CSS/darkmodesheet.css", "CSS/stylesheet.css");
        getStyleClass().add("map-label-small");
    }
}
