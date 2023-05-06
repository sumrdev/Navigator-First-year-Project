package marp.view.gui.buttons;

import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapToggleButton extends ToggleButton {
    public MapToggleButton(String name) {
        super(name);
        this.getStyleClass().add("map-button");
    }

    public MapToggleButton(Image icon) {

        ImageView iconView = new ImageView(icon);
        iconView.setFitHeight(48);
        iconView.setFitWidth(48);
        setGraphic(iconView);

        getStylesheets().add("CSS/stylesheet.css");
        getStyleClass().add("map-button");

    }
}