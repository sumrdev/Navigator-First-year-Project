package marp.mapelements;

import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;


public abstract class Element implements Serializable{
    protected long id;
    public String type;
    public abstract void draw(GraphicsContext gc, int levelOfDetail, double zoom);
    public abstract float[] getBounds();
    public abstract void drawBounds(GraphicsContext gc, float zoom);
}
