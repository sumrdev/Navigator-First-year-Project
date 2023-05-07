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

public class PointOfInterestPanel  extends MenuPanel {
    public TextField pointNameField;

    public MapTextButton createPointButton;
    public MapButton cancelButton;

    // Constructor
    public PointOfInterestPanel(MapMenu mapMenu) {


        //Create text field + button for inputting the name of the custom point
        pointNameField = new TextField();
        pointNameField.setMinWidth(360);
        pointNameField.setPromptText("Name your point of interest!");

        createPointButton = new MapTextButton("Create new point of interest");
        createPointButton.setMinHeight(48);
        createPointButton.setMinWidth(200);

        //create container + label
        VBox pointBox = new VBox(new MapLabelSmall("Name of your point of interest:"), pointNameField);
        pointBox.setSpacing(5);

        // Create a VBox to hold the textfield and button
        VBox newPointMenu = new VBox(new MapLabel("Create a new point of interest"), pointBox, createPointButton);
        newPointMenu.getStylesheets().add("CSS/stylesheet.css");
        newPointMenu.getStyleClass().add("map-vbox");
        newPointMenu.setMaxHeight(250);

        //create cancel button
        cancelButton = new MapButton(  new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/cross.png"))));

        // Add buttons and search bars to the panel
        this.getChildren().addAll(newPointMenu, cancelButton);
        this.setPadding(new Insets(20, 20, 20, 20));
        this.setSpacing(10);
        this.setPickOnBounds(false);
    }
}