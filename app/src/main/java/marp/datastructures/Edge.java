package marp.datastructures;

import java.io.Serializable;

public class Edge implements Serializable{
    long start, end, road;
    boolean driveable, walkable, roundabout;
    public Edge(long start, long end, long roadID, boolean driveable, boolean walkable, boolean roundabout) {
        this.start = start;
        this.end = end;
        this.road = roadID;

        this.driveable = driveable;
        this.walkable = walkable;
        this.roundabout = roundabout;
    }

    public boolean isDriveable(){
        return this.driveable;
    }

    public boolean isRoundabout(){
        return this.roundabout;
    }

    public boolean isWalkable(){
        return this.walkable;
    }

}
