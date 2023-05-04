package marp.mapelements;

import javafx.scene.canvas.GraphicsContext;

public class SimpleElement extends Element {
    private long id;
    private String type;
    private float[] x;
    private float[] y;
    private String role;

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

    public void setRole(String role){
        this.role = role;
    }

    @Override
    public void draw(GraphicsContext gc) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'draw'");
    }
}
