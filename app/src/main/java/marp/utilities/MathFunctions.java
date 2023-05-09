package marp.utilities;

public class MathFunctions {
    public static float distanceInMeters(float x1, float y1, float x2, float y2){
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(x2-x1);
        double dLng = Math.toRadians(y2-y1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(x1)) * Math.cos(Math.toRadians(x2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return (float)(earthRadius * c);
    }
    public static int getAngleBetweenTwoLines(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4){
        float angle1 = (float) Math.atan2(y1 - y2, x1 - x2);
        float angle2 = (float) Math.atan2(y3 - y4, x3 - x4);
        float angle = (float) Math.toDegrees(angle1-angle2);

        return (int) angle;
    }    
}