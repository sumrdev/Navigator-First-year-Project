package marp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import marp.mapelements.ComplexElement;
import marp.mapelements.Road;
import marp.mapelements.SimpleElement;
import marp.mapelements.SinglePointElement;

public class MapObjects {
    private ArrayList<ComplexElement> complexElements = new ArrayList<>();
    private ArrayList<SimpleElement> simpleElements = new ArrayList<>();
    private ArrayList<SinglePointElement> singlePointElements = new ArrayList<>();
    private ArrayList<Road> roads = new ArrayList<>();

    private HashMap<Long, SinglePointElement> pointIDtoPoint = new HashMap<>();
    private HashMap<Long, SimpleElement> simpleElementIDtoElement = new HashMap<>();

    private ArrayList<SinglePointElement> unfinishedWayPoints = new ArrayList<>();

    private String unfinishedWayType;

    private long unfinishedWayID; 
    private long unfinishedRelationID;

    private Road unfinishedRoad;

    private boolean isRoad;

    private ArrayList<SimpleElement> unfinishedRelationWays = new ArrayList<>();


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

    public ArrayList<SinglePointElement> getSinglePointElements() {
        return singlePointElements;
    }

    public void addComplexElement(ComplexElement element){
        complexElements.add(element);
    }

    public void addSimpleElement(SimpleElement element){
        simpleElements.add(element);
        simpleElementIDtoElement.put(element.getID(), element);
    }

    public void addSinglePointElement(SinglePointElement element){
        singlePointElements.add(element);
        pointIDtoPoint.put(element.getID(), element);
    }

    public SinglePointElement getSinglePointElementByID(long id){
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

    public void addPointToUnfinishedWay(SinglePointElement element){
        this.unfinishedWayPoints.add(element);
    }

    public void initializeEmptySimpleElement(long id){
        this.unfinishedWayID = id;
    }

    public void initializeEmptyComplexElement(long id){
        this.unfinishedRelationID = id;
    }

    public void setType(String type){
        this.unfinishedWayType = type;
    }

    public void convertSimpleElementToRoad(){
        unfinishedRoad = new Road(unfinishedWayID, unfinishedWayPoints);
        this.isRoad = true;
    }

    public void setRoadType(String type){
        unfinishedRoad.setType(type);
    }

    public void setRoadOneWay(boolean oneway){
        unfinishedRoad.setOneWay(oneway);
    }

    public void finishWay(){
        if(!isRoad){
            ArrayList<float[]> coords = convertPointArrToUseableFloatArr();
            simpleElements.add(new SimpleElement(this.unfinishedWayID, this.unfinishedWayType,coords.get(0),coords.get(1)));
        } else { 
            roads.add(this.unfinishedRoad);
        }
        unfinishedWayPoints = new ArrayList<>();
        unfinishedWayID = 0;
        unfinishedWayType = null;
    }

    public SimpleElement getSimpleElementByID(long id){
        return this.simpleElementIDtoElement.get(id);
    }

    public void addSimpleElementToUnfinishedRelation(SimpleElement simpleElement) {
        unfinishedRelationWays.add(simpleElement);
    }

    public void finishRelation(){
        complexElements.add(new ComplexElement(unfinishedRelationID,this.unfinishedWayType,unfinishedRelationWays));
        unfinishedRelationID = 0;
        unfinishedRelationWays = new ArrayList<>();
    }

    private ArrayList<float[]> convertPointArrToUseableFloatArr() {
        ArrayList<Float> x = new ArrayList<>();
        ArrayList<Float> y = new ArrayList<>();
        ArrayList<float[]> result = new ArrayList<>();
        float[] xFinal = new float[x.size()];
        float[] yFinal = new float[y.size()];
        result.add(xFinal);
        result.add(yFinal);

        for (SinglePointElement point : unfinishedWayPoints) {
            x.add(point.getX());
            y.add(point.getY());
        }

        for (int i = 0; i > x.size()-1; i++) {
            xFinal[i] = x.get(i);
            yFinal[i] = y.get(i);
        }
        return result;
    }


}
