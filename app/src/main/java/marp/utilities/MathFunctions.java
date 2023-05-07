package marp.utilities;

import java.util.HashMap;

import marp.datastructures.Edge;
import marp.mapelements.RoadNode;

public class MathFunctions {
    public static double distanceInMeters(float x1, float y1, float x2, float y2){
        double dx1 = x1/0.56;
        double dy1 = y1;
        double dx2 = x2/0.56;
        double dy2 = y2;

        int earthRadius = 6_371_000;
        dy1 = dy1 * Math.PI/180f;
        dy2 = dy2 * Math.PI/180f;
        double diffY = dy2-dy1;
        double diffX = (dx2-dx1) * Math.PI/180;
        double angle = Math.sin(diffY/2) * Math.sin(diffY/2) + Math.cos(y1) * Math.cos(y2) * Math.sin(diffX/2) * Math.sin(diffX/2);
        double distanceInMeters = earthRadius * 2 * Math.atan2(Math.sqrt(angle),Math.sqrt(1-angle)); 
        float distance = (float) distanceInMeters;
        return distance;
    }

    public static int getAngleBetweenTwoLines(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4){
        float angle1 = (float) Math.atan2(y1 - y2, x1 - x2);
        float angle2 = (float) Math.atan2(y3 - y4, x3 - x4);
        float angle = (float) Math.toDegrees(angle1-angle2);

        return (int) angle;
    }    
}