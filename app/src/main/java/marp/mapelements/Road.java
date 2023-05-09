package marp.mapelements;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import marp.mapelements.details.MapColor;
import marp.mapelements.details.RoadType;

import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;

public class Road extends Element{
    ArrayList<RoadNode> nodes;

    boolean oneway;
    boolean roundabout;

    boolean driveable;
    boolean walkable;

    RoadType roadType;

    int speed;

    String name;

    private float[] boundingCoords = {Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY};
    
    public Road(long id, ArrayList<RoadNode> nodes, RoadType type, int speed, boolean oneway, boolean roundabout, String name) {
        this.id = id;
        this.nodes = nodes;
        this.roadType = type;
        this.speed = speed;
        this.oneway = oneway;
        this.roundabout = roundabout;
        this.name = name;

        if(this.roundabout) this.oneway = true;

        for (RoadNode roadNode : nodes) {
            if(roadNode.getX() < boundingCoords[0]){
                boundingCoords[0] = roadNode.getX();
            }
            if (roadNode.getX() > boundingCoords[2]){
                boundingCoords[2] = roadNode.getX();
            }
            if(roadNode.getY() < boundingCoords[1]){
                boundingCoords[1] = roadNode.getY();
            }
            if (roadNode.getY() > boundingCoords[3]){
                boundingCoords[3] = roadNode.getY();
            }
        }
    }

    public RoadType getRoadType() {
        return roadType;
    }
    public void setRoadType(RoadType roadType){
        this.roadType = roadType;
    }

    public String getName(){
        return this.name;
    }

    public long getID(){
        return this.id;
    }

    public void setOneWay(boolean oneway){
        this.oneway = oneway;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }
    
    private ArrayList<RoadNode> convertPointArrayToRoadNodeArray(ArrayList<Point> nodes){
        ArrayList<RoadNode> roadNodes = new ArrayList<>();
        for (Point node : nodes) {
            roadNodes.add(new RoadNode(node));
        }
        return roadNodes;
    }

    public ArrayList<RoadNode> getNodes(){
        return nodes;
    }

    public RoadNode getNode(int index){
        return nodes.get(index);
    }

    public int getNodeSize(){
        return nodes.size();
    }

    public boolean isOneWay(){
        return this.oneway;
    }

    public boolean isRoundabout(){
        return this.roundabout;
    }

    public boolean isDriveable(){
        return this.roadType.isDriveable();
    }

    public boolean isWalkable(){
        return this.roadType.isWalkable();
    }

    public int getSpeed(){
        return this.speed;
    }

    public void draw(GraphicsContext gc, double zoom){

        gc.setLineWidth((zoom * roadType.getRoadWidth()));
        gc.setStroke(MapColor.getInstance().colorMap.get(roadType.toString()));
        draw(gc,1, zoom);
    }

    public void drawOutline(GraphicsContext gc, double zoom) {
        gc.setLineWidth(zoom*roadType.getOutlineWidth());
        gc.setStroke(MapColor.getInstance().colorMap.get(roadType.toString()+"_OUTLINE"));
        draw(gc,1, zoom);
    }
    public void drawClose(GraphicsContext gc){
        gc.setLineWidth((0.00002 + 0.000005 * roadType.getRoadWidth()));
        gc.setStroke(MapColor.getInstance().colorMap.get(roadType.toString()));
        draw(gc,1, 1);
    }
    public void drawCloseOutline(GraphicsContext gc){
        gc.setLineWidth((0.000025 + 0.000005 * roadType.getRoadWidth()));
        gc.setStroke(MapColor.getInstance().colorMap.get(roadType.toString()+"_OUTLINE"));
        draw(gc,1, 1);
    }

    public void drawName(GraphicsContext gc, double zoom){
        if (name != null) {
            double firstX = nodes.get(0).x;
            double firstY = nodes.get(0).y;
            double lastX = nodes.get(nodes.size()-1).x;
            double lastY = nodes.get(nodes.size()-1).y;
            double middleX = nodes.get(nodes.size()/2).x;
            double middleY = nodes.get(nodes.size()/2).y;
            //determine if road is too short to have road displayed in order to avoid showing too many names:
            double distanceX = lastX - firstX;
            double distanceY = lastY - firstY;
            double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
            if (distance > 0.0005) {
                gc.setFill(Color.rgb(30, 30, 30, 1));
                gc.setStroke(Color.WHITE);
                gc.setLineWidth(zoom * 2);
                gc.setFont(Font.font("Helvetica Neue", zoom * 10));

                //calculate angle from first and last node to get approximation of slope of road
                double angle = Math.toDegrees(Math.atan2(lastY - firstY, lastX - firstX));
                if (angle > 90) {
                    angle = angle - 180;
                } else if (angle < -90) {
                    angle = angle + 180;
                }

                //Save the current state of the graphics context
                gc.save();

                //Translate the gc to the middleX and middleY coordinates and rotate by angle
                gc.translate(middleX, middleY);
                gc.rotate(angle);


                // Calculates the dimensions of the text
                Text text = new Text(name);
                text.setFont(Font.font("Helvetica Neue", zoom * 10));
                double textWidth = text.getLayoutBounds().getWidth();
                double textHeight = text.getLayoutBounds().getHeight();

                // Translates the gc back to the original position
                gc.translate(-textWidth / 2, textHeight / 2);

                // Draws the rotated text at (0, 0) in the translated gc
                gc.strokeText(name, 0, 0);
                gc.fillText(name, 0, 0);

                gc.restore(); // Restores the saved state of the graphics context
            }
        }
    }
    @Override
    public void draw(GraphicsContext gc, int levelOfDetail, double zoom){
        if(levelOfDetail>nodes.size()) return;
        for (int i = 0; i < nodes.size()-levelOfDetail; i+=levelOfDetail) {
            gc.strokeLine(nodes.get(i).getX(), nodes.get(i).getY(), nodes.get(i+levelOfDetail).getX(), nodes.get(i+levelOfDetail).getY());
        }
        gc.strokeLine(nodes.get(nodes.size()-levelOfDetail).getX(), nodes.get(nodes.size()-levelOfDetail).getY(), nodes.get(nodes.size()-1).getX(), nodes.get(nodes.size()-1).getY());
    }

    @Override
    public float[] getBounds() {
        return boundingCoords;
    }
    @Override
    public void drawBounds(GraphicsContext gc, float zoom) {

    }
}

