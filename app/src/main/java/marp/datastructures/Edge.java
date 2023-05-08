package marp.datastructures;

import java.io.Serializable;

import marp.mapelements.Road;
import marp.utilities.MathFunctions;

public class Edge implements Serializable{
    Long start, end, road;
    boolean driveable, walkable;
    public Edge(Long start, Long end, Long roadID, boolean driveable, boolean walkable) {
        this.start = start;
        this.end = end;
        this.road = roadID;

        this.driveable = driveable;
        this.walkable = walkable;
    }

    public boolean isDriveable(){
        return this.driveable;
    }

    public boolean isWalkable(){
        return this.walkable;
    }

}
