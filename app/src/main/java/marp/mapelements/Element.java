package marp.mapelements;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public abstract class Element {
    public String type;
    private Color color;
    public abstract void draw(GraphicsContext gc);
}
