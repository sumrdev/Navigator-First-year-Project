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

    /**
     * Changes CSS sheets of this MapButton if parameter is set to true
     * 
     * @param activate (if activated then darkmode will be set)
     */
    public void activateDarkMode(boolean activate) {
        if (activate) {
            if (getStylesheets().contains("CSS/stylesheet.css")) {
                getStylesheets().remove("CSS/stylesheet.css");
                getStylesheets().add("CSS/darkmodesheet.css");
            }

        } else {
            if (getStylesheets().contains("CSS/darkmodesheet.css")) {
                getStylesheets().remove("CSS/darkmodesheet.css");
                getStylesheets().add("CSS/stylesheet.css");
            }
        }
    }
}