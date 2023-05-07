package marp.view;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;
import marp.mapelements.*;
import marp.mapelements.details.MapColor;
import marp.model.Model;
import marp.utilities.MathFunctions;
import marp.view.gui.MapLabel;
import marp.view.gui.ZoomMenu;
import marp.view.gui.buttons.MapTextButton;
import marp.view.gui.menugui.MapMenu;

import javax.crypto.spec.PSource;

public class MapScene extends Scene{
    public MapTextButton loadButton;
    private Model model;
    private ZoomMenu zoomMenu;
    private  MapMenu mapMenu;

    private Canvas canvas;
    public GraphicsContext gc;
    public Affine trans = new Affine();


    public MapScene(Model model, MapMenu mapMenu, ZoomMenu zoomMenu, Canvas canvas) {
        super(new VBox());
        this.model = model;
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();

        this.mapMenu = mapMenu;
        this.zoomMenu = zoomMenu;

        StackPane stackedElements = new StackPane();

        canvas.widthProperty().addListener(observable -> redraw());
        canvas.heightProperty().addListener(observable -> redraw());

        // Pan and zoom to frame the map.
        pan(-0.56 * model.getMapObjects().getMinX(), model.getMapObjects().getMaxY());
        zoom(0, 0, canvas.getHeight() / (model.getMapObjects().getMaxY() - model.getMapObjects().getMinY()));
        calculateZoomMenuDistance();
        redraw();

        stackedElements.getChildren().addAll(canvas, mapMenu, zoomMenu);
        StackPane.setAlignment(zoomMenu, Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(mapMenu, Pos.TOP_LEFT);

        this.setRoot(stackedElements);

    }
    public void pan(double dx, double dy){
        trans.prependTranslation(dx, dy);
    }

    public void zoom(double x, double y, double factor) {
        //When prepending scale we are actually multiplying all coordinates by something. If all coordinates are positive and we zoom, all coordinates will move to the right, which is not the desired effect!
        //Therefore, we subtract the position of the mouse to move the map such that the position of the cursor is the origin of the canvas.
        //We then prepend the scale, which has the effect of zooming.
        //Then we add the position of the mouse back in order to move the map back into its original position.

        pan(-x, -y);
        trans.prependScale(factor, factor);
        pan(x, y);

        if(factor < 100){
            zoomMenu.updateZoomLevel(factor);

            if (zoomMenu.getZoomlevel() > 1000){
                // if zooming out
                if (factor < 1){
                    double test = (zoomMenu.getZoomlevel()*factor - 1000);
                    //zoomMenu.distanceLine.setEndX(test);
                }
                // if zooming in
                if (factor > 1){
                    double test = (zoomMenu.getZoomlevel()*factor - 1000);
                    //zoomMenu.distanceLine.setEndX(test);
                }
            } else {
                zoomMenu.distanceLine.setEndX(200);
            }
        }
    }
    public Point2D screenCoordsToMapCoords(Point2D point) {
        try {
            return trans.inverseTransform(point);
        } catch (NonInvertibleTransformException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Bounds screenBoundsToMapBounds(Bounds bounds) {
        try {
            return trans.inverseTransform(bounds);
        } catch (NonInvertibleTransformException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void calculateZoomMenuDistance() {
        Bounds bounds = screenBoundsToMapBounds(canvas.getLayoutBounds());

        double x = MathFunctions.distanceInMeters((float) (bounds.getMinX()/0.56), (float) bounds.getMinY(), (float) (bounds.getMaxX()/0.56), (float) bounds.getMinY());
        zoomMenu.setDistance(x);
    }


    public void resetAffine() {
        trans = new Affine();
    }
    public void redraw() {
        gc.setTransform(new Affine());
        gc.setFill(MapColor.getInstance().colorMap.get("BACKGROUND"));
        gc.setLineCap(StrokeLineCap.ROUND);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setTransform(trans);
        gc.setLineWidth(1 / Math.sqrt(trans.determinant()));

        Bounds bounds = screenBoundsToMapBounds(canvas.getLayoutBounds());

        //Calculate levelOfDetails value, which indicates how many points to skip when drawing map elements.
        int levelOfDetails = 4;
        if (zoomMenu.getZoomlevel() < 3000) {
            levelOfDetails = 2;
        } if (zoomMenu.getZoomlevel() < 1500) {
            levelOfDetails = 1;
        }

        //Draw all elements in correct order
        drawCoastlines(levelOfDetails, bounds);
        drawWaterAreas(levelOfDetails, bounds);
        drawTerrain(levelOfDetails, bounds);
        drawBuildings(levelOfDetails, bounds);
        drawPaths(bounds);
        drawNormalRoads(bounds);
        drawLargeRoads(bounds);
        drawMotorways(bounds);
        drawRoadsClose(bounds);
        drawRoute();
        drawAddress(bounds);
        drawMotorwayNames(bounds);
        drawLargeRoadNames(bounds);
        drawNormalRoadNames(bounds);
        drawCountryNames(bounds);
        drawCityNames(bounds);
        drawTownNames(bounds);
        drawPlaceNames(bounds);
        drawLandmarks(bounds);
        drawBusLandmarks(bounds);
        drawTrainLandmarks(bounds);
        drawCustomLandmarks();
        drawSelectedPoint();
        drawStartAndEndPoint();

    }
    private void drawCoastlines(int levelOfDetail, Bounds bounds) {
        for (Element coastline : model.getMapObjects().getCoastLinesAreaTree().getElementsInRange(bounds)) {
            coastline.draw(gc, levelOfDetail, 1);
        }
    }
    // draw custom landmarks
    private void drawCustomLandmarks() {
        for (PointOfInterest customPointOfInterest : model.getMapObjects().getCustomPOIList()) {
            customPointOfInterest.draw(gc, (1 / Math.sqrt(trans.determinant())) * 20);
        }
    }
    private void drawBusLandmarks(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 300) {
            if (model.isLandmarksVisible) {
                for (PointOfInterest pointOfInterest : model.getMapObjects().getBusPOITree().getElementsInRange(bounds)) {
                    pointOfInterest.draw(gc, (1 / Math.sqrt(trans.determinant())) * 20);
                }
            }
        }
    }
    private void drawTrainLandmarks(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 1000) {
            if (model.isLandmarksVisible) {
                for (PointOfInterest pointOfInterest : model.getMapObjects().getTrainPOITree().getElementsInRange(bounds)) {
                    pointOfInterest.draw(gc, (1 / Math.sqrt(trans.determinant())) * 20);
                }
            }
        }
    }
    private void drawLandmarks(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 75) {
            if (model.isLandmarksVisible) {
                for (PointOfInterest pointOfInterest : model.getMapObjects().getPOITree().getElementsInRange(bounds)) {
                    pointOfInterest.draw(gc, (1 / Math.sqrt(trans.determinant())) * 20);
                }
            }
        }
    }
    private void drawSelectedPoint() {
        if (model.getSelectedPointMarker() != null) {
            model.getSelectedPointMarker().draw(gc, (1 / Math.sqrt(trans.determinant())) * 30);
        }
    }
    private void drawStartAndEndPoint() {
        if (model.getStartLocationMarker() != null) {
            model.getStartLocationMarker().draw(gc, (1 / Math.sqrt(trans.determinant())) * 30);
        }
        if (model.getEndLocationMarker() != null) {
            model.getEndLocationMarker().draw(gc, (1 / Math.sqrt(trans.determinant())) * 30);
        }
    }
    private void drawCountryNames(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 300000 && zoomMenu.getZoomlevel() > 5000) {
            for (PlaceName placeName : model.getMapObjects().getQuiteLargeNameTree().getElementsInRange(bounds)) {
                placeName.draw(gc, (float) (1 / Math.sqrt(trans.determinant())));
            }
        }
    }
    private void drawCityNames(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 300000 && zoomMenu.getZoomlevel() > 150) {
            for (PlaceName placeName : model.getMapObjects().getLargeNameTree().getElementsInRange(bounds)) {
                placeName.draw(gc, (float) (1 / Math.sqrt(trans.determinant())));
            }
        }
    }
    private void drawTownNames(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 20000 && zoomMenu.getZoomlevel() > 150) {
            for (PlaceName placeName : model.getMapObjects().getMediumLargePlaceNameTree().getElementsInRange(bounds)) {
                placeName.draw(gc, (float) (1 / Math.sqrt(trans.determinant())));
            }
        }
    }
    private void drawPlaceNames(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 15000 && zoomMenu.getZoomlevel() > 150) {
            for (PlaceName placeName : model.getMapObjects().getMediumPlaceNameTree().getElementsInRange(bounds)) {
                placeName.draw(gc, (float) (1 / Math.sqrt(trans.determinant())));
            }
        }
    }
    private void drawAddress(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 20 ) {
            if (model.isAddressVisible) {
                gc.setFill(Color.rgb(30, 30,30,1));
                for (Address address : model.getMapObjects().getAddressTree().getElementsInRange(bounds)) {
                    address.draw(gc, (float) (1 / Math.sqrt(trans.determinant())));
                }
            }
        }
    }
    private void drawLargeRoads(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 15000 && zoomMenu.getZoomlevel() > 50) {
            if (model.isRoadsVisible) {
                for (Road road : model.getMapObjects().getLargeRoadsTree().getElementsInRange(bounds)) {
                    road.drawOutline(gc, (1 / Math.sqrt(trans.determinant())));
                }
                for (Road road : model.getMapObjects().getLargeRoadsTree().getElementsInRange(bounds)) {
                    road.draw(gc, (1 / Math.sqrt(trans.determinant())));
                }
            }
        }
    }
    private void drawMotorways(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 15000 && zoomMenu.getZoomlevel() > 50) {
            if (model.isRoadsVisible) {
                for (Road road : model.getMapObjects().getMotorWaysTree().getElementsInRange(bounds)) {
                    road.drawOutline(gc, (1 / Math.sqrt(trans.determinant())));
                }
                for (Road road : model.getMapObjects().getMotorWaysTree().getElementsInRange(bounds)) {
                    road.draw(gc, (1 / Math.sqrt(trans.determinant())));
                }
            }
        }
    }
    private void drawNormalRoads(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 800 && zoomMenu.getZoomlevel() > 50) {
            if (model.isRoadsVisible) {
                for (Road road : model.getMapObjects().getSmallRoadsTree().getElementsInRange(bounds)) {
                    road.drawOutline(gc, (1 / Math.sqrt(trans.determinant())));
                }
                for (Road road : model.getMapObjects().getSmallRoadsTree().getElementsInRange(bounds)) {
                    road.draw(gc, (1 / Math.sqrt(trans.determinant())));
                }
            }
        }
    }
    private void drawRoadsClose(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 50) {
            if (model.isRoadsVisible) {
                for (Road road : model.getMapObjects().getSmallRoadsTree().getElementsInRange(bounds)) {
                    road.drawCloseOutline(gc);
                }
                for (Road road : model.getMapObjects().getLargeRoadsTree().getElementsInRange(bounds)) {
                    road.drawCloseOutline(gc);
                }
                for (Road road : model.getMapObjects().getMotorWaysTree().getElementsInRange(bounds)) {
                    road.drawCloseOutline(gc);
                }
                for (Road road : model.getMapObjects().getSmallRoadsTree().getElementsInRange(bounds)) {
                    road.drawClose(gc);
                }
                for (Road road : model.getMapObjects().getLargeRoadsTree().getElementsInRange(bounds)) {
                    road.drawClose(gc);
                }
                for (Road road : model.getMapObjects().getMotorWaysTree().getElementsInRange(bounds)) {
                    road.drawClose(gc);
                }
            }
        }
    }
    private void drawNormalRoadNames(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 50) {
            if (model.isRoadsVisible) {
                for (Road road : model.getMapObjects().getSmallRoadsTree().getElementsInRange(bounds)) {
                    road.drawName(gc, (1 / Math.sqrt(trans.determinant())));
                }
            }
        }
    }
    private void drawLargeRoadNames(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 50) {
            if (model.isRoadsVisible) {
                for (Road road : model.getMapObjects().getLargeRoadsTree().getElementsInRange(bounds)) {
                    road.drawName(gc, (1 / Math.sqrt(trans.determinant())));
                }
            }
        }
    }
    private void drawMotorwayNames(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 200) {
            if (model.isRoadsVisible) {
                for (Road road : model.getMapObjects().getMotorWaysTree().getElementsInRange(bounds)) {
                    road.drawName(gc, (1 / Math.sqrt(trans.determinant())));
                }
            }
        }
    }

    private void drawPaths(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 200) {
            if (model.isRoadsVisible) {
                for (Road road : model.getMapObjects().getFootPathsTree().getElementsInRange(bounds)) {
                    road.draw(gc, (1 / Math.sqrt(trans.determinant())));
                }
            }
        }
    }
    private void drawTerrain (int levelOfDetail, Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 3000) {
            if (model.isTerrainVisible) {
                for (Element terrainElement : model.getMapObjects().getTerrainAreasTree().getElementsInRange(bounds)) {
                    terrainElement.draw(gc, levelOfDetail, 1);
                    terrainElement.drawBounds(gc);
                }
            }
        }
    }
    private void drawWaterAreas(int levelOfDetail, Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 10000) {
            for (Element waterAreaElement : model.getMapObjects().getWaterAreasTree().getElementsInRange(bounds)) {
                waterAreaElement.draw(gc, levelOfDetail, 1);
            }
        }
    }
    private void drawBuildings(int levelOfDetail, Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 200) {
            if (model.isBuildingsVisible) {
                for (Element elementBuilding : model.getMapObjects().getBuildingsTree().getElementsInRange(bounds)) {
                    elementBuilding.draw(gc, levelOfDetail, 1);
                }
            }
        }
    }
    private void drawRoute() {
        if ( model.getMapObjects().getDigraph() != null) {
            model.getMapObjects().getDigraph().draw(gc);
        }
    }
}
