package marp.view.gui.menugui;

import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import marp.view.gui.MapLabel;
import marp.view.gui.buttons.MapButton;
import marp.view.gui.buttons.MapToggleButton;
import java.util.Objects;

import javafx.geometry.Insets;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class ColorblindnessModePanel extends MenuPanel {

    public MapToggleButton deuteranopiaButton;
    public MapToggleButton protanopiaButton;
    public MapToggleButton tritanopiaButton;
    public MapToggleButton monochromacyButton;
    public MapButton exitButton;
    private int buttonsMinWidth = 20;

    public ColorblindnessModePanel() {
        super();
        exitButton = new MapButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/menu.png"))));

        deuteranopiaButton = new MapToggleButton("Deuteranopia");
        deuteranopiaButton.setMinWidth(buttonsMinWidth);
        deuteranopiaButton.setMinWidth(150);
        tritanopiaButton = new MapToggleButton("Tritanopia");
        tritanopiaButton.setMinWidth(buttonsMinWidth);
        tritanopiaButton.setMinWidth(150);
        protanopiaButton = new MapToggleButton("Protanopia");
        protanopiaButton.setMinWidth(buttonsMinWidth);
        protanopiaButton.setMinWidth(150);
        monochromacyButton = new MapToggleButton("Monochromacy");
        monochromacyButton.setMinWidth(buttonsMinWidth);
        monochromacyButton.setMinWidth(150);

        ToggleGroup colorBlindnessModeToggleGroup = new ToggleGroup();
        colorBlindnessModeToggleGroup.getToggles().addAll(deuteranopiaButton, tritanopiaButton, protanopiaButton,
                monochromacyButton);
        VBox colorBlindModesDisplay = new VBox(new MapLabel("Choose color blind mode:"), deuteranopiaButton,
                tritanopiaButton, protanopiaButton, monochromacyButton);
        colorBlindModesDisplay.getStylesheets().addAll("CSS/darkmodesheet.css", "CSS/stylesheet.css");
        colorBlindModesDisplay.setMinWidth(400);
        colorBlindModesDisplay.setAlignment(Pos.TOP_CENTER);
        colorBlindModesDisplay.setBackground(new Background(new BackgroundFill(Color.web("#efefef"), new CornerRadii(24), Insets.EMPTY)));
        colorBlindModesDisplay.setPadding(new Insets(20));
        colorBlindModesDisplay.setSpacing(20);
        colorBlindModesDisplay.setAlignment(Pos.TOP_CENTER);

        this.getChildren().addAll(colorBlindModesDisplay, exitButton);
        this.setPadding(new Insets(20));
        this.setSpacing(10);
        this.setPickOnBounds(false);
    }
}
