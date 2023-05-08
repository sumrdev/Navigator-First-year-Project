package marp.mapelements.details;

public enum RoadType {
    MOTORWAY(6, 7, 130, false, true),
    PRIMARY(5, 6, 80, true, true),
    TERTIARY(3, 4, 80, true, true),
    RESIDENTIAL(2, 3, 50, true, true),
    PEDESTRIAN(2, 3, 10, true, false),
    PATH(0.7f, 0, 10, true, false),
    DO_NOT_SHOW(0, 0, 0, false, false),
    UNDEFINED(0, 0, 0, false, false);
    private float roadWidth;
    private float outlineWidth;
    private int speed;
    private boolean walkable;
    private boolean driveable;
    RoadType(float roadWidth, float outlineWidth, int speed, boolean walkable, boolean driveable) {
        this.roadWidth = roadWidth;
        this.outlineWidth = outlineWidth;
        this.speed = speed;
        this.walkable = walkable;
        this.driveable = driveable;
    }

    public float getRoadWidth() {
        return roadWidth;
    }
    
    public float getOutlineWidth() {
        return outlineWidth;
    }
    public int getSpeed() {
        return speed;
    }
    
    public boolean isDriveable() {
        return driveable;
    }
    
    public boolean isWalkable() {
        return walkable;
    }

}
