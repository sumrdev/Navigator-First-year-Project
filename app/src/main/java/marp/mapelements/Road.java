package marp.mapelements;

import java.util.ArrayList;

public class Road extends SimpleShape {
    ArrayList<Point> nodes;
    boolean oneway;
    String type;
    int speed;
    
    public Road(Long id, ArrayList<Point> nodes) {
        super(id);
        this.nodes = nodes;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setOneWay(boolean oneway){
        this.oneway = oneway;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }
}
