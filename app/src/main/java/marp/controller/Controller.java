package marp.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import marp.mapelements.Address;
import marp.mapelements.MapPoint;
import marp.mapelements.PointOfInterest;
import marp.mapelements.details.MapColor;
import marp.model.Model;
import marp.mapelements.details.PointType;
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

    public Controller(View view, Model model){
        this.model = model;
        this.view = view;
        fileList = IOFunctions.getFiles();
        setFileChooser();
        this.stage = view.getPrimaryStage();
        createButtonControl();

    }

    private void setFileChooser(){
        this.fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("OSM files", "*.osm"),
            new FileChooser.ExtensionFilter("BIN files", "*.bin"),
            new FileChooser.ExtensionFilter("ZIP files", "*.zip"),
            new FileChooser.ExtensionFilter("All files", "*.*"));
    }

    private void createButtonControl() {
        //Clicking on the map updates the latest clicked coordinates. if isCreatingCustomPointOfInterest is true, lastPressedX is set as well.
        view.getCanvas().setOnMousePressed(e -> {
            lastX = (float) e.getX();
            lastY = (float) e.getY();
            if (isCreatingCustomPointOfInterest){
                mouseDragStartPositionX = lastX;
                mouseDragStartPositionY = lastY;
            }
        });

        view.getCanvas().setOnMouseReleased(e -> {
            //When releasing the mouse, if the mouse drag start position is equal to the current position, it counts as a mouse click.
            if (mouseDragStartPositionX == e.getX() && mouseDragStartPositionY == e.getY()) {
                //There are two cases: Either we are making a new POI or we are selecting a point.
                if (isCreatingCustomPointOfInterest) {
                    view.getMapMenu().changeMenuPanel(view.getMapMenu().getPointOfInterestPanel());
                    toggleIsCreatingCustomPointOfInterest();
                } else {
                    //convert the screen coords of the mouse click to map coords and find the nearest selectable element.
                    Point2D point = view.getMapScene().screenCoordsToMapCoords(new Point2D(lastX, lastY));
                    MapPoint nearestPoint = model.getNearestPointForMapSelection(point);
                    // make a POI as a marker of the selected point
                    model.setSelectedPointMarker(new PointOfInterest(nearestPoint.getName(), PointType.SELECTED, nearestPoint.getX(), nearestPoint.getX(), false));
                    // focus on the point without panning
                    focusOnPoint(model.getSelectedPointMarker(), false);
                }
            }
        });

        view.getCanvas().setOnMouseDragged(e -> {
            // When dragging the mouse we pan with the difference in mouse x and y of the mouse position.
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

        //##########################################################
        //############# Zoom menu buttons ##########################
        //##########################################################

        view.getZoomMenu().zoomIn.setOnAction(e -> {
            view.getMapScene().zoom(500, 350, 2 * view.getZoomMenu().getZoomMultiplier());
            view.getMapScene().redraw();
        });

        view.getZoomMenu().zoomOut.setOnAction(e -> {
            view.getMapScene().zoom(500F, 350F, (float) (0.5 / view.getZoomMenu().getZoomMultiplier()));
            view.getMapScene().redraw();
        });

        //##########################################################
        //############# Minimized panel buttons ####################
        //##########################################################

        view.getMapMenu().getMinimizedPanel().directionsButton.setOnAction(e -> {
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getDirectionsPanel());
        });
        view.getMapMenu().getMinimizedPanel().settingsButton.setOnAction(e -> {
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getSettingsPanel());
        });
        view.getMapMenu().getMinimizedPanel().searchBar.setOnAction(e -> {
            focusOnPoint(view.getMapMenu().getMinimizedPanel().searchBar.getAddress(), true);
            view.getMapMenu().getMinimizedPanel().searchBar.clear();
        });
        view.getMapMenu().getMinimizedPanel().searchButton.setOnAction(e -> {
            focusOnPoint(view.getMapMenu().getMinimizedPanel().searchBar.getAddress(), true);
            view.getMapMenu().getMinimizedPanel().searchBar.clear();
        });
        view.getMapMenu().getMinimizedPanel().pointOfInterestButton.setOnAction(e -> {
            toggleIsCreatingCustomPointOfInterest();
        });

        //##########################################################
        //############# Directions panel buttons ###################
        //##########################################################

        view.getMapMenu().getDirectionsPanel().minimizeButton.setOnAction(e -> {
            // Set the menu panel to the minimized menu panel
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getMinimizedPanel());
            view.getMapMenu().getDirectionsPanel().setGuideShow(false);

            // Set the selectedPointMarker to null so no selected point is shown when the minimized menu is shown.
            model.setSelectedPointMarker(null);
        });

        view.getMapMenu().getDirectionsPanel().settingsButton.setOnAction(e -> {
            view.getMapMenu().getDirectionsPanel().setGuideShow(false);

            // Set the menu panel to the settings menu panel
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getSettingsPanel());
        });

        view.getMapMenu().getDirectionsPanel().swapButton.setOnAction(e -> {
            // First get the start location field text and save it as temp text,
            // then set ge start location field text to the text from the end location field,
            // then set the temp text in the end location field.
            String tempText = view.getMapMenu().getDirectionsPanel().startLocationField.getText();
            view.getMapMenu().getDirectionsPanel().startLocationField.setText(view.getMapMenu().getDirectionsPanel().endLocationField.getText());
            view.getMapMenu().getDirectionsPanel().endLocationField.setText(tempText);
        });
        view.getMapMenu().getDirectionsPanel().findRouteButton.setOnAction( e -> {
            //TODO: Fix when this all works...
            //model.graph.runaStarWithNodeIndex(Integer.parseInt(view.getMapMenu().getDirectionsPanel().startLocationField.getText()), Integer.parseInt(view.getMapMenu().getDirectionsPanel().endLocationField.getText()));
            //view.getMapMenu().getDirectionsPanel().receiveGuideList(null);
            view.getMapMenu().getDirectionsPanel().setGuideShow(true);
            view.getMapScene().redraw();
        });

        //##########################################################
        //############# Selected point panel #######################
        //##########################################################

        view.getMapMenu().getSelectedPointPanel().minimizeButton.setOnAction(e -> {
            // Set the menu panel to the minimized menu panel
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getMinimizedPanel());
            // Set the selectedPointMarker to null so no selected point is shown when the minimized menu is shown.
            model.setSelectedPointMarker(null);
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
            // On enter pressed in the searchbar, focus on and pan to the point of the address in the search bar.
            focusOnPoint(view.getMapMenu().getSelectedPointPanel().searchBar.getAddress(), true);
            // Clear the searchbar text
            view.getMapMenu().getSelectedPointPanel().searchBar.clear();
        });
        view.getMapMenu().getSelectedPointPanel().searchButton.setOnAction(e -> {
            // On clicking search button, focus on and pan to the point of the address in the search bar.
            focusOnPoint(view.getMapMenu().getSelectedPointPanel().searchBar.getAddress(), true);
            // Clear the searchbar text
            view.getMapMenu().getSelectedPointPanel().searchBar.clear();
        });
        view.getMapMenu().getSelectedPointPanel().directionsToSelectedPointButton.setOnAction(e -> {
            // Not all selected points are addresses. We find the closest address and add it to the destination searchbar on the directions panel.
            Address closestAddress = model.getMapObjects().getAddressTree().getNearest(new float[]{model.getSelectedPointMarker().getX(), model.getSelectedPointMarker().getY()}, 5);
            // Add the address to the searchbar as text.
            view.getMapMenu().getDirectionsPanel().endLocationField.setText(closestAddress.getStreet() + " " + closestAddress.getHouseNumber() + " " + closestAddress.getPostCode() + " " + closestAddress.getCity());
            // Change the menu panel to the directions panel
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getDirectionsPanel());
        });
        view.getMapMenu().getSelectedPointPanel().saveLocationButton.setOnAction(e -> {
            // When saving the selected point, we make a new point of interest with the type Favourite to mark the location of the saved point.
            //TODO: Possitble to delete saved points.
            model.getMapObjects().getCustomPOIList().add(new PointOfInterest(view.getMapMenu().getPointOfInterestPanel().pointNameField.getText(), PointType.FAVOURITE, (float) (view.getMapMenu().getSelectedPointPanel().mapPoint.getX()/0.56), -view.getMapMenu().getSelectedPointPanel().mapPoint.getY(), true));
            view.getMapMenu().getPointOfInterestPanel().pointNameField.clear();
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getMinimizedPanel());
            view.getMapScene().redraw();
        });

        //##########################################################
        //############# Settings panel #############################
        //##########################################################

        view.getMapMenu().getSettingsPanel().minimizeButton.setOnAction(e -> {
            // Set the menu panel to the minimized menu panel
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getMinimizedPanel());
            // Set the selectedPointMarker to null so no selected point is shown when the minimized menu is shown.
            model.setSelectedPointMarker(null);
        });

        view.getMapMenu().getSettingsPanel().directionsButton.setOnAction(e -> {
            // Set the menu panel to the directions menu panel
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getDirectionsPanel());
        });

        view.getMapMenu().getSettingsPanel().saveMapButton.setOnAction(e -> {
            //TODO: Is this even a function we have?
            //try {
            //    model.save(model.filename);
            //} catch (IOException ex) {
            //    throw new RuntimeException(ex);
            //}
        });

        view.getMapMenu().getSettingsPanel().loadAnotherOSMButton.setOnAction(e -> {
            //set the scene to chooseMapScene
            this.stage.setScene(view.chooseMapScene);
            this.stage.show();
        });

        view.getMapMenu().getSettingsPanel().normalModeButton.setOnAction(e -> {
            MapColor.getInstance().changeTheme("default");
            view.getMapScene().redraw();
        });

        view.getMapMenu().getSettingsPanel().nightModeButton.setOnAction(e -> {
            MapColor.getInstance().changeTheme("dark");
            view.getMapScene().redraw();
        });

        view.getMapMenu().getSettingsPanel().colorBlindModeButton.setOnAction(e -> {
            MapColor.getInstance().changeTheme("colorblind");
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

        //##########################################################
        //############# Create point panel #########################
        //##########################################################

        view.getMapMenu().getPointOfInterestPanel().createPointButton.setOnAction(e -> {
            Point2D landmarkCoords = view.getMapScene().screenCoordsToMapCoords(new Point2D(lastX, lastY));

            model.getMapObjects().getCustomPOIList().add(new PointOfInterest(view.getMapMenu().getPointOfInterestPanel().pointNameField.getText(), PointType.CUSTOM, (float) (landmarkCoords.getX()/0.56), (float) -landmarkCoords.getY(), false));
            view.getMapMenu().getPointOfInterestPanel().pointNameField.clear();
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getMinimizedPanel());
            view.getMapScene().redraw();
        });

        view.getMapMenu().getPointOfInterestPanel().cancelButton.setOnAction(e -> {
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getMinimizedPanel());
        });

        //##########################################################
        //############# CHOOSE MAP SCENE ###########################
        //##########################################################

        view.chooseMapScene.loadButton.setOnAction(e -> {
            view.createNewMapScene();
            view.setScene(view.getMapScene());
                }
                );
    }
            /*FileChooser fileChooser = new FileChooser();
            try {
                fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("OSM files", "*.osm"),
                    new FileChooser.ExtensionFilter("BIN files", "*.bin"),
                    new FileChooser.ExtensionFilter("All files", "*.*"));

                File selectedFile = fileChooser.showOpenDialog(stage);
                if (selectedFile == null) {

                } else {
                    try {
                        view.getMapScene().resetAffine();
                        this.model = Model.createModel(selectedFile);
                        view.model = this.model;
                        view.createNewMapScene();
                        view.setScene(view.getMapScene());
                    } catch (javax.xml.parsers.FactoryConfigurationError e2) {
                        e2.printStackTrace();
                    }
                }
            } catch (Exception e1) {
                // TODO: handle exception
                System.out.println("Error getting items from listview: ");
                e1.printStackTrace();
            }
        });

    }*/

    public void toggleIsCreatingCustomPointOfInterest(){
        if (isCreatingCustomPointOfInterest){
            isCreatingCustomPointOfInterest = false;
            // set the cursor to the default cursor.
            view.getMapScene().gc.getCanvas().setCursor(Cursor.DEFAULT);

            //Change the graphic of the button for creating new points of interest in the minimized menu.
            Image newPointIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/star.png")));
            ImageView imageView = new ImageView(newPointIcon);
            imageView.setFitHeight(48);
            imageView.setFitWidth(48);
            view.getMapMenu().getMinimizedPanel().pointOfInterestButton.setGraphic(imageView);
        }
        else {
            isCreatingCustomPointOfInterest = true;
            // set the cursor to the crosshair cursor.
            view.getMapScene().gc.getCanvas().setCursor(Cursor.CROSSHAIR);

            // Change the graphic of the button for creating new points of interest in the minimized menu.
            Image cancelIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/cross.png")));
            ImageView imageView = new ImageView(cancelIcon);
            imageView.setFitHeight(48);
            imageView.setFitWidth(48);
            view.getMapMenu().getMinimizedPanel().pointOfInterestButton.setGraphic(imageView);

        }
    }
    public void focusOnPoint(MapPoint mapPoint, boolean shouldPan) {
        // Attempt pan only if the point to pan to is not null
        if (mapPoint != null) {
            // Update the menu panel to the selected point menu
            view.getMapMenu().changeMenuPanel(view.getMapMenu().getSelectedPointPanel());
            view.getMapMenu().getSelectedPointPanel().setMapPoint(mapPoint);

            //We only pan if the point to focus on is found through a search bar. Not when clicking on a point.
            if (shouldPan) {
                // Find the middle screen coordinate and find the map coordinates for this point.
                Point2D firstPoint = view.getMapScene().screenCoordsToMapCoords(new Point2D(view.getCanvas().getWidth()/2, view.getMapScene().getHeight()/2));

                // Pan once to the side and do the same again. In this way we find the relationship between the panning and the distance.
                view.getMapScene().pan(10, 10);
                Point2D secondPoint = view.getMapScene().screenCoordsToMapCoords(new Point2D(view.getMapScene().getWidth()/2, view.getMapScene().getHeight()/2));

                // Calculate the factors needed to convert between screen coordinates and map coordinates
                float xfactor = (float) (10/(secondPoint.getX()-firstPoint.getX()));
                float yfactor = (float) (10/(secondPoint.getY()-firstPoint.getY()));

                // Calculate the distance to the point in map coordinates
                float xDiff = (float) (mapPoint.getX() - secondPoint.getX());
                float yDiff = (float) (mapPoint.getY() - secondPoint.getY());
                float xDist = xDiff * xfactor;
                float yDist = yDiff * yfactor;

                // Pan to the selected point
                view.getMapScene().pan(xDist, yDist);
            }
            // Make a custom landmark to show the selected point
            model.setSelectedPointMarker(new PointOfInterest(mapPoint.getName(), mapPoint.getType(), mapPoint.getX()/0.56f, -mapPoint.getY(), false));
            // Redraw the view
            view.getMapScene().redraw();
        }
    }
}
