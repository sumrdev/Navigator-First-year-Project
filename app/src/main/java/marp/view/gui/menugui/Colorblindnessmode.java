package marp.view.gui.menugui;

import marp.view.gui.MapLabel;
import marp.view.gui.buttons.MapButton;
import marp.view.gui.buttons.MapToggleButton;
import java.util.Objects;

import javafx.geometry.Insets;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class Colorblindnessmode extends MenuPanel {

    public MapToggleButton deuteranopiaButton;
    public MapToggleButton protanopiaButton;
    public MapToggleButton tritanopiaButton;
    public MapToggleButton monochromacyButton;
    public MapButton exitButton;
    private int buttonsMinWidth = 20;

    /**
     * Constructor for creating buttons for enabling/disabling color modes
     * 
     * @param MapMenu
     */
    public Colorblindnessmode(MapMenu mapMenu) {
        super();
        exitButton = new MapButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/menu.png"))));

        deuteranopiaButton = new MapToggleButton("Deuteranopia");
        deuteranopiaButton.setMinWidth(buttonsMinWidth);
        tritanopiaButton = new MapToggleButton("Tritanopia");
        tritanopiaButton.setMinWidth(buttonsMinWidth);
        protanopiaButton = new MapToggleButton("Protanopia");
        protanopiaButton.setMinWidth(buttonsMinWidth);
        monochromacyButton = new MapToggleButton("Monochromacy");
        monochromacyButton.setMinWidth(buttonsMinWidth);

        ToggleGroup colorBlindnessModeToggleGroup = new ToggleGroup();
        colorBlindnessModeToggleGroup.getToggles().addAll(deuteranopiaButton, tritanopiaButton, protanopiaButton,
                monochromacyButton);
        VBox colorBlindModesDisplay = new VBox(exitButton, new MapLabel("Choose color blind mode:"), deuteranopiaButton,
                tritanopiaButton, protanopiaButton, monochromacyButton);
        colorBlindModesDisplay.setSpacing(10);

        colorBlindModesDisplay.getStylesheets().add("CSS/stylesheet.css");
        colorBlindModesDisplay.getStyleClass().add("map-vbox");
        colorBlindModesDisplay.setMaxWidth(400);
        colorBlindModesDisplay.setMaxHeight(400);

        this.getChildren().addAll(colorBlindModesDisplay);
        this.setPadding(new Insets(20));
        this.setSpacing(10);
        this.setPickOnBounds(false);
    }
}
