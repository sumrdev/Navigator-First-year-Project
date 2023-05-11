package marp.mapelements;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import marp.color.MapColor;
import marp.mapelements.details.FontSize;
import marp.mapelements.details.ShapeType;

import java.util.ArrayList;

public class SimpleShape extends Element {
    private ShapeType type;
    protected float[] x;
    protected float[] y;
    private String role;
    private float[] boundingCoords = { Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY,
            Float.NEGATIVE_INFINITY };

    public SimpleShape(ShapeType type, float[] x, float[] y) {
        this.type = type;
        this.x = x;
        this.y = y;

        for (int i = 0; i < x.length; i++) {
            if (x[i] < boundingCoords[0]) {
                boundingCoords[0] = x[i];
            }
            if (x[i] > boundingCoords[2]) {
                boundingCoords[2] = x[i];
            }
            if (y[i] < boundingCoords[1]) {
                boundingCoords[1] = y[i];
            }
            if (y[i] > boundingCoords[3]) {
                boundingCoords[3] = y[i];
            }
        }
    }

    public SimpleShape(ShapeType type, ArrayList<RoadNode> nodes) {
        this.type = type;
        this.x = new float[nodes.size()];
        this.y = new float[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            x[i] = nodes.get(i).getX();
            y[i] = nodes.get(i).getY();
        }
        for (int i = 0; i < x.length; i++) {
            if (x[i] < boundingCoords[0]) {
                boundingCoords[0] = x[i];
            }
            if (x[i] > boundingCoords[2]) {
                boundingCoords[2] = x[i];
            }
            if (y[i] < boundingCoords[1]) {
                boundingCoords[1] = y[i];
            }
            if (y[i] > boundingCoords[3]) {
                boundingCoords[3] = y[i];
            }
        }
    }

    public void flip() {
        float[] newX = new float[this.x.length];
        float[] newY = new float[this.y.length];

        for (int i = 0; i < this.x.length; i++) {
            newX[i] = this.x[this.x.length - 1 - i];
            newY[i] = this.y[this.y.length - 1 - i];
        }

        this.x = newX;
        this.y = newY;
    }

    public static SimpleShape merge(SimpleShape s1, SimpleShape s2) {
        float[] newX = new float[s1.x.length + s2.x.length];
        float[] newY = new float[s1.y.length + s2.y.length];

        for (int i = 0; i < s1.x.length; i++) {
            newX[i] = s1.x[i];
            newY[i] = s1.y[i];
        }

        for (int i = 0; i < s2.x.length; i++) {
            newX[i + s1.x.length] = s2.x[i];
            newY[i + s1.y.length] = s2.y[i];
        }

        return new SimpleShape(s1.type, newX, newY);
    }

    public static SimpleShape mergeThreeWays(SimpleShape s1, SimpleShape s2, SimpleShape s3) {
        int length = 0;
        if (s1 != null)
            length += s1.x.length;
        if (s2 != null)
            length += s2.x.length;
        if (s3 != null)
            length += s3.x.length;

        float[] newX = new float[length];
        float[] newY = new float[length];

        int offsetIndex = 0;
        if (s1 != null) {
            for (int i = 0; i < s1.x.length; i++) {
                newX[i] = s1.x[i];
                newY[i] = s1.y[i];
            }
            offsetIndex += s1.x.length;
        }
        if (s2 != null) {
            for (int i = 0; i < s2.x.length; i++) {
                newX[i + offsetIndex] = s2.x[i];
                newY[i + offsetIndex] = s2.y[i];
            }
            offsetIndex += s2.x.length;
        }
        if (s3 != null) {
            for (int i = 0; i < s3.x.length; i++) {
                newX[i + offsetIndex] = s3.x[i];
                newY[i + offsetIndex] = s3.y[i];
            }
        }
        return new SimpleShape(s2.type, newX, newY);
    }

    public Point2D getFirst() {
        return new Point2D(this.x[0], this.y[0]);
    }

    public Point2D getLast() {
        return new Point2D(this.x[this.x.length - 1], this.y[this.y.length - 1]);
    }

    public ShapeType getType() {
        return this.type;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public void draw(GraphicsContext gc, int levelOfDetail, double zoom) {
        // drawBounds(gc);
        gc.setFill(MapColor.getInstance().colorMap.get(type.toString()));
        if (levelOfDetail > x.length)
            return;
        gc.beginPath();
        gc.moveTo(x[0], y[0]);
        for (int i = 1; i < x.length - levelOfDetail; i += levelOfDetail) {
            gc.lineTo(x[i], y[i]);
        }
        gc.closePath();
        gc.fill();
    }

    public void drawLine(GraphicsContext gc, double zoom) {
        gc.setStroke(MapColor.getInstance().colorMap.get(type.toString()));
        gc.setLineWidth(1 * zoom);
        gc.beginPath();
        gc.moveTo(x[0], y[0]);
        for (int i = 1; i < x.length; i++) {
            gc.lineTo(x[i], y[i]);
        }
        gc.stroke();
    }

    @Override
    public float[] getBounds() {
        return this.boundingCoords;
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
