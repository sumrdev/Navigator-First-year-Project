package marp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;

import marp.model.Model;

public class DigraphTest {
    Model model;
    @BeforeEach
    void setUp() throws ClassNotFoundException, URISyntaxException, XMLStreamException, FactoryConfigurationError, IOException {
        //url https://overpass-api.de/api/map?bbox=11.6115,55.1367,11.9638,55.2675
        InputStream in = getClass().getResourceAsStream("/maps/4_roundabouts.osm");
        this.model = Model.updateModel(in, "4_roundabouts.osm");

    }

    @Test
    void instructionsTest(){
        long roadNodeIDA = 423493290;
        long roadNodeIDB = 298861825;
        List<String> directions = model.getMapObjects().getDigraph().aStar( roadNodeIDA, roadNodeIDB, true);
        assertEquals(5, directions.size());
        directions = model.getMapObjects().getDigraph().aStar( roadNodeIDA, roadNodeIDB, false);
        assertEquals(1, directions.size());
    }
}
