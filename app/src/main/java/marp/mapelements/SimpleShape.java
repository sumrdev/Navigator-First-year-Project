package marp.mapelements;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class SimpleShape extends Element {
    private long id;
    private String type;
    private float[] x;
    private float[] y;
    private String role;
    private float[] boundingCoords = {Float.MAX_VALUE, Float.MAX_VALUE, Float.MIN_VALUE, Float.MIN_VALUE};

    protected SimpleShape(Long id){
        this.id = id;
    }

    public SimpleShape(Long id, String type, float[] x, float[] y){
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
    
    public Point2D getFirst(){
        return new Point2D(this.x[0], this.y[0]);
    }

    public Point2D getLast(){
        return new Point2D(this.x[this.x.length-1], this.y[this.y.length-1]);
    }

    public long getID(){
        return this.id;
    }

    public String getType(){
        return this.type;
    }

    public String getRole(){
        return this.role;
    }

    public void setRole(String role){
        this.role = role;
    }

    @Override
    public void draw(GraphicsContext gc, int levelOfDetail, int zoom) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'draw'");
    }

    @Override
    public float[] getBounds() {
        return this.boundingCoords;
    }
}