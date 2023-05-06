package marp.view.gui.buttons;

import javafx.scene.control.Button;

public class MapTextButton extends Button {
    public MapTextButton(String name) {
        super(name);

        getStylesheets().add("CSS/stylesheet.css");
        getStyleClass().add("map-button");
    }
}