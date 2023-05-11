package marp.datastructures;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
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

    /**
     *
     * @return the number of elements in the tree
     */
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
        /**
         * a float array of length 4 in the order {x_min,y_min, x_max, y_max}
         */
        float[] boundingRect;
        T value;

        /**
         *
         * @param values a list of elements that the Node should contain, either in further nodes or as its value
         * @param layer the layer this is from the root Node
         */
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
                size = values.size();
            }
        }

        /**
         *
         * @return the widest bounding rect that can be made from its two children
         */
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

        /**
         * Creates child nodes for the node that called this
         * @param values the list of values that should be split between the two child nodes
         */
        protected void splitNode(List<T> values){
            sortList(values);
            List<T>[] lists = splitList(values);
            low = new Node(lists[0], layer + 1);
            high = new Node(lists[1], layer + 1);
        }

        /**
         *
         * @param values the lists that should be split
         * @return the new lists, slit in the correct dimension given by the layer value, that should be used to create new child Nodes
         */
        protected List<T>[] splitList(List<T> values){
            List<T>[] result = new List[children];
            for(int i=0; i<children ;i++){
                result[i] = new ArrayList<>(values.subList(values.size()*i/children,values.size()*(i+1)/children));
            }
            return result;
        }

        /**
         * sorts a given list in a way that it can be split evenly
         * @param values the values that should be sorted
         */
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

        /**
         * Adds all elements within the rangeCoords to a given list
         * @param rangeCoords the coordinates that all elements should be given from, as a float array of length 4 in the order {x_min,y_min, x_max, y_max}
         * @param list the list that the elements should be added to
         */
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

        /**
         * Adds all node bounds within the rangeCoords to a given list
         * @param rangeCoords the coordinates that all node bounds should be given from
         * @param list the list that the node bounds should be added to
         */
        public void getElementsInRangeDebug(float[] rangeCoords, List<float[]> list){
            if (low != null && low.intersects(rangeCoords)) {
                low.getElementsInRangeDebug(rangeCoords, list);
            }
            if (high != null && high.intersects(rangeCoords)) {
                high.getElementsInRangeDebug(rangeCoords, list);
            }
            list.add(boundingRect);
        }

        /**
         * Modifies the parameter "lowest" so that it contains the lowest element in the tree
         * @param point the point, given as a float array of length 2 in the order {x,y}
         * @param lowest an object containing the element with the lowest distance to the point seen so far
         */
        public void getNearest(float[] point, NodeDistance lowest){
            if(low != null && low.distance(point) < lowest.distance){
                low.getNearest(point, lowest);
            }
            if(high != null && high.distance(point) < lowest.distance) {
                high.getNearest(point, lowest);
            }
            if(value != null) {
                lowest.update(value, distance(point[0], point[1], value));
            }
        }

        /**
         *
         * @param rect1 a float array of length 4 in the order {x_min,y_min, x_max, y_max}
         * @param rect2 a float array of length 4 in the order {x_min,y_min, x_max, y_max}
         * @return {@code true} if the two rectangles intersects
         */
        protected boolean intersects(float[] rect1, float[] rect2){
            return rect1[0] <= rect2[2] && rect2[0] <= rect1[2] && rect1[1] <= rect2[3] && rect2[1] <= rect1[3];
        }

        /**
         *
         * @param  rangeCoords the range, given as a float array of length 4 in the order {x_min,y_min, x_max, y_max}
         * @return {@code true} if there's an intersection between the rangeCoords and the boundingRect of this node
         */
        protected boolean intersects(float[] rangeCoords){
            return intersects(boundingRect, rangeCoords);
        }
        /**
         * @param  x the x value of the point
         * @param  y the y value of the point
         * @param  bounds the bounding rect given as a float array of length 4 in the order {x_min,y_min, x_max, y_max}
         * @return the distance from the bounding rect to the point
         */
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
        /**
         * @param  x the x value of the point
         * @param  y the y value of the point
         * @param  element the element that should be found the distance to
         * @return the distance of the elements bounding rect to the point
         */
        protected float distance(float x, float y, T element){
            return distance(x, y, element.getBounds());
        }
        /**
         *
         * @param  x1 the x value of the point
         * @param  y1 the y value of the point
         * @return the distance from this node bounding rect to the point
         */
        protected float distance(float x1, float y1){
            return distance(x1,y1,boundingRect);
        }

        /**
         *
         * @param  point the point, given as a float array of length 2 in the order {x,y}
         * @return the distance from this to the point
         */
        protected float distance(float[] point){
            return distance(point[0], point[1]);
        }

        /**
         *
         * @return the size of the tree Node
         */
        public int getSize(){
            return size;
        }

        /**
         *
         * @return the highest layer value of any tree Node
         */
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

    private class NodeDistance{
        T element;
        float distance;
        public NodeDistance(T element, float distance) {
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
    /**
     *
     * @param box the coordinates that all elements should be given from given as a javaFX Bounds
     * @return a list containing the elements that has a bounding box that intersects with the range given
     * @see javafx.geometry.Bounds
     */
    public List<T> getElementsInRange(Bounds box){
        float[] coords = new float[4];
        coords[0] = (float) box.getMinX();
        coords[1] = (float) box.getMinY();
        coords[2] = (float) box.getMaxX();
        coords[3] = (float) box.getMaxY();
        return getElementsInRange(coords);
    }

    /**
     *
     * @param range the coordinates that all elements should be given from as a float array of length 4 in the order {x_min,y_min, x_max, y_max}
     * @return a list containing the elements that has a bounding box that intersects with the range given
     */
    public List<T> getElementsInRange(float[] range){
        List<T> result = new ArrayList<>();
        if(treeNode != null) {
            treeNode.getElementsInRange(range, result);
        }
        return result;
    }

    /**
     *
     * @param  point the point, given as a float array of length 2 in the order {x,y}
     * @return The element with the lowest distance to the point
     */
    public T getNearest(float[] point){
        NodeDistance lowest = new NodeDistance(null, Float.POSITIVE_INFINITY);
        if(treeNode != null) {
            treeNode.getNearest(point, lowest);
        }
        T result = lowest.element;
        return result;
    }

    /**
     *
     * @param point the point, given as a Point of the type made for the map of denmark project
     * @return The element with the lowest distance to the point
     * @see marp.mapelements.Point
     */
    public T getNearest(Point point){
        return getNearest(new float[]{point.getX(), point.getY()});
    }

    /**
     *
     * @param point the point, given as a javaFX Point2D, that should be used to get the node bounds that enclose the given point
     * @return a list of float arrays witch are the boundaries of the tree nodes that intersects the point
     * @see javafx.geometry.Point2D
     */
    public List<float[]> getElementsInRangeDebug(Point2D point){
        float[] pointAsBound = new float[]{(float) point.getX(), (float) point.getY(), (float) point.getX(), (float) point.getY()};
        List<float[]> treeNodeBounds = new ArrayList<>();
        if(treeNode != null) {
            treeNode.getElementsInRangeDebug(pointAsBound, treeNodeBounds);
        }
        return treeNodeBounds;
    }
}