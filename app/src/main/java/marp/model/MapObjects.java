package marp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import marp.datastructures.RTree;
import marp.mapelements.ComplexShape;
import marp.mapelements.Road;
import marp.mapelements.SimpleShape;
import marp.mapelements.Point;
import marp.mapelements.LandMark;

public class MapObjects implements Serializable{
    private ArrayList<ComplexShape> complexShapes = new ArrayList<>();
    private ArrayList<SimpleShape> SimpleShapes = new ArrayList<>();
    private ArrayList<Road> roads = new ArrayList<>();
    public ArrayList<Point> points = new ArrayList<>();

    public RTree<Point> pointTree;

    private RTree<Road> motorwayTree;
    private RTree<Road> primaryRoadTree;
    private RTree<Road> smallRoadTree;

    private RTree<SimpleShape> buildingTree;
    private RTree<SimpleShape> waterTree;
    private RTree<SimpleShape> landuseTree;

    private RTree<ComplexShape> coastlineTree;
    private RTree<ComplexShape> complexBuildingTree;

    private RTree<LandMark> landMarkTree;

    private HashMap<Long, Point> pointIDtoPoint = new HashMap<>();
    private HashMap<Long, SimpleShape> SimpleShapeIDToSimpleShape = new HashMap<>();

    private ArrayList<Point> unfinishedSimpleShapePoints = new ArrayList<>();

    private String unfinishedPointType;
    private String unfinishedShapeType;


    private Point unfinishedPoint;

    private long unfinishedSimpleShapeID; 
    private long unfinishedRelationID;

    private Road unfinishedRoad;

    private boolean isRoad;

    private int completeAddressCount = 0;
    private String city;
    private String housenumber;
    private String postcode;
    private String street;
    
    private String name;

    private int fontSize;

    private ArrayList<SimpleShape> unfinishedRelationSimpleShapes = new ArrayList<>();


    private float minX;
    private float minY;
    private float maxX;
    private float maxY;

    public MapObjects(){

    }

    public float getMinX(){
        return this.minX;
    }

    public float getMinY(){
        return this.minY;
    }

    public float getMaxX(){
        return this.maxX;
    }

    public float getMaxY(){
        return this.maxY;
    }

    public ArrayList<ComplexShape> getComplexShapes() {
        return complexShapes;
    }

    public void addPoint(Point element){
        points.add(element);
    }

    public void buildPointTree(){
        pointTree = new RTree<>(points);
    }

    public ArrayList<SimpleShape> getSimpleShapes() {
        return SimpleShapes;
    }

    public void addComplexShape(ComplexShape element){
        complexShapes.add(element);
    }

    public void addSimpleShape(SimpleShape element){
        SimpleShapes.add(element);
        SimpleShapeIDToSimpleShape.put(element.getID(), element);
    }

    public void addPointToHashMap(Point element){
        unfinishedPoint = element;
        pointIDtoPoint.put(element.getID(), element);
    }

    public Point getPointByID(long id){
        return this.pointIDtoPoint.get(id);
    }

    public void setMinX(float value){
        this.minX = value;
    }

    public void setMinY(float value){
        this.minY = value;
    }

    public void setMaxX(float value){
        this.maxX = value;
    }

    public void setMaxY(float value){
        this.maxY = value;
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

    public void setPointType(String type){
        this.unfinishedPointType = type;
    }

    public void setShapeType(String type){
        this.unfinishedShapeType = type;
    }

    public void convertSimpleShapeToRoad(){
        unfinishedRoad = new Road(unfinishedSimpleShapeID, unfinishedSimpleShapePoints);
        this.isRoad = true;
    }

    public void setRoadType(String type){
        convertSimpleShapeToRoad();
        unfinishedRoad.setType(type);
    }

    public void setRoadOneWay(boolean oneway){
        unfinishedRoad.setOneWay(oneway);
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

    public void setFontSize(int value) {
        this.fontSize = value;
    }

    public void finishPoint() {
        if (unfinishedPointType!=null){
            new LandMark(name, unfinishedPointType, unfinishedPointType, unfinishedPoint.getX(), unfinishedPoint.getY());
        } else if(this.completeAddressCount==4){
            //create address element and add to trie
        } else if (fontSize!=0){
            //create placename element
        }
        cleanUpAddressVariables();
        this.unfinishedPoint = null;
        this.unfinishedPointType = null;
        this.fontSize = 0;
        this.name = null;
    }


    public void finishSimpleShape(){
        if(!isRoad){
            ArrayList<float[]> coords = convertPointArrToUseableFloatArr();
            if(this.unfinishedPointType!=null){
                //create POI element and add to trie
            }
            if(this.unfinishedShapeType==null){
                this.SimpleShapeIDToSimpleShape.put(this.unfinishedSimpleShapeID, new SimpleShape(this.unfinishedSimpleShapeID, this.unfinishedShapeType, coords.get(0),coords.get(1)));
            }else {
                switch (this.unfinishedShapeType) {
                    case "building":
                        //add to separate tree
                        break;
                    case "water":
                        //add to separate tree
                        break;
                    case "landuse":
                    case "grass":
                    case "forest":
                    case "cement":
                    case "commercial":
                    case "farmland":
                        //add to separate tree
                        break;
                    default:
                        break;
                }
            }
            // add to tree command: new SimpleShape(this.unfinishedSimpleShapeID, this.unfinishedShapeType, coords.get(0),coords.get(1));
        }else if (this.completeAddressCount==4){
            //create address element and add to trie
        } else { 
            roads.add(this.unfinishedRoad);
            // add to tree
        }
        isRoad = false;
        unfinishedSimpleShapePoints = new ArrayList<>();
        unfinishedSimpleShapeID = 0;
        unfinishedShapeType = null;
        unfinishedPointType = null;
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
        complexShapes.add(new ComplexShape(unfinishedRelationID,this.unfinishedShapeType, unfinishedRelationSimpleShapes));
        unfinishedRelationSimpleShapes = new ArrayList<>();
        unfinishedRelationID = 0;
        unfinishedShapeType = null;
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



}
