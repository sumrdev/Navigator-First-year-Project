package marp.view.gui.menugui;

import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import marp.view.gui.MapLabel;
import marp.view.gui.MapLabelSmall;
import marp.view.gui.buttons.MapButton;
import marp.view.gui.buttons.MapTextButton;

import java.util.Objects;

public class PointOfInterestPanel extends MenuPanel {
    private TextField pointNameField;

    private MapTextButton createPointButton;
    private MapButton cancelButton;
    private MapButton takeSnapShotButton;
    private MapLabel createPOILabel = new MapLabel("Create a new point of interest");
    private MapLabelSmall nameOfPOI = new MapLabelSmall("Name of your point of interest:");

    public MapButton getTakeSnapShotButton() {
        return takeSnapShotButton;
    }

    public TextField getPointNameField(){
        return pointNameField;
    }

    public MapTextButton getCreatePointButton(){
        return createPointButton;
    }

    public MapButton getCancelButton(){
        return cancelButton;
    }

    public VBox newPointMenu;

    // Constructor
    public PointOfInterestPanel() {

        // Create text field + button for inputting the name of the custom point
        pointNameField = new TextField();
        pointNameField.setMinWidth(360);
        pointNameField.setPromptText("Name your point of interest!");

        createPointButton = new MapTextButton("Create new point of interest");
        createPointButton.setMinHeight(48);
        createPointButton.setMinWidth(200);

        // create container + label
        VBox pointBox = new VBox(nameOfPOI, pointNameField);
        pointBox.setSpacing(5);

        // Create a VBox to hold the textfield and button
        newPointMenu = new VBox(createPOILabel, pointBox, createPointButton);
        newPointMenu.getStylesheets().addAll("CSS/darkmodesheet.css", "CSS/stylesheet.css");
        newPointMenu.getStyleClass().add("map-vbox");
        newPointMenu.setMaxHeight(250);

        // create cancel button
        cancelButton = new MapButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/cross.png"))));

        takeSnapShotButton = new MapButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/camera.png"))));

        // Add buttons and search bars to the panel
        this.getChildren().addAll(newPointMenu, cancelButton);
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
            if (newPointMenu.getStylesheets().contains("CSS/stylesheet.css")) {
                newPointMenu.getStylesheets().remove("CSS/stylesheet.css");
                newPointMenu.getStylesheets().add("CSS/darkmodesheet.css");
            }
        } else {
            if (newPointMenu.getStylesheets().contains("CSS/darkmodesheet.css")) {
                newPointMenu.getStylesheets().remove("CSS/darkmodesheet.css");
                newPointMenu.getStylesheets().add("CSS/stylesheet.css");
            }
        }

        createPointButton.activateDarkMode(activate);
        cancelButton.activateDarkMode(activate);
        takeSnapShotButton.activateDarkMode(activate);
        createPOILabel.activateDarkMode(activate);
        nameOfPOI.activateDarkMode(activate);

    }
}