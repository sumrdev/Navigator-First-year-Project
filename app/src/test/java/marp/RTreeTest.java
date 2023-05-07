package marp;

import marp.datastructures.RTree;
import marp.mapelements.Element;
import marp.model.Model;

import marp.utilities.DefaultPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;

public class RTreeTest {
    Model model;
    @BeforeEach
    public void getData()throws Exception{
        String defaultFilename = "bornholm.osm";
        URL defaultFileURL = new URL(DefaultPath.getDefaultPath()+defaultFilename);
        model = Model.createModel(defaultFileURL);
    }
    @Test
    public void fillBuildingsRTree(){
        RTree<Element> buildingRTree = new RTree<>(model.getMapObjects().getBuildingsList());
    }
    @Test
    public void searchPoint(){

    }
}
