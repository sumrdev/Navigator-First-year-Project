package marp.view.gui.menugui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;
import marp.mapelements.Address;
import marp.mapelements.MapPoint;
import marp.mapelements.details.PointType;
import marp.model.Model;
import marp.view.gui.MapLabel;
import marp.view.gui.MapLabelSmall;
import marp.view.gui.SearchBar;
import marp.view.gui.buttons.MapButton;
import marp.view.gui.buttons.MapTextButton;
import marp.view.gui.buttons.MapToggleButton;

import java.util.Objects;

public class SelectedPointPanel extends MenuPanel {
    Address address;
    MapLabel selectedPointName;
    MapLabelSmall selectedPointType;

    private SearchBar searchBar;
    private MapButton directionsButton;
    private MapButton settingsButton;
    private MapButton minimizeButton;
    private MapToggleButton saveLocationButton;
    private MapTextButton directionsToSelectedPointButton;
    private MapPoint mapPoint;
    private MapButton searchButton;

    private VBox selectedPointInfo;
    private VBox buttonContainer;

    private TextArea notesTextArea;
    public TextArea getNotesTextArea() {
        return notesTextArea;
    }

    private Text takeNoteText;
    private Text takeNoteText2;
    private String selectedAddressString = "";

    public SelectedPointPanel(Model model) {
        super();

        // create buttons
        directionsButton = new MapButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/directions.png"))));
        settingsButton = new MapButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/settings.png"))));
        minimizeButton = new MapButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/cross.png"))));
        searchButton = new MapButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/search.png"))));

        // create search bar and fill it in with the current address
        searchBar = new SearchBar(model, 5);
        searchBar.setMinWidth(400);
        searchBar.setPromptText("Search the map");

        // Create text for displaying the name or address of the selected point
        selectedPointName = new MapLabel("Name");
        selectedPointName.setTextAlignment(TextAlignment.LEFT);
        // Create label for displaying the type of the selected point
        selectedPointType = new MapLabelSmall("Type");
        selectedPointType.setTextAlignment(TextAlignment.LEFT);

        // Create text area for notes
        notesTextArea = new TextArea();
        takeNoteText = new Text("Write notes for this Bookmark:");
        takeNoteText.setFont(Font.font("verdana", FontWeight.MEDIUM, FontPosture.REGULAR, 19)); 
        takeNoteText2 = new Text("Bookmark this point to save your notes\n(If already bookmarked, just press close to save your edit)");
        takeNoteText2.setFont(Font.font("verdana", FontWeight.MEDIUM, FontPosture.ITALIC, 11)); 

        // Create a buttons to bookmark the location and get directions
        saveLocationButton = new MapToggleButton(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/bookmark.png"))));
        directionsToSelectedPointButton = new MapTextButton("Get directions!");
        directionsToSelectedPointButton.setMinWidth(200);

        // Create container for buttons
        HBox saveAndDirectionsButtons = new HBox(saveLocationButton, directionsToSelectedPointButton);
        saveAndDirectionsButtons.setSpacing(20);
        saveAndDirectionsButtons.setAlignment(Pos.CENTER);

        // Create container for labels + buttons
        selectedPointInfo = new VBox(selectedPointName, selectedPointType, saveAndDirectionsButtons, takeNoteText, takeNoteText2, notesTextArea);
        selectedPointInfo.getStylesheets().addAll("CSS/darkmodesheet.css", "CSS/stylesheet.css");
        selectedPointInfo.getStyleClass().add("map-vbox");
        selectedPointInfo.setMaxHeight(500);
        selectedPointInfo.setMinWidth(400);
        selectedPointInfo.setMaxWidth(400);
        selectedPointInfo.setAlignment(Pos.CENTER_LEFT);

        // Create container for search bar + point info and buttons
        VBox searchBarAndInfo = new VBox(searchBar, selectedPointInfo);
        searchBarAndInfo.setSpacing(20);
        searchBarAndInfo.setPickOnBounds(false);

        // Create container to allign search button and minimize button over each other
        buttonContainer = new VBox(searchButton, minimizeButton);
        buttonContainer.setSpacing(20);

        // Add everything + generic navication buttons to menu panel
        this.getChildren().addAll(searchBarAndInfo, buttonContainer, directionsButton, settingsButton);
        this.setPadding(new Insets(20));
        this.setSpacing(10);
        this.setPickOnBounds(false);

    }

    public void setMapPoint(MapPoint mapPoint) {
        this.mapPoint = mapPoint;
        selectedPointName.setText(mapPoint.getName());
        selectedPointType.setText(mapPoint.getType().typeName);
        selectedAddressString = mapPoint.getName();
    }

    public void setSavePointButtonMode(PointType pointType) {
        saveLocationButton.setSelected(pointType == PointType.FAVOURITE);
    }

    /**
     * Changes CSS sheets if parameter is set to true
     * 
     * @param activate of type boolean
     */
    public void activateDarkMode(boolean activate) {
        if (activate) {
            if (selectedPointInfo.getStylesheets().contains("CSS/stylesheet.css")) {
                selectedPointInfo.getStylesheets().remove("CSS/stylesheet.css");
                selectedPointInfo.getStylesheets().add("CSS/darkmodesheet.css");
            }
        } else {
            if (selectedPointInfo.getStylesheets().contains("CSS/darkmodesheet.css")) {
                selectedPointInfo.getStylesheets().remove("CSS/darkmodesheet.css");
                selectedPointInfo.getStylesheets().add("CSS/stylesheet.css");
            }
        }

        directionsButton.activateDarkMode(activate);

        settingsButton.activateDarkMode(activate);

        minimizeButton.activateDarkMode(activate);

        minimizeButton.activateDarkMode(activate);

        saveLocationButton.activateDarkMode(activate);

        directionsToSelectedPointButton.activateDarkMode(activate);

        searchButton.activateDarkMode(activate);

        selectedPointName.activateDarkMode(activate);

        selectedPointType.activateDarkMode(activate);
    }

    public String getNote(){
        return notesTextArea.getText();
    }

    public SearchBar getSearchBar() {
        return searchBar;
    }

    public MapButton getDirectionsButton() {
        return directionsButton;
    }

    public MapButton getSettingsButton() {
        return settingsButton;
    }

    public MapButton getMinimizeButton() {
        return minimizeButton;
    }

    public MapToggleButton getSaveLocationButton() {
        return saveLocationButton;
    }

    public MapTextButton getDirectionsToSelectedPointButton() {
        return directionsToSelectedPointButton;
    }

    public MapPoint getMapPoint() {
        return mapPoint;
    }

    public MapButton getSearchButton() {
        return searchButton;
    }

    public VBox getSelectedPointInfo() {
        return selectedPointInfo;
    }

    public VBox getButtonContainer() {
        return buttonContainer;
    }

    public String getSelectedAddress(){
        return selectedAddressString;
    }
}