package marp.mapelements;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.FillRule;
import javafx.scene.text.Font;
import marp.color.MapColor;
import marp.mapelements.details.FontSize;
import marp.mapelements.details.ShapeType;

public class ComplexShape extends Element {
    private ShapeType type;
    private ArrayList<SimpleShape> elements;
    private ArrayList<SimpleShape> outerElements;
    private ArrayList<SimpleShape> innerElements;
    private float[] boundingCoords;

    public ComplexShape(ShapeType type, ArrayList<SimpleShape> elements) {
        this.type = type;
        this.elements = orderAndFlipWays(elements);
        outerElements = new ArrayList<>();
        innerElements = new ArrayList<>();
        findInnerAndOuterElements();

        float[] bounds;
        bounds = new float[] { Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY,
                Float.NEGATIVE_INFINITY };
        for (SimpleShape outerElement : outerElements) {
            for (int i = 0; i < outerElement.x.length; i++) {
                if (outerElement.x[i] < bounds[0]) {
                    bounds[0] = outerElement.x[i];
                }
                if (outerElement.y[i] < bounds[1]) {
                    bounds[1] = outerElement.y[i];
                }
                if (outerElement.x[i] > bounds[2]) {
                    bounds[2] = outerElement.x[i];
                }
                if (outerElement.y[i] > bounds[3]) {
                    bounds[3] = outerElement.y[i];
                }
            }
        }
        this.boundingCoords = bounds;
    }

    private void findInnerAndOuterElements() {
        for (SimpleShape simpleShape : elements) {
            if (simpleShape.getRole() == null || simpleShape.getRole().equals("outer")) {
                outerElements.add(simpleShape);
            } else if (simpleShape.getRole().equals("inner")) {
                innerElements.add(simpleShape);
            }
        }
    }

    public static ArrayList<SimpleShape> orderAndFlipWays(ArrayList<SimpleShape> elements) {
        ArrayList<SimpleShape> distinctSimpleShapes = new ArrayList<>();
        HashMap<Point2D, SimpleShape> coordsMap = new HashMap<>();

        for (SimpleShape simpleShape : elements) {

            SimpleShape containsFirstCoordsInWay = coordsMap.remove(simpleShape.getFirst());
            SimpleShape containsLastCoordsInWay = coordsMap.remove(simpleShape.getLast());

            // To avoid issues where the same way is located from both ends, set
            // containsLastCoordsInWay to null if they are the same.
            if (containsFirstCoordsInWay != null && containsLastCoordsInWay != null) {
                if (containsFirstCoordsInWay == containsLastCoordsInWay) {
                    containsLastCoordsInWay = null;
                }
            }
            if (containsFirstCoordsInWay != null) {
                if (simpleShape.getFirst().equals(containsFirstCoordsInWay.getFirst())) {
                    containsFirstCoordsInWay.flip();
                }
            }
            if (containsLastCoordsInWay != null) {
                if (simpleShape.getLast().equals(containsLastCoordsInWay.getLast())) {
                    containsLastCoordsInWay.flip();
                }
            }
            SimpleShape mergedShape = SimpleShape.mergeThreeWays(containsFirstCoordsInWay, simpleShape,
                    containsLastCoordsInWay);
            coordsMap.put(mergedShape.getFirst(), mergedShape);
            coordsMap.put(mergedShape.getLast(), mergedShape);

        }
        coordsMap.forEach((coord, simpleShape) -> {
            if (simpleShape.getLast().equals(coord)) {
                distinctSimpleShapes.add(simpleShape);
            }
        });
        return distinctSimpleShapes;
    }

    @Override
    public void draw(GraphicsContext gc, int levelOfDetail, double zoom) {
        // drawBounds(gc);
        gc.setFillRule(FillRule.EVEN_ODD);
        gc.setFill(MapColor.getInstance().colorMap.get(this.type.toString()));
        gc.beginPath();

        for (int i = outerElements.size() - 1; i >= 0; i--) {
            SimpleShape outerElement = outerElements.get(i);
            gc.moveTo(outerElement.x[0], outerElement.y[0]);
            for (int j = outerElement.x.length - 1; j > 0; j--) {
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

    public void drawBounds(GraphicsContext gc, float zoom) {
        gc.setFill(Color.PURPLE);
        gc.beginPath();
        gc.moveTo(boundingCoords[0], boundingCoords[1]);
        gc.lineTo(boundingCoords[2], boundingCoords[1]);
        gc.lineTo(boundingCoords[2], boundingCoords[3]);
        gc.lineTo(boundingCoords[0], boundingCoords[3]);
        gc.closePath();
        gc.fill();

        // Draw text at each corner
        gc.setStroke(Color.TRANSPARENT);
        gc.setFont(Font.font("Helvetica Neue", zoom * FontSize.QUITE_SMALL.getSize()));
        gc.setFill(Color.BLACK);
        gc.fillText("(" + boundingCoords[0] + ", " + boundingCoords[1] + ")", boundingCoords[0], boundingCoords[1]);
        gc.fillText("(" + boundingCoords[2] + ", " + boundingCoords[1] + ")", boundingCoords[2], boundingCoords[1]);
        gc.fillText("(" + boundingCoords[2] + ", " + boundingCoords[3] + ")", boundingCoords[2], boundingCoords[3]);
        gc.fillText("(" + boundingCoords[0] + ", " + boundingCoords[3] + ")", boundingCoords[0], boundingCoords[3]);
    }
}
