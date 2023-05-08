package marp.model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import javafx.geometry.Point2D;
import marp.datastructures.SimpleTrie;
import marp.mapelements.*;

import marp.parser.OSMParser;

public class Model implements Serializable{
    private MapObjects mapObjects;
    private MapPoint selectedPont;
    private PointOfInterest selectedPointMarker;
    private PointOfInterest startLocationMarker;
    private PointOfInterest endLocationMarker;
    public int transportMode = 0;
    public boolean isRoadsVisible = true;
    public boolean isLandmarksVisible = true;
    public boolean isAddressVisible = true;
    public boolean isTerrainVisible = true;
    public boolean isBuildingsVisible = true;

    private Model(MapObjects mapObjects, String filename) throws FileNotFoundException, IOException {
        this.mapObjects = mapObjects;
        save(filename);
    }
    public static Model createModel(URL fileURL) throws URISyntaxException, XMLStreamException,
            FactoryConfigurationError, ClassNotFoundException, IOException {
                File file = Paths.get(fileURL.toURI()).toFile();
        return findLoadType(new FileInputStream(file), file.getName());
    }

    public static Model createModel(InputStream inputStream, String filename) throws URISyntaxException, XMLStreamException,
            FactoryConfigurationError, ClassNotFoundException, IOException {
        return findLoadType(inputStream, filename);
    }

    public static Model createModel(File file) throws URISyntaxException, XMLStreamException,
            FactoryConfigurationError, ClassNotFoundException, IOException {
        return findLoadType(new FileInputStream(file), file.getName());
    }

    public MapObjects getMapObjects() {
        return mapObjects;
    }

    private static Model findLoadType(InputStream inputStream, String filename)
            throws XMLStreamException, FactoryConfigurationError, ClassNotFoundException, IOException {
        OSMParser osmParser = new OSMParser();
        String filetype = filename.split("\\.")[1];
        System.out.println("Filetype: " + filetype + "\nFilename: " + filename);
        switch (filetype) {
            case "bin":
                return loadBIN(inputStream);
            case "zip":
                return loadZIP(inputStream, filename);
            case "osm":
                MapObjects mapObjects = osmParser.parseOSM(inputStream);
                return new Model(mapObjects, filename);
            default:
                throw new IOException("Filetype not supported");
        }
    }

    private static Model loadZIP(InputStream inputStream, String filename) throws IOException, XMLStreamException, FactoryConfigurationError {
        ZipInputStream input = new ZipInputStream(inputStream);
        input.getNextEntry();
        OSMParser osmParser = new OSMParser();
        MapObjects mapObjects = osmParser.parseOSM(input);
        input.close();
        return new Model(mapObjects,filename);
    }


    private static Model loadBIN(InputStream inputStream) throws IOException, ClassNotFoundException {
        System.out.println(inputStream);
        try (var bin = new ObjectInputStream(new BufferedInputStream(inputStream))) {
            System.gc();
            return (Model) bin.readObject();
        }
    }

    private void save(String filename) {
        new Thread(() -> {
            Time time = new Time(System.currentTimeMillis());
            String fn = filename.split("\\.")[0] + ".bin";
            try (var out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("data/maps/"+fn)))) {
                out.writeObject(this);
                System.out.println("Saved to: " + fn);
                System.out.println("Saved in: " + (new Time(System.currentTimeMillis()).getTime() - time.getTime())/1000 + "s");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    public SimpleTrie getSuggestionTrie() {
        //return suggestionTrie;
        //TODO: Fix
        return mapObjects.getTrie();
    }

    public List<String> getFileList() {
        //return getFiles();
        //TODO: Should getFiles maybe be in model? If it's not, is it ok to call utilities from view?
        return new ArrayList<String>();
    }

    public MapPoint getNearestPointForMapSelection(Point2D point) {
        //We look for distance to mouse among addresses, then landmarks and update the selected element if we find a shorter distance.
        MapPoint selectedElement;

        MapPoint nearestAddress = mapObjects.getAddressTree().getNearest(new float[]{(float) point.getX(), (float) point.getY()});
        //calculate distance
        selectedElement = nearestAddress;
        //address distance is calculated:
        double currentDistance = Math.sqrt(Math.pow(nearestAddress.getX() - point.getX(), 2) + Math.pow(nearestAddress.getY() - point.getY(), 2));

        MapPoint nearestLandmark = mapObjects.getPOITree().getNearest(new float[]{(float) point.getX(), (float) point.getY()});
        //calculate distance to nearest POI
        if (nearestLandmark != null) {
            double landmarkDistance = Math.sqrt(Math.pow(nearestLandmark.getX() - point.getX(), 2) + Math.pow(nearestLandmark.getY() - point.getY(), 2));
            if (landmarkDistance < currentDistance) {
                currentDistance = landmarkDistance;
                selectedElement = nearestLandmark;
            }
        }

        MapPoint nearestTrainLandmark = mapObjects.getTrainPOITree().getNearest(new float[]{(float) point.getX(), (float) point.getY()});
        //calculate distance to nearest train POI
        if (nearestTrainLandmark != null) {
            double trainLandmarkDistance = Math.sqrt(Math.pow(nearestTrainLandmark.getX() - point.getX(), 2) + Math.pow(nearestTrainLandmark.getY() - point.getY(), 2));
            if (trainLandmarkDistance < currentDistance) {
                currentDistance = trainLandmarkDistance;
                selectedElement = nearestTrainLandmark;
            }
        }

        MapPoint nearestBusLandmark = mapObjects.getBusPOITree().getNearest(new float[]{(float) point.getX(), (float) point.getY()});
        //calculate distance to nearest bus POI
        if (nearestBusLandmark != null) {
            double busLandmarkDistance = Math.sqrt(Math.pow(nearestBusLandmark.getX() - point.getX(), 2) + Math.pow(nearestBusLandmark.getY() - point.getY(), 2));
            if (busLandmarkDistance < currentDistance) {
                selectedElement = nearestBusLandmark;
            }
        }

        //calculate distance to nearest custom point of interest, by iterating through the list of all of them. Inefficient but necessary, as they are not part of a tree.

        for (PointOfInterest poi : mapObjects.getCustomPOIList()){
            double customPOIDistance = Math.sqrt(Math.pow(poi.getX() - point.getX(), 2) + Math.pow(poi.getY() - point.getY(), 2));
            if (customPOIDistance < currentDistance) {
                selectedElement = poi;
            }
        }

        //We use a point of interest to represent the currently selected point. We update selected point to a new point with the coordinates of the selected point.
        selectedPointMarker = new PointOfInterest(selectedElement.getName(), selectedElement.getType(), selectedElement.getX()*0.56f, -selectedElement.getY(), false);
        return selectedPointMarker;
    }
    public String getNearestRoadNameForMapSelection(Point2D point) {
        // We look for distance to mouse among roads and update the selected road if we find a shorter distance.
        String selectedRoadName = null;
        double currentDistance = Double.MAX_VALUE;

        Road nearestSmallRoad = mapObjects.getSmallRoadsTree().getNearest(new float[]{(float) point.getX(), (float) point.getY()});
        // calculate distance to nearest small road
        if (nearestSmallRoad != null) {
            for (RoadNode roadNode : nearestSmallRoad.getNodes()) {
                double smallRoadDistance = Math.sqrt(Math.pow(roadNode.getX() - point.getX(), 2) + Math.pow(roadNode.getY() - point.getY(), 2));
                if (smallRoadDistance < currentDistance) {
                    currentDistance = smallRoadDistance;
                    selectedRoadName = nearestSmallRoad.getName();
                }
            }
        }
        Road nearestLargeRoad = mapObjects.getLargeRoadsTree().getNearest(new float[]{(float) point.getX(), (float) point.getY()});
        // calculate distance to nearest large road
        if (nearestLargeRoad != null) {
            for (RoadNode roadNode : nearestLargeRoad.getNodes()) {
                double largeRoadDistance = Math.sqrt(Math.pow(roadNode.getX() - point.getX(), 2) + Math.pow(roadNode.getY() - point.getY(), 2));
                if (largeRoadDistance < currentDistance) {
                    currentDistance = largeRoadDistance;
                    selectedRoadName = nearestLargeRoad.getName();
                }
            }
        }
        Road nearestMotorWay = mapObjects.getMotorWaysTree().getNearest(new float[]{(float) point.getX(), (float) point.getY()});
        // calculate distance to nearest motorway
        if (nearestMotorWay != null) {
            for (RoadNode roadNode : nearestMotorWay.getNodes()) {
                double motorWayDistance = Math.sqrt(Math.pow(roadNode.getX() - point.getX(), 2) + Math.pow(roadNode.getY() - point.getY(), 2));
                if (motorWayDistance < currentDistance) {
                    currentDistance = motorWayDistance;
                    selectedRoadName = nearestMotorWay.getName();
                }
            }
        }

        Road nearestFootPath = mapObjects.getFootPathsTree().getNearest(new float[]{(float) point.getX(), (float) point.getY()});
        // calculate distance to nearest footpath
        if (nearestFootPath != null) {
            for (RoadNode roadNode : nearestFootPath.getNodes()) {
                double footPathDistance = Math.sqrt(Math.pow(roadNode.getX() - point.getX(), 2) + Math.pow(roadNode.getY() - point.getY(), 2));
                if (footPathDistance < currentDistance) {
                    currentDistance = footPathDistance;
                    selectedRoadName = nearestFootPath.getName();
                }
            }
        }

        return selectedRoadName;
    }
    public void setSelectedPointMarker(PointOfInterest newSelectedPoint) {
        selectedPointMarker = newSelectedPoint;
    }

    public PointOfInterest getSelectedPointMarker() {
        return selectedPointMarker;
    }
    public PointOfInterest getStartLocationMarker() {
        return startLocationMarker;
    }
    public PointOfInterest getEndLocationMarker() {
        return endLocationMarker;
    }

    public void setStartLocationMarker(PointOfInterest newStartLocationMarker) {
        startLocationMarker = newStartLocationMarker;
    }
    public void setEndLocationMarker(PointOfInterest newEndLocationMarker) {
        endLocationMarker = newEndLocationMarker;
    }

    public int getTransportMode() {
        return transportMode;
    }
    public void setSelectedPoint(MapPoint mapPoint) {
        selectedPont = mapPoint;
    }
    public MapPoint getSelectedPont() {
        return selectedPont;
    }
}

