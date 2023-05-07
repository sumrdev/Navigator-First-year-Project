package marp.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import javafx.geometry.Point2D;
import javafx.print.*;
import javafx.scene.Cursor;
import javafx.scene.control.ListView;
import javafx.scene.image.*;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import marp.datastructures.Digraph;
import marp.mapelements.*;
import marp.model.Model;
import marp.mapelements.details.*;
import marp.utilities.DefaultPath;
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
        createButtonControl();

        try {
            this.view.listView.getItems().addAll(fileList);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error getting items from List View: ");
            e.printStackTrace();
        }

        this.view.listView.setOnMouseClicked(e -> {
            try {
                URL fileURL = new URL(DefaultPath.getDefaultPath() + this.view.listView.getSelectionModel().getSelectedItem());
                Model.createModel(fileURL);
                this.view.createNewMapScene();
                this.view.setScene(this.view.getMapScene());
            } catch (Exception e1) {
                System.out.println(e1.getMessage());
            }

        });
    }

    private void setFileChooser() {
        this.fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("OSM files", "*.osm"),
                new FileChooser.ExtensionFilter("BIN files", "*.bin"),
                new FileChooser.ExtensionFilter("ZIP files", "*.zip"),
                new FileChooser.ExtensionFilter("All files", "*.*"));
    }

    private void createButtonControl() {
        // Clicking on the map updates the latest clicked coordinates. if
        // isCreatingCustomPointOfInterest is true, lastPressedX is set as well.

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
        });

        view.getCanvas().setOnMouseDragged(e -> {
            // When dragging the mouse we pan with the difference in mouse x and y of the
            // mouse position.
            float dx = (float) (e.getX() - lastX);
            float dy = (float) (e.getY() - lastY);
            view.getMapScene().pan(dx, dy);
            view.getMapScene().redraw();
            // Update last x and y
            lastX = (float) e.getX();
            lastY = (float) e.getY();
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
            view.getMapScene().zoom(500, 350, 2 * view.getZoomMenu().getZoomMultiplier());
            view.getMapScene().redraw();
        });

        view.getZoomMenu().zoomOut.setOnAction(e -> {
            view.getMapScene().zoom(500F, 350F, (float) (0.5 / view.getZoomMenu().getZoomMultiplier()));
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
            PrinterJob job = PrinterJob.createPrinterJob();
            job.getPrinter().createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
            if (job != null) {
                view.getCanvas().getTransforms().add(new Scale(0.2, 0.2));
                job.printPage(view.getCanvas());
                job.endJob();
                view.getCanvas().getTransforms().add(new Scale(5, 5));
            }
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
        view.getMapMenu().getDirectionsPanel().findRouteButton.setOnAction(e -> {
            RoadNode start = model.getMapObjects().getRoadNodeRTree()
                    .getNearest(view.getMapMenu().getDirectionsPanel().startLocationField.getAddress());
            RoadNode end = model.getMapObjects().getRoadNodeRTree()
                    .getNearest(view.getMapMenu().getDirectionsPanel().endLocationField.getAddress());
            List<String> directions = model.getMapObjects().getDigraph().aStar(start, end, true);
            // model.graph.runaStarWithNodeIndex(Integer.parseInt(view.getMapMenu().getDirectionsPanel().startLocationField.getText()),
            // Integer.parseInt(view.getMapMenu().getDirectionsPanel().endLocationField.getText()));
            // view.getMapMenu().getDirectionsPanel().receiveGuideList(null);
            view.getMapMenu().getDirectionsPanel().setGuideShow(true);
            view.getMapMenu().getDirectionsPanel().receiveGuideList(directions);
            view.getMapScene().redraw();
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
            // Not all selected points are addresses. We find the closest address and add it
            // to the destination searchbar on the directions panel.
            Address closestAddress = model.getMapObjects().getAddressTree().getNearest(
                    new float[] { model.getSelectedPointMarker().getX(), model.getSelectedPointMarker().getY() });
            // Add the address to the searchbar as text.
            view.getMapMenu().getDirectionsPanel().endLocationField
                    .setText(closestAddress.getStreet() + " " + closestAddress.getHouseNumber() + " "
                            + closestAddress.getPostCode() + " " + closestAddress.getCity());
            // Change the menu panel to the directions panel
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getDirectionsPanel());
        });
        view.getMapMenu().getSelectedPointPanel().saveLocationButton.setOnAction(e -> {
            // When saving the selected point, we make a new point of interest with the type
            // Favourite to mark the location of the saved point.
            // TODO: Possitble to delete saved points.
            model.getMapObjects().getCustomPOIList()
                    .add(new PointOfInterest(view.getMapMenu().getPointOfInterestPanel().pointNameField.getText(),
                            PointType.FAVOURITE,
                            (float) (view.getMapMenu().getSelectedPointPanel().mapPoint.getX() / 0.56),
                            -view.getMapMenu().getSelectedPointPanel().mapPoint.getY(), true));
            view.getMapMenu().getPointOfInterestPanel().pointNameField.clear();
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getMinimizedPanel());
            view.getMapScene().redraw();
        });

        // ##########################################################
        // ############# Settings panel #############################
        // ##########################################################

        view.getMapMenu().getSettingsPanel().minimizeButton.setOnAction(e -> {
            // Set the menu panel to the minimized menu panel
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getMinimizedPanel());
            // Set the selectedPointMarker to null so no selected point is shown when the
            // minimized menu is shown.
            model.setSelectedPointMarker(null);
        });

        view.getMapMenu().getSettingsPanel().directionsButton.setOnAction(e -> {
            // Set the menu panel to the directions menu panel
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getDirectionsPanel());
        });

        view.getMapMenu().getSettingsPanel().saveMapButton.setOnAction(e -> {
            // TODO: Is this even a function we have?
            // try {
            // model.save(model.filename);
            // } catch (IOException ex) {
            // throw new RuntimeException(ex);
            // }
        });

        view.getMapMenu().getSettingsPanel().takeSnapshotButton.setOnAction(e -> {
            PrinterJob job = PrinterJob.createPrinterJob();
            job.getPrinter().createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
            if (job != null) {
                view.getCanvas().getTransforms().add(new Scale(0.2, 0.2));
                job.printPage(view.getCanvas());
                job.endJob();
                view.getCanvas().getTransforms().add(new Scale(5, 5));
            }
        });

        view.getMapMenu().getSettingsPanel().loadAnotherOSMButton.setOnAction(e -> {
            // set the scene to chooseMapScene
            this.stage.setScene(view.chooseMapScene);
            this.stage.show();
        });

        view.getMapMenu().getSettingsPanel().normalModeButton.setOnAction(e -> {
            MapColor.getInstance().changeTheme("default");
            Digraph.setColor(1);
            view.getMapScene().redraw();
        });

        view.getMapMenu().getSettingsPanel().nightModeButton.setOnAction(e -> {
            MapColor.getInstance().changeTheme("dark");
            Digraph.setColor(1);
            view.getMapScene().redraw();
        });

        view.getMapMenu().getSettingsPanel().colorBlindModeButton.setOnAction(e -> {
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getColorblindnessmode());
            view.getMapScene().redraw();
        });

        view.getMapMenu().getColorblindnessmode().exitButton.setOnAction(e -> {
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getSettingsPanel());
        });

        view.getMapMenu().getColorblindnessmode().deuteranopiaButton.setOnAction(e -> {
            MapColor.getInstance().changeTheme("deuteranopia");
            Digraph.setColor(1);
            view.getMapScene().redraw();
        });

        view.getMapMenu().getColorblindnessmode().protanopiaButton.setOnAction(e -> {
            MapColor.getInstance().changeTheme("protanopia");
            Digraph.setColor(2);
            view.getMapScene().redraw();
        });

        view.getMapMenu().getColorblindnessmode().tritanopiaButton.setOnAction(e -> {
            MapColor.getInstance().changeTheme("tritanopia");
            Digraph.setColor(1);
            view.getMapScene().redraw();
        });

        view.getMapMenu().getColorblindnessmode().monochromacyButton.setOnAction(e -> {
            MapColor.getInstance().changeTheme("monochromia");
            Digraph.setColor(3);
            view.getMapScene().redraw();
        });

        view.getMapMenu().getSettingsPanel().hideRoadsCheckbox.setOnAction(e -> {
            model.isRoadsVisible = !model.isRoadsVisible;
            view.getMapScene().redraw();
        });

        view.getMapMenu().getSettingsPanel().hideBuildingsCheckbox.setOnAction(e -> {
            model.isBuildingsVisible = !model.isBuildingsVisible;
            view.getMapScene().redraw();
        });
        view.getMapMenu().getSettingsPanel().hideTerrainCheckbox.setOnAction(e -> {
            model.isTerrainVisible = !model.isTerrainVisible;
            view.getMapScene().redraw();
        });
        view.getMapMenu().getSettingsPanel().hideLandmarkCheckbox.setOnAction(e -> {
            model.isLandmarksVisible = !model.isLandmarksVisible;
            view.getMapScene().redraw();
        });
        view.getMapMenu().getSettingsPanel().hideAddressesCheckbox.setOnAction(e -> {
            model.isAddressVisible = !model.isAddressVisible;
            view.getMapScene().redraw();
        });

        // ##########################################################
        // ############# Create point panel #########################
        // ##########################################################

        view.getMapMenu().getPointOfInterestPanel().createPointButton.setOnAction(e -> {
            Point2D customPointCoords = view.getMapScene().screenCoordsToMapCoords(new Point2D(lastX, lastY));

            model.getMapObjects().getCustomPOIList()
                    .add(new PointOfInterest(view.getMapMenu().getPointOfInterestPanel().pointNameField.getText(),
                            PointType.FAVOURITE, (float) (customPointCoords.getX()), (float) customPointCoords.getY(),
                            false));
            view.getMapMenu().getPointOfInterestPanel().pointNameField.clear();
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getMinimizedPanel());
            view.getMapScene().redraw();
        });

        view.getMapMenu().getPointOfInterestPanel().cancelButton.setOnAction(e -> {
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getMinimizedPanel());
        });

        // ##########################################################
        // ############# CHOOSE MAP SCENE ###########################
        // ##########################################################

        view.chooseMapScene.loadButton.setOnAction(e -> {
            view.createNewMapScene();
            view.setScene(view.getMapScene());
        });
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

            // We only pan if the point to focus on is found through a search bar. Not when
            // clicking on a point.
            if (shouldPan) {
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
            if (shouldSetMarker) {
                // Make a custom landmark to show the selected point
                model.setSelectedPointMarker(
                        new PointOfInterest("", PointType.SELECTED, (float) (mapPoint.getX()), mapPoint.getY(), false));
                // Redraw the view
                view.getMapScene().redraw();
            }
        }
    }
}
