package marp.model;

import marp.datastructures.Digraph;
import marp.datastructures.RTree;
import marp.datastructures.Trie;
import marp.mapelements.*;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

public class MapObjects implements Serializable{
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
    public final ArrayList<PointOfInterest> favouritesMarkerList = new ArrayList<>();
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
    public ArrayList<PointOfInterest> getFavouritesMarkerList() {
        return favouritesMarkerList;
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

    //#####################################################
    //######## Roads lists and trees ######################
    //#####################################################
    private final ArrayList<Road> roadsList = new ArrayList<>();
    private final ArrayList<Road> motorWaysList = new ArrayList<>();
    private RTree<Road> motorWaysTree;
    private final ArrayList<Road> largeRoadsList = new ArrayList<>();
    private RTree<Road> largeRoadsTree;
    private final ArrayList<Road> mediumRoadsList = new ArrayList<>();
    private RTree<Road> mediumRoadsTree;
    private final ArrayList<Road> smallRoadsList = new ArrayList<>();
    private RTree<Road> smallRoadsTree;
    private final ArrayList<Road> footpathList = new ArrayList<>();
    private RTree<Road> footPathsTree;
    private RTree<RoadNode> roadNodeRTree;
    public RTree<Road> getMotorWaysTree() {
        return motorWaysTree;
    }
    public RTree<Road> getLargeRoadsTree() {
        return largeRoadsTree;
    }
    public RTree<Road> getMediumRoadsTree() { return mediumRoadsTree; }
    public RTree<Road> getSmallRoadsTree() {
        return smallRoadsTree;
    }
    public RTree<Road> getFootPathsTree() {
        return footPathsTree;
    }
    public ArrayList<Road> getRoadsList() {
        return roadsList;
    }
    public ArrayList<Road> getMotorWaysList() {
        return motorWaysList;
    }
    public ArrayList<Road> getLargeRoadsList() {
        return largeRoadsList;
    }
    public ArrayList<Road> getMediumRoadsList() { return mediumRoadsList; }
    public ArrayList<Road> getSmallRoadsList() {
        return smallRoadsList;
    }
    public ArrayList<Road> getFootPathsList() {
        return footpathList;
    }
    //#####################################################
    //######## Coastline lists and trees ##################
    //#####################################################
    public ArrayList<SimpleShape> coastLineAreasList = new ArrayList<>();
    public ArrayList<SimpleShape> getCoastLineAreasList() {
        return coastLineAreasList;
    }
    private RTree<SimpleShape> coastLineAreasTree;
    public RTree<SimpleShape> getCoastLinesAreaTree() {
        return coastLineAreasTree;
    }
    public RTree<RoadNode> getRoadNodeRTree(){
        return roadNodeRTree;
    }

    public void buildTrees() {
        Thread address = new Thread(() -> {
            Time startTime = new Time(System.currentTimeMillis());
            addressTree = new RTree<>(addressList);
            Time endTime = new Time(System.currentTimeMillis());
            System.out.println("Time to build address tree: " + (endTime.getTime() - startTime.getTime()) + "ms");
        });

        Thread poi = new Thread(() -> {
            Time startTime = new Time(System.currentTimeMillis());
            trainPOITree = new RTree<>(trainPOIList);
            busPOITree = new RTree<>(busPOIList);
            POITree = new RTree<>(POIList);
            Time endTime = new Time(System.currentTimeMillis());
            System.out.println("Time to build POI trees: " + (endTime.getTime() - startTime.getTime()) + "ms");
        });

        Thread placeNames = new Thread(() -> {
            Time startTime = new Time(System.currentTimeMillis());
            quiteSmallPlaceNameTree = new RTree<>(quiteSmallPlaceNameList);
            smallPlaceNameTree = new RTree<>(smallPlaceNameList);
            mediumPlaceNameTree = new RTree<>(mediumPlaceNameList);
            mediumLargePlaceNameTree = new RTree<>(mediumLargePlaceNameList);
            largeNameTree = new RTree<>(largePlaceNameList);
            quiteLargeNameTree = new RTree<>(quiteLargePlaceNameList);
            Time endTime = new Time(System.currentTimeMillis());
            System.out.println("Time to build place name trees: " + (endTime.getTime() - startTime.getTime()) + "ms");
        });
        Thread roadsAndCoastline = new Thread(() -> {
            Time startTime = new Time(System.currentTimeMillis());
            coastLineAreasTree = new RTree<>(coastLineAreasList);
            motorWaysTree = new RTree<>(motorWaysList);
            largeRoadsTree = new RTree<>(largeRoadsList);
            mediumRoadsTree = new RTree<>(mediumRoadsList);
            smallRoadsTree = new RTree<>(smallRoadsList);
            footPathsTree = new RTree<>(footpathList);
            Time endTime = new Time(System.currentTimeMillis());
            System.out.println("Time to build roads and coastline trees: " + (endTime.getTime() - startTime.getTime()) + "ms");
        });
        Thread buildings = new Thread(() -> {
            Time startTime = new Time(System.currentTimeMillis());
            buildingsTree = new RTree<>(buildingsList);
            Time endTime = new Time(System.currentTimeMillis());
            System.out.println("Time to build buildings tree: " + (endTime.getTime() - startTime.getTime()) + "ms");
        });

        Thread terrainAndWater = new Thread(() -> {
            Time startTime = new Time(System.currentTimeMillis());
            waterAreasTree = new RTree<>(waterAreasList);
            terrainAreasTree = new RTree<>(terrainAreasList);
            Time endTime = new Time(System.currentTimeMillis());
            System.out.println("Time to build terrain and water trees: " + (endTime.getTime() - startTime.getTime()) + "ms");
        });

        poi.start();
        placeNames.start();
        roadsAndCoastline.start();
        buildings.start();
        terrainAndWater.start();
        address.start();

        try {
            poi.join();
            placeNames.join();
            roadsAndCoastline.join();
            buildings.join();
            terrainAndWater.join();
            address.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void buildDigraph(HashMap<Long, RoadNode> roadNodes){
        roadNodeRTree = new RTree<>(new ArrayList<>(roadNodes.values()));
        digraph = new Digraph(roadsList, roadNodes);
    }

    private Trie trie = new Trie();
    public Trie getTrie(){
        return trie;
    }

    private Digraph digraph;
    public Digraph getDigraph(){
        return digraph;
    }
    public void clearRoute(){
        digraph.clearNavigation();
    }
}
