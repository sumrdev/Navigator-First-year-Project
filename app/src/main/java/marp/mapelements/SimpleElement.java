package marp.mapelements;

import javafx.scene.canvas.GraphicsContext;

public class SimpleElement extends Element {
    private long id;
    private String type;
    private float[] x;
    private float[] y;

    protected SimpleElement(Long id){
        this.id = id;
    }

    public SimpleElement(Long id, String type, float[] x, float[] y){
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;

    }

    public long getID(){
        return this.id;
    }

    @Override
    public void draw(GraphicsContext gc) {
        throw new UnsupportedOperationException("Unimplemented method 'draw'");
    }
    
}
