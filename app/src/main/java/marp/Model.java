package marp;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class Model {

    List<Way> ways = new ArrayList<Way>();
    
    Model(String filename) throws XMLStreamException, FactoryConfigurationError{
        parseOSM(filename);
    }

    private void parseOSM(String filename) throws XMLStreamException, FactoryConfigurationError{
        XMLStreamReader reader = XMLInputFactory.newInstance()
        .createXMLStreamReader(new InputStreamReader(getClass()
        .getResourceAsStream(filename)));
        Map<Long, Node> nodes = new HashMap<>();
        while(reader.hasNext()){
            var tag = reader.next();
            if(tag == XMLStreamConstants.START_ELEMENT){
                String name = reader.getLocalName();
                if(name.equals("node")){
                    long id = Long.parseLong(reader.getAttributeValue(null, "id"));
                    double lat = Double.parseDouble(reader.getAttributeValue(null, "lat"));
                    double lon = Double.parseDouble(reader.getAttributeValue(null, "lon"));
                    nodes.put(id, new Node(lat, lon));
                }
                if(name.equals("nd")){
                    long id = Long.parseLong(reader.getAttributeValue(null, "ref"));
                    nodes.get(id);
                    
                }
            }
        }
    }
}
