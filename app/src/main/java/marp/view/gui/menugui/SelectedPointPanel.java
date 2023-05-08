package marp.view.gui.menugui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import marp.mapelements.Address;
import marp.mapelements.MapPoint;
import marp.model.Model;
import marp.view.gui.MapLabel;
import marp.view.gui.MapLabelSmall;
import marp.view.gui.SearchBar;
import marp.view.gui.buttons.MapButton;
import marp.view.gui.buttons.MapTextButton;
import marp.view.gui.buttons.MapToggleButton;

import java.util.Objects;

public class SelectedPointPanel  extends MenuPanel {
    Address address;
    Label selectedPointName;
    Label selectedPointType;
    public SearchBar searchBar;
    public MapButton directionsButton;
    public MapButton settingsButton;
    public MapButton minimizeButton;
    public MapToggleButton saveLocationButton;
    public MapTextButton directionsToSelectedPointButton;
    public MapPoint mapPoint;
    public MapButton searchButton;


    public SelectedPointPanel(Model model) {
        super();


        //create buttons
        directionsButton = new MapButton(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/directions.png"))));
        settingsButton = new MapButton(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/settings.png"))));
        minimizeButton = new MapButton(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/cross.png"))));
        searchButton = new MapButton(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/search.png"))));


        //create search bar and fill it in with the current address
        searchBar = new SearchBar(model, 5);
        searchBar.setMinWidth(400);
        searchBar.setPromptText("Search the map");

        //Create text for displaying the name or address of the selected point
        selectedPointName = new MapLabel("Name");
        selectedPointName.setTextAlignment(TextAlignment.LEFT);
        //Create label for displaying the type of the selected point
        selectedPointType = new MapLabelSmall("Type");
        selectedPointType.setTextAlignment(TextAlignment.LEFT);

        //Create a buttons to bookmark the location and get directions
        saveLocationButton = new MapToggleButton(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/bookmark.png"))));
        directionsToSelectedPointButton = new MapTextButton("Get directions!");
        directionsToSelectedPointButton.setMinWidth(200);

        // Create container for buttons
        HBox saveAndDirectionsButtons = new HBox(saveLocationButton, directionsToSelectedPointButton);
        saveAndDirectionsButtons.setSpacing(20);
        saveAndDirectionsButtons.setAlignment(Pos.CENTER);

        //Create container for labels + buttons
        VBox selectedPointInfo = new VBox(selectedPointName, selectedPointType, saveAndDirectionsButtons);
        selectedPointInfo.getStylesheets().add("CSS/stylesheet.css");
        selectedPointInfo.getStyleClass().add("map-vbox");
        selectedPointInfo.setMaxHeight(500);
        selectedPointInfo.setMinWidth(400);
        selectedPointInfo.setMaxWidth(400);
        selectedPointInfo.setAlignment(Pos.CENTER_LEFT);

        //Create container for search bar + point info and buttons
        VBox searchBarAndInfo = new VBox(searchBar, selectedPointInfo);
        searchBarAndInfo.setSpacing(20);
        searchBarAndInfo.setPickOnBounds(false);

        //Create container to allign search button and minimize button over each other
        VBox buttonContainer = new VBox(searchButton, minimizeButton);
        buttonContainer.setSpacing(20);

        //Add everything + generic navication buttons to menu panel
        this.getChildren().addAll(searchBarAndInfo, buttonContainer, directionsButton, settingsButton);
        this.setPadding(new Insets(20));
        this.setSpacing(10);
        this.setPickOnBounds(false);

    }
    public void setMapPoint(MapPoint mapPoint) {
        this.mapPoint = mapPoint;
        selectedPointName.setText(mapPoint.getName());
        selectedPointType.setText(mapPoint.getType().typeName);

    }
    public void setSavePointButtonMode(boolean isFavourite) {
        if (!isFavourite) {
            saveLocationButton.setSelected(false);
        } else {
            saveLocationButton.setSelected(true);
        }
    }
}