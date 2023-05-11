package marp;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import marp.mapelements.Point;
import marp.utilities.MathFunctions;

public class utilitiesTest {
    @Test
    public void distanceInMetersTest(){
        MathFunctions functions = new MathFunctions();

        Point point1 = new Point((float)55.0071690, (float)11.9125322);
        Point point2 = new Point((float)55.0072456, (float)11.9124224);
        float testFloat = functions.distanceInMeters(point1.getX(),point1.getY(), point2.getX(),point2.getY());
        //testing diameter of Gåsetårnet in Vordingborg
        Assertions.assertTrue((float)10 < testFloat && testFloat < (float) 11);

        point1 = new Point((float)55.0071690, (float)-11.9125322);
        point2 = new Point((float)55.0072456, (float)-11.9124224);
        testFloat = functions.distanceInMeters(point1.getX(),point1.getY(),point2.getX(),point2.getY());
        //testing diameter of Gåsetårnet in Vordingborg, some negative floats
        Assertions.assertTrue((float)10 < testFloat && testFloat < (float) 11);

        point1 = new Point((float)-55.0071690, (float)-11.9125322);
        point2 = new Point((float)-55.0072456, (float)-11.9124224);
        testFloat = functions.distanceInMeters(point1.getX(),point1.getY(),point2.getX(),point2.getY());
        //testing diameter of Gåsetårnet in Vordingborg, negative floats
        Assertions.assertTrue((float)10 < testFloat && testFloat < (float) 11);

        point1 = new Point((float)57.7259703, (float)10.5844602);
        point2 = new Point((float)54.8295003, (float)9.3606529);
        testFloat = functions.distanceInMeters(point1.getX(),point1.getY(),point2.getX(),point2.getY());
        System.out.println(testFloat);
        //testing distance from Skagen to Padborg (from center in OSM, where their names are written on the map)
        Assertions.assertTrue((float)330000 < testFloat && testFloat < (float) 340000);

    }
    @Test
    public void getAngleBetweenLinesTest(){
        MathFunctions functions = new MathFunctions();
        Point point1 = new Point(1,1);
        Point point2 = new Point(2,1);
        Point point3 = new Point(1,2);
        Point point4 = new Point(0,1);
        Point point5 = new Point(1,0);
        Assertions.assertEquals(0, functions.getAngleBetweenTwoLines(point1.getX(),point1.getY(), point2.getX(), point2.getY(), point1.getX(),point1.getY(), point2.getX(),point2.getY()));
        Assertions.assertEquals(90, functions.getAngleBetweenTwoLines(point1.getX(),point1.getY(), point2.getX(), point2.getY(), point1.getX(),point1.getY(), point5.getX(),point5.getY()));
        Assertions.assertEquals(180, functions.getAngleBetweenTwoLines(point1.getX(),point1.getY(), point2.getX(), point2.getY(), point1.getX(),point1.getY(), point4.getX(),point4.getY()));
        Assertions.assertEquals(270, functions.getAngleBetweenTwoLines(point1.getX(),point1.getY(), point2.getX(), point2.getY(), point1.getX(),point1.getY(), point3.getX(),point3.getY()));

        point1 = new Point(0,0);
        point2 = new Point(1,0);
        point3 = new Point(0,1);
        point4 = new Point(-1,0);
        point5 = new Point(0, -1);
        Assertions.assertEquals(0, functions.getAngleBetweenTwoLines(point1.getX(),point1.getY(), point2.getX(), point2.getY(), point1.getX(),point1.getY(), point2.getX(),point2.getY()));
        Assertions.assertEquals(90, functions.getAngleBetweenTwoLines(point1.getX(),point1.getY(), point2.getX(), point2.getY(), point1.getX(),point1.getY(), point5.getX(),point5.getY()));
        Assertions.assertEquals(180, functions.getAngleBetweenTwoLines(point1.getX(),point1.getY(), point2.getX(), point2.getY(), point1.getX(),point1.getY(), point4.getX(),point4.getY()));
        Assertions.assertEquals(270, functions.getAngleBetweenTwoLines(point1.getX(),point1.getY(), point2.getX(), point2.getY(), point1.getX(),point1.getY(), point3.getX(),point3.getY()));

        point1 = new Point(-3 ,-3);
        point2 = new Point(-2,-3);
        point3 = new Point(-3,-2);
        point4 = new Point(-4,-3);
        point5 = new Point(-3, -4);
        Assertions.assertEquals(0, functions.getAngleBetweenTwoLines(point1.getX(),point1.getY(), point2.getX(), point2.getY(), point1.getX(),point1.getY(), point2.getX(),point2.getY()));
        Assertions.assertEquals(90, functions.getAngleBetweenTwoLines(point1.getX(),point1.getY(), point2.getX(), point2.getY(), point1.getX(),point1.getY(), point5.getX(),point5.getY()));
        Assertions.assertEquals(180, functions.getAngleBetweenTwoLines(point1.getX(),point1.getY(), point2.getX(), point2.getY(), point1.getX(),point1.getY(), point4.getX(),point4.getY()));
        Assertions.assertEquals(270, functions.getAngleBetweenTwoLines(point1.getX(),point1.getY(), point2.getX(), point2.getY(), point1.getX(),point1.getY(), point3.getX(),point3.getY()));

        point1 = new Point(1,1);
        point2 = new Point(2,1);
        point3 = new Point(1,100);
        point4 = new Point(0,1);
        point5 = new Point(1,0);
        Assertions.assertEquals(0, functions.getAngleBetweenTwoLines(point1.getX(),point1.getY(), point2.getX(), point2.getY(), point1.getX(),point1.getY(), point2.getX(),point2.getY()));
        Assertions.assertEquals(90, functions.getAngleBetweenTwoLines(point1.getX(),point1.getY(), point2.getX(), point2.getY(), point1.getX(),point1.getY(), point5.getX(),point5.getY()));
        Assertions.assertEquals(180, functions.getAngleBetweenTwoLines(point1.getX(),point1.getY(), point2.getX(), point2.getY(), point1.getX(),point1.getY(), point4.getX(),point4.getY()));
        Assertions.assertEquals(270, functions.getAngleBetweenTwoLines(point1.getX(),point1.getY(), point2.getX(), point2.getY(), point1.getX(),point1.getY(), point3.getX(),point3.getY()));

        point1 = new Point(1,1);
        point2 = new Point(2,1);
        point3 = new Point(1,2);
        point4 = new Point(1,3);
        Assertions.assertEquals(270, functions.getAngleBetweenTwoLines(point1.getX(),point1.getY(), point2.getX(), point2.getY(), point3.getX(),point3.getY(), point4.getX(),point4.getY()));

    }
}
