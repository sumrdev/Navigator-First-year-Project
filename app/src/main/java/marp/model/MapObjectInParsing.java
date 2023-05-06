package marp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.geometry.Point2D;
import marp.mapelements.*;
import marp.mapelements.details.FontSize;
import marp.mapelements.details.PointType;
import marp.mapelements.details.RoadType;
import marp.mapelements.details.ShapeType;

public class MapObjectInParsing implements Serializable{

    private HashMap<Long, Point> pointIDtoPoint = new HashMap<>();
    private HashMap<Long, SimpleShape> SimpleShapeIDToSimpleShape = new HashMap<>();

    private ArrayList<Point> unfinishedSimpleShapePoints = new ArrayList<>();

    private PointType unfinishedPointType = PointType.UNDEFINED;
    private ShapeType unfinishedShapeType = ShapeType.UNDEFINED;
    private RoadType unfinishedRoadType = RoadType.UNDEFINED;
    private boolean isRoad;
    private  boolean oneWay;
    private int speed;
    private Point unfinishedPoint;

    private long unfinishedSimpleShapeID; 
    private long unfinishedRelationID;

    //private Road unfinishedRoad;
    private int completeAddressCount = 0;
    private String city;
    private String housenumber;
    private String postcode;
    private String street;
    private String name;
    private FontSize fontSize = FontSize.UNDEFINED;
    private ArrayList<SimpleShape> unfinishedRelationSimpleShapes = new ArrayList<>();
    private MapObjects mapObjects;

    public MapObjectInParsing(MapObjects mapObjects){
        this.mapObjects = mapObjects;
    }

    //public ArrayList<ComplexShape> getComplexShapes() {
    //    return complexShapes;
    //}

    //public ArrayList<SimpleShape> getSimpleShapes() {
    //    return SimpleShapes;
    //}

    //public void addComplexShape(ComplexShape element){
    //    complexShapes.add(element);
    //}

    //public void addSimpleShape(SimpleShape element){
    //    SimpleShapes.add(element);
    //    SimpleShapeIDToSimpleShape.put(element.getID(), element);
    //}

    public void addPointToHashMap(Point point){
        unfinishedPoint = point;
        pointIDtoPoint.put(point.getID(), point);
    }

    public Point getPointByID(long id){
        return this.pointIDtoPoint.get(id);
    }

    public void addPointToUnfinishedSimpleShape(Point element){
        this.unfinishedSimpleShapePoints.add(element);
    }

    public void initializeEmptySimpleShape(long id){
        this.unfinishedSimpleShapeID = id;
    }

    public void initializeEmptyComplexShape(long id){
        this.unfinishedRelationID = id;
    }

    public void setPointType(PointType pointType){
        this.unfinishedPointType = pointType;
    }

    public void setShapeType(ShapeType shapeType){
        this.unfinishedShapeType = shapeType;
    }

    //public void convertSimpleShapeToRoad(){
    //TODO: WHAT IS HAPPENING HERE...?
    //    unfinishedRoad = new Road(unfinishedRoad);
    //    this.isRoad = true;
    //}

    public void setRoadType(RoadType type){
        unfinishedRoadType = type;
        isRoad = true;
        //convertSimpleShapeToRoad();
        //unfinishedRoad.setRoadType(type);
    }

    public void setRoadOneWay(boolean oneway){
        oneway = true;
        //unfinishedRoad.setOneWay(oneway);
    }

    public void setCity(String city){
        this.completeAddressCount++;
        this.city = city;
    }

    public void setPostcode(String postcode){
        this.completeAddressCount++;
        this.postcode = postcode;
    }

    public void setHouseNumber(String housenumber){
        this.completeAddressCount++;
        this.housenumber = housenumber;
    }

    public void setStreet(String street){
        this.completeAddressCount++;
        this.street = street;
    }

    public void setName(String value) {
        this.name = value;
	}

    public void setFontSize(FontSize value) {
        this.fontSize = value;
    }

    public void finishPoint() {
        if (unfinishedPointType==PointType.UNDEFINED && this.completeAddressCount==4){
            Address address = new Address(this.street, this.housenumber, this.postcode, this.city, unfinishedPoint.getX(), unfinishedPoint.getY(), -1);
            mapObjects.getAddressList().add(address);
        } else if(fontSize != FontSize.UNDEFINED){
            PlaceName placeName = new PlaceName(this.name, this.fontSize, this.unfinishedPoint.getX(), this.unfinishedPoint.getY());
            switch (fontSize) {
                case QUITE_SMALL:
                    mapObjects.getQuiteSmallPlaceNamesList().add(placeName);
                    break;
                case SMALL:
                    mapObjects.getSmallPlaceNamesList().add(placeName);
                    break;
                case MEDIUM:
                    mapObjects.getMediumPlaceNamesList().add(placeName);
                    break;
                case MEDIUM_LARGE:
                    mapObjects.getMediumLargePlaceNamesList().add(placeName);
                    break;
                case LARGE:
                    mapObjects.getLargePlaceNamesList().add(placeName);
                    break;
                case QUITE_LARGE:
                    mapObjects.getQuiteLargePlaceNamesList().add(placeName);
                    break;
                }
        } else if (unfinishedPointType != PointType.UNDEFINED) {
            PointOfInterest pointOfInterest = new PointOfInterest(this.name, this.unfinishedPointType, this.unfinishedPoint.getX(), this.unfinishedPoint.getY(), false);
            switch (unfinishedPointType) {
                case BUS_STOP:
                    mapObjects.getBusPOIList().add(pointOfInterest);
                    break;
                case METRO_STATION:
                case TRAIN_STATION:
                    mapObjects.getTrainPOIList().add(pointOfInterest);
                    break;
                default:
                    mapObjects.getPOIList().add(pointOfInterest);
                    break;
            }
        }
        cleanUpAddressVariables();
        this.unfinishedPoint = null;
        this.unfinishedPointType = PointType.UNDEFINED;
        this.fontSize = FontSize.UNDEFINED;
        this.name = null;
    }

    public void finishSimpleShape(){
        if(!isRoad){
            ArrayList<float[]> coords = convertPointArrToUseableFloatArr();
            if(this.unfinishedPointType!=PointType.UNDEFINED){
                // Some shapes have information that we would like to show as points. To accomplish this, we make a new
                // point of interest in the middle of the shape corresponding to the type, if the shape has a point type value.
                Point2D shapeCenterCoords = findCenterOfShape(coords.get(0), coords.get(1));
                PointOfInterest pointOfInterest = new PointOfInterest(this.name, this.unfinishedPointType, (float) shapeCenterCoords.getX(), (float) shapeCenterCoords.getY(), false);
                mapObjects.getPOIList().add(pointOfInterest);
            }
            if (this.completeAddressCount==4) {
                Point2D shapeCenterCoords = findCenterOfShape(coords.get(0), coords.get(1));
                Address address = new Address(street, housenumber, postcode, city, (float) shapeCenterCoords.getX(), (float) shapeCenterCoords.getY(), -1);
                mapObjects.getAddressList().add(address);
            }
            if(this.unfinishedShapeType==ShapeType.UNDEFINED){
                this.SimpleShapeIDToSimpleShape.put(this.unfinishedSimpleShapeID, new SimpleShape(this.unfinishedSimpleShapeID, this.unfinishedShapeType, coords.get(0),coords.get(1)));
            }else {
                switch (this.unfinishedShapeType) {
                    case BUILDING:
                        mapObjects.getBuildingsList().add(new SimpleShape(this.unfinishedSimpleShapeID, this.unfinishedShapeType, coords.get(0), coords.get(1)));
                        break;
                    case WATER:
                        mapObjects.getWaterAreasList().add(new SimpleShape(this.unfinishedSimpleShapeID, this.unfinishedShapeType, coords.get(0), coords.get(1)));
                        break;
                    case GRASS:
                    case FOREST:
                    case CEMENT:
                    case COMMERCIAL_GROUND:
                    case FARMLAND:
                        mapObjects.getTerrainAreasList().add(new SimpleShape(this.unfinishedSimpleShapeID, this.unfinishedShapeType, coords.get(0), coords.get(1)));
                        break;
                    default:
                        break;
                }
            }
        } else {
            if (unfinishedRoadType != RoadType.UNDEFINED) {
                if (speed == -1) {
                    //If the speed hasn't been set, we get the speed from the standard speed limits for different road types.
                    speed = unfinishedRoadType.getSpeed();
                }
            }
            switch (unfinishedRoadType) {
                case MOTORWAY:
                    mapObjects.getMotorWaysList().add(new Road(this.unfinishedSimpleShapeID, this.unfinishedSimpleShapePoints, this.unfinishedRoadType, this.speed, this.oneWay, this.name));
                    break;
                case PRIMARY:
                case TERTIARY:
                    mapObjects.getLargeRoadsList().add(new Road(this.unfinishedSimpleShapeID, this.unfinishedSimpleShapePoints, this.unfinishedRoadType, this.speed, this.oneWay, this.name));
                    break;
                case RESIDENTIAL:
                case PEDESTRIAN:
                    mapObjects.getSmallRoadsList().add(new Road(this.unfinishedSimpleShapeID, this.unfinishedSimpleShapePoints, this.unfinishedRoadType, this.speed, this.oneWay, this.name));
                    break;
                case PATH:
                    mapObjects.getFootPathsList().add(new Road(this.unfinishedSimpleShapeID, this.unfinishedSimpleShapePoints, this.unfinishedRoadType, this.speed, this.oneWay, this.name));
                    break;
            }
        }
        unfinishedSimpleShapePoints = new ArrayList<>();
        unfinishedSimpleShapeID = 0;
        unfinishedShapeType = ShapeType.UNDEFINED;
        unfinishedPointType = PointType.UNDEFINED;
        isRoad = false;
        speed = -1;
        unfinishedRoadType = RoadType.UNDEFINED;
        cleanUpAddressVariables();
    }

    private void cleanUpAddressVariables(){
        this.city = null;
        this.postcode = null;
        this.housenumber = null;
        this.street = null;
        this.completeAddressCount = 0;
    }

    public SimpleShape getSimpleShapeByID(long id){
        return this.SimpleShapeIDToSimpleShape.get(id);
    }

    public void handleSimpleShape(long id, String role){
        SimpleShape element = this.SimpleShapeIDToSimpleShape.get(id);
        if(element==null) return;
        element.setRole(role);
        unfinishedRelationSimpleShapes.add(element);

    }

    public void finishRelation(){
        //TODO: We have to add the relation to more specific lists based on type.
        //complexShapes.add(new ComplexShape(unfinishedRelationID,this.unfinishedShapeType, unfinishedRelationSimpleShapes));
        unfinishedRelationSimpleShapes = new ArrayList<>();
        unfinishedRelationID = 0;
        unfinishedShapeType = ShapeType.UNDEFINED;
    }

    private ArrayList<float[]> convertPointArrToUseableFloatArr() {
        ArrayList<Float> x = new ArrayList<>();
        ArrayList<Float> y = new ArrayList<>();
        ArrayList<float[]> result = new ArrayList<>();
        
        for (Point point : unfinishedSimpleShapePoints) {
            x.add(point.getX());
            y.add(point.getY());
        }

        float[] xFinal = new float[x.size()];
        float[] yFinal = new float[y.size()];
        result.add(xFinal);
        result.add(yFinal);

        for (int i = 0; i > x.size()-1; i++) {
            xFinal[i] = x.get(i);
            yFinal[i] = y.get(i);
        }
        return result;
    }
    private Point2D findCenterOfShape(float[] xCoords, float[] yCoords) {
        // to find the middle we take the average x and y coordinate.
        float sumX = 0;
        float sumY = 0;
        for (float x : xCoords) {
            sumX += x;
        }
        for (float y : yCoords) {
            sumY += y;
        }
        return new Point2D(sumX/xCoords.length, sumY/yCoords.length);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}