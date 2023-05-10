package marp.view.gui.menugui;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import marp.model.Model;
import marp.view.gui.SearchBar;
import marp.view.gui.buttons.MapButton;

import java.util.Objects;

public class MinimizedPanel extends MenuPanel {
    public MapButton directionsButton;
    public MapButton settingsButton;
    public MapButton minimizeButton;
    public SearchBar searchBar;
    public MapButton searchButton;
    public MapButton pointOfInterestButton;
    public MapButton takeSnapshotButton;

    public MinimizedPanel(Model model) {

        // Set up the minimized menu
        searchBar = new SearchBar(model, 5);
        searchBar.setMinWidth(400);
        searchBar.setPromptText("Search the map");
        searchButton = new MapButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/search.png"))));
        minimizeButton = new MapButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/menu.png"))));
        directionsButton = new MapButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/directions.png"))));
        settingsButton = new MapButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/settings.png"))));
        takeSnapshotButton = new MapButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/camera.png"))));

        pointOfInterestButton = new MapButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/star.png"))));
        this.getChildren().addAll(searchBar, searchButton, directionsButton, settingsButton, pointOfInterestButton,
                takeSnapshotButton);
        this.setPadding(new Insets(20, 20, 20, 20));
        this.setSpacing(10);
        this.setPickOnBounds(false);

    }

    /**
     * Changes CSS sheets if parameter is set to true
     * 
     * @param activate
     */
    public void activateDarkMode(boolean activate) {
        if (activate) {
            if (searchBar.getStylesheets().contains("CSS/stylesheet.css")) {
                searchBar.getStylesheets().remove("CSS/stylesheet.css");
                searchBar.getStylesheets().add("CSS/darkmodesheet.css");
            }
        } else {
            if (searchBar.getStylesheets().contains("CSS/darkmodesheet.css")) {
                searchBar.getStylesheets().remove("CSS/darkmodesheet.css");
                searchBar.getStylesheets().add("CSS/stylesheet.css");
            }
        }

        directionsButton.activateDarkMode(activate);

        settingsButton.activateDarkMode(activate);

        minimizeButton.activateDarkMode(activate);

        searchButton.activateDarkMode(activate);

        pointOfInterestButton.activateDarkMode(activate);

        takeSnapshotButton.activateDarkMode(activate);
    }
}