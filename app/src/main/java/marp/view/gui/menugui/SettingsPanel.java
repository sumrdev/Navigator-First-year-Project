package marp.view.gui.menugui;

import javafx.geometry.Insets;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import marp.view.gui.MapLabel;
import marp.view.gui.MapLabelSmall;
import marp.view.gui.buttons.MapButton;
import marp.view.gui.buttons.MapCheckBox;
import marp.view.gui.buttons.MapTextButton;
import marp.view.gui.buttons.MapToggleButton;

import java.util.Objects;

public class SettingsPanel extends MenuPanel {

    public MapTextButton loadAnotherOSMButton;
    public MapTextButton saveMapButton;
    public MapButton directionsButton;
    public MapButton minimizeButton;
    public MapToggleButton normalModeButton;
    public MapToggleButton nightModeButton;
    public MapToggleButton colorBlindModeButton;
    public MapCheckBox hideAddressesCheckbox;
    public MapCheckBox hideLandmarkCheckbox;
    public MapCheckBox hideRoadsCheckbox;
    public MapCheckBox hideBuildingsCheckbox;
    public MapCheckBox hideTerrainCheckbox;
    public MapButton takeSnapshotButton;

    // slider for zoom adjustment
    public Slider zoomAdjustSlider;

    public SettingsPanel(MapMenu mapMenu) {
        super();

        //create buttons
        directionsButton = new MapButton(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/directions.png"))));
        minimizeButton = new MapButton(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/menu.png"))));
        MapButton settingsButton = new MapButton(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/settings.png"))), true);
        saveMapButton = new MapTextButton("Save map");
        saveMapButton.setMinWidth(160);
        loadAnotherOSMButton = new MapTextButton("Load another OSM file");
        loadAnotherOSMButton.setMinWidth(160);
        takeSnapshotButton = new MapButton(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/camera.png"))));

        //Create container for save and load buttons

        HBox saveAndLoadButtons = new HBox(saveMapButton, loadAnotherOSMButton);
        saveAndLoadButtons.setSpacing(20);

        //Create the top row of buttons for map modes as toggle buttons to allow only one selected mode at a time

        normalModeButton = new MapToggleButton("Normal Mode");
        normalModeButton.setMinWidth(110);
        nightModeButton = new MapToggleButton("Night Mode");
        nightModeButton.setMinWidth(100);
        colorBlindModeButton = new MapToggleButton("Color Blind Mode");
        colorBlindModeButton.setMinWidth(120);
        ToggleGroup mapModeToggleGroup = new ToggleGroup();
        mapModeToggleGroup.getToggles().addAll(normalModeButton, nightModeButton, colorBlindModeButton);
        HBox mapDisplayButtons = new HBox(normalModeButton, nightModeButton, colorBlindModeButton);
        mapDisplayButtons.setSpacing(10);

        //Create label and the checkbox row for items to hide on the map and add them to a container with zoom slider + label

        hideAddressesCheckbox = new MapCheckBox("     Addresses");
        hideLandmarkCheckbox = new MapCheckBox("     Points of Interest");
        hideRoadsCheckbox = new MapCheckBox("     Roads");
        hideBuildingsCheckbox = new MapCheckBox("     Buildings");
        hideTerrainCheckbox = new MapCheckBox("     Terrain");
        zoomAdjustSlider =  new Slider(1, 10, 1);
        VBox hideMapItemsCheckboxesAndZoomSettings = new VBox(new MapLabelSmall("Don't show:"), hideAddressesCheckbox,
                hideLandmarkCheckbox, hideRoadsCheckbox, hideBuildingsCheckbox, hideTerrainCheckbox, new MapLabel("Adjust zoom buttons strength"), zoomAdjustSlider);
        hideMapItemsCheckboxesAndZoomSettings.setSpacing(20);
        hideMapItemsCheckboxesAndZoomSettings.setPadding(new Insets(20));


        //Add all buttons to one VBox
        VBox settingsMenuButtons = new VBox(new MapLabel("Save or load map"), saveAndLoadButtons, new MapLabel("Map display settings"), mapDisplayButtons, hideMapItemsCheckboxesAndZoomSettings);
        settingsMenuButtons.getStylesheets().add("CSS/stylesheet.css");
        settingsMenuButtons.getStyleClass().add("map-vbox");
        settingsMenuButtons.setMaxHeight(400);
        settingsMenuButtons.setMinWidth(400);

        //Add the new UI elements to the menu
        this.getChildren().addAll(settingsMenuButtons, minimizeButton, directionsButton, settingsButton, takeSnapshotButton);
        this.setPadding(new Insets(20));
        this.setSpacing(10);
        this.setPickOnBounds(false);


    }
}