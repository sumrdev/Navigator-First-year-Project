package marp.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import javafx.geometry.Point2D;
import javafx.print.*;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import marp.datastructures.Digraph;
import marp.mapelements.*;
import marp.model.Model;
import marp.mapelements.details.*;
import marp.utilities.IOFunctions;
import marp.view.View;

public class Controller {
    Model model;
    View view;

    float lastX;
    float lastY;

    List<String> fileList = new ArrayList<>();
    FileChooser fileChooser = new FileChooser();

    Stage stage;

    double zoomFactor = 1.0;

    private float mouseDragStartPositionX;
    private float mouseDragStartPositionY;
    private boolean isCreatingCustomPointOfInterest = false;

    public Controller(View view, Model model) throws MalformedURLException {
        this.model = model;
        this.view = view;
        fileList = IOFunctions.getFileNames();
        setFileChooser();
        this.stage = view.getPrimaryStage();
        createChooseMapSceneButtons();
    }

    private void setFileChooser() {
        this.fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("OSM files", "*.osm"),
                new FileChooser.ExtensionFilter("BIN files", "*.bin"),
                new FileChooser.ExtensionFilter("ZIP files", "*.zip"),
                new FileChooser.ExtensionFilter("All files", "*.*"));
    }

    private void createMapSceneButtons() {
        // Clicking on the map updates the latest clicked coordinates. if
        // isCreatingCustomPointOfInterest is true, lastPressedX is set as well.

        view.getCanvas().setOnMouseMoved(e -> {
            view.getNearestRoadInfo().setRoadNameText(model.getNearestRoadNameForMapSelection(
                    view.getMapScene().screenCoordsToMapCoords(new Point2D(e.getX(), e.getY()))));
        });
        view.getCanvas().setOnMousePressed(e -> {
            lastX = (float) e.getX();
            lastY = (float) e.getY();
            // if (isCreatingCustomPointOfInterest){
            mouseDragStartPositionX = lastX;
            mouseDragStartPositionY = lastY;
            // }
        });
        view.getCanvas().setOnMouseReleased(e -> {
            // When releasing the mouse, if the mouse drag start position is equal to the
            // current position, it counts as a mouse click.
            if (mouseDragStartPositionX == (float) e.getX() && mouseDragStartPositionY == (float) e.getY()) {
                // There are two cases: Either we are making a new POI or we are selecting a
                // point.
                if (isCreatingCustomPointOfInterest) {
                    view.getMapMenu().changeMenuPanel(view.getMapMenu().getPointOfInterestPanel());
                    toggleIsCreatingCustomPointOfInterest();
                } else {
                    // convert the screen coords of the mouse click to map coords and find the
                    // nearest selectable element.
                    Point2D point = view.getMapScene().screenCoordsToMapCoords(new Point2D(lastX, lastY));
                    MapPoint nearestPoint = model.getNearestPointForMapSelection(point);
                    if (view.getMapMenu().getDirectionsPanel().endLocationField.isFocused()) {
                        view.getMapMenu().getDirectionsPanel().endLocationField.setText(closestAddressToPointAsString(nearestPoint));
                        setEndLocation(view.getMapMenu().getDirectionsPanel().endLocationField.getAddress(), false);
                        model.getMapObjects().clearRoute();
                        view.getMapScene().redraw();
                    } else if (view.getMapMenu().getDirectionsPanel().startLocationField.isFocused()) {
                        view.getMapMenu().getDirectionsPanel().startLocationField.setText(closestAddressToPointAsString(nearestPoint));
                        model.getMapObjects().clearRoute();
                        view.getMapScene().redraw();
                        setStartLocation(view.getMapMenu().getDirectionsPanel().startLocationField.getAddress(), false);
                    } else {
                    model.setSelectedPoint(nearestPoint);
                    // focus on the point without panning
                    focusOnPoint(nearestPoint, false, false);
                    // set marker point on selected point.
                    PointOfInterest pointMarker = new PointOfInterest("", PointType.SELECTED,
                            (float) (nearestPoint.getX() / 0.56), -nearestPoint.getY(), false);
                    model.setSelectedPointMarker(new PointOfInterest("", PointType.SELECTED,
                            (float) (nearestPoint.getX() / 0.56), -nearestPoint.getY(), false));
                    view.getMapScene().redraw();
                    }
                }
            } else if(e.getButton() == MouseButton.SECONDARY) {
                view.getMapScene().redraw();
                view.getMapScene().drawUserMadeLine(new Point2D(mouseDragStartPositionX, mouseDragStartPositionY), new Point2D(e.getX(), e.getY()));
            }
        });

        view.getCanvas().setOnMouseDragged(e -> {
            if(e.getButton() == MouseButton.PRIMARY) {
                // When dragging the mouse we pan with the difference in mouse x and y of the
                // mouse position.
                float dx = (float) (e.getX() - lastX);
                float dy = (float) (e.getY() - lastY);
                view.getMapScene().pan(dx, dy);
                view.getMapScene().redraw();
                // Update last x and y
                lastX = (float) e.getX();
                lastY = (float) e.getY();
            }
        });

        view.getCanvas().setOnScroll(e -> {
            double factor = e.getDeltaY();
            view.getMapScene().zoom((float) e.getX(), (float) e.getY(), (float) Math.pow(1.01, factor));
            view.getMapScene().redraw();
        });

        // ##########################################################
        // ############# Zoom menu buttons ##########################
        // ##########################################################

        view.getZoomMenu().zoomIn.setOnAction(e -> {
            view.getMapScene().zoom(500, 350, 1.2 * view.getZoomMenu().getZoomMultiplier());
            view.getMapScene().redraw();
        });

        view.getZoomMenu().zoomOut.setOnAction(e -> {
            view.getMapScene().zoom(500F, 350F, (float) (0.8 / view.getZoomMenu().getZoomMultiplier()));
            view.getMapScene().redraw();
        });

        // ##########################################################
        // ############# Minimized panel buttons ####################
        // ##########################################################

        view.getMapMenu().getMinimizedPanel().directionsButton.setOnAction(e -> {
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getDirectionsPanel());
        });
        view.getMapMenu().getMinimizedPanel().settingsButton.setOnAction(e -> {
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getSettingsPanel());
        });
        view.getMapMenu().getMinimizedPanel().searchBar.setOnAction(e -> {
            focusOnPoint(view.getMapMenu().getMinimizedPanel().searchBar.getAddress(), true, true);
            view.getMapMenu().getMinimizedPanel().searchBar.clear();
        });
        view.getMapMenu().getMinimizedPanel().searchButton.setOnAction(e -> {
            focusOnPoint(view.getMapMenu().getMinimizedPanel().searchBar.getAddress(), true, true);
            view.getMapMenu().getMinimizedPanel().searchBar.clear();
        });
        view.getMapMenu().getMinimizedPanel().pointOfInterestButton.setOnAction(e -> {
            toggleIsCreatingCustomPointOfInterest();
        });

        view.getMapMenu().getMinimizedPanel().takeSnapshotButton.setOnAction(e -> {
            takeSnapShot();
        });

        // ##########################################################
        // ############# Directions panel buttons ###################
        // ##########################################################

        view.getMapMenu().getDirectionsPanel().minimizeButton.setOnAction(e -> {
            // Set the menu panel to the minimized menu panel
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getMinimizedPanel());
            view.getMapMenu().getDirectionsPanel().setGuideShow(false);

            // Set the selectedPointMarker to null so no selected point is shown when the
            // minimized menu is shown.
            model.setSelectedPointMarker(null);
        });

        view.getMapMenu().getDirectionsPanel().settingsButton.setOnAction(e -> {
            view.getMapMenu().getDirectionsPanel().setGuideShow(false);

            // Set the menu panel to the settings menu panel
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getSettingsPanel());
        });

        view.getMapMenu().getDirectionsPanel().swapButton.setOnAction(e -> {
            // First get the start location field text and save it as temp text,
            // then set ge start location field text to the text from the end location
            // field,
            // then set the temp text in the end location field.
            String tempText = view.getMapMenu().getDirectionsPanel().startLocationField.getText();
            view.getMapMenu().getDirectionsPanel().startLocationField
                    .setText(view.getMapMenu().getDirectionsPanel().endLocationField.getText());
            view.getMapMenu().getDirectionsPanel().endLocationField.setText(tempText);
        });
        view.getMapMenu().getDirectionsPanel().startLocationField.setOnAction(e -> {
            if (view.getMapMenu().getDirectionsPanel().startLocationField.getAddress() != null) {
                setStartLocation(view.getMapMenu().getDirectionsPanel().startLocationField.getAddress(), true);
                view.getMapMenu().getMinimizedPanel().searchBar.clear();
                model.getMapObjects().clearRoute();
                view.getMapScene().redraw();
            }
        });
        view.getMapMenu().getDirectionsPanel().startSearchButton.setOnAction(e -> {
            if (view.getMapMenu().getDirectionsPanel().startLocationField.getAddress() != null) {
                setStartLocation(view.getMapMenu().getDirectionsPanel().startLocationField.getAddress(), true);
                view.getMapMenu().getMinimizedPanel().searchBar.clear();
                model.getMapObjects().clearRoute();
                view.getMapScene().redraw();
            }
        });
        view.getMapMenu().getDirectionsPanel().endLocationField.setOnAction(e -> {
            if (view.getMapMenu().getDirectionsPanel().endLocationField.getAddress() != null) {
                setEndLocation(view.getMapMenu().getDirectionsPanel().endLocationField.getAddress(), true);
                view.getMapMenu().getMinimizedPanel().searchBar.clear();
                model.getMapObjects().clearRoute();
                view.getMapScene().redraw();
            }
        });
        view.getMapMenu().getDirectionsPanel().endSearchButton.setOnAction(e -> {
            if (view.getMapMenu().getDirectionsPanel().endLocationField.getAddress() != null) {
                setEndLocation(view.getMapMenu().getDirectionsPanel().endLocationField.getAddress(), true);
                view.getMapMenu().getMinimizedPanel().searchBar.clear();
                model.getMapObjects().clearRoute();
                view.getMapScene().redraw();
            }
        });
        view.getMapMenu().getDirectionsPanel().carButton.setOnAction(e -> {
            model.transportMode = 0;
            model.getMapObjects().getDigraph().setDriving();
        });
        view.getMapMenu().getDirectionsPanel().walkButton.setOnAction(e -> {
            model.transportMode = 1;
            model.getMapObjects().getDigraph().setWalking();
        });
        view.getMapMenu().getDirectionsPanel().bikeButton.setOnAction(e -> {
            model.transportMode = 2;
            model.getMapObjects().getDigraph().setWalking();
        });
        view.getMapMenu().getDirectionsPanel().findRouteButton.setOnAction( e -> {
            calculateRoute();
        });

        // ##########################################################
        // ############# Selected point panel #######################
        // ##########################################################

        view.getMapMenu().getSelectedPointPanel().minimizeButton.setOnAction(e -> {
            // Set the menu panel to the minimized menu panel
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getMinimizedPanel());
            // Set the selectedPointMarker to null so no selected point is shown when the
            // minimized menu is shown.
            model.setSelectedPointMarker(null);
            view.getMapScene().redraw();
        });

        view.getMapMenu().getSelectedPointPanel().directionsButton.setOnAction(e -> {
            // Set the menu panel to the directions menu panel
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getDirectionsPanel());
        });
        view.getMapMenu().getSelectedPointPanel().settingsButton.setOnAction(e -> {
            // Set the menu panel to the settings menu panel
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getSettingsPanel());
        });
        view.getMapMenu().getSelectedPointPanel().searchBar.setOnAction(e -> {
            // On enter pressed in the searchbar, focus on and pan to the point of the
            // address in the search bar.
            focusOnPoint(view.getMapMenu().getSelectedPointPanel().searchBar.getAddress(), true, true);
            // Clear the searchbar text
            view.getMapMenu().getSelectedPointPanel().searchBar.clear();
        });
        view.getMapMenu().getSelectedPointPanel().searchButton.setOnAction(e -> {
            // On clicking search button, focus on and pan to the point of the address in
            // the search bar.
            focusOnPoint(view.getMapMenu().getSelectedPointPanel().searchBar.getAddress(), true, true);
            // Clear the searchbar text
            view.getMapMenu().getSelectedPointPanel().searchBar.clear();
        });
        view.getMapMenu().getSelectedPointPanel().directionsToSelectedPointButton.setOnAction(e -> {
            view.getMapMenu().getDirectionsPanel().endLocationField.setText(closestAddressToPointAsString(model.getSelectedPont()));
            // Change the menu panel to the directions panel
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getDirectionsPanel());
            setEndLocation(view.getMapMenu().getDirectionsPanel().endLocationField.getAddress(), false);
            if (view.getMapMenu().getDirectionsPanel().startLocationField.getAddress() != null) {
                calculateRoute();
            } else {
                model.getMapObjects().clearRoute();
                view.getMapScene().redraw();
            }
        });
        view.getMapMenu().getSelectedPointPanel().saveLocationButton.setOnAction(e -> {
            // When pressing the save point button we first check the status of the button
            // to see if we need to save a point
            // or delete a saved point.
            if (!model.getSelectedPont().getFavouriteStatus()) {
                // first set the isFavourite variable in the selected point.
                model.getSelectedPont().setFavouriteStatus(true);
                // When saving the selected point, we make a new point of interest with the type
                // favourite to mark the location of the saved point.
                model.getMapObjects().getFavouritesMarkerList()
                        .add(new PointOfInterest(view.getMapMenu().getPointOfInterestPanel().pointNameField.getText(),
                                PointType.FAVOURITE,
                                (float) (view.getMapMenu().getSelectedPointPanel().mapPoint.getX() / 0.56),
                                -view.getMapMenu().getSelectedPointPanel().mapPoint.getY(), true));
                view.getMapMenu().getPointOfInterestPanel().pointNameField.clear();
                view.getMapMenu().changeMenuPanel(view.getMapMenu().getMinimizedPanel());
                model.setSelectedPointMarker(null);
                view.getMapScene().redraw();
            } else {
                // first set the isFavourite variable in the selected point.
                // view.getMapMenu().getSelectedPointPanel().mapPoint.setFavouriteStatus(false);
                // We want to find the marker point with the same coordinates as the current
                // point and delete it.
                // We find the nearest point in the customPOIList and delete it.
                // calculate distance to the nearest custom point of interest, by iterating
                // through the list of all of them. Inefficient but necessary, as they are not
                // part of a tree.
                for (PointOfInterest poi : model.getMapObjects().getFavouritesMarkerList()) {
                    double customPOIDistance = Math.sqrt(
                            Math.pow(poi.getX() - view.getMapMenu().getSelectedPointPanel().mapPoint.getX(), 2) + Math
                                    .pow(poi.getY() - view.getMapMenu().getSelectedPointPanel().mapPoint.getY(), 2));
                    if (customPOIDistance == 0) {
                        model.getMapObjects().getCustomPOIList().remove(poi);
                    }
                }
            }
        });

        // ##########################################################
        // ############# Settings panel #############################
        // ##########################################################

        view.getMapMenu().getSettingsPanel().getMinimizeButton().setOnAction(e -> {
            // Set the menu panel to the minimized menu panel
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getMinimizedPanel());
            // Set the selectedPointMarker to null so no selected point is shown when the
            // minimized menu is shown.
            model.setSelectedPointMarker(null);
        });

        view.getMapMenu().getSettingsPanel().getDirectionsButton().setOnAction(e -> {
            // Set the menu panel to the directions menu panel
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getDirectionsPanel());
        });

        view.getMapMenu().getSettingsPanel().getLoadAnotherOSMButton().setOnAction(e -> {
            // set the scene to chooseMapScene
            this.stage.setScene(view.chooseMapScene);
            this.stage.show();
        });

        view.getMapMenu().getSettingsPanel().getNormalModeButton().setOnAction(e -> {
            MapColor.getInstance().changeTheme("default");
            Digraph.setColor(1);

            view.getMapMenu().getSettingsPanel().activateDarkMode(false);
            view.getMapMenu().getMinimizedPanel().activateDarkMode(false);
            view.getMapMenu().getPointOfInterestPanel().activateDarkMode(false);
            view.getMapMenu().getSelectedPointPanel().activateDarkMode(false);
            view.getMapMenu().getDirectionsPanel().activateDarkMode(false);
            view.getMapMenu().getColorblindnessModePanel().activateDarkMode(false);
            view.getZoomMenu().activateDarkMode(false);

            view.getMapScene().redraw();
        });

        view.getMapMenu().getSettingsPanel().getNightModeButton().setOnAction(e -> {
            MapColor.getInstance().changeTheme("dark");
            Digraph.setColor(1);

            view.getMapMenu().getSettingsPanel().activateDarkMode(true);
            view.getMapMenu().getMinimizedPanel().activateDarkMode(true);
            view.getMapMenu().getPointOfInterestPanel().activateDarkMode(true);
            view.getMapMenu().getSelectedPointPanel().activateDarkMode(true);
            view.getMapMenu().getDirectionsPanel().activateDarkMode(true);
            view.getMapMenu().getColorblindnessModePanel().activateDarkMode(true);
            view.getZoomMenu().activateDarkMode(true);

            view.getMapScene().redraw();
        });

        view.getMapMenu().getSettingsPanel().getColorBlindModeButton().setOnAction(e -> {
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getColorblindnessModePanel());
            view.getMapScene().redraw();
        });

        view.getMapMenu().getColorblindnessModePanel().getNormalButton().setOnAction(e->{
            MapColor.getInstance().changeTheme("default");
            Digraph.setColor(1);
            view.getMapScene().redraw();
        });

        view.getMapMenu().getColorblindnessModePanel().exitButton.setOnAction(e -> {
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getSettingsPanel());
        });

        view.getMapMenu().getColorblindnessModePanel().deuteranopiaButton.setOnAction(e -> {
            MapColor.getInstance().changeTheme("deuteranopia");
            Digraph.setColor(1);
            view.getMapScene().redraw();
        });

        view.getMapMenu().getColorblindnessModePanel().protanopiaButton.setOnAction(e -> {
            MapColor.getInstance().changeTheme("protanopia");
            Digraph.setColor(2);
            view.getMapScene().redraw();
        });

        view.getMapMenu().getColorblindnessModePanel().tritanopiaButton.setOnAction(e -> {
            MapColor.getInstance().changeTheme("tritanopia");
            Digraph.setColor(1);
            view.getMapScene().redraw();
        });

        view.getMapMenu().getColorblindnessModePanel().monochromacyButton.setOnAction(e -> {
            MapColor.getInstance().changeTheme("monochromia");
            Digraph.setColor(3);
            view.getMapScene().redraw();
        });

        view.getMapMenu().getSettingsPanel().getHideRoadsCheckbox().setOnAction(e -> {
            model.isRoadsVisible = !model.isRoadsVisible;
            view.getMapScene().redraw();
        });

        view.getMapMenu().getSettingsPanel().getHideBuildingsCheckbox().setOnAction(e -> {
            model.isBuildingsVisible = !model.isBuildingsVisible;
            view.getMapScene().redraw();
        });
        view.getMapMenu().getSettingsPanel().getHideTerrainCheckbox().setOnAction(e -> {
            model.isTerrainVisible = !model.isTerrainVisible;
            view.getMapScene().redraw();
        });
        view.getMapMenu().getSettingsPanel().getHideLandmarkCheckbox().setOnAction(e -> {
            model.isLandmarksVisible = !model.isLandmarksVisible;
            view.getMapScene().redraw();
        });
        view.getMapMenu().getSettingsPanel().getHideAddressesCheckbox().setOnAction(e -> {
            model.isAddressVisible = !model.isAddressVisible;
            view.getMapScene().redraw();
        });
        view.getMapMenu().getSettingsPanel().getZoomAdjustSlider().setOnMouseReleased(e -> {
            view.getZoomMenu()
                    .setZoomMultiplier((float) view.getMapMenu().getSettingsPanel().getZoomAdjustSlider().getValue());
        });

        // ##########################################################
        // ############# Create point panel #########################
        // ##########################################################

        view.getMapMenu().getPointOfInterestPanel().createPointButton.setOnAction(e -> {
            Point2D customPointCoords = view.getMapScene().screenCoordsToMapCoords(new Point2D(lastX, lastY));

            model.getMapObjects().getCustomPOIList()
                    .add(new PointOfInterest(view.getMapMenu().getPointOfInterestPanel().pointNameField.getText(),
                            PointType.CUSTOM, (float) (customPointCoords.getX()), (float) customPointCoords.getY(),
                            false));
            view.getMapMenu().getPointOfInterestPanel().pointNameField.clear();
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getMinimizedPanel());
            view.getMapScene().redraw();
        });

        view.getMapMenu().getPointOfInterestPanel().cancelButton.setOnAction(e -> {
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getMinimizedPanel());
        });

        
    }
    
    // ##########################################################
    // ############# CHOOSE MAP SCENE ###########################
    // ##########################################################

    public void createChooseMapSceneButtons() {
        view.chooseMapScene.loadDefaultBinaryButton.setOnAction(e -> {
            try {
                Model.updateModel(getClass().getResource("/maps/"+Model.getDefaultMap()));
            } catch (ClassNotFoundException | URISyntaxException | XMLStreamException | FactoryConfigurationError
                    | IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            view.creatMenusForMapScene(Model.getInstance());
            view.createNewMapScene(Model.getInstance());
            createMapSceneButtons();
            view.setScene(view.getMapScene());
        });

        try {
            this.view.listView.getItems().addAll(fileList);
        } catch (Exception e) {
            System.out.println("Error getting items from List View: ");
            e.printStackTrace();
        }

        view.listView.setOnMouseClicked(e -> {
            try {
                URL fileURL = new URL(Paths.get("data/maps/" + view.listView.getSelectionModel().getSelectedItem())
                        .toUri().toURL().toString());
                Model.updateModel(fileURL);
                view.creatMenusForMapScene(Model.getInstance());
                view.createNewMapScene(Model.getInstance());
                createMapSceneButtons();
                view.setScene(view.getMapScene());
            } catch (Exception e1) {
                System.out.println(e1.getMessage());
            }

        });

        view.chooseMapScene.chooseOwnFileButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            try {
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("OSM files", "*.osm"),
                        new FileChooser.ExtensionFilter("BIN files", "*.bin"),
                        new FileChooser.ExtensionFilter("ZIP files", "*.zip"),
                        new FileChooser.ExtensionFilter("All files", "*.*"));
                File selectedFile = fileChooser.showOpenDialog(view.primaryStage);
                if (selectedFile != null) {
                    Model.updateModel(selectedFile.toURI().toURL());
                    System.out.println(selectedFile.toURI().toURL().toString());
                    view.creatMenusForMapScene(Model.getInstance());
                    view.createNewMapScene(Model.getInstance());
                    createMapSceneButtons();
                    view.setScene(view.getMapScene());
                }
            } catch (ClassNotFoundException | URISyntaxException | XMLStreamException | FactoryConfigurationError
                    | IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
    }


    private String closestAddressToPointAsString(MapPoint selectedPont) {
        // Not all selected points are addresses. We find the closest address and add it
        // to the destination searchbar on the directions panel.
        Address closestAddress = model.getMapObjects().getAddressTree().getNearest(
                new float[] {(float) (selectedPont.getX()/0.56), -selectedPont.getY() });
        // Add the address to the searchbar as text.
        return closestAddress.getStreet() + " " + closestAddress.getHouseNumber() + " "
                        + closestAddress.getPostCode() + " " + closestAddress.getCity();
    }
    /*
     * FileChooser fileChooser = new FileChooser();
     * try {
     * fileChooser.getExtensionFilters().addAll(
     * new FileChooser.ExtensionFilter("OSM files", "*.osm"),
     * new FileChooser.ExtensionFilter("BIN files", "*.bin"),
     * new FileChooser.ExtensionFilter("All files", "*.*"));
     * 
     * File selectedFile = fileChooser.showOpenDialog(stage);
     * if (selectedFile == null) {
     * 
     * } else {
     * try {
     * view.getMapScene().resetAffine();
     * this.model = Model.createModel(selectedFile);
     * view.model = this.model;
     * view.createNewMapScene();
     * view.setScene(view.getMapScene());
     * } catch (javax.xml.parsers.FactoryConfigurationError e2) {
     * e2.printStackTrace();
     * }
     * }
     * } catch (Exception e1) {
     * // TODO: handle exception
     * System.out.println("Error getting items from listview: ");
     * e1.printStackTrace();
     * }
     * });
     * 
     * }
     */

    private void setStartLocation(Address address, boolean shouldPan) {
        if (shouldPan) {
            panToPoint(address);
        }
        // Make a custom landmark to show the selected point
        if (address != null) {
            model.setStartLocationMarker(
                    new PointOfInterest("", PointType.START_LOCATION, (float) (address.getX()), address.getY(), false));
        } else {
            model.setStartLocationMarker(null);
        }
        // Redraw the view
        view.getMapScene().redraw();
    }


    private void setEndLocation(Address address, boolean shouldPan) {
        if (shouldPan) {
            panToPoint(address);
        }
        // Make a custom landmark to show the selected point
        if (address != null) {
            model.setEndLocationMarker(
                    new PointOfInterest("", PointType.END_LOCATION, (float) (address.getX()), address.getY(), false));
        } else {
            model.setEndLocationMarker(null);
        }
        // Redraw the view
        view.getMapScene().redraw();
    }

    public void toggleIsCreatingCustomPointOfInterest() {
        if (isCreatingCustomPointOfInterest) {
            isCreatingCustomPointOfInterest = false;
            // set the cursor to the default cursor.
            view.getCanvas().setCursor(Cursor.DEFAULT);

            // Change the graphic of the button for creating new points of interest in the
            // minimized menu.
            Image newPointIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/star.png")));
            ImageView imageView = new ImageView(newPointIcon);
            imageView.setFitHeight(48);
            imageView.setFitWidth(48);
            view.getMapMenu().getMinimizedPanel().pointOfInterestButton.setGraphic(imageView);
        } else {
            isCreatingCustomPointOfInterest = true;
            // set the cursor to the crosshair cursor.
            view.getCanvas().setCursor(Cursor.CROSSHAIR);

            // Change the graphic of the button for creating new points of interest in the
            // minimized menu.
            Image cancelIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/cross.png")));
            ImageView imageView = new ImageView(cancelIcon);
            imageView.setFitHeight(48);
            imageView.setFitWidth(48);
            view.getMapMenu().getMinimizedPanel().pointOfInterestButton.setGraphic(imageView);

        }
    }

    public void focusOnPoint(MapPoint mapPoint, boolean shouldPan, boolean shouldSetMarker) {
        // Attempt pan only if the point to pan to is not null
        if (mapPoint != null) {
            // Update the menu panel to the selected point menu
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getSelectedPointPanel());
            view.getMapMenu().getSelectedPointPanel().setMapPoint(mapPoint);
            view.getMapMenu().getSelectedPointPanel().setSavePointButtonMode(mapPoint.getFavouriteStatus());
            // We only pan if the point to focus on is found through a search bar. Not when
            // clicking on a point.
            if (shouldPan) {
                // Find the middle screen coordinate and find the map coordinates for this
                // point.
                Point2D firstPoint = view.getMapScene().screenCoordsToMapCoords(
                        new Point2D(view.getCanvas().getWidth() / 2, view.getMapScene().getHeight() / 2));
               panToPoint(mapPoint);
            }
            if (shouldSetMarker) {
                // Make a custom landmark to show the selected point
                model.setSelectedPointMarker(
                        new PointOfInterest("", PointType.SELECTED, (float) (mapPoint.getX()), mapPoint.getY(), false));
                // Redraw the view
                view.getMapScene().redraw();
            }
        }
    }
    public void panToPoint(MapPoint mapPoint) {
        // Find the middle screen coordinate and find the map coordinates for this
        // point.
        Point2D firstPoint = view.getMapScene().screenCoordsToMapCoords(
                new Point2D(view.getCanvas().getWidth() / 2, view.getMapScene().getHeight() / 2));

        // Pan once to the side and do the same again. In this way we find the
        // relationship between the panning and the distance.
        view.getMapScene().pan(10, 10);
        Point2D secondPoint = view.getMapScene().screenCoordsToMapCoords(
                new Point2D(view.getMapScene().getWidth() / 2, view.getMapScene().getHeight() / 2));

        // Calculate the factors needed to convert between screen coordinates and map
        // coordinates
        float xfactor = (float) (10 / (secondPoint.getX() - firstPoint.getX()));
        float yfactor = (float) (10 / (secondPoint.getY() - firstPoint.getY()));

        // Calculate the distance to the point in map coordinates
        float xDiff = (float) (mapPoint.getX() - secondPoint.getX());
        float yDiff = (float) (mapPoint.getY() - secondPoint.getY());
        float xDist = xDiff * xfactor;
        float yDist = yDiff * yfactor;

        // Pan to the selected point
        view.getMapScene().pan(xDist, yDist);
    }

    private void takeSnapShot() {
        try {
            PrinterJob job = PrinterJob.createPrinterJob();
            job.getPrinter().createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
            if (job != null) {
                view.getCanvas().getTransforms().add(new Scale(0.2, 0.2));
                job.printPage(view.getCanvas());
                job.endJob();
                view.getCanvas().getTransforms().add(new Scale(5, 5));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert errorMsg = new Alert(AlertType.ERROR);
            errorMsg.setTitle("Unable to start printing!");
            errorMsg.setContentText("MARP was unable to start printing the scene. \nPlease try again later...");

            errorMsg.show();
        }
    }
    private void calculateRoute() {
        if (view.getMapMenu().getDirectionsPanel().startLocationField.getAddress() != null && view.getMapMenu().getDirectionsPanel().endLocationField.getAddress() != null) {
            RoadNode start = model.getMapObjects().getRoadNodeRTree().getNearest(view.getMapMenu().getDirectionsPanel().startLocationField.getAddress());
            RoadNode end = model.getMapObjects().getRoadNodeRTree().getNearest(view.getMapMenu().getDirectionsPanel().endLocationField.getAddress());
            setStartLocation(view.getMapMenu().getDirectionsPanel().startLocationField.getAddress(), false);
            setEndLocation(view.getMapMenu().getDirectionsPanel().endLocationField.getAddress(), false);
            List<String> directions = model.getMapObjects().getDigraph().aStar(end, start, true);
            float distance = model.getMapObjects().getDigraph().getDistance();
            int travelTime = model.getMapObjects().getDigraph().getTravelTime(model.getTransportMode());
            //model.graph.runaStarWithNodeIndex(Integer.parseInt(view.getMapMenu().getDirectionsPanel().startLocationField.getText()), Integer.parseInt(view.getMapMenu().getDirectionsPanel().endLocationField.getText()));
            //view.getMapMenu().getDirectionsPanel().receiveGuideList(null);
            view.getMapMenu().getDirectionsPanel().setGuideShow(true);
            view.getMapMenu().getDirectionsPanel().receiveGuideList(directions);
            view.getMapMenu().getDirectionsPanel().updateDistanceAndTime(distance, travelTime);
            view.getMapScene().redraw();
        }
    }
}
