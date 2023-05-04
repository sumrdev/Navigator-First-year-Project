package marp.parser;

import java.io.InputStream;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import marp.mapelements.SimpleElement;
import marp.mapelements.Point;
import marp.model.MapObjects;

public class OSMParser {
    public MapObjects parseOSM(InputStream inputStream) throws XMLStreamException, FactoryConfigurationError{
        XMLStreamReader xmlsr = XMLInputFactory.newInstance().createXMLStreamReader(inputStream);
        MapObjects mapObjects = new MapObjects();
        
        while(xmlsr.hasNext()){
            int xmltag = xmlsr.next();
            switch (xmltag) {
                case XMLStreamConstants.START_ELEMENT:
                    String startTag = xmlsr.getLocalName();
                    switch (startTag) {
                        case "bounds":
                            mapObjects.setMinX(Float.parseFloat(xmlsr.getAttributeValue(null, "minlon")));
                            mapObjects.setMinY(Float.parseFloat(xmlsr.getAttributeValue(null, "minlat")));
                            mapObjects.setMaxY(Float.parseFloat(xmlsr.getAttributeValue(null, "maxlon")));
                            mapObjects.setMaxY(Float.parseFloat(xmlsr.getAttributeValue(null, "maxlat")));
                            break;
                        case "node":
                            long id = Long.parseLong(xmlsr.getAttributeValue(null, "id"));
                            float x = Float.parseFloat(xmlsr.getAttributeValue(null, "lon"));
                            float y = Float.parseFloat(xmlsr.getAttributeValue(null, "lat"));
                            Point point = new Point(id, x, y);
                            mapObjects.addPointToHashMap(point);
                            break;
                        case "nd":
                            long nodeID = Long.parseLong(xmlsr.getAttributeValue(null, "ref"));
                            mapObjects.addPointToUnfinishedSimpleElement(mapObjects.getPointByID(nodeID));
                            break;
                        case "way":
                            mapObjects.initializeEmptySimpleElement(Long.parseLong(xmlsr.getAttributeValue(null, "id")));
                            break;
                        case "member":
                            long memberID = Long.parseLong(xmlsr.getAttributeValue(null, "ref"));
                            String role = xmlsr.getAttributeValue(null, "role");
                            switch (role) {
                                case "inner":
                                case "outer":
                                    mapObjects.handleSimpleElement(memberID, role);
                                default:
                                    break;
                            }
                            break;
                        case "relation":
                            mapObjects.initializeEmptyComplexElement(Long.parseLong(xmlsr.getAttributeValue(null, "id")));
                            break;
                        case "tag":
                        String key = xmlsr.getAttributeValue(null, "k");
                        String value = xmlsr.getAttributeValue(null, "v");
                        switch (key) {
                            case "highway":
                                switch (value) {
                                    case "motorway":
                                        mapObjects.setRoadType("motorway");
                                        break;
                                    case "primary":
                                    case "trunk":
                                        mapObjects.setRoadType("primary");
                                        break;
                                    case "secondary":
                                    case "tertiary":
                                    case "tertiary_link":
                                        mapObjects.setRoadType("tertiary");
                                        break;
                                    case "unclassified":
                                    case "residential":
                                        mapObjects.setRoadType("residential");
                                        break;
                                    case "pedestrian":
                                        mapObjects.setRoadType("pedestrian");
                                        break;
                                    case "footway":
                                    case "steps":
                                    case "escalator":
                                        mapObjects.setRoadType("footpath");
                                        break;
                                    case "path":
                                    case "cycleway":
                                    case "bridleway":
                                        mapObjects.setRoadType(value);
                                        break;
                                    default:
                                        mapObjects.setRoadType("unknown");
                                        break;
                                    }
                            case "oneway":
                                mapObjects.setRoadOneWay(value.equals("yes"));
                                break;
                            case "building":
                                mapObjects.setShapeType(value);
                                break;
                            case "natural":
                                switch (value) {
                                    case "grass":
                                    case "meadow":
                                    case "grassland":
                                    case "moor":
                                    case "wetland":
                                    case "heath":
                                        mapObjects.setShapeType("grass");
                                        break;
                                    case "water":
                                    case "fountain":
                                        mapObjects.setShapeType("water");
                                        break;
                                    case "coastline":
                                        mapObjects.setShapeType("coastline");
                                    default:
                                        break;
                            }
                            case "water":
                            case "habour":
                                mapObjects.setShapeType("water");
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
                                    case "military":
                                        mapObjects.setShapeType("grass");
                                        break;
                                    case "forest":
                                    case "shrub":
                                        mapObjects.setShapeType("forest");
                                        break;
                                    case "basin":
                                        mapObjects.setShapeType("water");
                                        break;
                                    case "construction":
                                    case "industrial":
                                    case "residential":
                                    case "landfill":
                                        mapObjects.setShapeType("cement");
                                        break;
                                    case "commercial":
                                    case "retail":
                                        mapObjects.setShapeType("commercial");
                                        break;
                                    case "allotments":
                                    case "farmland":
                                    case "orchard":
                                    case "vineyard":
                                        mapObjects.setShapeType("farmland");
                                        break;
                                    default:
                                        break;
                                }
                                case "addr:city":
                                    mapObjects.setCity(value);
                                    break;
                                case "addr:housenumber":
                                    mapObjects.setHouseNumber(value);
                                    break;
                                case "addr:postcode":
                                    mapObjects.setPostcode(value);
                                    break;
                                case "addr:street":
                                    mapObjects.setStreet(value);
                                    break;
                                case "shop":
                                    mapObjects.setPointType("Shop");
                                    break;
                                case "bus":
                                    mapObjects.setPointType("Bus stop");
                                    break;
                                case "station":
                                    switch (value) {
                                        case "subway":
                                            mapObjects.setPointType("Metro station");
                                            break;
                                        default:
                                            mapObjects.setPointType("Train station");
                                            break;
                                    }
                                    break;
                                case "amenity":
                                    switch (value) {
                                        case "place_of_worship":
                                            mapObjects.setPointType("Place of worship");
                                            break;
                                        case "restaurant":
                                        case "fast_food":
                                        case "food_court":
                                            mapObjects.setPointType("Restaurant");
                                            break;
                                        case "bar":
                                        case "pub":
                                            mapObjects.setPointType("Bar");
                                            break;
                                        case "cafe":
                                            mapObjects.setPointType("Cafe");
                                            break;
                                        case "bank":
                                            mapObjects.setPointType("Bank");
                                            break;
                                        case "clinic":
                                        case "dentist":
                                        case "doctors":
                                        case "pharmacy":
                                        case "hospital":
                                            mapObjects.setPointType("Healthcare");
                                            break;
                                        case "cinema":
                                        case "theatre":
                                            mapObjects.setPointType("Theatre");
                                            break;
                                        case "toilets":
                                            mapObjects.setPointType("Toilets");
                                            break;
                                        default:
                                            break;
                                    }
                                case "name":
                                    mapObjects.setName(value);
                                    break;
                                case "place":
                                    switch (value) {
                                        case "country":
                                        case "region":
                                        case "sea":
                                            mapObjects.setFontSize(35);
                                            break;
                                        case "city":
                                            mapObjects.setFontSize(30);
                                            break;
                                        case "town":
                                        case "borough":
                                        case "suburb":
                                        case "island":
                                            mapObjects.setFontSize(20);
                                            break;
                                        case "village":
                                        case "hamlet":
                                        case "smalltown":
                                        case "quarter":
                                        case "square":
                                            mapObjects.setFontSize(15);
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
                    switch(endTag){
                        case "node":
                            mapObjects.finishPoint();
                            break;
                        case "way":
                            mapObjects.finishSimpleElement();
                            break;
                        case "relation":
                            mapObjects.finishRelation();
                            break;
                    }
                    break;
            }
        }
        return null;
    }
}
