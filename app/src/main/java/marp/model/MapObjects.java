package marp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import marp.mapelements.ComplexElement;
import marp.mapelements.Road;
import marp.mapelements.SimpleElement;
import marp.mapelements.Point;

public class MapObjects {
    private ArrayList<ComplexElement> complexElements = new ArrayList<>();
    private ArrayList<SimpleElement> simpleElements = new ArrayList<>();
    private ArrayList<Road> roads = new ArrayList<>();

    private HashMap<Long, Point> pointIDtoPoint = new HashMap<>();
    private HashMap<Long, SimpleElement> simpleElementIDtoElement = new HashMap<>();

    private ArrayList<Point> unfinishedSimpleElementPoints = new ArrayList<>();

    private String unfinishedPointType;
    private String unfinishedShapeType;


    private Point unfinishedPoint;

    private long unfinishedSimpleElementID; 
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

    private ArrayList<SimpleElement> unfinishedRelationSimpleElements = new ArrayList<>();


    private float minX;
    private float minY;
    private float maxX;
    private float maxY;

    public MapObjects(){

    }

    public ArrayList<ComplexElement> getComplexElements() {
        return complexElements;
    }

    public ArrayList<SimpleElement> getSimpleElements() {
        return simpleElements;
    }

    public void addComplexElement(ComplexElement element){
        complexElements.add(element);
    }

    public void addSimpleElement(SimpleElement element){
        simpleElements.add(element);
        simpleElementIDtoElement.put(element.getID(), element);
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

    public void addPointToUnfinishedSimpleElement(Point element){
        this.unfinishedSimpleElementPoints.add(element);
    }

    public void initializeEmptySimpleElement(long id){
        this.unfinishedSimpleElementID = id;
    }

    public void initializeEmptyComplexElement(long id){
        this.unfinishedRelationID = id;
    }

    public void setPointType(String type){
        this.unfinishedPointType = type;
    }

    public void setShapeType(String type){
        this.unfinishedShapeType = type;
    }

    public void convertSimpleElementToRoad(){
        unfinishedRoad = new Road(unfinishedSimpleElementID, unfinishedSimpleElementPoints);
        this.isRoad = true;
    }

    public void setRoadType(String type){
        convertSimpleElementToRoad();
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
            //create POI element 
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


    public void finishSimpleElement(){
        if(!isRoad){
            ArrayList<float[]> coords = convertPointArrToUseableFloatArr();
            if(this.unfinishedPointType!=null){
                //create POI element and add to trie
            }
            if(this.unfinishedShapeType==null){
                this.simpleElementIDtoElement.put(this.unfinishedSimpleElementID, new SimpleElement(this.unfinishedSimpleElementID, this.unfinishedShapeType, coords.get(0),coords.get(1)));
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
            // add to tree command: new SimpleElement(this.unfinishedSimpleElementID, this.unfinishedShapeType, coords.get(0),coords.get(1));
        }else if (this.completeAddressCount==4){
            //create address element and add to trie
        } else { 
            roads.add(this.unfinishedRoad);
            // add to tree
        }
        isRoad = false;
        unfinishedSimpleElementPoints = new ArrayList<>();
        unfinishedSimpleElementID = 0;
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

    public SimpleElement getSimpleElementByID(long id){
        return this.simpleElementIDtoElement.get(id);
    }

    public void handleSimpleElement(long id, String role){
        SimpleElement element = this.simpleElementIDtoElement.get(id);
        if(element==null) return;
        element.setRole(role);
        unfinishedRelationSimpleElements.add(element);

    }

    public void finishRelation(){
        complexElements.add(new ComplexElement(unfinishedRelationID,this.unfinishedShapeType, unfinishedRelationSimpleElements));
        unfinishedRelationSimpleElements = new ArrayList<>();
        unfinishedRelationID = 0;
        unfinishedShapeType = null;
    }

    private ArrayList<float[]> convertPointArrToUseableFloatArr() {
        ArrayList<Float> x = new ArrayList<>();
        ArrayList<Float> y = new ArrayList<>();
        ArrayList<float[]> result = new ArrayList<>();
        
        for (Point point : unfinishedSimpleElementPoints) {
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
