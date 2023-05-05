package marp.mapelements;

public interface MapPoint {
    String getName();
    String getType();
    String getDisplayedType();
    void setType(String newType);

    float getX();

    float getY();
}

