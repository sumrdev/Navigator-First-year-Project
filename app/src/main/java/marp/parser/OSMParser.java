package marp.parser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import marp.mapelements.ComplexShape;
import marp.mapelements.Point;
import marp.mapelements.SimpleShape;
import marp.mapelements.details.FontSize;
import marp.mapelements.details.PointType;
import marp.mapelements.details.RoadType;
import marp.mapelements.details.ShapeType;
import marp.model.MapObjectInParsing;
import marp.model.MapObjects;

public class OSMParser {
    public MapObjects parseOSM(InputStream inputStream) throws XMLStreamException, FactoryConfigurationError {
        Time start = new Time(System.currentTimeMillis());
        Time result;
        XMLStreamReader xmlsr = XMLInputFactory.newInstance()
                .createXMLStreamReader(new BufferedInputStream(inputStream));
        MapObjects mapObjects = new MapObjects();
        MapObjectInParsing mapObjectInParsing = new MapObjectInParsing(mapObjects);
        while (xmlsr.hasNext()) {
            int xmltag = xmlsr.next();
            switch (xmltag) {
                case XMLStreamConstants.START_ELEMENT:
                    String startTag = xmlsr.getLocalName();
                    switch (startTag) {
                        case "bounds":
                            mapObjects.setMinX(Float.parseFloat(xmlsr.getAttributeValue(null, "minlon")));
                            mapObjects.setMinY(Float.parseFloat(xmlsr.getAttributeValue(null, "minlat")));
                            mapObjects.setMaxX(Float.parseFloat(xmlsr.getAttributeValue(null, "maxlon")));
                            mapObjects.setMaxY(Float.parseFloat(xmlsr.getAttributeValue(null, "maxlat")));
                            break;
                        case "node":
                            long id = Long.parseLong(xmlsr.getAttributeValue(null, "id"));
                            float x = Float.parseFloat(xmlsr.getAttributeValue(null, "lon"));
                            float y = Float.parseFloat(xmlsr.getAttributeValue(null, "lat"));
                            Point point = new Point(id, x, y);
                            mapObjectInParsing.addPointToHashMap(point);
                            break;
                        case "nd":
                            long nodeID = Long.parseLong(xmlsr.getAttributeValue(null, "ref"));
                            mapObjectInParsing.addPointToUnfinishedSimpleShape(mapObjectInParsing.getPointByID(nodeID));
                            break;
                        case "way":
                            mapObjectInParsing
                                    .initializeEmptySimpleShape(Long.parseLong(xmlsr.getAttributeValue(null, "id")));
                            break;
                        case "member":
                            long memberID = Long.parseLong(xmlsr.getAttributeValue(null, "ref"));
                            String role = xmlsr.getAttributeValue(null, "role");
                            switch (role) {
                                case "inner":
                                case "outer":
                                    mapObjectInParsing.handleSimpleShape(memberID, role);
                                default:
                                    break;
                            }
                            break;
                        case "relation":
                            mapObjectInParsing
                                    .initializeEmptyComplexShape(Long.parseLong(xmlsr.getAttributeValue(null, "id")));
                            break;
                        case "tag":
                            String key = xmlsr.getAttributeValue(null, "k");
                            String value = xmlsr.getAttributeValue(null, "v");
                            switch (key) {
                                case "highway":
                                    switch (value) {
                                        case "motorway":
                                            mapObjectInParsing.setRoadType(RoadType.MOTORWAY);
                                            break;
                                        case "primary":
                                        case "trunk":
                                            mapObjectInParsing.setRoadType(RoadType.PRIMARY);
                                            break;
                                        case "secondary":
                                        case "tertiary":
                                        case "tertiary_link":
                                            mapObjectInParsing.setRoadType(RoadType.TERTIARY);
                                            break;
                                        case "unclassified":
                                        case "residential":
                                            mapObjectInParsing.setRoadType(RoadType.RESIDENTIAL);
                                            break;
                                        case "pedestrian":
                                            mapObjectInParsing.setRoadType(RoadType.PEDESTRIAN);
                                            break;
                                        case "footway":
                                        case "steps":
                                        case "escalator":
                                        case "proposed":
                                            mapObjectInParsing.setRoadType(RoadType.DO_NOT_SHOW);
                                            break;
                                        case "path":
                                        case "cycleway":
                                        case "bridleway":
                                            mapObjectInParsing.setRoadType(RoadType.PATH);
                                            break;
                                        case "service":
                                            mapObjectInParsing.setRoadType(RoadType.SERVICE);
                                            break;
                                        default:
                                            // in cases where the road hasn't been classified, set the type to
                                            // residential to avoid overestimating size + speed
                                            mapObjectInParsing.setRoadType(RoadType.RESIDENTIAL);
                                            break;
                                    }
                                    break;
                                case "oneway":
                                    mapObjectInParsing.setRoadOneWay(value.equals("yes"));
                                    break;
                                case "junction":
                                    if(value.equals("roundabout")) {
                                        mapObjectInParsing.setRoadRoundabout(true);
                                    }
                                    break;
                                case "maxspeed":
                                    try {
                                        int speed = Integer.parseInt(value);
                                        mapObjectInParsing.setSpeed(speed);
                                    } catch (NumberFormatException e) {
                                        // value could not be parsed to int, do nothing
                                    }
                                    break;
                                case "building":
                                    mapObjectInParsing.setShapeType(ShapeType.BUILDING);
                                    break;
                                case "railway":
                                    switch (value) {
                                        case "rail":
                                        case "subway":
                                            mapObjectInParsing.setShapeType(ShapeType.RAILWAY);
                                            break;
                                    }
                                    break;
                                case "tunnel":
                                    mapObjectInParsing.setIsTunnel(value.equals("yes"));
                                    break;
                                case "natural":
                                    switch (value) {
                                        case "wood":
                                        case "forest":
                                        case "scrub":
                                            mapObjectInParsing.setShapeType(ShapeType.FOREST);
                                            break;
                                        case "grass":
                                        case "meadow":
                                        case "grassland":
                                        case "moor":
                                        case "wetland":
                                        case "heath":
                                            mapObjectInParsing.setShapeType(ShapeType.GRASS);
                                            break;
                                        case "water":
                                        case "fountain":
                                            mapObjectInParsing.setShapeType(ShapeType.WATER);
                                            break;
                                        case "coastline":
                                            mapObjectInParsing.setShapeType(ShapeType.COASTLINE);
                                            break;
                                        default:
                                            break;
                                    }
                                    break;
                                case "water":
                                case "harbour":
                                    mapObjectInParsing.setShapeType(ShapeType.WATER);
                                    break;
                                case "waterway":
                                    mapObjectInParsing.setShapeType(ShapeType.WATERWAY);
                                    break;
                                case "landuse":
                                    switch (value) {
                                        case "grass":
                                        case "meadow":
                                        case "garden":
                                        case "playground":
                                        case "recreation_ground":
                                        case "cemetery":
                                        case "wetland":
                                        case "heath":
                                            mapObjectInParsing.setShapeType(ShapeType.GRASS);
                                            break;
                                        case "forest":
                                        case "shrub":
                                            mapObjectInParsing.setShapeType(ShapeType.FOREST);
                                            break;
                                        case "basin":
                                            mapObjectInParsing.setShapeType(ShapeType.WATER);
                                            break;
                                        case "construction":
                                        case "industrial":
                                        case "residential":
                                        case "landfill":
                                            mapObjectInParsing.setShapeType(ShapeType.CEMENT);
                                            break;
                                        case "commercial":
                                        case "retail":
                                            mapObjectInParsing.setShapeType(ShapeType.COMMERCIAL_GROUND);
                                            break;
                                        case "allotments":
                                        case "farmland":
                                        case "farmyard":
                                        case "orchard":
                                        case "vineyard":
                                            mapObjectInParsing.setShapeType(ShapeType.FARMLAND);
                                            break;
                                        default:
                                            break;
                                    }
                                    break;
                                case "addr:city":
                                    mapObjectInParsing.setCity(value);
                                    break;
                                case "addr:housenumber":
                                    mapObjectInParsing.setHouseNumber(value);
                                    break;
                                case "addr:postcode":
                                    mapObjectInParsing.setPostcode(value);
                                    break;
                                case "addr:street":
                                    mapObjectInParsing.setStreet(value);
                                    break;
                                case "shop":
                                    mapObjectInParsing.setPointType(PointType.SHOP);
                                    break;
                                case "bus":
                                    mapObjectInParsing.setPointType(PointType.BUS_STOP);
                                    break;
                                case "station":
                                    switch (value) {
                                        case "subway":
                                            mapObjectInParsing.setPointType(PointType.METRO_STATION);
                                            break;
                                        default:
                                            mapObjectInParsing.setPointType(PointType.TRAIN_STATION);
                                            break;
                                    }
                                    break;
                                case "amenity":
                                    switch (value) {
                                        case "place_of_worship":
                                            mapObjectInParsing.setPointType(PointType.PLACE_OF_WORSHIP);
                                            break;
                                        case "restaurant":
                                        case "fast_food":
                                        case "food_court":
                                            mapObjectInParsing.setPointType(PointType.RESTAURANT);
                                            break;
                                        case "bar":
                                        case "pub":
                                            mapObjectInParsing.setPointType(PointType.BAR);
                                            break;
                                        case "cafe":
                                            mapObjectInParsing.setPointType(PointType.CAFE);
                                            break;
                                        case "bank":
                                            mapObjectInParsing.setPointType(PointType.BANK);
                                            break;
                                        case "clinic":
                                        case "dentist":
                                        case "doctors":
                                        case "pharmacy":
                                        case "hospital":
                                            mapObjectInParsing.setPointType(PointType.HEALTHCARE);
                                            break;
                                        case "cinema":
                                        case "theatre":
                                            mapObjectInParsing.setPointType(PointType.THEATRE);
                                            break;
                                        case "toilets":
                                            mapObjectInParsing.setPointType(PointType.TOILETS);
                                            break;
                                        default:
                                            break;
                                    }
                                    break;
                                case "name":
                                    mapObjectInParsing.setName(value);
                                    break;
                                case "place":
                                    switch (value) {
                                        case "country":
                                            mapObjectInParsing.setFontSize(FontSize.QUITE_LARGE);
                                        case "region":
                                        case "sea":
                                            mapObjectInParsing.setFontSize(FontSize.LARGE);
                                            break;
                                        case "city":
                                            mapObjectInParsing.setFontSize(FontSize.MEDIUM_LARGE);
                                            break;
                                        case "town":
                                        case "borough":
                                        case "suburb":
                                        case "island":
                                            mapObjectInParsing.setFontSize(FontSize.MEDIUM);
                                            break;
                                        case "village":
                                        case "hamlet":
                                        case "smalltown":
                                        case "quarter":
                                        case "square":
                                            mapObjectInParsing.setFontSize(FontSize.SMALL);
                                            break;
                                        default:
                                            break;
                                    }
                                    break;
                            }
                        default:
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    String endTag = xmlsr.getLocalName();
                    switch (endTag) {
                        case "node":
                            mapObjectInParsing.finishPoint();
                            break;
                        case "way":
                            mapObjectInParsing.finishSimpleShape();
                            break;
                        case "relation":
                            mapObjectInParsing.finishRelation();
                            break;
                    }
                    break;
            }
        }
        result = new Time(System.currentTimeMillis());
        System.out.println("OSM Parsed in: " + (result.getTime() - start.getTime()) / 1000 + "s");

        mapObjects.coastLineAreasList = ComplexShape.orderAndFlipWays(mapObjectInParsing.getCoastlineSegments());
        Thread trees = new Thread(() -> {
            Time startTrees = new Time(System.currentTimeMillis());
            mapObjects.buildTrees();
            Time resultTrees = new Time(System.currentTimeMillis());
            System.out.println("All trees built in: " + (resultTrees.getTime() - startTrees.getTime()) / 1000 + "s");
        });
        Thread graph = new Thread(() -> {
            mapObjects.buildDigraph(mapObjectInParsing.getRoadNodeIDtoRoadNode());
        });

        trees.start();
        graph.start();

        try {
            trees.join();
            graph.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.gc();
        System.out.println("done");
        return mapObjects;
    }
}
