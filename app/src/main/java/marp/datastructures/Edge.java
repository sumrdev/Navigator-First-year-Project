package marp.datastructures;

import marp.mapelements.Road;

public class Edge {
    Long start, end, road;
    public Edge(Long start, Long end, Long roadID) {
        this.start = start;
        this.end = end;
        this.road = roadID;
    }
}
