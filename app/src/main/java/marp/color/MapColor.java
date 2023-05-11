package marp.color;

import javafx.scene.paint.Color;
import marp.mapelements.details.RoadType;
import marp.mapelements.details.ShapeType;

import java.util.HashMap;

public class MapColor {
    private static MapColor instance = new MapColor();
    private HashMap<String, Color> defaultColorMap = new HashMap<String, Color>();
    private HashMap<String, Color> darkColorMap = new HashMap<String, Color>();
    private HashMap<String, Color> deuteranopiaBlindColorMap = new HashMap<String, Color>();
    private HashMap<String, Color> protanopiaBlindColorMap = new HashMap<String, Color>();
    private HashMap<String, Color> tritanBlindColorMap = new HashMap<String, Color>();
    private HashMap<String, Color> monochromaBlindColorMap = new HashMap<String, Color>();
    public HashMap<String, Color> colorMap = new HashMap<String, Color>();

    private MapColor() {
        defaultColorMap.put("BACKGROUND", Color.rgb(176, 211, 232));
        defaultColorMap.put("WATER", Color.rgb(176, 211, 232));
        defaultColorMap.put("WATER_close", Color.rgb(176, 211, 232));
        defaultColorMap.put("BUILDING", Color.rgb(190, 190, 190));
        defaultColorMap.put("BUILDING_close", Color.rgb(190, 190, 190));
        defaultColorMap.put("COASTLINE", Color.rgb(206, 234, 214));
        defaultColorMap.put("coastline_near", Color.rgb(255, 255, 255));
        defaultColorMap.put("coastline_far", Color.rgb(206, 234, 214));
        defaultColorMap.put("GRASS", Color.rgb(206, 234, 214));
        defaultColorMap.put("GRASS_close", Color.rgb(206, 234, 214));
        defaultColorMap.put("FOREST", Color.rgb(145, 189, 159));
        defaultColorMap.put("FOREST_close", Color.rgb(145, 189, 159));
        defaultColorMap.put("CEMENT", Color.rgb(233, 233, 233));
        defaultColorMap.put("CEMENT_close", Color.rgb(233, 233, 233));
        defaultColorMap.put("COMMERCIAL_GROUND", Color.rgb(225, 224, 212));
        defaultColorMap.put("COMMERCIAL_GROUND_close", Color.rgb(225, 224, 212));
        defaultColorMap.put("WATERWAY", Color.rgb(176, 211, 232));
        defaultColorMap.put("WATERWAY_close", Color.rgb(176, 211, 232));
        defaultColorMap.put("RAILWAY", Color.rgb(190, 190, 190));
        defaultColorMap.put("RAILWAY_close", Color.rgb(190, 190, 190));
        defaultColorMap.put("SECONDARY", Color.WHITE);
        defaultColorMap.put("SECONDARY_close", Color.WHITE);
        defaultColorMap.put("SECONDARY_OUTLINE", Color.LIGHTGRAY);
        defaultColorMap.put("SECONDARY_OUTLINE_close", Color.LIGHTGRAY);
        defaultColorMap.put("MOTORWAY", Color.YELLOW);
        defaultColorMap.put("MOTORWAY_close", Color.YELLOW);
        defaultColorMap.put("MOTORWAY_OUTLINE", Color.ORANGE);
        defaultColorMap.put("MOTORWAY_OUTLINE_close", Color.ORANGE);
        defaultColorMap.put("PEDESTRIAN", Color.rgb(230, 230, 230));
        defaultColorMap.put("PEDESTRIAN_close", Color.rgb(230, 230, 230));
        defaultColorMap.put("PEDESTRIAN_OUTLINE", Color.LIGHTGRAY);
        defaultColorMap.put("PEDESTRIAN_OUTLINE_close", Color.LIGHTGRAY);
        defaultColorMap.put("PRIMARY", Color.LIGHTYELLOW);
        defaultColorMap.put("PRIMARY_close", Color.LIGHTYELLOW);
        defaultColorMap.put("PRIMARY_OUTLINE", Color.YELLOW);
        defaultColorMap.put("PRIMARY_OUTLINE_close", Color.YELLOW);
        defaultColorMap.put("PATH", Color.GREEN);
        defaultColorMap.put("PATH_close", Color.GREEN);
        defaultColorMap.put("PATH_OUTLINE", Color.TRANSPARENT);
        defaultColorMap.put("PATH_OUTLINE_close", Color.TRANSPARENT);
        defaultColorMap.put("RESIDENTIAL", Color.WHITE);
        defaultColorMap.put("RESIDENTIAL_close", Color.WHITE);
        defaultColorMap.put("RESIDENTIAL_OUTLINE", Color.LIGHTGRAY);
        defaultColorMap.put("RESIDENTIAL_OUTLINE_close", Color.LIGHTGRAY);
        defaultColorMap.put("TERTIARY", Color.WHITE);
        defaultColorMap.put("TERTIARY_close", Color.WHITE);
        defaultColorMap.put("TERTIARY_OUTLINE", Color.LIGHTGRAY);
        defaultColorMap.put("TERTIARY_OUTLINE_close", Color.LIGHTGRAY);
        defaultColorMap.put("TEXT_OUTLINE", Color.WHITE);
        defaultColorMap.put("TEXT_OUTLINE_close", Color.WHITE);
        defaultColorMap.put("TEXT", Color.BLACK);
        defaultColorMap.put("TEXT_close", Color.BLACK);

        // Dark mode
        darkColorMap.put("BACKGROUND", Color.rgb(68, 87, 101,1));
        darkColorMap.put("WATER", Color.rgb(68, 87, 101,1));
        darkColorMap.put("WATER_close", Color.rgb(68, 87, 101,1));
        darkColorMap.put("BUILDING", Color.rgb(51, 51, 51,1));
        darkColorMap.put("BUILDING_close", Color.rgb(51, 51, 51,1));
        darkColorMap.put("COASTLINE", Color.rgb(83, 115, 99,1));
        darkColorMap.put("coastline_near", Color.rgb(115, 115, 115,1));
        darkColorMap.put("coastline_far", Color.rgb(83, 115, 99,1));
        darkColorMap.put("GRASS", Color.rgb(83, 115, 99,1));
        darkColorMap.put("GRASS_close", Color.rgb(83, 115, 99,1));
        darkColorMap.put("FOREST", Color.rgb(67, 94, 81,1));
        darkColorMap.put("FOREST_close", Color.rgb(67, 94, 81,1));
        darkColorMap.put("CEMENT", Color.rgb(105, 105, 105,1));
        darkColorMap.put("CEMENT_close", Color.rgb(105, 105, 105,1));
        darkColorMap.put("COMMERCIAL_GROUND", Color.rgb(105, 102, 96,1));
        darkColorMap.put("COMMERCIAL_GROUND_close", Color.rgb(105, 102, 96,1));
        darkColorMap.put("WATERWAY", Color.rgb(68, 87, 101,1));
        darkColorMap.put("WATERWAY_close", Color.rgb(68, 87, 101,1));
        darkColorMap.put("RAILWAY", Color.rgb(80, 80, 80,1));
        darkColorMap.put("RAILWAY_close", Color.rgb(80, 80, 80,1));
        darkColorMap.put("SECONDARY", Color.rgb(145, 145, 145,1));
        darkColorMap.put("SECONDARY_close", Color.rgb(145, 145, 145,1));
        darkColorMap.put("SECONDARY_OUTLINE", Color.rgb(105, 105, 105,1));
        darkColorMap.put("SECONDARY_OUTLINE_close", Color.rgb(105, 105, 105,1));
        darkColorMap.put("MOTORWAY", Color.rgb(112, 68, 68,1));
        darkColorMap.put("MOTORWAY_close", Color.rgb(112, 68, 68,1));
        darkColorMap.put("MOTORWAY_OUTLINE", Color.rgb(96, 51, 51,1));
        darkColorMap.put("MOTORWAY_OUTLINE_close", Color.rgb(96, 51, 51,1));
        darkColorMap.put("PEDESTRIAN", Color.rgb(105, 105, 105,1));
        darkColorMap.put("PEDESTRIAN_close", Color.rgb(105, 105, 105,1));
        darkColorMap.put("PEDESTRIAN_OUTLINE", Color.rgb(85, 85, 85,1));
        darkColorMap.put("PEDESTRIAN_OUTLINE_close", Color.rgb(85, 85, 85,1));
        darkColorMap.put("PRIMARY", Color.rgb(131, 103, 103,1));
        darkColorMap.put("PRIMARY_close", Color.rgb(131, 103, 103,1));
        darkColorMap.put("PRIMARY_OUTLINE", Color.rgb(112, 68, 68,1));
        darkColorMap.put("PRIMARY_OUTLINE_close", Color.rgb(112, 68, 68,1));
        darkColorMap.put("PATH", Color.rgb(96, 51, 51,1));
        darkColorMap.put("PATH_close", Color.rgb(96, 51, 51,1));
        darkColorMap.put("PATH_OUTLINE", Color.TRANSPARENT);
        darkColorMap.put("PATH_OUTLINE_close", Color.TRANSPARENT);
        darkColorMap.put("RESIDENTIAL", Color.rgb(145, 145, 145,1));
        darkColorMap.put("RESIDENTIAL_close", Color.rgb(145, 145, 145,1));
        darkColorMap.put("RESIDENTIAL_OUTLINE", Color.rgb(105, 105, 105,1));
        darkColorMap.put("RESIDENTIAL_OUTLINE_close", Color.rgb(105, 105, 105,1));
        darkColorMap.put("TERTIARY", Color.rgb(145, 145, 145,1));
        darkColorMap.put("TERTIARY_close", Color.rgb(145, 145, 145,1));
        darkColorMap.put("TERTIARY_OUTLINE", Color.rgb(105, 105, 105,1));
        darkColorMap.put("TERTIARY_OUTLINE_close", Color.rgb(105, 105, 105,1));
        darkColorMap.put("TEXT_OUTLINE", Color.rgb(51, 51, 51,1));
        darkColorMap.put("TEXT_OUTLINE_close", Color.rgb(51, 51, 51,1));
        darkColorMap.put("TEXT", Color.WHITE);
        darkColorMap.put("TEXT_close", Color.WHITE);


        // Green blindness
        deuteranopiaBlindColorMap.put("BACKGROUND", Color.rgb(187, 169, 149,1));
        deuteranopiaBlindColorMap.put("WATER", Color.rgb(187, 169, 149,1));
        deuteranopiaBlindColorMap.put("WATER_close", Color.rgb(187, 169, 149,1));
        deuteranopiaBlindColorMap.put("BUILDING", Color.rgb(190, 190, 190,1));
        deuteranopiaBlindColorMap.put("BUILDING_close", Color.rgb(190, 190, 190,1));
        deuteranopiaBlindColorMap.put("COASTLINE", Color.rgb(203, 196, 220,1));
        deuteranopiaBlindColorMap.put("coastline_near", Color.rgb(255, 255, 255,1));
        deuteranopiaBlindColorMap.put("coastline_far", Color.rgb(203, 196, 220,1));
        deuteranopiaBlindColorMap.put("GRASS", Color.rgb(203, 196, 220,1));
        deuteranopiaBlindColorMap.put("GRASS_close", Color.rgb(203, 196, 220,1));
        deuteranopiaBlindColorMap.put("FOREST", Color.rgb(167, 161, 190,1));
        deuteranopiaBlindColorMap.put("FOREST_close", Color.rgb(167, 161, 190,1));
        deuteranopiaBlindColorMap.put("CEMENT", Color.rgb(233, 233, 233,1));
        deuteranopiaBlindColorMap.put("CEMENT_close", Color.rgb(233, 233, 233,1));
        deuteranopiaBlindColorMap.put("COMMERCIAL_GROUND", Color.rgb(225, 224, 212,1));
        deuteranopiaBlindColorMap.put("COMMERCIAL_GROUND_close", Color.rgb(225, 224, 212,1));
        deuteranopiaBlindColorMap.put("WATERWAY", Color.rgb(187, 169, 149,1));
        deuteranopiaBlindColorMap.put("WATERWAY_close", Color.rgb(187, 169, 149,1));
        deuteranopiaBlindColorMap.put("RAILWAY", Color.rgb(190, 190, 190,1));
        deuteranopiaBlindColorMap.put("RAILWAY_close", Color.rgb(190, 190, 190,1));
        deuteranopiaBlindColorMap.put("SECONDARY", Color.WHITE);
        deuteranopiaBlindColorMap.put("SECONDARY_close", Color.WHITE);
        deuteranopiaBlindColorMap.put("SECONDARY_OUTLINE", Color.LIGHTGRAY);
        deuteranopiaBlindColorMap.put("SECONDARY_OUTLINE_close", Color.LIGHTGRAY);
        deuteranopiaBlindColorMap.put("MOTORWAY", Color.YELLOW);
        deuteranopiaBlindColorMap.put("MOTORWAY_close", Color.YELLOW);
        deuteranopiaBlindColorMap.put("MOTORWAY_OUTLINE", Color.ORANGE);
        deuteranopiaBlindColorMap.put("MOTORWAY_OUTLINE_close", Color.ORANGE);
        deuteranopiaBlindColorMap.put("PEDESTRIAN", Color.rgb(230, 230, 230,1));
        deuteranopiaBlindColorMap.put("PEDESTRIAN_close", Color.rgb(230, 230, 230,1));
        deuteranopiaBlindColorMap.put("PEDESTRIAN_OUTLINE", Color.LIGHTGRAY);
        deuteranopiaBlindColorMap.put("PEDESTRIAN_OUTLINE_close", Color.LIGHTGRAY);
        deuteranopiaBlindColorMap.put("PRIMARY", Color.LIGHTYELLOW);
        deuteranopiaBlindColorMap.put("PRIMARY_close", Color.LIGHTYELLOW);
        deuteranopiaBlindColorMap.put("PRIMARY_OUTLINE", Color.YELLOW);
        deuteranopiaBlindColorMap.put("PRIMARY_OUTLINE_close", Color.YELLOW);
        deuteranopiaBlindColorMap.put("PATH", Color.rgb(69, 65, 96,1));
        deuteranopiaBlindColorMap.put("PATH_close", Color.rgb(69, 65, 96,1));
        deuteranopiaBlindColorMap.put("PATH_OUTLINE", Color.TRANSPARENT);
        deuteranopiaBlindColorMap.put("PATH_OUTLINE_close", Color.TRANSPARENT);
        deuteranopiaBlindColorMap.put("RESIDENTIAL", Color.WHITE);
        deuteranopiaBlindColorMap.put("RESIDENTIAL_close", Color.WHITE);
        deuteranopiaBlindColorMap.put("RESIDENTIAL_OUTLINE", Color.LIGHTGRAY);
        deuteranopiaBlindColorMap.put("RESIDENTIAL_OUTLINE_close", Color.LIGHTGRAY);
        deuteranopiaBlindColorMap.put("TERTIARY", Color.WHITE);
        deuteranopiaBlindColorMap.put("TERTIARY_close", Color.WHITE);
        deuteranopiaBlindColorMap.put("TERTIARY_OUTLINE", Color.LIGHTGRAY);
        deuteranopiaBlindColorMap.put("TERTIARY_OUTLINE_close", Color.LIGHTGRAY);
        deuteranopiaBlindColorMap.put("TEXT_OUTLINE", Color.WHITE);
        deuteranopiaBlindColorMap.put("TEXT_OUTLINE_close", Color.WHITE);
        deuteranopiaBlindColorMap.put("TEXT", Color.BLACK);
        deuteranopiaBlindColorMap.put("TEXT_close", Color.BLACK);

        // Red blindness
        protanopiaBlindColorMap.put("BACKGROUND", Color.rgb(176, 211, 232,1));
        protanopiaBlindColorMap.put("WATER", Color.rgb(176, 211, 232,1));
        protanopiaBlindColorMap.put("WATER_close", Color.rgb(176, 211, 232,1));
        protanopiaBlindColorMap.put("BUILDING", Color.rgb(190, 190, 190,1));
        protanopiaBlindColorMap.put("BUILDING_close", Color.rgb(190, 190, 190,1));
        protanopiaBlindColorMap.put("COASTLINE", Color.rgb(206, 234, 214,1));
        protanopiaBlindColorMap.put("coastline_near", Color.rgb(255, 255, 255,1));
        protanopiaBlindColorMap.put("coastline_far", Color.rgb(206, 234, 214,1));
        protanopiaBlindColorMap.put("GRASS", Color.rgb(206, 234, 214,1));
        protanopiaBlindColorMap.put("GRASS_close", Color.rgb(206, 234, 214,1));
        protanopiaBlindColorMap.put("FOREST", Color.rgb(145, 189, 159,1));
        protanopiaBlindColorMap.put("FOREST_close", Color.rgb(145, 189, 159,1));
        protanopiaBlindColorMap.put("CEMENT", Color.rgb(233, 233, 233,1));
        protanopiaBlindColorMap.put("CEMENT_close", Color.rgb(233, 233, 233,1));
        protanopiaBlindColorMap.put("COMMERCIAL_GROUND", Color.LIGHTYELLOW);
        protanopiaBlindColorMap.put("COMMERCIAL_GROUND_close", Color.LIGHTYELLOW);
        protanopiaBlindColorMap.put("WATERWAY", Color.rgb(176, 211, 232,1));
        protanopiaBlindColorMap.put("WATERWAY_close", Color.rgb(176, 211, 232,1));
        protanopiaBlindColorMap.put("RAILWAY", Color.rgb(190, 190, 190,1));
        protanopiaBlindColorMap.put("RAILWAY_close", Color.rgb(190, 190, 190,1));
        protanopiaBlindColorMap.put("SECONDARY", Color.WHITE);
        protanopiaBlindColorMap.put("SECONDARY_close", Color.WHITE);
        protanopiaBlindColorMap.put("SECONDARY_OUTLINE", Color.LIGHTGRAY);
        protanopiaBlindColorMap.put("SECONDARY_OUTLINE_close", Color.LIGHTGRAY);
        protanopiaBlindColorMap.put("MOTORWAY", Color.rgb(234, 176, 183,1));
        protanopiaBlindColorMap.put("MOTORWAY_close", Color.rgb(234, 176, 183,1));
        protanopiaBlindColorMap.put("MOTORWAY_OUTLINE", Color.rgb(201, 136, 142,1));
        protanopiaBlindColorMap.put("MOTORWAY_OUTLINE_close", Color.rgb(201, 136, 142,1));
        protanopiaBlindColorMap.put("PEDESTRIAN", Color.rgb(230, 230, 230,1));
        protanopiaBlindColorMap.put("PEDESTRIAN_close", Color.rgb(230, 230, 230,1));
        protanopiaBlindColorMap.put("PEDESTRIAN_OUTLINE", Color.LIGHTGRAY);
        protanopiaBlindColorMap.put("PEDESTRIAN_OUTLINE_close", Color.LIGHTGRAY);
        protanopiaBlindColorMap.put("PRIMARY", Color.rgb(245, 216, 217,1));
        protanopiaBlindColorMap.put("PRIMARY_close", Color.rgb(245, 216, 217,1));
        protanopiaBlindColorMap.put("PRIMARY_OUTLINE", Color.rgb(234, 176, 183,1));
        protanopiaBlindColorMap.put("PRIMARY_OUTLINE_close", Color.rgb(234, 176, 183,1));
        protanopiaBlindColorMap.put("PATH", Color.GREEN);
        protanopiaBlindColorMap.put("PATH_close", Color.GREEN);
        protanopiaBlindColorMap.put("PATH_OUTLINE", Color.TRANSPARENT);
        protanopiaBlindColorMap.put("PATH_OUTLINE_close", Color.TRANSPARENT);
        protanopiaBlindColorMap.put("RESIDENTIAL", Color.WHITE);
        protanopiaBlindColorMap.put("RESIDENTIAL_close", Color.WHITE);
        protanopiaBlindColorMap.put("RESIDENTIAL_OUTLINE", Color.LIGHTGRAY);
        protanopiaBlindColorMap.put("RESIDENTIAL_OUTLINE_close", Color.LIGHTGRAY);
        protanopiaBlindColorMap.put("TERTIARY", Color.WHITE);
        protanopiaBlindColorMap.put("TERTIARY_close", Color.WHITE);
        protanopiaBlindColorMap.put("TERTIARY_OUTLINE", Color.LIGHTGRAY);
        protanopiaBlindColorMap.put("TERTIARY_OUTLINE_close", Color.LIGHTGRAY);
        protanopiaBlindColorMap.put("TEXT_OUTLINE", Color.WHITE);
        protanopiaBlindColorMap.put("TEXT_OUTLINE_close", Color.WHITE);
        protanopiaBlindColorMap.put("TEXT", Color.BLACK);
        protanopiaBlindColorMap.put("TEXT_close", Color.BLACK);


        // Blue blindness
        tritanBlindColorMap.put("BACKGROUND", Color.rgb(203, 196, 220,1));
        tritanBlindColorMap.put("WATER", Color.rgb(203, 196, 220,1));
        tritanBlindColorMap.put("WATER_close", Color.rgb(203, 196, 220,1));
        tritanBlindColorMap.put("BUILDING", Color.rgb(190, 190, 190,1));
        tritanBlindColorMap.put("BUILDING_close", Color.rgb(190, 190, 190,1));
        tritanBlindColorMap.put("COASTLINE", Color.rgb(206, 234, 214,1));
        tritanBlindColorMap.put("coastline_near", Color.rgb(255, 255, 255,1));
        tritanBlindColorMap.put("coastline_far", Color.rgb(206, 234, 214,1));
        tritanBlindColorMap.put("GRASS", Color.rgb(206, 234, 214,1));
        tritanBlindColorMap.put("GRASS_close", Color.rgb(206, 234, 214,1));
        tritanBlindColorMap.put("FOREST", Color.rgb(145, 189, 159,1));
        tritanBlindColorMap.put("FOREST_close", Color.rgb(145, 189, 159,1));
        tritanBlindColorMap.put("CEMENT", Color.rgb(233, 233, 233,1));
        tritanBlindColorMap.put("CEMENT_close", Color.rgb(233, 233, 233,1));
        tritanBlindColorMap.put("COMMERCIAL_GROUND", Color.LIGHTYELLOW);
        tritanBlindColorMap.put("COMMERCIAL_GROUND_close", Color.LIGHTYELLOW);
        tritanBlindColorMap.put("WATERWAY", Color.rgb(203, 196, 220,1));
        tritanBlindColorMap.put("WATERWAY_close", Color.rgb(203, 196, 220,1));
        tritanBlindColorMap.put("RAILWAY", Color.rgb(190, 190, 190,1));
        tritanBlindColorMap.put("RAILWAY_close", Color.rgb(190, 190, 190,1));
        tritanBlindColorMap.put("SECONDARY", Color.WHITE);
        tritanBlindColorMap.put("SECONDARY_close", Color.WHITE);
        tritanBlindColorMap.put("SECONDARY_OUTLINE", Color.LIGHTGRAY);
        tritanBlindColorMap.put("SECONDARY_OUTLINE_close", Color.LIGHTGRAY);
        tritanBlindColorMap.put("MOTORWAY", Color.YELLOW);
        tritanBlindColorMap.put("MOTORWAY_close", Color.YELLOW);
        tritanBlindColorMap.put("MOTORWAY_OUTLINE", Color.ORANGE);
        tritanBlindColorMap.put("MOTORWAY_OUTLINE_close", Color.ORANGE);
        tritanBlindColorMap.put("PEDESTRIAN", Color.rgb(230, 230, 230,1));
        tritanBlindColorMap.put("PEDESTRIAN_close", Color.rgb(230, 230, 230,1));
        tritanBlindColorMap.put("PEDESTRIAN_OUTLINE", Color.LIGHTGRAY);
        tritanBlindColorMap.put("PEDESTRIAN_OUTLINE_close", Color.LIGHTGRAY);
        tritanBlindColorMap.put("PRIMARY", Color.LIGHTYELLOW);
        tritanBlindColorMap.put("PRIMARY_close", Color.LIGHTYELLOW);
        tritanBlindColorMap.put("PRIMARY_OUTLINE", Color.YELLOW);
        tritanBlindColorMap.put("PRIMARY_OUTLINE_close", Color.YELLOW);
        tritanBlindColorMap.put("PATH", Color.GREEN);
        tritanBlindColorMap.put("PATH_close", Color.GREEN);
        tritanBlindColorMap.put("PATH_OUTLINE", Color.TRANSPARENT);
        tritanBlindColorMap.put("PATH_OUTLINE_close", Color.TRANSPARENT);
        tritanBlindColorMap.put("RESIDENTIAL", Color.WHITE);
        tritanBlindColorMap.put("RESIDENTIAL_close", Color.WHITE);
        tritanBlindColorMap.put("RESIDENTIAL_OUTLINE", Color.LIGHTGRAY);
        tritanBlindColorMap.put("RESIDENTIAL_OUTLINE_close", Color.LIGHTGRAY);
        tritanBlindColorMap.put("TERTIARY", Color.WHITE);
        tritanBlindColorMap.put("TERTIARY_close", Color.WHITE);
        tritanBlindColorMap.put("TERTIARY_OUTLINE", Color.LIGHTGRAY);
        tritanBlindColorMap.put("TERTIARY_OUTLINE_close", Color.LIGHTGRAY);
        tritanBlindColorMap.put("TEXT_OUTLINE", Color.WHITE);
        tritanBlindColorMap.put("TEXT_OUTLINE_close", Color.WHITE);
        tritanBlindColorMap.put("TEXT", Color.BLACK);
        tritanBlindColorMap.put("TEXT_close", Color.BLACK);

        // Grey-scale
        monochromaBlindColorMap.put("BACKGROUND", Color.rgb(255, 255, 255,1));
        monochromaBlindColorMap.put("WATER", Color.rgb(255, 255, 255,1));
        monochromaBlindColorMap.put("WATER_close", Color.rgb(255, 255, 255,1));
        monochromaBlindColorMap.put("BUILDING", Color.rgb(190, 190, 190,1));
        monochromaBlindColorMap.put("BUILDING_close", Color.rgb(190, 190, 190,1));
        monochromaBlindColorMap.put("COASTLINE", Color.web("#E4E4E4"));
        monochromaBlindColorMap.put("coastline_near", Color.rgb(255, 255, 255,1));
        monochromaBlindColorMap.put("coastline_far", Color.rgb(214, 214, 214,1));
        monochromaBlindColorMap.put("GRASS", Color.rgb(214, 214, 214,1));
        monochromaBlindColorMap.put("GRASS_close", Color.rgb(214, 214, 214,1));
        monochromaBlindColorMap.put("FOREST", Color.web("#6E6E6E"));
        monochromaBlindColorMap.put("FOREST_close", Color.web("#6E6E6E"));
        monochromaBlindColorMap.put("CEMENT", Color.rgb(233, 233, 233,1));
        monochromaBlindColorMap.put("CEMENT_close", Color.rgb(233, 233, 233,1));
        monochromaBlindColorMap.put("COMMERCIAL_GROUND", Color.LIGHTGRAY);
        monochromaBlindColorMap.put("COMMERCIAL_GROUND_close", Color.LIGHTGRAY);
        monochromaBlindColorMap.put("WATERWAY", Color.rgb(255, 255, 255,1));
        monochromaBlindColorMap.put("WATERWAY_close", Color.rgb(255, 255, 255,1));
        monochromaBlindColorMap.put("RAILWAY", Color.rgb(190, 190, 190,1));
        monochromaBlindColorMap.put("RAILWAY_close", Color.rgb(190, 190, 190,1));
        monochromaBlindColorMap.put("SECONDARY", Color.WHITE);
        monochromaBlindColorMap.put("SECONDARY_close", Color.WHITE);
        monochromaBlindColorMap.put("SECONDARY_OUTLINE", Color.LIGHTGRAY);
        monochromaBlindColorMap.put("SECONDARY_OUTLINE_close", Color.LIGHTGRAY);
        monochromaBlindColorMap.put("MOTORWAY", Color.web("#F2F1F1"));
        monochromaBlindColorMap.put("MOTORWAY_close", Color.web("#F2F1F1"));
        monochromaBlindColorMap.put("MOTORWAY_OUTLINE", Color.DARKGRAY);
        monochromaBlindColorMap.put("MOTORWAY_OUTLINE_close", Color.DARKGRAY);
        monochromaBlindColorMap.put("PEDESTRIAN", Color.rgb(230, 230, 230,1));
        monochromaBlindColorMap.put("PEDESTRIAN_close", Color.rgb(230, 230, 230,1));
        monochromaBlindColorMap.put("PEDESTRIAN_OUTLINE", Color.LIGHTGRAY);
        monochromaBlindColorMap.put("PEDESTRIAN_OUTLINE_close", Color.LIGHTGRAY);
        monochromaBlindColorMap.put("PRIMARY", Color.LIGHTGRAY);
        monochromaBlindColorMap.put("PRIMARY_close", Color.LIGHTGRAY);
        monochromaBlindColorMap.put("PRIMARY_OUTLINE", Color.DARKGRAY);
        monochromaBlindColorMap.put("PRIMARY_OUTLINE_close", Color.DARKGRAY);
        monochromaBlindColorMap.put("PATH", Color.web("#5E5E5E"));
        monochromaBlindColorMap.put("PATH_close", Color.web("#5E5E5E"));
        monochromaBlindColorMap.put("PATH_OUTLINE", Color.TRANSPARENT);
        monochromaBlindColorMap.put("PATH_OUTLINE_close", Color.TRANSPARENT);
        monochromaBlindColorMap.put("RESIDENTIAL", Color.WHITE);
        monochromaBlindColorMap.put("RESIDENTIAL_close", Color.WHITE);
        monochromaBlindColorMap.put("RESIDENTIAL_OUTLINE", Color.LIGHTGRAY);
        monochromaBlindColorMap.put("RESIDENTIAL_OUTLINE_close", Color.LIGHTGRAY);
        monochromaBlindColorMap.put("TERTIARY", Color.WHITE);
        monochromaBlindColorMap.put("TERTIARY_close", Color.WHITE);
        monochromaBlindColorMap.put("TERTIARY_OUTLINE", Color.LIGHTGRAY);
        monochromaBlindColorMap.put("TERTIARY_OUTLINE_close", Color.LIGHTGRAY);
        monochromaBlindColorMap.put("TEXT_OUTLINE", Color.WHITE);
        monochromaBlindColorMap.put("TEXT_OUTLINE_close", Color.WHITE);
        monochromaBlindColorMap.put("TEXT", Color.rgb(30, 30,30,1));
        monochromaBlindColorMap.put("TEXT_close", Color.rgb(30, 30,30,1));

        colorMap = defaultColorMap;
    }


    public void changeTheme(String theme) {
        switch (theme) {
            case "default":
                colorMap = defaultColorMap;
                break;
            case "dark":
                colorMap = darkColorMap;
                break;
            case "deuteranopia":
                colorMap = deuteranopiaBlindColorMap;
                break;
            case "protanopia":
                colorMap = protanopiaBlindColorMap;
                break;
            case "tritanopia":
                colorMap = tritanBlindColorMap;
                break;
            case "monochromia":
                colorMap = monochromaBlindColorMap;
                break;
            default:
                colorMap = defaultColorMap;
                break;
        }
    }
    public void updateCoastlineColorFromZoom(double zoomLevel) {
        Color coastlineNear = colorMap.get("coastline_near");
        Color coastlineFar = colorMap.get("coastline_far");

        if (zoomLevel < 300) {
            colorMap.put("COASTLINE", coastlineNear);
        } else if (zoomLevel > 900) {
            colorMap.put("COASTLINE", coastlineFar);
        } else {
            double factor = (zoomLevel - 300) / 600.0;

            Color interpolatedColor = coastlineNear.interpolate(coastlineFar, factor);
            colorMap.put("COASTLINE", interpolatedColor);

        }
    }

    public void fadeElementColor(double zoomLevel, ShapeType shapeType, int fadeStart, int fadeLength) {
        Color shapeNear = colorMap.get(shapeType.toString() + "_close");
        Color transparent = new Color(shapeNear.getRed(), shapeNear.getGreen(), shapeNear.getBlue(), 0);

        if (zoomLevel < fadeStart-fadeLength) {
            colorMap.put(shapeType.toString(), shapeNear);
        } else if (zoomLevel > fadeStart) {
            colorMap.put(shapeType.toString(), transparent);
        } else {
            double factor = (zoomLevel - (fadeStart-fadeLength)) / (fadeStart - (fadeStart - fadeLength));

            Color interpolatedColor = shapeNear.interpolate(transparent, factor);
            colorMap.put(shapeType.toString(), interpolatedColor);

        }
    }

    public void fadeRoadColor(double zoomLevel, RoadType roadType, int fadeStart, int fadeLength) {
        Color roadNear = colorMap.get(roadType.toString() + "_close");
        Color roadTransparent = new Color(roadNear.getRed(), roadNear.getGreen(), roadNear.getBlue(), 0);
        Color roadOutlineNear = colorMap.get(roadType.toString() + "_OUTLINE_close");

        if (zoomLevel < fadeStart-fadeLength) {
            colorMap.put(roadType.toString(), roadNear);
            colorMap.put(roadType.toString() + "_OUTLINE", roadOutlineNear);
        } else if (zoomLevel > fadeStart) {
            colorMap.put(roadType.toString(), roadTransparent);
            colorMap.put(roadType.toString() + "_OUTLINE", roadTransparent);
        } else {
            double factor = (zoomLevel - (fadeStart-fadeLength)) / (fadeStart - (fadeStart - fadeLength));

            Color interpolatedColor = roadNear.interpolate(roadTransparent, factor);
            colorMap.put(roadType.toString(), interpolatedColor);
            colorMap.put(roadType.toString() + "_OUTLINE", roadTransparent);

        }
    }

    public void fadeTextColor(double zoomLevel, int fadeStart, int fadeLength) {
        Color textNear = colorMap.get("TEXT_close");
        Color textOutlineNear = colorMap.get("TEXT_OUTLINE_close");
        Color textTransparent = new Color(textNear.getRed(), textNear.getGreen(), textNear.getBlue(), 0);
        Color textOutlineTransparent = new Color(textOutlineNear.getRed(), textOutlineNear.getGreen(), textOutlineNear.getBlue(), 0);

        if (zoomLevel < fadeStart-fadeLength) {
            colorMap.put("TEXT", textNear);
            colorMap.put("TEXT_OUTLINE", textOutlineNear);
        } else if (zoomLevel > fadeStart) {
            colorMap.put("TEXT", textTransparent);
            colorMap.put("TEXT_OUTLINE", textOutlineTransparent);
        } else {
            double factor = (zoomLevel - (fadeStart-fadeLength)) / (fadeStart - (fadeStart - fadeLength));

            Color interpolatedColor = textNear.interpolate(textTransparent, factor);
            colorMap.put("TEXT", interpolatedColor);
            Color interpolatedOutlineColor = textOutlineNear.interpolate(textOutlineTransparent, factor);
            colorMap.put("TEXT_OUTLINE", interpolatedOutlineColor);

        }
    }


    public static MapColor getInstance() {
        return instance;
    }

}
