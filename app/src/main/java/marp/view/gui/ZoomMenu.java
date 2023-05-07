package marp.view.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import marp.view.gui.MapLabelSmall;
import marp.view.gui.buttons.MapTextButton;

public class ZoomMenu extends VBox {
    double zoomlevel;
    public double getZoomlevel() {
        return zoomlevel;
    }

    MapLabelSmall zoomLevelLabel;

    public MapTextButton zoomIn;
    public MapTextButton zoomOut;

    // Distance line
    public Line distanceLine;
    float zoomMultiplier = 1;

    //Since the navigation is handled in view, we
    public ZoomMenu(double zoomlevel) {
        this.zoomlevel = zoomlevel;

        distanceLine = new Line(100, 150, 200, 150);
        distanceLine.setStrokeWidth(2.5);

        // Create zoom level label
        zoomLevelLabel = new MapLabelSmall("Distance: " + zoomlevel);

        // Zoom in button
        zoomIn = new MapTextButton("+");
        zoomIn.setMaxSize(48, 48);

        // Zoom out button
        zoomOut = new MapTextButton("-");
        zoomOut.setMaxSize(48, 48);

        this.getChildren().addAll(zoomIn, zoomOut, zoomLevelLabel, distanceLine);
        this.setPadding(new Insets(5));
        this.setSpacing(5);
        this.setAlignment(Pos.BOTTOM_RIGHT);

        //limit menu size
        this.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);

        // Set the mouse transparent property of the map menu only for transparent pixels
        this.setPickOnBounds(false);
    }

    public void updateZoomLevel(double newZoomValue){
        this.zoomlevel /= newZoomValue;

        if (this.zoomlevel > 1000) {
            zoomLevelLabel.setText("Distance: " + (String.format("%.2f", this.zoomlevel / 1000) + " km"));
        } else {
            zoomLevelLabel.setText("Distance: " + String.format("%.2f", this.zoomlevel) + " m");
        }
    }

    public void resetZoomLevel(){
        this.zoomlevel = 100;
        zoomLevelLabel.setText("Distance: " + String.format("%.2f", this.zoomlevel) + " m");
    }

    public void changeDistanceLine(double newEndX){
        distanceLine.setEndX(newEndX);
    }

    public void setDistance(double dist){
        if(dist > 1000) {
            dist /= 10;
        }

        this.zoomlevel = dist;
        zoomLevelLabel.setText("Distance: " + String.format("%.2f", dist) + " m");
    }
    public float getZoomMultiplier() {
        return zoomMultiplier;
    }
    public void setZoomMultiplier(float newZoomMultiplier) {
        zoomMultiplier = newZoomMultiplier;
    }
}
