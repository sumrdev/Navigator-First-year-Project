package marp.mapelements;

import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Point extends Element {
    long id;
    float x;
    float y;

    public Point(long id, float x, float y){
        this.id = id;
        this.x = 0.56f*x;
        this.y = -y;
    }

    public Point(float x, float y){
        this.x = 0.56f*x;
        this.y = -y;
    }

    public long getID(){
        return this.id;
    }

    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }

    @Override
    public void draw(GraphicsContext gc, int levelOfDetail, int zoom) {
        gc.setFill(Color.RED);
        gc.fillOval(x, y, 0.001, 0.001);
    }

    @Override
    public float[] getBounds() {
        return new float[]{x,y,x,y};
    }
}
