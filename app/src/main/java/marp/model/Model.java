package marp.model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import javafx.geometry.Point2D;
import marp.datastructures.SimpleTrie;
import marp.mapelements.MapPoint;
import marp.mapelements.PointOfInterest;

import marp.parser.OSMParser;
import marp.utilities.DefaultPath;;

public class Model implements Serializable{
    private MapObjects mapObjects;
    private PointOfInterest selectedPointMarker;
    public boolean isRoadsVisible = true;
    public boolean isLandmarksVisible = true;
    public boolean isAddressVisible = true;
    public boolean isTerrainVisible = true;
    public boolean isBuildingsVisible = true;

    public static Model createModel(URL fileURL) throws URISyntaxException, XMLStreamException,
            FactoryConfigurationError, ClassNotFoundException, IOException {
                File file = Paths.get(fileURL.toURI()).toFile();
        return findLoadType(file.getAbsolutePath());
    }

    public static Model createModel(File file) throws URISyntaxException, XMLStreamException,
            FactoryConfigurationError, ClassNotFoundException, IOException {
        return findLoadType(file.getAbsolutePath());
    }

    public MapObjects getMapObjects() {
        return mapObjects;
    }

    private static Model findLoadType(String filepath)
            throws XMLStreamException, FactoryConfigurationError, ClassNotFoundException, IOException {
        OSMParser osmParser = new OSMParser();
        System.out.println(filepath);
        File file = new File(filepath);
        String filename = file.getName();
        file = null;
        
        String filetype = filepath.split("\\.")[1];
        System.out.println("Filetype: " + filetype + "\n filename: " + filename);

        switch (filetype) {
            case "bin":
                return loadBIN(new FileInputStream(filepath));
            case "zip":
                return loadZIP(filepath, filename);
            case "osm":
                MapObjects mapObjects = osmParser.parseOSM(new FileInputStream(filepath));
                return new Model(mapObjects, filename);
            default:
                throw new IOException("Filetype not supported");
        }
    }

    private static Model loadZIP(String filepath, String filename) throws IOException, XMLStreamException, FactoryConfigurationError {
        ZipInputStream input = new ZipInputStream(new FileInputStream(filepath));
        input.getNextEntry();
        OSMParser osmParser = new OSMParser();
        MapObjects mapObjects = osmParser.parseOSM(input);
        input.close();
        return new Model(mapObjects,filename);
    }

    private Model(MapObjects mapObjects, String filename) throws FileNotFoundException, IOException {
        this.mapObjects = mapObjects;
        save(filename);
    }

    private static Model loadBIN(FileInputStream fileInputStream) throws IOException, ClassNotFoundException {
        try (var bin = new ObjectInputStream(new BufferedInputStream(fileInputStream))) {
            return (Model) bin.readObject();
        }
    }

    private void save(String filename) {
        new Thread(() -> {
            String fn = filename.split("\\.")[0] + ".bin";
            try (var out = new ObjectOutputStream(new FileOutputStream(DefaultPath.getDefaultPath() + fn))) {
                out.writeObject(this);
                System.out.println("Saved to: " + fn);
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

        MapPoint nearestAddress = mapObjects.getAddressTree().getNearest(new float[]{(float) point.getX(), (float) point.getY()}, 5);
        //calculate distance
        selectedElement = nearestAddress;
        //address distance is calculated:
        double currentDistance = Math.sqrt(Math.pow(nearestAddress.getX() - point.getX(), 2) + Math.pow(nearestAddress.getY() - point.getY(), 2));

        MapPoint nearestLandmark = mapObjects.getPOITree().getNearest(new float[]{(float) point.getX(), (float) point.getY()}, 5);
        //calculate distance to nearest POI
        if (nearestLandmark != null) {
            double landmarkDistance = Math.sqrt(Math.pow(nearestLandmark.getX() - point.getX(), 2) + Math.pow(nearestLandmark.getY() - point.getY(), 2));
            if (landmarkDistance < currentDistance) {
                currentDistance = landmarkDistance;
                selectedElement = nearestLandmark;
            }
        }

        MapPoint nearestTrainLandmark = mapObjects.getTrainPOITree().getNearest(new float[]{(float) point.getX(), (float) point.getY()}, 5);
        //calculate distance to nearest train POI
        if (nearestTrainLandmark != null) {
            double trainLandmarkDistance = Math.sqrt(Math.pow(nearestTrainLandmark.getX() - point.getX(), 2) + Math.pow(nearestTrainLandmark.getY() - point.getY(), 2));
            if (trainLandmarkDistance < currentDistance) {
                currentDistance = trainLandmarkDistance;
                selectedElement = nearestTrainLandmark;
            }
        }

        MapPoint nearestBusLandmark = mapObjects.getBusPOITree().getNearest(new float[]{(float) point.getX(), (float) point.getY()}, 5);
        //calculate distance to nearest bus POI
        if (nearestBusLandmark != null) {
            double busLandmarkDistance = Math.sqrt(Math.pow(nearestBusLandmark.getX() - point.getX(), 2) + Math.pow(nearestBusLandmark.getY() - point.getY(), 2));
            if (busLandmarkDistance < currentDistance) {
                selectedElement = nearestBusLandmark;
            }
        }
        //We use a point of interest to represent the currently selected point. We update selected point to a new point with the coordinates of the selected point.
        selectedPointMarker = new PointOfInterest(selectedElement.getName(), selectedElement.getType(), selectedElement.getX()*0.56f, -selectedElement.getY(), false);
        return selectedPointMarker;
    }

    public void setSelectedPointMarker(PointOfInterest newSelectedPoint) {
        selectedPointMarker = newSelectedPoint;
    }

    public PointOfInterest getSelectedPointMarker() {
        return selectedPointMarker;
    }
}

