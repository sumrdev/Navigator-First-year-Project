package marp.view;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;
import marp.mapelements.*;
import marp.mapelements.details.MapColor;
import marp.mapelements.details.RoadType;
import marp.mapelements.details.ShapeType;
import marp.model.Model;
import marp.utilities.MathFunctions;
import marp.view.gui.NearestRoadInfo;
import marp.view.gui.ZoomMenu;
import marp.view.gui.buttons.MapTextButton;
import marp.view.gui.menugui.MapMenu;

public class MapScene extends Scene{
    public MapTextButton loadButton;
    private Model model;
    private ZoomMenu zoomMenu;
    private Canvas canvas;
    public GraphicsContext gc;
    public Affine trans = new Affine();

    public MapScene(Model model, MapMenu mapMenu, ZoomMenu zoomMenu, NearestRoadInfo nearestRoadInfo, Canvas canvas) {
        super(new VBox());
        this.model = model;
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();

        this.zoomMenu = zoomMenu;
        StackPane stackedElements = new StackPane();

        canvas.widthProperty().addListener(observable -> redraw());
        canvas.heightProperty().addListener(observable -> redraw());

        // Pan and zoom to frame the map.
        pan(-0.56 * model.getMapObjects().getMinX(), model.getMapObjects().getMaxY());
        zoom(0, 0, canvas.getHeight() / (model.getMapObjects().getMaxY() - model.getMapObjects().getMinY()));
        calculateZoomMenuDistance();
        redraw();

        stackedElements.getChildren().addAll(canvas, mapMenu, zoomMenu, nearestRoadInfo);
        StackPane.setAlignment(zoomMenu, Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(mapMenu, Pos.TOP_LEFT);
        StackPane.setAlignment(nearestRoadInfo, Pos.BOTTOM_LEFT);

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

        if((zoomMenu.getZoomlevel() <= 15) && (factor > 1)){
            System.out.println("CANNOT ZOOM IN FURTHER");
        } else if((zoomMenu.getZoomlevel() >= 50000) && (factor < 1)){
            System.out.println("CANNOT ZOOM OUT FURTHER");
        } else {
            pan(-x, -y);
            trans.prependScale(factor, factor);
            pan(x, y);

            zoomMenu.updateZoomLevel(factor);
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
        Point2D a = screenCoordsToMapCoords(new Point2D(100, 150));
        Point2D b = screenCoordsToMapCoords(new Point2D(200, 150));
        double x = MathFunctions.distanceInMeters((float) a.getX(),(float) a.getY(),(float) b.getX(),(float) b.getY());
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
        int levelOfDetails = 16;
        if (zoomMenu.getZoomlevel() < 10000) {
            levelOfDetails = 4;
        }
        if (zoomMenu.getZoomlevel() < 3000) {
            levelOfDetails = 2;
        } if (zoomMenu.getZoomlevel() < 1500) {
            levelOfDetails = 1;
        }

        //Draw all elements in correct order
        drawCoastlines(levelOfDetails, bounds);
        //drawBounds(bounds);
        drawTerrain(levelOfDetails, bounds);
        drawWaterAreas(levelOfDetails, bounds);
        drawWaterway(bounds);
        drawBuildings(levelOfDetails, bounds);
        drawPaths(bounds);
        drawSmallRoads(bounds);
        drawMediumRoads(bounds);
        drawLargeRoads(bounds);
        drawMotorways(bounds);
        drawRoadsClose(bounds);
        drawRailway(bounds);
        drawRoute();
        drawAddress(bounds);
        drawMotorwayNames(bounds);
        drawLargeRoadNames(bounds);
        drawMediumRoadNames(bounds);
        drawSmallRoadNames(bounds);
        drawSmallPlaceNames(bounds);
        drawPlaceNames(bounds);
        drawTownNames(bounds);
        drawCityNames(bounds);
        drawCountryNames(bounds);
        drawLandmarks(bounds);
        drawBusLandmarks(bounds);
        drawTrainLandmarks(bounds);
        drawCustomLandmarks();
        drawSelectedPoint();
        drawStartAndEndPoint();
    }

    private void drawCoastlines(int levelOfDetail, Bounds bounds) {
        MapColor.getInstance().updateCoastlineColorFromZoom(zoomMenu.getZoomlevel());
        for (Element coastline : model.getMapObjects().getCoastLinesAreaTree().getElementsInRange(bounds)) {
            coastline.draw(gc, levelOfDetail, 1);
        }
    }
    // draw custom landmarks
    private void drawCustomLandmarks() {
        for (PointOfInterest customPointOfInterest : model.getMapObjects().getCustomPOIList()) {
            customPointOfInterest.draw(gc, (1 / Math.sqrt(trans.determinant())) * 30);
        }
        for (PointOfInterest favouritePointMarker : model.getMapObjects().getFavouritesMarkerList()) {
            favouritePointMarker.draw(gc, (1 / Math.sqrt(trans.determinant())) * 30);
        }
    }
    private void drawBusLandmarks(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 200) {
            if (model.isLandmarksVisible) {
                for (PointOfInterest pointOfInterest : model.getMapObjects().getBusPOITree().getElementsInRange(bounds)) {
                    pointOfInterest.draw(gc, (1 / Math.sqrt(trans.determinant())) * 20);
                }
            }
        }
    }
    private void drawTrainLandmarks(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 500) {
            if (model.isLandmarksVisible) {
                for (PointOfInterest pointOfInterest : model.getMapObjects().getTrainPOITree().getElementsInRange(bounds)) {
                    pointOfInterest.draw(gc, (1 / Math.sqrt(trans.determinant())) * 20);
                }
            }
        }
    }
    private void drawLandmarks(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 100) {
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
        if (model.getNavigationVisibility()) {
            if (model.getStartLocationMarker() != null) {
                model.getStartLocationMarker().draw(gc, (1 / Math.sqrt(trans.determinant())) * 30);
            }
            if (model.getEndLocationMarker() != null) {
                model.getEndLocationMarker().draw(gc, (1 / Math.sqrt(trans.determinant())) * 30);
            }
        }
    }
    public void drawUserMadeLine(Point2D start, Point2D end){
        start = screenCoordsToMapCoords(start);
        end   = screenCoordsToMapCoords(end);
        gc.setStroke(Color.PURPLE);
        gc.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
        System.out.println("distance of userMadeLine: " + MathFunctions.distanceInMeters((float)start.getX(), (float)start.getY(), (float)end.getX(), (float)end.getY()));
    }
    private void drawCountryNames(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 300000 && zoomMenu.getZoomlevel() > 5000) {
            for (PlaceName placeName : model.getMapObjects().getQuiteLargeNameTree().getElementsInRange(bounds)) {
                placeName.draw(gc, (float) (1 / Math.sqrt(trans.determinant())));
            }
        }
    }
    private void drawCityNames(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 100000 && zoomMenu.getZoomlevel() > 150) {
            MapColor.getInstance().fadeTextColor((zoomMenu.getZoomlevel()), 100000, 5000);
            for (PlaceName placeName : model.getMapObjects().getLargeNameTree().getElementsInRange(bounds)) {
                placeName.draw(gc, (float) (1 / Math.sqrt(trans.determinant())));
            }
        }
    }
    private void drawTownNames(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 7000 && zoomMenu.getZoomlevel() > 150) {
            MapColor.getInstance().fadeTextColor((zoomMenu.getZoomlevel()), 7000, 1000);
            for (PlaceName placeName : model.getMapObjects().getMediumLargePlaceNameTree().getElementsInRange(bounds)) {
                placeName.draw(gc, (float) (1 / Math.sqrt(trans.determinant())));
            }
        }
    }
    private void drawPlaceNames(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 7500 && zoomMenu.getZoomlevel() > 150) {
            MapColor.getInstance().fadeTextColor((zoomMenu.getZoomlevel()), 7500, 1000);
            for (PlaceName placeName : model.getMapObjects().getMediumPlaceNameTree().getElementsInRange(bounds)) {
                placeName.draw(gc, (float) (1 / Math.sqrt(trans.determinant())));
            }
        }
    }
    private void drawSmallPlaceNames(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 2500 && zoomMenu.getZoomlevel() > 150) {
            MapColor.getInstance().fadeTextColor((zoomMenu.getZoomlevel()), 2500, 500);
            for (PlaceName placeName : model.getMapObjects().getSmallPlaceNameTree().getElementsInRange(bounds)) {
                placeName.draw(gc, (float) (1 / Math.sqrt(trans.determinant())));
            }
        }
    }
    private void drawAddress(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 20 ) {
            MapColor.getInstance().fadeTextColor((zoomMenu.getZoomlevel()), 20, 5);
            if (model.isAddressVisible) {
                gc.setFill(Color.rgb(30, 30,30,1));
                for (Address address : model.getMapObjects().getAddressTree().getElementsInRange(bounds)) {
                    address.draw(gc, (float) (1 / Math.sqrt(trans.determinant())));
                }
            }
        }
    }
    private void drawMotorways(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 50000 && zoomMenu.getZoomlevel() > 50) {
            MapColor.getInstance().fadeRoadColor((zoomMenu.getZoomlevel()),RoadType.MOTORWAY, 50000, 5000);

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
    private void drawLargeRoads(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 15000 && zoomMenu.getZoomlevel() > 50) {
            MapColor.getInstance().fadeRoadColor((zoomMenu.getZoomlevel()),RoadType.PRIMARY, 15000, 3000);
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
    private void drawMediumRoads(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 2500 && zoomMenu.getZoomlevel() > 50) {
            MapColor.getInstance().fadeRoadColor((zoomMenu.getZoomlevel()),RoadType.TERTIARY, 2500, 150);
            if (model.isRoadsVisible) {
                for (Road road : model.getMapObjects().getMediumRoadsTree().getElementsInRange(bounds)) {
                    road.drawOutline(gc, (1 / Math.sqrt(trans.determinant())));
                }
                for (Road road : model.getMapObjects().getMediumRoadsTree().getElementsInRange(bounds)) {
                    road.draw(gc, (1 / Math.sqrt(trans.determinant())));
                }
            }
        }
    }
    private void drawSmallRoads(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 750 && zoomMenu.getZoomlevel() > 50) {
            if (model.isRoadsVisible) {
                MapColor.getInstance().fadeRoadColor((zoomMenu.getZoomlevel()),RoadType.RESIDENTIAL, 750, 100);
                MapColor.getInstance().fadeRoadColor((zoomMenu.getZoomlevel()),RoadType.PEDESTRIAN, 750, 100);

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
                for (Road road : model.getMapObjects().getMediumRoadsTree().getElementsInRange(bounds)) {
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
                for (Road road : model.getMapObjects().getMediumRoadsTree().getElementsInRange(bounds)) {
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
    private void drawSmallRoadNames(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 35) {
            MapColor.getInstance().fadeTextColor((zoomMenu.getZoomlevel()), 35, 10);
            if (model.isRoadsVisible) {
                for (Road road : model.getMapObjects().getSmallRoadsTree().getElementsInRange(bounds)) {
                    road.drawName(gc, (1 / Math.sqrt(trans.determinant())));
                }
            }
        }
    }
    private void drawMediumRoadNames(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 50) {
            MapColor.getInstance().fadeTextColor((zoomMenu.getZoomlevel()), 50, 10);
            if (model.isRoadsVisible) {
                for (Road road : model.getMapObjects().getMediumRoadsTree().getElementsInRange(bounds)) {
                    road.drawName(gc, (1 / Math.sqrt(trans.determinant())));
                }
            }
        }
    }
    private void drawLargeRoadNames(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 50) {
            MapColor.getInstance().fadeTextColor((zoomMenu.getZoomlevel()), 50, 10);
            if (model.isRoadsVisible) {
                for (Road road : model.getMapObjects().getLargeRoadsTree().getElementsInRange(bounds)) {
                    road.drawName(gc, (1 / Math.sqrt(trans.determinant())));
                }
            }
        }
    }
    private void drawMotorwayNames(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 175) {
            MapColor.getInstance().fadeTextColor((zoomMenu.getZoomlevel()), 175, 50);
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
                MapColor.getInstance().fadeRoadColor((zoomMenu.getZoomlevel()), RoadType.PATH, 200, 50);
                for (Road road : model.getMapObjects().getFootPathsTree().getElementsInRange(bounds)) {
                    road.draw(gc, (1 / Math.sqrt(trans.determinant())));
                }
            }
        }
    }
    private void drawTerrain (int levelOfDetail, Bounds bounds) {
        MapColor.getInstance().fadeElementColor((zoomMenu.getZoomlevel()), ShapeType.GRASS, 5000, 700);
        MapColor.getInstance().fadeElementColor((zoomMenu.getZoomlevel()), ShapeType.FOREST, 5000, 700);
        MapColor.getInstance().fadeElementColor((zoomMenu.getZoomlevel()), ShapeType.CEMENT, 5000, 700);
        MapColor.getInstance().fadeElementColor((zoomMenu.getZoomlevel()), ShapeType.COMMERCIAL_GROUND, 5000, 700);
        if (zoomMenu.getZoomlevel() < 5000) {
            if (model.isTerrainVisible) {
                for (Element terrainElement : model.getMapObjects().getTerrainAreasTree().getElementsInRange(bounds)) {
                    terrainElement.draw(gc, levelOfDetail, 1);
                }
            }
        }
    }
    private void drawWaterAreas(int levelOfDetail, Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 10000) {
            MapColor.getInstance().fadeElementColor((zoomMenu.getZoomlevel()), ShapeType.WATER, 10000, 700);
            for (Element waterAreaElement : model.getMapObjects().getWaterAreasTree().getElementsInRange(bounds)) {
                waterAreaElement.draw(gc, levelOfDetail, 1);
            }
        }
    }
    private void drawBuildings(int levelOfDetail, Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 200) {
            MapColor.getInstance().fadeElementColor((zoomMenu.getZoomlevel()), ShapeType.BUILDING, 200, 50);
            if (model.isBuildingsVisible) {
                for (Element elementBuilding : model.getMapObjects().getBuildingsTree().getElementsInRange(bounds)) {
                    elementBuilding.draw(gc, levelOfDetail, 1);
                }
            }
        }
    }
    private void drawRailway(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 750) {
            MapColor.getInstance().fadeElementColor((zoomMenu.getZoomlevel()), ShapeType.RAILWAY, 750, 200);
            if (model.isRoadsVisible) {
                for (SimpleShape railway : model.getMapObjects().getRailwayTree().getElementsInRange(bounds)) {
                    railway.drawLine(gc, (1 / Math.sqrt(trans.determinant())));
                }
            }
        }
    }
    private void drawWaterway(Bounds bounds) {
        if (zoomMenu.getZoomlevel() < 1500) {
            MapColor.getInstance().fadeElementColor((zoomMenu.getZoomlevel()), ShapeType.WATERWAY, 1500, 500);
            if (model.isRoadsVisible) {
                for (SimpleShape waterway : model.getMapObjects().getWaterwayTree().getElementsInRange(bounds)) {
                    waterway.drawLine(gc, (1 / Math.sqrt(trans.determinant())));
                }
            }
        }
    }
    private void drawRoute() {
        if (model.getNavigationVisibility()) {
            if (model.getMapObjects().getDigraph() != null) {
                model.getMapObjects().getDigraph().draw(gc);
            }
        }
    }
    private void drawBounds(Bounds bounds) {
        for (Element element : model.getMapObjects().getCoastLinesAreaTree().getElementsInRange(bounds)) {
            element.drawBounds(gc, (float) (1 / Math.sqrt(trans.determinant())));
        }
    }
}
