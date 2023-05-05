package marp.mapelements;

import java.util.ArrayList;

public class Road extends SimpleShape {
    ArrayList<RoadNode> nodes;
    boolean oneway;
    String type;
    int speed;
    
    public Road(Long id, ArrayList<Point> nodes) {
        super(id);
        this.nodes = convertPointArrayToRoadNodeArray(nodes);;
    }

    private ArrayList<RoadNode> convertPointArrayToRoadNodeArray(ArrayList<Point> nodes){
        ArrayList<RoadNode> roadNodes = new ArrayList<>();
        for (Point node : nodes) {
            roadNodes.add(new RoadNode(node));
        }
        return roadNodes;
    }

    public ArrayList<RoadNode> getNodes(){
        return nodes;
    }

    public RoadNode getNode(int index){
        return nodes.get(index);
    }
    
    public int getNodeSize(){
        return nodes.size();
    }

    public boolean isOneWay(){
        return this.oneway;
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
