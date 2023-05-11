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

    private MapTextButton loadAnotherOSMButton;
    private MapButton directionsButton;
    private MapButton minimizeButton;
    private MapButton settingsButton;
    private MapButton takeSnapShotButton;
    
    private MapToggleButton normalModeButton;
    private MapToggleButton nightModeButton;
    private MapTextButton colorBlindModeButton;
    private MapCheckBox hideAddressesCheckbox;
    private MapCheckBox hideLandmarkCheckbox;
    private MapCheckBox hideRoadsCheckbox;
    private MapCheckBox hideBuildingsCheckbox;
    private MapCheckBox hideTerrainCheckbox;
    private VBox settingsMenuButtons;
    // slider for zoom adjustment
    private Slider zoomAdjustSlider;

    /**
     * 
     * @return MapTextButton loadAnotherOSMButton
     */
    public MapTextButton getLoadAnotherOSMButton() {
        return loadAnotherOSMButton;
    }
    
    /**
     * 
     * @return MapButton directionsButton
     */
    public MapButton getDirectionsButton() {
        return directionsButton;
    }
    
    /**
     * 
     * @return MapButton minimizeButton
     */
    public MapButton getMinimizeButton() {
        return minimizeButton;
    }
    
    /**
     * 
     * @return MapButton settingsButton
     */
    public MapButton getSettingsButton() {
        return settingsButton;
    }

    /**
     * 
     * @return MapToggleButton normalModeButton
     */
    public MapToggleButton getNormalModeButton() {
        return normalModeButton;
    }
    
    /**
     * 
     * @return MapToggleButton nightModeButton
     */
    public MapToggleButton getNightModeButton() {
        return nightModeButton;
    }
    
    /**
     * 
     * @return MapToggleButton colorBlindModeButton
     */
    public MapTextButton getColorBlindModeButton() {
        return colorBlindModeButton;
    }
    
    /**
     * 
     * @return MapCheckBox hideAddressesCheckbox
     */
    public MapCheckBox getHideAddressesCheckbox() {
        return hideAddressesCheckbox;
    }
    
    /**
     * 
     * @return MapCheckBox hideLandmarkCheckbox
     */
    public MapCheckBox getHideLandmarkCheckbox() {
        return hideLandmarkCheckbox;
    }
    
    /**
     * 
     * @return MapCheckBox hideRoadsCheckbox
     */
    public MapCheckBox getHideRoadsCheckbox() {
        return hideRoadsCheckbox;
    }
    
    /**
     * 
     * @return MapCheckBox hideBuildingsCheckbox
     */
    public MapCheckBox getHideBuildingsCheckbox() {
        return hideBuildingsCheckbox;
    }

    /**
     * 
     * @return MapCheckBox hideTerrainCheckbox
     */
    public MapCheckBox getHideTerrainCheckbox() {
        return hideTerrainCheckbox;
    }
    
    /**
     * 
     * @return VBox settingsMenuButtons
     */
    public VBox getSettingsMenuButtons() {
        return settingsMenuButtons;
    }
    
    /**
     * 
     * @return Slider zoomAdjustSlider
     */
    public Slider getZoomAdjustSlider() {
        return zoomAdjustSlider;
    }

    /**
     * 
     * @return MapButton takeSnapShotButton
     */
    public MapButton getTakeSnapShotButton() {
        return takeSnapShotButton;
    }
    
    // labels
    private MapLabelSmall dontShow;
    private MapLabel adjustZoomText;
    private MapLabel saveOrLoadtext;
    private MapLabel mapDisplaySettinsgText;
    
    public SettingsPanel() {
        super();
        
        // labels
        dontShow = new MapLabelSmall("Don't show:");
        adjustZoomText = new MapLabel("Adjust zoom buttons strength");
        saveOrLoadtext = new MapLabel("Load map");
        mapDisplaySettinsgText = new MapLabel("Map display settings");

        // create buttons
        directionsButton = new MapButton(
            new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/directions.png"))));
            minimizeButton = new MapButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/cross.png"))));
                settingsButton = new MapButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/settings.png"))), true);
        loadAnotherOSMButton = new MapTextButton("Open new map");
        loadAnotherOSMButton.setMinWidth(160);
        takeSnapShotButton = new MapButton(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/camera.png"))));

        // Create the top row of buttons for map modes as toggle buttons to allow only
        // one selected mode at a time

        normalModeButton = new MapToggleButton("Normal Mode");
        normalModeButton.setMinWidth(110);
        nightModeButton = new MapToggleButton("Night Mode");
        nightModeButton.setMinWidth(100);
        colorBlindModeButton = new MapTextButton("Color Blind Mode");
        colorBlindModeButton.setMinWidth(120);
        ToggleGroup mapModeToggleGroup = new ToggleGroup();
        mapModeToggleGroup.getToggles().addAll(normalModeButton, nightModeButton);
        HBox mapDisplayButtons = new HBox(normalModeButton, nightModeButton, colorBlindModeButton);
        mapDisplayButtons.setSpacing(10);

        // Create label and the checkbox row for items to hide on the map and add them
        // to a container with zoom slider + label

        hideAddressesCheckbox = new MapCheckBox("     Addresses");
        hideLandmarkCheckbox = new MapCheckBox("     Points of Interest");
        hideRoadsCheckbox = new MapCheckBox("     Roads");
        hideBuildingsCheckbox = new MapCheckBox("     Buildings");
        hideTerrainCheckbox = new MapCheckBox("     Terrain");
        zoomAdjustSlider = new Slider(1, 10, 5.5);
        VBox hideMapItemsCheckboxesAndZoomSettings = new VBox(dontShow, hideAddressesCheckbox,
                hideLandmarkCheckbox, hideRoadsCheckbox, hideBuildingsCheckbox, hideTerrainCheckbox,
                adjustZoomText, zoomAdjustSlider);
        hideMapItemsCheckboxesAndZoomSettings.setSpacing(20);
        hideMapItemsCheckboxesAndZoomSettings.setPadding(new Insets(20));

        // Add all buttons to one VBox
        settingsMenuButtons = new VBox(saveOrLoadtext, loadAnotherOSMButton,
                mapDisplaySettinsgText, mapDisplayButtons, hideMapItemsCheckboxesAndZoomSettings);
        settingsMenuButtons.getStylesheets().addAll("CSS/darkmodesheet.css", "CSS/stylesheet.css");
        settingsMenuButtons.getStyleClass().addAll("map-vbox");

        settingsMenuButtons.setMaxHeight(400);
        settingsMenuButtons.setMinWidth(400);

        // Add the new UI elements to the menu
        this.getChildren().addAll(settingsMenuButtons, minimizeButton, directionsButton, settingsButton, takeSnapShotButton);
        this.setPadding(new Insets(20));
        this.setSpacing(10);
        this.setPickOnBounds(false);

    }

    /**
     * Changes CSS sheets if parameter is set to true
     * 
     * @param activate (if activated then darkmode will be set)
     */
    public void activateDarkMode(boolean activate) {
        if (activate) {
            if (settingsMenuButtons.getStylesheets().contains("CSS/stylesheet.css")) {
                settingsMenuButtons.getStylesheets().remove("CSS/stylesheet.css");
                settingsMenuButtons.getStylesheets().add("CSS/darkmodesheet.css");
            }
        } else {
            if (settingsMenuButtons.getStylesheets().contains("CSS/darkmodesheet.css")) {
                settingsMenuButtons.getStylesheets().remove("CSS/darkmodesheet.css");
                settingsMenuButtons.getStylesheets().add("CSS/stylesheet.css");
            }
        }

        minimizeButton.activateDarkMode(activate);

        directionsButton.activateDarkMode(activate);

        settingsButton.activateDarkMode(activate);

        takeSnapShotButton.activateDarkMode(activate);

        dontShow.activateDarkMode(activate);

        adjustZoomText.activateDarkMode(activate);

        saveOrLoadtext.activateDarkMode(activate);

        loadAnotherOSMButton.activateDarkMode(activate);

        mapDisplaySettinsgText.activateDarkMode(activate);

        hideAddressesCheckbox.activateDarkMode(activate);

        hideLandmarkCheckbox.activateDarkMode(activate);

        hideRoadsCheckbox.activateDarkMode(activate);

        hideBuildingsCheckbox.activateDarkMode(activate);

        hideTerrainCheckbox.activateDarkMode(activate);

    }
}