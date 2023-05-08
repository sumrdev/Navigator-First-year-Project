package marp.mapelements;

import javafx.scene.canvas.GraphicsContext;
import marp.mapelements.details.PointType;

public class PointOfInterest extends Point implements MapPoint {

    String name;
    //The type of the landmark ie. "Library", "Memorial", etc.
    PointType type;
    boolean isFavourite;

    public PointOfInterest(String name, PointType type, float x, float y, boolean isFavourite){
        super(x, y);
        this.name = name;
        this.type = type;
        this.isFavourite = isFavourite;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PointType getType() {
        return type;
    }

    @Override
    public void setType(PointType newType) {
        type = newType;
    }

    public void draw(GraphicsContext gc, double size) {
        if (isFavourite) {
            gc.drawImage(PointType.FAVOURITE.icon, this.x - (size) / 2, this.y - (size) / 2, size, size);
        } else {
            gc.drawImage(type.icon, this.x - (size) / 2, this.y - (size) / 2, size, size);
        }
    }
    @Override
    public float getX() {
        return x;
    }
    @Override
    public float getY() {
        return y;
    }
    @Override
    public boolean getFavouriteStatus() {
        return isFavourite;
    }
    @Override
    public void setFavouriteStatus(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }
}

