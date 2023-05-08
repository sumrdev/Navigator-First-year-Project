package marp.mapelements;

import marp.mapelements.details.PointType;

public interface MapPoint {
    String getName();
    PointType getType();
    void setType(PointType newType);

    float getX();

    float getY();

    boolean getFavouriteStatus();

    void setFavouriteStatus(boolean isFavourite);
}

