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
    private MapToggleButton normalModeButton;
    private MapToggleButton nightModeButton;
    private MapToggleButton colorBlindModeButton;
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
    public MapToggleButton getColorBlindModeButton() {
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

        // Create the top row of buttons for map modes as toggle buttons to allow only
        // one selected mode at a time

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

        System.out.println("settingsMenuButtons stylesheets: " + settingsMenuButtons.getStylesheets());

        // Add the new UI elements to the menu
        this.getChildren().addAll(settingsMenuButtons, minimizeButton, directionsButton, settingsButton);
        this.setPadding(new Insets(20));
        this.setSpacing(10);
        this.setPickOnBounds(false);

    }

    /**
     * Changes CSS sheets of SettingsPanel if parameter is set to true
     * 
     * @param set of type boolean
     */
    public void activateDarkMode(boolean set) {
        if (set) {
            if (settingsMenuButtons.getStylesheets().contains("CSS/stylesheet.css")) {
                settingsMenuButtons.getStylesheets().remove("CSS/stylesheet.css");
                settingsMenuButtons.getStylesheets().add("CSS/darkmodesheet.css");
            }

            if (minimizeButton.getStylesheets().contains("CSS/stylesheet.css")) {
                minimizeButton.getStylesheets().remove("CSS/stylesheet.css");
                minimizeButton.getStylesheets().add("CSS/darkmodesheet.css");
            }

            if (directionsButton.getStylesheets().contains("CSS/stylesheet.css")) {
                directionsButton.getStylesheets().remove("CSS/stylesheet.css");
                directionsButton.getStylesheets().add("CSS/darkmodesheet.css");
            }

            if (settingsButton.getStylesheets().contains("CSS/stylesheet.css")) {
                settingsButton.getStylesheets().remove("CSS/stylesheet.css");
                settingsButton.getStylesheets().add("CSS/darkmodesheet.css");
            }

            if (dontShow.getStylesheets().contains("CSS/stylesheet.css")) {
                dontShow.getStylesheets().remove("CSS/stylesheet.css");
                dontShow.getStylesheets().add("CSS/darkmodesheet.css");
            }

            if (adjustZoomText.getStylesheets().contains("CSS/stylesheet.css")) {
                adjustZoomText.getStylesheets().remove("CSS/stylesheet.css");
                adjustZoomText.getStylesheets().add("CSS/darkmodesheet.css");
            }

            if (saveOrLoadtext.getStylesheets().contains("CSS/stylesheet.css")) {
                saveOrLoadtext.getStylesheets().remove("CSS/stylesheet.css");
                saveOrLoadtext.getStylesheets().add("CSS/darkmodesheet.css");
            }

            if (loadAnotherOSMButton.getStylesheets().contains("CSS/stylesheet.css")) {
                loadAnotherOSMButton.getStylesheets().remove("CSS/stylesheet.css");
                loadAnotherOSMButton.getStylesheets().add("CSS/darkmodesheet.css");
            }

            if (mapDisplaySettinsgText.getStylesheets().contains("CSS/stylesheet.css")) {
                mapDisplaySettinsgText.getStylesheets().remove("CSS/stylesheet.css");
                mapDisplaySettinsgText.getStylesheets().add("CSS/darkmodesheet.css");
            }

            if (hideAddressesCheckbox.getStylesheets().contains("CSS/stylesheet.css")) {
                hideAddressesCheckbox.getStylesheets().remove("CSS/stylesheet.css");
                hideAddressesCheckbox.getStylesheets().add("CSS/darkmodesheet.css");
            }

            if (hideLandmarkCheckbox.getStylesheets().contains("CSS/stylesheet.css")) {
                hideLandmarkCheckbox.getStylesheets().remove("CSS/stylesheet.css");
                hideLandmarkCheckbox.getStylesheets().add("CSS/darkmodesheet.css");
            }

            if (hideRoadsCheckbox.getStylesheets().contains("CSS/stylesheet.css")) {
                hideRoadsCheckbox.getStylesheets().remove("CSS/stylesheet.css");
                hideRoadsCheckbox.getStylesheets().add("CSS/darkmodesheet.css");
            }

            if (hideBuildingsCheckbox.getStylesheets().contains("CSS/stylesheet.css")) {
                hideBuildingsCheckbox.getStylesheets().remove("CSS/stylesheet.css");
                hideBuildingsCheckbox.getStylesheets().add("CSS/darkmodesheet.css");
            }

            if (hideTerrainCheckbox.getStylesheets().contains("CSS/stylesheet.css")) {
                hideTerrainCheckbox.getStylesheets().remove("CSS/stylesheet.css");
                hideTerrainCheckbox.getStylesheets().add("CSS/darkmodesheet.css");
            }

        } else {
            if (settingsMenuButtons.getStylesheets().contains("CSS/darkmodesheet.css")) {
                settingsMenuButtons.getStylesheets().remove("CSS/darkmodesheet.css");
                settingsMenuButtons.getStylesheets().add("CSS/stylesheet.css");
            }
            if (minimizeButton.getStylesheets().contains("CSS/darkmodesheet.css")) {
                minimizeButton.getStylesheets().remove("CSS/darkmodesheet.css");
                minimizeButton.getStylesheets().add("CSS/stylesheet.css");
            }
            if (directionsButton.getStylesheets().contains("CSS/darkmodesheet.css")) {
                directionsButton.getStylesheets().remove("CSS/darkmodesheet.css");
                directionsButton.getStylesheets().add("CSS/stylesheet.css");
            }
            if (settingsButton.getStylesheets().contains("CSS/darkmodesheet.css")) {
                settingsButton.getStylesheets().remove("CSS/darkmodesheet.css");
                settingsButton.getStylesheets().add("CSS/stylesheet.css");
            }
            if (loadAnotherOSMButton.getStylesheets().contains("CSS/darkmodesheet.css")) {
                loadAnotherOSMButton.getStylesheets().remove("CSS/darkmodesheet.css");
                loadAnotherOSMButton.getStylesheets().add("CSS/stylesheet.css");
            }
            if (dontShow.getStylesheets().contains("CSS/darkmodesheet.css")) {
                dontShow.getStylesheets().remove("CSS/darkmodesheet.css");
                dontShow.getStylesheets().add("CSS/stylesheet.css");
            }
            if (adjustZoomText.getStylesheets().contains("CSS/darkmodesheet.css")) {
                adjustZoomText.getStylesheets().remove("CSS/darkmodesheet.css");
                adjustZoomText.getStylesheets().add("CSS/stylesheet.css");
            }
            if (saveOrLoadtext.getStylesheets().contains("CSS/darkmodesheet.css")) {
                saveOrLoadtext.getStylesheets().remove("CSS/darkmodesheet.css");
                saveOrLoadtext.getStylesheets().add("CSS/stylesheet.css");
            }
            if (mapDisplaySettinsgText.getStylesheets().contains("CSS/darkmodesheet.css")) {
                mapDisplaySettinsgText.getStylesheets().remove("CSS/darkmodesheet.css");
                mapDisplaySettinsgText.getStylesheets().add("CSS/stylesheet.css");
            }



            if (hideAddressesCheckbox.getStylesheets().contains("CSS/stylesheet.css")) {
                hideAddressesCheckbox.getStylesheets().remove("CSS/stylesheet.css");
                hideAddressesCheckbox.getStylesheets().add("CSS/darkmodesheet.css");
            }

            if (hideLandmarkCheckbox.getStylesheets().contains("CSS/stylesheet.css")) {
                hideLandmarkCheckbox.getStylesheets().remove("CSS/stylesheet.css");
                hideLandmarkCheckbox.getStylesheets().add("CSS/darkmodesheet.css");
            }

            if (hideRoadsCheckbox.getStylesheets().contains("CSS/stylesheet.css")) {
                hideRoadsCheckbox.getStylesheets().remove("CSS/stylesheet.css");
                hideRoadsCheckbox.getStylesheets().add("CSS/darkmodesheet.css");
            }

            if (hideBuildingsCheckbox.getStylesheets().contains("CSS/stylesheet.css")) {
                hideBuildingsCheckbox.getStylesheets().remove("CSS/stylesheet.css");
                hideBuildingsCheckbox.getStylesheets().add("CSS/darkmodesheet.css");
            }

            if (hideTerrainCheckbox.getStylesheets().contains("CSS/stylesheet.css")) {
                hideTerrainCheckbox.getStylesheets().remove("CSS/stylesheet.css");
                hideTerrainCheckbox.getStylesheets().add("CSS/darkmodesheet.css");
            }

        }
    }
}