package marp;

import marp.datastructures.RTree;
import marp.mapelements.Element;
import marp.model.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RTreeTest {
    List<Element> data;
    Model model;
    @BeforeEach
    public void createData(){
        data = new ArrayList<>();
    }
    @Test
    public void buildingRTreeFromList(){
        RTree<Element> buildingRTree = new RTree<>(data);
    }
    @Test
    public void searchPoint(){

    }
}
