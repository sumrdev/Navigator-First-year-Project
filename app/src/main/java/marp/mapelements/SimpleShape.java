package marp.mapelements;

import javafx.scene.canvas.GraphicsContext;

public class SimpleShape extends Element {
    private long id;
    private String type;
    private float[] x;
    private float[] y;
    private String role;

    protected SimpleShape(Long id){
        this.id = id;
    }

    public SimpleShape(Long id, String type, float[] x, float[] y){
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
    public void draw(GraphicsContext gc, int levelOfDetail, int zoom) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'draw'");
    }

    @Override
    public double[] getBounds() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBounds'");
    }
}
