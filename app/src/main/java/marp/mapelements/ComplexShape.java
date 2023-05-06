package marp.mapelements;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import marp.mapelements.details.ShapeType;

public class ComplexShape extends Element {
    private long id;
    private ShapeType type;
    private ArrayList<SimpleShape> elements;

    public ComplexShape(long id, ShapeType type, ArrayList<SimpleShape> elements){
        this.id = id;
        this.type = type;
        this.elements = elements;

    }

    private void orderAndFlipWays(){
        ArrayList<SimpleShape> orderedElements = new ArrayList<SimpleShape>();
        HashMap<Point2D, SimpleShape> coordsMap = new HashMap<>();

        for (SimpleShape simpleShape : this.elements) {
            if(simpleShape.getRole() != null){
                if(simpleShape.getRole().equals("outer")){
                    orderedElements.add(simpleShape);
                    continue;
                }
            }
            SimpleShape containsFirstCoordsInWay = coordsMap.get(simpleShape.getFirst());
            SimpleShape containsLastCoordsInWay = coordsMap.get(simpleShape.getLast());
            SimpleShape currentElement = null;

            if(containsFirstCoordsInWay!=null){
                if(simpleShape.getFirst().equals(containsFirstCoordsInWay.getFirst())){
                    currentElement = SimpleShape.merge(containsFirstCoordsInWay, simpleShape);
                    currentElement.flip();
                }
                else if(simpleShape.getFirst().equals(containsFirstCoordsInWay.getLast())){
                    currentElement = SimpleShape.merge(containsFirstCoordsInWay, simpleShape);
                }
            }
            else if(containsLastCoordsInWay!=null){
                if(simpleShape.getLast().equals(containsLastCoordsInWay.getFirst())){
                    currentElement = SimpleShape.merge(simpleShape, containsLastCoordsInWay);
                }
                else if(simpleShape.getLast().equals(containsLastCoordsInWay.getLast())){
                    currentElement = SimpleShape.merge(simpleShape, containsLastCoordsInWay);
                    currentElement.flip();
                }
            }
            if(currentElement != null){
                coordsMap.put(currentElement.getFirst(), currentElement);
                coordsMap.put(currentElement.getLast(), currentElement);
            }
            else{
                coordsMap.put(simpleShape.getFirst(), simpleShape);
                coordsMap.put(simpleShape.getLast(), simpleShape);
            }
        }
        coordsMap.forEach((coord, simpleShape)->{
            if(simpleShape.getLast().equals(coord)){
                orderedElements.add(simpleShape);
            }
        });
        this.elements = orderedElements;
    }

    @Override
    public void draw(GraphicsContext gc, int levelOfDetail, double zoom) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'draw'");
    }

    @Override
    public float[] getBounds() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBounds'");
    }
}
