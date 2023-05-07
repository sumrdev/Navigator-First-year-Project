package marp.view.gui.menugui;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import marp.datastructures.SimpleTrie;
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
        searchButton = new MapButton(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/search.png"))));
        minimizeButton = new MapButton(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/menu.png"))));
        directionsButton = new MapButton(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/directions.png"))));
        settingsButton = new MapButton(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/settings.png"))));
        takeSnapshotButton = new MapButton(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/camera.png"))));

        pointOfInterestButton = new MapButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/star.png"))));
        this.getChildren().addAll(searchBar, searchButton, directionsButton, settingsButton, pointOfInterestButton, takeSnapshotButton);
        this.setPadding(new Insets(20, 20, 20, 20));
        this.setSpacing(10);
        this.setPickOnBounds(false);

    }

}