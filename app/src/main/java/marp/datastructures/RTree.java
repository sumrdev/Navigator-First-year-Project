package marp.datastructures;

import javafx.geometry.Bounds;
import marp.mapelements.Element;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class RTree<T extends Element> implements Serializable {
    private T reallyBadClosestGuess;
    private Node treeNode;
    private final int dimensions;
    private final int maxElementsPerLeaf;
    public RTree(List<T> values){
        maxElementsPerLeaf = 100;
        dimensions = 2;
        treeNode = new Node(values, 0);
        // reallyBadClosestGuess = values.get(values.size()/2);
    }
    public RTree(List<T> values, int maxElementsPerLeaf){
        this.maxElementsPerLeaf = maxElementsPerLeaf;
        dimensions = 2;
        treeNode = new Node(values, 0);
        reallyBadClosestGuess = values.get(values.size()/2);
    }
    //could be used to return rTree for an area instead of a list
    /*protected RTree(Node newTreeNode){
        this.treeNode = newTreeNode;
    }*/
    protected class Node implements Serializable{
        int size;
        //      protected Node[]
        protected int children;
        protected Node low;
        protected Node high;
        boolean hasChildren;
        int layer;
        //[0],[1] will be min, coords, (x,y respectively) and [2],[3] will be max coords
        float[] boundingRect;
        //values kan laves til en linked list da denne er samme hastighed at lave for each på
        //og er hurtigere hvis der skal laves quick select
        List<T> values;
        public Node(List<T> values, int layer) {
            children = 2;
            this.layer = layer;
            boundingRect = getNodeBounds(values);
            size = values.size();
            if (values.size() > maxElementsPerLeaf){
                splitNode(values);
            } else {
                this.values = values;
            }
        }
        protected float[] getNodeBounds(List<T> values){
            float[] result = new float[]{Float.MAX_VALUE, Float.MAX_VALUE, -1000, -1000};
            for(var element : values){
                float[] bounds = element.getBounds();
                if(bounds[0] < result[0]){
                    result[0] = bounds[0];
                } else if(bounds[2] > result[2]) {
                    result[2] = bounds[2];
                }
                if(bounds[1] < result[1]){
                    result[1] = bounds[1];
                } else if(bounds[3] > result[3]){
                    result[3] = bounds[3];
                }
            }
            return result;
        }
        protected void splitNode(List<T> values){
            sortList(values);
            List<T>[] lists = splitList(values);
            low = new Node(lists[0], layer + 1);
            high = new Node(lists[1], layer + 1);
            hasChildren = true;
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
            if(!hasChildren){
                for(T element: values){
                    if(intersects(rangeCoords, element.getBounds())) {
                        list.add(element);
                    }
                }
                return;
            }
            if(low.intersects(rangeCoords)){
                low.getElementsInRange(rangeCoords,list);
            }
            if(high.intersects(rangeCoords)){
                high.getElementsInRange(rangeCoords,list);
            }
        }
        /*public T getClosest(float x, float y, T currentClosest){
            //still has a problem that the currentClosest element is null at the start at will throw an exception
            if(hasChildren){
                if(low.distance(x,y) < distance(x,y,currentClosest)){
                    currentClosest = low.getClosest(x,y,currentClosest);
                }
                if(high.distance(x,y) < distance(x,y,currentClosest)){
                    currentClosest = high.getClosest(x,y,currentClosest);
                }
            }else {
                for(T element: values) {
                    if(distance(x,y,element) < distance(x,y,currentClosest)){
                        currentClosest = element;
                    }
                }
            }
            return currentClosest;
        }*/
        public void getNearest(float[] point, PriorityQueue<NodeDistance> pq, int count){
            if(!hasChildren){
                for(T element: values) {
                    float distance = distance(point[0], point[1], element);
                    if(distance < pq.peek().distance) {
                        pq.offer(new NodeDistance(element, distance));
                    }
                }
            }else {
                if(low.distance(point) < pq.peek().distance){
                    low.getNearest(point, pq, count);
                }
                if(high.distance(point) < pq.peek().distance){
                    high.getNearest(point, pq, count);
                }
                /*
                float[] lowDistances = low.distances(point);
                float[] highDistances = high.distances(point);
                if (lowDistances[0] < highDistances[0]) {
                    if (pq.size() < count || lowDistances[0] < pq.peek().distance) {
                        low.getNearest(point, pq, count);
                    }
                    if (pq.size() < count || highDistances[0] < pq.peek().distance) {
                        high.getNearest(point, pq, count);
                    }
                } else {
                    if (pq.size() < count || highDistances[0] < pq.peek().distance) {
                        high.getNearest(point, pq, count);
                    }
                    if (pq.size() < count || lowDistances[0] < pq.peek().distance) {
                        low.getNearest(point, pq, count);
                    }
                }*/
            }
        }
        private float[] distances(float[] point){
            float[] result = new float[2];
            result[0] = distance(point);
            if (hasChildren) {
                result[1] = Math.min(low.distances(point)[0], high.distances(point)[0]);
            } else {
                result[1] = result[0];
            }
            return result;
        }
        protected boolean intersects(float[] rangeCoords){
            return intersects(boundingRect, rangeCoords);
            //return boundingRect[0] <= rangeCoords[2] && rangeCoords[0] <= boundingRect[2] && boundingRect[1] <= rangeCoords[3] && rangeCoords[1] <= boundingRect[3];
        }
        protected boolean intersects(float[] rect1, float[] rect2){
            return rect1[0] <= rect2[2] && rect2[0] <= rect1[2] && rect1[1] <= rect2[3] && rect2[1] <= rect1[3];
        }
        public float distance(float x1, float y1, float x2, float y2) {
            return (float) Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
        }
        public float distance(float x, float y, T element){
            return distance(x, y, element.getBounds());
        }
        public float distance(float x, float y, float[] bounds){
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
            return distance(x,y,elementX,elementY);
        }
        public float distance(float x1, float y1){
            return distance(x1,y1,boundingRect);
        }
        public float distance(float[] point){
            return distance(point[0], point[1]);
        }
        //could maybe add the coords as values int the node to not get them from the value everytime
    }
    public class NodeDistance implements Comparable<NodeDistance>{
        T element;
        float distance;
        public NodeDistance(T element, float distance) {
            this.element = element;
            this.distance = distance;
        }
        public int compareTo(NodeDistance o2){
            return (this.distance < o2.distance) ? -1:
                    (this.distance > o2.distance) ? 1:
                            0;
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
    public List<T> getElementsInRange(float[] minCoord, float[] maxCoord){
        return getElementsInRange(new float[]{minCoord[0], minCoord[1], maxCoord[0], maxCoord[1]});
    }
    //public T getClosest(float[] coord){    }
    //not working
    public List<T> getElementsInRange(float xMin, float yMin, float xMax, float yMax){
        return getElementsInRange(new float[]{xMin,yMin,xMax,yMax});
    }
    public List<T> getElementsInRange(float[] coords){
        List<T> result = new ArrayList<>();
        treeNode.getElementsInRange(coords, result);
        return result;
    }
    public List<T> getElements(){
        List<T> result = new ArrayList<>();
        treeNode.getElementsInRange(treeNode.boundingRect, result);
        return result;
    }
    public int Size(){
        return treeNode.size;
    }
    public T getNearest(float[] ds, int elements){
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>();
        pq.add(new NodeDistance(null, Float.MAX_VALUE));
        treeNode.getNearest(ds, pq, elements);
        T result = pq.peek().element;
        return result;
    }
}