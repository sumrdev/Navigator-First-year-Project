package marp.view.gui.menugui;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import marp.datastructures.Trie;
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
     * Changes CSS sheets of MinimizedPanel if parameter is set to true
     * 
     * @param set
     */
    public void activateDarkMode(boolean set) {
        if (set) {
            if (searchBar.getStylesheets().contains("CSS/stylesheet.css")) {
                searchBar.getStylesheets().remove("CSS/stylesheet.css");
                searchBar.getStylesheets().add("CSS/darkmodesheet.css");
            }
            if (directionsButton.getStylesheets().contains("CSS/stylesheet.css")) {
                directionsButton.getStylesheets().remove("CSS/stylesheet.css");
                directionsButton.getStylesheets().add("CSS/darkmodesheet.css");
            }
            if (settingsButton.getStylesheets().contains("CSS/stylesheet.css")) {
                settingsButton.getStylesheets().remove("CSS/stylesheet.css");
                settingsButton.getStylesheets().add("CSS/darkmodesheet.css");
            }
            if (takeSnapshotButton.getStylesheets().contains("CSS/stylesheet.css")) {
                takeSnapshotButton.getStylesheets().remove("CSS/stylesheet.css");
                takeSnapshotButton.getStylesheets().add("CSS/darkmodesheet.css");
            }
            if (minimizeButton.getStylesheets().contains("CSS/stylesheet.css")) {
                minimizeButton.getStylesheets().remove("CSS/stylesheet.css");
                minimizeButton.getStylesheets().add("CSS/darkmodesheet.css");
            }
            if (searchButton.getStylesheets().contains("CSS/stylesheet.css")) {
                searchButton.getStylesheets().remove("CSS/stylesheet.css");
                searchButton.getStylesheets().add("CSS/darkmodesheet.css");
            }
            if (pointOfInterestButton.getStylesheets().contains("CSS/stylesheet.css")) {
                pointOfInterestButton.getStylesheets().remove("CSS/stylesheet.css");
                pointOfInterestButton.getStylesheets().add("CSS/darkmodesheet.css");
            }

        } else {
            if (searchBar.getStylesheets().contains("CSS/darkmodesheet.css")) {
                searchBar.getStylesheets().remove("CSS/darkmodesheet.css");
                searchBar.getStylesheets().add("CSS/stylesheet.css");
            }
            if (directionsButton.getStylesheets().contains("CSS/darkmodesheet.css")) {
                directionsButton.getStylesheets().remove("CSS/darkmodesheet.css");
                directionsButton.getStylesheets().add("CSS/stylesheet.css");
            }
            if (settingsButton.getStylesheets().contains("CSS/darkmodesheet.css")) {
                settingsButton.getStylesheets().remove("CSS/darkmodesheet.css");
                settingsButton.getStylesheets().add("CSS/stylesheet.css");
            }
            if (takeSnapshotButton.getStylesheets().contains("CSS/darkmodesheet.css")) {
                takeSnapshotButton.getStylesheets().remove("CSS/darkmodesheet.css");
                takeSnapshotButton.getStylesheets().add("CSS/stylesheet.css");
            }
            if (minimizeButton.getStylesheets().contains("CSS/darkmodesheet.css")) {
                minimizeButton.getStylesheets().remove("CSS/darkmodesheet.css");
                minimizeButton.getStylesheets().add("CSS/stylesheet.css");
            }
            if (searchButton.getStylesheets().contains("CSS/darkmodesheet.css")) {
                searchButton.getStylesheets().remove("CSS/darkmodesheet.css");
                searchButton.getStylesheets().add("CSS/stylesheet.css");
            }
            if (pointOfInterestButton.getStylesheets().contains("CSS/darkmodesheet.css")) {
                pointOfInterestButton.getStylesheets().remove("CSS/darkmodesheet.css");
                pointOfInterestButton.getStylesheets().add("CSS/stylesheet.css");
            }
        }

    }
}