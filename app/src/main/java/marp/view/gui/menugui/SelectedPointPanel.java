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

    public VBox selectedPointInfo;
    public VBox buttonContainer;

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
        selectedPointInfo = new VBox(selectedPointName, selectedPointType, saveAndDirectionsButtons);
        selectedPointInfo.getStylesheets().addAll("CSS/darkmodesheet.css", "CSS/stylesheet.css");
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
        buttonContainer = new VBox(searchButton, minimizeButton);
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

    /**
     * Changes CSS sheets of SelectedPointPanel if parameter is set to true
     * @param set of type boolean
     */
    public void activateDarkMode(boolean set){
        if (set){
            if (selectedPointInfo.getStylesheets().contains("CSS/stylesheet.css")) {
                selectedPointInfo.getStylesheets().remove("CSS/stylesheet.css");
                selectedPointInfo.getStylesheets().add("CSS/darkmodesheet.css");
            }
            if (directionsToSelectedPointButton.getStylesheets().contains("CSS/stylesheet.css")) {
                directionsToSelectedPointButton.getStylesheets().remove("CSS/stylesheet.css");
                directionsToSelectedPointButton.getStylesheets().add("CSS/darkmodesheet.css");
            }
            if (buttonContainer.getStylesheets().contains("CSS/stylesheet.css")) {
                buttonContainer.getStylesheets().remove("CSS/stylesheet.css");
                buttonContainer.getStylesheets().add("CSS/darkmodesheet.css");
            }
            if (directionsButton.getStylesheets().contains("CSS/stylesheet.css")) {
                directionsButton.getStylesheets().remove("CSS/stylesheet.css");
                directionsButton.getStylesheets().add("CSS/darkmodesheet.css");
            }
            if (settingsButton.getStylesheets().contains("CSS/stylesheet.css")) {
                settingsButton.getStylesheets().remove("CSS/stylesheet.css");
                settingsButton.getStylesheets().add("CSS/darkmodesheet.css");
            }
            if (minimizeButton.getStylesheets().contains("CSS/stylesheet.css")) {
                minimizeButton.getStylesheets().remove("CSS/stylesheet.css");
                minimizeButton.getStylesheets().add("CSS/darkmodesheet.css");
            }
            if (searchButton.getStylesheets().contains("CSS/stylesheet.css")) {
                searchButton.getStylesheets().remove("CSS/stylesheet.css");
                searchButton.getStylesheets().add("CSS/darkmodesheet.css");
            }
            if (saveLocationButton.getStylesheets().contains("CSS/stylesheet.css")) {
                saveLocationButton.getStylesheets().remove("CSS/stylesheet.css");
                saveLocationButton.getStylesheets().add("CSS/darkmodesheet.css");
            }
        } else {
            if (selectedPointInfo.getStylesheets().contains("CSS/darkmodesheet.css")) {
                selectedPointInfo.getStylesheets().remove("CSS/darkmodesheet.css");
                selectedPointInfo.getStylesheets().add("CSS/stylesheet.css");
            }
            if (directionsToSelectedPointButton.getStylesheets().contains("CSS/darkmodesheet.css")) {
                directionsToSelectedPointButton.getStylesheets().remove("CSS/darkmodesheet.css");
                directionsToSelectedPointButton.getStylesheets().add("CSS/stylesheet.css");
            }
            if (buttonContainer.getStylesheets().contains("CSS/darkmodesheet.css")) {
                buttonContainer.getStylesheets().remove("CSS/darkmodesheet.css");
                buttonContainer.getStylesheets().add("CSS/stylesheet.css");
            }
            if (directionsButton.getStylesheets().contains("CSS/darkmodesheet.css")) {
                directionsButton.getStylesheets().remove("CSS/darkmodesheet.css");
                directionsButton.getStylesheets().add("CSS/stylesheet.css");
            }
            if (settingsButton.getStylesheets().contains("CSS/darkmodesheet.css")) {
                settingsButton.getStylesheets().remove("CSS/darkmodesheet.css");
                settingsButton.getStylesheets().add("CSS/stylesheet.css");
            }
            if (minimizeButton.getStylesheets().contains("CSS/darkmodesheet.css")) {
                minimizeButton.getStylesheets().remove("CSS/darkmodesheet.css");
                minimizeButton.getStylesheets().add("CSS/stylesheet.css");
            }
            if (searchButton.getStylesheets().contains("CSS/darkmodesheet.css")) {
                searchButton.getStylesheets().remove("CSS/darkmodesheet.css");
                searchButton.getStylesheets().add("CSS/stylesheet.css");
            }
            if (saveLocationButton.getStylesheets().contains("CSS/darkmodesheet.css")) {
                saveLocationButton.getStylesheets().remove("CSS/darkmodesheet.css");
                saveLocationButton.getStylesheets().add("CSS/stylesheet.css");
            }
        }
    }
}