package marp.datastructures;

import java.io.Serializable;

import marp.mapelements.Road;

public class Edge implements Serializable{
    Long start, end, road;
    boolean driveable, walkable, bikeable;
    public Edge(Long start, Long end, Long roadID, boolean driveable, boolean walkable, boolean bikeable) {
        this.start = start;
        this.end = end;
        this.road = roadID;

        this.driveable = driveable;
        this.walkable = walkable;
        this.bikeable = bikeable;
    }
}
