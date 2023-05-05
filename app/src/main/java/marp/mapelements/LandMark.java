package marp.mapelements;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.Objects;

public class LandMark extends Point implements MapPoint{


    String name;
    //The type of the landmark ie. "Library", "Memorial", etc.
    String type;
    String displayedType;

    public LandMark(String name, String type, String displayedType, float x, float y){
        super(x, y);
        this.name = name;
        this.type = type;
        this.displayedType = displayedType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return type;
    }
    @Override
    public String getDisplayedType() {
        return displayedType;
    }

    @Override
    public void setType(String newType) {
        type = newType;
    }

    public void draw(GraphicsContext gc, float size) {

    }
    
    @Override
    public float getX() {
        return x;
    }
    @Override
    public float getY() {
        return y;
    }
}