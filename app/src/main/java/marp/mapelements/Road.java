package marp.mapelements;

import java.util.ArrayList;

public class Road extends SimpleElement {
    ArrayList<SinglePointElement> nodes;
    boolean oneway;
    String type;
    
    public Road(Long id, ArrayList<SinglePointElement> nodes) {
        super(id);
        this.nodes = nodes;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setOneWay(boolean oneway){
        this.oneway = oneway;
    }
}
