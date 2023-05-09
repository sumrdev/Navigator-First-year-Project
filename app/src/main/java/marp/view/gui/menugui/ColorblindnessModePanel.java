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

	private MapToggleButton normalButton;
	public MapToggleButton deuteranopiaButton;
	public MapToggleButton protanopiaButton;
	public MapToggleButton tritanopiaButton;
	public MapToggleButton monochromacyButton;
	public MapButton exitButton;
	private int buttonsMinWidth = 150;
	private VBox colorBlindModesDisplay;

	public MapToggleButton getNormalButton() {
		return normalButton;
	}

	public ColorblindnessModePanel() {
		super();
		exitButton = new MapButton(
				new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/menu.png"))));

		normalButton = new MapToggleButton("None");
		normalButton.setMinWidth(buttonsMinWidth);
		deuteranopiaButton = new MapToggleButton("Deuteranopia");
		deuteranopiaButton.setMinWidth(buttonsMinWidth);
		tritanopiaButton = new MapToggleButton("Tritanopia");
		tritanopiaButton.setMinWidth(buttonsMinWidth);
		protanopiaButton = new MapToggleButton("Protanopia");
		protanopiaButton.setMinWidth(buttonsMinWidth);
		monochromacyButton = new MapToggleButton("Monochromacy");
		monochromacyButton.setMinWidth(buttonsMinWidth);

		ToggleGroup colorBlindnessModeToggleGroup = new ToggleGroup();
		colorBlindnessModeToggleGroup.getToggles().addAll(normalButton, deuteranopiaButton, tritanopiaButton,
				protanopiaButton,
				monochromacyButton);
		colorBlindModesDisplay = new VBox(new MapLabel("Choose color blind mode:"), normalButton,
				deuteranopiaButton,
				tritanopiaButton, protanopiaButton, monochromacyButton);
		colorBlindModesDisplay.getStylesheets().addAll("CSS/darkmodesheet.css", "CSS/stylesheet.css");
		colorBlindModesDisplay.setMinWidth(400);
		colorBlindModesDisplay.setMaxHeight(400);
		colorBlindModesDisplay.setAlignment(Pos.TOP_CENTER);
		
		colorBlindModesDisplay.setPadding(new Insets(20));
		colorBlindModesDisplay.setSpacing(20);
		colorBlindModesDisplay.setAlignment(Pos.TOP_CENTER);
		colorBlindModesDisplay.getStyleClass().add("map-vbox");

		this.getChildren().addAll(colorBlindModesDisplay, exitButton);
		this.setPadding(new Insets(20));
		this.setSpacing(10);
		this.setPickOnBounds(false);
	}

	/**
	 * Changes CSS sheets of ColorblindnessModePanel if parameter is set to true
	 * 
	 * @param set
	 */
	public void activateDarkMode(boolean set) {
		if (set) {
			if (colorBlindModesDisplay.getStylesheets().contains("CSS/stylesheet.css")) {
                colorBlindModesDisplay.getStylesheets().remove("CSS/stylesheet.css");
                colorBlindModesDisplay.getStylesheets().add("CSS/darkmodesheet.css");
            }
			if (exitButton.getStylesheets().contains("CSS/stylesheet.css")) {
                exitButton.getStylesheets().remove("CSS/stylesheet.css");
                exitButton.getStylesheets().add("CSS/darkmodesheet.css");
            }
		} else {
			if (colorBlindModesDisplay.getStylesheets().contains("CSS/darkmodesheet.css")) {
                colorBlindModesDisplay.getStylesheets().remove("CSS/darkmodesheet.css");
                colorBlindModesDisplay.getStylesheets().add("CSS/stylesheet.css");
            }
			if (exitButton.getStylesheets().contains("CSS/darkmodesheet.css")) {
                exitButton.getStylesheets().remove("CSS/darkmodesheet.css");
                exitButton.getStylesheets().add("CSS/stylesheet.css");
            }
		}
	}
}
