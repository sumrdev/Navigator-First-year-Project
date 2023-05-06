package marp.model;

import com.google.common.base.MoreObjects;
import marp.datastructures.RTree;
import marp.mapelements.*;

import java.util.ArrayList;
import java.util.Collection;

public class MapObjects {
    //#####################################################
    //######## Bounds #####################################
    //#####################################################
    private float minX;
    private float minY;
    private float maxX;
    private float maxY;

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

    public float getMinX() {
        return minX;
    }

    public float getMinY() {
        return minY;
    }

    public float getMaxX() {
        return maxX;
    }

    public float getMaxY() {
        return maxY;
    }

    //#####################################################
    //######## Address list and tree ######################
    //#####################################################
    private final ArrayList<Address> addressList = new ArrayList<>();
    private RTree<Address> addressTree;
    public RTree<Address> getAddressTree() {
        return addressTree;
    }
    public ArrayList<Address> getAddressList() {
        return addressList;
    }
    //#####################################################
    //######## POI lists and trees ########################
    //#####################################################
    private final ArrayList<PointOfInterest> trainPOIList = new ArrayList<>();
    private RTree<PointOfInterest> trainPOITree;
    private final ArrayList<PointOfInterest> busPOIList = new ArrayList<>();
    private RTree<PointOfInterest> busPOITree;
    private final ArrayList<PointOfInterest> POIList = new ArrayList<>();
    private RTree<PointOfInterest> POITree;
    private final ArrayList<PointOfInterest> customPOIList = new ArrayList<>();
    public ArrayList<PointOfInterest> getTrainPOIList() {
        return trainPOIList;
    }
    public ArrayList<PointOfInterest> getBusPOIList() {
        return busPOIList;
    }
    public ArrayList<PointOfInterest> getPOIList() {
        return POIList;
    }
    public RTree<PointOfInterest> getTrainPOITree() {
        return trainPOITree;
    }
    public RTree<PointOfInterest> getBusPOITree() {
        return busPOITree;
    }
    public RTree<PointOfInterest> getPOITree() {
        return POITree;
    }
    public ArrayList<PointOfInterest> getCustomPOIList() {
        return customPOIList;
    }

    //#####################################################
    //######## Place name lists and trees #################
    //#####################################################
    private final ArrayList<PlaceName> quiteSmallPlaceNameList = new ArrayList<>();
    private RTree<PlaceName> quiteSmallPlaceNameTree;
    private final ArrayList<PlaceName> smallPlaceNameList = new ArrayList<>();
    private RTree<PlaceName> smallPlaceNameTree;
    private final ArrayList<PlaceName> mediumPlaceNameList = new ArrayList<>();
    private RTree<PlaceName> mediumPlaceNameTree;
    private final ArrayList<PlaceName> mediumLargePlaceNameList = new ArrayList<>();
    private RTree<PlaceName> mediumLargePlaceNameTree;
    private final ArrayList<PlaceName> largePlaceNameList = new ArrayList<>();
    private RTree<PlaceName> largeNameTree;
    private final ArrayList<PlaceName> quiteLargePlaceNameList = new ArrayList<>();
    private RTree<PlaceName> quiteLargeNameTree;
    public RTree<PlaceName> getQuiteSmallPlaceNameTree() {
        return quiteSmallPlaceNameTree;
    }
    public RTree<PlaceName> getSmallPlaceNameTree() {
        return smallPlaceNameTree;
    }
    public RTree<PlaceName> getMediumPlaceNameTree() {
        return mediumPlaceNameTree;
    }
    public RTree<PlaceName> getMediumLargePlaceNameTree() {
        return mediumLargePlaceNameTree;
    }
    public RTree<PlaceName> getLargeNameTree() {
        return largeNameTree;
    }
    public RTree<PlaceName> getQuiteLargeNameTree() {
        return quiteLargeNameTree;
    }
    public ArrayList<PlaceName> getQuiteSmallPlaceNamesList() {
        return quiteSmallPlaceNameList;
    }

    public ArrayList<PlaceName> getSmallPlaceNamesList() {
        return smallPlaceNameList;
    }
    public ArrayList<PlaceName> getMediumPlaceNamesList() {
        return mediumPlaceNameList;
    }
    public ArrayList<PlaceName> getMediumLargePlaceNamesList() {
        return mediumLargePlaceNameList;
    }
    public ArrayList<PlaceName> getLargePlaceNamesList() {
        return largePlaceNameList;
    }
    public ArrayList<PlaceName> getQuiteLargePlaceNamesList() {
        return quiteLargePlaceNameList;
    }


    //#####################################################
    //######## Areas and buildings lists and trees ########
    //#####################################################
    private final ArrayList<Element> buildingsList = new ArrayList<>();
    private RTree<Element> buildingsTree;
    private final ArrayList<Element> waterAreasList = new ArrayList<>();
    private RTree<Element> waterAreasTree;
    private final ArrayList<Element> terrainAreasList = new ArrayList<>();
    private RTree<Element> terrainAreasTree;
    private final ArrayList<Element> coastLineAreasList = new ArrayList<>();
    private RTree<Element> coastLineAreasTree;
    public RTree<Element> getCoastLinesAreaTree() {
        return coastLineAreasTree;
    }
    public RTree<Element> getBuildingsTree() {
        return buildingsTree;
    }
    public RTree<Element> getWaterAreasTree() {
        return waterAreasTree;
    }
    public RTree<Element> getTerrainAreasTree() {
        return terrainAreasTree;
    }
    public ArrayList<Element> getBuildingsList() {
        return buildingsList;
    }
    public ArrayList<Element> getWaterAreasList() {
        return waterAreasList;
    }
    public ArrayList<Element> getTerrainAreasList() {
        return terrainAreasList;
    }
    public ArrayList<Element> getCoastLineAreasList() {
        return coastLineAreasList;
    }
    //#####################################################
    //######## Roads lists and trees ######################
    //#####################################################
    private final ArrayList<Road> motorWaysList = new ArrayList<>();
    private RTree<Road> motorWaysTree;
    private final ArrayList<Road> largeRoadsList = new ArrayList<>();
    private RTree<Road> largeRoadsTree;
    private final ArrayList<Road> smallRoadsList = new ArrayList<>();
    private RTree<Road> smallRoadsTree;
    private final ArrayList<Road> footpathList = new ArrayList<>();
    private RTree<Road> footPathsTree;

    public RTree<Road> getMotorWaysTree() {
        return motorWaysTree;
    }
    public RTree<Road> getLargeRoadsTree() {
        return largeRoadsTree;
    }
    public RTree<Road> getSmallRoadsTree() {
        return smallRoadsTree;
    }
    public RTree<Road> getFootPathsTree() {
        return footPathsTree;
    }
    public ArrayList<Road> getMotorWaysList() {
        return motorWaysList;
    }
    public ArrayList<Road> getLargeRoadsList() {
        return largeRoadsList;
    }
    public ArrayList<Road> getSmallRoadsList() {
        return smallRoadsList;
    }
    public ArrayList<Road> getFootPathsList() {
        return footpathList;
    }
    public void buildTrees() {
        addressTree = new RTree<>(addressList);
        trainPOITree = new RTree<>(trainPOIList);
        busPOITree = new RTree<>(busPOIList);
        POITree = new RTree<>(POIList);
        quiteSmallPlaceNameTree = new RTree<>(quiteSmallPlaceNameList);
        smallPlaceNameTree = new RTree<>(smallPlaceNameList);
        mediumPlaceNameTree = new RTree<>(mediumPlaceNameList);
        mediumLargePlaceNameTree = new RTree<>(mediumLargePlaceNameList);
        largeNameTree = new RTree<>(largePlaceNameList);
        quiteLargeNameTree = new RTree<>(quiteLargePlaceNameList);
        buildingsTree = new RTree<>(buildingsList);
        waterAreasTree = new RTree<>(waterAreasList);
        terrainAreasTree = new RTree<>(terrainAreasList);
        coastLineAreasTree = new RTree<>(coastLineAreasList);
    }
}
