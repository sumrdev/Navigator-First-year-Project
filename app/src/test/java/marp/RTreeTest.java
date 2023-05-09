package marp;

import com.sun.source.tree.AssertTree;
import javafx.geometry.Point2D;
import marp.datastructures.RTree;
import marp.mapelements.Element;
import marp.mapelements.SimpleShape;
import marp.model.Model;

import marp.utilities.MathFunctions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RTreeTest {
    List<Element> data;
    @BeforeEach
    public void createData(){
        data = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i<10_000; i++){
            float[] x = new float[random.nextInt(10) +1];
            float[] y = new float[x.length];
            for (int j=0; j<x.length; j++) {
                x[j] = random.nextFloat();
                y[j] = random.nextFloat();
            }
            data.add(new SimpleShape(null, x, y));
        }
    }
    @Test
    public void depthTest(){
        RTree<Element> rTree = new RTree<>(data);
        double expectedMaxDepth = Math.log10(data.size())/Math.log10(2);
        Assertions.assertTrue(rTree.treeDepth() < expectedMaxDepth+1);
    }
    @Test
    public void sizeTest(){
        RTree<Element> rTree = new RTree<>(data);
        Assertions.assertEquals(rTree.size(), data.size());
    }
    @Test
    public void buildingRTreeFromListTest(){
        RTree<Element> rTree = new RTree<>(data);
        //how to check if rTree is build correct...?
    }
    @Test
    public void getNearestToPointTest(){
        //probably a fail bc of order
        RTree<Element> rTree = new RTree<>(data);
        Random random = new Random();
        float[] point = new float[]{random.nextFloat(), random.nextFloat()};
        Element lowest = data.get(0);
        for(Element element: data){
            if(distanceToPoint(element.getBounds(), point) < distanceToPoint(lowest.getBounds(), point))
                lowest = element;
        }
        Assertions.assertEquals(lowest, rTree.getNearest(point));
    }
    public double distanceToPoint(float[] element, float[] point){
        float elementX;
        float elementY;
        //find closest x element to the current element
        if(point[0] > element[2]){
            elementX = element[2];
        }else if(point[0] < element[0]){
            elementX = element[0];
        }else{
            elementX = point[0];
        }
        //find closest y element to the current element
        if(point[1] > element[3]){
            elementY = element[3];
        }else if(point[1] < element[1]){
            elementY = element[1];
        }else{
            elementY = point[1];
        }
        return MathFunctions.distanceInMeters(point[0], point[1], elementX, elementY);
    }
    @Test
    public void getElementsInRangeTest(){
        float[] range = new float[4];
    }
}
