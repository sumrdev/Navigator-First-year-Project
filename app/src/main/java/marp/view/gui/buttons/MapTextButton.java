package marp.view.gui.buttons;

import javafx.scene.control.Button;

public class MapTextButton extends Button {
    public MapTextButton(String name) {
        super(name);

        getStylesheets().addAll("CSS/darkmodesheet.css", "CSS/stylesheet.css");
        getStyleClass().add("map-button");
    }

    /**
     * Changes CSS sheets of this MapTextButton if parameter is set to true
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
