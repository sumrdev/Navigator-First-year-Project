package marp.view.GUIElements;

import javafx.scene.control.Button;

public class MapButtonText extends Button {
    public MapButtonText(String name) {
        super(name);
        getStylesheets().add("CSS/stylesheet.css");
        getStyleClass().add("map-button");
    }
}
