package marp.view.gui.menugui;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import marp.datastructures.SimpleTrie;
import marp.model.Model;
import marp.view.gui.MapLabel;
import marp.view.gui.MapLabelSmall;
import marp.view.gui.SearchBar;
import marp.view.gui.buttons.MapButton;
import marp.view.gui.buttons.MapTextButton;

import java.util.Objects;

public class DirectionsPanel extends MenuPanel {
    public SearchBar startLocationField;
    public SearchBar endLocationField;
    public MapButton minimizeButton;
    public MapButton settingsButton;
    public MapButton directionsButton;
    public MapButton swapButton;
    public MapTextButton findRouteButton;
    public MapButton startSearchButton;
    public MapButton endSearchButton;


    // Constructor
    public DirectionsPanel(MapMenu mapMenu, Model model) {

        // Create buttons
        minimizeButton = new MapButton(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/menu.png"))));
        settingsButton = new MapButton(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/settings.png"))));
        directionsButton = new MapButton(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/directions.png"))), true);
        startSearchButton = new MapButton(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/search.png"))));
        endSearchButton = new MapButton(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/search.png"))));
        swapButton = new MapButton(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/swap.png"))));
        findRouteButton = new MapTextButton("Find the best route!");
        findRouteButton.setMinHeight(48);
        findRouteButton.setMinWidth(200);

        //Create new search bars for start and end locations
        startLocationField = new SearchBar(model, 5);
        endLocationField = new SearchBar(model, 5);
        startLocationField.setMinWidth(302);
        startLocationField.setPromptText("Where are you traveling from?");
        endLocationField.setMinWidth(302);
        endLocationField.setPromptText("Where are you traveling to?");

        //Create containers for search bars and buttons
        HBox startLocationSearchbarBox = new HBox(startLocationField, startSearchButton);
        startLocationSearchbarBox.setSpacing(10);
        HBox endLocationSearchbarBox = new HBox(endLocationField, endSearchButton);
        endLocationSearchbarBox.setSpacing(10);

        //Create containers for search bars with buttons and text
        VBox startSearchBarBox = new VBox(new MapLabelSmall("Start location: "), startLocationSearchbarBox);
        startSearchBarBox.setSpacing(5);
        VBox endSearchBarBox = new VBox(new MapLabelSmall("Destination: "), endLocationSearchbarBox);
        endSearchBarBox.setSpacing(5);

        // Create a VBox to hold start and end location search bars with buttons and labels, swap button, find route button, and labels
        VBox startAndEndLocation = new VBox(new MapLabel("Find the best route to your destination"), startSearchBarBox, swapButton, endSearchBarBox, findRouteButton);
        startAndEndLocation.getStylesheets().add("CSS/stylesheet.css");
        startAndEndLocation.getStyleClass().add("map-vbox");
        startAndEndLocation.setMaxHeight(400);

        //Add generic menu navigation buttons
        this.getChildren().addAll(startAndEndLocation, minimizeButton, directionsButton, settingsButton);
        this.setPadding(new Insets(20, 20, 20, 20));
        this.setSpacing(10);
        this.setPickOnBounds(false);
    }
}