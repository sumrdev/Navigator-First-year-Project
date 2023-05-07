package marp.mapelements;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import marp.mapelements.details.MapColor;
import marp.mapelements.details.ShapeType;

import java.util.Arrays;
import java.util.Objects;

public class SimpleShape extends Element {
    private ShapeType type;
    protected float[] x;
    protected float[] y;
    private String role;
    private float[] boundingCoords = {Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY};

    protected SimpleShape(Long id){
        this.id = id;
    }

    public SimpleShape(Long id, ShapeType type, float[] x, float[] y){
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;

        for (int i = 0; i < x.length; i++) {
            if(x[i] < boundingCoords[0]){
                boundingCoords[0] = x[i];
            }
            if (x[i] > boundingCoords[2]){
                boundingCoords[2] = x[i];
            }
            if(y[i] < boundingCoords[1]){
                boundingCoords[1] = y[i];
            }
            if (y[i] > boundingCoords[3]){
                boundingCoords[3] = y[i];
            }
        }
    }

    public void flip(){
        float[] newX = new float[this.x.length];
        float[] newY = new float[this.y.length];

        for (int i = 0; i < this.x.length; i++) {
            newX[i] = this.x[this.x.length-1-i];
            newY[i] = this.y[this.y.length-1-i];
        }

        this.x = newX;
        this.y = newY;
    }

    public static SimpleShape merge(SimpleShape s1, SimpleShape s2){
        float[] newX = new float[s1.x.length + s2.x.length];
        float[] newY = new float[s1.y.length + s2.y.length];

        for (int i = 0; i < s1.x.length; i++) {
            newX[i] = s1.x[i];
            newY[i] = s1.y[i];
        }

        for (int i = 0; i < s2.x.length; i++) {
            newX[i+s1.x.length] = s2.x[i];
            newY[i+s1.y.length] = s2.y[i];
        }

        return new SimpleShape(s1.id, s1.type, newX, newY);
    }
    public static SimpleShape mergeThreeWays(SimpleShape s1, SimpleShape s2, SimpleShape s3) {
        int length = 0;
        if (s1 != null) length += s1.x.length;
        if (s2 != null) length += s2.x.length;
        if (s3 != null) length += s3.x.length;


        float[] newX = new float[length];
        float[] newY = new float[length];

        int offsetIndex = 0;
        if (s1 != null) {
            for (int i = 0; i < s1.x.length; i++) {
                newX[i] = s1.x[i];
                newY[i] = s1.y[i];
            }
            offsetIndex += s1.x.length;
        }
        if (s2 != null) {
            for (int i = 0; i < s2.x.length; i++) {
                newX[i + offsetIndex] = s2.x[i];
                newY[i + offsetIndex] = s2.y[i];
            }
            offsetIndex += s2.x.length;
        }
        if (s3 != null) {
            for (int i = 0; i < s3.x.length; i++) {
                newX[i + offsetIndex] = s3.x[i];
                newY[i + offsetIndex] = s3.y[i];
            }
        }
        return new SimpleShape(s2.id, s2.type, newX, newY);
    }
    
    public Point2D getFirst(){
        return new Point2D(this.x[0], this.y[0]);
    }

    public Point2D getLast(){
        return new Point2D(this.x[this.x.length-1], this.y[this.y.length-1]);
    }

    public long getID(){
        return this.id;
    }

    public ShapeType getType(){
        return this.type;
    }

    public String getRole(){
        return this.role;
    }

    public void setRole(String role){
        this.role = role;
    }

    @Override
    public void draw(GraphicsContext gc, int levelOfDetail, double zoom) {
        gc.setFill(MapColor.getInstance().colorMap.get(type.toString()));
        if(levelOfDetail>x.length) return;
        gc.beginPath();
        gc.moveTo(x[0], y[0]);
        for (int i = 1; i < x.length-levelOfDetail; i+=levelOfDetail) {
            gc.lineTo(x[i], y[i]);
        }
        gc.closePath();
        gc.fill();
    }

    @Override
    public float[] getBounds() {
        return this.boundingCoords;
    }
    public void drawBounds(GraphicsContext gc) {
        gc.beginPath();
        gc.moveTo(boundingCoords[0], boundingCoords[1]);
        gc.moveTo(boundingCoords[2], boundingCoords[1]);
        gc.moveTo(boundingCoords[2], boundingCoords[3]);
        gc.moveTo(boundingCoords[0], boundingCoords[3]);
        gc.closePath();
        gc.setFill(Color.PURPLE);
        gc.fill();
    }
}
