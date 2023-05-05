package marp.mapelements;

import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public abstract class Element implements Serializable{
    public String type;
    private Color color;
    public abstract void draw(GraphicsContext gc, int levelOfDetail, int zoom);
    public abstract float[] getBounds();
}
