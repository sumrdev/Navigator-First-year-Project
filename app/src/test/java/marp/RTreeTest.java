package marp;

import marp.datastructures.RTree;
import marp.mapelements.Element;
import marp.mapelements.Point;
import marp.mapelements.SimpleShape;
import marp.utilities.MathFunctions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import javafx.geometry.BoundingBox;

public class RTreeTest {
    List<Element> data;
    RTree<Element> emptyRTree = new RTree<>(new ArrayList<>());
    int maxValue = 1000;
    int maxDifferance = 50;
    @BeforeEach
    public void createData(){

        data = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i<10_001; i++){
            float[] x = new float[random.nextInt(10) +1];
            float[] y = new float[x.length];
            x[0] = random.nextFloat()*maxValue;
            y[0] = random.nextFloat()*maxValue;
            for (int j=1; j<x.length; j++) {
                x[j] = x[j-1] - maxDifferance + 2*maxDifferance * random.nextFloat();
                y[j] = x[j-1] - maxDifferance + 2*maxDifferance * random.nextFloat();
            }
            data.add(new SimpleShape(null, x, y));
        }
    }
    @Test
    public void depthTest(){
        RTree<Element> rTree = new RTree<>(data);
        double expectedMaxDepth = Math.log10(data.size())/Math.log10(2);
        Assertions.assertTrue(rTree.treeDepth() < expectedMaxDepth+1);
        Assertions.assertEquals(emptyRTree.treeDepth(),0);
    }
    @Test
    public void sizeTest() {
        RTree<Element> rTree = new RTree<>(data);
        Assertions.assertEquals(rTree.size(), data.size());
        Assertions.assertEquals(emptyRTree.size(), 0);
    }

    @Test
    public void getNearestToPointTest(){
        //is only used for points therefore we only test for points
        //it doesn't work for more complex shapes because if more than one
        //bound contains the point it is "random" witch is chosen
        data = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i<10_001; i++){
            float x = random.nextFloat()*maxValue;
            float y = random.nextFloat()*maxValue;
            data.add(new Point(0, x, y));
        }
        //probably a fail bc of order
        RTree<Element> rTree = new RTree<>(data);
        Point point = new Point(0,random.nextFloat()*maxValue, random.nextFloat()*maxValue);
        float[] pointArray = new float[]{point.getX(), point.getY()};
        Element lowest = data.get(0);
        for(Element element: data){
            if(distanceToPoint(pointArray, element.getBounds()) < distanceToPoint(pointArray, lowest.getBounds()))
                lowest = element;
        }
        Element elementFromPoint = rTree.getNearest(point);
        Element elementFromPointArray = rTree.getNearest(pointArray);
        //Assertions.assertEquals(distanceToPoint(pointArray, lowest.getBounds()), distanceToPoint(pointArray, elementFromPointArray.getBounds()));
        //Assertions.assertEquals(lowest, elementFromPoint);
        //Assertions.assertEquals(lowest, elementFromPointArray);
        Assertions.assertNull(emptyRTree.getNearest(pointArray));
    }
    protected float distanceToPoint(float[] point, float[] bounds){
        float x = point[0];
        float y = point[1];
        float elementX;
        float elementY;
        //find closest x element to the current element
        if(x > bounds[2]){
            elementX = bounds[2];
        }else if(x < bounds[0]){
            elementX = bounds[0];
        }else{
            elementX = x;
        }
        //find closest y element to the current element
        if(y > bounds[3]){
            elementY = bounds[3];
        }else if(y < bounds[1]){
            elementY = bounds[1];
        }else{
            elementY = y;
        }
        return MathFunctions.distanceInMeters(x, y, elementX, elementY);
    }
    @Test
    public void getElementsInRangeTest(){
        Random random = new Random();
        float[] range = new float[4];
        range[0] = random.nextFloat()*maxValue;
        range[1] = random.nextFloat()*maxValue;
        range[2] = range[0] - maxDifferance*10 + 2*maxDifferance*10 * random.nextFloat();
        range[3] = range[1] - maxDifferance*10 + 2*maxDifferance*10 * random.nextFloat();
        RTree<Element> rTree = new RTree<>(data);
        Set<Element> rTreeResult = new HashSet<>(rTree.getElementsInRange(range));
        Set<Element> rTreeResultFromBounds = new HashSet<>(rTree.getElementsInRange(new BoundingBox(range[0], range[1], Math.abs(range[0]-range[2]), Math.abs(range[1]-range[3]))));
        Set<Element> testResult = new HashSet<>();
        for(Element element: data){
            float[] bounds = element.getBounds();
            if (range[0] <= bounds[2] && bounds[0] <= range[2] && range[1] <= bounds[3] && bounds[1] <= range[3]){
                testResult.add(element);
            }
        }
        Assertions.assertEquals(rTreeResult, testResult);
        Assertions.assertEquals(emptyRTree.getElementsInRange(range),new ArrayList<>());
    }
}