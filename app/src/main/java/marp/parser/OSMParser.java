package marp.parser;

import java.io.InputStream;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import marp.mapelements.SimpleElement;
import marp.mapelements.SinglePointElement;
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
                            SinglePointElement spe = new SinglePointElement(id, x, y);
                            mapObjects.addSinglePointElement(spe);
                            break;
                        case "nd":
                            long nodeID = Long.parseLong(xmlsr.getAttributeValue(null, "ref"));
                            mapObjects.addPointToUnfinishedWay(mapObjects.getSinglePointElementByID(nodeID));
                            break;
                        case "way":
                            mapObjects.initializeEmptySimpleElement(Long.parseLong(xmlsr.getAttributeValue(null, "id")));
                            break;
                        case "member":
                            break;
                        case "relation":
                            mapObjects.initializeEmptyComplexElement(Long.parseLong(xmlsr.getAttributeValue(null, "id")));
                            break;
                        case "tag":
                        String key = xmlsr.getAttributeValue(null, "k");
                        String value = xmlsr.getAttributeValue(null, "v");
                        switch (key) {
                            case "highway":
                            mapObjects.convertSimpleElementToRoad();
                                switch (value) {
                                    case "motorway":
                                    case "primary":
                                    case "trunk":
                                    case "secondary":
                                    case "tertiary":
                                    case "tertiary_link":
                                    case "unclassified":
                                    case "residential":
                                    case "pedestrian":
                                    case "footway":
                                    case "steps":
                                    case "escalator":
                                    case "path":
                                    case "cycleway":
                                    case "bridleway":
                                        mapObjects.setRoadType(value);
                                        break;
                                    default:
                                        mapObjects.setRoadType("unclassified");
                                        break;
                                    }
                            case "oneway":
                                mapObjects.setRoadOneWay(value.equals("yes"));
                                break;
                            case "building":
                                mapObjects.setType(value);
                                break;
                            case "natural":
                                switch (value) {
                                    case "grass":
                                    case "meadow":
                                    case "grassland":
                                    case "moor":
                                    case "wetland":
                                    case "heath":
                                        mapObjects.setType("grass");
                                        break;
                                    case "water":
                                    case "fountain":
                                        mapObjects.setType("water");
                                        break;
                                    case "coastline":
                                        mapObjects.setType("coastline");
                                    default:
                                        break;
                            }
                            case "water":
                                mapObjects.setType("water");
                                break;
                            case "habour":
                                mapObjects.setType("water");
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
                                        mapObjects.setType("grassy");
                                        break;
                                    case "forest":
                                    case "shrub":
                                        mapObjects.setType("forest");
                                        break;
                                    case "basin":
                                        mapObjects.setType("water");
                                        break;
                                    case "construction":
                                    case "industrial":
                                    case "residential":
                                    case "landfill":
                                        mapObjects.setType("cement");
                                        break;
                                    case "commercial":
                                    case "retail":
                                        mapObjects.setType("commercial");
                                        break;
                                    case "allotments":
                                    case "farmland":
                                    case "orchard":
                                    case "vineyard":
                                        mapObjects.setType("jungle");
                                        break;
                                    default:
                                        break;
                                }
                        }

                        default:
                        break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    String endTag = xmlsr.getLocalName();
                    switch(endTag){
                        case "way":
                            mapObjects.finishWay();
                        break;
                        case "relation":
                        break;
                    }
                    break;
            }
        }
        return null;
    }
}
