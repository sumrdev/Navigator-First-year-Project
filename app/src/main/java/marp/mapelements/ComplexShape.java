package marp.mapelements;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.FillRule;
import marp.mapelements.details.MapColor;
import marp.mapelements.details.ShapeType;

public class ComplexShape extends Element {
    private long id;
    private ShapeType type;
    private ArrayList<SimpleShape> elements;
    private ArrayList<SimpleShape> outerElements;
    private ArrayList<SimpleShape> innerElements;
    private float[] boundingCoords;

    public ComplexShape(long id, ShapeType type, ArrayList<SimpleShape> elements){
        this.id = id;
        this.type = type;
        this.elements = elements;
        outerElements = new ArrayList<>();
        innerElements = new ArrayList<>();
        orderAndFlipWays();
        findInnerAndOuterElements();

        float[] bounds;
        bounds = new float[]{Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY};
        for (SimpleShape outerElement : outerElements) {
            for (int i = 0; i < outerElement.x.length; i++) {
                if(outerElement.x[i] < bounds[0]){
                    bounds[0] = outerElement.x[i];
                }
                if(outerElement.y[i] < bounds[1]){
                    bounds[1] = outerElement.y[i];
                }
                if(outerElement.x[i] > bounds[2]){
                    bounds[2] = outerElement.x[i];
                }
                if(outerElement.y[i] > bounds[3]){
                    bounds[3] = outerElement.y[i];
                }
            }
        }
        this.boundingCoords = bounds;
    }
    private void findInnerAndOuterElements(){
        for (SimpleShape simpleShape : elements) {
            if(simpleShape.getRole()==null || simpleShape.getRole().equals("outer")){
                outerElements.add(simpleShape);
            }else if(simpleShape.getRole().equals("inner")){
                innerElements.add(simpleShape);
            }
        }
    }

    private void orderAndFlipWays() {
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
    public void draw(GraphicsContext gc, int levelOfDetail, double zoom){
        System.out.println("TEST !");
        gc.setFillRule(FillRule.EVEN_ODD);
        gc.setFill(MapColor.getInstance().colorMap.get(this.type.toString()));
        gc.beginPath();

        for (int i = outerElements.size()-1; i >= 0; i--) {
            SimpleShape outerElement = outerElements.get(i);
            gc.moveTo(outerElement.x[0], outerElement.y[0]);
            for (int j = outerElement.x.length-1; j > 0; j--) {
                gc.lineTo(outerElement.x[j], outerElement.y[j]);
            }
        }
        for (SimpleShape innerElement : innerElements) {
            gc.moveTo(innerElement.x[0], innerElement.y[0]);
            for (int i = 1; i < innerElement.x.length; i++) {
                gc.lineTo(innerElement.x[i], innerElement.y[i]);
            }
        }
        gc.closePath();
        gc.fill();
    }

    @Override
    public float[] getBounds() {
        return boundingCoords;
    }
}
