package marp.view.gui.menugui;

import javafx.scene.layout.HBox;
import marp.datastructures.Trie;
import marp.model.Model;

public class MapMenu extends HBox {

    private MinimizedPanel minimizedPanel;
    private Trie simpleTrie;
    private DirectionsPanel directionsPanel;
    private SettingsPanel settingsPanel;
    private SelectedPointPanel selectedPointPanel;
    private PointOfInterestPanel pointOfInterestPanel;
    private ColorblindnessModePanel colorblindnessmode;

    public MapMenu(Model model) {

        //Set up the various menu panels
        minimizedPanel = new MinimizedPanel(model);
        directionsPanel = new DirectionsPanel(model);
        settingsPanel = new SettingsPanel();
        selectedPointPanel = new SelectedPointPanel(model);
        pointOfInterestPanel = new PointOfInterestPanel();
        colorblindnessmode = new ColorblindnessModePanel();

        //add the minimized menu as the initial menu panel
        this.getChildren().add(minimizedPanel);

        // Set the mouse transparent property of the map menu only for transparent part of the menu
        this.setPickOnBounds(false);
    }

    public void changeMenuPanel(MenuPanel menuPanel) {
        this.getChildren().clear();
        this.getChildren().add(menuPanel);
    }
    public MinimizedPanel getMinimizedPanel() {
        return minimizedPanel;
    }
    public DirectionsPanel getDirectionsPanel() {
        return directionsPanel;
    }
    public SettingsPanel getSettingsPanel() {
        return settingsPanel;
    }
    public SelectedPointPanel getSelectedPointPanel() {
        return selectedPointPanel;
    }
    public PointOfInterestPanel getPointOfInterestPanel() {
        return pointOfInterestPanel;
    }
    public ColorblindnessModePanel getColorblindnessModePanel() {
        return colorblindnessmode;
    }
}