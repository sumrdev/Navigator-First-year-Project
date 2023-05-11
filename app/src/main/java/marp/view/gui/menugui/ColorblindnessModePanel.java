package marp.view.gui.menugui;

import javafx.geometry.Pos;
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
	private MapToggleButton deuteranopiaButton;
	private MapToggleButton protanopiaButton;
	private MapToggleButton tritanopiaButton;
	private MapToggleButton monochromacyButton;
	private MapButton exitButton;
	private int buttonsMinWidth = 150;
	private VBox colorBlindModesDisplay;
	private MapLabel chooseModeLabel;

	public MapToggleButton getNoneButton() {
		return normalButton;
	}

	public MapToggleButton getDeuteranopiaButton(){
		return deuteranopiaButton;
	}
	public MapToggleButton getProtanopiaButton(){
		return protanopiaButton;
	}
	public MapToggleButton getTritanopiaButton(){
		return tritanopiaButton;
	}
	public MapToggleButton getMonochromacyButton(){
		return monochromacyButton;
	}
	public MapButton getExitButton(){
		return exitButton;
	}

	public ColorblindnessModePanel() {
		super();
		chooseModeLabel = new MapLabel("Choose color blind mode:");

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
		monochromacyButton = new MapToggleButton("Monochromia");
		monochromacyButton.setMinWidth(buttonsMinWidth);

		ToggleGroup colorBlindnessModeToggleGroup = new ToggleGroup();
		colorBlindnessModeToggleGroup.getToggles().addAll(normalButton, deuteranopiaButton, tritanopiaButton,
				protanopiaButton,
				monochromacyButton);
		colorBlindModesDisplay = new VBox(chooseModeLabel, normalButton,
				deuteranopiaButton,
				tritanopiaButton, protanopiaButton, monochromacyButton);
		colorBlindModesDisplay.getStylesheets().addAll("CSS/darkmodesheet.css", "CSS/stylesheet.css");
		colorBlindModesDisplay.getStyleClass().add("map-vbox");
		colorBlindModesDisplay.setMinWidth(400);
		colorBlindModesDisplay.setMaxHeight(400);
		colorBlindModesDisplay.setAlignment(Pos.TOP_CENTER);

		colorBlindModesDisplay.setPadding(new Insets(20));
		colorBlindModesDisplay.setSpacing(20);
		colorBlindModesDisplay.setAlignment(Pos.TOP_CENTER);

		this.getChildren().addAll(colorBlindModesDisplay, exitButton);
		this.setPadding(new Insets(20));
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
			if (colorBlindModesDisplay.getStylesheets().contains("CSS/stylesheet.css")) {
				colorBlindModesDisplay.getStylesheets().remove("CSS/stylesheet.css");
				colorBlindModesDisplay.getStylesheets().add("CSS/darkmodesheet.css");
			}
		} else {
			if (colorBlindModesDisplay.getStylesheets().contains("CSS/darkmodesheet.css")) {
				colorBlindModesDisplay.getStylesheets().remove("CSS/darkmodesheet.css");
				colorBlindModesDisplay.getStylesheets().add("CSS/stylesheet.css");
			}
		}

		chooseModeLabel.activateDarkMode(activate);
		exitButton.activateDarkMode(activate);

	}
}
