package marp.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import marp.parser.OSMParser;;

public class Model {
    public Model(URL fileURL) throws URISyntaxException, FileNotFoundException, XMLStreamException, FactoryConfigurationError{
        File file = Paths.get(fileURL.toURI()).toFile();
        OSMParser osmParser = new OSMParser();
        MapObjects mapObjects = osmParser.parseOSM(new FileInputStream(file));
        System.out.println("beans");
    }

    public Model(String filename) throws FileNotFoundException, XMLStreamException, FactoryConfigurationError{
       OSMParser osmParser = new OSMParser();
       MapObjects mapObjects = osmParser.parseOSM(new FileInputStream(filename));
    }
}
