package marp.mapelements;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;

public class ComplexShape extends Element {

    public ComplexShape(long id, String type, ArrayList<SimpleShape> elements){

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
