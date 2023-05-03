package marp.mapelements;

import javafx.scene.canvas.GraphicsContext;

public class SinglePointElement extends Element {
    long id;
    float x;
    float y;

    public SinglePointElement(long id, float x, float y){
        this.id = id;
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
    public void draw(GraphicsContext gc) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'draw'");
    }
}
