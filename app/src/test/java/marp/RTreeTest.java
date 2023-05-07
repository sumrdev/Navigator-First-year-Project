package marp;

import marp.datastructures.RTree;
import marp.mapelements.Element;
import marp.model.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

public class RTreeTest {
    Model model;
    @BeforeEach
    public void getData()throws Exception{
        String defaultSubDir = "data/maps/";
        String defaultFilename = "bornholm.osm";
        File defaultFile = Paths.get(defaultSubDir, defaultFilename).toFile();
        model = Model.createModel(defaultFile);
    }
    @Test
    public void fillBuildingsRTree(){
        RTree<Element> buildingRTree = new RTree<>(model.getMapObjects().getBuildingsList());
    }
    @Test
    public void searchPoint(){

    }
}
