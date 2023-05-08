package marp.view.gui.buttons;

import javafx.scene.control.CheckBox;

public class MapCheckBox extends CheckBox {
    String name;
    public MapCheckBox(String name) {
        super(name);
        getStylesheets().addAll("CSS/darkmodesheet.css", "CSS/stylesheet.css");
        getStyleClass().add("map-check-box");
    }
}
