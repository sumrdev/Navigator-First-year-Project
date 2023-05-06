package marp.mapelements;

import java.io.Serializable;
import java.util.ArrayList;

import marp.datastructures.Edge;

public class RoadNode implements Serializable {
    float x,y;
    ArrayList<Edge> edges;
    long id;
    public RoadNode(Point node) {
        this.x = node.getX();
        this.y = node.getY();
        this.id = node.getID();
        this.edges = new ArrayList<Edge>();        
    }

    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }

    public long getID(){
        return this.id;
    }

    
    public ArrayList<Edge> getEdges(){
        return this.edges;
    }
    
    public void addEdge(Edge edge){
        this.edges.add(edge);
    }
}
