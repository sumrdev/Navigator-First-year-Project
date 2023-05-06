package marp.mapelements.details;

public enum RoadType {
    MOTORWAY(6, 7, 130),
    PRIMARY(5, 6, 80),
    TERTIARY(3, 4, 80),
    RESIDENTIAL(2, 3, 50),
    PEDESTRIAN(2, 3, 10),
    PATH(0.7f, 0, 10),
    DO_NOT_SHOW(0, 0, 0),
    UNDEFINED(0, 0, 0);
    private float roadWidth;
    private float outlineWidth;
    private int speed;
    RoadType(float roadWidth, float outlineWidth, int speed) {
        this.roadWidth = roadWidth;
        this.outlineWidth = outlineWidth;
        this.speed = speed;
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
}
