package marp.datastructures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import marp.mapelements.Road;
import marp.mapelements.RoadNode;

public class Digraph implements Serializable {
    ArrayList<Road> roads;
    HashMap<Long, RoadNode> nodes;

    public Digraph(ArrayList<Road> roads) {
        this.nodes = new HashMap<>();
        this.roads = roads;
        for (Road road : roads) {
            nodes.put(road.getNode(0).getID(), road.getNode(0));
            for (int i = 0; i < road.getNodeSize(); i++) {
                RoadNode node1 = road.getNode(i);
                RoadNode node2 = road.getNode(i + 1);
                nodes.put(node1.getID(), node1);
                node1.addEdge(new Edge(node1.getID(), node2.getID(), road.getID()));
                if(road.isOneWay()) node2.addEdge(new Edge(node2.getID(), node1.getID(), road.getID()));
            }
        }
    }
}
