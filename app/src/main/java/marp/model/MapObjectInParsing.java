package marp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import javafx.geometry.Point2D;
import marp.mapelements.*;
import marp.mapelements.details.FontSize;
import marp.mapelements.details.PointType;
import marp.mapelements.details.RoadType;
import marp.mapelements.details.ShapeType;

public class MapObjectInParsing implements Serializable {

    private HashMap<Long, Point> pointIDtoPoint = new HashMap<>();
    private HashMap<Long, RoadNode> roadNodeIDtoRoadNode = new HashMap<>();
    private HashMap<Long, SimpleShape> SimpleShapeIDToSimpleShape = new HashMap<>();

    private ArrayList<Point> unfinishedSimpleShapePoints = new ArrayList<>();

    private PointType unfinishedPointType = PointType.UNDEFINED;
    private ShapeType unfinishedShapeType = ShapeType.UNDEFINED;
    private RoadType unfinishedRoadType = RoadType.UNDEFINED;
    private boolean isRoad;
    private boolean isOneWay;
    private boolean roundabout;
    private int speed;
    private boolean isTunnel = false;
    private Point unfinishedPoint;

    public long unfinishedSimpleShapeID;
    private ArrayList<SimpleShape> coastLineSegmentList = new ArrayList<>();

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
    }

    public void setPointType(PointType pointType){
        this.unfinishedPointType = pointType;
    }

    public void setShapeType(ShapeType shapeType){
        this.unfinishedShapeType = shapeType;
    }

    public void setRoadType(RoadType type){
        unfinishedRoadType = type;
        isRoad = true;
    }

    public void setRoadOneWay(boolean oneway){
        isOneWay = oneway;
    }

    public void setRoadRoundabout(boolean roundabout){
        this.roundabout = true;
    }

    public HashMap<Long, RoadNode> getRoadNodeIDtoRoadNode() {
        return roadNodeIDtoRoadNode;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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
            Address address = new Address(this.street, this.housenumber, this.postcode, this.city, unfinishedPoint.getX(), unfinishedPoint.getY());
            mapObjects.getAddressList().add(address);
            mapObjects.getTrie().insert(address);
        } else if (fontSize != FontSize.UNDEFINED) {
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
            //some points of interest only have a type and no name
            if (this.name == null){
                this.name = unfinishedPointType.typeName;
            }
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
        this.unfinishedShapeType = ShapeType.UNDEFINED;
        this.fontSize = FontSize.UNDEFINED;
        this.name = null;
        this.unfinishedRoadType = RoadType.UNDEFINED;
        this.isRoad = false;
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
                Address address = new Address(street, housenumber, postcode, city, (float) shapeCenterCoords.getX(), (float) shapeCenterCoords.getY());
                mapObjects.getAddressList().add(address);
                mapObjects.getTrie().insert(address);
            }
            if(this.unfinishedShapeType==ShapeType.UNDEFINED){
                this.SimpleShapeIDToSimpleShape.put(this.unfinishedSimpleShapeID, new SimpleShape(this.unfinishedShapeType, coords.get(0),coords.get(1)));
            }else {
                switch (this.unfinishedShapeType) {
                    case COASTLINE:
                        coastLineSegmentList.add(new SimpleShape(this.unfinishedShapeType, coords.get(0), coords.get(1)));
                        break;
                    case BUILDING:
                        mapObjects.getBuildingsList().add(new SimpleShape( this.unfinishedShapeType, coords.get(0), coords.get(1)));
                        break;
                    case WATER:
                        mapObjects.getWaterAreasList().add(new SimpleShape( this.unfinishedShapeType, coords.get(0), coords.get(1)));
                        break;
                    case GRASS:
                    case FOREST:
                    case CEMENT:
                    case COMMERCIAL_GROUND:
                        mapObjects.getTerrainAreasList().add(new SimpleShape(this.unfinishedShapeType, coords.get(0), coords.get(1)));
                        break;
                    case WATERWAY:
                        mapObjects.getWaterwayList().add(new SimpleShape(this.unfinishedShapeType, coords.get(0), coords.get(1)));
                        break;
                    case RAILWAY:
                        if (!isTunnel) {
                            mapObjects.getRailwayList().add(new SimpleShape(this.unfinishedShapeType, coords.get(0), coords.get(1)));
                        }
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
            ArrayList<RoadNode> roadNodes = new ArrayList<>();
            for(Point point: this.unfinishedSimpleShapePoints){
                if(!roadNodeIDtoRoadNode.containsKey(point.getID())){
                    roadNodeIDtoRoadNode.put(point.getID(), new RoadNode(point));
                }
                roadNodes.add(roadNodeIDtoRoadNode.get(point.getID()));
            }
            Road road = new Road(this.unfinishedSimpleShapeID, roadNodes, this.unfinishedRoadType, this.speed, this.isOneWay, this.roundabout, this.name);
            mapObjects.getRoadsList().add(road);
            SimpleShapeIDToSimpleShape.put(this.unfinishedSimpleShapeID, new SimpleShape(this.unfinishedShapeType, roadNodes));
            switch (unfinishedRoadType) {
                case MOTORWAY:
                    mapObjects.getMotorWaysList().add(road);
                    break;
                case PRIMARY:
                    mapObjects.getLargeRoadsList().add(road);
                    break;
                case TERTIARY:
                    mapObjects.getMediumRoadsList().add(road);
                    break;
                case RESIDENTIAL:
                case PEDESTRIAN:
                case SERVICE:
                    mapObjects.getSmallRoadsList().add(road);
                    break;
                case PATH:
                    mapObjects.getFootPathsList().add(road);
                    break;
            }
        }
        unfinishedSimpleShapePoints = new ArrayList<>();
        unfinishedSimpleShapeID = 0;
        unfinishedShapeType = ShapeType.UNDEFINED;
        unfinishedPointType = PointType.UNDEFINED;
        isRoad = false;
        roundabout = false;
        isOneWay = false;
        speed = -1;
        isTunnel = false;
        unfinishedRoadType = RoadType.UNDEFINED;
        cleanUpAddressVariables();
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
        deletePointHashMap();
        if (this.completeAddressCount==4){
            Address address = new Address(this.street, this.housenumber, this.postcode, this.city, unfinishedPoint.getX(), unfinishedPoint.getY());
            mapObjects.getAddressList().add(address);
            mapObjects.getTrie().insert(address);
        }
        if (unfinishedRelationSimpleShapes.size() > 0) {
            switch (unfinishedShapeType) {
                case BUILDING:
                    mapObjects.getBuildingsList().add(new ComplexShape( this.unfinishedShapeType, this.unfinishedRelationSimpleShapes));
                    break;
                case WATER:
                    mapObjects.getWaterAreasList().add(new ComplexShape( this.unfinishedShapeType, this.unfinishedRelationSimpleShapes));
                    break;
                case GRASS:
                case FOREST:
                case CEMENT:
                case COMMERCIAL_GROUND:
                    mapObjects.getTerrainAreasList().add(new ComplexShape(this.unfinishedShapeType, this.unfinishedRelationSimpleShapes));
                    break;
                default:
                    break;
            }
        }
        cleanUpAddressVariables();
        unfinishedRelationSimpleShapes = new ArrayList<>();
        unfinishedShapeType = ShapeType.UNDEFINED;
        unfinishedPointType = PointType.UNDEFINED;
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

        for (int i = 0; i < x.size(); i++) {
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

    public ArrayList<SimpleShape> getCoastlineSegments() {
        return coastLineSegmentList;
    }

    public void setIsTunnel(boolean tunnel) {
        this.isTunnel = tunnel;
    }

    public void deletePointHashMap() {
        if (this.pointIDtoPoint != null) {
            this.pointIDtoPoint = null;
            System.gc();
        }
    }

    private void cleanUpAddressVariables(){
        this.city = null;
        this.postcode = null;
        this.housenumber = null;
        this.street = null;
        this.completeAddressCount = 0;
    }
}
