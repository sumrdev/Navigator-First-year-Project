package marp.view.gui.menugui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import marp.model.Model;
import marp.view.gui.MapLabel;
import marp.view.gui.MapLabelSmall;
import marp.view.gui.SearchBar;
import marp.view.gui.buttons.MapButton;
import marp.view.gui.buttons.MapTextButton;
import marp.view.gui.buttons.MapToggleButton;

import java.util.List;
import java.util.Objects;

public class DirectionsPanel extends MenuPanel {
    private SearchBar startLocationField;
    private SearchBar endLocationField;
    private MapButton minimizeButton;
    private MapButton settingsButton;
    private MapButton takesnapshoButton;
    private MapButton directionsButton;
    private MapButton swapButton;
    private MapTextButton findRouteButton;
    private MapButton startSearchButton;
    private MapButton endSearchButton;
    private MapToggleButton carButton;
    private MapToggleButton walkButton;

    private MapToggleButton bikeButton;
    private ObservableList<String> guideList;
    private ListView<String> guideView;
    private MapLabelSmall distanceLabel;
    private MapLabelSmall timeLabel;
    private HBox distanceAndTime;

    private MapLabel findRouteLabel = new MapLabel("Find the best route to your destination");
    private MapLabelSmall startLocatLabelSmall = new MapLabelSmall("Start location: ");
    private MapLabelSmall destinationLabelSmall = new MapLabelSmall("Destination: ");
    private MapLabelSmall modeLabelSmall = new MapLabelSmall("Mode of transportation");

    VBox startAndEndLocation;

    // Constructor
    public DirectionsPanel(Model model) {

        // Create buttons
        minimizeButton = new MapButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/cross.png"))));
        settingsButton = new MapButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/settings.png"))));
        directionsButton = new MapButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/directions.png"))), true);
        takesnapshoButton = new MapButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/camera.png"))));
        startSearchButton = new MapButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/search.png"))));
        endSearchButton = new MapButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/search.png"))));
        swapButton = new MapButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/swap.png"))));
        findRouteButton = new MapTextButton("Find the best route!");
        findRouteButton.setMinHeight(48);
        findRouteButton.setMinWidth(200);

        // create buttons for transportation method
        carButton = new MapToggleButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/car.png"))));
        carButton.setSelected(true);
        walkButton = new MapToggleButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/walk.png"))));
        bikeButton = new MapToggleButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/bike.png"))));
        ToggleGroup mapModeToggleGroup = new ToggleGroup();
        mapModeToggleGroup.getToggles().addAll(carButton, walkButton, bikeButton);
        HBox modeOfTransportationButtons = new HBox(carButton, walkButton, bikeButton);
        modeOfTransportationButtons.setSpacing(30);
        modeOfTransportationButtons.setAlignment(Pos.CENTER);
        VBox modeOfTransportationContainer = new VBox(modeLabelSmall, modeOfTransportationButtons);
        modeOfTransportationContainer.setSpacing(10);

        // Create new search bars for start and end locations
        startLocationField = new SearchBar(model, 5);
        endLocationField = new SearchBar(model, 5);
        startLocationField.setMinWidth(302);
        startLocationField.setPromptText("Where are you traveling from?");
        endLocationField.setMinWidth(302);
        endLocationField.setPromptText("Where are you traveling to?");

        // Create containers for search bars and buttons
        HBox startLocationSearchbarBox = new HBox(startLocationField, startSearchButton);
        startLocationSearchbarBox.setSpacing(10);
        HBox endLocationSearchbarBox = new HBox(endLocationField, endSearchButton);
        endLocationSearchbarBox.setSpacing(10);

        // Create containers for search bars with buttons and text
        VBox startSearchBarBox = new VBox(startLocatLabelSmall, startLocationSearchbarBox);
        startSearchBarBox.setSpacing(5);
        VBox endSearchBarBox = new VBox(destinationLabelSmall, endLocationSearchbarBox);
        endSearchBarBox.setSpacing(5);

        // Create HBox with distance and estimated travel time.
        distanceLabel = new MapLabelSmall("5 km");
        HBox distanceLabelContainer = new HBox(distanceLabel);
        distanceLabelContainer.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(distanceLabelContainer, Priority.ALWAYS);

        timeLabel = new MapLabelSmall("30 min");
        HBox timeLabelContainer = new HBox(timeLabel);
        timeLabelContainer.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(timeLabelContainer, Priority.ALWAYS);

        distanceAndTime = new HBox(timeLabelContainer, distanceLabelContainer);
        distanceAndTime.setAlignment(Pos.CENTER);
        distanceAndTime.setVisible(false);

        // Create guide view to hold directions
        guideList = FXCollections.observableArrayList();
        guideView = new ListView<>();

        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();

        guideView.setOnMouseClicked((e) -> {
            if (e.getClickCount() == 2) {
                String selected = guideView.getSelectionModel().getSelectedItem();
                content.putString(selected);
                clipboard.setContent(content);
            }
        });

        guideView.getStylesheets().addAll("CSS/darkmodesheet.css", "CSS/stylesheet.css");
        guideView.getStyleClass().add("file-list");
        guideView.setMaxWidth(400);
        guideView.setPrefHeight(1000);
        guideView.setItems(guideList);
        guideView.setVisible(false);

        // Create a VBox to hold start and end location search bars with buttons and
        // labels, swap button, find route button, and labels
        startAndEndLocation = new VBox(findRouteLabel, startSearchBarBox, swapButton, endSearchBarBox,
                modeOfTransportationContainer, findRouteButton, distanceAndTime, guideView);
        startAndEndLocation.setMaxHeight(400);
        startSearchButton.setAlignment(Pos.TOP_CENTER);
        startAndEndLocation.setBackground(
                new Background(new BackgroundFill(Color.web("#efefef"), new CornerRadii(24), Insets.EMPTY)));
        startAndEndLocation.setPadding(new Insets(20));
        startAndEndLocation.setSpacing(20);
        startAndEndLocation.setAlignment(Pos.TOP_CENTER);

        startAndEndLocation.getStylesheets().addAll("CSS/darkmodesheet.css", "CSS/stylesheet.css");
        startAndEndLocation.getStyleClass().add("map-vbox");

        // Add generic menu navigation buttons
        this.getChildren().addAll(startAndEndLocation, minimizeButton, directionsButton, settingsButton,
                takesnapshoButton);
        this.setPadding(new Insets(20, 20, 40, 20));
        this.setSpacing(10);
        this.setPickOnBounds(false);
    }

    public void receiveGuideList(List<String> list) {
        guideList.clear();
        guideList.addAll(list);
    }

    public void setGuideShow(boolean shouldShow) {
        guideView.setVisible(shouldShow);
        distanceAndTime.setVisible(shouldShow);
        if (shouldShow) {
            startAndEndLocation.setMaxHeight(10000);
        } else {
            startAndEndLocation.setMaxHeight(400);
        }
    }

    public void updateDistanceAndTime(float dist, int time) {
        distanceLabel.setText(dist + " km");
        timeLabel.setText(time + " min");
    }

    /**
     * Changes CSS sheets if parameter is set to true
     * 
     * @param activate
     */
    public void activateDarkMode(boolean activate) {
        if (activate) {
            if (startAndEndLocation.getStylesheets().contains("CSS/stylesheet.css")) {
                startAndEndLocation.getStylesheets().remove("CSS/stylesheet.css");
                startAndEndLocation.getStylesheets().add("CSS/darkmodesheet.css");
            }
        } else {
            if (startAndEndLocation.getStylesheets().contains("CSS/darkmodesheet.css")) {
                startAndEndLocation.getStylesheets().remove("CSS/darkmodesheet.css");
                startAndEndLocation.getStylesheets().add("CSS/stylesheet.css");
            }
        }

        minimizeButton.activateDarkMode(activate);

        settingsButton.activateDarkMode(activate);

        takesnapshoButton.activateDarkMode(activate);

        directionsButton.activateDarkMode(activate);

        startSearchButton.activateDarkMode(activate);

        endSearchButton.activateDarkMode(activate);

        swapButton.activateDarkMode(activate);

        findRouteButton.activateDarkMode(activate);

        carButton.activateDarkMode(activate);

        walkButton.activateDarkMode(activate);

        bikeButton.activateDarkMode(activate);

        findRouteLabel.activateDarkMode(activate);

        startLocatLabelSmall.activateDarkMode(activate);

        destinationLabelSmall.activateDarkMode(activate);

        modeLabelSmall.activateDarkMode(activate);
    }

    public SearchBar getStartLocationField() {
        return startLocationField;
    }

    public SearchBar getEndLocationField() {
        return endLocationField;
    }

    public MapButton getMinimizeButton() {
        return minimizeButton;
    }

    public MapButton getSettingsButton() {
        return settingsButton;
    }

    public MapButton getTakesnapshoButton() {
        return takesnapshoButton;
    }

    public MapButton getDirectionsButton() {
        return directionsButton;
    }

    public MapButton getStartSearchButton() {
        return startSearchButton;
    }

    public MapButton getEndSearchButton() {
        return endSearchButton;
    }

    public MapButton getSwapButton() {
        return swapButton;
    }

    public MapTextButton getFindRouteButton() {
        return findRouteButton;
    }

    public MapToggleButton getCarButton() {
        return carButton;
    }

    public MapToggleButton getWalkButton() {
        return walkButton;
    }

    public MapToggleButton getBikeButton() {
        return bikeButton;
    }
}