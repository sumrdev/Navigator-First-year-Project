package marp.mapelements;

import java.io.Serializable;
import java.util.ArrayList;

import marp.datastructures.Edge;

public class RoadNode extends Point implements Serializable {
    ArrayList<Edge> edges;

    public RoadNode(Point node) {
        super(node.getX(), node.getY());
        this.id = node.getID();
        this.edges = new ArrayList<Edge>();
    }

    public ArrayList<Edge> getEdges(boolean walking) {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        if (walking) {
            for (Edge edge : this.edges) {
                if (edge.isWalkable()) {
                    edges.add(edge);
                }
            }
            return edges;
        } else {
            for (Edge edge : this.edges) {
                if (edge.isDriveable()) {
                    edges.add(edge);
                }
            }
            return edges;
        }
    }

    public ArrayList<Edge> getEdges() {
        return this.edges;
    }

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }
}
