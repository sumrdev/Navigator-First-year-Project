package marp.view.gui.buttons;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapButton extends Button {
    public MapButton(Image icon, boolean selected) {

        ImageView iconView = new ImageView(icon);
        iconView.setFitHeight(48);
        iconView.setFitWidth(48);
        setGraphic(iconView);

        getStylesheets().addAll("CSS/darkmodesheet.css", "CSS/stylesheet.css");
        if (!selected) {
            getStyleClass().add("map-button");
        } else {
            getStyleClass().add("map-button-pressed-down");
        }
    }
    public MapButton(Image icon) {
        this(icon, false);
    }
}