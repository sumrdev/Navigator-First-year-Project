package marp.datastructures;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import marp.mapelements.Element;
import marp.mapelements.Point;
import marp.utilities.MathFunctions;

import java.io.Serializable;
import java.util.*;

public class RTree<T extends Element> implements Serializable {
    private Node treeNode;
    private final int dimensions;

    /**
     * Constructs an RTree containing the elements of the specified list
     * Params:
     * values – the list of elements
     * Throws:
     * NullPointerException – if null is given as a parameter
     */
    public RTree(List<T> values){
        dimensions = 2;
        if(values.size() != 0) {
            treeNode = new Node(values, 0);
        }
    }
    public int size(){
        return treeNode != null ? treeNode.getSize() : 0;
    }
    public int treeDepth(){
        return treeNode != null ? treeNode.getLeafDepth(): 0;
    }
    protected class Node implements Serializable{
        int size;
        protected int children;
        protected Node low;
        protected Node high;
        int layer;
        //[0],[1] will be min, coords, (x,y respectively) and [2],[3] will be max coords
        float[] boundingRect;
        //values kan laves til en linked list da denne er samme hastighed at lave for each på
        //og er hurtigere hvis der skal laves quick select
        T value;
        public Node(List<T> values, int layer) {
            children = 2;
            this.layer = layer;
            if (values.size() > 1){
                splitNode(values);
                boundingRect = getBoundingRectFromChildren();
                size = high.getSize() + low.getSize();
            } else {
                this.value = values.get(0);
                boundingRect = value.getBounds();
                size = 1;
            }
        }
        //only works for RTrees with 2 children
        protected float[] getBoundingRectFromChildren(){
            float[] result = low.boundingRect;
            float[] bounds = high.boundingRect;
            if(bounds[0] < result[0]){
                result[0] = bounds[0];
            } if(bounds[2] > result[2]) {
                result[2] = bounds[2];
            }
            if(bounds[1] < result[1]){
                result[1] = bounds[1];
            } if(bounds[3] > result[3]){
                result[3] = bounds[3];
            }
            return result;
        }
        protected void splitNode(List<T> values){
            sortList(values);
            List<T>[] lists = splitList(values);
            low = new Node(lists[0], layer + 1);
            high = new Node(lists[1], layer + 1);
        }
        protected List<T>[] splitList(List<T> values){
            List<T>[] result = new List[children];
            for(int i=0; i<children ;i++){
                result[i] = new ArrayList<>(values.subList(values.size()*i/children,values.size()*(i+1)/children));
            }
            return result;
        }
        protected void sortList(List<T> values){
            values.sort(new Comparator<T>() {
                //compares the min values of the elements, the idea is just to split it evenly
                // that the ranges are correct is not supposed to be checked here
                @Override
                public int compare(T o1, T o2) {
                    return (o1.getBounds()[layer%dimensions] < o2.getBounds()[layer%dimensions]) ? -1:
                            (o1.getBounds()[layer%dimensions] > o2.getBounds()[layer%dimensions]) ? 1:
                                    0;
                }
            });
        }
        public void getElementsInRange(float[] rangeCoords, List<T> list){
            if(low != null && low.intersects(rangeCoords)) {
                low.getElementsInRange(rangeCoords, list);
            }
            if(high != null && high.intersects(rangeCoords)){
                high.getElementsInRange(rangeCoords,list);
            } else if(low == null && intersects(rangeCoords, value.getBounds())){
                list.add(value);
            }
        }
        public void getElementsInRangeDebug(float[] rangeCoords, List<float[]> list){
            if (low != null && low.intersects(rangeCoords)) {
                low.getElementsInRangeDebug(rangeCoords, list);
            }
            if (high != null && high.intersects(rangeCoords)) {
                high.getElementsInRangeDebug(rangeCoords, list);
            }
            list.add(boundingRect);
        }
        public void getNearest(float[] point, NodeDistance lowest){
            if(low != null && low.distance(point) < lowest.distance){
                low.getNearest(point, lowest);
            }
            if(high != null && high.distance(point) < lowest.distance) {
                high.getNearest(point, lowest);
            } else if(low == null) {
                lowest.update(value, distance(point[0], point[1], value));
            }
        }
        protected boolean intersects(float[] rect1, float[] rect2){
            return rect1[0] <= rect2[2] && rect2[0] <= rect1[2] && rect1[1] <= rect2[3] && rect2[1] <= rect1[3];
        }
        protected boolean intersects(float[] rangeCoords){
            return intersects(boundingRect, rangeCoords);
        }
        protected float distance(float x, float y, float[] bounds){
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
        protected float distance(float x, float y, T element){
            return distance(x, y, element.getBounds());
        }
        protected float distance(float x1, float y1){
            return distance(x1,y1,boundingRect);
        }
        protected float distance(float[] point){
            return distance(point[0], point[1]);
        }
        public int getSize(){
            return size;
        }
        public int getLeafDepth(){
            int lowDepth =0;
            int highDepth = 0;
            if(low != null)
                lowDepth = low.getLeafDepth();
            if(high!= null)
                highDepth = high.getLeafDepth();
            else if(low == null)
                return layer;
            return Math.max(lowDepth,highDepth);
        }
    }
    public class NodeDistance{
        T element;
        float distance;
        private NodeDistance(T element, float distance) {
            this.element = element;
            this.distance = distance;
        }
        public void update(T element, float distance){
            if(distance < this.distance){
                this.element = element;
                this.distance = distance;
            }
        }
    }
    public List<T> getElementsInRange(Bounds box){
        float[] coords = new float[4];
        coords[0] = (float) box.getMinX();
        coords[1] = (float) box.getMinY();
        coords[2] = (float) box.getMaxX();
        coords[3] = (float) box.getMaxY();
        return getElementsInRange(coords);
    }
    public List<T> getElementsInRange(float[] coords){
        List<T> result = new ArrayList<>();
        if(treeNode != null) {
            treeNode.getElementsInRange(coords, result);
        }
        return result;
    }
    public T getNearest(float[] bound){
        NodeDistance lowest = new NodeDistance(null, Float.POSITIVE_INFINITY);
        if(treeNode != null) {
            treeNode.getNearest(bound, lowest);
        }
        T result = lowest.element;
        return result;
    }
    public T getNearest(Point point){
        return getNearest(new float[]{point.getX(), point.getY()});
    }

    public void getElementsInRangeDebug(GraphicsContext gc, Point2D point){
        float[] pointAsBound = new float[]{(float) point.getX(), (float) point.getY(), (float) point.getX(), (float) point.getY()};
        List<float[]> treeNodeBounds = new ArrayList<>();
        if(treeNode != null) {
            treeNode.getElementsInRangeDebug(pointAsBound, treeNodeBounds);
        }
        for (float[] boundingCoords : treeNodeBounds) {
            System.out.println("bounds found: " + Arrays.toString(boundingCoords));
            gc.setStroke(Color.PURPLE);
            gc.strokeLine(boundingCoords[0], boundingCoords[1],boundingCoords[2], boundingCoords[1]);
            gc.strokeLine(boundingCoords[2], boundingCoords[1],boundingCoords[2], boundingCoords[3]);
            gc.strokeLine(boundingCoords[2], boundingCoords[3],boundingCoords[0], boundingCoords[3]);
            gc.strokeLine(boundingCoords[0], boundingCoords[3],boundingCoords[0], boundingCoords[1]);
        }
    }
}